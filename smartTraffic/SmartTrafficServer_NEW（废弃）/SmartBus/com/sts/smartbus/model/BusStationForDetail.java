package com.sts.smartbus.model;

import java.util.List;

public class BusStationForDetail 
{
	private BusStation Station;
	private List<BusRealLine> LineList;

	public BusStation getStation() {
		return Station;
	}
	public void setStation(BusStation station) {
		Station = station;
	}
	public List<BusRealLine> getLineList() {
		return LineList;
	}
	public void setLineList(List<BusRealLine> lineList) {
		LineList = lineList;
	}
	
	
}
