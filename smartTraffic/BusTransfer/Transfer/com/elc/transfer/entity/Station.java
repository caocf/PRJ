package com.elc.transfer.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class Station {
	private int id;
	private String name;
	private double lan;
	private double lon;
	
	private List<Line> lines;
	private List<Station> nearbyStations;

	
	public List<Station> getNearbyStations() {
		return nearbyStations;
	}
	public void setNearbyStations(List<Station> nearbyStations) {
		this.nearbyStations = nearbyStations;
	}
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

	public List<Line> getLines() {
		return lines==null?new ArrayList<Line>():lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	public double getLan() {
		return lan;
	}
	public double getLon() {
		return lon;
	}
	public void setLan(double lan) {
		this.lan = lan;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public void addLine(Line l)
	{
		if(lines==null)
			lines=new ArrayList<>();
			
		lines.add(l);
	}
}
