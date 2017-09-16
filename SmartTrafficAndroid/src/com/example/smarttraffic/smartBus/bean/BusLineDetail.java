package com.example.smarttraffic.smartBus.bean;

import java.io.Serializable;
import java.util.List;

public class BusLineDetail implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
