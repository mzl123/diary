package com.mzl0101.tree.controller;


import com.mzl0101.tree.entity.SysTreeNode;
import com.mzl0101.tree.service.ISysTreeNodeService;
import com.mzl0101.util.TreeNodeUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/tree")
public class SysTreeNodeController {
    private static final Logger logger = LoggerFactory.getLogger(SysTreeNodeController.class);
    @Autowired
    private ISysTreeNodeService treeNodeService;

    /**
     * 根据节点查询该节点下所有节点
     * @return
     */

    @ApiOperation(value = "树查询测试" ,  notes="树查询测试")
    @RequestMapping(value="/find",method= RequestMethod.GET)
    public SysTreeNode getTreeUserAll() {
        return treeNodeService.findAll();
    }


}
