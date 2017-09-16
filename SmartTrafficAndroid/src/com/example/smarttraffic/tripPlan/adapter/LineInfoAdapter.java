package com.example.smarttraffic.tripPlan.adapter;

import java.util.List;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 线路信息列表适配器
 * @author Administrator zhou
 *
 */
public class LineInfoAdapter extends BaseAdapter
{
	List<String> data;				//线路信息
	Context context;
	
	int[] imageView;				//线路方向图标

	public LineInfoAdapter(Context context,List<String> data)
	{
		this.data=data;
		this.context=context;
		imageView=new int[]{R.drawable.left,R.drawable.arrow_up,R.drawable.right,R.drawable.trip_icon_start,R.drawable.trip_icon_finish};
	}
		
	public List<String> getData()
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

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_line_info_trip,null);
		holder = new ViewHolder();
		
		holder.imageView = (ImageView) convertView.findViewById(R.id.line_orient);
		holder.textView=(TextView)convertView.findViewById(R.id.line_info);		
		
		if(position==0  )
		{
			holder.imageView.setImageResource(imageView[3]);
		}
		else if (position==(data.size()-1)) 
		{
			holder.imageView.setImageResource(imageView[4]);
		}
		else
		{
			holder.imageView.setImageResource(getImageID(data.get(position)));
		}
		holder.textView.setText(data.get(position));
		
		return convertView;
	}

	public int getImageID(String data)
	{
		String[] contains=new String[]{"左转","直行","右转"};
		
		for(int i=0;i<contains.length;i++)
		{
			if(data.contains(contains[i]))
			{
				return imageView[i];
			}
		}
		
		return imageView[1];
	}
	
	public void update(List<String> data)
	{
			this.data=data;
		notifyDataSetChanged();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		ImageView imageView;
		TextView textView;
	}
}

