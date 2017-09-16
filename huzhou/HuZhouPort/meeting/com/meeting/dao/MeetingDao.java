package com.meeting.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.organization.model.Department;
import com.huzhouport.user.model.User;
import com.meeting.model.MeetingBasic;

public class MeetingDao 
{	
	SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
	
	
	HibernateTemplate hibernateTemplate;
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
	{
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public void applyroom(MeetingBasic meetingBasic)
	{
		MeetingBasic v=meetingBasic;
		int a=v.getApplicaionor();
		hibernateTemplate.save(meetingBasic);
	}
	
	public User getDepHeader(int depid)
	{
		//正职
		String hql="from User u where u.partOfDepartment="+depid+" and u.userStatus=1 and (u.partOfPost=5 or u.partOfPost=15 or u.partOfPost=18)";
		List list=hibernateTemplate.find(hql);		
		if(list.size()>0)
		{
			return (User) list.get(0);
		}
		else
		{
			//副职
			String hql1="from User u where u.partOfDepartment="+depid+" and u.userStatus=1 and (u.partOfPost=13 or u.partOfPost=14 or u.partOfPost=16 or u.partOfPost=17 or u.partOfPost=19)";
			List list1=hibernateTemplate.find(hql);
			return (User) list.get(0);
		}
	}
	public int getOfficeID(int depid)
	{
		List list1=hibernateTemplate.find("from Department u where u.departmentId="+depid);
		if(list1.size()>0)
		{
			Department mydep=(Department) list1.get(0);
			String hql="from Department u where u.partOfDepartmentId="+mydep.getPartOfDepartmentId()+" and u.departmentName='办公室'";
			List list=hibernateTemplate.find(hql);
			if(list.size()>0)
			{
				Department dp=(Department)list.get(0);
				return dp.getDepartmentId();
			}
		}	
		
		return  0;
			
	}
	
	public List<?> queryMeetingRooms()
	{
		String hql="from MeetingType mt ,MeetingRoomType mrt where mt.typeid=mrt.id";
		return hibernateTemplate.find(hql);
	}
	
	
	public List<?> queryApplicationRecord(int year,int month,int roomid)
	{
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String first=sd.format(calendar.getTime());
		int lasttday=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lasttday);
		String last=sd.format(calendar.getTime());
		
		String hql="select mb.meetingdate from MeetingBasic mb where mb.meetingdate>='"+first+"' and mb.meetingdate<='"+last+"' and mb.meetingroom="+roomid+" group by mb.meetingdate";
		return hibernateTemplate.find(hql);
	}
	
	public List<?> queryApplicationRecordByDay(Date date,int roomid)
	{
		String sdate=sd.format(date);
		
		String hql="from MeetingBasic mb,User u where mb.applicaionor=u.userId and mb.meetingroom="+roomid+" and mb.meetingdate='"+sdate+"'";
		return hibernateTemplate.find(hql); 
	}
	public List<?> queryApplicationRecordByStatus(int userid,int status)
	{
		String statustring="";
		switch(status)
		{
		case 1://待审批
			statustring=" and mb.depstatus=1 and mb.status!=3";
			break;
		case 2://同意
			statustring=" and mb.depstatus=2 and mb.status=2";
			break;
		case 3://驳回
			statustring=" and (mb.depstatus=3 or mb.status=3)";
			break;
		case 4://撤回
			statustring=" and mb.depstatus="+status;
			break;
		}
		
		String hql="from MeetingBasic mb,MeetingType mt,User u  where mb.meetingroom=mt.id and mb.approver=u.userId" +
				" and mb.applicaionor="+userid;
		return hibernateTemplate.find(hql/*+statustring*/+" order by mb.applytime desc");
	}
	
	public List<?> queryApplicationRecordByApprover(int userid,int status)
	{
		String s="";
		
		if(status==0)//未审核
		{
			s=" and mb.status=1";
		}else
		{
			s=" and mb.status<>1";
		}
		
		String hql="from MeetingBasic mb,MeetingType mt,User u,User ap  where mb.meetingroom=mt.id and mb.applicaionor=u.userId and ap.userId="+userid+" and mb.approver="+userid
				;
		return hibernateTemplate.find(hql+s+" order by mb.applytime desc");
	}
	
	public List<?> queryApplicationRecordByOffice(int status,int pid)
	{
		String s="";
		
		if(status==0)//未审核
		{
			s=" and mb.depstatus=1";
		}else
		{
			s=" and mb.depstatus<>1";
		}
		
		String hql="from MeetingBasic mb,MeetingType mt,User u ,User ap where mb.meetingroom=mt.id and mb.applicaionor=u.userId and mb.approver=ap.userId" +
				" and mb.status=2 and mb.officeapprover="+pid;
		return hibernateTemplate.find(hql+s+" order by mb.applytime desc");
	}
	
