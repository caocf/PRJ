package com.example.smarttraffic.airport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;

public class AirStationSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		List<AirStationInfo> result=new ArrayList<AirStationInfo>();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			
			JSONArray array=jsonObject.getJSONArray("airStations");
			
			for(int i=0;i<array.length();i++)
			{
				jsonObject=array.getJSONObject(i);
				AirStationInfo temp=new AirStationInfo();
				
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
