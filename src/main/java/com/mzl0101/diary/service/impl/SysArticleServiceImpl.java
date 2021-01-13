package com.mzl0101.diary.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl0101.diary.entity.SysArticle;
import com.mzl0101.diary.entity.SysStorage;
import com.mzl0101.diary.mapper.SysArticleMapper;
import com.mzl0101.diary.mapper.SysStorageMapper;
import com.mzl0101.diary.service.ISysArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;

@Service
public class SysArticleServiceImpl extends ServiceImpl<SysArticleMapper, SysArticle> implements ISysArticleService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SysArticleServiceImpl.class);

    @Value("${cmd.threadname:cmd-executor}")
    private String threadName;

    @Value("${cmd.taskQueueMaxStorage:20}")
    private Integer taskQueueMaxStorage;

    @Value("${cmd.corePoolSize:4}")
    private Integer corePoolSize;

    @Value("${cmd.maximumPoolSize:8}")
    private Integer maximumPoolSize;

    @Value("${cmd.keepAliveSeconds:15}")
    private  Integer  keepAliveSeconds;
    private ThreadPoolExecutor executor;
    private static final String  BASH = "sh";
    private static final String  BASH_PARAM = "-c";

    @Override
    public void afterPropertiesSet() {
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveSeconds, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(taskQueueMaxStorage),
                new ThreadFactory() {
                    public Thread newThread(Runnable r) {
                        return new Thread(r, threadName + r.hashCode());
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());
    }


    class ReadTask implements Callable<String> {
        InputStream is;

        ReadTask(InputStream is) {
            this.is = is;
        }

        @Override
        public String call() throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

    @Autowired
    private SysArticleMapper sysArticleMapper;
    @Autowired
    private SysStorageMapper sysStorageMapper;
    @Autowired
    private SaveSysArticleBatchServiceImpl saveSysArticleBatchService;
    @Value("${article.path}")
    private String articlePath;
    @Value("${img.path}")
    private String imgPath;
    public static SysArticleServiceImpl sysArticleServiceImpl;


    @PostConstruct
    public void init(){
        sysArticleServiceImpl = this;
        sysArticleServiceImpl.sysStorageMapper = this.sysStorageMapper;
    }
    @Override
    public void sync() throws IOException {
        //1.读取文件
        //1.1 根据目录读取markdown文件
        List<SysArticle> articles = new ArrayList<>();
        try (Stream<Path> filePathStream=Files.walk(Paths.get(articlePath))) {
            filePathStream.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    System.out.println(filePath);
                    try {
                        final Map<String, String> fileMap = readMarkdownFiles(filePath.toString());
                        SysArticle sysArticle = new SysArticle();
                        sysArticle.setArticleTitle(fileMap.get("title"));
                        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd"));
                        sysArticle.setArticleDate(sdf.parse(fileMap.get("date")));
                        sysArticle.setArticleCategories(fileMap.get("categories"));
                        sysArticle.setArticleContent(fileMap.get("article"));
                        sysArticle.setArticleTags(fileMap.get("tags"));
                        sysArticle.setArticleCreateTime(new Date());
                        articles.add(sysArticle);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        saveSysArticleBatchService.saveBatch(articles);
        //1.2 根据目录读取图片文件
        File imgFile = new File(imgPath);
        readImageFiles(imgFile);
    }

    @Override
    public void deploy(SysArticle sysArticle) throws IOException {
        System.out.println(sysArticle.toString());
        //1.获取数据
        sysArticle.setArticleCreateTime(new Date());
        //2.保存数据
        sysArticleMapper.insert(sysArticle);
        //3.创建markdown文件
        createNewMarkdownFile(articlePath, sysArticle);
    }

    @Override
    public String confirmDeployArticles(){
        // 生成静态部署文件
        Process p = null;
        String res;
        try {
            List<String> cmds = new ArrayList<>();
            cmds.add("ipconfig");
            ProcessBuilder pb = new ProcessBuilder(cmds);
            p = pb.start();
            Future<String> errorFuture = executor.submit(new ReadTask(p.getErrorStream()));
            Future<String> resFuture = executor.submit(new ReadTask(p.getInputStream()));
            int exitValue = p.waitFor();
            if (exitValue > 0) {
                throw new RuntimeException(errorFuture.get());
            }
            res = resFuture.get();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (p != null) {p.destroy();}
        }
        if (StringUtils.isNotBlank(res) && res.endsWith(System.lineSeparator())) {
            res = res.substring(0, res.lastIndexOf(System.lineSeparator()));
        }
        return  res;
    }
    /**
     * 根据文件路径读取文件中的内容并返回结果
     * @param filePath
     * @return
     */
    private static Map<String,String> readMarkdownFiles(String filePath) throws IOException{
        Map<String,String> result = new HashMap<>();
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(fileReader);
        //设置指定行数
        int titleNumber = 2,  dateNumber = 3, tagsNumber = 4, categoriesNumber = 5, articleNumber = 7;
        String title="", date = "", tags = "",categories = "";
        String txt = "" ;
        StringBuilder article = new StringBuilder();
        int lines = 0 ;
        while (txt != null ) {
            lines++;
            txt = reader.readLine();
            if (lines == titleNumber) {
                title = txt;
            }
            if (lines == dateNumber) {
                date = txt;
            }
            if (lines == tagsNumber) {
                tags = txt;
            }
            if (lines == categoriesNumber) {
                categories = txt;
            }
            if (lines >= articleNumber) {
                article.append(txt);
            }
        }
        reader.close();
        fileReader.close();
        long timeEnd = System.currentTimeMillis();
        result.put("title", title.split(":")[1]);
        result.put("date", date.split(":")[1]);
        result.put("tags", tags.split(":")[1]);
        result.put("categories", categories.split(":")[1]);
        result.put("article", article.toString());
        return result;
    }

    private static void readImageFiles(File file){
        File[] fs = file.listFiles();
        for(File f:fs){
            if(f.isDirectory())
            {
                readImageFiles(f);
            }
            if(f.isFile())
            {
                System.out.println(f);
                String fileName = f.getName();
                SysStorage sysStorage = new SysStorage();
                sysStorage.setStorageName(fileName);
                sysStorage.setStoragePath(f.getPath());
                sysStorage.setStorageSize(f.length()+"");
                sysStorage.setStorageSuffix(fileName.substring(fileName.lastIndexOf(".")+1));
                sysStorage.setStorageType("图片");
                sysStorage.setStorageCreateTime(new Date());
                sysArticleServiceImpl.sysStorageMapper.insert(sysStorage);
            }
        }
    }

    private static void createNewMarkdownFile(String articlePath, SysArticle sysArticle) throws IOException {
        String title = sysArticle.getArticleTitle();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(sysArticle.getArticleDate());
        String categories = sysArticle.getArticleCategories();
        String tags = sysArticle.getArticleTags();
        String content = sysArticle.getArticleContent();
        String fileName = articlePath + title + ".md";
        Path path = Paths.get(fileName);
        // 使用newBufferedWriter创建文件并写文件
        // try-with-resources方法来关闭流，不用手动关闭
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("---");
            writer.newLine();
            writer.write("title: "+ title);
            writer.newLine();
            writer.write("date: "+ date);
            writer.newLine();
            writer.write("tags: "+ tags);
            writer.newLine();
            writer.write("categories: "+ categories);
            writer.newLine();
            writer.write("---");
            writer.newLine();
            writer.write(content);
        }
        //追加写模式
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path,
                             StandardCharsets.UTF_8,
                             StandardOpenOption.APPEND)){
            writer.newLine();
        }
    }

    /**
     * 递归写法遍历文件夹下所有文件
     * @param file
     */
    private static void readFiles(File file){
        File[] fs = file.listFiles();
        for(File f:fs){
            if(f.isDirectory())	//若是目录，则递归打印该目录下的文件
                readFiles(f);
            if(f.isFile())		//若是文件，直接打印
                System.out.println(f);
        }
    }


}
