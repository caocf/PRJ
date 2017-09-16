package com.example.smarttraffic.airport;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

public class AirTicketRequest extends BaseRequest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String startCity;
	private String endCity;
	private Calendar calendar;
	private int BusType;
	private int searchType;
	private int page;
	private int num;
	
	private boolean isByStation;
	
	public int getPage()
	{
		return page;
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	public int getNum()
	{
		return num;
	}
	public void setNum(int num)
	{
		this.num = num;
	}
	
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
	public boolean isByStation()
	{
		return isByStation;
	}
	public void setByStation(boolean isByStation)
	{
		this.isByStation = isByStation;
	}



	private final String START_CITY="startCity";
	private final String END_CITY="arriveCity";
	private final String START_DATE="startDate";
	private final String PAGE="page";
	private final String NUM="num";
	
	@Override
	public PostParams CreatePostParams()
	{
		if(isByStation)
		{
			return searchByStation();
		}
		else 
		{
			return searchByName();
		}
	}
	
	
	private PostParams searchByStation()
	{
		PostParams params=new PostParams();
		params.setUrl(HttpUrlRequestAddress.AIR_REQUEST_STATION_TICKET_URL);
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put(START_CITY, startCity);
		
		if(calendar==null)
		{
			calendar=Calendar.getInstance();
		}
		param.put(START_DATE, new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
		
		if(page!=0 && num!=0)
		{
			param.put(PAGE, page);
			param.put(NUM, num);
		}
		
		params.setParams(param);
		
		return params;
	}
	
	private PostParams searchByName()
	{
		PostParams params=new PostParams();
		params.setUrl(HttpUrlRequestAddress.AIR_REQUEST_STATION_TICKET_URL);
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put(START_CITY, startCity);
		param.put(END_CITY, endCity);
		
		if(calendar==null)
		{
			calendar=Calendar.getInstance();
		}
		param.put(START_DATE, new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
		
		if(page!=0 && num!=0)
		{
			param.put(PAGE, page);
			param.put(NUM, num);
		}
		
		params.setParams(param);
		
		return params;
	}
}
