package net.hxkg.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hxkg.channel.HttpRequest.onResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 
import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class HBSpinner extends Spinner implements onResult,AdapterView.OnItemSelectedListener
{
	List<String> data_list=new ArrayList<>();
	ArrayAdapter<String> arr_adapter;
	
	public HBSpinner(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		arr_adapter=new ArrayAdapter(context,R.layout.simple_spinner_item,data_list);
		//arr_adapter.setDropDownViewResource(com.hxkg.ghpublic.R.layout.item_dropdown);
		arr_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
		setAdapter(arr_adapter);
		RequestData1();
	}
	
	private void RequestData1()
	{ 
		data_list.add("标志牌\n缺失损毁");
		data_list.add("标志牌\n倾斜");
		data_list.add("标志牌\n被遮挡");
		data_list.add("反光膜\n老化脱落");
		data_list.add("浮标位移");
		data_list.add("其他问题"); 
		
		arr_adapter.notifyDataSetChanged();
	}
	
	private void RequestData()
	{
		HttpRequest hr=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("sfgg",1);
		map.put("xzqh",1);
		hr.post("http://172.21.25.40/Channel/"+"hangdao/queryallhangdao", map);
	}

	@Override
	public void onSuccess(String result) 
	{
		try {
			JSONArray arr=new JSONArray(result);
			for(int i=0;i<arr.length();i++)
			{
				JSONObject object=arr.getJSONObject(i);
				String nameString=object.getString("regionname");
				data_list.add(nameString);
			}
			
			arr_adapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
