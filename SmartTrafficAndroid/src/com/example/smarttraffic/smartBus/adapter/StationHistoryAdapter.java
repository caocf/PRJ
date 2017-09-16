package com.example.smarttraffic.smartBus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.HistoryBaseAdapter;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByName;
import com.example.smarttraffic.smartBus.bean.LineOnStation;

/**
 * 站点历史记录视频器
 * @author Administrator zhou
 *
 */
public class StationHistoryAdapter extends HistoryBaseAdapter
{
	List<BusStationForQueryByName> data;
	Context context;
	
	public StationHistoryAdapter(Context context,List<BusStationForQueryByName> data)
	{
		this.data=data;
		this.context=context;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_history_smart_bus_station,null);
		holder = new ViewHolder();
		
		try
		{
			holder.name=(TextView) convertView.findViewById(R.id.smart_bus_history_station_name);
			holder.lines = (TextView) convertView.findViewById(R.id.smart_bus_history_station_line);
			
			holder.name.setText(data.get(position).getName());
			String lineName="";
			for(int i=0;i<data.get(position).getList().size();i++)
			{	
				LineOnStation l=data.get(position).getList().get(i);
				
				lineName+=l.getName();
				
				if(i<data.get(position).getList().size()-1)
					lineName+=",";
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
	
	@Override
	public int getHID(int position)
	{
		return data.get(position).getHistoryID();
	}
	
	@Override
	public int getHType()
	{
		return ContentType.SMART_BUS_STATION_HISTORY;
	}
	
	@Override
	public void removeByID(int id)
	{
		data.remove(id);
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView name;
		TextView lines;
	}
}