package com.meeting.action;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huzhouport.illegal.service.impl.AbnormalpushService;
import com.huzhouport.user.model.User;
import com.meeting.model.MeetingBasic;
import com.meeting.service.MeetingService;

public class MeetingAction 
{	
	MeetingService meetingService;
	
	public void setMeetingService(MeetingService meetingService)
	{
		this.meetingService=meetingService;
	}
	
	MeetingBasic meetingBasic;
	
	public void setMeetingBasic(MeetingBasic meetingBasic)
	{
		this.meetingBasic=meetingBasic;
	}
	public MeetingBasic getMeetingBasic()
	{
		return meetingBasic;
	}
	
	User user;
	public void setUser(User user)
	{
		this.user=user;
		
	}
	public User getUser()
	{
		return user;
	}
	
	List list;
	public void setList(List list)
	{
		this.list=list;
	}
	public List getList()
	{
		return list;
	}
	
	int year,month;	
	
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getResultcode() {
		return resultcode;
	}
	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}

	int resultcode=-1;
	
	private AbnormalpushService pushservice;//=new AbnormalpushService();
	
	
	public AbnormalpushService getPushservice() {
		return pushservice;
	}
	public void setPushservice(AbnormalpushService pushservice) {
		this.pushservice = pushservice;
	}
	public String applyroom()
	{
		meetingBasic.setApplytime(new Date());
		meetingBasic.setStatus(1);//待审核
		meetingBasic.setApprovemark("");
		meetingBasic.setDepstatus(1);//待审核
		meetingBasic.setDepapmark("");
		meetingBasic.setIsread(1);//申请者已读		
		
		int depid=user.getPartOfDepartment();
		if(depid==263)//如果是局领导
		{
			meetingBasic.setApprover(meetingBasic.getApplicaionor());
			meetingBasic.setStatus(2);//同意
			meetingBasic.setApprovetime(new Date());
			
		}else//其他部门
		{
			User header=meetingService.getDepHeader(user.getPartOfDepartment());//找到正确的审核人
			if(header!=null)
				meetingBasic.setApprover(header.getUserId());
			if(meetingBasic.getApplicaionor()==meetingBasic.getApprover())
			{
				meetingBasic.setStatus(2);//同意
				meetingBasic.setApprovetime(new Date());
			}
		}	
		
		//int officeid=meetingService.getOfficeID(user.getPartOfDepartment());
		meetingBasic.setOfficeapprover(2988);//办公室审核专人
		meetingService.applyroom(meetingBasic);		
		resultcode=1;		
		
		if(meetingBasic.getApplicaionor()==meetingBasic.getApprover())//直接给办公室
		{
			JSONObject msg=new JSONObject();
			JSONArray ids=new JSONArray();
			/*List<?> list=meetingService.findIDsByDepid(officeid);
			for(int i=0;i<list.size();i++)
			{
				int id=(Integer) list.get(i);
				ids.add(id);
			}*/
			ids.add(meetingBasic.getOfficeapprover());
			msg.put("ids",ids);
			msg.put("title", "您有新的会议申请审批");
			msg.put("tip", "");
			
			msg.put("mstype", 1);//会议消息
			msg.put("type", 2);//给审核者
			msg.put("status", 1);
			msg.put("id", meetingBasic.getId());
			String s1=msg.toString();				
			pushservice.pushAbnormal(s1);
		}else//给科长
		{
			JSONObject msg=new JSONObject();
			JSONArray ids=new JSONArray();
			ids.add(meetingBasic.getApprover());
			msg.put("ids",ids);
			msg.put("title", "您有新的会议申请审批");
			msg.put("tip", "");
			
			msg.put("mstype", 1);//会议消息
			msg.put("type", 2);//给审核者
			msg.put("status", 1);
			msg.put("id", meetingBasic.getId());
			String s1=msg.toString();				
			pushservice.pushAbnormal(s1);
		}			
		
		
		return "success";
	}
	
	Map<Integer,Integer> roomids;
	
	
	public Map<Integer, Integer> getRoomids() {
		return roomids;
	}
	public void setRoomids(Map<Integer, Integer> roomids) {
		this.roomids = roomids;
	}

	String date1,date2;
	
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	public String getDate2() {
		return date2;
	}
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	public String queryMeetingRooms()
	{
		list=meetingService.queryMeetingRooms();
		roomids=meetingService.findUnvailableRoomIDs(date1, date2);
		return "success";		
	}
	
	public String queryApplicationRecord()
	{
		list=meetingService.queryApplicationRecord(year, month,meetingBasic.getMeetingroom());
		
		return "success";
	}
	
	public String queryApplicationRecordByDay()
	{
		list=meetingService.queryApplicationRecordByDay(meetingBasic.getMeetingdate(),meetingBasic.getMeetingroom());
		
		return "success";
	}
	
	public String queryApplicationRecordByApprover()
	{
		list=meetingService.queryApplicationRecordByApprover(user.getUserId(),meetingBasic.getStatus());
		
		return "success";
	}
	
	public String queryApplicationRecordByOffice()
	{
		list=meetingService.queryApplicationRecordByOffice(meetingBasic.getDepstatus(),user.getUserId());
		
		return "success";
	}
	
	public String queryApplicationRecordByStatus()
	{
		list=meetingService.queryApplicationRecordByStatus(user.getUserId(),meetingBasic.getStatus());
		return "success";
	}
	
	
	int checktype;
	
	public int getChecktype() {
		return checktype;
	}
	public void setChecktype(int checktype) {
		this.checktype = checktype;
	}
	public String commitCheck()
	{
		MeetingBasic mb=meetingService.findMBByID(meetingBasic.getId());
		if(mb==null)return "error";
		
		if(checktype==1)//科长审批
		{
			mb.setStatus(meetingBasic.getStatus());
			mb.setApprovemark(meetingBasic.getApprovemark());
			mb.setApprovetime(new Date());
			meetingBasic.setApprovetime(new Date());
			meetingBasic.setDepaptime(mb.getDepaptime());
			
			if(pushservice!=null)
			{
				JSONObject msg=new JSONObject();
				if(mb.getStatus()==3)//驳回，反馈给申请者
				{
					JSONArray ids=new JSONArray();
					ids.add( mb.getApplicaionor());
					msg.put("ids",ids);
					msg.put("title", "您有新的会议审批结果");
					msg.put("tip", "申请已驳回");
					
					msg.put("mstype", 1);//会议消息
					msg.put("type", 1);//给申请者
					msg.put("status", 3);
					msg.put("id", mb.getId());
					
					mb.setIsread(0);//重设未读
				
				}else//流转到办公室
				{
					JSONArray ids=new JSONArray();
					/*List<?> list=meetingService.findIDsByDepid(mb.getOfficeapprover());
					for(int i=0;i<list.size();i++)
					{
						int id=(Integer) list.get(i);
						ids.add(id);
					}*/
					ids.add(mb.getOfficeapprover());
					msg.put("ids",ids);
					msg.put("title", "您有新的会议审批");
					msg.put("tip", "");
					
					msg.put("mstype", 1);//会议消息
					msg.put("type", 2);//给审核者
					msg.put("status", 1);
					msg.put("id", mb.getId());
				}			
				
				String s1=msg.toString();
				pushservice.pushAbnormal(s1);
				
			}
		}
		else//办公室审批
		{
			mb.setDepstatus(meetingBasic.getDepstatus());
			mb.setDepapmark(meetingBasic.getDepapmark());
			mb.setDepaptime(new Date());
			meetingBasic.setDepaptime(new Date());
			meetingBasic.setApprovetime(mb.getApprovetime());
			
			if(pushservice!=null)
			{
				JSONObject msg=new JSONObject();
				JSONArray ids=new JSONArray();
				ids.add( mb.getApplicaionor());
				msg.put("ids",ids);
				msg.put("title", "您有新的会议审批");
				if(mb.getDepstatus()==3)
				{
					msg.put("tip", "申请已驳回");
					msg.put("status", 3);
				}
				else
				{
					msg.put("tip", "申请已通过");
					msg.put("status", 2);
				}
				
				msg.put("mstype", 1);//会议消息
				msg.put("type", 1);//给申请者				
				msg.put("id", mb.getId());
				
				String s1=msg.toString();
				pushservice.pushAbnormal(s1);
				
			}
			
			mb.setIsread(0);//重设未读
		}
		
		
		meetingService.commitCheck(mb);
		
		return "success";
	}
	public String updateIsRead()
	{
		MeetingBasic mb=meetingService.findMBByID(meetingBasic.getId());
		mb.setIsread(1);
		meetingService.updateIsRead(mb);
		return "success";
	}
	
	int ap;
	int check;
	int officecheck;
	
	
	public int getAp() {
		return ap;
	}
	public void setAp(int ap) {
		this.ap = ap;
	}
	public int getCheck() {
		return check;
	}
	public void setCheck(int check) {
		this.check = check;
	}
	public int getOfficecheck() {
		return officecheck;
	}
	public void setOfficecheck(int officecheck) {
		this.officecheck = officecheck;
	}
	public String countAp()
	{
		ap=meetingService.countAp(user.getUserId());
		check=meetingService.countMyCheck(user.getUserId());
		officecheck=meetingService.countOfficeCheck(user.getUserId());
		return "success";
	}
	public String countMyCheck()
	{
		resultcode=meetingService.countMyCheck(user.getUserId());
		return "success";
	}
	public String countOfficeCheck()
	{
		resultcode=meetingService.countOfficeCheck(user.getUserId());
		return "success";
	}
	
	public String allMeetingRooms()
	{
		list=meetingService.queryMeetingRooms();
		return "success";
	}
	
	public String meetingRoomByMonth()
	{
		list=meetingService.meetingRoomByMonth(year,month,meetingBasic.getMeetingroom());
		return "success";
	}
	
	
	public String test()
	{		
		/*JSONObject msg=new JSONObject();
		msg.put("type", 1);
		JSONArray ids=new JSONArray();
		ids.add(219);
		msg.put("ids", ids);
		String s1=msg.toString();
		abpush.pushAbnormal(s1);*/
		int officeid=meetingService.getOfficeID(user.getPartOfDepartment());
		List<?> list=meetingService.findIDsByDepid(officeid);
		
		return "success";
	}
	
}
