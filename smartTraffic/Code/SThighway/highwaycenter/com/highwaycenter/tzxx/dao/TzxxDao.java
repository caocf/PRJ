package com.highwaycenter.tzxx.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.tzxx.model.HTzTzxxb;

public interface TzxxDao {
	public BaseQueryRecords selectTzxxList(Integer  page,Integer rows,String columnId,String selectvalue); 
	
	public HTzTzxxb selectTzxxXq(String main_id);
	
	public List<Object[]> selectProperty (String key1,String key2);

}
