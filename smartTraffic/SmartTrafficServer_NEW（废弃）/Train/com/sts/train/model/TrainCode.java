package com.sts.train.model;

import java.util.Date;

public class TrainCode 
{
	String trainNo;
	String station;
	Date planStart;
	Date planEnd;
	Date realStart;
	Date realReach;
	int retain;
	double length;
	
	
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
	public Date getPlanStart() {
		return planStart;
	}
	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}
	public Date getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}
	public Date getRealStart() {
		return realStart;
	}
	public void setRealStart(Date realStart) {
		this.realStart = realStart;
	}
	public Date getRealReach() {
		return realReach;
	}
	public void setRealReach(Date realReach) {
		this.realReach = realReach;
	}
	public int getRetain() {
		return retain;
	}
	public void setRetain(int retain) {
		this.retain = retain;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	
	
}
