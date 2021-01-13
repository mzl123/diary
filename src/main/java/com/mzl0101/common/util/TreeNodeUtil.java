package com.mzl0101.common.util;

import com.mzl0101.diary.entity.SysNode;

import java.util.*;

public class TreeNodeUtil {
    private List<Map<Long, Object>> mapList = new ArrayList<>();

    //初始化
    public TreeNodeUtil(List<SysNode> List) {
        for (SysNode item : List)
        {
            Map<Long, Object> tempMap = new HashMap<>();
            tempMap.put(item.getNodeId(), item);
            mapList.add(tempMap);
        }
    }

    //根据id获取节点
    public SysNode getTreePoById(Long parentId) {
        SysNode treePo = new SysNode();
        for(Map temp :mapList)
        {
            Set<Long> keys = temp.keySet();
            if(keys.toArray()[0].equals(parentId))
            {
                treePo = (SysNode) temp.get(keys.toArray()[0]);
                break;
            }
        }
        return treePo;
    }
    //获取子节点
    public List<SysNode> getChildrenPoById(Long parentId) {
        List<SysNode> childrenTreePo = new ArrayList<SysNode>();

        for(Map temp :mapList)
        {
            Set<Long> keys = temp.keySet();
            Long key = (Long) keys.toArray()[0];
            SysNode temppo = (SysNode) temp.get(key);
            Long tempParId = temppo.getNodeParentId();
            if(tempParId.equals(parentId))
            {
                childrenTreePo.add(temppo);
            }
        }
        return childrenTreePo;
    }

    //构造新节点方法
    public SysNode generateTreePo(Long rootId) {
        SysNode root = this.getTreePoById(rootId);// 获取父这个节点
        List<SysNode> childrenTreePo = this.getChildrenPoById(rootId);

        for (int i=0;i<childrenTreePo.size();i++) {
            SysNode item = childrenTreePo.get(i);
            SysNode tp = this.generateTreePo(item.getNodeId());
            if (root.getNodes() == null) {
                root.setNodes(new ArrayList<SysNode>());
            }
            root.getNodes().add(tp);
        }
        return root;
    }

}
