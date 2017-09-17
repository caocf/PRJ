package com.sts.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.sts.user.model.AppUser;

public class AppUserDao
{
	HibernateTemplate hibernateTemplate;
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public List<AppUser> queryUserByName(String phone) {
		
		String hql = "from AppUser u where u.phone='" + phone + "'";

		return (List<AppUser>) hibernateTemplate.find(hql);
	}
	
	public List<AppUser> queryUser(String phone,String password)
	{
		String hql = "from AppUser u where u.phone='" + phone + "' and u.userpassword='"+password+"'";

		return (List<AppUser>) hibernateTemplate.find(hql);
	}
	
	public void saveUser(AppUser user)
	{
		hibernateTemplate.save(user);
	}
	

	public void updateUser(AppUser u)
	{
		hibernateTemplate.update(u);
	}
}
