package com.example.smarttraffic.bike.bean;

import java.io.Serializable;

public class BikeRidingStationOnLine implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int seq;
	private int stationID;
	private String stationName;
	private String info;
	private boolean isShowInfo;
	
	
	public boolean isShowInfo()
	{
		return isShowInfo;
	}
	public void setShowInfo(boolean isShowInfo)
	{
		this.isShowInfo = isShowInfo;
	}
	public int getSeq()
	{
		return seq;
	}
	public void setSeq(int seq)
	{
		this.seq = seq;
	}
	public int getStationID()
	{
		return stationID;
	}
	public void setStationID(int stationID)
	{
		this.stationID = stationID;
	}
	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}
	public String getStationName()
	{
		return stationName;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	
	
}
