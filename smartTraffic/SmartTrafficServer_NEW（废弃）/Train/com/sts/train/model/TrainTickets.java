package com.sts.train.model;

public class TrainTickets 
{
	private String TrainNo;
	private String startCity;
	private String firstCity;
	private String startTime;
	private String endCity;
	private String lastCity;
	private String endTime;
	private String costTime;
	private double firPrice;
	private double secPrice;
	private double busPrice;
	private int secLeftNum;
	private int busLeftNum;
	private int firLeftNum;
	public String getTrainNo()
	{
		return TrainNo;
	}
	public void setTrainNo(String trainNo)
	{
		TrainNo = trainNo;
	}
	public String getStartCity()
	{
		return startCity;
	}
	public void setStartCity(String startCity)
	{
		this.startCity = startCity;
	}
	public String getFirstCity()
	{
		return firstCity;
	}
	public void setFirstCity(String firstCity)
	{
		this.firstCity = firstCity;
	}
	public String getStartTime()
	{
		return startTime;
	}
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
	public String getEndCity()
	{
		return endCity;
	}
	public void setEndCity(String endCity)
	{
		this.endCity = endCity;
	}
	public String getLastCity()
	{
		return lastCity;
	}
	public void setLastCity(String lastCity)
	{
		this.lastCity = lastCity;
	}
	public String getEndTime()
	{
		return endTime;
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	public String getCostTime()
	{
		return costTime;
	}
	public void setCostTime(String costTime)
	{
		this.costTime = costTime;
	}
	public double getFirPrice()
	{
		return firPrice;
	}
	public void setFirPrice(double firPrice)
	{
		this.firPrice = firPrice;
	}
	public double getSecPrice()
	{
		return secPrice;
	}
	public void setSecPrice(double secPrice)
	{
		this.secPrice = secPrice;
	}
	public double getBusPrice()
	{
		return busPrice;
	}
	public void setBusPrice(double busPrice)
	{
		this.busPrice = busPrice;
	}
	public int getSecLeftNum()
	{
		return secLeftNum;
	}
	public void setSecLeftNum(int secLeftNum)
	{
		this.secLeftNum = secLeftNum;
	}
	public int getBusLeftNum()
	{
		return busLeftNum;
	}
	public void setBusLeftNum(int busLeftNum)
	{
		this.busLeftNum = busLeftNum;
	}
	public int getFirLeftNum()
	{
		return firLeftNum;
	}
	public void setFirLeftNum(int firLeftNum)
	{
		this.firLeftNum = firLeftNum;
	}
}
