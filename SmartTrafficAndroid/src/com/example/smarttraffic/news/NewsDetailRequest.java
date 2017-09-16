package com.example.smarttraffic.news;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.util.DoString;

public class NewsDetailRequest extends BaseRequest
{
	int id;
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	private final String DETAIL_ID="newsID";

	@Override
	public String CreateUrl()
	{
		if(id>0)
		{
			String resut=HttpUrlRequestAddress.NEWS_DETAIL_REQUES_URL+"?";
			
			return DoString.URLJoint(resut, DETAIL_ID, id);
		}
		
		return "";			
	}
}
