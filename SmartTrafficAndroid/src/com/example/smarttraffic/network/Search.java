package com.example.smarttraffic.network;

/**
 * 网络请求及结果处理接口
 * 包括Get/post/webservice请求及处理
 * 处理方法优先级顺序：GET<POST<WEBSERVICE
 * @author Administrator zhou
 *
 */
public interface Search 
{
	/**
	 * 根据请求获取数据，然后解析返回结果,实际包括（searchHttp/searchPost/searchWebService其中之一）+（parse/parseWebService）
	 * @param request 请求参数
	 * @return 解析后结果
	 */
	public Object search(Request request);
	
	/**
	 * 返回搜索类别
	 * 不同搜索类别有不同处理流程（本项目中只有一个默认流程）
	 * @return 搜索类别 
	 */
	public int getSearchKind();
	
	/**
	 * 根据url获取内容，并将内容处理成字符串
	 * @param url url地址
	 * @return 返回内容字符串
	 */
	public String searchHttp(String url);
	
	/**
	 * post请求
	 * @param params post请求参数
	 * @return 内容字符串
	 */
	public String searchPost(PostParams params);
	
	/**
	 * webservice请求
	 * @param params webservice参数
	 * @return SOAP对象
	 */
	public Object searchWebService(WebServiceParams params);
	
	/**
	 * 处理字符串内容结果
	 * @param data 请求返回的字符串内容
	 * @return 解析完成后的内容对象
	 */
	public Object parse(String data);
	
	/**
	 * 处理webservice内容结果
	 * @param object webservice请求返回的内容
	 * @return  解析完成后的内容对象
	 */
	public Object parseWebService(Object object);
	
	/**
	 * 对内容进行排序（本项目中大部分返回结果是list类型）
	 * @param data 解析后的数据对象
	 * @return 排序后的数据对象 
	 */
	public Object order(Object data);

}
