package com.example.smarttraffic.user;

import java.io.Serializable;

/**
 * 用户信息类
 * @author Administrator
 *
 */
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public int userid;
	public String phone;
	public String userpassword;
	public String username;
	public String email;
	public int getUserid()
	{
		return userid;
	}
	public void setUserid(int userid)
	{
		this.userid = userid;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getUserpassword()
	{
		return userpassword;
	}
	public void setUserpassword(String userpassword)
	{
		this.userpassword = userpassword;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	
	
}
