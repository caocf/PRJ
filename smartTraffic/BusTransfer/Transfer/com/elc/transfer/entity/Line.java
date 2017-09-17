package com.elc.transfer.entity;

import java.util.ArrayList;
import java.util.List;

public class Line {
	private int id;
	private String name;
	private int direct;
	private List<Station> stations;
	
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
	
	
	
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	public void addStations(Station s)
	{
		if(stations==null)
			stations=new ArrayList<>();
			
			stations.add(s);
	}

	
}
