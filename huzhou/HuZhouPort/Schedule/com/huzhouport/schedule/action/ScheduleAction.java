package com.huzhouport.schedule.action;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huzhouport.common.action.BaseAction;
import com.huzhouport.illegal.service.impl.AbnormalpushService;

import com.huzhouport.schedule.model.ScheduleAttachment;
import com.huzhouport.schedule.model.ScheduleEvent;
import com.huzhouport.schedule.model.ScheduleEventAttachment;
import com.huzhouport.schedule.model.ScheduleEventUser;
import com.huzhouport.schedule.model.ScheduleKind;
import com.huzhouport.schedule.service.ScheduleService;
import com.huzhouport.user.model.User;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class ScheduleAction extends BaseAction implements ModelDriven<ScheduleEvent> 
{
	private ScheduleEvent scheduleEvent;
	private ScheduleAttachment scheduleAttachment;
	private ScheduleEventAttachment scheduleEventAttachment;
	private ScheduleKind ScheduleKind;
	private ScheduleEventUser scheduleEventUser;
	private ScheduleService scheduleService;
	private User user;
	private List<?> list;
	private int ieventId;
	
	private AbnormalpushService pushservice;
	

	public AbnormalpushService getPushservice() {
		return pushservice;
	}

	public void setPushservice(AbnormalpushService pushservice) {
		this.pushservice = pushservice;
	}

	public int getIeventId() {
		return ieventId;
	}

	public void setIeventId(int ieventId) {
		this.ieventId = ieventId;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public ScheduleEvent getModel() {
		return scheduleEvent;
	}

	public ScheduleEvent getScheduleEvent() {
		return scheduleEvent;
	}

	public void setScheduleEvent(ScheduleEvent scheduleEvent) {
		this.scheduleEvent = scheduleEvent;
	}

	public ScheduleAttachment getScheduleAttachment() {
		return scheduleAttachment;
	}

	public void setScheduleAttachment(ScheduleAttachment scheduleAttachment) {
		this.scheduleAttachment = scheduleAttachment;
	}

	public ScheduleEventAttachment getScheduleEventAttachment() {
		return scheduleEventAttachment;
	}

	public void setScheduleEventAttachment(
			ScheduleEventAttachment scheduleEventAttachment) {
		this.scheduleEventAttachment = scheduleEventAttachment;
	}

	public ScheduleKind getScheduleKind() {
		return ScheduleKind;
	}

	public void setScheduleKind(ScheduleKind scheduleKind) {
		ScheduleKind = scheduleKind;
	}

	public ScheduleEventUser getScheduleEventUser() {
		return scheduleEventUser;
	}

	public void setScheduleEventUser(ScheduleEventUser scheduleEventUser) {
		this.scheduleEventUser = scheduleEventUser;
	}

	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// 根据用户搜相关事件
	public String EventListByUserId() throws Exception {
		list = this.scheduleService.EventListByUserId(user);
		return SUCCESS;
	}

	// 日程类型
	public String EventKindList() throws Exception {
		list = this.scheduleService.EventKindList();
		return SUCCESS;
	}

	// 根据用户和时间搜相关事件
	public String EventListByUserIdAndTime() throws Exception {
		list = this.scheduleService.EventListByUserIdAndTime(user,
				scheduleEvent);
		return SUCCESS;
	}

	// 根据用户和时间搜相关事件
	public String EventListByUserIdAndNowDate() throws Exception {
		list = this.scheduleService.EventListByUserIdAndNowDate(user);
		return SUCCESS;
	}
	
	// 根据用户和时间搜相关事件
	public String EventListByUserIdAndNowDateR() throws Exception {
		list = this.scheduleService.EventListByUserIdAndNowDate(user);
		return SUCCESS;
	}
	
	// 根据事件编号搜索附件
	public String FindAttachmentByEventId() throws Exception {
		list = this.scheduleService.FindAttachmentByEventId(scheduleEvent);
		return SUCCESS;
	}

	// 根据事件编号搜索相关人员
	public String FindUserListByEventId() throws Exception {
		list = this.scheduleService.FindUserListByEventId(scheduleEvent);
		return SUCCESS;
	}

	// 查看根据事件ID
	public String EventListByEventId() throws Exception {
		list = this.scheduleService.EventListByEventId(scheduleEvent);
		return SUCCESS;
	}

	// 修改根据事件ID
	public String updateByEventId() throws Exception {
		try{
		this.scheduleService.updateByEventId(scheduleEvent, scheduleEventUser,
				scheduleAttachment);
		ieventId=1;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return SUCCESS;
	}

	// 删除根据事件ID
	public String deleteByEventId() throws Exception 
	{
		this.scheduleService.deleteByEventId(scheduleEvent);
		ieventId=1;
		return SUCCESS;
	}

	// 增加事件
	public String addEvent() throws Exception 
	{
		try{
		this.scheduleService.addEvent(scheduleEvent,scheduleEventUser, scheduleAttachment);
		ieventId=1;
		
		JSONObject msg=new JSONObject();
		JSONArray ids=new JSONArray();
		//ids.add(scheduleEventUser.getUserId());
		String idlist[]=scheduleEventUser.getEventUserList().split(",");
		
		for(String sid:idlist)
		{
			int id=Integer.parseInt(sid);
			ids.add(id);
		}
		msg.put("ids", ids);
		msg.put("title", "您有新的公务通知");
		msg.put("tip", scheduleEvent.getEventName());
		msg.put("mstype", 4);//公务通知
		
		msg.put("scheduleEvent", scheduleEvent);
		msg.put("scheduleEventUser", scheduleEventUser);
		
		pushservice.pushAbnormal(msg.toJSONString());
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return SUCCESS;
	}

	// 删除附件根据附件ID
	public String deleteAttachmentIdByAttachmentId() throws Exception {
		this.scheduleService.deleteAttachmentIdByAttachmentId(scheduleEvent,
				scheduleAttachment);
		ieventId=1;
		return SUCCESS;
	}
	// 增加参加人意见AddAgreeReason
	public String AddAgreeReason() throws Exception {
		this.scheduleService.AddAgreeReason(scheduleEventUser);
		ieventId=1;
		return SUCCESS;
	}
	public String upDateRemind() throws Exception {
		this.scheduleService.upDateRemind(scheduleEventUser);
		ieventId=1;
		return SUCCESS;
	}
	// 转发
	public String AddUser() throws Exception {
		this.scheduleService.AddAUser(scheduleEventUser,user.getUserId());
		return SUCCESS;
	}
	public String FindUserAndScheduleEventUserByEventId()throws Exception{
		list=this.scheduleService.FindUserAndScheduleEventUserByEventId(scheduleEvent);
		return SUCCESS;
	}
	public String FindFirstUser()throws Exception{
		list=this.scheduleService.FindFirstUser(scheduleEvent);
		return SUCCESS;
	}
	//闹钟服务
	public String EventClickListByUserId() throws Exception {
		list = this.scheduleService.EventClickListByUserId(user);
		return SUCCESS;
	}
	//回复情况
	public String FindFeedbackByEventId()throws Exception{
		list = this.scheduleService.FindFeedbackByEventId(scheduleEvent.getEventId());
		return SUCCESS;
	}
	
	int userid_count;
	
	public int getUserid_count() {
		return userid_count;
	}

	public void setUserid_count(int userid_count) {
		this.userid_count = userid_count;
	}

	//总体未回复数量
	public String unfeedCount()
	{
		userid_count=this.scheduleService.unfeedCount(userid_count,scheduleEvent.getEventTime());
		return SUCCESS;
	}
}
