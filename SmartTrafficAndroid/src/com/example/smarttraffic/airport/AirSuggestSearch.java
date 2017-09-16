package com.example.smarttraffic.airport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;

public class AirSuggestSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		List<AirCity> result=new ArrayList<AirCity>();
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONArray array=jsonObject.getJSONArray("airCities");
			
			for(int i=0;i<array.length();i++)
			{
				jsonObject=array.getJSONObject(i);
				
				AirCity temp=new AirCity();
				temp.setAbbreviation(jsonObject.getString("abbreviation"));
				temp.setEnglishName(jsonObject.getString("englishName"));
				temp.setName(jsonObject.getString("name"));
								
				result.add(temp);
				
			}
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		
		return result;
	}
}
