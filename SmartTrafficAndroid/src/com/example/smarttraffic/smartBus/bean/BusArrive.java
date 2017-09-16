package com.example.smarttraffic.smartBus.bean;
import java.util.List;

public class BusArrive 
{
	private String Time;
	private List<BusLocation> BusLocationList;
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public List<BusLocation> getBusLocationList() {
		return BusLocationList;
	}
	public void setBusLocationList(List<BusLocation> busLocationList) {
		BusLocationList = busLocationList;
	}
	
	
}
