package com.example.smarttraffic.train;

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

/**
 * 铁路车票信息适配器
 * @author Administrator zhou
 *
 */
public class TrainTicketsAdapter extends BaseAdapter
{
	private Context context;
	private List<TrainTickets> data;
	private int kind;    			//0余票
	
	
	public int getKind()
	{
		return kind;
	}

	public TrainTicketsAdapter(Context c,List<TrainTickets> data)
	{
		this(c, data, 0);
	}

	public TrainTicketsAdapter(Context c,List<TrainTickets> data,int kind)
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
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_train_ticket, null);
			
			tickets=new Holder();
			tickets.trainID=(TextView)convertView.findViewById(R.id.train_search_id);
			tickets.trainKind=(TextView)convertView.findViewById(R.id.train_search_kind);
			tickets.startCity=(TextView)convertView.findViewById(R.id.train_search_startcity);
			tickets.endCity=(TextView)convertView.findViewById(R.id.train_search_endcity);
			tickets.startTime=(TextView)convertView.findViewById(R.id.train_search_starttime);
			tickets.endTime=(TextView)convertView.findViewById(R.id.train_search_endtime);
			tickets.costTime=(TextView)convertView.findViewById(R.id.train_tickets_cost_time);
			
			if(kind==2)
			{
				convertView.findViewById(R.id.train_ticket_price_linear).setVisibility(View.VISIBLE);
				tickets.first=(TextView)convertView.findViewById(R.id.train_search_first);
				tickets.second=(TextView)convertView.findViewById(R.id.train_search_second);
				tickets.business=(TextView)convertView.findViewById(R.id.train_search_business);
				tickets.other=(TextView)convertView.findViewById(R.id.train_search_other);
			}
			
			convertView.setTag(tickets);
		}
		else
		{
			tickets=(Holder)convertView.getTag();
		}
		tickets.trainID.setText(data.get(position).getTrainNumber());
		tickets.trainKind.setText(data.get(position).getTrainKind());
		tickets.startCity.setText(data.get(position).getStartCity());
		tickets.endCity.setText(data.get(position).getEndCity());
		tickets.startTime.setText(data.get(position).getLeaveTime());
		tickets.endTime.setText(data.get(position).getReachTime());
		

		
		tickets.costTime.setText(data.get(position).getCostTime());

		if(kind==2)
		{
			int[] limit=new int[]{5,15};
			int[] color=new int[]{0xffff0000,0xffffff00};
			
			int left=data.get(position).getFirst();
			ViewSetter.setTextColor(tickets.first, limit, color, left);		
			tickets.first.setText(String.valueOf(data.get(position).getFirst()));
			
			left=data.get(position).getSecond();
			ViewSetter.setTextColor(tickets.second, limit, color, left);	
			tickets.second.setText(String.valueOf(data.get(position).getSecond()));
			
			left=data.get(position).getBusiness();
			ViewSetter.setTextColor(tickets.business, limit, color, left);	
			tickets.business.setText(String.valueOf(data.get(position).getBusiness()));
			
			left=data.get(position).getOther();
			ViewSetter.setTextColor(tickets.other, limit, color, left);	
			tickets.other.setText(String.valueOf(data.get(position).getOther()));
		}
		
		return convertView;
	}
	
	
	
	public List<TrainTickets> getData()
	{
		return data;
	}

	public void update(List<TrainTickets> object)
	{
		this.data=null;
		this.data=object;
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data=new ArrayList<TrainTickets>();
		notifyDataSetChanged();
	}
	
	class Holder
	{
		public TextView trainID;
		public TextView trainKind;
		public TextView startCity;
		public TextView endCity;
		public TextView startTime;
		public TextView endTime;
		public TextView costTime;
		public TextView first;
		public TextView second;
		public TextView business;
		public TextView other;
	}
}
