package com.huzhouport.schedule.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.ManageFile;
import com.huzhouport.pushmsg.model.PushMsg;
import com.huzhouport.pushmsg.service.PushMsgService;
import com.huzhouport.schedule.dao.ScheduleDao;
import com.huzhouport.schedule.model.ScheduleAttachment;
import com.huzhouport.schedule.model.ScheduleEvent;
import com.huzhouport.schedule.model.ScheduleEventAttachment;
import com.huzhouport.schedule.model.ScheduleEventUser;
import com.huzhouport.schedule.service.ScheduleService;
import com.huzhouport.user.model.User;


public class ScheduleServiceImpl extends BaseServiceImpl<ScheduleAttachment> implements ScheduleService{
	private ScheduleDao scheduleDao;
	
	private PushMsgService pushMsgService;
	
	public void setPushMsgService(PushMsgService pushMsgService) {
		this.pushMsgService = pushMsgService;
	}
	
	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}
	
	public List<?> EventListByUserId(User user)throws Exception{
		List<?> list=this.scheduleDao.EventListByUserId(user);
		List<Map<String,String>> list2=new ArrayList<Map<String,String>>();
		for(int i=0;i<list.size();i++)
		{
			Map<String,String> map=new HashMap<String,String>();
			map.put("eventTime", (String) list.get(i));
			list2.add(map);
		}
		return list2;
	}
	public List<?> EventListByUserIdAndTime(User user,ScheduleEvent scheduleEvent)throws Exception{
		return this.scheduleDao.EventListByUserIdAndTime(user, scheduleEvent);
	}
	public List<?> EventListByEventId(ScheduleEvent scheduleEvent)throws Exception{
		return this.scheduleDao.EventListByEventId(scheduleEvent);
	}
	public List<?> FindAttachmentByEventId(ScheduleEvent scheduleEvent)throws Exception{
		return this.scheduleDao.FindAttachmentByEventId(scheduleEvent);
	}
	public List<?> FindUserListByEventId(ScheduleEvent scheduleEvent)throws Exception{
		return this.scheduleDao.FindUserListByEventId(scheduleEvent);
	}
	public List<?> EventKindList()throws Exception{
		return this.scheduleDao.EventKindList();
	}
	public String deleteByEventId(ScheduleEvent scheduleEvent)throws Exception
	{
		List<ScheduleAttachment> list=this.scheduleDao.FindAttachmentByEventId(scheduleEvent);
		//删除事件附件关联表
		for(int i=0;i<list.size();i++)
		{ ScheduleEventAttachment sea=new ScheduleEventAttachment();
		  sea.setAttachmentId(list.get(i).getAttachmentId());
		  sea.setEventId(scheduleEvent.getEventId());
		  this.scheduleDao.deleteEventAttachment(sea);
		}
		//删除附件表
		for(int i=0;i<list.size();i++)
		{ ScheduleAttachment sa=new ScheduleAttachment();
		  sa.setAttachmentId(list.get(i).getAttachmentId());
		  this.scheduleDao.deleteAttachment(sa);
		}
		//删除附件文件
		ManageFile manageFile=new ManageFile();
		for(int i=0;i<list.size();i++)
		{
		String sPath=ServletActionContext.getServletContext().getRealPath(
				"/"+GlobalVar.FilePATH+"/"+list.get(i).getAttachmentPath());
		manageFile.DeleteFolder(sPath);
		}
		//删除事件用户关联表
		List<ScheduleEventUser> list2=this.scheduleDao.FindUserListByEventId(scheduleEvent);
		for(int i=0;i<list2.size();i++)
		{ ScheduleEventUser seu=new ScheduleEventUser();
		seu.setUserId(list2.get(i).getUserId());
		seu.setEventId(scheduleEvent.getEventId());
		  this.scheduleDao.deleteEventUser(seu);
		}

		//删除事件表
		this.scheduleDao.deleteEvent(scheduleEvent);
		return null;
	}
	public String addEvent(ScheduleEvent scheduleEvent,ScheduleEventUser scheduleEventUser,ScheduleAttachment scheduleAttachment) throws Exception
	{
		//保存事件
		int iEventId=Integer.parseInt(this.scheduleDao.addEvent(scheduleEvent));
		scheduleEvent.setEventId(iEventId);
		//保存事件用户关联表
		//int iEventId=scheduleDao.EventListByTime(scheduleEvent).get(0).getEventId();
		String[] list=scheduleEventUser.getEventUserList().split(",");
		int iAgreeUser=scheduleEventUser.getUserId();
		String remindTime=scheduleEventUser.getEventRemind();
		int agreeType=scheduleEventUser.getEventRemindType();
		for(int i=0;i<list.length;i++)
		{   ScheduleEventUser seu=new ScheduleEventUser();
		    seu.setEventId(iEventId);
		     seu.setUserId(Integer.parseInt(list[i].trim()));
		     if(iAgreeUser==Integer.parseInt(list[i].trim()))
		     {
		    	 seu.setUserAgree(0);
		    	 seu.setEventRemind(remindTime);
		    	 seu.setEventRemindType(agreeType);
		    	 
		     }
		     else 
	    	 {
		    	 seu.setUserAgree(3);
		    	 seu.setEventRemind(remindTime);
		    	 seu.setEventRemindType(agreeType);
	    	 }
			this.scheduleDao.addEventUser(seu);
			
			/*//add by Grond Start

			PushMsg pushMsg=new PushMsg();
			pushMsg.setPushmsgtime(new Date());
			pushMsg.setMsgstatus(1);//消息状态自定义：1未推送未读；2未推送已读，3已推送未读；4已推送已读；消息轮询主要是未推送的。
			if(scheduleEvent.getEventKind()==1)
			{
				pushMsg.setModulerid(4);//模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息;4为会议安排；	
			}
			else
			{
				pushMsg.setModulerid(1);//模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息;4为会议安排；
				
			}
			pushMsg.setMessageid(scheduleEvent.getEventId());//消息内容ID，是日程安排表、请假申请表、通知公告信息表的外键
			pushMsg.setUserid(seu.getUserId());
			this.scheduleDao.savaObject(pushMsg);
			
			//add by Grond End*/

		}
		
		
		//上传文件并保存
		if(scheduleAttachment!=null)
		addAttachment(iEventId,scheduleAttachment);
		return null;
	}
	public String addAttachment(int iEventId,ScheduleAttachment scheduleAttachment) throws Exception{
		List<File> uploads=scheduleAttachment.getAf();
        List<String> uploadsName=scheduleAttachment.getAfFileName();
        if (uploads != null)
        {
            int i = 0;
            for (; i < uploads.size(); i++)
            {
                java.io.InputStream is = new java.io.FileInputStream(uploads.get(i));
                int beginIndex=uploadsName.get(i).lastIndexOf('.'); 
                String stype=uploadsName.get(i).substring(beginIndex+1);
                String sname=(new Date()).getTime()+"."+stype;
                String root=ServletActionContext.getServletContext().getRealPath(GlobalVar.FilePATH) + "/"+sname;
                java.io.OutputStream os = new java.io.FileOutputStream(root);
                byte buffer[] = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0)
                {
                    os.write(buffer, 0, count);
                }
                os.close();
                is.close();
                //保存附件表
                ScheduleAttachment SA=new ScheduleAttachment();
                SA.setAttachmentName(uploadsName.get(i));
                SA.setAttachmentPath(sname);
                this.scheduleDao.addAttachment(SA);
                List<ScheduleAttachment> list=this.scheduleDao.findAttachment(sname);
                
                int iAttachmentId=list.get(0).getAttachmentId();
                //保存事件附件表
                ScheduleEventAttachment sea=new ScheduleEventAttachment();
                sea.setEventId(iEventId);
                sea.setAttachmentId(iAttachmentId);
                
                this.scheduleDao.addEventAttachment(sea);
            }
        }
		
			return null;
	}
	public String deleteAttachmentIdByAttachmentId(ScheduleEvent scheduleEvent,ScheduleAttachment scheduleAttachment)throws Exception{
		int aId=scheduleAttachment.getAttachmentId();
		//删除事件用户关联表
		ScheduleEventAttachment sea=new ScheduleEventAttachment();
		  sea.setAttachmentId(scheduleAttachment.getAttachmentId());
		  sea.setEventId(scheduleEvent.getEventId());
		  this.scheduleDao.deleteEventAttachment(sea);
		//删除附件表
		  List<ScheduleAttachment> list=this.scheduleDao.FindAttachmentByAttachmentId(aId);
		this.scheduleDao.deleteAttachment(scheduleAttachment);
		
		//删除附件文件
		ManageFile manageFile=new ManageFile();
		String sPath=ServletActionContext.getServletContext().getRealPath(
				"/"+GlobalVar.FilePATH+"/"+list.get(0).getAttachmentPath());
		manageFile.DeleteFolder(sPath);

		return null;
	}
	
	public String updateByEventId(ScheduleEvent scheduleEvent,ScheduleEventUser scheduleEventUser,ScheduleAttachment scheduleAttachment) throws Exception{
		int iEventId=scheduleEvent.getEventId();
		//修改事件
		this.scheduleDao.updateEvent(scheduleEvent);
		//删除事件用户关联表
		int iAgreeUser=scheduleEventUser.getUserId();
		String sRemind=scheduleEventUser.getEventRemind();
		int iRemindType=scheduleEventUser.getEventRemindType();
		
		List<ScheduleEventUser> EventUserlist=this.scheduleDao.FindUserListByEventId(scheduleEvent);
		String[] list=scheduleEventUser.getEventUserList().split(",");			
		
		//删除事件用户关联表
		for (int i = 0; i < EventUserlist.size(); i++) {
			int it = 0;
			for (int j = 0; j < list.length; j++) {
				System.out.println(list[j].trim() + " " + " "
						+ EventUserlist.get(i).getUserId() + " " + iAgreeUser);
				if (Integer.parseInt(list[j].trim()) == EventUserlist.get(i)
						.getUserId()) {
					it = 1;
				}
				System.out.println(j+":"+it);
			}
			if (it == 0) {
				ScheduleEventUser seu = new ScheduleEventUser();
				seu.setEventId(iEventId);
				seu.setUserId(EventUserlist.get(i).getUserId());
				this.scheduleDao.deleteEventUser(seu);
			}
		}
		ScheduleEventUser seu = new ScheduleEventUser();
		seu.setEventId(iEventId);
		seu.setUserId(iAgreeUser);
		seu.setUserAgree(0);
		seu.setEventRemind(sRemind);
		seu.setEventRemindType(iRemindType);
		this.scheduleDao.addEventUser(seu);
		for (int i = 0; i < list.length; i++) {
			int it2 = 0;
			for (int j = 0; j < EventUserlist.size(); j++) {
				if (Integer.parseInt(list[i].trim()) == EventUserlist.get(j)
						.getUserId()) {
					it2 = 1;
					break;
				}
			}
			
			ScheduleEventUser seu2 = new ScheduleEventUser();

			if (it2 == 0) {
				seu2.setEventId(iEventId);
				seu2.setUserId(Integer.parseInt(list[i].trim()));
				seu2.setUserAgree(3);
				seu2.setEventRemind("0");
				seu2.setEventRemindType(1);
				this.scheduleDao.addEventUser(seu2);
		
			
			//add by Grond Start

			/*PushMsg pushMsg=new PushMsg();
			pushMsg.setPushmsgtime(new Date());
			pushMsg.setMsgstatus(1);//消息状态自定义：1未推送未读；2未推送已读，3已推送未读；4已推送已读；消息轮询主要是未推送的。
			if(scheduleEvent.getEventKind()==1)
			{
				pushMsg.setModulerid(4);//模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息;4为会议安排；	
			}
			else
			{
				pushMsg.setModulerid(1);//模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息;4为会议安排；
				
			}
			pushMsg.setMessageid(scheduleEvent.getEventId());//消息内容ID，是日程安排表、请假申请表、通知公告信息表的外键
			pushMsg.setUserid(seu2.getUserId());
			this.scheduleDao.savaObject(pushMsg);
			
			//add by Grond End*/
			}
		}
		//上传文件并保存
		String sMeg="";
		if(scheduleAttachment!=null)
		sMeg=addAttachment(iEventId,scheduleAttachment);
		return sMeg;
	}
	
	public String AddAgreeReason(ScheduleEventUser scheduleEventUser)throws Exception{
		return this.scheduleDao.AddAgreeReason(scheduleEventUser);
	}
	public List<?> FindUserAndScheduleEventUserByEventId(ScheduleEvent scheduleEvent)throws Exception{
		return this.scheduleDao.FindUserAndScheduleEventUserByEventId(scheduleEvent);
	}
	public List<?> FindFirstUser(ScheduleEvent scheduleEvent)throws Exception{
		return this.scheduleDao.FindFirstUser(scheduleEvent);
	}
	//闹钟服务
	public List<?> EventClickListByUserId(User user)throws Exception{
		List<?> list=this.scheduleDao.EventClickListByUserId(user);
		List<Map<String,Object>> list2=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++)
		{
			Map<String,Object> map=new HashMap<String,Object>();
			Object[] listItem=(Object[]) list.get(i);
			map.put("eventTime", listItem[0]);
			map.put("eventRemind",listItem[1]);
			map.put("eventRemindType", listItem[2]);
			map.put("eventId", listItem[3]);
			map.put("eventName", listItem[4]);
			list2.add(map);
		}
		return list2;
	}

	public String AddAUser(ScheduleEventUser scheduleEventUser,int userid)
			throws Exception {
		String[] s=scheduleEventUser.getEventUserList().split(",");
		int eventid=scheduleEventUser.getEventId();
		this.scheduleDao.ChangeUserToforwarding(eventid,userid);
		List<ScheduleEvent> list=this.scheduleDao.EventInfoByEventId(eventid);
		for(int i=0;i<s.length;i++){
			scheduleEventUser.setUserId(Integer.valueOf(s[i]));
			this.scheduleDao.AddUser(scheduleEventUser);
			//add by Grond Start
			if(list.size()>0){
			PushMsg pushMsg=new PushMsg();
			pushMsg.setPushmsgtime(new Date());
			pushMsg.setMsgstatus(1);//消息状态自定义：1未推送未读；2未推送已读，3已推送未读；4已推送已读；消息轮询主要是未推送的。
			if(list.get(0).getEventKind()==1)
			{
				pushMsg.setModulerid(4);//模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息;4为会议安排；	
			}
			else
			{
				pushMsg.setModulerid(1);//模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息;4为会议安排；
				
			}
			pushMsg.setMessageid(eventid);//消息内容ID，是日程安排表、请假申请表、通知公告信息表的外键
			pushMsg.setUserid(Integer.valueOf(s[i]));
			this.scheduleDao.savaObject(pushMsg);
			}
			//add by Grond End
		}
		
		return null;
	}

	public List<?> EventListByUserIdAndNowDate(User user) throws Exception 
	{		
		List<?> data =  scheduleDao.EventListByUserIdAndNowDate(user);
		/*List<Integer> messageids = new ArrayList<Integer>();
		for (int i = 0; i < data.size(); i++)
		{
			Object[] objs = (Object[]) data.get(i);
			
			ScheduleEvent event = (ScheduleEvent) objs[0];
			
			messageids.add(event.getEventId());
		}
		
		//this.pushMsgService.pushPushMsg(PushMsg.MODULERID_MEETING, user.getUserId(), messageids);*/
		
		return data;
	}
	//回复情况
	public List<?> FindFeedbackByEventId(int eventid)throws Exception{
		return this.scheduleDao.FindFeedbackByEventId(eventid);
	}

	@Override
	public String upDateRemind(ScheduleEventUser scheduleEventUser)
			throws Exception {
		this.scheduleDao.upDateRemind(scheduleEventUser);
		return null;
	}

	@Override
	public int unfeedCount(int userid,String etime) 
	{
		// TODO Auto-generated method stub
		return this.scheduleDao.unfeedCount(userid, etime);
	}
}
