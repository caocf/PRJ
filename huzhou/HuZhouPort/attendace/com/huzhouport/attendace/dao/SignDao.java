package com.huzhouport.attendace.dao;

import java.util.List;
import java.util.Map;

import com.huzhouport.attendace.model.Sign;
import com.huzhouport.common.dao.BaseDao;

public interface SignDao extends BaseDao<Sign>{
	
	//查询条数
	public int countPageSignInfo(Sign sign, String condition)throws Exception;
	
	//查询信息
	public List<?> searchSignInfo(Sign sign,String condition,String sequence,int startSet, int maxSet) throws Exception;
	
	//增加
	public String addSignInfo(Sign sign) throws Exception;
	
	//按时间查询
	public Map<String,Integer> findSignByCondition(Sign sign) throws Exception;
	
	//查询当天签到数
	public List<?> seeSignInfoID(Sign sign) throws Exception;
}
