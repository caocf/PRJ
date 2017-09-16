package com.example.smarttraffic.favor;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.HistoryBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TwoItemsAdapter extends HistoryBaseAdapter
{
	List<TwoItemsData> data;
	Context context;
	boolean isCanDelete;
	
	public TwoItemsAdapter(Context context,List<TwoItemsData> data)
	{
		this.data=data;
		this.context=context;
		isCanDelete=false;
	}
	
	public TwoItemsAdapter(Context context,List<TwoItemsData> data,boolean isCanDelete)
	{
		this.data=data;
		if(this.data==null)
			data=new ArrayList<TwoItemsData>();
		this.context=context;
		this.isCanDelete=isCanDelete;
	}
		
	public List<TwoItemsData> getData()
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

		convertView = LayoutInflater.from(context).inflate(R.layout.two_item_listview,null);
		holder = new ViewHolder();
		
		holder.startPoint = (TextView) convertView.findViewById(R.id.start_point_list_item_text);
		holder.endPoint=(TextView)convertView.findViewById(R.id.end_point_list_item_text);		
//		holder.betweenImageView=(ImageView)convertView.findViewById(R.id.between_list_item_image);
		
		holder.startPoint.setText(data.get(position).getStart());
		holder.endPoint.setText(data.get(position).getEnd());
		
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
	
	
	public int countSelect()
	{
		int c=0;
		for(int i=0;i<data.size();i++)
		{
			c+=(data.get(i).isSelect())?1:0;
		}
		
		return c;
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
	
	
	
	public void update(List<TwoItemsData> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<TwoItemsData>();
		else
			this.data=data;
		notifyDataSetChanged();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	@Override
	public int getHID(int position)
	{
		return data.get(position).getId();
	}
	
	@Override
	public void removeByID(int id)
	{
		this.data.remove(id);
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView startPoint;
		TextView endPoint;
		ImageView betweenImageView;
		ImageView deleteImageView;
	}
}

