package com.sts.smartbus.model;

public class BusLineForDetail 
{
	private BusLine Line;
	private StationOnLine DownList;
	private StationOnLine UpList;
	
	public BusLine getLine() {
		return Line;
	}
	public void setLine(BusLine line) {
		Line = line;
	}
	public StationOnLine getDownList() {
		return DownList;
	}
	public void setDownList(StationOnLine downList) {
		DownList = downList;
	}
	public StationOnLine getUpList() {
		return UpList;
	}
	public void setUpList(StationOnLine upList) {
		UpList = upList;
	}
	
	
}
