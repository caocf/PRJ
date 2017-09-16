package com.huzhouport.schedule.model;

public class ScheduleEvent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int eventId;
	private String eventName;
	private int eventKind;
	private String eventTime;
	private String eventLocation;
	private String eventContent;
	
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public int getEventKind() {
		return eventKind;
	}
	public void setEventKind(int eventKind) {
		this.eventKind = eventKind;
	}
	
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public String getEventContent() {
		return eventContent;
	}
	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}
	
	
}