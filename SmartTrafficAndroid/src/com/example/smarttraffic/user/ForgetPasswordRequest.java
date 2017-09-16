package com.example.smarttraffic.user;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * 找回密码请求类
 * @author Administrator zhou
 *
 */
public class ForgetPasswordRequest extends BaseRequest
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
		PostParams params=new PostParams();
		params.setUrl(HttpUrlRequestAddress.USER_REGISTER_URL);
		
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("user.phone", phone);
		
		params.setParams(paramMap);
		
		return params;
	}
}
