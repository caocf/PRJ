package com.huzhouport.schedule.model;

public class ScheduleKind implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private int scheduleKindId;
	private String scheduleKindName;
	
	public int getScheduleKindId() {
		return scheduleKindId;
	}
	public void setScheduleKindId(int scheduleKindId) {
		this.scheduleKindId = scheduleKindId;
	}
	public String getScheduleKindName() {
		return scheduleKindName;
	}
	public void setScheduleKindName(String scheduleKindName) {
		this.scheduleKindName = scheduleKindName;
	}
	
}
