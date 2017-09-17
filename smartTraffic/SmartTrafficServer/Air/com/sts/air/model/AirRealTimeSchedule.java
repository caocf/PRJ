package com.sts.air.model;

import java.util.Date;

public class AirRealTimeSchedule 
{
	private Date leaveTime;
	private Date reachTime;
	private Date realLeaveTime;
	private Date realReachTime;
	private String airNumber;
	private String airKind;
	private String startCity;
	private String endCity;
	private String company;
	private int status;			//0：待发；1：飞行中；2：抵达；3：延迟
	
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
	public Date getRealLeaveTime() {
		return realLeaveTime;
	}
	public void setRealLeaveTime(Date realLeaveTime) {
		this.realLeaveTime = realLeaveTime;
	}
	public Date getRealReachTime() {
		return realReachTime;
	}
	public void setRealReachTime(Date realReachTime) {
		this.realReachTime = realReachTime;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
}
