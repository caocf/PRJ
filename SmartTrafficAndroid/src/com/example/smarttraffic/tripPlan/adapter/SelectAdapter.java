package com.example.smarttraffic.tripPlan.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttraffic.R;

/**
 * 偏好选择适配器
 * @author Administrator zhou
 *
 */
public class SelectAdapter extends BaseAdapter
{
	List<String> data;
	Context context;
	boolean isCanDelete;
	int selectID;
	
	public SelectAdapter(Context context,List<String> data)
	{
		this(context, data, false);
	}
	
	public SelectAdapter(Context context,List<String> data,boolean isCanDelete)
	{
		this(context, data,isCanDelete,0);
	}
	
	public SelectAdapter(Context context,List<String> data,boolean isCanDelete,int selectID)
	{
		this.data=data;
		this.context=context;
		this.isCanDelete=isCanDelete;
		this.selectID=selectID;
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

		convertView = LayoutInflater.from(context).inflate(R.layout.one_item_listview,null);
		holder = new ViewHolder();
		
		holder.content = (TextView) convertView.findViewById(R.id.start_one_point_list_item_text);
		
		holder.content.setText(data.get(position));

		return convertView;
	}
	
	public View getCanDeleteView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.can_select_one_item_listview,null);
		holder = new ViewHolder();
		
		holder.content = (TextView) convertView.findViewById(R.id.select_one_point_list_item_text);
		holder.select=(ImageView)convertView.findViewById(R.id.select_list_item_image);
		
		holder.content.setText(data.get(position));
		
		if(position==selectID)
		{
			holder.select.setImageResource(R.drawable.item_check);
		}
		else
		{
			holder.select.setImageResource(R.drawable.item_uncheck);
		}
		
		return convertView;
	}
	
	public void changeCheck(int position)
	{
		if(isCanDelete)
			selectID=position;
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView content;
		ImageView select;
	}
}


