package com.example.smarttraffic.busStation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;


public class BusTicketRequest extends BaseRequest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String startCity;
	private String endCity;
	private Calendar calendar;
	private int BusType;
	private int searchType;
	
	
	
	public Calendar getCalendar()
	{
		return calendar;
	}
	public void setCalendar(Calendar calendar)
	{
		this.calendar = calendar;
	}
	public String getStartCity() {
		return startCity;
	}
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	public String getEndCity() {
		return endCity;
	}
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	
	public int getBusType() {
		return BusType;
	}
	public void setBusType(int busType) {
		BusType = busType;
	}
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	
	@Override
	public PostParams CreatePostParams()
	{
		PostParams postParams=new PostParams();
		postParams.setUrl(HttpUrlRequestAddress.BUS_REQUEST_TICKET_URL);
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("startStation", startCity);
		param.put("arriveStation", endCity);
		
		postParams.setParams(param);
		
		return postParams;
	}
	
	
}
