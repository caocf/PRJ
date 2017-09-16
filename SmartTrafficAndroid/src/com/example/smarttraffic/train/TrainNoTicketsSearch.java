package com.example.smarttraffic.train;

import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;

public class TrainNoTicketsSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		TrainNoTickets result=new TrainNoTickets();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			jsonObject=jsonObject.getJSONObject("trainTickets");
			
			result.setBusLeftNum(jsonObject.getInt("busLeftNum"));
			result.setBusPrice(jsonObject.getDouble("busPrice"));
			result.setCostTime(jsonObject.getString("costTime"));
			result.setEndCity(jsonObject.getString("endCity"));
			result.setEndTime(jsonObject.getString("endTime"));
			result.setFirLeftNum(jsonObject.getInt("firLeftNum"));
			result.setFirPrice(jsonObject.getDouble("firPrice"));
			result.setFirstCity(jsonObject.getString("firstCity"));
			result.setLastCity(jsonObject.getString("lastCity"));
			result.setSecLeftNum(jsonObject.getInt("secLeftNum"));
			result.setSecPrice(jsonObject.getDouble("secPrice"));
			result.setStartCity(jsonObject.getString("startCity"));
			result.setStartTime(jsonObject.getString("startTime"));
			result.setTrainNo(jsonObject.getString("trainNo"));
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
		
		return result;
	}
}
