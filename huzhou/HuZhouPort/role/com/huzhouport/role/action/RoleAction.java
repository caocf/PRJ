package com.huzhouport.role.action;

import java.util.List;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.role.model.Permission;
import com.huzhouport.role.model.PermissionKind;
import com.huzhouport.role.model.Role;
import com.huzhouport.role.model.RolePermission;
import com.huzhouport.role.service.RoleService;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class RoleAction extends BaseAction implements ModelDriven<Role> {

	private Role role = new Role();
	private Permission permission = new Permission();
	private PermissionKind permissionKind = new PermissionKind();
	private RolePermission rolePermission = new RolePermission();
	private List<?> list;
	private RoleService roleService;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Role getModel() {

		return role;
	}

	public Permission getModel1() {

		return permission;
	}

	public PermissionKind getModel11() {

		return permissionKind;
	}

	public RolePermission getModel111() {

		return rolePermission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermissionKind(PermissionKind permissionKind) {
		this.permissionKind = permissionKind;
	}

	public PermissionKind getPermissionKind() {
		return permissionKind;
	}

	public void setRolePermission(RolePermission rolePermission) {
		this.rolePermission = rolePermission;
	}

	public RolePermission getRolePermission() {
		return rolePermission;
	}

	// 显示角色列表
	public String showRoleList() throws Exception {
		try {

			list = this.roleService.showRoleList(role);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 显示权限列表
	public String showPermissionList() throws Exception {
		try {
			list = this.roleService.showPermissionList(permission);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 显示权限类型列表
	public String showPermissionKindList() throws Exception {
		try {
			list = this.roleService.showPermissionKindList();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 按角色ID显示权限
	public String searchRolePermissionByRoleId() throws Exception {
		try {
			//pc获取权限id
			int roleId = role.getRoleId();
			if (roleId == 0 && session.get("roleId") != null) {
				roleId = Integer.parseInt(session.get("roleId").toString());
				role.setRoleId(roleId);
			}
			list = this.roleService.searchRolePermissionByRoleId(role
					.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 添加角色
	public String addRole() throws Exception {
		try {
			this.roleService.addRole(role);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return SUCCESS;
	}

	// 删除角色
	public String deleteRole() throws Exception {
		try {
			list = this.roleService.deleteRole(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 修改角色
	public String updateRole() throws Exception {
		try {
			this.roleService.updateRole(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 根据用户角色获取相关信息
	public String GetRoleInforBySession() throws Exception {
		try {
			list=this.roleService.GetRoleInforBySession(permission.getPermissionId(),session.get("roleId").toString(),session.get("userId").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
