package com.example.smarttraffic.smartBus.adapter;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.smartBus.bean.StationInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 收藏站点适配器
 * @author Administrator zhou
 *
 */
public class StationAdapter extends BaseAdapter
{
	Context context;
	List<StationInfo> data;
	boolean delete;					//是否删除

	
	public List<StationInfo> getData()
	{
		return data;
	}

	public StationAdapter(Context context,List<StationInfo> data)
	{
		this(context,data,false);	
	}
	
	public StationAdapter(Context context,List<StationInfo> data,boolean delete)
	{
		this.context=context;
		this.data=data;
		this.delete=delete;
		
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
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_bus_station_favor, null);
		
		Holder holder=new Holder();
		holder.name=(TextView)convertView.findViewById(R.id.favor_name);
		holder.line=(TextView)convertView.findViewById(R.id.favor_address);
		holder.imageView=(ImageView)convertView.findViewById(R.id.favor_select);
		
		holder.name.setText(data.get(position).getName());
		
		String temp="";
		String[] tempsStrings=data.get(position).getLines();
		
		if(tempsStrings==null || tempsStrings.length==0)
		{
		}
		else
		{
			for(int i=0;i<tempsStrings.length;i++)
			{
				if(i>0)
					temp+="，";
				
				temp+=tempsStrings[i];
			}
		}
		holder.line.setText(temp);
		
		if(delete)
		{
			holder.imageView.setVisibility(android.view.View.VISIBLE);
			
			if(data.get(position).isSelect())
			{
				holder.imageView.setImageResource(R.drawable.item_check);
			}
			else 
			{
				holder.imageView.setImageResource(R.drawable.item_uncheck);
			}
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
	
	public void refreshList(List<StationInfo> data)
	{
		this.data=data;
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data.clear();
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
	
	public void changeCheck(int position)
	{
		if(delete)
			data.get(position).setSelect(!data.get(position).isSelect());
		notifyDataSetChanged();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	class Holder
	{
		public TextView name;
		public TextView line;
		public ImageView imageView;
	}
}
