package com.huzhouport.schedule.model;

public class ScheduleEventUser implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int eventId;
	private int userId;
	private int userAgree;//0:发起人，1：参加，2：不参加,3：未回复,4:转发并参与，5：转发并不参与,6：转发
	private String agreeReason;
	private String eventRemind;
	private int eventRemindType;//1:铃声2：震动3：铃声和震动
	private String eventUserList;
	public String getEventUserList() {
		return eventUserList;
	}
	public void setEventUserList(String eventUserList) {
		this.eventUserList = eventUserList;
	}
	public String getEventRemind() {
		return eventRemind;
	}
	public void setEventRemind(String eventRemind) {
		this.eventRemind = eventRemind;
	}
	public int getEventRemindType() {
		return eventRemindType;
	}
	public void setEventRemindType(int eventRemindType) {
		this.eventRemindType = eventRemindType;
	}
	public String getAgreeReason() {
		return agreeReason;
	}
	public void setAgreeReason(String agreeReason) {
		this.agreeReason = agreeReason;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserAgree() {
		return userAgree;
	}
	public void setUserAgree(int userAgree) {
		this.userAgree = userAgree;
	}
	

}
