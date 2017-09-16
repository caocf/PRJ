package com.huzhouport.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.user.dao.UserDao;
import com.huzhouport.user.model.User;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
	

	// 增加
	public String addUserInfo(User user) throws Exception {
		this.hibernateTemplate.save(user);
		return null;
	}

	// 用户通过ID
	public List<?> showUserById(User user)throws Exception{
		String hql = "select u,d,de,r,s from User u,Post d,Department de,Role r,Status s where u.partOfPost=d.postId and u.partOfDepartment=de.departmentId  " +
				"and u.partOfRole=r.roleId and u.userStatus=s.statusId and u.userId="+user.getUserId();
		return this.hibernateTemplate.find(hql);
	}
	
	//查询职务
	public List<?> showPostInfo() throws Exception {
		String hql="from Post p ";
		return this.hibernateTemplate.find(hql);
	}
	//查询状态
	public List<?> showStatusInfo() throws Exception {
		String hql="from Status";
		return this.hibernateTemplate.find(hql);
	}

	// 修改
	public String updateUserInfo(User user) throws Exception {
		this.hibernateTemplate.update(user);
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<User> AllUser() throws Exception{
		String hql="from User";
		return this.hibernateTemplate.find(hql);
	}
	@SuppressWarnings("unchecked")
	public List<User> queryByLoginName(User user) throws Exception {
		String hql = "from User u where u.userName='" + user.getUserName()
				+ "'";
		return this.hibernateTemplate.find(hql);
	}
	//通过用户模糊搜索姓名
	@SuppressWarnings("unchecked")
	public List<User> FindByName(User user) throws Exception {
		String hql = "from User u where u.name like '%" + user.getName()
				+ "%'";
		return this.hibernateTemplate.find(hql);
	}
	//编辑验证
	@SuppressWarnings("unchecked")
	public List<User> FindUpdateUser(User user) throws Exception {
		String hql = "from User u where u.userName='" + user.getUserName()+"' and u.userId!="+user.getUserId();
    return this.hibernateTemplate.find(hql);
   }
	
	// 查询或显示全部用户列表
	public List<?> searchUserInfo(User user, String condition, String sequence,
			int startSet, int maxSet) throws Exception 
	{
		String hql = "select u,d,de,r,s from User u,Post d,Department de,Role r,Status s where u.partOfPost=d.postId and u.partOfDepartment=de.departmentId  and u.partOfRole=r.roleId and u.userStatus=s.statusId ";
		if(user.getPartOfDepartment()!=0)
		{
			didlist=new ArrayList<Integer>();
			didlist.add(user.getPartOfDepartment());
			FindDepartment(didlist);
			for(int i=0;i<didlist.size();i++){
				if(i==0){
					hql+=" and ( de.departmentId="+didlist.get(i);
				}
				else{
					hql += " or de.departmentId="+didlist.get(i);
				}
				
			}
			hql+=" ) ";
		}
		if (condition != "") {
			hql += "and ( " + condition + " )";
		}
		if (sequence != null) {
			hql += sequence;
		}
		List<?> list = this.queryqueryEForeignKey(user, hql, startSet, maxSet);
		return list;
	}
	//下级部门
	private List<Integer> didlist;
	public void FindDepartment(List<Integer> did)
	{
		String hql="select d.departmentId from Department d where d.partOfDepartmentId="+did.get(0);
		for(int i=1;i<did.size();i++){
			hql+=" or d.partOfDepartmentId="+did.get(i);
		}
		List<Integer> list=this.hibernateTemplate.find(hql);
		if(list!=null){
			didlist.addAll(list);
			if(list.size()>0){
				FindDepartment(list);
			}
		}
		
	}	
	//分页
	public int countPageUserInfo(User user, String condition) throws Exception {
		String hql = "select count(*) from User u,Post d,Department de,Role r,Status s where u.partOfPost=d.postId and  u.partOfDepartment=de.departmentId  and u.partOfRole=r.roleId and u.userStatus=s.statusId ";
		if(user.getPartOfDepartment()!=0){
			didlist=new ArrayList<Integer>();
			didlist.add(user.getPartOfDepartment());
			FindDepartment(didlist);
			for(int i=0;i<didlist.size();i++){
				if(i==0){
					hql+=" and ( de.departmentId="+didlist.get(i);
				}
				else{
					hql += " or de.departmentId="+didlist.get(i);
				}
				
			}
			hql+=" ) ";
		}
		if (condition != "") {
			hql += "and ( " + condition + " )";
		}
		return this.countEForeignKey(user, hql);
	}
	//检查用户密码
	@SuppressWarnings("unchecked")
	public List<User> CheckPassword(User user)throws Exception{
		String hql="from User u where u.userId="+user.getUserId()+" and u.password='"+user.getEmail()+"'";
		return this.hibernateTemplate.find(hql);
	}
	//修改密码
	public String ChangePassword(User user)throws Exception{
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> conditionMap = new HashMap<String, String>();
		map.put("password",user.getPassword());
		conditionMap.put("userId", String.valueOf(user.getUserId()));
		this.modifyByConditions(user,map,conditionMap,null);
		return null;
	}

	//修改用户状态
	public String deleteUserInfo(User user) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Integer> conditionMap = new HashMap<String, Integer>();
		map.put("userStatus",user.getUserStatus());
		conditionMap.put("userId", user.getUserId());
		this.updateByInt(user,map,conditionMap,null);
		return null;
	}
	//用户和部门
	public List<?> AllUserAndDepartment()throws Exception{
		String hql="select d,u from User as u,Department as d where u.partOfDepartment=d.departmentId order by d.departmentId asc";
		return this.hibernateTemplate.find(hql);
	}
	public List<?> AllUserAndDepartmentTel(User user)throws Exception{
		String name=user.getName();
		name=new String(name.getBytes("ISO8859-1"),"UTF-8");
		String hql="select d,u from User as u,Department as d where u.partOfDepartment=d.departmentId and (u.name like '%"+name+"%' or u.tel like '%"+name+"%') order by d.departmentId asc";
		return this.hibernateTemplate.find(hql);
	}
	public List<?> AllUserAndDepartmentUsername(User user)throws Exception{
		String name=user.getName();
		name=new String(name.getBytes("ISO8859-1"),"UTF-8");
		String hql="select d,u from User as u,Department as d where u.partOfDepartment=d.departmentId and u.name like '%"+name+"%' order by d.departmentId asc";
		return this.hibernateTemplate.find(hql);
	}
	public List<?> AllUserAndDepartmentById(User user)throws Exception{
		int id=user.getUserId();
		String hql="select d,u from User as u,Department as d where u.partOfDepartment=d.departmentId and u.userId="+id+" order by d.departmentId asc";
		return this.hibernateTemplate.find(hql);
	}
	public List<?> UserListByDepartment(User user) throws Exception{
		String hql="from User u where u.partOfDepartment="+user.getPartOfDepartment()+" and u.userStatus=1 order by u.order ASC";
		didlist=new ArrayList<Integer>();
		didlist.add(user.getPartOfDepartment());
		FindDepartment(didlist);
		for(int i=1;i<didlist.size();i++){
			hql += " or u.partOfDepartment="+didlist.get(i);	
		}
		return this.hibernateTemplate.find(hql);
	}

	public List<User> showUserByNumId(int id) throws Exception 
	{
		String hql = "select u from User u where u.userId="+id;
		return this.hibernateTemplate.find(hql);
	}

	public List<User> showUserByNumOrder(int order) throws Exception 
	{
		String hql = "select u from User u where u.order="+order;
		return this.hibernateTemplate.find(hql);
	}
}
