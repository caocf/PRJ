package com.sts.air.model;

import java.util.Date;

public class AirSchedule 
{
	private Date leaveTime;
	private Date reachTime;
	private String airNumber;
	private String airKind;
	private String startCity;
	private String endCity;
	private long time;
	private String company;
	
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public Date getReachTime() {
		return reachTime;
	}
	public void setReachTime(Date reachTime) {
		this.reachTime = reachTime;
	}
	public String getAirNumber() {
		return airNumber;
	}
	public void setAirNumber(String airNumber) {
		this.airNumber = airNumber;
	}
	public String getAirKind() {
		return airKind;
	}
	public void setAirKind(String airKind) {
		this.airKind = airKind;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
}
