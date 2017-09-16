package com.example.smarttraffic.nearby;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.smartBus.GoThereMapActivity;
import com.example.smarttraffic.util.StartIntent;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NearbyDetailListAdapter extends BaseAdapter
{
	Activity context;
	List<NearBy> data;
	boolean hasGone;

	public NearbyDetailListAdapter(Activity context,List<NearBy> data)
	{
		this(context, data, true);
	}
	
	public NearbyDetailListAdapter(Activity context,List<NearBy> data,boolean hasGone)
	{
		this.context=context;
		this.data=data;
		this.hasGone=hasGone;
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
		convertView = LayoutInflater.from(context).inflate(R.layout.driving_school_listview, null);
		
		Holder holder=new Holder();
		holder.name=(TextView)convertView.findViewById(R.id.driving_school_name);
		holder.content=(TextView)convertView.findViewById(R.id.driving_school_information);
			
		holder.name.setText(data.get(position).getName());
		holder.content.setText(data.get(position).getAddress());
		
		if(hasGone)
		{
			holder.imageView=(ImageView)convertView.findViewById(R.id.driving_get_there);
			holder.imageView.setOnClickListener(new GoThereOnclick(position));
		}
		else
		{
			holder.imageView=(ImageView)convertView.findViewById(R.id.driving_get_there);
			holder.imageView.setVisibility(View.GONE);
		}
		
		return convertView;
	}

	public void refreshList(List<NearBy> data)
	{
		this.data.clear();
		this.data.addAll(data);
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data.clear();
		notifyDataSetChanged();
	}
	
	
	
	public List<NearBy> getData() {
		return data;
	}
	public void setData(List<NearBy> data) {
		this.data = data;
	}

	class Holder
	{
		public TextView name;
		public TextView content;
		public ImageView imageView;
	}
	
	
	class GoThereOnclick implements OnClickListener
	{
		private int position;
		
		public GoThereOnclick(int position)
		{
			this.position=position;
		}
		
		@Override
		public void onClick(View v)
		{
			Bundle bundle=new Bundle();
			bundle.putDouble(GoThereMapActivity.GO_THERE_LANTITUDE, data.get(position).getLan());
			bundle.putDouble(GoThereMapActivity.GO_THERE_LONGTITUDE,data.get(position).getLon());
			
			StartIntent.startIntentWithParam(context, GoThereMapActivity.class, bundle);
		}
	}
}
