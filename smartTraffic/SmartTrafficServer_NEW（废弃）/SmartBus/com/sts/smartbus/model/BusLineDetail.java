package com.sts.smartbus.model;

import java.util.List;

public class BusLineDetail 
{
	BusLine Line;
	List<StationOnLine> DownList;
	List<StationOnLine> UpList;
	public BusLine getLine() {
		return Line;
	}
	public void setLine(BusLine line) {
		Line = line;
	}
	public List<StationOnLine> getDownList() {
		return DownList;
	}
	public void setDownList(List<StationOnLine> downList) {
		DownList = downList;
	}
	public List<StationOnLine> getUpList() {
		return UpList;
	}
	public void setUpList(List<StationOnLine> upList) {
		UpList = upList;
	}
	
	
}
