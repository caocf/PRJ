package com.sts.smartbus.model;

public class Trace 
{
	int id;
	int lineID;
	int direct;
	double lan;
	double lon;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLineID() {
		return lineID;
	}
	public void setLineID(int lineID) {
		this.lineID = lineID;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public double getLan() {
		return lan;
	}
	public void setLan(double lan) {
		this.lan = lan;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	
}
