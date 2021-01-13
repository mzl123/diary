package com.mzl0101.diary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzl0101.diary.entity.SysNode;
import com.mzl0101.diary.mapper.SysNodeMapper;
import com.mzl0101.diary.service.ISysNodeService;
import com.mzl0101.common.util.RedisClient;
import com.mzl0101.common.util.TreeNodeUtil;
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
    @Autowired
    private SysNodeMapper sysNodeMapper;
    @Autowired
    private RedisClient redisClient;
    @Override
    public SysNode findAll() {
        SysNode sysNode = null;
        // 查询redis
        Object treeNode = redisClient.get("treeNode");
        if (treeNode == null)
        {
            // 查询mysql数据库
            QueryWrapper<SysNode> queryWrapper = new QueryWrapper<>();
            List<SysNode> treeNodeList = sysNodeMapper.selectList(queryWrapper);
            TreeNodeUtil treeNodeUti = new TreeNodeUtil(treeNodeList);
            sysNode = treeNodeUti.generateTreePo(-1l);
            redisClient.set("treeNode", sysNode);
            redisClient.expire("treeNode", 60);
        }
        else
        {
            sysNode = (SysNode) treeNode;
        }
        return sysNode;
    }

}
