package com.sts.user.service;

import java.util.List;

import javax.xml.registry.infomodel.User;

import com.sts.user.dao.AppUserDao;
import com.sts.user.model.AppUser;

public class AppUserService {

	AppUserDao appUserDao;
	
	public void setAppUserDao(AppUserDao appUserDao) {
		this.appUserDao = appUserDao;
	}
	
	public boolean appPhoneIsExisted(String phone)
	{
		List<AppUser> result=this.appUserDao.queryUserByName(phone);
		
		if(result!=null && result.size()>0)
			return true;
		
		return false;
	}
	
	public boolean appAddUser(AppUser user)
	{
		try
		{
		if(!appPhoneIsExisted(user.getPhone()))
		{
			this.appUserDao.saveUser(user);
			return true;
		}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return false;
	}
	
	public AppUser appLogin(AppUser user)
	{
		List<AppUser> result=this.appUserDao.queryUser(user.getPhone(), user.getUserpassword());
		
		if(result==null || result.size()==0)
			return null;
		
		return result.get(0);
	}
	
	public AppUser queryAppUser(String phone)
	{
		List<AppUser> result=this.appUserDao.queryUserByName(phone);
		
		if(result!=null && result.size()>0)
			return result.get(0);
		
		return null;
	}
	
	public void updateUser(AppUser user)
	{
		this.appUserDao.updateUser(user);
	}
}
