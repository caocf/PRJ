package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import net.hxkg.user.User;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Manspinner extends Spinner implements onResult
{
	List<String> data_list=new ArrayList<>();
	List<String> id_list=new ArrayList<>();
	ArrayAdapter<String> arr_adapter;
	
	public String selectedValue;
	
	public Manspinner(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		arr_adapter= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data_list);
		arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.setAdapter(arr_adapter);
		this.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) 
			{
				selectedValue=id_list.get(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				selectedValue="";
				
			}
			
		});
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("id", User.sjzgString);
		hr.post(Constants.BaseURL+"Approvers", map);
	}

	@Override
	public void onSuccess(String result)
	{
		try 
		{
			JSONObject reJsonObject=new JSONObject(result);
			JSONArray object=reJsonObject.getJSONArray("obj");
			for(int i=0;i<object.length();i++)
			{
				JSONObject object2=(JSONObject) object.opt(i);
				data_list.add(object2.getString("xm"));
				id_list.add(object2.getString("id"));
			}
			arr_adapter.notifyDataSetChanged();
			
		} catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onError(int httpcode)
	{
		// TODO Auto-generated method stub
		
	}

}
