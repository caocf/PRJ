package com.example.smarttraffic.bike.bean;

import java.io.Serializable;

public class BikeStation implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Id;
	private String Name;
	private String AreaName;
	private int Count;
	private String Address;
	private String ServiceTime;
	private boolean IsAllDay;
	private boolean PersonDuty;
	private String StationPhone;
	private double Longitude;
	private double Latitude;
	private int Distance;
	private int left;
	private int favorID;
	private boolean isSelect;
	
	public boolean isSelect()
	{
		return isSelect;
	}
	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
	
	public int getFavorID()
	{
		return favorID;
	}
	public void setFavorID(int favorID)
	{
		this.favorID = favorID;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getAreaName() {
		return AreaName;
	}
	public void setAreaName(String areaName) {
		AreaName = areaName;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getServiceTime() {
		return ServiceTime;
	}
	public void setServiceTime(String serviceTime) {
		ServiceTime = serviceTime;
	}
	public boolean isIsAllDay() {
		return IsAllDay;
	}
	public void setIsAllDay(boolean isAllDay) {
		IsAllDay = isAllDay;
	}
	public boolean isPersonDuty() {
		return PersonDuty;
	}
	public void setPersonDuty(boolean personDuty) {
		PersonDuty = personDuty;
	}
	public String getStationPhone() {
		return StationPhone;
	}
	public void setStationPhone(String stationPhone) {
		StationPhone = stationPhone;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public int getDistance() {
		return Distance;
	}
	public void setDistance(int distance) {
		Distance = distance;
	}
	public int getLeft()
	{
		return left;
	}
	public void setLeft(int left)
	{
		this.left = left;
	}
	public int getBorrowed()
	{
		return Count-left;
	}
	
	
}
