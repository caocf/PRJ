package com.elc.transfer.core;

import com.elc.transfer.entity.Reply;
import com.elc.transfer.entity.Request;

/**
 * 
 * @author Administrator
 *
 */
public interface IArithmeticCore {
	 
	/**
	 * 
	 * @param r
	 * @return
	 */
	public Reply arithmeticTransfer(Request r);
	
	public void saveResultToCache(Reply reply);
}
