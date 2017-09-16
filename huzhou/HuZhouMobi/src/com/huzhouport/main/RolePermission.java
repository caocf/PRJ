package com.huzhouport.main;


public class RolePermission implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

	private int permissionId;
	private int status;
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}