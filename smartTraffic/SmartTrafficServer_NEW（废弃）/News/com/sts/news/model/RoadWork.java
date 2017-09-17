package com.sts.news.model;

import java.sql.Date;

public class RoadWork 
{
	String workRoadName;
	String workRoadStart;
	String workRoadEnd;
	Date workRoadStartTime;
	Date workRoadEndTime;
	String degree;
	
	public String getWorkRoadName() {
		return workRoadName;
	}
	public void setWorkRoadName(String workRoadName) {
		this.workRoadName = workRoadName;
	}
	public String getWorkRoadStart() {
		return workRoadStart;
	}
	public void setWorkRoadStart(String workRoadStart) {
		this.workRoadStart = workRoadStart;
	}
	public String getWorkRoadEnd() {
		return workRoadEnd;
	}
	public void setWorkRoadEnd(String workRoadEnd) {
		this.workRoadEnd = workRoadEnd;
	}
	public Date getWorkRoadStartTime() {
		return workRoadStartTime;
	}
	public void setWorkRoadStartTime(Date workRoadStartTime) {
		this.workRoadStartTime = workRoadStartTime;
	}
	public Date getWorkRoadEndTime() {
		return workRoadEndTime;
	}
	public void setWorkRoadEndTime(Date workRoadEndTime) {
		this.workRoadEndTime = workRoadEndTime;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}

	
}
