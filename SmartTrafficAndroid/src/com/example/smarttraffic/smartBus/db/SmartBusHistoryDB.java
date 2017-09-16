package com.example.smarttraffic.smartBus.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.common.localDB.BaseHistoryDBoperation;
import com.example.smarttraffic.common.localDB.BaseHistoryData;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.smartBus.bean.BusLine;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByName;

/**
 * 智慧公交历史数据库操作
 * @author Administrator zhou
 *
 */
public class SmartBusHistoryDB extends BaseHistoryDBoperation
{
	private int types[]=new int[]{ContentType.SMART_BUS_TRANSFER_HISTORY,ContentType.SMART_BUS_LINE_HISTORY,ContentType.SMART_BUS_STATION_HISTORY};
	
	public SmartBusHistoryDB(Context context)
	{
		super(context);
	}

	public void insert(BusLine busLine)
	{
		String content=JSON.toJSONString(busLine);
		insert(types[1], content);
	}
	
	public boolean isHistory(BusLine busLine)
	{
		busLine.setHistoryID(0);
		String content=JSON.toJSONString(busLine);
		
		return isHistory(types[1], content);
	}
	
	public List<BusLine> selectForHistory()
	{
		List<BusLine> result=new ArrayList<BusLine>();
		try
		{
			List<BaseHistoryData> data=selectForBaseData(types[1]);
			
			for(BaseHistoryData d:data)
			{
				BusLine BusLine=JSON.parseObject(d.getContent(), BusLine.class);
				BusLine.setHistoryID(d.getId());
				
				result.add(BusLine);
			}
		}
		catch(Exception e)
		{
			
		}
		return result;
	}
	
	public void toUpdate(BusLine busLine)
	{
		delete(busLine.getHistoryID());
		insert(busLine);
	}
	
	
	public void insert(BusStationForQueryByName busStationForQueryByName)
	{
		String content=JSON.toJSONString(busStationForQueryByName);
		insert(types[2], content);
	}
	
	public boolean isHistory(BusStationForQueryByName busStationForQueryByName)
	{
		busStationForQueryByName.setHistoryID(0);
		String content=JSON.toJSONString(busStationForQueryByName);
		
		return isHistory(types[2], content);
	}
	
	public List<BusStationForQueryByName> selectForStationHistory()
	{
		List<BusStationForQueryByName> result=new ArrayList<BusStationForQueryByName>();
		try
		{
			List<BaseHistoryData> data=selectForBaseData(types[2]);
			
			for(BaseHistoryData d:data)
			{
				BusStationForQueryByName busStationForQueryByName=JSON.parseObject(d.getContent(), BusStationForQueryByName.class);
				busStationForQueryByName.setHistoryID(d.getId());
				
				result.add(busStationForQueryByName);
			}
		}
		catch(Exception e)
		{
			
		}
		return result;
	}
	
}
