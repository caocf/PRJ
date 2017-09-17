package com.sts.favor.model;

public class AppBikeStationFavor 
{
	int id;
	int stationID;
	int userID;
	String stationName;
	String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationName() {
		return stationName;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStationID() {
		return stationID;
	}
	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
}
