package com.highwaycenter.common.dao;

import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.highwaycenter.bean.selectListBean;

public interface SearchDao {
	public List<Object> selectKey(String tableName,String keyname,String valuename);
	
	public List<Object> selectSingleKey(String tablename,String keyname);
	
	public BaseQueryRecords selectByHql(String hql,int page,int rows);

}
