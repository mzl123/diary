package com.mzl0101.tree.controller;


import com.alibaba.fastjson.JSONObject;
import com.mzl0101.tree.entity.SysTreeNode;
import com.mzl0101.tree.service.ISysTreeNodeService;
import com.mzl0101.util.JwtUtil;
import com.mzl0101.util.TreeNodeUtils2;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * ???ڵ????ݱ 前端控制器
 * </p>
 *
 * @author mzl
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/tree/sys-tree-node")
public class SysTreeNodeController {
    private static final Logger logger = LoggerFactory.getLogger(SysTreeNodeController.class);
    @Autowired
    private ISysTreeNodeService treeNodeService;

    /**
     * 根据节点查询该节点下所有节点
     * @return
     */

    //@JwtToken
    @ApiOperation(value = "树查询" ,  notes="树查询")
    @RequestMapping(value="/findall",method= RequestMethod.POST)
    public SysTreeNode getTreeUserAll(@RequestParam("root") Long root) {
        //Integer root = 1;
        List<SysTreeNode> list = treeNodeService.findAll();
        logger.info("---------tuList size is "+list.size()+"--------------------");
        //TreeNodeUtil tuu = new TreeNodeUtil(list);
        TreeNodeUtils2 treeNodeUti = new TreeNodeUtils2(list);
        SysTreeNode tn = treeNodeUti.generateTreePo(root);
        return tn;
    }

   /* @ApiOperation(value = "用户查询" ,  notes="用户查询")
    @RequestMapping(value="/user",method= RequestMethod.POST)
    public List<Map<String, Object>> getTreeUser() {
        return treeNodeService.userQuery();
    }*/

    @PostMapping("/login")
    @ApiOperation(value = "登录" ,  notes="登录")
    public Object login( String userName, String passWord){
        JSONObject jsonObject=new JSONObject();
        // 检验用户是否存在(为了简单，这里假设用户存在，并制造一个uuid假设为用户id)
        String userId = UUID.randomUUID().toString();
        // 生成签名
        String token= JwtUtil.sign(userId);
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("userName", userName);
        userInfo.put("passWord", passWord);
        jsonObject.put("token", token);
        jsonObject.put("user", userInfo);
        return jsonObject;
    }

}
