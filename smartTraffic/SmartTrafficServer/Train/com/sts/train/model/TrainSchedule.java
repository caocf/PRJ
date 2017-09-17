package com.sts.train.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrainSchedule implements Serializable
{
	
	public static final int END_POINT=0;
	public static final int CROSS_POINT=1;
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date leaveTime;
	private Date  reachTime;
	private String trainNumber;
	private int trainKind;
	private String startCity;
	private String endCity;
	private String firstCity;
	private String lastCity;
	private int startKind;
	private int endKind;
	
	private double length;	
	private String costTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLeaveTimeShort()
	{
		return new SimpleDateFormat("hh:mm:ss").format(leaveTime);
	}
	
	public String getReachTimeShort() {
		return new SimpleDateFormat("hh:mm:ss").format(reachTime);
	}
	public Date getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public Date getReachTime() {
		return reachTime;
	}
	public void setReachTime(Date reachTime) {
		this.reachTime = reachTime;
	}
	public String getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
	public int getTrainKind() {
		return trainKind;
	}
	public void setTrainKind(int trainKind) {
		this.trainKind = trainKind;
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
	public String getFirstCity() {
		return firstCity;
	}
	public void setFirstCity(String firstCity) {
		this.firstCity = firstCity;
	}
	public String getLastCity() {
		return lastCity;
	}
	public void setLastCity(String lastCity) {
		this.lastCity = lastCity;
	}
	public int getStartKind() {
		return startKind;
	}
	public void setStartKind(int startKind) {
		this.startKind = startKind;
	}
	public int getEndKind() {
		return endKind;
	}
	public void setEndKind(int endKind) {
		this.endKind = endKind;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public String getCostTime() {
		return costTime;
	}
	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}
	
	
}
