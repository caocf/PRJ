package com.elc.transfer.input;

import com.elc.transfer.entity.Request;

/**
 * 请求数据过滤器
 * @author Administrator
 *
 */
public interface IInputFilter {

	/**
	 * 请求内容过滤
	 * @param r 用户请求
	 * @return 是否接接受请求
	 */
	public boolean doFilter(Request r);
	
	/**
	 * 设置下一个过滤器
	 * @param filter 下一个过滤器
	 */
	public void setNext(IInputFilter filter);
	
	
	/**
	 * 获取下一个过滤器
	 * @return 下一个过滤器
	 */
	public IInputFilter getNext();
}
