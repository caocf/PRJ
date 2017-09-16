package com.huzhouport.role.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.organization.model.Department;
import com.huzhouport.role.dao.RoleDao;
import com.huzhouport.role.model.Permission;
import com.huzhouport.role.model.PermissionKind;
import com.huzhouport.role.model.Role;
import com.huzhouport.role.model.RolePermission;
import com.huzhouport.user.model.Post;
import com.huzhouport.user.model.User;

public class RoleDaoImpl implements RoleDao {
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 角色列表
	@SuppressWarnings("unchecked")
	public List<Role> showRoleList(Role role) throws Exception {
		String hql = "from Role r";
		return this.hibernateTemplate.find(hql);
	}

	// 权限列表
	@SuppressWarnings("unchecked")
	public List<Permission> showPermissionList(Permission permission)
			throws Exception {
		String hql = "from Permission p where partOfKind="
				+ permission.getPartOfKind();
		return this.hibernateTemplate.find(hql);
	}
	 //统计权限个数
	@SuppressWarnings("unchecked")
	public List<Permission> countPermissionList() throws Exception{
		String hql = "from Permission ";
	    return this.hibernateTemplate.find(hql);
	}
	// 权限类型列表
	@SuppressWarnings("unchecked")
	public List<PermissionKind> showPermissionKindList() throws Exception {
		String hql = "from PermissionKind";
		return this.hibernateTemplate.find(hql);
	}

	// 按角色ID查找
	@SuppressWarnings("unchecked")
	public List<RolePermission> searchRolePermissionByRoleId(int roleId) throws Exception {
		String hql = "from RolePermission as rp,Role as r where rp.roleId=r.roleId and r.roleId=" + roleId;
		return this.hibernateTemplate.find(hql);
	}

	// 新增角色
	public String addRole(Role role) throws Exception {
		this.hibernateTemplate.save(role);
		return null;
	}

	public String addRolePermissionInfo(RolePermission rolePermission)
			throws Exception {
		this.hibernateTemplate.save(rolePermission);
		return null;
	}

	// 检查要删除的角色
	@SuppressWarnings("unchecked")
	public List<?> checkDeleteRole(Role role) throws Exception {
		String hql1 = "from User as u where u.partOfRole=" + role.getRoleId();
		String hql2 = "from Post as p where p.defaultRole=" + role.getRoleId();

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<User> list1 = new ArrayList<User>();
		list1 = this.hibernateTemplate.find(hql1);
		for (int i = 0; i < list1.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("Name", list1.get(i).getName());
			list.add(map);
		}
		List<Post> list2 = new ArrayList<Post>();
		list2 = this.hibernateTemplate.find(hql2);
		for (int i = 0; i < list2.size(); i++) {
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("Name", list2.get(i).getPostName());
			list.add(map2);
		}

		return list;
	}

	// 删除权限
	public String deleteRole(Role role) throws Exception {
		this.hibernateTemplate.delete(role);
		return null;
	}

	// 删除角色权限
	public String deleteRolePermission(int roleId)
			throws Exception {
		String hql="delete from RolePermission rp where rp.roleId="+roleId;
		this.hibernateTemplate.bulkUpdate(hql);
		return null;
	}

	// 修改角色
	public String updateRole(Role role) throws Exception {
		this.hibernateTemplate.update(role);
		return null;
	}

	// 修改角色权限
	public String updateRolePermission(RolePermission rp) throws Exception {
		this.hibernateTemplate.saveOrUpdate(rp);
		return null;
	}

	// 检查角色名
	@SuppressWarnings("unchecked")
	public List<Role> checkRoleName(Role role) throws Exception {
		String hql = "from Role r where r.roleName='" + role.getRoleName() + "' ";
		List<Role> list = new ArrayList<Role>();
		list = this.hibernateTemplate.find(hql);
		return list;
	}

	// 检查要修改的角色名
	@SuppressWarnings("unchecked")
	public List<Role> checkUpdateRoleName(Role role) throws Exception {
		String hql = "";
		hql = "from Role r where r.roleName='" + role.getRoleName()
				+ "' and r.roleId!=" + role.getRoleId();
		return this.hibernateTemplate.find(hql);
	}
	//通过权限和角色查找
	@SuppressWarnings("unchecked")
	public List<RolePermission> FindStatusByRole(int permission,String roleId) throws Exception {
		String hql = "from RolePermission as rp where rp.roleId="+roleId+" and rp.permissionId=" + permission;
		return this.hibernateTemplate.find(hql);
	}
	//通过权限和角色查找
	@SuppressWarnings("unchecked")
	public List<Department> FindDepartmentByUserId(String userId) throws Exception{
		String hql="select d from Department d,User u where d.departmentId=u.partOfDepartment and u.userId="+userId;
		return this.hibernateTemplate.find(hql);
	}
}
