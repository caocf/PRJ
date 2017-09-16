package com.example.smarttraffic.bike.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.bike.bean.BikeRiding;
import com.example.smarttraffic.common.localDB.BaseHistoryDBoperation;
import com.example.smarttraffic.common.localDB.BaseHistoryData;
import com.example.smarttraffic.common.localDB.ContentType;

public class BikeHistoryDB extends BaseHistoryDBoperation
{

	public BikeHistoryDB(Context context)
	{
		super(context);
	}

	public void insertRiding(BikeRiding bikeRiding)
	{
		bikeRiding.setHistoryID(0);
		bikeRiding.setFavorID(0);
		String s=JSON.toJSONString(bikeRiding);
		insert(ContentType.BIKE_RIDING_DRIVING, s);
	}
	
	public boolean isHistoryRiding(BikeRiding bikeRiding)
	{
		bikeRiding.setHistoryID(0);
		bikeRiding.setFavorID(0);
		String s=JSON.toJSONString(bikeRiding);
		
		return isHistory(ContentType.BIKE_RIDING_DRIVING, s);
	}
	
	public List<BikeRiding> selectForBikeRiding()
	{
		List<BaseHistoryData> d=selectForBaseData(ContentType.BIKE_RIDING_DRIVING);
		
		if(d==null)
			return null;
		
		List<BikeRiding> result=new ArrayList<BikeRiding>();
		
		for(int i=0;i<d.size();i++)
		{
			BikeRiding temp=JSON.parseObject(d.get(i).getContent(),BikeRiding.class);
			temp.setFavorID(d.get(i).getId());
			temp.setHistoryID(d.get(i).getId());
			result.add(temp);
		}
		
		return result;
	}
}
