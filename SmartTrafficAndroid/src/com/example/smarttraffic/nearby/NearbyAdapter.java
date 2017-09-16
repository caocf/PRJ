package com.example.smarttraffic.nearby;

import java.util.List;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NearbyAdapter extends BaseAdapter
{
	Context context;
	List<NearbyType> data;

	
	public List<NearbyType> getData()
	{
		return data;
	}
	public NearbyAdapter(Context context,List<NearbyType> data)
	{
		this.context=context;
		this.data=data;

		
	}
	@Override
	public int getCount() {
//		if(data.size()%3==0)
			return data.size();
//		else
//		{
//			return (data.size()/3+1)*3;
//		}
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
//		convertView = LayoutInflater.from(context).inflate(R.layout.listview_nearby,null);
//		
//		TextView content=(TextView)convertView.findViewById(R.id.nearby_name);
//		content.setText(data.get(position).getName());
//		content.setTextSize(16);
//		content.setHeight(46);
		TextView content=new TextView(context);
		
		if(position<data.size())
			content.setText(data.get(position).getName());
		content.setTextSize(16);
		content.setBackgroundResource(R.drawable.selector_nearby_under_backgroud);
		content.setGravity(Gravity.CENTER);
		content.setPadding(0, 25, 0, 25);
		
		return content;
	}

}
