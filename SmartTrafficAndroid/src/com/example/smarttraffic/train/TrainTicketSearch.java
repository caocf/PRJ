package com.example.smarttraffic.train;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.Request;

/**
 * 铁路车票信息解析类
 * @author Administrator zhou
 *
 */
public class TrainTicketSearch extends BaseSearch
{	
	Request request;
	String data;
	
	@Override
	public Object search(Request request) {
		return super.search(request);
	}

	@Override
	public Object parse(String data) {
		
		List<TrainTickets> result=new ArrayList<TrainTickets>();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONArray jsonArray=jsonObject.getJSONArray("schedules");
			
			for(int i=0;i<jsonArray.length();i++)
			{
				jsonObject=jsonArray.getJSONObject(i);
				
				TrainTickets temp=new TrainTickets();
				temp.setStartCity(jsonObject.getString("startCity"));
				temp.setEndCity(jsonObject.getString("endCity"));
				temp.setFirstCity(jsonObject.getString("firstCity"));
				temp.setLastCity(jsonObject.getString("lastCity"));
				temp.setLeaveTime(jsonObject.getString("leaveTimeShort"));
				temp.setReachTime(jsonObject.getString("reachTimeShort"));
				temp.setCostTime(jsonObject.getString("costTime"));
				temp.setLength(jsonObject.getDouble("length"));
				temp.setTrainNumber(jsonObject.getString("trainNumber"));
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
