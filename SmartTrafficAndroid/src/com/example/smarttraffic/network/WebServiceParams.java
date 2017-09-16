package com.example.smarttraffic.network;

import java.util.Map;

/**
 * webservice请求参数类
 * @author Administrator zhou
 *
 */
public class WebServiceParams 
{
	private String url;
	private String nameSpace;
	private String method;
	Map<String, Object> params;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	
	
}
