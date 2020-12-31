package com.mzl0101.diary.controller;


import com.mzl0101.diary.service.ISysArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class SysArticleController {
    private static final Logger logger = LoggerFactory.getLogger(SysArticleController.class);

    @Autowired
    private ISysArticleService articleService;
}
