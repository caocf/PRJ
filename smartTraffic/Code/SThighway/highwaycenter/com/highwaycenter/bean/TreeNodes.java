package com.highwaycenter.bean;

import java.util.List;

public class TreeNodes   implements java.io.Serializable {
	

	private static final long serialVersionUID = -1695322733925429937L;
	private Object nodeId ;   // 子节点编号
	private Object fzgxId ;   //父子关系id
	private String nodeDesc; //子节点名称
	private Object parientNodeId; // 父节点编号
	private List<TreeNodes> childrenNodes;  //子节点集合
	private List<Object> otherAttributes; //其他属性
	
	public TreeNodes(Object nodeId ,Object fzgxId,String nodeDesc,Object parientNodeId){
		this.nodeId = nodeId;
		this.fzgxId = fzgxId;
		this.nodeDesc = nodeDesc;
		this.parientNodeId = parientNodeId;
		
	}
	
	public TreeNodes(Object nodeId ,String nodeDesc,Object parientNodeId){
		this.nodeId = nodeId;
		this.fzgxId = fzgxId;
		this.nodeDesc = nodeDesc;
		this.parientNodeId = parientNodeId;
		
	}
	public TreeNodes(){
		
	}
	
	public String toString() {
		String result = "{"
		+ "nodeId : '" + nodeId + "'"
		+ ", nodeDesc : '" + nodeDesc + "'"+",parientNodeId :'" +parientNodeId +"'";
	    
		if (otherAttributes != null && otherAttributes.size() != 0) {
			for(TreeNodes node:childrenNodes){
				result += node.toString();
			}
		}
	

		return result + "} \n";
		
		}
	
	public String print() {
		String result = "{"
		+ "nodeId : '" + nodeId + "'"
		+ ", nodeDesc : '" + nodeDesc + "'"+",parientNodeId :'" +parientNodeId +"'";
		if 
		  (childrenNodes != null && childrenNodes.size() != 0) 
		{
		result += ", children : " + childrenNodes.toString();
		} else {
		}
		return result + "}";
		
		}
	

	public Object getNodeId() {
		return nodeId;
	}

	public void setNodeId(Object nodeId) {
		this.nodeId = nodeId;
	}

	public Object getFzgxId() {
		return fzgxId;
	}

	public void setFzgxId(Object fzgxId) {
		this.fzgxId = fzgxId;
	}

	public String getNodeDesc() {
		return nodeDesc;
	}

	public void setNodeDesc(String nodeDesc) {
		this.nodeDesc = nodeDesc;
	}

	public Object getParientNodeId() {
		return parientNodeId;
	}

	public void setParientNodeId(Object parientNodeId) {
		this.parientNodeId = parientNodeId;
	}

	public List<TreeNodes> getChildrenNodes() {
		return childrenNodes;
	}
	public void setChildrenNodes(List<TreeNodes> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}
	public List<Object> getOtherAttributes() {
		return otherAttributes;
	}
	public void setOtherAttributes(List<Object> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

}
