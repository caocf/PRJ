package com.hxkg.meeting;

import java.io.Serializable;

public class MeetingRecord implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String username;
	String topic;
	String tel;
	int status;
	String date;
	String time;
	String approvetime;
	String depaptime;
	String approvemark;
	String depapmark;
	int approveid;
	String approvername;
	int officeid;
	int isread;
	
	public int getIsread() {
		return isread;
	}
	public void setIsread(int isread) {
		this.isread = isread;
	}
	public int getOfficeid() {
		return officeid;
	}
	public void setOfficeid(int officeid) {
		this.officeid = officeid;
	}
	int statusShow;
	
	public int getStatusShow() {
		return statusShow;
	}
	public void setStatusShow(int statusShow) {
		this.statusShow = statusShow;
	}
	public int getApproveid() {
		return approveid;
	}
	public void setApproveid(int approveid) {
		this.approveid = approveid;
	}
	public String getApprovername() {
		return approvername;
	}
	public void setApprovername(String approvername) {
		this.approvername = approvername;
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
	public String getApprovetime() {
		return approvetime;
	}
	public void setApprovetime(String approvetime) {
		this.approvetime = approvetime;
	}
	public String getDepaptime() {
		return depaptime;
	}
	public void setDepaptime(String depaptime) {
		this.depaptime = depaptime;
	}
	public int getDepstatus() {
		return depstatus;
	}
	public void setDepstatus(int depstatus) {
		this.depstatus = depstatus;
	}
	String roomname;
	int roomid;
	int depstatus;
	
	public int getRoomid() {
		return roomid;
	}
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	String aptime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getAptime() {
		return aptime;
	}
	public void setAptime(String aptime) {
		this.aptime = aptime;
	}
	
}
