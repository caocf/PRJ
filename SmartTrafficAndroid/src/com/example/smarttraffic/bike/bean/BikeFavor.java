package com.example.smarttraffic.bike.bean;

public class BikeFavor
{
	String start;
	String end;
	double lan1;
	double lan2;
	double lon1;
	double lon2;
	
	int favorID;
	
	int time;
	int length;
	
	boolean isSelect;
	
	public boolean isSelect()
	{
		return isSelect;
	}
	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}
	public String getStart()
	{
		return start;
	}
	public void setStart(String start)
	{
		this.start = start;
	}
	public String getEnd()
	{
		return end;
	}
	public void setEnd(String end)
	{
		this.end = end;
	}
	public double getLan1()
	{
		return lan1;
	}
	public void setLan1(double lan1)
	{
		this.lan1 = lan1;
	}
	public double getLan2()
	{
		return lan2;
	}
	public void setLan2(double lan2)
	{
		this.lan2 = lan2;
	}
	public double getLon1()
	{
		return lon1;
	}
	public void setLon1(double lon1)
	{
		this.lon1 = lon1;
	}
	public double getLon2()
	{
		return lon2;
	}
	public void setLon2(double lon2)
	{
		this.lon2 = lon2;
	}
	public int getFavorID()
	{
		return favorID;
	}
	public void setFavorID(int favorID)
	{
		this.favorID = favorID;
	}
	public int getTime()
	{
		return time;
	}
	public void setTime(int time)
	{
		this.time = time;
	}
	public int getLength()
	{
		return length;
	}
	public void setLength(int length)
	{
		this.length = length;
	}
	
	
}
