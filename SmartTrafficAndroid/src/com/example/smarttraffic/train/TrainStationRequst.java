package com.example.smarttraffic.train;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

public class TrainStationRequst extends BaseRequest
{
	public String airNo;
	
	public String getAirNo()
	{
		return airNo;
	}

	public void setAirNo(String airNo)
	{
		this.airNo = airNo;
	}

	@Override
	public PostParams CreatePostParams()
	{
		PostParams params=new PostParams();
		
		params.setUrl(HttpUrlRequestAddress.TRAIN_SEARCH_NO_STATION_URL);
		Map<String, Object> pMap=new HashMap<String, Object>();
		pMap.put("no", airNo);
		params.setParams(pMap);
		
		return params;
	}
}
