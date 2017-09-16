package com.huzhouport.user.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.user.model.User;

public interface UserService {

	// 查询User用户
	public List<?> searchUserInfo(User user, int pageNo, int pageSize)
			throws Exception;

	// 全部User
	public List<User> AllUser() throws Exception;

	// 增加
	public String addUserInfo(User user) throws Exception;

	// 修改
	public String updateUserInfo(User user) throws Exception;

	// 选择删除
	public String deleteUserInfo(User user) throws Exception;

	// 根据用户ID删除记录
	public String deleteUserById(User user) throws Exception;

	// 查询条数
	public Map<String, Integer> countPageUserInfo(User user, int pageSize)
			throws Exception;

	// 用户通过ID
	public List<?> showUserById(User user) throws Exception;

	// 显示职务
	public List<?> showPostInfo() throws Exception;

	// 显示状态
	public List<?> showStatusInfo() throws Exception;

	// 检验用户名
	public List<User> searchDataByConditions(User userinfo) throws Exception;

	// 修改密码
	public String ChangePassword(User user) throws Exception;

	// 检查密码
	public int CheckPassword(User user) throws Exception;

	// 用户和部门
	public List<?> AllUserAndDepartment() throws Exception;

	// 通过用户模糊搜索姓名
	public List<User> FindByName(User user) throws Exception;

	public List<?> AllUserAndDepartmentTel(User user) throws Exception;

	public List<?> AllUserAndDepartmentUsername(User user) throws Exception;

	public List<?> AllUserAndDepartmentById(User user) throws Exception;

	public List<User> queryByLoginName(User user) throws Exception;
	//通过部门
	public List<?> UserListByDepartment(User user) throws Exception;
	
	public List<User> showUserByNumId(int id) throws Exception;
	
	public List<User> showUserByNumOrder(int order) throws Exception;

}
