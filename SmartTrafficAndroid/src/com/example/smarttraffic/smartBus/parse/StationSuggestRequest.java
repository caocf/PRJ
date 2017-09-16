package com.example.smarttraffic.smartBus.parse;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * 站点建议请求
 * @author Administrator zwc
 *
 */
public class StationSuggestRequest extends BaseRequest
{
	public StationSuggestRequest(String name)
	{
		this.stationName=name;
	}
	
	private String stationName;
	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}
	
	@Override
	public PostParams CreatePostParams()
	{
		PostParams postParams=new PostParams();
		postParams.setUrl(HttpUrlRequestAddress.SMART_BUS_STATION_SUUGESTION_URL);
		Map<String, Object> d=new HashMap<String, Object>();
		d.put("stationName", stationName);
		postParams.setParams(d);
		
		return postParams;				
	}

}
