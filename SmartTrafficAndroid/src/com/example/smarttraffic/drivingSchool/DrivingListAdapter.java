package com.example.smarttraffic.drivingSchool;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.tripPlan.TripMapActivity;
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

public class DrivingListAdapter extends BaseAdapter
{
	Activity context;
	List<DrivingSchool> data;

	public DrivingListAdapter(Activity context,List<DrivingSchool> data)
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
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.driving_school_listview, null);
		
		Holder holder=new Holder();
		holder.name=(TextView)convertView.findViewById(R.id.driving_school_name);
		holder.content=(TextView)convertView.findViewById(R.id.driving_school_information);
		holder.imageView=(ImageView)convertView.findViewById(R.id.driving_get_there);
		
		holder.name.setText(data.get(position).getName());
		holder.content.setText(data.get(position).getAddress());
		holder.imageView.setOnClickListener(new GoThereOnclick(position));
		
		return convertView;
	}

	public void refreshList(List<DrivingSchool> data)
	{
		this.data=null;
		this.data=data;
		notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data.clear();
		notifyDataSetChanged();
	}
	
	
	
	public List<DrivingSchool> getData() {
		return data;
	}
	public void setData(List<DrivingSchool> data) {
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
			DrivingSchool drivingSchool=data.get(position);
			Bundle bundle=new Bundle();
			bundle.putSerializable(DrivingInfoListFragment.DRIVING_SCHOOL_INFO, drivingSchool);
			bundle.putInt(TripMapActivity.FROM_NAME, 1);
			
			StartIntent.startIntentWithParam(context, TripMapActivity.class,bundle);
			
		}
	}
}
