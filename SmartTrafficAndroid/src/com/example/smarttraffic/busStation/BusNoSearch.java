package com.example.smarttraffic.busStation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.Request;

public class BusNoSearch extends BaseSearch
{

	@Override
	public Object search(Request request) {
		return super.search(request);
	}


	@Override
	public Object parse(String data) {
		Bus result=new Bus();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			jsonObject=jsonObject.getJSONObject("busCodes");
			result.setBusNo(jsonObject.getString("busNo"));
			result.setCompany(jsonObject.getString("company"));
			result.setEndCity(jsonObject.getString("endCity"));
			result.setEndStation(jsonObject.getString("endStation"));
			result.setStartCity(jsonObject.getString("startCity"));
			result.setStartStation(jsonObject.getString("startStation"));
			
			List<BusOrder> orders=new ArrayList<BusOrder>();
			JSONArray array=jsonObject.getJSONArray("orders");
			for(int i=0;i<array.length();i++)
			{
				jsonObject=array.getJSONObject(i);
				
				BusOrder temp=new BusOrder();
				temp.setOrder(jsonObject.getInt("order"));
				temp.setPlanStartTime(jsonObject.getString("planStartTime"));
				temp.setRealEndTime(jsonObject.getString("realEndTime"));
				temp.setRealStartTime(jsonObject.getString("realStartTime"));
				temp.setPlanEndTime(jsonObject.getString("planEndTime"));
				
				orders.add(temp);
			}
			
			result.setOrders(orders);
			
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}	
	

}
