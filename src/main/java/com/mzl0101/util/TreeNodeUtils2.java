package com.mzl0101.util;

import com.mzl0101.tree.entity.SysTreeNode;

import java.util.*;

public class TreeNodeUtils2 {
    private List<Map<Long, Object>> mapList = new ArrayList<>();

    //初始化
    public TreeNodeUtils2(List<SysTreeNode> List) {
        for (SysTreeNode item : List)
        {
            Map<Long, Object> tempMap = new HashMap<>();
            tempMap.put(item.getNodeId(), item);
            mapList.add(tempMap);
        }
    }

    //根据id获取节点
    public SysTreeNode getTreePoById(Long parentId) {
        SysTreeNode treePo = new SysTreeNode();
        for(Map temp :mapList)
        {
            Set<Long> keys = temp.keySet();
            if(keys.toArray()[0].equals(parentId))
            {
                treePo = (SysTreeNode) temp.get(keys.toArray()[0]);
                break;
            }
        }
        return treePo;
    }
    //获取子节点
    public List<SysTreeNode> getChildrenPoById(Long parentId) {
        List<SysTreeNode> childrenTreePo = new ArrayList<SysTreeNode>();

        for(Map temp :mapList)
        {
            Set<Long> keys = temp.keySet();
            Long key = (Long) keys.toArray()[0];
            SysTreeNode temppo = (SysTreeNode) temp.get(key);
            Long tempParId = temppo.getNodeParentId();
            if(tempParId.equals(parentId))
            {
                childrenTreePo.add(temppo);
            }
        }
        return childrenTreePo;
    }

    //构造新节点方法
    public SysTreeNode generateTreePo(Long rootId) {
        SysTreeNode root = this.getTreePoById(rootId);// 获取父这个节点
        List<SysTreeNode> childrenTreePo = this.getChildrenPoById(rootId);

        for (int i=0;i<childrenTreePo.size();i++) {
            SysTreeNode item = childrenTreePo.get(i);
            SysTreeNode tp = this.generateTreePo(item.getNodeId());
            if(root.getNodes()!=null)
            {
                root.getNodes().add(tp);
            }else{
                root.setNodes(new ArrayList<SysTreeNode>());
                root.getNodes().add(tp);
            }
        }
        return root;
    }

}
