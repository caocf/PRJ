package com.huzhouport.role.model;

public class Permission implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int permissionId;
	private String permissionName;
	private int partOfKind;
	
	
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public int getPartOfKind() {
		return partOfKind;
	}
	public void setPartOfKind(int partOfKind) {
		this.partOfKind = partOfKind;
	}

	
}