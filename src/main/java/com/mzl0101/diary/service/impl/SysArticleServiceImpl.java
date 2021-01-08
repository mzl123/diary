package com.mzl0101.diary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl0101.diary.entity.SysArticle;
import com.mzl0101.diary.mapper.SysArticleMapper;
import com.mzl0101.diary.service.ISysArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysArticleServiceImpl extends ServiceImpl<SysArticleMapper, SysArticle> implements ISysArticleService {
    private static final Logger logger = LoggerFactory.getLogger(SysArticleServiceImpl.class);

    @Autowired
    private SysArticleMapper sysArticleMapper;

    @Override
    public void deploy(){
        //1.读取文件
        //1.1 根据目录读取markdown文件
        //readMarkdownFiles();
        //1.2 根据目录读取图片文件
        //readImageFiles();
        //2.保存数据

        //3.创建文件
        //4.生成静态文件
    }
}
