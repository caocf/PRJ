package com.huzhouport.schedule.dao.impl;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.common.util.ChangeType;
import com.huzhouport.schedule.dao.ScheduleDao;
import com.huzhouport.schedule.model.ScheduleAttachment;
import com.huzhouport.schedule.model.ScheduleEvent;
import com.huzhouport.schedule.model.ScheduleEventAttachment;
import com.huzhouport.schedule.model.ScheduleEventUser;
import com.huzhouport.user.model.User;

public class ScheduleDaoImpl extends BaseDaoImpl<ScheduleEvent> implements ScheduleDao{
	
	public List<?> EventListByUserId(User user)throws Exception{
		String hql="select se.eventTime from ScheduleEvent as se,ScheduleEventUser as seu,User as u,ScheduleKind sk" +
					" where  se.eventId=seu.eventId and seu.userId=u.userId and sk.scheduleKindId=se.eventKind and seu.userId="+user.getUserId();
		return this.hibernateTemplate.find(hql);
	}
	public List<?> EventListByUserIdAndTime(User user,ScheduleEvent scheduleEvent)throws Exception{
		String hql="select se,seu,u,sk from ScheduleEvent as se,ScheduleEventUser as seu,User as u,ScheduleKind as sk " +
				" where  se.eventId=seu.eventId and seu.userId=u.userId and sk.scheduleKindId=se.eventKind and "+
				"se.eventTime like '%"+scheduleEvent.getEventTime()+"%' and seu.userId="+user.getUserId() +" ORDER BY se.eventTime desc";
		return this.hibernateTemplate.find(hql);
	}
	
