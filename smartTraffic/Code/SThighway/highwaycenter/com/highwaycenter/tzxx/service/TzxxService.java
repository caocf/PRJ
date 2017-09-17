package com.highwaycenter.tzxx.service;

import com.common.action.BaseResult;


public interface TzxxService {
	public BaseResult selectTzxxList(Integer  page,Integer rows,String columnId,String selectvalue); 
	
	public BaseResult selectTzxxXq(String main_id);
	
	public BaseResult selectTzxxColumnList();

}
