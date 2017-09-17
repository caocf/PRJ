package com.sts.smartbus.model;

public class LineForStation extends BusLineForQueryByName
{
	double distance;
	int crowed;				//1:正常；2：拥挤 
	int speed;				//1：正常；2：缓慢行驶
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getCrowed() {
		return crowed;
	}
	public void setCrowed(int crowed) {
		this.crowed = crowed;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
}
