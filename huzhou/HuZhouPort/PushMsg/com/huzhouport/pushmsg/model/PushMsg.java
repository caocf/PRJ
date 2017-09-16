/**
 * 
 */
package com.huzhouport.pushmsg.model;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class PushMsg implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private int pushmsgid;//  
	private int userid;//  
	private int messageid;// 消息内容ID，是日程安排表、请假申请表、通知公告信息表的外键 
	private int modulerid;//  模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息;4为会议安排； 5为违章取证;6为行业资讯
	private int msgstatus;// 消息状态自定义：1未推送未读；2未推送已读，3已推送未读；4已推送已读；消息轮询主要是未推送的。
	private Date pushmsgtime;//  
	
	
	// 用户ID 0-->推送所有的, -1-->推送内部, -2-->推送外部, >0-->具体某用户
	public static int USERID_BOTH = 0;
	public static int USERID_INNER = -1;
	public static int USERID_PUBLIC = -2;
		
	// 1未推送未读；2未推送已读，3已推送未读；4已推送已读；
	public static int MSGSTATUS_NOTPUSH_NOTREAD = 1;
	public static int MSGSTATUS_NOTPUSH_READ = 2;
	public static int MSGSTATUS_PUSHED_NOTREAD = 3;
	public static int MSGSTATUS_PUSHED_READ = 4;
		
	// 模块ID，自定义：1为来自日程安排的消息；2为来自通知公告的消息；3为来自请假申请表的消息;4为会议安排；5为违章取证;6为行业资讯
	public static int MODULERID_SCHEDULE = 1;
	public static int MODULERID_NOTIFY = 2;
	public static int MODULERID_LEAVEANDOVERTIME = 3;
	public static int MODULERID_MEETING = 4;
	public static int MODULERID_ILLEGAL = 5;
	public static int MODULERID_INDUSTRYINFO = 6;
	
	public int getPushmsgid() {
		return pushmsgid;
	}
	public void setPushmsgid(int pushmsgid) {
		this.pushmsgid = pushmsgid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getMessageid() {
		return messageid;
	}
	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}
	public int getModulerid() {
		return modulerid;
	}
	public void setModulerid(int modulerid) {
		this.modulerid = modulerid;
	}
	public int getMsgstatus() {
		return msgstatus;
	}
	public void setMsgstatus(int msgstatus) {
		this.msgstatus = msgstatus;
	}
	public Date getPushmsgtime() {
		return pushmsgtime;
	}
	public void setPushmsgtime(Date pushmsgtime) {
		this.pushmsgtime = pushmsgtime;
	}
	

}
