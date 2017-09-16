package com.example.smarttraffic.news;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;

public class NewsDetailSearch extends BaseSearch
{
	public final String NAME="newsDetailForPhone";
	public final String TYPE="type";
	public final String SUBTYPE="subType";
	public final String CONTENT="content";
	public final String DATE="date";
	public final String ID="id";
	public final String TITLE="title";
	public final String AUTHOR="author";
	public final String CLICKNUM="clickNum";
	public final String SOURCE="source";
		
	@Override
	public Object parse(String data) 
	{
		DetailNews temp=new DetailNews();
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			jsonObject =jsonObject.getJSONObject(NAME);
			
			temp.setId(jsonObject.getInt(ID));
			temp.setTitle(jsonObject.getString(TITLE));
			temp.setContent(jsonObject.getString(CONTENT));
			temp.setDate(jsonObject.getString(DATE));
			temp.setType(jsonObject.getInt(TYPE));
			temp.setSubType(jsonObject.getInt(SUBTYPE));	
			temp.setAuthor(jsonObject.getString(AUTHOR));
			temp.setClickNum(jsonObject.getInt(CLICKNUM));
			temp.setSource(jsonObject.getString(SOURCE));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}	
		return temp;
	}
	
}
