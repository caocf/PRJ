package com.example.smarttraffic.weather;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;

/**
 * 请求天气信息
 * @author Administrator zhou
 *
 */
public class WeatherRequest extends BaseRequest
{
	@Override
	public String CreateUrl()
	{
		return HttpUrlRequestAddress.WEATHER_BAIDU_URL;
	}
	
}
