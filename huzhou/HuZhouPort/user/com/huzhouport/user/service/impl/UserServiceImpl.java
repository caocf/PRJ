package com.huzhouport.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.user.dao.UserDao;
import com.huzhouport.user.model.User;
import com.huzhouport.user.service.UserService;


public class UserServiceImpl extends BaseServiceImpl<User> implements UserService  {
	private UserDao userDao;
	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String addUserInfo(User user) throws Exception {
		if (userDao.queryByLoginName(user).size() == 0) {
			this.userDao.addUserInfo(user);
			} else {
				throw new Exception("user");
		}
		return null;
	}
	public List<User> queryByLoginName(User user) throws Exception {
		return this.userDao.queryByLoginName(user);
	}

	public String deleteUserInfo(User user) throws Exception {
		if(null==user.getUserIdList()){
			throw new Exception("请选择要删除的数据！");
		}
		String[] list=user.getUserIdList().split(",");
		
		for(int i=0;i<list.length;i++){
			user.setUserId(Integer.valueOf(list[i].trim()));
			this.userDao.deleteUserInfo(user);
		}
		return null;
		
	}
	
	public String deleteUserById(User user) throws Exception {
			this.userDao.deleteUserInfo(user);
		
		return null;
		
	}
//搜索或显示全部列表
	public List<?> searchUserInfo(User user,int pageNo, int pageSize) throws Exception 
	{
		int beginIndex = (pageNo - 1) * pageSize;
		return this.userDao.searchUserInfo(user,
				qcs.QCS(user.getQueryCondition()),
				qcs.Sequence(user.getQueryCondition()), beginIndex,
				pageSize);
		
	}
	public List<User> AllUser() throws Exception{
		return this.userDao.AllUser();
	}
	public String updateUserInfo(User user) throws Exception {
		if (userDao.FindUpdateUser(user).size() == 0) {	
		   this.userDao.updateUserInfo(user);
		} else {
			throw new Exception("user");
	}
	return null;
	}

	public Map<String,Integer> countPageUserInfo(User user, int pageSize) throws Exception
	{
		Map<String,Integer> map=new HashMap<String, Integer>();
		int total = this.userDao.countPageUserInfo(user,qcs.QCS(user.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}
	// 用户通过ID
	public List<?> showUserById(User user)throws Exception{
		return this.userDao.showUserById(user);
	}
	//显示职务
	public List<?> showPostInfo() throws Exception {
		return this.userDao.showPostInfo();
	}
	//显示状态
	public List<?> showStatusInfo() throws Exception {
		return this.userDao.showStatusInfo();
	}
	 //检验用户名
	public List<User> searchDataByConditions(User user)throws Exception{
	    return this.userDao.queryByLoginName(user);	
	}
	//修改密码
	public String ChangePassword(User user)throws Exception{
		return  this.userDao.ChangePassword(user);
	}
	public int CheckPassword(User user)throws Exception{
		List<User> list =this.userDao.CheckPassword(user);
		return list.size();
	}
	public List<?> AllUserAndDepartment()throws Exception{
		return this.userDao.AllUserAndDepartment();
	}

	public List<?> AllUserAndDepartmentTel(User user)throws Exception{
		return this.userDao.AllUserAndDepartmentTel(user);
	}
	public List<?> AllUserAndDepartmentUsername(User user)throws Exception{
		return this.userDao.AllUserAndDepartmentUsername(user);
	}
	public List<?> AllUserAndDepartmentById(User user)throws Exception{
		return this.userDao.AllUserAndDepartmentById(user);
	}

	//通过用户模糊搜索姓名
	public List<User> FindByName(User user) throws Exception{
		return this.userDao.FindByName(user);
	}
	//通过部门
	public List<?> UserListByDepartment(User user) throws Exception{
		return this.userDao.UserListByDepartment(user);
	}

	public List<User> showUserByNumId(int id) throws Exception 
	{
		return this.userDao.showUserByNumId(id);
	}

	public List<User> showUserByNumOrder(int order) throws Exception {
		// TODO Auto-generated method stub
		return this.userDao.showUserByNumOrder(order);
	}
}
