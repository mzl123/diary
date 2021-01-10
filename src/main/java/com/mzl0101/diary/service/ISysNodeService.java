package com.mzl0101.diary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl0101.diary.entity.SysNode;

/**
 * @author mzl
 * @since 2020-06-24
 */
public interface ISysNodeService extends IService<SysNode> {
    SysNode findAll();
}
