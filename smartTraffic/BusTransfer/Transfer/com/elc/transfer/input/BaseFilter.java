package com.elc.transfer.input;

import com.elc.transfer.entity.Request;

/**
 * 基础过滤器
 * @author Administrator
 *
 */
public class BaseFilter implements IInputFilter{

	IInputFilter nextFilter;
	
	@Override
	public boolean doFilter(Request r) {

		return true;
	}

	@Override
	public void setNext(IInputFilter filter) {
		nextFilter=filter;
	}

	@Override
	public IInputFilter getNext() {
		// TODO Auto-generated method stub
		return nextFilter.getNext();
	}

}
