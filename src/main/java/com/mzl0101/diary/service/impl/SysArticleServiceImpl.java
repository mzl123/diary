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
}