	public void commitCheck(MeetingBasic meetingBasic)
	{
		hibernateTemplate.update(meetingBasic);
	}
	
	public MeetingBasic findMBByID(int id)
	{		
		String hql="from MeetingBasic mb where mb.id="+id;
		List<?> list= hibernateTemplate.find(hql);
		if(list.size()>0)
		{
			return (MeetingBasic) list.get(0);
		}else
		{
			return null;
		}
	}
	
	public List<?> findIDsByDepid(int depid)
	{
		String hql="select u.userId from User u where u.partOfDepartment="+depid;
		return hibernateTemplate.find(hql);
		
	}
	
	SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public Map<Integer,Integer> findUnvailableRoomIDs(String d1,String d2)
	{
		Map<Integer,Integer> ids=new HashMap();
		try
		{
			Calendar c1=Calendar.getInstance();
			Calendar c2=Calendar.getInstance();	
			c1.setTimeInMillis(sd1.parse(d1).getTime());
			c2.setTimeInMillis(sd1.parse(d2).getTime());
			/*c1.setTime(sd1.parse(d1));
			c1.set(Calendar.SECOND, 0);
			c1.set(Calendar.MILLISECOND, 0);
			c2.setTime(sd1.parse(d2));
			c2.set(Calendar.SECOND, 0);
			c2.set(Calendar.MILLISECOND, 0);*/
			String date=d1.substring(0,10);
			
			String hql="from MeetingBasic mb where mb.meetingdate='"+date+"'";
			List<?> list=hibernateTemplate.find(hql);
			for(int i=0;i<list.size();i++)
			{
				MeetingBasic m=(MeetingBasic) list.get(i);
				String time[]=m.getMeetingtime().split("-");
				String md1=date+" "+time[0];
				String md2=date+" "+time[1];
				Calendar mc1=Calendar.getInstance();
				Calendar mc2=Calendar.getInstance();
				mc1.setTimeInMillis(sd1.parse(md1).getTime());
				mc2.setTimeInMillis(sd1.parse(md2).getTime());
				/*mc1.setTime(sd1.parse(md1));//起始
				mc1.set(Calendar.SECOND, 0);
				mc1.set(Calendar.MILLISECOND, 0);
				mc2.setTime(sd1.parse(md2));//结束
				mc2.set(Calendar.SECOND, 0);
				mc2.set(Calendar.MILLISECOND, 0);*/
				if(mc1.getTimeInMillis()<c2.getTimeInMillis()&&c1.getTimeInMillis()<mc2.getTimeInMillis())//时间重叠
				{
					ids.put(m.getMeetingroom(),m.getMeetingroom());					
				}
			}
		}catch(Exception e)
		{
			
		}
		
		return ids;
	}
	
	public void updateIsRead(MeetingBasic mb)
	{
		hibernateTemplate.update(mb);
	}
	public int  countAp(int userid)
	{
		String hql="select count(*) from MeetingBasic mb where mb.isread=0 and mb.applicaionor="+userid;
		List list=hibernateTemplate.find(hql);
		return Integer.parseInt(list.get(0).toString());
	}
	public int countMyCheck(int userid)
	{
		String hql="select count(*) from MeetingBasic mb where mb.status=1 and mb.approver="+userid;
		List list=hibernateTemplate.find(hql);
		return Integer.parseInt(list.get(0).toString());
	}
	public int countOfficeCheck(int userid)
	{
		String hql="select count(*) from MeetingBasic mb where mb.depstatus=1 and mb.status=2 and mb.officeapprover="+userid;
		List list=hibernateTemplate.find(hql);
		return Integer.parseInt(list.get(0).toString());
	}
	
	public List<?> meetingRoomByMonth(int year,int month,int roomid)
	{
		Calendar c2=Calendar.getInstance();
		c2.set(Calendar.YEAR, year);
		c2.set(Calendar.MONTH, month-1);
		int lastday=c2.getActualMaximum(Calendar.DAY_OF_MONTH);
		String date1=String.format("%04d-%02d-%02d", year,month,1);
		String date2=String.format("%04d-%02d-%02d", year,month,lastday);
		String hql="from MeetingBasic mb where mb.meetingroom="+roomid+" and mb.meetingdate >= '"+date1+"' and mb.meetingdate<='"+date2+"'";
		
		return hibernateTemplate.find(hql);
		
	}

}
