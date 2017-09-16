package com.example.smarttraffic.alarm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.smartBus.bean.LineOfStation;
import com.example.smarttraffic.smartBus.bean.LineOnStation;
import com.example.smarttraffic.util.ViewSetter;

public class AlarmLineSelectAdapter extends BaseAdapter
{
	List<LineOnStation> data;
	Activity activity;
	
	public AlarmLineSelectAdapter(Activity activity,List<LineOnStation> data)
	{
		this.data=data;
		this.activity=activity;
	}
	
	public List<LineOnStation> getData()
	{
		return data;
	}

	@Override
	public int getCount() {
		return data.size()+1;
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

		convertView = LayoutInflater.from(activity).inflate(R.layout.listview_smart_bus_station_for_line,null);
		holder = new ViewHolder();
		
		try
		{
			if(position==0)
			{
				TextView textView=new TextView(activity);
				textView.setPadding(10, 10, 10, 10);
				textView.setText("途径线路");
				
				return textView;
			}
			else
			{			
				holder.name=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_name);
				holder.start=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_start);
				holder.end=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_end);
				
				holder.length=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_length);
				holder.crowed=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_crowed);
				holder.speed=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_speed);
				
				holder.onLine=(ImageView)convertView.findViewById(R.id.listview_smart_bus_station_line_online);
			
				if(data.get(position-1).isSelect())
					holder.onLine.setImageResource(R.drawable.item_check);
				else
					holder.onLine.setImageResource(R.drawable.item_uncheck);
				
				holder.name.setText(data.get(position-1).getName());
				holder.start.setText("("+data.get(position-1).getStartStationName());
				holder.end.setText(data.get(position-1).getEndStationName()+")");
				
				if(data.get(position-1).getBusLocationList()!=null&&data.get(position-1).getBusLocationList().size()>0)
				{
					holder.length.setText("下路公交车还有"+data.get(position-1).getBusLocationList().get(0).getDistance()+"米到站");
					
					int[] color=new int[]{0xff00ff00,0xffffff00,0xffff0000};
					holder.crowed.setText(LineOfStation.STRING_CROWED[data.get(position-1).getBusLocationList().get(0).getCongestionDegree()]);
					ViewSetter.setTextColor(holder.crowed, color, data.get(position-1).getBusLocationList().get(0).getCongestionDegree());
					
					holder.speed.setText(LineOfStation.STRING_SPEED[data.get(position-1).getBusLocationList().get(0).getDriveState()]);
					ViewSetter.setTextColor(holder.speed, color, data.get(position-1).getBusLocationList().get(0).getDriveState());
				}
			}
		}
		catch(Exception e)
		{
			
		}
		return convertView;
	}
	
	public void update(List<LineOnStation> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<LineOnStation>();
		else
			this.data=data;
		notifyDataSetChanged();
	}
	
	public void change(int num)
	{
		data.get(num-1).setSelect(!data.get(num-1).isSelect());
		update();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView name;
		TextView start;
		TextView end;
		
		TextView length;
		TextView crowed;
		TextView speed;
		
		ImageView onLine;
	}
}