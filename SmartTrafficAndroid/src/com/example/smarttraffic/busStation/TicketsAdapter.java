package com.example.smarttraffic.busStation;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TicketsAdapter extends BaseAdapter
{
	private Context context;
	private List<Ticket> data;
	private int kind;    			//0余票
	
	public TicketsAdapter(Context c,List<Ticket> data)
	{
		this(c, data, 0);
	}

	public TicketsAdapter(Context c,List<Ticket> data,int kind)
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
		convertView = LayoutInflater.from(context).inflate(R.layout.bus_tickets_listview, null);
		
		Holder tickets=new Holder();
		tickets.timer=(TextView)convertView.findViewById(R.id.bus_tickets_time);
		tickets.type=(TextView)convertView.findViewById(R.id.bus_tickets_type);
		tickets.start=(TextView)convertView.findViewById(R.id.bus_tickets_start);
		tickets.end=(TextView)convertView.findViewById(R.id.bus_tickets_end);
		tickets.price=(TextView)convertView.findViewById(R.id.bus_tickets_price);
		tickets.left=(TextView)convertView.findViewById(R.id.bus_tickets_left);
		
		tickets.timer.setText(data.get(position).getLeaveTime());
		tickets.type.setText(data.get(position).getCarNumber());
		tickets.start.setText(data.get(position).getStartCity());
		tickets.end.setText(data.get(position).getEndCity());
		tickets.price.setText("￥"+String.valueOf(data.get(position).getPrice()));
		
		if(kind==0)
		{
			tickets.left.setText("余"+String.valueOf(data.get(position).getLeaveTicketNumber()));
		}
		else 
		{
			tickets.left.setText(String.valueOf(data.get(position).getLength())+"km");
		}
		
		return convertView;
	}
	
	public void update(List<Ticket> object)
	{
		this.data=null;
		this.data=object;
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data=new ArrayList<Ticket>();
		notifyDataSetChanged();
	}
	
	
	public List<Ticket> getData() {
		return data;
	}

	class Holder
	{
		public TextView timer;
		public TextView type;
		public TextView start;
		public TextView end;
		public TextView price;
		public TextView left;
	}
}
