package com.elc.transfer.output;

import com.elc.transfer.entity.Reply;

/**
 * 
 * @author Administrator
 *
 */
public interface IOutputFilter {

	/**
	 * 
	 * @param reply
	 */
	public void doFilter(Reply reply);
	
	/**
	 * 
	 * @param filter
	 */
	public void setNext(IOutputFilter filter);
	
	/**
	 * 
	 * @return
	 */
	public IOutputFilter getNext();
}
