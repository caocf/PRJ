package com.example.smarttraffic.network;

/**
 * 基本请求实现类
 * 所有网络请求访问类都继承此类
 * @author Administrator zhou
 *
 */
public class BaseRequest implements Request
{
	@Override
	public boolean paramsCheck()
	{
		return true;
	}
	@Override
	public String CreateUrl() {
		return null;
	}
	
	@Override
	public WebServiceParams createWebServiceParams() {
		return null;
	}

	@Override
	public PostParams CreatePostParams()
	{
		return null;
	}
}
