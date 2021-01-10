package com.mzl0101.diary.controller;


import com.mzl0101.diary.service.ISysNodeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tree")
public class SysNodeController {
    private static final Logger logger = LoggerFactory.getLogger(SysNodeController.class);
    @Autowired
    private ISysNodeService treeNodeService;

    /**
     * 根据节点查询该节点下所有节点
     * @return
     */

    @ApiOperation(value = "树查询测试" ,  notes="树查询测试")
    @RequestMapping(value="/find",method= RequestMethod.GET)
    public ResponseEntity getTreeUserAll() {
        return new ResponseEntity<>(treeNodeService.findAll(), HttpStatus.OK);
    }


}
