package com.huzhouport.role.model;


public class RolePermission implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private int permissionId;
	private int roleId;
	private int status;//是否有权限（0或者1）

	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
}