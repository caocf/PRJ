package com.example.smarttraffic.bike;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.bike.bean.BikeStationTemp;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.util.DoString;

public class BikeStationSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		BikeStationTemp result=JSON.parseObject(DoString.parseThreeNetString(data),BikeStationTemp.class);
		
		return result.getStationList();
	}
}
