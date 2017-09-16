package com.huzhouport.leaveandovertime.dao;



import java.util.List;

import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApproval;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApprovalbean;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKind;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;
import com.huzhouport.log.model.PageModel;





public interface Dao {
	
	
	
	
	

//	public List<Userbean> findAllUser(String hql);
	
	
	
	public PageModel findByPageScroll(String hql,int firstPage,int maxPage,String action);  //请假加班分页
	public List findByUsername(String hql);
	public LeaveOrOtKindbean findByLeaveOrOtKindbean(String hql);
	public List<LeaveOrOt> findByLeaveOrOt(String hql);
	public void update(String hql);
	public LeaveOrOtKind findByKindID(String hql);
	public void saveLeaveOrOt(LeaveOrOt leaveOrOt);
	public List<LeaveOrOtKindbean> findByuserid1(String hql);
	public List<LeaveOrOtKindbean> findByuserid3(String hql);
	 public List<LeaveOrOtKind> findByKindName(String hql);
	 
	 public List<LeaveOrOtKindbean> selectByuserid1(String hql);
	 
	 public List<LeaveOrOtApprovalbean> leaveOrOtApproval(String hql);
	 public void saveLeaveOrOtApproval (LeaveOrOtApproval approval);
//	
	
}
