package com.example.smarttraffic.trafficValue;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;

public class TrafficValueRequest extends BaseRequest
{	
	@Override
	public String CreateUrl()
	{
		return HttpUrlRequestAddress.TRAFFIC_VALUE_URL;
	}
	
}
