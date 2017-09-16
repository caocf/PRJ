package com.huzhouport.leaveandovertime.service;



import java.util.List;

import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApproval;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApprovalbean;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKind;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;
import com.huzhouport.log.model.PageModel;





public interface LeaveAndOvertimeServer {
	
	
	
	
	
	
	
	
	
	
	public PageModel<LeaveOrOtKindbean> findByScrollServer(int currentPage,int maxPage ,String action); //请假加班显示
	public PageModel<LeaveOrOtKindbean> findByScrollServer1(int currentPage,int maxPage ,String action,String userid); //待审批显示
	public PageModel<LeaveOrOtKindbean> findByScrollServer2(int currentPage,int maxPage ,String action); //加班显示
	public PageModel<LeaveOrOtKindbean> findByScrollServer3(int currentPage,int maxPage ,String action); //请假显示
	public PageModel<LeaveOrOtKindbean> findByScrollServer5(int currentPage,int maxPage ,String action); //请假显示
	public List findByUsername(String content); //通过名字 找出userid //模糊
	public List findByUserName(String resultname); //通过名字 找出userid 
	public PageModel<LeaveOrOtKindbean> findByScrollServer4(int currentPage,int maxPage ,String action,String content); //查找
    public LeaveOrOtKindbean findByLeaveOrOtKindbean(int leaveOrOtID1); //显示具体审批内容
    public List<LeaveOrOt> findByLeaveOrOt(int leaveOrOtID,int userid,int i); //用户是第几个审批人
    public void updateLeaveOrOt(int leaveOrOtID,int ApprovalResult1,String ApprovalOpinion1,int i); //修改审批意见
    public void updateLeaveOrOt1(int leaveOrOtID,int i); //修改状态
    public LeaveOrOtKind findByKindID(String kindName);  //通过请假类别 获得他的id
    public void saveLeaveOrOt(LeaveOrOt leaveOrOt); //保存请假 
    public List<LeaveOrOtKindbean> findByuserid1(String userid); //请假显示自己的
    public List<LeaveOrOtKindbean> findByuserid2(String userid); //加班显示自己的
    public List<LeaveOrOtKindbean> findByuserid3(); //显示全部的
    public List<LeaveOrOtKind> findByKindName(String kindType); //查找请假类型
    public List<LeaveOrOtKindbean> leaveAndOvertimefinish(String userid); //请假显示自己的审批过的
    public List<LeaveOrOtKindbean> leaveAndOvertimeunfinish(String userid); //请假显示自己的要审批的
    public List<LeaveOrOtKindbean> leaveAndOvertiemALLbymy(String userid); //显示自己申请的
    public List<LeaveOrOtKindbean> selectLeaveAndOvertimefinish(String userid,String content); //请假显示自己的审批过的 查找
    public List<LeaveOrOtKindbean> selectLeaveAndOvertimeunfinish(String userid,String content); //请假显示自己的要审批的 查找
    public List<LeaveOrOtKindbean> selectleaveAndOvertiemALLbymy(String userid,String content); //查找自己申请的
    public List<LeaveOrOtKindbean> selectLeaveAndOvertiemALL(String content); //查找全部的
    public List<LeaveOrOtApprovalbean> leaveOrOtApproval(String userid);
    public void saveLeaveOrOtApproval (LeaveOrOtApproval approval);
    public void updateLeaveOrOtApproval (LeaveOrOtApproval approval);
  
}
