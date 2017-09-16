package com.huzhouport.role.model;

import java.util.List;

public class Role implements java.io.Serializable {
   private static final long serialVersionUID = 1L;

	private int roleId;
	private String roleName;
    private List<RolePermission> rpList;
    private String listPermission;

	public String getListPermission() {
		return listPermission;
	}
	public void setListPermission(String listPermission) {
		this.listPermission = listPermission;
	}
	public List<RolePermission> getRpList() {
		return rpList;
	}
	public void setRpList(List<RolePermission> rpList) {
		this.rpList = rpList;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}