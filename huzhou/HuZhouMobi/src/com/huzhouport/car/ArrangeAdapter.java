package com.huzhouport.car;

import java.util.ArrayList;
import java.util.List;

import com.example.huzhouport.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArrangeAdapter extends BaseAdapter
{
	Context context;
	List<CarInfo> list=new ArrayList<>();
	List<Integer> unvaliableCarids=new ArrayList<>();
	
	public ArrangeAdapter(Context context,List<CarInfo> list)
	{
		this.context=context;
		this.list=list;
	}
	
	public void setUnvaliableCarids(List<Integer> unvaliableCarids)
	{
		this.unvaliableCarids=unvaliableCarids;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView=View.inflate(context, R.layout.car_item, null);
		TextView nameTextView=(TextView) convertView.findViewById(R.id.name);
		TextView capTextView=(TextView) convertView.findViewById(R.id.cap);
		
		CarInfo carInfo=list.get(position);
		
		
		nameTextView.setText(carInfo.getName()+"("+carInfo.getCapcity()+"×ù)");
		capTextView.setText(carInfo.getDriver());
		int id=carInfo.getId();
		for(int uid:unvaliableCarids)
		{
			if(id==uid)
			{
				
				//convertView.setEnabled(false);
				nameTextView.setEnabled(false);
				capTextView.setEnabled(false);
				break;
			}
		}
		return convertView;
	}

}
