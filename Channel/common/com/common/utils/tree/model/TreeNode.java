package com.common.utils.tree.model;

/**
 * 树节点
 * 
 * @author DJ
 * 
 */
public class TreeNode {
	/**
	 * 节点ID
	 */
	private int id;
	/**
	 * 节点名
	 */
	private String name;
	/**
	 * 节点类型
	 */
	private int type;
	/**
	 * 节点描述
	 */
	private String desc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return name + ":" + desc;
	}
}
