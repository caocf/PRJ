package com.example.smarttraffic.smartBus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.smartBus.bean.Transfer;

/**
 * 换乘收藏视频器
 * @author Administrator zhou
 *
 */
public class TransferAdapter extends BaseAdapter
{
	List<Transfer> data;
	Context context;
	boolean isCanDelete;			//是否可删除
	
	public TransferAdapter(Context context,List<Transfer> data)
	{
		this.data=data;
		this.context=context;
		isCanDelete=false;
	}
	
	public TransferAdapter(Context context,List<Transfer> data,boolean isCanDelete)
	{
		this.data=data;
		if(this.data==null)
			data=new ArrayList<Transfer>();
		this.context=context;
		this.isCanDelete=isCanDelete;
	}
		
	public List<Transfer> getData()
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
		holder=new ViewHolder();

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_smart_bus_transfer,null);

		holder.name=(TextView)convertView.findViewById(R.id.listview_transfer_name);
		holder.content=(TextView)convertView.findViewById(R.id.listview_transfer_content);
		
		Transfer transfer=data.get(position);
		
		String name="";
		for(String s:transfer.getLineList())
			name+=s+"->";
		
		name=name.substring(0, name.length()-2);
		
		holder.name.setText(name);
		holder.content.setText("全程"+transfer.getDistance()+"米/"+"耗时"+transfer.getTime()+"分");
		
		return convertView;
	}
	
	
	
	
	public void update(List<Transfer> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<Transfer>();
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
		TextView name;
		TextView content;
	}
}