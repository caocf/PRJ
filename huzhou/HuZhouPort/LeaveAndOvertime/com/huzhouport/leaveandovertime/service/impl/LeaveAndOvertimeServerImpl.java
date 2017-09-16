package com.huzhouport.leaveandovertime.service.impl;



import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huzhouport.leaveandovertime.dao.Dao;
import com.huzhouport.leaveandovertime.model.LeaveOrOt;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApproval;
import com.huzhouport.leaveandovertime.model.LeaveOrOtApprovalbean;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKind;
import com.huzhouport.leaveandovertime.model.LeaveOrOtKindbean;
import com.huzhouport.leaveandovertime.service.LeaveAndOvertimeServer;


import com.huzhouport.log.model.PageModel;
import com.huzhouport.pushmsg.model.PushMsg;
import com.huzhouport.pushmsg.service.PushMsgService;









public class LeaveAndOvertimeServerImpl implements LeaveAndOvertimeServer{
	private Dao dao;
	
	private PushMsgService pushMsgService;
	
	public void setPushMsgService(PushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}
	
	
//	public Dao getDao() {
//		return dao;
//	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	
	
	


	public PageModel<LeaveOrOtKindbean> findByScrollServer(int currentPage, int maxPage,String action) { //请假加班显示

		String hql="from LeaveOrOt l,LeaveOrOtKind lk ,User u where l.leaveOrOtKind=lk.kindID and l.leaveOrOtUser=u.userId order by l.leaveOrOtDate desc";
		
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	
	public PageModel<LeaveOrOtKindbean> findByScrollServer1(int currentPage, int maxPage,String action,String userid) { //待审批显示
	
		//String hql="from LeaveOrOt l2 left join User u4  on  l2.leaveOrOtKind=u4.userId left join User u1  on  l2.approvalID1=u1.userId left join User u2  on  l2.approvalID2=u2.userId left join User u3  on  l2.approvalID3=u3.userId  inner join LeaveOrOtKind l1 on l1.kindID=l2.leaveOrOtKind";
		String hql="from LeaveOrOt l,LeaveOrOtKind lk ,User u where l.leaveOrOtKind=lk.kindID and l.leaveOrOtUser=u.userId and l.approvalResult=1 and l.approvalID1="+userid+" order by l.leaveOrOtDate desc ";		
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	
	public PageModel<LeaveOrOtKindbean> findByScrollServer2(int currentPage, int maxPage,String action) { //加班显示
		
		//String hql="from LeaveOrOt l2 left join User u4  on  l2.leaveOrOtKind=u4.userId left join User u1  on  l2.approvalID1=u1.userId left join User u2  on  l2.approvalID2=u2.userId left join User u3  on  l2.approvalID3=u3.userId  inner join LeaveOrOtKind l1 on l1.kindID=l2.leaveOrOtKind";
		String hql="from LeaveOrOt l,LeaveOrOtKind lk ,User u where l.leaveOrOtKind=lk.kindID and l.leaveOrOtUser=u.userId and lk.kindType=2 order by l.leaveOrOtDate desc";
		//System.out.println("hql="+hql); 		
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	public PageModel<LeaveOrOtKindbean> findByScrollServer3(int currentPage, int maxPage,String action) { //请假显示
		
		//String hql="from LeaveOrOt l2 left join User u4  on  l2.leaveOrOtKind=u4.userId left join User u1  on  l2.approvalID1=u1.userId left join User u2  on  l2.approvalID2=u2.userId left join User u3  on  l2.approvalID3=u3.userId  inner join LeaveOrOtKind l1 on l1.kindID=l2.leaveOrOtKind";
		String hql="from LeaveOrOt l,LeaveOrOtKind lk ,User u where l.leaveOrOtKind=lk.kindID and l.leaveOrOtUser=u.userId and lk.kindType=1 order by l.leaveOrOtDate desc ";
		//System.out.println("hql="+hql); 		
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	public PageModel<LeaveOrOtKindbean> findByScrollServer5(int currentPage, int maxPage,String action) { //请假显示
		
		//String hql="from LeaveOrOt l2 left join User u4  on  l2.leaveOrOtKind=u4.userId left join User u1  on  l2.approvalID1=u1.userId left join User u2  on  l2.approvalID2=u2.userId left join User u3  on  l2.approvalID3=u3.userId  inner join LeaveOrOtKind l1 on l1.kindID=l2.leaveOrOtKind";
		String hql="from LeaveOrOt l,LeaveOrOtKind lk ,User u where l.leaveOrOtKind=lk.kindID and l.leaveOrOtUser=u.userId and lk.kindType=3 order by l.leaveOrOtDate desc ";
		//System.out.println("hql="+hql); 		
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	public List findByUsername(String content){
		String hql="from User where name like '%"+content+"%' ";
		System.out.println("hql="+hql); 
		return this.dao.findByUsername(hql);
	}
	public List findByUserName(String resultname){//通过名字 找出userid 
	String hql="from User where name="+resultname;
	System.out.println("hql="+hql); 
	return this.dao.findByUsername(hql);
	}
	
	
	public PageModel<LeaveOrOtKindbean> findByScrollServer4(int currentPage, int maxPage,String action,String content) { //请假显示
		//String hql="from LeaveOrOt l2 left join User u4  on  l2.leaveOrOtKind=u4.userId left join User u1  on  l2.approvalID1=u1.userId left join User u2  on  l2.approvalID2=u2.userId left join User u3  on  l2.approvalID3=u3.userId  inner join LeaveOrOtKind l1 on l1.kindID=l2.leaveOrOtKind";
		String hql="from LeaveOrOt l,LeaveOrOtKind lk,User u where l.leaveOrOtKind=lk.kindID and l.leaveOrOtUser=u.userId  and (u.name like '%"+content+"%'";
		hql=hql+" or  l.lastDate like '%"+content+"%') order by l.leaveOrOtDate desc ";		
		return this.dao.findByPageScroll(hql, currentPage, maxPage ,action);
	}
	
	 public LeaveOrOtKindbean findByLeaveOrOtKindbean(int leaveOrOtID1){
		 String hql="from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID and l.leaveOrOtID="+leaveOrOtID1;
			
		 System.out.println("安卓端读取请假加班时，设置为已推送已读"+hql); 
			
			
			this.pushMsgService.readPushMsg(PushMsg.MODULERID_LEAVEANDOVERTIME, -1, leaveOrOtID1);
			
			return this.dao.findByLeaveOrOtKindbean(hql);
	 }
	 public List<LeaveOrOt> findByLeaveOrOt(int leaveOrOtID,int userid ,int i){
		 String hql="from LeaveOrOt l where l.leaveOrOtID="+leaveOrOtID+" and approvalID"+i+"="+userid;
		 
		 
		 System.out.println("hql="+hql); 
		 return this.dao.findByLeaveOrOt(hql); 
	 }
	
	 public void updateLeaveOrOt(int leaveOrOtID,int ApprovalResult1,String ApprovalOpinion1,int i){
	  Date now = new Date();
	  DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到分）
      String declareTime = d8.format(now);
		 String hql="update LeaveOrOt l set l.approvalResult"+i+"="+ApprovalResult1+ ",l.approvalOpinion"+i+"='"+ApprovalOpinion1+"',l.approvalTime"+i+"='"+declareTime+"' where l.leaveOrOtID="+leaveOrOtID;
		 System.out.println("hql="+hql); 
		 dao.update(hql); 
	 }
	 
	 public void updateLeaveOrOt1(int leaveOrOtID,int i){
		 //String hql="update Supervision1 set supervisionResult="+result+" where supervisionId="+supervisionId;
		 String hql="update LeaveOrOt l set l.approvalResult="+i+" where l.leaveOrOtID="+leaveOrOtID;
		 System.out.println("hql="+hql); 
		 dao.update(hql);
		 
	 }
	 
	 public LeaveOrOtKind findByKindID(String kindName){
		 String hql="from LeaveOrOtKind l where l.kindName='"+kindName+"'";
		 System.out.println("hql="+hql); 
		 return this.dao.findByKindID(hql);
	 }
	 
	 public void saveLeaveOrOt(LeaveOrOt leaveOrOt){
		 dao.saveLeaveOrOt(leaveOrOt);
	 }
	 
	 public List<LeaveOrOtKindbean> findByuserid1(String userid){
		 String hql=" from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID and lk.kindType=1 and l.leaveOrOtUser="+userid+" order by l.approvalResult ";
			System.out.println("hql="+hql); 		
		return this.dao.findByuserid1(hql);
	 }
	 public List<LeaveOrOtKindbean> findByuserid2(String userid){
		 String hql=" from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID and lk.kindType=2 and l.leaveOrOtUser="+userid+" order by l.approvalResult ";
			System.out.println("hql="+hql); 		
		return this.dao.findByuserid1(hql);
	 }
	 public List<LeaveOrOtKindbean> findByuserid3(){
		 String hql=" from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID order by l.leaveOrOtDate desc ";
			System.out.println("hql="+hql); 		
		return this.dao.findByuserid3(hql);
	 }
	 
	 public List<LeaveOrOtKind> findByKindName(String kindType){
		 String hql=" from LeaveOrOtKind lk where lk.kindType="+kindType;
			System.out.println("hql="+hql); 		
		return this.dao.findByKindName(hql);
	 }
	 
	 public List<LeaveOrOtKindbean> leaveAndOvertimefinish(String userid){
		 //String hql=" from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID  and (l.approvalID1="+userid+" or l.approvalID2="+userid+" or l.approvalID3="+userid+")and(l.approvalResult=3 or l.approvalResult=4) order by l.leaveOrOtDate desc ";
		 String hql=" from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID  and l.approvalID1="+userid+" and(l.approvalResult=3 or l.approvalResult=4) order by l.leaveOrOtDate desc ";
		 System.out.println("hql="+hql); 		
		 List<LeaveOrOtKindbean> data =  this.dao.findByuserid1(hql);
		 
		 List<Integer> messageids = new ArrayList<Integer>();
		 for (int i = 0; i < data.size(); i++) {
			 messageids.add(data.get(i).getLeaveOrOtID());
		 }
		 
		 //安卓端加载数据时， 将消息设置为已推送
		 System.out.println("安卓端加载请假加班数据时， 将消息设置为已推送");
		 this.pushMsgService.pushPushMsg(PushMsg.MODULERID_LEAVEANDOVERTIME, Integer.parseInt(userid), messageids);
		 
		//获得消息的状态
		 List<LeaveOrOtKindbean> ret = new ArrayList<LeaveOrOtKindbean>();
		 Map<String, PushMsg> mapMsgs = this.pushMsgService.getPushMsgs(PushMsg.MODULERID_LEAVEANDOVERTIME, -1, messageids);
		 for (int i = 0; i < data.size(); i++) {
			LeaveOrOtKindbean dt = data.get(i);
			
			LeaveOrOtKindBeanWithStatus dtnew = new LeaveOrOtKindBeanWithStatus(dt);
			
			if (mapMsgs.get(""+dt.getLeaveOrOtID()) != null)
				dtnew.setMsgstatus(mapMsgs.get(""+dt.getLeaveOrOtID()).getMsgstatus());
			else
				dtnew.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_READ);
			
			ret.add(dtnew);
		 }
		 
		 return ret;
	 }
	 public List<LeaveOrOtKindbean> leaveAndOvertimeunfinish(String userid){
		 //String hql=" from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID  and (l.approvalID1="+userid+" or l.approvalID2="+userid+" or l.approvalID3="+userid+")and(l.approvalResult=1 or l.approvalResult=2) order by l.leaveOrOtDate desc ";
		 String hql=" from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID  and l.approvalID1="+userid+" and(l.approvalResult=1 or l.approvalResult=2) order by l.leaveOrOtDate desc ";	
		 System.out.println("hql="+hql); 		
		 List<LeaveOrOtKindbean> data = this.dao.findByuserid3(hql);
		 
		 List<Integer> messageids = new ArrayList<Integer>();
		 for (int i = 0; i < data.size(); i++) {
			 messageids.add(data.get(i).getLeaveOrOtID());
		 }
		//安卓端加载数据时， 将消息设置为已推送
		 System.out.println("安卓端加载请假加班数据时， 将消息设置为已推送");
		 this.pushMsgService.pushPushMsg(PushMsg.MODULERID_LEAVEANDOVERTIME, Integer.parseInt(userid), messageids);
		 
		//获得消息的状态
		 List<LeaveOrOtKindbean> ret = new ArrayList<LeaveOrOtKindbean>();
		 Map<String, PushMsg> mapMsgs = this.pushMsgService.getPushMsgs(PushMsg.MODULERID_LEAVEANDOVERTIME, -1, messageids);
		 for (int i = 0; i < data.size(); i++) {
			LeaveOrOtKindbean dt = data.get(i);
			
			LeaveOrOtKindBeanWithStatus dtnew = new LeaveOrOtKindBeanWithStatus(dt);
			
			if (mapMsgs.get(""+dt.getLeaveOrOtID()) != null)
				dtnew.setMsgstatus(mapMsgs.get(""+dt.getLeaveOrOtID()).getMsgstatus());
			else
				dtnew.setMsgstatus(PushMsg.MSGSTATUS_PUSHED_READ);
			
			ret.add(dtnew);
		 }
		 
		 return ret;
	 }
	 public List<LeaveOrOtKindbean> leaveAndOvertiemALLbymy(String userid){
		 String hql=" from LeaveOrOt l,LeaveOrOtKind lk where l.leaveOrOtKind=lk.kindID  and l.leaveOrOtUser="+userid+" order by l.leaveOrOtDate desc ";
			System.out.println("hql="+hql); 		
		return this.dao.findByuserid1(hql);
	 }
	 
	 public List<LeaveOrOtKindbean> selectLeaveAndOvertimefinish(String userid,String content){
		 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 //String hql=" from LeaveOrOt l,LeaveOrOtKind lk , User u  where l.leaveOrOtKind=lk.kindID and u.userId=l.leaveOrOtUser  and (l.approvalID1="+userid+" or l.approvalID2="+userid+" or l.approvalID3="+userid+")and (l.approvalResult=3 or l.approvalResult=4)  and ( u.name like '%"+content+"%' or l.leaveOrOtReason like '%"+content+"%' or l.leaveOrOtDate like '%"+content+"%' ) order by l.leaveOrOtDate desc ";		
		   String hql=" from LeaveOrOt l,LeaveOrOtKind lk , User u  where l.leaveOrOtKind=lk.kindID and u.userId=l.leaveOrOtUser  and l.approvalID1="+userid+" and(l.approvalResult=3 or l.approvalResult=4) and (u.name like '%"+content+"%' or l.leaveOrOtReason like '%"+content+"%'  ) order by l.leaveOrOtDate desc ";
		 return this.dao.selectByuserid1(hql);
	 }
	 public List<LeaveOrOtKindbean> selectLeaveAndOvertimeunfinish(String userid,String content){
		 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 //String hql=" from LeaveOrOt l,LeaveOrOtKind lk , User u  where l.leaveOrOtKind=lk.kindID and u.userId=l.leaveOrOtUser  and (l.approvalID1="+userid+" or l.approvalID2="+userid+" or l.approvalID3="+userid+")and(l.approvalResult=1 or l.approvalResult=2) and (u.name like '%"+content+"%' or l.leaveOrOtReason like '%"+content+"%' or l.leaveOrOtDate like '%"+content+"%' ) order by l.leaveOrOtDate desc ";		
		   String hql=" from LeaveOrOt l,LeaveOrOtKind lk , User u  where l.leaveOrOtKind=lk.kindID and u.userId=l.leaveOrOtUser  and l.approvalID1="+userid+" and(l.approvalResult=1 or l.approvalResult=2) and (u.name like '%"+content+"%' or l.leaveOrOtReason like '%"+content+"%') order by l.leaveOrOtDate desc ";
		 return this.dao.selectByuserid1(hql);
	 }
	 
	 public List<LeaveOrOtKindbean> selectleaveAndOvertiemALLbymy(String userid,String content){
		 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		 String hql=" from LeaveOrOt l,LeaveOrOtKind lk   where l.leaveOrOtKind=lk.kindID  and l.leaveOrOtUser="+userid+" and ( l.leaveOrOtReason like '%"+content+"%' ) order by l.leaveOrOtDate desc ";	
		return this.dao.findByuserid1(hql);
	 }
	 public List<LeaveOrOtKindbean> selectLeaveAndOvertiemALL(String content){
		 try {
				content=new String(content.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		// String hql=" from LeaveOrOt l,LeaveOrOtKind lk , User u where l.leaveOrOtKind=lk.kindID and u.userId=l.leaveOrOtUser  and (u.name like binary '%"+content+"%' or l.leaveOrOtReason like binary '%"+content+"%' or l.leaveOrOtDate like binary '%"+content+"%' ) order by l.leaveOrOtDate desc ";
		 String hql=" from LeaveOrOt l,LeaveOrOtKind lk , User u where l.leaveOrOtKind=lk.kindID and u.userId=l.leaveOrOtUser  and (u.name like '%"+content+"%' or l.leaveOrOtReason like '%"+content+"%' ) order by l.leaveOrOtDate desc ";
	     System.out.println("hql="+hql); 		
		return this.dao.findByuserid3(hql);
	 }
	 
	  public List<LeaveOrOtApprovalbean> leaveOrOtApproval(String userid){
		  String hql="from LeaveOrOtApproval l  where  l.userID ="+userid;
		  return this.dao.leaveOrOtApproval(hql);
	  }
	  
	  public void saveLeaveOrOtApproval (LeaveOrOtApproval approval){
		  dao.saveLeaveOrOtApproval(approval);
	  }
	  
	  public void updateLeaveOrOtApproval (LeaveOrOtApproval approval){
		  //String hql="update LeaveOrOt l set l.approvalResult="+i+" where l.leaveOrOtID="+leaveOrOtID;
		  String hql="update LeaveOrOtApproval l set l.approval1 ="+approval.getApproval1()+",l.approval2="+approval.getApproval2()+",l.approval3="+approval.getApproval3()+"where l.userID="+approval.getUserID();
		  dao.update(hql);
	  }

	 
}
