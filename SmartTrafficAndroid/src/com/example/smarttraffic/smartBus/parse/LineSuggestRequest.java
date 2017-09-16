package com.example.smarttraffic.smartBus.parse;

import java.util.HashMap;
import java.util.Map;

import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;

/**
 * 线路建议搜索类
 * @author Administrator zwc
 *
 */
public class LineSuggestRequest extends BaseRequest
{
	public LineSuggestRequest(String lineName)
	{
		this.lineName = lineName;
	}

	String lineName;

	public void setLineName(String lineName)
	{
		this.lineName = lineName;
	}

	@Override
	public PostParams CreatePostParams()
	{
		PostParams params = new PostParams();
		params.setUrl(HttpUrlRequestAddress.SMART_BUS_LINE_SUUGESTION_URL);
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("lineName", lineName);
		params.setParams(p);

		return params;
	}

}
