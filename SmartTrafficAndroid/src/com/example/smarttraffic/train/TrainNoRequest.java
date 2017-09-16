package com.example.smarttraffic.train;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * 铁路信息车次号请求
 * @author Administrator zhou
 *
 */
public class TrainNoRequest extends BaseRequest implements Serializable 
{
	private static final long serialVersionUID = 1L;
	String busNo;
	Calendar calendar;
	int type;
		
	public String getBusNo()
	{
		return busNo;
	}

	public void setBusNo(String busNo)
	{
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
	
	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	@Override
	public PostParams CreatePostParams()
	{
		PostParams postParams=new PostParams();
		postParams.setUrl(HttpUrlRequestAddress.TRAIN_REQUEST_NO_URL);
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("trainCode", busNo);
		postParams.setParams(param);
		
		return postParams;
	}
	
}
