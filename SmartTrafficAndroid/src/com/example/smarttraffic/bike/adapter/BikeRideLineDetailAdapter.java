package com.example.smarttraffic.bike.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeRidingStationOnLine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BikeRideLineDetailAdapter extends BaseAdapter
{
	List<BikeRidingStationOnLine> data;
	Context context;
	
	public BikeRideLineDetailAdapter(Context context,List<BikeRidingStationOnLine> data)
	{
		this.data=data;
		this.context=context;
	}
	
	public List<BikeRidingStationOnLine> getData()
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

	int[] image=new int[]{R.drawable.selector_input_dowm,R.drawable.selector_input_up};
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{

		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_bike_riding_detail,null);
		holder = new ViewHolder();
		
		holder.changeImageView = (ImageView) convertView.findViewById(R.id.bike_riding_detail_change);
		holder.nameTextView=(TextView)convertView.findViewById(R.id.bike_riding_detail_station);		
		holder.infoTextView=(TextView)convertView.findViewById(R.id.bike_riding_detail_infomation);
		
		holder.nameTextView.setText(data.get(position).getStationName());
		
		if(data.get(position).isShowInfo())
		{
			holder.infoTextView.setVisibility(View.VISIBLE);
			holder.infoTextView.setText(data.get(position).getInfo());
			holder.changeImageView.setBackgroundResource(image[1]);
		}
		else
		{
			holder.infoTextView.setVisibility(View.GONE);
			holder.changeImageView.setBackgroundResource(image[0]);
		}
		
		return convertView;	
	}

	public void change(int position)
	{
		this.data.get(position).setShowInfo(!this.data.get(position).isShowInfo());
		notifyDataSetChanged();
	}
	
	public void update(List<BikeRidingStationOnLine> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<BikeRidingStationOnLine>();
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
		TextView nameTextView;
		TextView infoTextView;
		ImageView changeImageView;
	}
}

