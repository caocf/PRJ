package com.example.smarttraffic.smartBus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeStation;

/**
 * 站点历史记录视频器
 * @author Administrator zhou
 *
 */
public class BikeStationHistoryAdapter extends BaseAdapter
{
	List<BikeStation> data;
	Context context;
	
	boolean hiddenPic;
	
	public BikeStationHistoryAdapter(Context context,List<BikeStation> data)
	{
		this.data=data;
		this.context=context;
	}
	
	public BikeStationHistoryAdapter(Context context,List<BikeStation> data,boolean hiddenPic)
	{
		this.data=data;
		this.context=context;
		this.hiddenPic=hiddenPic;
	}
	
	
	public List<BikeStation> getData()
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

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_smart_bus_nearby_station,null);
		holder = new ViewHolder();
		
		try
		{
			holder.name=(TextView) convertView.findViewById(R.id.smart_bus_history_station_name);
			holder.lines = (TextView) convertView.findViewById(R.id.smart_bus_history_station_line);
			
			holder.name.setText(data.get(position).getName());
			holder.lines.setText(data.get(position).getAddress());		
			
			if(hiddenPic)
				convertView.findViewById(R.id.smart_bus_history_station_image).setVisibility(View.GONE);
			
		}
		catch(Exception e)
		{
			
		}
		return convertView;
	}
	
	public void update(List<BikeStation> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<BikeStation>();
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
		TextView name;
		TextView lines;
	}
}