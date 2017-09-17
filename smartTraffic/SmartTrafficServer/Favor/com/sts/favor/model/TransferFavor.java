package com.sts.favor.model;

public class TransferFavor 
{
	int id;
	int userID;
	String startLantitude;
	String startLongtitude;
	String endLantitude;
	String endLongtitude;
	String startName;
	String endName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getStartLantitude() {
		return startLantitude;
	}
	public void setStartLantitude(String startLantitude) {
		this.startLantitude = startLantitude;
	}
	public String getStartLongtitude() {
		return startLongtitude;
	}
	public void setStartLongtitude(String startLongtitude) {
		this.startLongtitude = startLongtitude;
	}
	public String getEndLantitude() {
		return endLantitude;
	}
	public void setEndLantitude(String endLantitude) {
		this.endLantitude = endLantitude;
	}
	public String getEndLongtitude() {
		return endLongtitude;
	}
	public void setEndLongtitude(String endLongtitude) {
		this.endLongtitude = endLongtitude;
	}
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	public String getEndName() {
		return endName;
	}
	public void setEndName(String endName) {
		this.endName = endName;
	}
	
}
