package com.example.smarttraffic.busStation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.Request;

public class BusTicketSearch extends BaseSearch
{	
	Request request;
	String data;
	
	@Override
	public Object search(Request request) {
		return super.search(request);
	}


	@Override
	public Object parse(String data) {
		
		List<Ticket> result=new ArrayList<Ticket>();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			
			JSONArray array=jsonObject.getJSONArray("busSchedules");
			for(int i=0;i<array.length();i++)
			{
				jsonObject=array.getJSONObject(i);
				Ticket temp=new Ticket();
				
				temp.setCarKind(jsonObject.getString("carKind"));
				temp.setCarNumber(jsonObject.getString("carNumber"));
				temp.setEndCity(jsonObject.getString("endStation"));
				temp.setLeaveTicketNumber(jsonObject.getInt("leaveTicketNumber"));
				temp.setLeaveTime(jsonObject.getString("leaveTime"));
				temp.setLength(jsonObject.getDouble("length"));
				temp.setPrice(jsonObject.getDouble("price"));
				temp.setReachTime(jsonObject.getString("reachTime"));
				temp.setStartCity(jsonObject.getString("startStation"));
				
				
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
