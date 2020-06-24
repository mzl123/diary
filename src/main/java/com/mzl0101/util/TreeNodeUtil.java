package com.mzl0101.util;


import com.mzl0101.tree.entity.SysTreeNode;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public class TreeNodeUtil {
	//private List<TreeNode> TreeNodeList = new ArrayList<TreeNode>();
	private Map<Long, SysTreeNode> map = new HashMap<Long, SysTreeNode>();
	
	public TreeNodeUtil(List<SysTreeNode> List) {
		//TreeNodeList = List;
		/*for (TreeNode item : List) 
		{
			map.put(item.getId(),item);
		}*/
		map = List.stream().collect(Collectors.toMap(SysTreeNode::getNodeId, a -> a));
	}
	/**
	 * 根据id查找该节点
	 * @param parentId
	 * @return
	 */
	public SysTreeNode getTreeNodeById(Long parentId) {
		SysTreeNode TreeNode = new SysTreeNode();
		Set<Entry<Long, SysTreeNode>> entrys = map.entrySet();
		for(Entry<Long, SysTreeNode> entry:entrys)
		{
			if(entry.getKey().equals(parentId))
			{
				//System.out.println("key值："+entry.getKey()+" value值："+entry.getValue());
				TreeNode = entry.getValue();
				break;
			}
			
		}

	/*	for (TreeNode item : TreeNodeList) {
			
			if (map.containsKey(String.valueOf(parentId)))
			{
				TreeNode = mp.getValue();
				break;
			}
			if (item.getId() == parentId) {
				TreeNode = item;
				break;
			}
		}*/
		
		return TreeNode;
	}
	
	public SysTreeNode getTreeNodeByParentId(Long parentId) {
		SysTreeNode TreeNode = new SysTreeNode();
		Set<Entry<Long, SysTreeNode>> entrys = map.entrySet();
		for(Entry<Long, SysTreeNode> entry:entrys)
		{
			if(entry.getKey().equals(parentId))
			{
				//System.out.println("key值："+entry.getKey()+" value值："+entry.getValue());
				TreeNode = getTreeNodeById(entry.getValue().getNodeParentId());
				
				break;
			}
			
		}
		return TreeNode;
	}
	
	

	public List<SysTreeNode> getChildrenUserById(Long parentId) {
		List<SysTreeNode> childrenTreeNode = new ArrayList<SysTreeNode>();
		//System.out.println("ParentId is "+parentId + "---------------------");
		Set<Entry<Long, SysTreeNode>> entrys = map.entrySet();
		
		for(Entry<Long, SysTreeNode> entry:entrys)
		{
			if(entry.getValue().getNodeParentId().equals(parentId))
			{
				//System.out.println("key值："+entry.getKey()+" value值："+entry.getValue());
				
				childrenTreeNode.add(entry.getValue());
			}
			
		}
		/*for (TreeNode item : TreeNodeList) {
			
			//System.out.println("item Id is" + item.getId());
			if (item.getParentId().equals(parentId)) 
			{
				childrenTreeNode.add(item);
				//System.out.println("-----------添加成功 ----------------------------------");
			}	
		}*/
		return childrenTreeNode;
	}

	/**
	 * 递归方式
	 * @param rootId
	 * @return
	 */
	/*public TreeNode generateTreeNode(Long rootId) {
		TreeNode root = this.getTreeNodeById(rootId);// 获取父这个节点
		//System.out.println("------ root 节 点   " + root.getId() +"----------------");
		List<TreeNode> childrenTreeNode = this.getChildrenUserById(rootId); 
		// 获取直属子节点列表
		//System.out.println("------  节点列表  childrenTreeNode" + childrenTreeNode.size()+"-------------");
		for (TreeNode item : childrenTreeNode) {
			TreeNode tu = this.generateTreeNode(item.getId());
			root.getNodes().add(tu);
		}
		return root;
	}*/
	/**
	 * 非递归方式
	 * @param rootId
	 * @return
	 */
	public SysTreeNode generateTreeNode(Long rootId) {
		
		SysTreeNode root = this.getTreeNodeById(rootId);// 获取父这个节点
		
		List<SysTreeNode> childrenTreeNode = this.getChildrenUserById(rootId);
		// 获取直属子节点列表
		//System.out.println("------  节点列表  childrenTreeNode" + childrenTreeNode.size()+"-------------");
	
		while (childrenTreeNode.size()>0) {
			List<SysTreeNode> tempChilds = new ArrayList<SysTreeNode>();
			
			for (SysTreeNode child : childrenTreeNode)
			{ 
				
				
				// 遍历子节点
				Long tempId = child.getNodeId();
				//System.out.println("------ child 节 点   " + child.getId() +"----------------");
				//System.out.println("----"+this.getChildrenUserById(tempId).size()+"----");
				List<SysTreeNode> childChilds = new ArrayList<SysTreeNode>();
				childChilds = this.getChildrenUserById(tempId);
				//遍历子节点 并找到子节点的父节点 插入父节点
				//if(getTreeNodeByParentId(tempId).getId = );
				
				getTreeNodeByParentId(tempId).getNodes().add(child);
				if (childChilds.size()>0) 
				{
					
					//System.out.println("插入成功");	
					
					for(SysTreeNode item : childChilds)
					{
						//child.getNodes().add(item);
						
						tempChilds.add(item);
					}
					
				}
				
			}
			
			childrenTreeNode = tempChilds;
		}	
			//childrenTreeNode = this.getChildrenUserById(rootId); 
	
		return root;
	}
}
