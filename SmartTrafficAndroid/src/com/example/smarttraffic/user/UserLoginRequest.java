package com.example.smarttraffic.user;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * 用户登录请求类
 * @author Administrator zhou
 *
 */
public class UserLoginRequest extends BaseRequest
{
	private String phone;
	private String password;
		
	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public PostParams CreatePostParams()
	{
		PostParams params=new PostParams();
		params.setUrl(HttpUrlRequestAddress.USER_LOGIN_URL);
		
		Map<String, Object> p=new HashMap<String, Object>();
		p.put("user.phone", phone);
		p.put("user.userpassword", password);
		params.setParams(p);
			
		return params;
	}
}
