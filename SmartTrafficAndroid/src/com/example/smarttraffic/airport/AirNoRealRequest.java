package com.example.smarttraffic.airport;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;


public class AirNoRealRequest extends BaseRequest implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String airNo;
	Calendar calendar;		
	
	boolean isReal;
	boolean isStation;
	
	public String getAirNo()
	{
		return airNo;
	}

	public void setAirNo(String airNo)
	{
		this.airNo = airNo;
	}

	public Calendar getCalendar()
	{
		return calendar;
	}

	public void setCalendar(Calendar calendar)
	{
		this.calendar = calendar;
	}


	public boolean isReal()
	{
		return isReal;
	}

	public void setReal(boolean isReal)
	{
		this.isReal = isReal;
	}

	public boolean isStation()
	{
		return isStation;
	}

	public void setStation(boolean isStation)
	{
		this.isStation = isStation;
	}

	@Override
	public PostParams CreatePostParams()
	{
		PostParams params=new PostParams();
		
		params.setUrl(HttpUrlRequestAddress.AIR_SEARCH_NO_REAL_URL);
		Map<String, Object> pMap=new HashMap<String, Object>();
		pMap.put("no", airNo);
		params.setParams(pMap);
		
		return params;
	}

}
