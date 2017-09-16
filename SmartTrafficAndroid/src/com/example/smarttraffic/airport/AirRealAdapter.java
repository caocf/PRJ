package com.example.smarttraffic.airport;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AirRealAdapter extends BaseAdapter
{
	private Context context;
	private List<AirTickets> data;
	
	public AirRealAdapter(Context c,List<AirTickets> data)
	{
		this.context=c;
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
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_air_real_ticket, null);
		
		Holder tickets=new Holder();
		tickets.airNoTextView=(TextView)convertView.findViewById(R.id.air_real_no);
		tickets.airStartCityTextView=(TextView)convertView.findViewById(R.id.air_real_start_city);
		tickets.airStartTimeTextView=(TextView)convertView.findViewById(R.id.air_real_start_time);
		tickets.airStatusTextView=(TextView)convertView.findViewById(R.id.air_real_status);
		tickets.airEndCityTextView=(TextView)convertView.findViewById(R.id.air_real_end_city);
		tickets.airEndTimeTextView=(TextView)convertView.findViewById(R.id.air_real_end_time);

		tickets.airNoTextView.setText(data.get(position).getAirNumber());
		tickets.airStartCityTextView.setText(data.get(position).getStartCity().substring(0, 4));
		tickets.airStartTimeTextView.setText(data.get(position).getLeaveTime().split("T")[1]);
		tickets.airStatusTextView.setText("未知");
		tickets.airEndCityTextView.setText(data.get(position).getEndCity().substring(0, 4));
		tickets.airEndTimeTextView.setText(data.get(position).getReachTime().split("T")[1]);
			
		return convertView;
	}
	
	public void update(List<AirTickets> object)
	{
		this.data=null;
		this.data=object;
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data=new ArrayList<AirTickets>();
		notifyDataSetChanged();
	}
	
	public List<AirTickets> getData()
	{
		return data;
	}

	class Holder
	{
		public TextView airNoTextView;
		public TextView airStartCityTextView;
		public TextView airStartTimeTextView;
		public TextView airStatusTextView;
		public TextView airEndCityTextView;
		public TextView airEndTimeTextView;
		
		
	}
}
