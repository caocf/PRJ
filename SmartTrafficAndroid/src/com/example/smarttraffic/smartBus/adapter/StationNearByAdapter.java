package com.example.smarttraffic.smartBus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.smartBus.GoThereMapActivity;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByName;
import com.example.smarttraffic.smartBus.bean.LineOnStation;
import com.example.smarttraffic.util.StartIntent;

/**
 * 站点历史记录视频器
 * @author Administrator zhou
 *
 */
public class StationNearByAdapter extends BaseAdapter
{
	List<BusStationForQueryByName> data;
	Activity context;
	boolean goThere;
	boolean isShowDistance;
	
	public StationNearByAdapter(Activity context,List<BusStationForQueryByName> data)
	{
		this(context, data, false,false);
	}
	
	public StationNearByAdapter(Activity context,List<BusStationForQueryByName> data,boolean showDistance)
	{
		this(context, data, false,showDistance);
	}
	
	
	public StationNearByAdapter(Activity context,List<BusStationForQueryByName> data,boolean gothere,boolean showDistance)
	{
		this.data=data;
		this.context=context;
		this.goThere=gothere;
		this.isShowDistance=showDistance;
	}
	
	public List<BusStationForQueryByName> getData()
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

	int p;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;

		p=position;
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_smart_bus_nearby_station,null);
		holder = new ViewHolder();
		
		try
		{
			holder.name=(TextView) convertView.findViewById(R.id.smart_bus_history_station_name);
			holder.lines = (TextView) convertView.findViewById(R.id.smart_bus_history_station_line);
			holder.distace=(TextView)convertView.findViewById(R.id.smart_bus_history_station_distance);
			
			if(goThere==true)
			{
				holder.imageView=(ImageView)convertView.findViewById(R.id.smart_bus_history_station_go_there);
				holder.imageView.setVisibility(View.VISIBLE);
				
				holder.imageView.setOnClickListener(new View.OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						Bundle bundle=new Bundle();
						bundle.putDouble(GoThereMapActivity.GO_THERE_LANTITUDE, data.get(p).getLatitude());
						bundle.putDouble(GoThereMapActivity.GO_THERE_LONGTITUDE,data.get(p).getLongitude());
						
						StartIntent.startIntentWithParam(context, GoThereMapActivity.class, bundle);
						
					}
				});
			}
			
			holder.name.setText(data.get(position).getName());
			if(isShowDistance)
			{
				holder.distace.setVisibility(View.VISIBLE);
				holder.distace.setText(data.get(position).getDistance()+"米");
			}
			else
				holder.distace.setVisibility(View.GONE);
			String lineName="";
			
			for(int i=0;i<data.get(position).getList().size();i++)
			{
				
				if(i>0)
					lineName+=",";
				LineOnStation l=data.get(position).getList().get(i);
				lineName+=l.getName();
				
			}
			holder.lines.setText(lineName);
		
		}
		catch(Exception e)
		{
			
		}
		return convertView;
	}
	
	public void update(List<BusStationForQueryByName> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<BusStationForQueryByName>();
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
		ImageView imageView;
		TextView name;
		TextView lines;
		TextView distace;
	}
}