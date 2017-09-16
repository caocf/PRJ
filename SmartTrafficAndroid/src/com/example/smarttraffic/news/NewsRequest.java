package com.example.smarttraffic.news;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.util.DoString;

public class NewsRequest extends BaseRequest
{
	private int type;
	private String subType;
	private int page;
	private int num;

	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public String getSubType()
	{
		return subType;
	}
	public void setSubType(String subType)
	{
		this.subType = subType;
	}
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

	private final String TYPE="type";
	private final String SUBTYPE="subType";
	private final String PAGE="page";
	private final String NUM="num";
	
	@Override
	public String CreateUrl() {	
		String result= HttpUrlRequestAddress.NEWS_REQUES_URL+"?";
		
		if(subType!=null && subType.length()>0)
		{
			result=DoString.URLJoint(result, TYPE, type);
			result=DoString.URLJoint(result, SUBTYPE, subType);
			
			if(num!=0)
			{
				result=DoString.URLJoint(result, PAGE, page);
				result=DoString.URLJoint(result, NUM, num);
			}
			else
			{
				//默认
				result=DoString.URLJoint(result, PAGE, 1);
				result=DoString.URLJoint(result, NUM, 30);
			}
		}

		return result;
	}
}
