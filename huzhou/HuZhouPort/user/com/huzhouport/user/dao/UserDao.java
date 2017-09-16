package com.huzhouport.user.dao;

import java.util.List;

import com.huzhouport.user.model.User;


public interface UserDao  {

	//查询User用户
	public List<?> searchUserInfo(User user,String condition,String sequence,int startSet, int maxSet) throws Exception;
	
	//增加
	public String addUserInfo(User user) throws Exception;
	
	//修改
	public String updateUserInfo(User user) throws Exception;
	
	//全部用户
	public List<User> AllUser() throws Exception;
	
	//删除
	public String deleteUserInfo(User user) throws Exception;

	 //检验用户名
	public List<User> queryByLoginName(User user) throws Exception;
	
	//查询条数
	public int countPageUserInfo(User user, String condition)throws Exception;
	
	// 用户通过ID
	public List<?> showUserById(User user)throws Exception;
	
	//查询全部
	public List<?> showPostInfo() throws Exception;
	
	//显示状态
	public List<?> showStatusInfo() throws Exception;
	
	//检查密码
	public List<User> CheckPassword(User user)throws Exception;
	//修改密码
	public String ChangePassword(User user)throws Exception;

	//编辑验证
	public List<User> FindUpdateUser(User user) throws Exception; 
	
	public List<?> AllUserAndDepartment()throws Exception;
	
	public List<?> AllUserAndDepartmentTel(User user)throws Exception;
	
	public List<?> AllUserAndDepartmentUsername(User user)throws Exception;
	
	public List<?> AllUserAndDepartmentById(User user)throws Exception;	
	
	//通过用户模糊搜索姓名
	public List<User> FindByName(User user) throws Exception;
	//通过部门
	public List<?> UserListByDepartment(User user) throws Exception;
	
	public List<User> showUserByNumId(int id)throws Exception;
	
	public List<User> showUserByNumOrder(int order)throws Exception;
}
