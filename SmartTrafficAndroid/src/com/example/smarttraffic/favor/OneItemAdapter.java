package com.example.smarttraffic.favor;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.HistoryBaseAdapter;

public class OneItemAdapter extends HistoryBaseAdapter
{
	List<OneItemData> data;
	Context context;
	boolean isCanDelete;
	
	public OneItemAdapter(Context context,List<OneItemData> data)
	{
		this(context, data, false);
	}
	
	public OneItemAdapter(Context context,List<OneItemData> data,boolean isCanDelete)
	{
		this.data=data;
		this.context=context;
		this.isCanDelete=isCanDelete;
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
		
		holder.content.setText(data.get(position).getData());

		return convertView;
	}
	
	public View getCanDeleteView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.can_select_one_item_listview,null);
		holder = new ViewHolder();
		
		holder.content = (TextView) convertView.findViewById(R.id.select_one_point_list_item_text);
		holder.select=(ImageView)convertView.findViewById(R.id.select_list_item_image);
		
		holder.content.setText(data.get(position).getData());
		
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
	
	
	
	public List<OneItemData> getData()
	{
		return data;
	}

	public void changeCheck(int position)
	{
		if(isCanDelete)
			data.get(position).setSelect(!data.get(position).isSelect());
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
	
	public void update(List<OneItemData> data)
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


