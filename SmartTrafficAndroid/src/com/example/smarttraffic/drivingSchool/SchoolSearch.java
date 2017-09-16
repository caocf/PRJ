package com.example.smarttraffic.drivingSchool;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.smarttraffic.network.BaseSearch;

public class SchoolSearch extends BaseSearch
{
	public static final String LIST="list";
	public static final String ID="id";
	public static final String NAME="name";
	public static final String ADDRESS="address";
	public static final String DISTANCE="distance";
	public static final String LEVEL="level";
	public static final String PHONE="phone";
	public static final String LANTITUDE="lantitude";
	public static final String LONGTITUDE="longtitude";

	@Override
	public Object parse(String data) {	
		List<DrivingSchool> list=new ArrayList<DrivingSchool>();
		
		try
		{		
			JSONObject jsonObject=new JSONObject(data);  
	        JSONArray jsonArray=jsonObject.getJSONArray(LIST);  
	        
	        for(int i=0;i<jsonArray.length();i++)
	        {  
	        	DrivingSchool temp =new DrivingSchool();
      	
	            JSONObject json=jsonArray.getJSONObject(i);  
	            temp.setId(json.getInt(ID));
	            temp.setAddress(json.getString(ADDRESS));
	            temp.setDistance(json.getInt(DISTANCE));
	            temp.setLantitude(json.getDouble(LANTITUDE));
	            temp.setLongtitude(json.getDouble(LONGTITUDE));
	            temp.setLevel(json.getInt(LEVEL));
	            temp.setName(json.getString(NAME));
//	            temp.setPhone(json.getString(PHONE));
	            temp.setPhone("");
	            
	            list.add(temp);
	            
	        }  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return list;	
	}
}

