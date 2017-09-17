package com.sts.favor.model;

public class AppTransferFavor 
{
	int id;
	int userID;
	double startLantitude;
	double startLongtitude;
	double endLantitude;
	double endLongtitude;
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
	public double getStartLantitude() {
		return startLantitude;
	}
	public void setStartLantitude(double startLantitude) {
		this.startLantitude = startLantitude;
	}
	public double getStartLongtitude() {
		return startLongtitude;
	}
	public void setStartLongtitude(double startLongtitude) {
		this.startLongtitude = startLongtitude;
	}
	public double getEndLantitude() {
		return endLantitude;
	}
	public void setEndLantitude(double endLantitude) {
		this.endLantitude = endLantitude;
	}
	public double getEndLongtitude() {
		return endLongtitude;
	}
	public void setEndLongtitude(double endLongtitude) {
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
