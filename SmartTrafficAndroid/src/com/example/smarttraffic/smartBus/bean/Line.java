package com.example.smarttraffic.smartBus.bean;

import java.util.List;

/**
 * 线路详情
 * @author Administrator zhou
 *
 */
public class Line
{
	int lineID;							//线路id
	String lineName;					//线路名称
	String first;						//始发站
	String last;						//终点站
	String interTime;					//发车间隔
	String price;						//价格
	String costTime;					//全程时间
	
	List<BusStationOnLine> busStationOnLines;			//途经站点

	public String getLineName()
	{
		return lineName;
	}

	public void setLineName(String lineName)
	{
		this.lineName = lineName;
	}

	public String getFirst()
	{
		return first;
	}

	public void setFirst(String first)
	{
		this.first = first;
	}

	public String getLast()
	{
		return last;
	}

	public void setLast(String last)
	{
		this.last = last;
	}

	public String getInterTime()
	{
		return interTime;
	}

	public void setInterTime(String interTime)
	{
		this.interTime = interTime;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	

	public String getCostTime()
	{
		return costTime;
	}

	public void setCostTime(String costTime)
	{
		this.costTime = costTime;
	}

	public List<BusStationOnLine> getBusStationOnLines()
	{
		return busStationOnLines;
	}

	public void setBusStationOnLines(List<BusStationOnLine> busStationOnLines)
	{
		this.busStationOnLines = busStationOnLines;
	}

	public int getLineID()
	{
		return lineID;
	}

	public void setLineID(int lineID)
	{
		this.lineID = lineID;
	}
	
	
}
