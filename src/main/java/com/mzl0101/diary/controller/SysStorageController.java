package com.mzl0101.diary.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storage")
public class SysStorageController {
    private static final Logger logger = LoggerFactory.getLogger(SysStorageController.class);
}
