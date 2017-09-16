package com.example.smarttraffic.bike.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeFavor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BikeRideAdapter extends BaseAdapter
{
	List<BikeFavor> data;
	Context context;
	boolean isCanDelete;
	
	public BikeRideAdapter(Context context,List<BikeFavor> data)
	{
		this.data=data;
		this.context=context;
		isCanDelete=false;
	}
	
	public BikeRideAdapter(Context context,List<BikeFavor> data,boolean isCanDelete)
	{
		this.data=data;
		if(this.data==null)
			data=new ArrayList<BikeFavor>();
		this.context=context;
		this.isCanDelete=isCanDelete;
	}
		
	public List<BikeFavor> getData()
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
		if(isCanDelete)
		{
			return getCanDeleteView(position, convertView, parent);
		}
		else
		{
			return getNoDeleteView(position, convertView, parent);		
		}
	}
	
	public View getNoDeleteView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_bike_riding_favor,null);
		holder = new ViewHolder();
		
		holder.startPoint = (TextView) convertView.findViewById(R.id.start_point_list_item_text);
		holder.endPoint=(TextView)convertView.findViewById(R.id.end_point_list_item_text);		
		holder.infoTextView=(TextView)convertView.findViewById(R.id.list_bike_favor_info);
//		holder.betweenImageView=(ImageView)convertView.findViewById(R.id.between_list_item_image);
		
		holder.startPoint.setText(data.get(position).getStart());
		holder.endPoint.setText(data.get(position).getEnd());
		holder.infoTextView.setText("行程"+data.get(position).getLength()+"米 用时"+data.get(position).getTime()+"分");
		
		return convertView;
	}
	
	public View getCanDeleteView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.can_select_two_item_listview,null);
		holder = new ViewHolder();
		
		holder.startPoint = (TextView) convertView.findViewById(R.id.delete_start_point_list_item_text);
		holder.endPoint=(TextView)convertView.findViewById(R.id.delete_end_point_list_item_text);		
//		holder.betweenImageView=(ImageView)convertView.findViewById(R.id.delete_between_list_item_image);
		holder.deleteImageView=(ImageView)convertView.findViewById(R.id.delete_select_list_item_image);
		
		holder.startPoint.setText(data.get(position).getStart());
		holder.endPoint.setText(data.get(position).getEnd());
		
		if(data.get(position).isSelect())
		{
			holder.deleteImageView.setImageResource(R.drawable.item_check);
		}
		else
		{
			holder.deleteImageView.setImageResource(R.drawable.item_uncheck);
		}
		
		return convertView;
	}
	
	public void changeCheck(int position)
	{
		if(isCanDelete)
			data.get(position).setSelect(!data.get(position).isSelect());
		notifyDataSetChanged();
	}
	
	public void selectALL(boolean select)
	{
		for(int i=0;i<data.size();i++)
		{
			data.get(i).setSelect(select);
		}
		notifyDataSetChanged();
	}
	
	public int countSelect()
	{
		int c=0;
		for(int i=0;i<data.size();i++)
		{
			c+=(data.get(i).isSelect())?1:0;
		}
		
		return c;
	}
	
	public void update(List<BikeFavor> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<BikeFavor>();
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
		TextView startPoint;
		TextView endPoint;
		
		TextView infoTextView;
		
		ImageView betweenImageView;
		ImageView deleteImageView;
		
		
	}
}

