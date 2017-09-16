package com.example.smarttraffic.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.smarttraffic.network.BaseSearch;

/**
 * 用户信息解析类
 * @author Administrator zhou
 *
 */
public class UserLoginSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		JSONObject object=JSON.parseObject(data);
		Message message=JSON.parseObject(object.getJSONObject("message").toJSONString(),Message.class);
		
		if(message.getStatus()==1)
			return JSON.parseObject(object.getJSONObject("result").toJSONString(),User.class);
		
		return message;
	}
}
