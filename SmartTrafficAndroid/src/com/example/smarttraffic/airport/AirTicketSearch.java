package com.example.smarttraffic.airport;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.Request;

public class AirTicketSearch extends BaseSearch
{	
	Request request;
	String data;
	
	@Override
	public Object search(Request request) {
		return super.search(request);
	}


	@Override
	public Object parse(String data) {

		List<AirTickets> result=new ArrayList<AirTickets>();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			
			JSONArray array=jsonObject.getJSONArray("schedules");
			for(int i=0;i<array.length();i++)
			{
				jsonObject=array.getJSONObject(i);
				
				AirTickets temp=new AirTickets();
				temp.setAirKind(jsonObject.getString("airKind"));
				temp.setAirNumber(jsonObject.getString("airNumber"));
				temp.setCompany(jsonObject.getString("company"));
				temp.setEndCity(jsonObject.getString("endCity"));
				temp.setLeaveTime(jsonObject.getString("leaveTime"));
				temp.setReachTime(jsonObject.getString("reachTime"));
				temp.setStartCity(jsonObject.getString("startCity"));
				temp.setTime(jsonObject.getLong("time"));
				
				result.add(temp);
			}
			
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		
		return result;
	}


}
