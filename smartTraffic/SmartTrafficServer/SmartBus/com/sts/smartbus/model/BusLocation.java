package com.sts.smartbus.model;

public class BusLocation 
{
	private int StationId;
	private String StationName;
	private int Distance;
	private int StationIndex;
	private String CarNumber;
	private int CongestionDegree;
	private int DriveState;
	public int getStationId() {
		return StationId;
	}
	public void setStationId(int stationId) {
		StationId = stationId;
	}
	public String getStationName() {
		return StationName;
	}
	public void setStationName(String stationName) {
		StationName = stationName;
	}
	public int getDistance() {
		return Distance;
	}
	public void setDistance(int distance) {
		Distance = distance;
	}
	public int getStationIndex() {
		return StationIndex;
	}
	public void setStationIndex(int stationIndex) {
		StationIndex = stationIndex;
	}
	public String getCarNumber() {
		return CarNumber;
	}
	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}
	public int getCongestionDegree() {
		return CongestionDegree;
	}
	public void setCongestionDegree(int congestionDegree) {
		CongestionDegree = congestionDegree;
	}
	public int getDriveState() {
		return DriveState;
	}
	public void setDriveState(int driveState) {
		DriveState = driveState;
	}
}
