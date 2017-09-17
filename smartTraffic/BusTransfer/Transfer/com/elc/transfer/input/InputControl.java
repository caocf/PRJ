package com.elc.transfer.input;

import com.elc.transfer.entity.Request;

/**
 * 输入内容控制器
 * @author Administrator
 *
 */
public class InputControl {
	
	IInputFilter firstFilter;
	
	/**
	 * 
	 */
	public void initFilter()
	{
		
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public boolean chains(Request request)
	{
		if(firstFilter==null)
			return true;
		
		return firstFilter.doFilter(request);
	}
}
