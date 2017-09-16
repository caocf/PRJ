package com.example.smarttraffic.common.parse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.nearby.NearBy;
import com.example.smarttraffic.network.BaseSearch;

/**
 * poi请求解析类
 * @author Administrator zwc
 *
 */
public class PoiSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		List<NearBy> result = new ArrayList<NearBy>();

		try
		{
			JSONObject jsonObject = new JSONObject(data);
			JSONArray array = jsonObject.getJSONArray("circles");

			for (int i = 0; i < array.length(); i++)
			{
				NearBy temp = new NearBy();

				jsonObject = array.getJSONObject(i);

				temp.setAddress(jsonObject.getString("address"));
				temp.setId(jsonObject.getString("id"));
				temp.setLan(jsonObject.getDouble("lan"));
				temp.setLon(jsonObject.getDouble("lon"));
				temp.setName(jsonObject.getString("name"));
				temp.setRegion(jsonObject.getString("region"));
				temp.setStreet(jsonObject.getString("street"));
				temp.setTime(jsonObject.getString("time"));

				result.add(temp);
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		return result;
	}
}
