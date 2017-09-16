package com.example.smarttraffic.bike.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeRidingLineInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BikeRideLineListAdapter extends BaseAdapter
{
	List<BikeRidingLineInfo> data;
	Context context;
	
	public BikeRideLineListAdapter(Context context,List<BikeRidingLineInfo> data)
	{
		this.data=data;
		this.context=context;
	}
	
	public List<BikeRidingLineInfo> getData()
	{
		return data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{

		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_bike_riding_list,null);
		holder = new ViewHolder();
		
		holder.seqTextView = (TextView) convertView.findViewById(R.id.listview_bike_riding_list_seq);
		holder.nameTextView=(TextView)convertView.findViewById(R.id.listview_bike_riding_list_name);		
		holder.infoTextView=(TextView)convertView.findViewById(R.id.listview_bike_riding_list_info);
		
		holder.seqTextView.setText("方案"+position+":");
		
		holder.nameTextView.setText(data.get(position).getStationListName());
		holder.infoTextView.setText("全程"+data.get(position).getLength()+"米  耗时"+data.get(position).getTime()+"分");
		
		return convertView;	

	}

	
	public void update(List<BikeRidingLineInfo> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<BikeRidingLineInfo>();
		else
			this.data=data;
		notifyDataSetChanged();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView seqTextView;
		TextView nameTextView;
		TextView infoTextView;
	}
}

