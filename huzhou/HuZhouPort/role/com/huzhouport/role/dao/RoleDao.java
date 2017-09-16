package com.huzhouport.role.dao;

import java.util.List;

import com.huzhouport.organization.model.Department;
import com.huzhouport.role.model.Permission;
import com.huzhouport.role.model.PermissionKind;
import com.huzhouport.role.model.Role;
import com.huzhouport.role.model.RolePermission;

public interface RoleDao {
	//显示角色
	public List<Role> showRoleList(Role role) throws Exception ;

	public String deleteRole(Role role) throws Exception;

	public String updateRole(Role role) throws Exception ;

	public List<Role> checkRoleName(Role role) throws Exception;

	public List<?> checkDeleteRole(Role role)throws Exception;
	
	public List<?> checkUpdateRoleName(Role role)throws Exception;
	 //显示权限类型列表
	public List<PermissionKind> showPermissionKindList() throws Exception;
	 //显示权限列表
	public List<Permission> showPermissionList(Permission permssion) throws Exception;
	 //统计权限个数
	public List<Permission> countPermissionList() throws Exception;
	//显示权限类型列表
	public List<RolePermission> searchRolePermissionByRoleId(int roleId) throws Exception;
	//保存角色和它的权限
	public String addRole(Role role)throws Exception;
	//删除角色权限
	public String deleteRolePermission(int roleId)throws Exception;

    public String addRolePermissionInfo(RolePermission rolePermission)throws Exception;
	
	//修改角色权限
	public String updateRolePermission(RolePermission rolePermission)throws Exception;
	//通过权限和角色查找
	public List<RolePermission> FindStatusByRole(int permission,String roleId) throws Exception;
	//通过权限和角色查找
	public List<Department> FindDepartmentByUserId(String userId) throws Exception;
	
}
