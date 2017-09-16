package com.example.smarttraffic.busStation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;

public class BusStationInfoSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		List<BusStationInfo> result=new ArrayList<BusStationInfo>();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			
			JSONArray array=jsonObject.getJSONArray("busStationIntroduces");
			
			for(int i=0;i<array.length();i++)
			{
				jsonObject=array.getJSONObject(i);
				BusStationInfo temp=new BusStationInfo();
				
				temp.setStationName(jsonObject.getString("stationName"));
				temp.setUrl(jsonObject.getString("url"));
				
				result.add(temp);
			}
			
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
}
