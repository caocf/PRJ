package com.elc.transfer.output;


import com.elc.transfer.entity.Reply;

/**
 * 输出结果控制器
 * @author Administrator
 *
 */
public class OutputControl {

	IOutputFilter firstFilter;
	
	public void initFilter()
	{
		
	}
	
	
	/**
	 * 
	 * @param reply
	 */
	public void chains(Reply reply)
	{
		if(firstFilter!=null)
			firstFilter.doFilter(reply);
	}
}
