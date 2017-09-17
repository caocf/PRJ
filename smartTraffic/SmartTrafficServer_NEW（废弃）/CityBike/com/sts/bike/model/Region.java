package com.sts.bike.model;

import java.util.List;

public class Region 
{
	private int regionID;
	private String regionName;
	private List<String> hotPoint;
	
	public int getRegionID() {
		return regionID;
	}
	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}
	
	public List<String> getHotPoint() {
		return hotPoint;
	}
	public void setHotPoint(List<String> hotPoint) {
		this.hotPoint = hotPoint;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
}
