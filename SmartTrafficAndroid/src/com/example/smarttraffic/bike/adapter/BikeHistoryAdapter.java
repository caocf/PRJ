package com.example.smarttraffic.bike.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeRiding;
import com.example.smarttraffic.common.adapter.HistoryBaseAdapter;
import com.example.smarttraffic.common.localDB.ContentType;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 历史记录列表适配器
 * @author Administrator zhou
 *
 */
public class BikeHistoryAdapter extends HistoryBaseAdapter
{
	List<BikeRiding> data;
	Context context;

	public BikeHistoryAdapter(Context context,List<BikeRiding> data)
	{
		this.data=data;
		if(this.data==null)
			data=new ArrayList<BikeRiding>();
		this.context=context;
	}
		
	public List<BikeRiding> getData()
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

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_history_trip,null);
		holder = new ViewHolder();
		
		holder.startPoint = (TextView) convertView.findViewById(R.id.start_point_list_item_text);
		holder.endPoint=(TextView)convertView.findViewById(R.id.end_point_list_item_text);		
		
		holder.startPoint.setText(data.get(position).getStart());
		holder.endPoint.setText(data.get(position).getEnd());
				
		return convertView;
	}

	
	public void update(List<BikeRiding> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<BikeRiding>();
		else
			this.data=data;
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
		return ContentType.BIKE_RIDING_DRIVING;
	}
	
	@Override
	public void removeByID(int id)
	{
		data.remove(id);
		update();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView startPoint;
		TextView endPoint;
	}
}

