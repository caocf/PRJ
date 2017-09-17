package com.sts.parkingdb.model;

public class ParkingDBReal 
{
	private String parkpointid;
	private int freecount;
	private String updateTime;
	
	public String getParkpointid() {
		return parkpointid;
	}
	public void setParkpointid(String parkpointid) {
		this.parkpointid = parkpointid;
	}
	public int getFreecount() {
		return freecount;
	}
	public void setFreecount(int freecount) {
		this.freecount = freecount;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
