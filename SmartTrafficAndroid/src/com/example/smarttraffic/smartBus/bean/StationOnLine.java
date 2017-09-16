package com.example.smarttraffic.smartBus.bean;

import java.io.Serializable;

public class StationOnLine implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int StationId;
	private int StationIndex;
	private String StationName;
	private double Longitude;
	private double Latitude;
	private String StartTime;
	private String EndTime;
	private String StationType;
	
	private boolean isSelect;
	
	public boolean isSelect()
	{
		return isSelect;
	}
	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
	public int getStationId() {
		return StationId;
	}
	public void setStationId(int stationId) {
		StationId = stationId;
	}
	public int getStationIndex() {
		return StationIndex;
	}
	public void setStationIndex(int stationIndex) {
		StationIndex = stationIndex;
	}
	public String getStationName() {
		return StationName;
	}
	public void setStationName(String stationName) {
		StationName = stationName;
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
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getStationType() {
		return StationType;
	}
	public void setStationType(String stationType) {
		StationType = stationType;
	}
	
	
}