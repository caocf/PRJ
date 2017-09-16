package com.example.smarttraffic.smartBus.bean;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.map.PointTraslation;

import cennavi.cenmapsdk.android.GeoPoint;


/**
 * 换乘线路
 * @author Administrator zhou
 *
 */
public class TransferLine
{
	private String direction;
	private double distance;
	private double lan;
	private double lon;
	private int stationID;
	private String stationName;
	private String time;
	private int type;
	private TransferLineDetail lineDetails;
	private List<TransferLineTrails> lineTrails;
	private String bikeStationAddress;
	private int stationType;
	
	public List<GeoPoint> getGeoPoints()
	{
		List<GeoPoint> result=new ArrayList<GeoPoint>();
		
		for(int i=0;i<lineTrails.size();i++)
			result.add(PointTraslation.transform(new GeoPoint(lineTrails.get(i).getLantitude(),lineTrails.get(i).getLongtitude())));
		
		return result;
		
	}
	
	public int getStationType()
	{
		return stationType;
	}
	public void setStationType(int stationType)
	{
		this.stationType = stationType;
	}
	public String getBikeStationAddress()
	{
		return bikeStationAddress;
	}
	public void setBikeStationAddress(String bikeStationAddress)
	{
		this.bikeStationAddress = bikeStationAddress;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getType()
	{
		return type;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public String getTime()
	{
		return time;
	}
	public String getDirection()
	{
		return direction;
	}
	public void setDirection(String direction)
	{
		this.direction = direction;
	}
	public double getDistance()
	{
		return distance;
	}
	public void setDistance(double distance)
	{
		this.distance = distance;
	}
	public double getLan()
	{
		return lan;
	}
	public void setLan(double lan)
	{
		this.lan = lan;
	}
	public double getLon()
	{
		return lon;
	}
	public void setLon(double lon)
	{
		this.lon = lon;
	}
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
	public TransferLineDetail getLineDetails()
	{
		return lineDetails;
	}
	public void setLineDetails(TransferLineDetail lineDetails)
	{
		this.lineDetails = lineDetails;
	}
	public List<TransferLineTrails> getLineTrails()
	{
		return lineTrails;
	}
	public void setLineTrails(List<TransferLineTrails> lineTrails)
	{
		this.lineTrails = lineTrails;
	}
	
	
	
}

