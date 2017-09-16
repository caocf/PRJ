package com.example.smarttraffic.smartBus.parse;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByNameTemp;
import com.example.smarttraffic.util.DoString;

/**
 * 站点建议解析
 * @author Administrator zwc
 *
 */
public class StationSuggestSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{					
		String data1=DoString.parseThreeNetString(data);
		
		BusStationForQueryByNameTemp result=JSON.parseObject(data1, BusStationForQueryByNameTemp.class);
		
		return result;
	}
}
