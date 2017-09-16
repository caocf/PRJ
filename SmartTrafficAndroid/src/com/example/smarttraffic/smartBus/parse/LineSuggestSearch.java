package com.example.smarttraffic.smartBus.parse;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.smartBus.bean.BusLine;
import com.example.smarttraffic.smartBus.bean.BusLineForQueryByName;
import com.example.smarttraffic.util.DoString;

/**
 * 线路建议解析类
 * @author Administrator zwc
 *
 */
public class LineSuggestSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		
		String data1 = DoString.parseThreeNetString(data);
		
		BusLineForQueryByName result = JSON.parseObject(data1,
				BusLineForQueryByName.class);

		List<BusLine> rList = new ArrayList<BusLine>();

		for (BusLine b : result.getLineList())
		{

			if (b.isIsRing())
			{
				rList.add(b);
			} else
			{
				BusLine busLine1 = (BusLine) b.clone();
				busLine1.setDirect(1);

				rList.add(busLine1);

				BusLine busLine2 = (BusLine) b.clone();
				busLine2.setDirect(2);

				rList.add(busLine2);
			}
		}

		return rList;
	}
}
