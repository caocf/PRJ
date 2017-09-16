package com.huzhouport.schedule.service;

import java.util.List;

import com.huzhouport.schedule.model.ScheduleAttachment;
import com.huzhouport.schedule.model.ScheduleEvent;
import com.huzhouport.schedule.model.ScheduleEventUser;
import com.huzhouport.user.model.User;

public interface ScheduleService {
	public List<?> EventListByUserId(User user)throws Exception;
	public List<?> EventListByUserIdAndTime(User user,ScheduleEvent scheduleEvent)throws Exception;
	public List<?> EventListByUserIdAndNowDate(User user)throws Exception;
	public List<?> EventListByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public List<?> FindAttachmentByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public List<?> FindUserListByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public List<?> EventKindList()throws Exception;
	public String deleteByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public String addEvent(ScheduleEvent scheduleEvent,ScheduleEventUser scheduleEventUser,ScheduleAttachment scheduleAttachment) throws Exception;
	public String updateByEventId(ScheduleEvent scheduleEvent,ScheduleEventUser scheduleEventUser,ScheduleAttachment scheduleAttachment) throws Exception;
	public String deleteAttachmentIdByAttachmentId(ScheduleEvent scheduleEvent,ScheduleAttachment scheduleAttachment)throws Exception;
	public String AddAgreeReason(ScheduleEventUser scheduleEventUser)throws Exception;
	public String upDateRemind(ScheduleEventUser scheduleEventUser)throws Exception;
	public String AddAUser(ScheduleEventUser scheduleEventUser,int userid)throws Exception;
	public List<?> FindUserAndScheduleEventUserByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public List<?> FindFirstUser(ScheduleEvent scheduleEvent)throws Exception;
	//闹钟服务
	public List<?> EventClickListByUserId(User user)throws Exception;
	//回复情况
	public List<?> FindFeedbackByEventId(int eventid)throws Exception;
	
	public int unfeedCount(int userid,String etime);
}
