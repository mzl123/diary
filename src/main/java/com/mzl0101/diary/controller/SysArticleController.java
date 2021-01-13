package com.mzl0101.diary.controller;


import com.mzl0101.diary.entity.SysArticle;
import com.mzl0101.diary.service.ISysArticleService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/article")
public class SysArticleController {
    private static final Logger logger = LoggerFactory.getLogger(SysArticleController.class);

    @Autowired
    private ISysArticleService articleService;


    @ApiOperation(value = "文章发布" ,  notes="文章发布")
    @RequestMapping(value="/deploy",method= RequestMethod.POST)
    public ResponseEntity deployArticle(@RequestBody SysArticle sysArticle) throws IOException {
        articleService.deploy(sysArticle);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ApiOperation(value = "文章同步" ,  notes="文章同步")
    @RequestMapping(value="/sync",method= RequestMethod.POST)
    public ResponseEntity syncAllArticle() throws IOException, ParseException {
        articleService.sync();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ApiOperation(value = "部署全部文章" ,  notes="部署全部文章")
    @RequestMapping(value="/deployArticles",method= RequestMethod.POST)
    public ResponseEntity confirmDeployArticles() throws IOException {
        String res = articleService.confirmDeployArticles();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
