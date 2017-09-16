package com.example.smarttraffic.carRepair;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.favor.TwoItemsData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavorAdapter extends BaseAdapter
{
	Context context;
	List<TwoItemsData> data;
	int kind;

	
	public List<TwoItemsData> getData()
	{
		return data;
	}

	public FavorAdapter(Context context,List<TwoItemsData> data)
	{
		this(context,data,1);	
	}
	
	public FavorAdapter(Context context,List<TwoItemsData> data,int kind)
	{
		this.context=context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_school_favor, null);
		
		Holder holder=new Holder();
		holder.name=(TextView)convertView.findViewById(R.id.favor_name);
		holder.address=(TextView)convertView.findViewById(R.id.favor_address);
		holder.imageView=(ImageView)convertView.findViewById(R.id.favor_select);
		
		holder.name.setText(data.get(position).getStart());
		holder.address.setText(data.get(position).getEnd());
		
		if(kind==2)
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

	public void refreshList(List<TwoItemsData> data)
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
	
	public int countSelect()
	{
		int i=0;
		
		for(int j=0;j<data.size();j++)
			i+=data.get(j).isSelect()?1:0;
		
		return i;
	}
	
	public void changeCheck(int position)
	{
		if(kind==2)
			data.get(position).setSelect(!data.get(position).isSelect());
		notifyDataSetChanged();
	}
	
	class Holder
	{
		public TextView name;
		public TextView address;
		public ImageView imageView;
	}
}
