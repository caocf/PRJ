package com.example.smarttraffic.news;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;

public class NewsSearch extends BaseSearch
{
	public final String LIST="newsList";
	public final String TYPE="type";
	public final String SUBTYPE="subType";
	public final String CONTENT="content";
	public final String DATE="date";
	public final String ID="id";
	public final String TITLE="title";
		
	@Override
	public Object parse(String data) 
	{
		List<News> result=new ArrayList<News>();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONArray array=jsonObject.getJSONArray(LIST);
			
			for(int i=0;i<array.length();i++)
			{
				jsonObject=array.getJSONObject(i);
				
				News temp=new News();
				temp.setId(jsonObject.getInt(ID));
				temp.setTitle(jsonObject.getString(TITLE));
				temp.setContent(jsonObject.getString(CONTENT));
				temp.setDate(jsonObject.getString(DATE));
				temp.setType(jsonObject.getInt(TYPE));
				temp.setSubType(jsonObject.getInt(SUBTYPE));
				
				result.add(temp);
			}
			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}	
		return result;
	}
	
}
