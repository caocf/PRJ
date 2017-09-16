package com.example.smarttraffic.tripPlan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 出行规划分段信息
 * @author Administrator zhou
 *
 */
public class TripListInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private double lenght;
	private String time;
	private String start;
	private String end;
	private int lan1;
	private int lon1;
	private int lan2;
	private int lon2;
	
	private List<String> lineData;

	public double getLenght()
	{
		return lenght;
	}
	public void setLenght(double lenght)
	{
		this.lenght = lenght;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public List<String> getLineData()
	{
		return lineData;
	}
	public void setLineData(List<String> lineData)
	{
		this.lineData = lineData;
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
	public int getLan1()
	{
		return lan1;
	}
	public void setLan1(int lan1)
	{
		this.lan1 = lan1;
	}
	public int getLon1()
	{
		return lon1;
	}
	public void setLon1(int lon1)
	{
		this.lon1 = lon1;
	}
	public int getLan2()
	{
		return lan2;
	}
	public void setLan2(int lan2)
	{
		this.lan2 = lan2;
	}
	public int getLon2()
	{
		return lon2;
	}
	public void setLon2(int lon2)
	{
		this.lon2 = lon2;
	}
	
}
