package com.example.smarttraffic.bike;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;

public class BikeStationRequestByLocation extends BaseRequest
{
	public BikeStationRequestByLocation()
	{
		count=10000;
		distance=999999999;
	}

	private double lan;
	private double lon;
	private int count;
	private int distance;
		
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

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getDistance()
	{
		return distance;
	}

	public void setDistance(int distance)
	{
		this.distance = distance;
	}


	@Override
	public String CreateUrl()
	{
		return HttpUrlRequestAddress.SMART_BUS_STATION_NEARBY_BIKE_URL+"?longtitude="+lon+"&lantitude="+lan+"&distance="+distance+"&count="+count;
	}
}
