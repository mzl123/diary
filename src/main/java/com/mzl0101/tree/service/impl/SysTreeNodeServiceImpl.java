package com.mzl0101.tree.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl0101.tree.entity.SysTreeNode;
import com.mzl0101.tree.mapper.SysTreeNodeMapper;
import com.mzl0101.tree.service.ISysTreeNodeService;
import com.mzl0101.util.RedisClient;
import com.mzl0101.util.TreeNodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mzl
 * @since 2020-06-24
 */
@Service
public class SysTreeNodeServiceImpl extends ServiceImpl<SysTreeNodeMapper, SysTreeNode> implements ISysTreeNodeService {
    private static final Logger logger = LoggerFactory.getLogger(SysTreeNodeServiceImpl.class);
    @Autowired
    private SysTreeNodeMapper sysTreeNodeMapper;
    @Autowired
    private RedisClient redisClient;
    @Override
    public SysTreeNode findAll() {
        SysTreeNode sysTreeNode = null;
        // 查询redis
        Object treeNode = redisClient.get("treeNode");
        if (treeNode == null)
        {
            // 查询mysql数据库
            QueryWrapper<SysTreeNode> queryWrapper = new QueryWrapper<>();
            List<SysTreeNode> treeNodeList =sysTreeNodeMapper.selectList(queryWrapper);
            TreeNodeUtil treeNodeUti = new TreeNodeUtil(treeNodeList);
            sysTreeNode = treeNodeUti.generateTreePo(-1l);
            redisClient.set("treeNode", sysTreeNode);
            redisClient.expire("treeNode", 60);
        }
        else
        {
            sysTreeNode = (SysTreeNode) treeNode;
        }
        return sysTreeNode;
    }

}
