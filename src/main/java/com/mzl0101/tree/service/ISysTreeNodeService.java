package com.mzl0101.tree.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzl0101.tree.entity.SysTreeNode;

import java.util.List;

/**
 * <p>
 * ???ڵ????ݱ 服务类
 * </p>
 *
 * @author mzl
 * @since 2020-06-24
 */
public interface ISysTreeNodeService extends IService<SysTreeNode> {
    SysTreeNode findAll();
}
