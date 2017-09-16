package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class WorkWaySpinner extends Spinner
{
	List<String> data_list=new ArrayList<>();
	ArrayAdapter<String> arr_adapter;

	public WorkWaySpinner(Context context, AttributeSet attrs) 
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
		data_list.add("装货");
		data_list.add("卸货"); 
		
		arr_adapter.notifyDataSetChanged();
	}

}
