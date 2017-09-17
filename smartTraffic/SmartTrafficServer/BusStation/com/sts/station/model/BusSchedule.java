package com.sts.station.model;

public class BusSchedule 
{
	private int id;
	private String leaveTime;
	private String reachTime;
	private String carNumber;
	private int carKind;
	private String startStation;
	private String endStation;
	private String company;
	
	private double length;
	private String costTime;
	
	private double price;
	private int leaveTicketNumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public String getReachTime() {
		return reachTime;
	}
	public void setReachTime(String reachTime) {
		this.reachTime = reachTime;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getLeaveTicketNumber() {
		return leaveTicketNumber;
	}
	public void setLeaveTicketNumber(int leaveTicketNumber) {
		this.leaveTicketNumber = leaveTicketNumber;
	}
	public int getCarKind() {
		return carKind;
	}
	public void setCarKind(int carKind) {
		this.carKind = carKind;
	}
	public String getStartStation() {
		return startStation;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String getEndStation() {
		return endStation;
	}
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCostTime() {
		return costTime;
	}
	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}
	
	
}
