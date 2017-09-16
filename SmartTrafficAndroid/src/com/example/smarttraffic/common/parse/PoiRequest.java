package com.example.smarttraffic.common.parse;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * poi请求类
 * @author Administrator zwc
 *
 */
public class PoiRequest extends BaseRequest
{ 
	public PoiRequest(String addr)
	{
		setAddress(addr);
	}
	private String address;
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	@Override
	public PostParams CreatePostParams()
	{
		PostParams param = new PostParams();
		param.setUrl(HttpUrlRequestAddress.SMART_BUS_CIRCLE_BY_NAME_URL);

		Map<String, Object> p = new HashMap<String, Object>();

		if(address!=null && !address.equals(""))
			p.put("name", address);
		param.setParams(p);

		return param;
	}
}
