package com.huzhouport.cruiselog.model;

public class CruiseLogBean implements java.io.Serializable{
	private int cruiseLogID;
	private String cruiseLogUser;
	private String cruiseLogUserName;
	private String cruiseLogLoaction;
	private String shipName;
	private String startTime;
	private String endTime;
	private int state;
	public int getCruiseLogID() {
		return cruiseLogID;
	}
	public void setCruiseLogID(int cruiseLogID) {
		this.cruiseLogID = cruiseLogID;
	}
	public String getCruiseLogUser() {
		return cruiseLogUser;
	}
	public void setCruiseLogUser(String cruiseLogUser) {
		this.cruiseLogUser = cruiseLogUser;
	}
	public String getCruiseLogLoaction() {
		return cruiseLogLoaction;
	}
	public void setCruiseLogLoaction(String cruiseLogLoaction) {
		this.cruiseLogLoaction = cruiseLogLoaction;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCruiseLogUserName() {
		return cruiseLogUserName;
	}
	public void setCruiseLogUserName(String cruiseLogUserName) {
		this.cruiseLogUserName = cruiseLogUserName;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	
}
