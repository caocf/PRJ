package com.example.smarttraffic.airport;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.Request;

public class AirNoRealSearch extends BaseSearch
{

	@Override
	public Object search(Request request) {
		return super.search(request);
	}

	@Override
	public Object parse(String data) {
		AirNoReal resutl=new AirNoReal();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			jsonObject=jsonObject.getJSONObject("airNoReal");
			
			resutl.setAirNo(jsonObject.getString("airNo"));
			resutl.setCompany(jsonObject.getString("company"));

			resutl.setEndAirport(jsonObject.getString("endAirport"));
			resutl.setEndCity(jsonObject.getString("endCity"));

			resutl.setStartAirport(jsonObject.getString("startAirport"));
			resutl.setStartCity(jsonObject.getString("startCity"));
			
			resutl.setAirKind(jsonObject.getString("airKind"));
			resutl.setStartPlanTime(jsonObject.getString("startPlanTime"));
			resutl.setEndPlanTime(jsonObject.getString("endPlanTime"));
			resutl.setStartRealTime(jsonObject.getString("startRealTime"));
			resutl.setEndRealTime(jsonObject.getString("endRealTime"));			
		} 
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resutl;
	}	
	

}
