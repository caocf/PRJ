package com.huzhouport.leaveandovertime.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.leaveandovertime.model.LeaveOrOt;

public interface AttedanceService {
	//根据权限显示请加加班的列表并分页
	public Map<String,Integer> CountAttedanceByPermission(LeaveOrOt leaveOrOt,int pageSize) throws Exception;
	public List<?> SearchAttedanceByPermission(LeaveOrOt leaveOrOt,int pageNo,int pageSize) throws Exception;
	//自己
	public Map<String,Integer> CountAttedanceByMy(LeaveOrOt leaveOrOt,int pageSize) throws Exception;
	public List<?> SearchAttedanceByMy(LeaveOrOt leaveOrOt,int pageNo,int pageSize) throws Exception;
	//APP
	public List<?> FindAttedanceByPermission_APP(LeaveOrOt leaveOrOt) throws Exception;

}
