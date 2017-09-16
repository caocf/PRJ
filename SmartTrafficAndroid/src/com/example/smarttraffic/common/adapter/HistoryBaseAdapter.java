package com.example.smarttraffic.common.adapter;

import com.example.smarttraffic.common.localDB.BaseHistoryDBoperation;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoryBaseAdapter extends BaseAdapter
{

	@Override
	public int getCount()
	{
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return null;
	}

	public int getHID(int position)
	{
		return 0;
	}
	
	public int getHType()
	{
		return 0;
	}
	
	public void Clear(Context context)
	{
		BaseHistoryDBoperation dbOperate=new BaseHistoryDBoperation(context);
		
		dbOperate.deleteAll(getHType());
		
		dbOperate.CloseDB();
		
		notifyDataSetChanged();
	}
	
	public View CreateClearView(Context context)
	{
		TextView clear=new TextView(context);

		clear.setText("清除历史记录");
		clear.setPadding(0, 20, 0, 20);
		clear.setTextSize(16);
		clear.setGravity(Gravity.CENTER);
		clear.setClickable(true);
		
		return clear;
	}
	
	public void removeByID(int id)
	{
	}
}
