package com.sts.station.model;

import java.util.List;

public class BusCode 
{
	String busNo;
	String startCity;
	String endCity;
	String startStation;
	String endStation;
	
	String company;
	
	List<BusOrder> orders;
	
	public String getBusNo() {
		return busNo;
	}

	public void setBusNo(String busNo) {
		this.busNo = busNo;
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

	

	public List<BusOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<BusOrder> orders) {
		this.orders = orders;
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

}
