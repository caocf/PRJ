package net.hxkg.lawexcut;

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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Lawtypespinner extends Spinner implements onResult
{
	List<String> data_list=new ArrayList<>();
	List<String> id_list=new ArrayList<>();
	public ArrayAdapter<String> arr_adapter;
	
	public int selectedValue=1;
	
	public Lawtypespinner(Context context, AttributeSet attrs)
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
				selectedValue=position+1;
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				selectedValue=0;
				
			}
			
		});
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		hr.post(Constants.BaseURL+"AllLawType", map);
	}

	@Override
	public void onSuccess(String result)
	{
		try 
		{
			JSONArray arr=new JSONArray(result);
			for(int i=0;i<arr.length();i++)
			{
				JSONObject object=(JSONObject) arr.opt(i);
				data_list.add(object.getString("status"));
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
