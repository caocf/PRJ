package com.example.smarttraffic.airport;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

public class AirSuggestRequest extends BaseRequest
{
	private String cityName;

	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}
	
	private final String CITY_NAME="cityName";
	
	@Override
	public PostParams CreatePostParams()
	{
		PostParams result=new PostParams();
		
		result.setUrl(HttpUrlRequestAddress.AIR_REQUEST_STATION_SUGGESTION_URL);
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put(CITY_NAME, cityName);
		result.setParams(paramMap);
		
		return result;
	}
}
