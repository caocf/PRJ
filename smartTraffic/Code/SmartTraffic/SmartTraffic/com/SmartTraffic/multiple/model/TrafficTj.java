package com.SmartTraffic.multiple.model;

public class TrafficTj {
	private TrafficModel dayrecords;
	private TrafficModel monthrecords;
	private TrafficModel yearrecords;
	
	
	
	public TrafficTj() {
		super();
	}
	
	
	public TrafficTj(TrafficModel dayrecords, TrafficModel monthrecords,
			TrafficModel yearrecords) {
		super();
		this.dayrecords = dayrecords;
		this.monthrecords = monthrecords;
		this.yearrecords = yearrecords;
	}


	public TrafficModel getDayrecords() {
		return dayrecords;
	}
	public void setDayrecords(TrafficModel dayrecords) {
		this.dayrecords = dayrecords;
	}
	public TrafficModel getMonthrecords() {
		return monthrecords;
	}
	public void setMonthrecords(TrafficModel monthrecords) {
		this.monthrecords = monthrecords;
	}
	public TrafficModel getYearrecords() {
		return yearrecords;
	}
	public void setYearrecords(TrafficModel yearrecords) {
		this.yearrecords = yearrecords;
	}
	

	
 
}
