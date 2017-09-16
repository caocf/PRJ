package com.huzhouport.role.model;

public class PermissionKind implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int permissionKindId;
	private String PermissionKindName;

	public int getPermissionKindId() {
		return permissionKindId;
	}

	public void setPermissionKindId(int permissionKindId) {
		this.permissionKindId = permissionKindId;
	}

	public String getPermissionKindName() {
		return PermissionKindName;
	}

	public void setPermissionKindName(String permissionKindName) {
		PermissionKindName = permissionKindName;
	}

}