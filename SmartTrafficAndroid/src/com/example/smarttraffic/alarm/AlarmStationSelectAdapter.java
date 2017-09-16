package com.example.smarttraffic.alarm;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.smartBus.bean.StationOnLine;

public class AlarmStationSelectAdapter extends BaseAdapter
{
	List<StationOnLine> data;
	Context context;
	
	public AlarmStationSelectAdapter(Context context,List<StationOnLine> data)
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
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.can_select_one_item_listview,null);
		holder = new ViewHolder();
		
		holder.content = (TextView) convertView.findViewById(R.id.select_one_point_list_item_text);
		holder.select=(ImageView)convertView.findViewById(R.id.select_list_item_image);
		
		holder.content.setText(data.get(position).getStationName());
		
		if(data.get(position).isSelect())
		{
			holder.select.setImageResource(R.drawable.item_check);
		}
		else
		{
			holder.select.setImageResource(R.drawable.item_uncheck);
		}
		
		return convertView;
	}
	
	
	public List<StationOnLine> getData()
	{
		return data;
	}

	public void changeCheck(int position)
	{
		data.get(position).setSelect(!data.get(position).isSelect());
		notifyDataSetChanged();
	}
	

	public void update(List<StationOnLine> data)
	{
		this.data=null;
		this.data=data;
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView content;
		ImageView select;
	}
}


