package com.example.smarttraffic.taxi;

import java.io.Serializable;

public class Taxi implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double distance;
	private int jd;
	private String sbid;
	private int wd;
	
	public double getDistance()
	{
		return distance;
	}
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
	public int getJd()
	{
		return jd;
	}
	public void setJd(int jd)
	{
		this.jd = jd;
	}
	public String getSbid()
	{
		return sbid;
	}
	public void setSbid(String sbid)
	{
		this.sbid = sbid;
	}
	public int getWd()
	{
		return wd;
	}
	public void setWd(int wd)
	{
		this.wd = wd;
	}
}
