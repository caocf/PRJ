package com.huzhouport.schedule.dao;

import java.util.List;


import com.huzhouport.common.dao.BaseDao;
import com.huzhouport.schedule.model.ScheduleAttachment;
import com.huzhouport.schedule.model.ScheduleEvent;
import com.huzhouport.schedule.model.ScheduleEventAttachment;
import com.huzhouport.schedule.model.ScheduleEventUser;
import com.huzhouport.user.model.User;

public interface ScheduleDao extends BaseDao<ScheduleEvent> {
	public List<?> EventListByUserId(User user)throws Exception;
	public List<?> EventListByUserIdAndTime(User user,ScheduleEvent scheduleEvent)throws Exception;
	public List<?> EventListByUserIdAndNowDate(User user)throws Exception;
	public List<?> EventListByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public List<ScheduleAttachment> FindAttachmentByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public List<ScheduleEventUser> FindUserListByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public List<?> FindUserAndScheduleEventUserByEventId(ScheduleEvent scheduleEvent)throws Exception;
	public List<?> FindFirstUser(ScheduleEvent scheduleEvent)throws Exception;	
	public List<?> EventKindList()throws Exception;
	public String deleteEventUser(ScheduleEventUser scheduleEventUser) throws Exception;
	public String deleteEventAttachment(ScheduleEventAttachment sea) throws Exception;
	public String deleteAttachment(ScheduleAttachment sa) throws Exception;
	public String deleteEvent(ScheduleEvent scheduleEvent) throws Exception;
	public String addEvent(ScheduleEvent scheduleEvent) throws Exception;
	public List<ScheduleEvent> EventListByTime(ScheduleEvent scheduleEvent)throws Exception;
	public String addEventUser(ScheduleEventUser scheduleEventUser)throws Exception;
	public String addAttachment(ScheduleAttachment scheduleAttachment)throws Exception;
	public List<ScheduleAttachment> findAttachment(String sname)throws Exception;
	public String addEventAttachment(ScheduleEventAttachment scheduleEventAttachment)throws Exception;
	public String updateEvent(ScheduleEvent scheduleEvent) throws Exception;
	public String AddAgreeReason(ScheduleEventUser scheduleEventUser)throws Exception;
	public String upDateRemind(ScheduleEventUser scheduleEventUser)throws Exception;
	public String AddUser(ScheduleEventUser scheduleEventUser)throws Exception;
	public List<ScheduleAttachment> FindAttachmentByAttachmentId(int attachmentId) throws Exception;
	//闹钟服务
	public List<?> EventClickListByUserId(User user)throws Exception;
	//回复情况
	public List<?> FindFeedbackByEventId(int eventid)throws Exception;
	//改变转发人状态
	public boolean ChangeUserToforwarding(int eventid,int userid)throws Exception;
	public List<ScheduleEvent> EventInfoByEventId(int eventid)throws Exception;
	public int unfeedCount(int userid,String etime);
}