	public List<?> EventListByUserIdAndNowDate(User user)throws Exception{
		String hql="select se,seu,u,sk from ScheduleEvent as se,ScheduleEventUser as seu,User as u,ScheduleKind as sk " +
				" where se.eventKind=1 and se.eventId=seu.eventId and seu.userId=u.userId and sk.scheduleKindId=se.eventKind and "+
				"date_format(se.eventTime,'%Y-%m-%d')>='"+ChangeType.YearMonthDay()+"' and seu.userId="+user.getUserId() +" ORDER BY se.eventTime desc";
		return this.hibernateTemplate.find(hql);
	}
	public List<?> EventListByEventId(ScheduleEvent scheduleEvent)throws Exception{
		String hql="select se,seu,u,sk from ScheduleEvent as se,ScheduleEventUser as seu,User as u,ScheduleKind as sk " +
					"where  se.eventId=seu.eventId and sk.scheduleKindId=se.eventKind and " +
					"seu.userId=u.userId and se.eventId="+scheduleEvent.getEventId();
		return this.hibernateTemplate.find(hql);
	}
	@SuppressWarnings("unchecked")
	public List<ScheduleAttachment> FindAttachmentByEventId(ScheduleEvent scheduleEvent)throws Exception{
		String hql="select sa from ScheduleEvent as se,ScheduleAttachment as sa, ScheduleEventAttachment as sea " +
				"where se.eventId=sea.eventId and sa.attachmentId=sea.attachmentId and se.eventId="+scheduleEvent.getEventId();
		return this.hibernateTemplate.find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<ScheduleEventUser> FindUserListByEventId(ScheduleEvent scheduleEvent)throws Exception{
		String hql="from ScheduleEventUser as seu " +
				"where seu.eventId="+scheduleEvent.getEventId();
		return this.hibernateTemplate.find(hql);
	}
	public List<?> FindUserAndScheduleEventUserByEventId(ScheduleEvent scheduleEvent)throws Exception{
		String hql="select u,seu from ScheduleEventUser as seu,User as u " +
				"where seu.userId=u.userId and seu.eventId="+scheduleEvent.getEventId();
		return this.hibernateTemplate.find(hql);
	}
	public List<?> FindFirstUser(ScheduleEvent scheduleEvent)throws Exception{
		String hql="select u.name from ScheduleEventUser as seu,User as u " +
		"where seu.userId=u.userId and seu.userAgree=0 and seu.eventId="+scheduleEvent.getEventId();
		return this.hibernateTemplate.find(hql);
	}
	@SuppressWarnings("unchecked")
	public List<ScheduleEvent> EventListByTime(ScheduleEvent scheduleEvent)throws Exception{
		String hql="select se from ScheduleEvent as se" +
				" where se.eventTime like '%"+scheduleEvent.getEventTime()+"%'";
		return this.hibernateTemplate.find(hql);
	}
	public List<?> EventKindList()throws Exception{
		String hql="from ScheduleKind";
		return this.hibernateTemplate.find(hql);
	}
	public String deleteEventUser(ScheduleEventUser scheduleEventUser) throws Exception{
		this.hibernateTemplate.delete(scheduleEventUser);
		return null;
	}
	
	public String deleteEventAttachment(ScheduleEventAttachment sea) throws Exception{
		this.hibernateTemplate.delete(sea);
		return null;
	}
	public String deleteEvent(ScheduleEvent scheduleEvent) throws Exception{
		this.hibernateTemplate.delete(scheduleEvent);
		return null;
	}
	public String addEvent(ScheduleEvent scheduleEvent) throws Exception{
		String ss=this.hibernateTemplate.save(scheduleEvent).toString();
		return ss;
	}
	public String updateEvent(ScheduleEvent scheduleEvent) throws Exception{
		this.hibernateTemplate.update(scheduleEvent);
		return null;
	}
	public String deleteAttachment(ScheduleAttachment sa) throws Exception{
		this.hibernateTemplate.delete(sa);
		return null;
	}
	public String addEventUser(ScheduleEventUser seu) throws Exception{
		this.hibernateTemplate.saveOrUpdate(seu);
		return null;
	}
	
	public String addAttachment(ScheduleAttachment sa) throws Exception{
		this.hibernateTemplate.saveOrUpdate(sa);
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<ScheduleAttachment> findAttachment(String sa) throws Exception{
		String hql="from ScheduleAttachment as sa where sa.attachmentPath='"+sa+"'";
		return this.hibernateTemplate.find(hql);
	}
	public String addEventAttachment(ScheduleEventAttachment sea) throws Exception{
		this.hibernateTemplate.save(sea);
		return null;
	}
	public String AddAgreeReason(ScheduleEventUser scheduleEventUser)throws Exception{
		List<ScheduleEventUser> seulist=this.hibernateTemplate.find("from ScheduleEventUser seu where seu.eventId="+scheduleEventUser.getEventId()+" and seu.userId="+scheduleEventUser.getUserId());
		if(seulist.size()>0){
		if(scheduleEventUser.getUserAgree()==1||scheduleEventUser.getUserAgree()==2){
			if(seulist.get(0).getUserAgree()==4||seulist.get(0).getUserAgree()==5){
				scheduleEventUser.setUserAgree(scheduleEventUser.getUserAgree()+3);
			}
		}
		}
		this.hibernateTemplate.saveOrUpdate(scheduleEventUser);
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<ScheduleAttachment> FindAttachmentByAttachmentId(int attachmentId) throws Exception{
		String hql="select sa from ScheduleAttachment as sa where sa.attachmentId="+attachmentId;
		return this.hibernateTemplate.find(hql);
		
	}
	//闹钟服务
	public List<?> EventClickListByUserId(User user)throws Exception{
		String hql="select se.eventTime,seu.eventRemind,seu.eventRemindType,se.eventId,se.eventName from ScheduleEvent as se,ScheduleEventUser as seu,User as u,ScheduleKind sk" +
					" where  se.eventId=seu.eventId and seu.userId=u.userId and sk.scheduleKindId=se.eventKind and seu.userId="+user.getUserId();
		return this.hibernateTemplate.find(hql);
	}
	public String AddUser(ScheduleEventUser scheduleEventUser) throws Exception {
		this.hibernateTemplate.save(scheduleEventUser);
		return null;
	}
	public List<?> FindFeedbackByEventId(int eventid)throws Exception
	{
		String hql="select u.name,seu.userAgree,seu.agreeReason,seu.userAgree from User u,ScheduleEventUser as seu " +
				"where u.userId=seu.userId and seu.eventId="+eventid;
		return this.hibernateTemplate.find(hql);
	}
	//改变转发人状态
	public boolean ChangeUserToforwarding(int eventid,int userid)throws Exception{
		//参与改成转发参与
		String hql="update ScheduleEventUser seu set seu.userAgree=seu.userAgree+3 where seu.userId="+userid+" and seu.eventId="+eventid;
		this.hibernateTemplate.bulkUpdate(hql);
		
		return true;
	}
	public List<ScheduleEvent> EventInfoByEventId(int eventid)throws Exception{
		String hql="select se from ScheduleEvent as se where se.eventId="+eventid;
		return this.hibernateTemplate.find(hql);
	}
	@Override
	public String upDateRemind(ScheduleEventUser scheduleEventUser)
			throws Exception {
		List<ScheduleEventUser> seulist=this.hibernateTemplate.find("from ScheduleEventUser seu where seu.eventId="+scheduleEventUser.getEventId()+" and seu.userId="+scheduleEventUser.getUserId());
		if(seulist.size()>0){
			seulist.get(0).setEventRemind(scheduleEventUser.getEventRemind());
			seulist.get(0).setEventRemindType(scheduleEventUser.getEventRemindType());
		}
		this.hibernateTemplate.saveOrUpdate(seulist.get(0));
		return null;
	}
	@Override
	public int unfeedCount(int userid,String etime) 
	{
		String hql="select count(*) from ScheduleEventUser as su, ScheduleEvent as se where su.eventId=se.eventId and su.userId="+userid
				+" and se.eventTime>='"+etime+"' and su.userAgree=3";
		List list=this.hibernateTemplate.find(hql);
		Object count=list.get(0);
		return  Integer.valueOf(count.toString());
			
	}
}
