package com.sts.favor.model;

public class AppStationFavor 
{
	int id;
	int stationID;
	int userID;
	String stationName;
	String lines;
	
	public String getStationName() {
		return stationName;
	}
	public String getLines() {
		return lines;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public void setLines(String lines) {
		this.lines = lines;
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
