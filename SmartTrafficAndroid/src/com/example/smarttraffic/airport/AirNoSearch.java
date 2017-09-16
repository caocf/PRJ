package com.example.smarttraffic.airport;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.Request;

public class AirNoSearch extends BaseSearch
{

	@Override
	public Object search(Request request) {
		return super.search(request);
	}

	@Override
	public Object parse(String data) {
		AirNo resutl=new AirNo();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			jsonObject=jsonObject.getJSONObject("airNo");
			
			resutl.setAirNo(jsonObject.getString("airNo"));
			resutl.setCompany(jsonObject.getString("company"));
			resutl.setCostTime(jsonObject.getString("costTime"));
			resutl.setCusLeftNum(jsonObject.getInt("cusLeftNum"));
			resutl.setCusPrice(jsonObject.getDouble("cusPrice"));
			resutl.setEcoLeftNum(jsonObject.getInt("ecoLeftNum"));
			resutl.setEcoPrice(jsonObject.getDouble("ecoPrice"));
			resutl.setEndAirport(jsonObject.getString("endAirport"));
			resutl.setEndCity(jsonObject.getString("endCity"));
			resutl.setEndTime(jsonObject.getString("endTime"));
			resutl.setFirLeftNum(jsonObject.getInt("firLeftNum"));
			resutl.setFirPrice(jsonObject.getDouble("firPrice"));
			resutl.setStartAirport(jsonObject.getString("startAirport"));
			resutl.setStartCity(jsonObject.getString("startCity"));
			resutl.setStartTime(jsonObject.getString("startTime"));
			
		} 
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resutl;
	}	
	

}
