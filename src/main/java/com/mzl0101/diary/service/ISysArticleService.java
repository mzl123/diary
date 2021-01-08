package com.mzl0101.diary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl0101.diary.entity.SysArticle;

public interface ISysArticleService extends IService<SysArticle> {

    void deploy();
}
