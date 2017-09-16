package com.example.smarttraffic.airport;


public class AirTickets 
{
	private int id;
	private String leaveTime;
	private String reachTime;
	private String airNumber;
	private String airKind;
	private String startCity;
	private String endCity;
	private String company;
	private long time;
	private int economy;
	private int business;
	private int top;
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
	public String getAirNumber() {
		return airNumber;
	}
	public void setAirNumber(String airNumber) {
		this.airNumber = airNumber;
	}
	public String getAirKind() {
		return airKind;
	}
	public void setAirKind(String airKind) {
		this.airKind = airKind;
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
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getEconomy() {
		return economy;
	}
	public void setEconomy(int economy) {
		this.economy = economy;
	}
	public int getBusiness() {
		return business;
	}
	public void setBusiness(int business) {
		this.business = business;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public String getCompany()
	{
		return company;
	}
	public void setCompany(String company)
	{
		this.company = company;
	}
	
	
}

