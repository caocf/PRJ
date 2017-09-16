package com.example.smarttraffic.parking;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.common.localDB.BaseHistoryDBoperation;
import com.example.smarttraffic.common.localDB.BaseHistoryData;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.nearby.NearBy;

/**
 * 智慧公交历史数据库操作
 * @author Administrator zhou
 *
 */
public class ParkingHistoryDB extends BaseHistoryDBoperation
{
	public ParkingHistoryDB(Context context)
	{
		super(context);
	}

	public void insert(NearBy n)
	{
		String content=JSON.toJSONString(n);
		insert(ContentType.PARKING_HISTORY, content);
	}
	
	public boolean isHistory(NearBy n)
	{
		n.setHistoryID(0);
		String content=JSON.toJSONString(n);
		
		return isHistory(ContentType.PARKING_HISTORY, content);
	}
	
	public List<NearBy> selectForHistory()
	{
		List<NearBy> result=new ArrayList<NearBy>();
		try
		{
			List<BaseHistoryData> data=selectForBaseData(ContentType.PARKING_HISTORY);
			
			for(BaseHistoryData d:data)
			{
				NearBy BusLine=JSON.parseObject(d.getContent(), NearBy.class);
				BusLine.setHistoryID(d.getId());
				
				result.add(BusLine);
			}
		}
		catch(Exception e)
		{
			
		}
		return result;
	}
	
	public void toUpdate(NearBy n)
	{
		delete(n.getHistoryID());
		insert(n);
	}
	
}
