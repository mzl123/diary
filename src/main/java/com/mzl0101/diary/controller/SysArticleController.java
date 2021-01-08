package com.mzl0101.diary.controller;


import com.mzl0101.diary.service.ISysArticleService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class SysArticleController {
    private static final Logger logger = LoggerFactory.getLogger(SysArticleController.class);

    @Autowired
    private ISysArticleService articleService;


    @ApiOperation(value = "文章发布" ,  notes="文章发布")
    @RequestMapping(value="/deploy",method= RequestMethod.POST)
    public ResponseEntity deployTreeUserAll() {
        articleService.deploy();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
