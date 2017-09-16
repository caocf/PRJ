package com.example.smarttraffic.busStation;


public class Ticket 
{
	private int id;
	private String leaveTime;
	private String reachTime;
	private String carNumber;
	private String carKind;
	private String startCity;
	private String endCity;
	
	private double length;
	
	private double price;
	private int leaveTicketNumber;
	public int getId() {
		return id;
	}
	
	public String getReachTime() {
		return reachTime;
	}

	public void setReachTime(String reachTime) {
		this.reachTime = reachTime;
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
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCarKind() {
		return carKind;
	}
	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
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
	
	
	
}
