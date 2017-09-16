package com.huzhouport.car;

import java.util.ArrayList;
import java.util.List;

import com.example.huzhouport.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CarAdapter extends BaseAdapter
{
	List<CarApplication> list=new ArrayList<>();
	Context context;
	
	public CarAdapter(Context context,List<CarApplication> list)
	{
		this.context=context;
		this.list=list;
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
		convertView=View.inflate(context,  R.layout.item_meetingrecord, null);
		TextView title=(TextView) convertView.findViewById(R.id.text1);
		TextView topic=(TextView) convertView.findViewById(R.id.topic);
		TextView time=(TextView) convertView.findViewById(R.id.time);
		TextView status=(TextView) convertView.findViewById(R.id.status);
		
		CarApplication carApplication=list.get(position);
		
		String name=carApplication.getUsername();
		String topicString=carApplication.getReason();
		String timeString=carApplication.getAptime();
		int statusShow=carApplication.getStatusShow();
		switch (statusShow) {
		case 1:
			status.setText("待审核");
			status.setTextColor(Color.parseColor("#ff8400"));
			break;
		case 2:
			status.setText("同意");
			status.setTextColor(Color.GREEN);
			break;
		case 3:
			status.setText("驳回");
			status.setTextColor(Color.RED);
			break;
		case 4:
			status.setText("已撤回");
			status.setTextColor(Color.parseColor("#f9f9f9"));
			break;

		default:
			break;
		}
		title.setText(name);
		topic.setText(topicString);
		time.setText(timeString.replace("T", " "));
		
		return convertView; 
	}

}
