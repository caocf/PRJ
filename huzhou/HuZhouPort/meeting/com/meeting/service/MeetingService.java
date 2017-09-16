package com.meeting.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huzhouport.user.model.User;
import com.meeting.dao.MeetingDao;
import com.meeting.model.MeetingBasic;

public class MeetingService 
{
	MeetingDao meetingDao;
	
	public void setMeetingDao(MeetingDao meetingDao)
	{
		this.meetingDao=meetingDao;
	}
	
	public void applyroom(MeetingBasic meetingBasic)
	{
		meetingDao.applyroom(meetingBasic);
	}
	
	public User getDepHeader(int depid)
	{
		return meetingDao.getDepHeader(depid);
	}
	
	public int getOfficeID(int depid)
	{
		return meetingDao.getOfficeID(depid);
	}
	
	
	public List<?> queryMeetingRooms()
	{
		return meetingDao.queryMeetingRooms();
	}
	
	public List<?> queryApplicationRecord(int year,int month,int roomid)
	{
		return meetingDao.queryApplicationRecord(year,month,roomid);
	}
	public List<?> queryApplicationRecordByDay(Date date,int roomid)
	{
		return meetingDao.queryApplicationRecordByDay(date,roomid);
	}
	public List<?> queryApplicationRecordByStatus(int userid,int status)
	{
		return meetingDao.queryApplicationRecordByStatus(userid,status);
	}
	
	public List<?> queryApplicationRecordByApprover(int userid,int status)
	{
		return meetingDao.queryApplicationRecordByApprover(userid,status);
	}
	
	public List<?> queryApplicationRecordByOffice(int status,int pid)
	{
		return meetingDao.queryApplicationRecordByOffice(status,pid);
	}
	
	public void commitCheck(MeetingBasic meetingBasic)
	{
		meetingDao.commitCheck(meetingBasic);
	}
	
	public MeetingBasic findMBByID(int id)
	{
		return meetingDao.findMBByID(id);
	}
	
	public List<?> findIDsByDepid(int depid)
	{
		return meetingDao.findIDsByDepid(depid);
		
	}
	
	public Map<Integer,Integer> findUnvailableRoomIDs(String d1,String d2)
	{
		
		return meetingDao.findUnvailableRoomIDs(d1,d2);
		
	}
	public void updateIsRead(MeetingBasic mb)
	{
		
		meetingDao.updateIsRead(mb);	
		
	}
	
	public int countAp(int userid)
	{
		
		return meetingDao.countAp(userid);	
		
	}
	public int countMyCheck(int userid)
	{
		
		return meetingDao.countMyCheck(userid);
		
	}
	public int countOfficeCheck(int userid)
	{
		
		return meetingDao.countOfficeCheck(userid);	
		
	}
	
	public List<?> meetingRoomByMonth(int year,int month,int roomid)
	{
		return meetingDao.meetingRoomByMonth(year,month,roomid);
		
	}
	
}
