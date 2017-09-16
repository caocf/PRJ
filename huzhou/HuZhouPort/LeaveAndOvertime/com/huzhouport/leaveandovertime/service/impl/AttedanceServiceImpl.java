package com.huzhouport.leaveandovertime.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.leaveandovertime.dao.AttedanceDao;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.service.AttedanceService;
import com.huzhouport.role.model.RolePermission;

public class AttedanceServiceImpl extends BaseServiceImpl<LeaveOrOt> implements
		AttedanceService {
	private AttedanceDao attedanceDao;

	public void setAttedanceDao(AttedanceDao attedanceDao) {
		this.attedanceDao = attedanceDao;
	}

	public Map<String, Integer> CountAttedanceByPermission(LeaveOrOt leaveOrOt,
			int pageSize) throws Exception {
		List<RolePermission> rp=this.attedanceDao.FindPermissionByUserId(leaveOrOt.getLeaveOrOtUser(), GlobalVar.PERMISSION_LEAVE);
		if(rp.size()>0){
			int status=rp.get(0).getStatus();
			Map<String,Integer> map=new HashMap<String, Integer>();
			int total = this.attedanceDao.CountAttedanceByPermission(leaveOrOt,status);
			map.put("allTotal", total);
			int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
			map.put("pages", pages);
			return map;
		}
		return null;
	}

	public List<?> SearchAttedanceByPermission(LeaveOrOt leaveOrOt, int pageNo,
			int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		List<RolePermission> rp=this.attedanceDao.FindPermissionByUserId(leaveOrOt.getLeaveOrOtUser(), GlobalVar.PERMISSION_LEAVE);
		if(rp.size()>0){
			int status=rp.get(0).getStatus();
			return this.attedanceDao.SearchAttedanceByPermission(leaveOrOt,status,beginIndex,pageSize);
		}
		return null;
		
	}
	public Map<String, Integer> CountAttedanceByMy(LeaveOrOt leaveOrOt,
			int pageSize) throws Exception {
			Map<String,Integer> map=new HashMap<String, Integer>();
			int total = this.attedanceDao.CountAttedanceByMy(leaveOrOt);
			map.put("allTotal", total);
			int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
			map.put("pages", pages);
			return map;
	}

	public List<?> SearchAttedanceByMy(LeaveOrOt leaveOrOt, int pageNo,
			int pageSize) throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		
			return this.attedanceDao.SearchAttedanceByMy(leaveOrOt,beginIndex,pageSize);
	}
	//APP
	public List<?> FindAttedanceByPermission_APP(LeaveOrOt leaveOrOt) throws Exception{
		List<RolePermission> rp=this.attedanceDao.FindPermissionByUserId(leaveOrOt.getLeaveOrOtUser(), GlobalVar.PERMISSION_LEAVE);
		if(rp.size()>0){
			int status=rp.get(0).getStatus();
			return this.attedanceDao.FindAttedanceByPermission_APP(leaveOrOt,status);
		}
		return null;
		
	
	}
}
