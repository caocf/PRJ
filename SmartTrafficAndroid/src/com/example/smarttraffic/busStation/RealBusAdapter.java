package com.example.smarttraffic.busStation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smarttraffic.R;

public class RealBusAdapter extends BaseAdapter
{
	private Context context;
	private List<BusOrder> data;
	private int kind;
	
	public RealBusAdapter(Context c,List<BusOrder> data)
	{
		this(c, data,1);
	}
	
	public RealBusAdapter(Context c,List<BusOrder> data,int kind)
	{
		this.context=c;
		this.data=data;
		this.kind=kind;
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
		if(kind==1)
		{
			
			
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_bus_realtime, null);
			
			Holder tickets=new Holder();
			tickets.firstTextView=(TextView)convertView.findViewById(R.id.listview_first);
			tickets.secondTextView=(TextView)convertView.findViewById(R.id.listview_second);
			tickets.thirdTextView=(TextView)convertView.findViewById(R.id.listview_third);
			tickets.forthTextView=(TextView)convertView.findViewById(R.id.listview_forth);
	
			if(position==0)
			{
				tickets.firstTextView.setText("车辆");
				tickets.secondTextView.setText("计划发车");
				tickets.thirdTextView.setText("实际发车");
				tickets.forthTextView.setText("实际到达");
			}
			else
			{
				tickets.firstTextView.setText("第"+String.valueOf(data.get(position-1).getOrder())+"辆");
				tickets.secondTextView.setText(data.get(position-1).getPlanStartTime().split("T")[1]);
				tickets.thirdTextView.setText(data.get(position-1).getRealStartTime().split("T")[1]);
				tickets.forthTextView.setText(data.get(position-1).getRealEndTime().split("T")[1]);
			}
		}
		else if(kind==2)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_bus_time, null);
			
			Holder tickets=new Holder();
			tickets.firstTextView=(TextView)convertView.findViewById(R.id.listview_first);
			tickets.secondTextView=(TextView)convertView.findViewById(R.id.listview_second);
	
			if(position==0)
			{
				tickets.firstTextView.setText("车辆");
				tickets.secondTextView.setText("发车时间");
			}
			else
			{
				tickets.firstTextView.setText("第"+String.valueOf(data.get(position-1).getOrder())+"辆");
				tickets.secondTextView.setText(data.get(position-1).getPlanStartTime().split("T")[1]);
			}
		}
		return convertView;
	}
	
	public void update(List<BusOrder> object)
	{
		this.data=null;
		this.data=object;
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data=new ArrayList<BusOrder>();
		notifyDataSetChanged();
	}
	
	class Holder
	{
		public TextView firstTextView;
		public TextView secondTextView;
		public TextView thirdTextView;
		public TextView forthTextView;

	}
}
