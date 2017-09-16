package com.huzhouport.attendace.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Location;
import com.huzhouport.attendace.model.Sign;
import com.huzhouport.common.service.BaseService;

public interface SignService extends BaseService<Sign>{
	//增加
	public String addSignInfo(Sign sign,Location location) throws Exception;
	
	//查询信息
	public List<?> searchSignInfo(Sign sign,int pageNo, int pageSize) throws Exception;
	
	//查询条数
	public Map<String,Integer> countPageSignInfo(Sign sign,int pageSize)throws Exception;
	
	//查询Id信息
	public List<?> seeSignInfoID(Sign sign) throws Exception;

}
