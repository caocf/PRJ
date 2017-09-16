package com.example.smarttraffic.carRepair;


import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;


public class CarRepairRequest extends BaseRequest
{
	String name;
	int page;
	int num;
	
	
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	@Override
	public PostParams CreatePostParams()
	{
		PostParams result =new PostParams();
		Map<String, Object> d=new HashMap<String, Object>();
		
		if(name==null || name.equals(""))
		{
			result.setUrl(HttpUrlRequestAddress.CAR_REPAIR_REQUES_URL);
		}
		else
		{
			result.setUrl(HttpUrlRequestAddress.CAR_REPAIR_REQUES_NAME_URL);
			d.put("name", name);
		}
		
		if(page>0 && num>0)
		{
			d.put("page", page);
			d.put("num", num);
			
		}
		result.setParams(d);
		
		return result;
	}
	
}
