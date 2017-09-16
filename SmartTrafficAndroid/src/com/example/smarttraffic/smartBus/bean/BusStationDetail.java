package com.example.smarttraffic.smartBus.bean;

import java.util.List;

public class BusStationDetail 
{
	BusStation Station;
	List<LineOnStation> LineList;
	public BusStation getStation() {
		return Station;
	}
	public void setStation(BusStation station) {
		Station = station;
	}
	public List<LineOnStation> getLineList() {
		return LineList;
	}
	public void setLineList(List<LineOnStation> lineList) {
		LineList = lineList;
	}
	
}
