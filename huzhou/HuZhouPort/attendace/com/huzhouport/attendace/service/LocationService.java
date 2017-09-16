package com.huzhouport.attendace.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.common.service.BaseService;

public interface LocationService extends BaseService<Location>{
	
	//查询信息
	public List<?> searchLocationInfo(Location location,int pageNo, int pageSize) throws Exception;
	
	//查询条数
	public Map<String,Integer> countPageLocationInfo(Location location,int pageSize)throws Exception;
}
