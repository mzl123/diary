package com.mzl0101.diary.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl0101.common.util.FileUtil;
import com.mzl0101.diary.entity.SysStorage;
import com.mzl0101.diary.mapper.SysStorageMapper;
import com.mzl0101.diary.service.ISysStorageService;
import com.mzl0101.diary.service.dto.SysStorageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class SysStorageServiceImpl extends ServiceImpl<SysStorageMapper, SysStorage> implements ISysStorageService {
    private static final Logger logger = LoggerFactory.getLogger(SysStorageServiceImpl.class);



    @Autowired(required = false)
    private SysStorageMapper sysStorageMapper;
    @Value("${img.path}")
    private String imgPath;


    @Override
    public SysStorageDto createStorageFiles(String name, MultipartFile multipartFile) throws Exception {
        //获取扩展名
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        File file = FileUtil.upload(multipartFile, imgPath +  File.separator);
        if(ObjectUtil.isNull(file)){
            throw new Exception("上传失败");
        }
        try {
            name = StringUtils.isBlank(name) ? FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            SysStorage sysStorage = new SysStorage();
            sysStorage.setStorageName(name);
            sysStorage.setStorageSuffix(suffix);
            sysStorage.setStoragePath(file.getPath());
            sysStorage.setStorageType("img");
            sysStorage.setStorageSize(FileUtil.getSize(multipartFile.getSize()));
            sysStorage.setStorageCreateTime(new DateTime());
            int i = sysStorageMapper.insert(sysStorage);
            SysStorageDto sysStorageDto = new SysStorageDto();
            sysStorageDto.setStorageName(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()));
            sysStorageDto.setStoragePath(file.getPath());
            return sysStorageDto;
        }catch (Exception e){
            FileUtil.del(file);
            throw e;
        }
    }

}
