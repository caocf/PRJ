package com.example.smarttraffic.bike.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.bike.bean.BikeFavor;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.common.localDB.BaseFavorDBOperation;
import com.example.smarttraffic.common.localDB.BaseFavorData;
import com.example.smarttraffic.common.localDB.ContentType;

public class BikeFavorDB extends BaseFavorDBOperation
{
	public BikeFavorDB(Context context)
	{
		super(context);
	}
	
	public void insertStation(BikeStation busStation)
	{
		busStation.setId(0);						
		busStation.setDistance(0);					
		String s=JSON.toJSONString(busStation);
		insert(ContentType.BIKE_STATION_FAVOR, s);
	}
	
	public boolean isFavorStation(BikeStation busStation)
	{
		busStation.setId(0);
		busStation.setDistance(0);
		String s=JSON.toJSONString(busStation);
		
		return isFavor(ContentType.BIKE_STATION_FAVOR, s);
	}
	
	public void delete(BikeStation bikeStation)
	{
		bikeStation.setId(0);
		bikeStation.setDistance(0);
		String s=JSON.toJSONString(bikeStation);
		
		deleteFavorID(ContentType.BIKE_STATION_FAVOR, s);
	}
	
	public List<BikeStation> selectForBikeStation()
	{
		List<BaseFavorData> d=selectForBaseFavor(ContentType.BIKE_STATION_FAVOR);
		
		if(d==null)
			return null;
		
		List<BikeStation> result=new ArrayList<BikeStation>();
		
		for(int i=0;i<d.size();i++)
		{
			BikeStation temp=JSON.parseObject(d.get(i).getContent(),BikeStation.class);
			temp.setFavorID(d.get(i).getId());
			result.add(temp);
		}
		
		return result;
	}
	
	
	
	
	public void insertRiding(BikeFavor bikeFavor)
	{
		bikeFavor.setFavorID(0);
		String s=JSON.toJSONString(bikeFavor);
		insert(ContentType.BIKE_RIDE_FAVOR, s);
	}
	
	public boolean isFavorRiding(BikeFavor bikeRiding)
	{
		bikeRiding.setFavorID(0);
		String s=JSON.toJSONString(bikeRiding);
		
		return isFavor(ContentType.BIKE_RIDE_FAVOR, s);
	}
	
	public void delete(BikeFavor bikeFavor)
	{
		bikeFavor.setFavorID(0);
		String s=JSON.toJSONString(bikeFavor);
		
		deleteFavorID(ContentType.BIKE_RIDE_FAVOR, s);
	}
	
	public List<BikeFavor> selectForBikeRiding()
	{
		List<BaseFavorData> d=selectForBaseFavor(ContentType.BIKE_RIDE_FAVOR);
		
		if(d==null)
			return null;
		
		List<BikeFavor> result=new ArrayList<BikeFavor>();
		
		for(int i=0;i<d.size();i++)
		{
			BikeFavor temp=JSON.parseObject(d.get(i).getContent(),BikeFavor.class);
			temp.setFavorID(d.get(i).getId());
			result.add(temp);
		}
		
		return result;
	}
}
