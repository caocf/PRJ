package com.hxkg.ereport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult; 
import android.R; 
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ShipSpinner extends Spinner implements onResult,AdapterView.OnItemSelectedListener
{
	List<String> data_list=new ArrayList<>();
	ArrayAdapter<String> arr_adapter;
	EReportListActivity era=null;
	
	String name;
	int position=0;
	
	public ShipSpinner(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		arr_adapter=new ArrayAdapter(context,R.layout.simple_spinner_item,data_list);
		//arr_adapter.setDropDownViewResource(com.hxkg.ghpublic.R.layout.item_dropdown);
		arr_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		setAdapter(arr_adapter);
		
	} 
	
	public void setID(String name)
	{
		this.name=name;
		RequestData();
	}
	
	private void RequestData( )
	{
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>(); 
		map.put("Username", SystemStatic.userName);
		hr.post(contants.baseUrl+"myshiplistPass", map);
	}

	@Override
	public void onSuccess(String result) 
	{
		try 
		{
			JSONArray arr=new JSONArray(result);
			for(int i=0;i<arr.length();i++)
			{
				JSONObject object=arr.getJSONObject(i);
				String nameString=object.getString("shipname");
				
				data_list.add(nameString);
				
				if(nameString.equals(name))
				{
					position=i;
				}
			}
			setSelection(position);
			arr_adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if(era!=null)
		//{
			//era.freshlist();
		//}
		
	}
	
	public void setEAR(EReportListActivity era)
	{
		this.era=era;
	}

	@Override
	public void onError(int httpcode) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		// TODO Auto-generated method stub
		
	}
}
