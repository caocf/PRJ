package com.example.smarttraffic.airport;

/**
 * 航班正晚点信息
 * @author Administrator zhou
 *
 */
public class Air
{
	String trainNo;
	String station;
	String planStart;
	String planEnd;
	String realStart;
	String realReach;
	String retain;
	
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public String getPlanStart() {
		return planStart;
	}
	public void setPlanStart(String planStart) {
		this.planStart = planStart;
	}
	public String getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(String planEnd) {
		this.planEnd = planEnd;
	}
	public String getRealStart() {
		return realStart;
	}
	public void setRealStart(String realStart) {
		this.realStart = realStart;
	}
	public String getRealReach() {
		return realReach;
	}
	public void setRealReach(String realReach) {
		this.realReach = realReach;
	}
	public String getRetain() {
		return retain;
	}
	public void setRetain(String retain) {
		this.retain = retain;
	}	
}
