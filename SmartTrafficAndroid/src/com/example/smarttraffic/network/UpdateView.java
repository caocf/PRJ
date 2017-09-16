package com.example.smarttraffic.network;

/**
 * 将网络数据请求解析后的结果更新到界面上
 * @author Administrator zhou
 *
 */
public interface UpdateView 
{
	/**
	 * 更新界面
	 * @param data Search类search方法返回的内容
	 */
	public void update(Object data);
}
