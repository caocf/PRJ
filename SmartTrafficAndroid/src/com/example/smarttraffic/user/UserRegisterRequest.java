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
public class UserRegisterRequest extends BaseRequest
{
	String phone;
	
	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}


	@Override
	public PostParams CreatePostParams()
	{
		PostParams resultParams=new PostParams();
		
		resultParams.setUrl(HttpUrlRequestAddress.USER_REGISTER_URL);
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("user.phone", phone);
		resultParams.setParams(paramMap);
		
		return resultParams;
	}
}
