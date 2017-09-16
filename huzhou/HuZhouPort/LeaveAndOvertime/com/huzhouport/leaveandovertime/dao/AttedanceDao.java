package com.huzhouport.leaveandovertime.dao;

import java.util.List;

import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.role.model.RolePermission;

public interface AttedanceDao {
	//通过用户Id查权限
	public List<RolePermission> FindPermissionByUserId(int userId,int permissionId) throws Exception;
	public int CountAttedanceByPermission(LeaveOrOt leaveOrOt,int status) throws Exception;
	// 查询列表
	public List<?> SearchAttedanceByPermission(LeaveOrOt leaveOrOt,int roleStatus,int startSet, int maxSet) throws Exception;
	public int CountAttedanceByMy(LeaveOrOt leaveOrOt) throws Exception;
	// 查询列表
	public List<?> SearchAttedanceByMy(LeaveOrOt leaveOrOt,int startSet, int maxSet) throws Exception;
	//APP
	public List<?> FindAttedanceByPermission_APP(LeaveOrOt leaveOrOt,int roleStatus) throws Exception;
}
