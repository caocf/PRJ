package com.example.smarttraffic.parking;

import java.io.Serializable;

public class Parking implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String parkpointid;
	private String chargemode;
	private int freecount;
	private double gpsla;
	private double gpslo;
	private int parklotcount;
	private int parktype;
	private String pointname;
	private String roadname;
	private String updateTime;
	private double distance;
	
	public double getDistance()
	{
		return distance;
	}
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
	public String getParkpointid()
	{
		return parkpointid;
	}
	public void setParkpointid(String parkpointid)
	{
		this.parkpointid = parkpointid;
	}
	public String getChargemode()
	{
		return chargemode;
	}
	public void setChargemode(String chargemode)
	{
		this.chargemode = chargemode;
	}
	public int getFreecount()
	{
		return freecount;
	}
	public void setFreecount(int freecount)
	{
		this.freecount = freecount;
	}
	public double getGpsla()
	{
		return gpsla;
	}
	public void setGpsla(double gpsla)
	{
		this.gpsla = gpsla;
	}
	public double getGpslo()
	{
		return gpslo;
	}
	public void setGpslo(double gpslo)
	{
		this.gpslo = gpslo;
	}
	public int getParklotcount()
	{
		return parklotcount;
	}
	public void setParklotcount(int parklotcount)
	{
		this.parklotcount = parklotcount;
	}
	public int getParktype()
	{
		return parktype;
	}
	public void setParktype(int parktype)
	{
		this.parktype = parktype;
	}
	public String getPointname()
	{
		return pointname;
	}
	public void setPointname(String pointname)
	{
		this.pointname = pointname;
	}
	public String getRoadname()
	{
		return roadname;
	}
	public void setRoadname(String roadname)
	{
		this.roadname = roadname;
	}
	public String getUpdateTime()
	{
		return updateTime;
	}
	public void setUpdateTime(String updateTime)
	{
		this.updateTime = updateTime;
	}
}
