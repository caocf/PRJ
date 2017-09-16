package com.example.smarttraffic.network;

/**
 * 网络请求接口
 * 主要用于生成请求地址
 * 包括Get/post/webservice请求
 * 请求方法优先级顺序：GET<POST<WEBSERVICE
 * @author Administrator zhou
 *
 */
public interface Request 
{
	/**
	 * 请求参数检查，此方法会在访问前调用，如检查失败将不会继续访问
	 * @return 参数是否合法
	 */
	public boolean paramsCheck();
	

	/**
	 * 创建请求地址，包括请求参数（Get方法）
	 * @return 返回url地址
	 */
	public String CreateUrl();
	

	/**
	 * 创建post请求参数
	 * @return 返回post参数
	 */
	public PostParams CreatePostParams();
	
	/**
	 * 创建webservice请求参数
	 * @return 返回webservice请求参数
	 */
	public WebServiceParams createWebServiceParams();
}
