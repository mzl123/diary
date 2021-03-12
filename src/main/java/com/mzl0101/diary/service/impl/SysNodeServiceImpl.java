package com.mzl0101.diary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl0101.common.util.TreeNodeUtil;
import com.mzl0101.diary.entity.SysNode;
import com.mzl0101.diary.mapper.SysNodeMapper;
import com.mzl0101.diary.service.ISysNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mzl
 * @since 2020-06-24
 */
@Service
public class SysNodeServiceImpl extends ServiceImpl<SysNodeMapper, SysNode> implements ISysNodeService {
    private static final Logger logger = LoggerFactory.getLogger(SysNodeServiceImpl.class);
    @Autowired(required = false)
    private SysNodeMapper sysNodeMapper;

    /**
     * 单纯测试节点工具类方法
     * @return
     */
    @Override
    public SysNode findAll() {
        QueryWrapper<SysNode> queryWrapper = new QueryWrapper<>();
        List<SysNode> treeNodeList = sysNodeMapper.selectList(queryWrapper);
        TreeNodeUtil treeNodeUti = new TreeNodeUtil(treeNodeList);
        SysNode sysNode = treeNodeUti.generateTreePo(-1l);
        return sysNode;
    }

}
