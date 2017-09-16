package com.example.smarttraffic.train;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

public class TrainNoTicketsRequest extends BaseRequest
{
	private String trainNo;
	private String start;
	private String end;
	
	public String getTrainNo()
	{
		return trainNo;
	}

	public void setTrainNo(String trainNo)
	{
		this.trainNo = trainNo;
	}

	public String getStart()
	{
		return start;
	}

	public void setStart(String start)
	{
		this.start = start;
	}

	public String getEnd()
	{
		return end;
	}

	public void setEnd(String end)
	{
		this.end = end;
	}

	@Override
	public PostParams CreatePostParams()
	{
		PostParams params=new PostParams();
		params.setUrl(HttpUrlRequestAddress.TRAIN_SEARCH_NO_TICKETS_URL);
		Map<String, Object> p=new HashMap<String, Object>();
		p.put("trainNo",trainNo );
		p.put("startStation", start);
		p.put("arriveStation", end);
		params.setParams(p);
		
		return params;
	}
}
