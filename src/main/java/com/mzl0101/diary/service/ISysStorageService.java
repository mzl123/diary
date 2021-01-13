package com.mzl0101.diary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl0101.diary.entity.SysStorage;
import com.mzl0101.diary.service.dto.SysStorageDto;

public interface ISysStorageService  extends IService<SysStorage> {

    SysStorageDto createStorageFiles(SysStorage sysStorage);
}
