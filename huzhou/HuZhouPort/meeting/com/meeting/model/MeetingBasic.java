package com.meeting.model;

import java.util.Date;

public class MeetingBasic 
{
	int id;
	int applicaionor;
	Date applytime;
	int meetingroom;
	String topic;
	Date meetingdate;
	String meetingtime;
	String tel;
	int approver;
	int officeapprover;
	int status;
	int depstatus;
	Date approvetime;
	Date depaptime;
	String approvemark;
	String depapmark;
	Integer isread;
	
	
	public Integer getIsread() {
		return isread;
	}
	public void setIsread(Integer isread) {
		this.isread = isread;
	}
	public String getApprovemark() {
		return approvemark;
	}
	public void setApprovemark(String approvemark) {
		this.approvemark = approvemark;
	}
	public String getDepapmark() {
		return depapmark;
	}
	public void setDepapmark(String depapmark) {
		this.depapmark = depapmark;
	}
	public Date getApprovetime() {
		return approvetime;
	}
	public void setApprovetime(Date approvetime) {
		this.approvetime = approvetime;
	}
	public Date getDepaptime() {
		return depaptime;
	}
	public void setDepaptime(Date depaptime) {
		this.depaptime = depaptime;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public int getId()
	{
		return id;
	}
	
	public void setApplicaionor(int applicaionor)
	{
		this.applicaionor=applicaionor;
	}
	public int getApplicaionor()
	{
		return applicaionor;
	}
	
	public void setApplytime(Date applytime)
	{
		this.applytime=applytime;
	}
	public Date getApplytime()
	{
		return applytime;
	}
	
	public void setMeetingroom(int meetingroom)
	{
		this.meetingroom=meetingroom;
	}
	public int getMeetingroom()
	{
		return meetingroom;
	}
	
	public void setApprover(int approver)
	{
		this.approver=approver;
	}
	public int getApprover()
	{
		return approver;
	}
	
	public void setOfficeapprover(int officeapprover)
	{
		this.officeapprover=officeapprover;
	}
	public int getOfficeapprover()
	{
		return officeapprover;
	}
	
	public void setStatus(int status)
	{
		this.status=status;
	}
	public int getStatus()
	{
		return status;
	}
	
	public void setDepstatus(int depstatus)
	{
		this.depstatus=depstatus;
	}
	public int getDepstatus()
	{
		return depstatus;
	}
	
	public void setTopic(String topic)
	{
		this.topic=topic;
	}
	public String getTopic()
	{
		return topic;
	}
	public void setMeetingtime(String meetingtime)
	{
		this.meetingtime=meetingtime;
	}
	public String getMeetingtime()
	{
		return meetingtime;
	}
	public void setTel(String tel)
	{
		this.tel=tel;
	}
	public String getTel()
	{
		return tel;
	}
	public void setMeetingdate(Date meetingdate)
	{
		this.meetingdate=meetingdate;
	}
	public Date getMeetingdate()
	{
		return meetingdate;
	}
	
	
}
