package com.example.smarttraffic.smartBus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.smartBus.SmartBusLineDetailActivity;
import com.example.smarttraffic.smartBus.bean.BusLocation;
import com.example.smarttraffic.smartBus.bean.LineOfStation;
import com.example.smarttraffic.smartBus.bean.LineOnStation;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;

/**
 * 站点线路适配器
 * @author Administrator zhou
 *
 */
public class StationLineAdapter extends BaseAdapter
{
	List<LineOnStation> data;
	Activity activity;
	
	public StationLineAdapter(Activity activity,List<LineOnStation> data)
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

		convertView = LayoutInflater.from(activity).inflate(R.layout.listview_smart_bus_station_for_line,null);
		holder = new ViewHolder();
		
		try
		{
			holder.name=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_name);
			holder.start=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_start);
			holder.end=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_end);
			
			holder.length=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_length);
			holder.crowed=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_crowed);
			holder.speed=(TextView)convertView.findViewById(R.id.listview_smart_bus_station_line_speed);
			
			holder.onLine=(ImageView)convertView.findViewById(R.id.listview_smart_bus_station_line_online);
		
			
			holder.name.setText(data.get(position).getName());
			holder.start.setText("("+data.get(position).getStartStationName().replaceAll("\\d+", ""));
			holder.end.setText(data.get(position).getEndStationName().replaceAll("\\d+", "")+")");
			
			BusLocation busLocation=new BusLocation();
			if(data.get(position).getBusLocationList()!=null && data.get(position).getBusLocationList().size()>0)
				busLocation=data.get(position).getBusLocationList().get(0);
			
			if(busLocation.getDriveState()==0)
				holder.length.setText("当前线路无实时信息");
			else
				holder.length.setText("下路公交车还有"+busLocation.getDistance()+"米到站");
			
			int[] color=new int[]{0xff000000,0xff00ff00,0xff00ff00,0xffff0000};
			holder.crowed.setText(LineOfStation.STRING_CROWED[busLocation.getCongestionDegree()]);
			ViewSetter.setTextColor(holder.crowed, color, busLocation.getCongestionDegree());
			
			holder.speed.setText(LineOfStation.STRING_SPEED[busLocation.getDriveState()]);
			ViewSetter.setTextColor(holder.speed, color, busLocation.getDriveState());
			
			holder.onLine.setOnClickListener(new gothere(data.get(position).getId(),data.get(position).getName()));
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
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	
	class gothere implements OnClickListener
	{
		int id;
		String name;
		public gothere(int id,String name)
		{
			this.id=id;
			this.name=name;
		}
		@Override
		public void onClick(View v)
		{
			Bundle bundle=new Bundle();
			bundle.putInt(SmartBusLineDetailActivity.SMART_BUS_LINE_ID, id);
			bundle.putString(SmartBusLineDetailActivity.SMART_BUS_LINE_NAME, name);
			
			StartIntent.startIntentWithParam(activity, SmartBusLineDetailActivity.class, bundle);
			
		}
		
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