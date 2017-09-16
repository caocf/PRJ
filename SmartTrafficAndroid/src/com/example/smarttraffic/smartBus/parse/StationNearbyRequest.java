package com.example.smarttraffic.smartBus.parse;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;

/**
 * 站点周边请求类
 * 
 * @author Administrator zwc
 * 
 */
public class StationNearbyRequest extends BaseRequest
{
	public StationNearbyRequest(double lan, double lon, int distance)
	{
		this.lan = lan;
		this.lon = lon;
		this.distance = distance;
	}

	private double lan;
	private double lon;
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
		return HttpUrlRequestAddress.SMART_BUS_STATION_NEARBY_STATION_URL
				+ "?longtitude=" + lon + "&lantitude=" + lan + "&distance="
				+ distance;
	}
}
