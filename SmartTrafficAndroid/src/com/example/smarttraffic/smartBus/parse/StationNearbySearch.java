package com.example.smarttraffic.smartBus.parse;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByNameTemp;
import com.example.smarttraffic.util.DoString;

/**
 * 站点周边解析类
 * @author Administrator zwc
 *
 */
public class StationNearbySearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		String data1 = DoString.parseThreeNetString(data);

		BusStationForQueryByNameTemp result = JSON.parseObject(data1,
				BusStationForQueryByNameTemp.class);

		return result;
	}
}
