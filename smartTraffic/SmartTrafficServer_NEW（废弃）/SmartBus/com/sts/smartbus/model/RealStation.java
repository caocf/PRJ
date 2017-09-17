package com.sts.smartbus.model;

import java.util.List;

public class RealStation 
{
	String stationName;
	String lineName;
	int orient;
	
	List<RealInfo> realInfos;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getOrient() {
		return orient;
	}

	public void setOrient(int orient) {
		this.orient = orient;
	}

	public List<RealInfo> getRealInfos() {
		return realInfos;
	}

	public void setRealInfos(List<RealInfo> realInfos) {
		this.realInfos = realInfos;
	}
	
}

