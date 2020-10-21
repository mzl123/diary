package com.mzl0101.tree.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl0101.tree.entity.SysTreeNode;
import com.mzl0101.tree.mapper.SysTreeNodeMapper;
import com.mzl0101.tree.service.ISysTreeNodeService;
import com.mzl0101.util.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    RedisClient redisClient;
    @Override
    public List<SysTreeNode> findAll() {
        List<SysTreeNode> treeNodeList = new ArrayList<>();
        // 查询redis
        Object treeNode = redisClient.get("treeNode");
        if (treeNode == null)
        {
            // 查询mysql数据库
            QueryWrapper<SysTreeNode> queryWrapper = new QueryWrapper<>();
            treeNodeList =sysTreeNodeMapper.selectList(queryWrapper);
            redisClient.set("treeNode", treeNodeList);
            redisClient.expire("treeNode", 60);
            logger.info("没有缓存走数据库-----------");
        }
        else
        {
            treeNodeList = (List<SysTreeNode>) treeNode;
            logger.info("有缓存------------");
        }
        return treeNodeList;
    }

}
