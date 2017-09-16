package com.example.smarttraffic.user;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * 用户注册请求类
 * @author Administrator zhou
 *
 */
public class UserRegisterPasswordRequest extends BaseRequest
{
	String phone;
	String username;
	String password;
	String passwordAgain;
	String email;
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}	
	public String getPasswordAgain()
	{
		return passwordAgain;
	}
	public void setPasswordAgain(String passwordAgain)
	{
		this.passwordAgain = passwordAgain;
	}
	
	@Override
	public PostParams CreatePostParams()
	{
		
		PostParams postParams=new PostParams();
		postParams.setUrl(HttpUrlRequestAddress.USER_SAVE_USER_INFO_URL);
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("user.phone", phone);
		param.put("user.username", username);
		param.put("user.userpassword", password);
		param.put("user.email",email);
		postParams.setParams(param);
		
		return postParams;
	}
}
