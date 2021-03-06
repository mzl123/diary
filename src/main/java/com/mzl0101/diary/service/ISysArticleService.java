package com.mzl0101.diary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl0101.diary.entity.SysArticle;

import java.io.IOException;

public interface ISysArticleService extends IService<SysArticle> {

    void deploy(SysArticle sysArticle) throws IOException;

    void sync() throws IOException;

    void confirmDeployArticles() throws IOException, InterruptedException;

}
