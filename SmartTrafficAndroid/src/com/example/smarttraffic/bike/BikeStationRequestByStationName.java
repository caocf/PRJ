package com.example.smarttraffic.bike;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;


public class BikeStationRequestByStationName extends BaseRequest
{

	private String stationName;
	public String getStationName()
	{
		return stationName;
	}
	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}

	@Override
	public PostParams CreatePostParams()
	{
		PostParams post=new PostParams();
		
		post.setUrl(HttpUrlRequestAddress.SMART_BIKE_STATION_BY_NAME_URL);
		
		Map<String, Object> m=new HashMap<String, Object>();
		m.put("stationName", stationName);
		
		post.setParams(m);
		return post;
	}
}
