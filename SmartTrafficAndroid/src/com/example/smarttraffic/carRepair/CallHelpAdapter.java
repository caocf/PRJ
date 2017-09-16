package com.example.smarttraffic.carRepair;

import java.util.List;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CallHelpAdapter extends BaseAdapter
{

	List<String> data;
	Context context;
	
	public CallHelpAdapter(Context context,List<String> data)
	{
		this.context=context;
		this.data=data;
	}
	
	@Override
	public int getCount()
	{
		return data.size();
	}

	@Override
	public Object getItem(int position)
	{
		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(position==0)
		{
			return singular(position, convertView, parent);
		}
		else
		{
			return dual(position, convertView, parent);
		}
		
	}
	
	public View singular(int position, View convertView, ViewGroup parent)
	{
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_dialog_left, null);
		
		TextView textView=(TextView)convertView.findViewById(R.id.call_help_dialog_left_textview);
		textView.setText(data.get(position));
		
		return convertView;
	}
	
	public View dual(int position, View convertView, ViewGroup parent)
	{
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_dialog_right, null);
		
		TextView textView=(TextView)convertView.findViewById(R.id.call_help_dialog_right_textview);
		textView.setText(data.get(position));
		
		return convertView;
	}
	
	public void add(String data)
	{
		this.data.add(data);
		update();
	}
	
	public void update(List<String> data)
	{
		this.data=null;
		this.data=data;
		update();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
}

