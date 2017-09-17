package com.sts.smartbus.model;

import java.util.List;

public class Station 
{
	int id;
	String name;
	String address;
	int orient;          // 1:上行 2：下行
	
	double lantitude;
	double longtitude;
	
	List<LineForStation> lineForStations;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getOrient() {
		return orient;
	}

	public void setOrient(int orient) {
		this.orient = orient;
	}

	public double getLantitude() {
		return lantitude;
	}

	public void setLantitude(double lantitude) {
		this.lantitude = lantitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public List<LineForStation> getLineForStations() {
		return lineForStations;
	}

	public void setLineForStations(List<LineForStation> lineForStations) {
		this.lineForStations = lineForStations;
	}
	
	
	
}
