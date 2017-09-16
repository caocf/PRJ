package com.huzhouport.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.huzhouport.organization.model.Department;
import com.huzhouport.role.dao.RoleDao;
import com.huzhouport.role.model.Permission;
import com.huzhouport.role.model.PermissionKind;
import com.huzhouport.role.model.Role;
import com.huzhouport.role.model.RolePermission;
import com.huzhouport.role.service.RoleService;

public class RoleServiceImpl implements RoleService {
	private RoleDao roleDao;


	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	
	// 显示角色列表
	public List<Role> showRoleList(Role role) throws Exception {
		return this.roleDao.showRoleList(role);
	}

	public List<PermissionKind> showPermissionKindList() throws Exception {
		return this.roleDao.showPermissionKindList();
	}

	public List<Permission> showPermissionList(Permission permission)
			throws Exception {
		return this.roleDao.showPermissionList(permission);
	}
	
	// 按角色id查找
	public List<RolePermission> searchRolePermissionByRoleId(int roleId) throws Exception {
		return this.roleDao.searchRolePermissionByRoleId(roleId);
	}

	// 增加角色
	public String addRole(Role role) throws Exception {
		String returnMark = "";
		if (roleDao.checkRoleName(role).size() == 0) {
			this.roleDao.addRole(role);
			RolePermission rp ;
			String[] pId=role.getListPermission().split(";");
			for (int i = 0; i <pId.length; i++) {
				rp = new RolePermission();
				rp.setPermissionId(Integer.parseInt(pId[i].split(":")[0]));
				rp.setRoleId(role.getRoleId());
				rp.setStatus(Integer.parseInt(pId[i].split(":")[1]));
				this.roleDao.updateRolePermission(rp);
			}
		} else {
			throw new Exception("此角色名已存在");
		}
		return returnMark;
	}


	// 删除角色
	public List<?> deleteRole(Role role) throws Exception {
		// 验证用户和职位是否拥有该角色
		if (roleDao.checkDeleteRole(role).size() > 0)
			return this.roleDao.checkDeleteRole(role);
		else {
			this.roleDao.deleteRolePermission(role.getRoleId());
			this.roleDao.deleteRole(role);
			return null;
		}
	}

	// 修改角色权限
	public String updateRole(Role role) throws Exception {
		if (roleDao.checkUpdateRoleName(role).size() == 0) {
		this.roleDao.updateRole(role);
		RolePermission rp = null;
        String[] pId=role.getListPermission().split(";");
        this.roleDao.deleteRolePermission(role.getRoleId());
		for (int i = 0; i <pId.length; i++) {
			rp = new RolePermission();
			rp.setPermissionId(Integer.parseInt(pId[i].split(":")[0]));
			rp.setRoleId(role.getRoleId());
			rp.setStatus(Integer.parseInt(pId[i].split(":")[1]));
			this.roleDao.updateRolePermission(rp);
		}
		
		} else {
			throw new Exception("此角色名已存在");
		}
		return null;
	}
	// 根据用户角色获取相关信息
	 public List<?> GetRoleInforBySession(int permission,String roleId,String userId) throws Exception{
		 List<RolePermission> rp=this.roleDao.FindStatusByRole(permission, roleId);
		 List<HashMap<String, String>> list=new ArrayList<HashMap<String, String>>();
		 HashMap<String, String> map=new HashMap<String, String>();
		 if(rp.size()>0){
			switch (rp.get(0).getStatus()) {
			case 0:
				map.put("roleStatus", "0");
				break;
			case 1:
				map.put("roleStatus", "1");
				break;
			case 2:
				map.put("roleStatus", "2");
				 List<Department> dp=this.roleDao.FindDepartmentByUserId(userId);
				 map.put("departmentName", dp.get(0).getDepartmentName());
				 map.put("departmentId", dp.get(0).getDepartmentId()+"");
				break;
			case 3:
				map.put("roleStatus", "3");
				break;

			default:map.put("roleStatus", "0");
				break;
			};
			 list.add(map);
		 }
		 return list;
	 }
}
