package com.sts.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.user.model.User;

/**
 * 用户操作DAO类
 * @author Administrator
 *
 */
public class UserDao 
{
	HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 根据用户名查询用户列表
	 * @param phone 手机号
	 * @param verify 是否只查询认证成功的用户信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> queryUserByName(String phone,boolean verify) {
		
		String hql;
		
		if(verify)
			hql = "from User u where u.phone='" + phone + "' and u.verifyStatus = 1";
		else
			hql = "from User u where u.phone='" + phone + "'";
		return (List<User>) hibernateTemplate.find(hql);
	}
	
	/**
	 * 登录查询
	 * @param phone 手机号
	 * @param password 密码
	 * @param verify 是否只查询认证成功的用户信息
	 * @return
	 */
	public List<User> queryUserByName(String phone,String password,boolean verify) {
		
		String hql;
		
		if(verify)
			hql = "from User u where u.phone='" + phone + "' and u.password='"+password+"' and u.verifyStatus = 1";
		else
			hql = "from User u where u.phone='" + phone +  "' and u.password='"+password+"'";
		return (List<User>) hibernateTemplate.find(hql);
	}
	
	/**
	 * 默认只查询认证成功的用户
	 * @param phone 手机号
	 * @return
	 */
	public List<User> queryUserByName(String phone)
	{
		return queryUserByName(phone,true);
	}
	
	/**
	 * 注册用户
	 * @param user 用户信息
	 */
	public void registerUser(User user)
	{
		hibernateTemplate.save(user);
	}
	
	/**
	 * 更新用户
	 * @param u 用户信息
	 */
	public void updateUser(User u)
	{
		hibernateTemplate.update(u);
	}
	
	/**
	 * 登录验证
	 * @param phone 手机号
	 * @param password 密码
	 * @return
	 */
	public List<User> verifyLogin(String phone,String password)
	{
		String hql = "from User u where u.phone='" + phone + "' and u.verifyStatus = 1 and u.password='"+password+"'";
		
		return (List<User>) hibernateTemplate.find(hql);
	}
	
	/**
	 * 通过ID查询用户
	 * @param id
	 * @return
	 */
	public User queryUserByID(int id) {
		
		return hibernateTemplate.get(User.class, id);
	}
}
