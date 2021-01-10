package com.mzl0101.diary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl0101.diary.entity.SysStorage;
import com.mzl0101.diary.mapper.SysStorageMapper;
import com.mzl0101.diary.service.ISysStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SysStorageServiceImpl extends ServiceImpl<SysStorageMapper, SysStorage> implements ISysStorageService {
    private static final Logger logger = LoggerFactory.getLogger(SysStorageServiceImpl.class);

}
