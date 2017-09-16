package com.example.smarttraffic.taxi;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.util.TextViewUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TaxiAdapter extends BaseAdapter
{
	private List<Taxi> data;
	private LayoutInflater inflater;
	
	public TaxiAdapter(List<Taxi> d,Context c)
	{
		this.data=d;
		this.inflater=LayoutInflater.from(c);
	}
	
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return data.size()+1;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return position==0?0:data.get(position-1);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView=inflater.inflate(R.layout.listview_taxi, null);
		if(position==0)
		{
			TextViewUtil.setText(convertView, R.id.textView1, "车牌号");
			TextViewUtil.setText(convertView, R.id.textView2,"距离");
		}
		else
		{
			TextViewUtil.setText(convertView, R.id.textView1, data.get(position-1).getSbid());
			TextViewUtil.setText(convertView, R.id.textView2, data.get(position-1).getDistance()+"米");
		}
		
		return convertView;
	}

}
