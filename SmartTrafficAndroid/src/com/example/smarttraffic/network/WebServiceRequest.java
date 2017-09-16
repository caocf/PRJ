package com.example.smarttraffic.network;

import java.util.Map;

/**
 * webservice请求类
 * @author Administrator zhou
 *
 */
public class WebServiceRequest extends BaseRequest
{
	
	private String Url;
	private String nameSpace;
	private String method;
	private Map<String, Object> params;
	
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	@Override
	public WebServiceParams createWebServiceParams() 
	{
		WebServiceParams result=new WebServiceParams();
		
		result.setUrl(getUrl());
		result.setNameSpace(getNameSpace());
		result.setMethod(getMethod());
		result.setParams(getParams());
		
		return result;
	}
}
