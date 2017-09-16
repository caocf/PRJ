package com.huzhouport.user.model;

public class Post implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int postId;
	private String postName;//职务名称
	private String defaultRole;//默认角色
	
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getDefaultRole() {
		return defaultRole;
	}
	public void setDefaultRole(String defaultRole) {
		this.defaultRole = defaultRole;
	}

}