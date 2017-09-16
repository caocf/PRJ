package com.example.smarttraffic.network;

import java.util.Map;

/**
 * post请求参数类
 * @author Administrator zhou
 *
 */
public class PostParams
{
	private String url;
	private Map<String, Object> params;
	
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public Map<String, Object> getParams()
	{
		return params;
	}
	public void setParams(Map<String, Object> params)
	{
		this.params = params;
	}
}
