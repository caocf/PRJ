package com.sts.breakRules.model;

import java.util.Date;

public class BreakRulesRecord 
{
	private String carID;
	private int carType;
	private Date date;
	private String addr;
	private int type;
	private String content;
	private double cutPoint;
	private double cutMoney;
	
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public int getCarType() {
		return carType;
	}
	public void setCarType(int carType) {
		this.carType = carType;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getCutPoint() {
		return cutPoint;
	}
	public void setCutPoint(double cutPoint) {
		this.cutPoint = cutPoint;
	}
	public double getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(double cutMoney) {
		this.cutMoney = cutMoney;
	}
	
}
