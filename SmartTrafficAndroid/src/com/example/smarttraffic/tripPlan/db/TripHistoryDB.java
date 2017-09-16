package com.example.smarttraffic.tripPlan.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.common.localDB.BaseHistoryDBoperation;
import com.example.smarttraffic.common.localDB.BaseHistoryData;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.tripPlan.bean.History;

/**
 * 出行历史记录数据库操作类
 * @author Administrator
 *
 */
public class TripHistoryDB extends BaseHistoryDBoperation
{
	private int typeID=ContentType.TRIP_PLAN_SELF_DRIVING;
	
	public TripHistoryDB(Context context)
	{
		super(context);
	}
	
	public TripHistoryDB(Context context,boolean common)
	{
		super(context);
		if(common)
			typeID=ContentType.TRIP_COMMON_DRIVING;
	}

	public void insert(History history)
	{
		String content=JSON.toJSONString(history);
		insert(typeID, content);
	}
	
	public boolean isHistory(History history)
	{
		String content=JSON.toJSONString(history);
		
		return isHistory(typeID, content);
	}
	
	public List<History> selectForHistory()
	{
		List<History> result=new ArrayList<History>();
		try
		{
			List<BaseHistoryData> data=selectForBaseData(typeID);
			
			for(BaseHistoryData d:data)
			{
				History history=JSON.parseObject(d.getContent(), History.class);
				history.setId(d.getId());
				
				result.add(history);
			}
		}
		catch(Exception e)
		{
			
		}
		return result;
	}
	
	public void toUpdate(History history)
	{
		delete(history.getId());
		insert(history);
	}
}
