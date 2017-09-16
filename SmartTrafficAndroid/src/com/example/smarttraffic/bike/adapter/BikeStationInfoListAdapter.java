package com.example.smarttraffic.bike.adapter;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.smartBus.GoThereMapActivity;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BikeStationInfoListAdapter extends BaseAdapter
{
	Activity context;
	List<BikeStation> data;

	public BikeStationInfoListAdapter(Activity context,List<BikeStation> data)
	{
		this.context=context;
		this.data=data;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_bike_info, null);
		
		Holder holder=new Holder();
		holder.name=(TextView)convertView.findViewById(R.id.list_bike_station_name);
		holder.address=(TextView)convertView.findViewById(R.id.list_bike_station_address);
		holder.info=(TextView)convertView.findViewById(R.id.list_bike_station_info);
		holder.imageView=(ImageView)convertView.findViewById(R.id.driving_get_there);
		
		holder.name.setText(data.get(position).getName());
		holder.address.setText(data.get(position).getAddress());
		
		String[] content=new String[]{"可借",String.valueOf(data.get(position).getLeft()),"辆  可还",String.valueOf(data.get(position).getBorrowed()),"辆"};
		String[] color=new String[]{"#000000","#ff0000","#000000","#ff0000","#000000"};
		ViewSetter.setTextviewColor(holder.info, content,color );
			
//		holder.info.setText("（可借"+data.get(position).getLeft()+"辆 可还"+data.get(position).getBorrowed()+"辆）");
		holder.imageView.setOnClickListener(new GoThereOnclick(position));
		
		return convertView;
	}

	public void refreshList(List<BikeStation> data)
	{
		this.data.clear();
		this.data.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data.clear();
		notifyDataSetChanged();
	}
	
	public List<BikeStation> getData() {
		return data;
	}
	public void setData(List<BikeStation> data) {
		this.data = data;
	}

	class Holder
	{
		public TextView name;
		public TextView address;
		public TextView info;
		public ImageView imageView;
	}
	
	class GoThereOnclick implements OnClickListener
	{
		private int position;
		
		public GoThereOnclick(int position)
		{
			this.position=position;
		}
		
		@Override
		public void onClick(View v)
		{
			Bundle bundle = new Bundle();
			bundle.putDouble(GoThereMapActivity.GO_THERE_LANTITUDE,
					data.get(position).getLatitude());
			bundle.putDouble(
					GoThereMapActivity.GO_THERE_LONGTITUDE, data
							.get(position).getLongitude());

			StartIntent.startIntentWithParam(
					context,
					GoThereMapActivity.class, bundle);
			
		}
	}
	
}
