package com.example.smarttraffic.user;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

public class ForgetPasswordModifyRequest extends BaseRequest
{
	String phone;
	String newPassword;

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}


	public String getNewPassword()
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	public PostParams CreatePostParams()
	{
		PostParams params=new PostParams();
		params.setUrl(HttpUrlRequestAddress.USER_FORGET_PASSWORD_2);
		
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("user.phone", phone);
		paramMap.put("user.userpassword", newPassword);
		
		params.setParams(paramMap);
		
		return params;
	}
}
