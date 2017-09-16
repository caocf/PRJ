package com.example.smarttraffic.tripPlan.bean;

import java.io.Serializable;
import java.util.Calendar;


/**
 * 出行规划请求信息类
 * @author Administrator zhou
 *
 */
public class CommonDrivingPlan implements Serializable
{
	private static final long serialVersionUID = 2L;
		
	
	private String start;
	private String end;
	private int lan1;
	private int lan2;
	private int lon1;
	private int lon2;
	private int strategy;
	
	private Calendar calendar;

	
	public int getLan1()
	{
		return lan1;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Calendar getCalendar()
	{
		return calendar;
	}
	public void setCalendar(Calendar calendar)
	{
		this.calendar = calendar;
	}
	public void setLan1(int lan1)
	{
		this.lan1 = lan1;
	}
	public int getLan2()
	{
		return lan2;
	}
	public void setLan2(int lan2)
	{
		this.lan2 = lan2;
	}
	public int getLon1()
	{
		return lon1;
	}
	public void setLon1(int lon1)
	{
		this.lon1 = lon1;
	}
	public int getLon2()
	{
		return lon2;
	}
	public void setLon2(int lon2)
	{
		this.lon2 = lon2;
	}
	public int getStrategy()
	{
		return strategy;
	}
	public void setStrategy(int strategy)
	{
		this.strategy = strategy;
	}
	
}
