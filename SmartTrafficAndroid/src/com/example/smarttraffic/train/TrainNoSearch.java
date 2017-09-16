package com.example.smarttraffic.train;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.Request;

/**
 * 铁路车次号信息解析
 * @author Administrator zhou
 *
 */
public class TrainNoSearch extends BaseSearch
{

	@Override
	public Object search(Request request) {
		return super.search(request);
	}


	@Override
	public Object parse(String data) {
		
		List<Train> result=new ArrayList<Train>();
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONArray array=jsonObject.getJSONArray("trainCodes");
			
			for(int i=0;i<array.length();i++)
			{
				jsonObject=array.getJSONObject(i);
				Train temp=new Train();
				temp.setPlanStart(jsonObject.getString("planStart"));
				temp.setPlanEnd(jsonObject.getString("planEnd"));
				temp.setRealStart(jsonObject.getString("realStart"));
				temp.setLength(jsonObject.getLong("length"));
				temp.setStation(jsonObject.getString("station"));
				temp.setRetain(jsonObject.getInt("retain"));
				
				result.add(temp);
			}
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return result;
	}	
	

	
}
