package com.highwaycenter.common.service;

import com.common.action.BaseResult;

public interface SerchService {
	public BaseResult advancedSearch(String type);
	
	public BaseResult searchSelect(String type,String key);

	
	public BaseResult selectListByCondition(String type, String condition,int page,int rows) ;
	
	
	public BaseResult selectItemLocation(String type,Object id,int rows);
}
