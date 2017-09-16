package com.huzhouport.role.service;

import java.util.List;

import com.huzhouport.role.model.Permission;
import com.huzhouport.role.model.PermissionKind;
import com.huzhouport.role.model.Role;
import com.huzhouport.role.model.RolePermission;


public interface RoleService {
	 public List<Role> showRoleList(Role role) throws Exception;
	 public List<Permission> showPermissionList(Permission permission) throws Exception;
	 public List<PermissionKind> showPermissionKindList() throws Exception;
	 public List<RolePermission> searchRolePermissionByRoleId(int roleId) throws Exception;
	 public String addRole(Role role) throws Exception;
	 public List<?> deleteRole(Role role) throws Exception;
	 public String updateRole(Role role) throws Exception;
	// 根据用户角色获取相关信息
	 public List<?> GetRoleInforBySession(int permission,String roleId,String userId) throws Exception; 
}
