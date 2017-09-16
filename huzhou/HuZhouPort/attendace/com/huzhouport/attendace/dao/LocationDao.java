package com.huzhouport.attendace.dao;

import java.util.List;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.dao.BaseDao;

public interface LocationDao  extends BaseDao<Location>{
	
	//查询条数
	public int countPageLocationInfo(Location location, String condition)throws Exception;
	
	//查询信息
	public List<?> searchLocationInfo(Location location,String condition,String sequence,int startSet, int maxSet) throws Exception;
	
}
