package com.mzl0101.diary.controller;

import com.mzl0101.diary.entity.SysStorage;
import com.mzl0101.diary.service.ISysStorageService;
import com.mzl0101.diary.service.dto.SysStorageDto;
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


@RestController
@RequestMapping("/storage")
public class SysStorageController {
    private static final Logger logger = LoggerFactory.getLogger(SysStorageController.class);

    @Autowired
    private ISysStorageService iSysStorageService;

    @ApiOperation(value = "创建存储文件" ,  notes="创建存储文件")
    @RequestMapping(value="/create",method= RequestMethod.POST)
    public ResponseEntity createStorageFiles(@RequestBody SysStorage sysStorage){
        return new ResponseEntity<SysStorageDto>(iSysStorageService.createStorageFiles(sysStorage),HttpStatus.OK);
    }
}
