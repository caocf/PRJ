package com.example.smarttraffic.smartBus.bean;

/**
 * 线路途经站点
 * @author Administrator zhou
 *
 */
public class BusStationOnLine
{
	int stationID;							//站点id
	String stationName;						//站点名称
	double lantitude;   					//站点纬度
	double longtitude;						//站点经度
	int sequence;							//站点在线路中序列
	int updown;								//上下行 1:up 2:dowm
	double lengthFromFirst;					//离始发点距离
	
	public int getStationID()
	{
		return stationID;
	}
	public void setStationID(int stationID)
	{
		this.stationID = stationID;
	}
	public String getStationName()
	{
		return stationName;
	}
	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}
	public double getLantitude()
	{
		return lantitude;
	}
	public void setLantitude(double lantitude)
	{
		this.lantitude = lantitude;
	}
	public double getLongtitude()
	{
		return longtitude;
	}
	public void setLongtitude(double longtitude)
	{
		this.longtitude = longtitude;
	}
	public int getSequence()
	{
		return sequence;
	}
	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}
	public int getUpdown()
	{
		return updown;
	}
	public void setUpdown(int updown)
	{
		this.updown = updown;
	}
	public double getLengthFromFirst()
	{
		return lengthFromFirst;
	}
	public void setLengthFromFirst(double lengthFromFirst)
	{
		this.lengthFromFirst = lengthFromFirst;
	}
}
