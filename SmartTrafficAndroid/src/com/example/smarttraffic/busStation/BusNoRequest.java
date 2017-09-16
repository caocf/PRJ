package com.example.smarttraffic.busStation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

public class BusNoRequest extends BaseRequest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String busNo;
	Calendar calendar;
	int type;
	
	int other;
	
		
	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public String getBusNo() {
		return busNo;
	}

	public void setBusNo(String busNo) {
		this.busNo = busNo;
	}



	public Calendar getCalendar()
	{
		return calendar;
	}

	public void setCalendar(Calendar calendar)
	{
		this.calendar = calendar;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public PostParams CreatePostParams()
	{
		PostParams postParams=new PostParams();
		
		postParams.setUrl(HttpUrlRequestAddress.BUS_REQUEST_NO_URL);
		
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("busCode", busNo);
		postParams.setParams(param);
		
		return postParams;
	}

//	@Override
//	public String CreateUrl() 
//	{
//		if(other==2)
//		{
//			return HttpUrlRequestAddress.BUS_REQUEST_STATION_URL;
//		}
//		else 
//		{
//			if(getType()==1)
//				return HttpUrlRequestAddress.BUS_REQUEST_NO_URL;
//			else if(getType()==0)
//				return HttpUrlRequestAddress.BUS_REQUEST_NO_WITHOUT_REAL_INFO_URL;
//			else
//				return "";
//		}
//	}
	
}
