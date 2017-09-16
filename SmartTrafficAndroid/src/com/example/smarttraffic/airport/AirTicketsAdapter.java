package com.example.smarttraffic.airport;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.util.ViewSetter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AirTicketsAdapter extends BaseAdapter
{
	private Context context;
	private List<AirTickets> data;
	private int kind;    			//0时刻表 2：余票
		
	public int getKind()
	{
		return kind;
	}

	public void setKind(int kind)
	{
		this.kind = kind;
	}

	public AirTicketsAdapter(Context c,List<AirTickets> data)
	{
		this(c, data, 0);
	}
	
	public AirTicketsAdapter(Context c,List<AirTickets> data,int kind)
	{
		this.context=c;
		this.data=data;
		this.kind=kind;
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
		Holder tickets=null;
		if(convertView==null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_air_ticket, null);
			
			tickets=new Holder();
			tickets.airID=(TextView)convertView.findViewById(R.id.train_search_id);
			tickets.airKind=(TextView)convertView.findViewById(R.id.train_search_kind);
			tickets.startCity=(TextView)convertView.findViewById(R.id.train_search_startcity);
			tickets.endCity=(TextView)convertView.findViewById(R.id.train_search_endcity);
			tickets.startTime=(TextView)convertView.findViewById(R.id.train_search_starttime);
			tickets.endTime=(TextView)convertView.findViewById(R.id.train_search_endtime);
			
			if(kind==2)
			{
				convertView.findViewById(R.id.air_tickets_layout).setVisibility(View.VISIBLE);
				tickets.ecnomy=(TextView)convertView.findViewById(R.id.train_search_first);
				tickets.business=(TextView)convertView.findViewById(R.id.train_search_second);
				tickets.top=(TextView)convertView.findViewById(R.id.train_search_business);
			}
			tickets.costTime=(TextView)convertView.findViewById(R.id.search_air_ticket_cost);
			
			convertView.setTag(tickets);
		}
		else
		{
			tickets=(Holder)convertView.getTag();
		}
		
		tickets.airID.setText(data.get(position).getAirNumber());
		tickets.airKind.setText(data.get(position).getAirKind());
		tickets.startCity.setText(data.get(position).getStartCity().substring(0,4));
		tickets.endCity.setText(data.get(position).getEndCity().substring(0, 4));
		tickets.startTime.setText(data.get(position).getLeaveTime().split("T")[1]);
		tickets.endTime.setText(data.get(position).getReachTime().split("T")[1]);
		
		if(kind==2)
		{
			int left=data.get(position).getEconomy();
			int[] limit=new int[]{5,15};
			int[] color=new int[]{0xffff0000,0xffffff00};
			
			ViewSetter.setTextColor(tickets.ecnomy, limit, color, left);
			tickets.ecnomy.setText(String.valueOf(left));
			
			left=data.get(position).getBusiness();
			ViewSetter.setTextColor(tickets.business, limit, color, left);
			tickets.business.setText(String.valueOf(left));
			
			left=data.get(position).getTop();
			ViewSetter.setTextColor(tickets.top, limit, color, left);
			tickets.top.setText(String.valueOf(left));
		}
		long time=data.get(position).getTime();
		
		if(time<0)
		{
			time+=24*60;
		}
		
		if(time<60)
		{
			tickets.costTime.setText(String.valueOf(time)+"分");
		}
		else
		{
			tickets.costTime.setText(String.valueOf(time/60)+"小时"+String.valueOf(time%60)+"分");
		}
		
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
		public TextView airID;
		public TextView airKind;
		public TextView startCity;
		public TextView endCity;
		public TextView startTime;
		public TextView endTime;
		public TextView costTime;
		public TextView ecnomy;
		public TextView business;
		public TextView top;
	}
}
