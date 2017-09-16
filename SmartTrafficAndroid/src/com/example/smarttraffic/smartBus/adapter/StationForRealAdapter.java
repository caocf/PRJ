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
import com.example.smarttraffic.smartBus.bean.BusLocation;
import com.example.smarttraffic.smartBus.bean.LineOfStation;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.ViewSetter;

/**
 * 站点线路实时信息适配器
 * @author Administrator zhou
 *
 */
public class StationForRealAdapter extends BaseAdapter
{
	List<BusLocation> data;
	Context context;
	
	public StationForRealAdapter(Context context,List<BusLocation> data)
	{
		this.data=data;
		this.context=context;
	}
	
	public List<BusLocation> getData()
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

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_smart_station_for_real,null);
		holder = new ViewHolder();
		
		try
		{
			holder.num=(TextView) convertView.findViewById(R.id.listview_real_1);
			holder.distance = (TextView) convertView.findViewById(R.id.listview_real_2);
			holder.crowd=(TextView) convertView.findViewById(R.id.listview_real_3);
			holder.speed = (TextView) convertView.findViewById(R.id.listview_real_4);
			
			if(position==0)
			{
				holder.num.setText("车牌");
				holder.distance.setText("离站距离");
				holder.crowd.setText("拥挤度");
				holder.speed.setText("行驶状态");
			}
			else 
			{
				holder.num.setText(data.get(position-1).getCarNumber());
				
				String dis="% 5d米/% 2d站";
				String dis2=DoString.format(dis, new Object[]{data.get(position-1).getDistance(),data.get(position-1).getStationIndex()});
				
				holder.distance.setText(dis2);
//				holder.distance.setText(data.get(position-1).getDistance()+"米/"+data.get(position-1).getStationIndex()+"站");

				int[] color=new int[]{0xff000000,0xff00ff00,0xff00ff00,0xffff0000};
				holder.crowd.setText(LineOfStation.STRING_CROWED[data.get(position-1).getCongestionDegree()]);
				ViewSetter.setTextColor(holder.crowd, color, data.get(position-1).getCongestionDegree());
				
				holder.speed.setText(LineOfStation.STRING_SPEED[data.get(position-1).getDriveState()]);
				ViewSetter.setTextColor(holder.speed, color, data.get(position-1).getDriveState());
			}
		
		}
		catch(Exception e)
		{
			
		}
		return convertView;
	}
	
	public void update(List<BusLocation> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<BusLocation>();
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
		TextView num;
		TextView distance;
		TextView crowd;
		TextView speed;
	}
}