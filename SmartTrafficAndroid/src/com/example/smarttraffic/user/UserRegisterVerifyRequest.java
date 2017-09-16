package com.example.smarttraffic.user;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * 用户验证请求类
 * @author Administrator zhou
 *
 */
public class UserRegisterVerifyRequest extends BaseRequest
{
	String phone;
	String code;
	
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	
	@Override
	public PostParams CreatePostParams()
	{
		PostParams postParams=new PostParams();
		postParams.setUrl(HttpUrlRequestAddress.USER_REGISTER_VERIFY_URL);
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("phone", phone);
		param.put("code", code);
		postParams.setParams(param);
		
		return postParams;
		
	}
}
