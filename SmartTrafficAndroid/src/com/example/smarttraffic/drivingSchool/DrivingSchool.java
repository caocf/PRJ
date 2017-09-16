package com.example.smarttraffic.drivingSchool;

import java.io.Serializable;


public class DrivingSchool implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	private int id;
	private String name;
	private String address;
	private double distance;
	private int level;
	private String phone;
	private double lantitude;
	private double longtitude;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public int getLevel()
	{
		return level;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
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
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getLantitude()
	{
		return lantitude;
	}
	public void setLantitude(double lantitude)
	{
		this.lantitude = lantitude;
	}
	public double getLongtitude()
	{
		return longtitude;
	}
	public void setLongtitude(double longtitude)
	{
		this.longtitude = longtitude;
	}
	
}
