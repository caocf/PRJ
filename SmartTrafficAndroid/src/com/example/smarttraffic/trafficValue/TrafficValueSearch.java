package com.example.smarttraffic.trafficValue;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.network.BaseSearch;


public class TrafficValueSearch extends BaseSearch
{
	
	@Override
	public Object parse(String data)
	{
		System.out.println(data);
		
		return JSON.parseObject(JSON.parseObject(data).getJSONObject("areaTpi").toJSONString(),TrafficValue.class);
	}

}
