package com.example.smarttraffic.driverguide;

import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.news.Ydld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapShowListAdapter extends BaseAdapter
{
	private Context context;
	private List<Ydld> data;

	public MapShowListAdapter(Context c, List<Ydld> data)
	{
		this.context = c;
		this.data = data;
	}

	@Override
	public int getCount()
	{

		return data.size();
	}

	@Override
	public Object getItem(int position)
	{

		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView textView= new TextView(context);
		
		try
		{
			
			textView.setText(data.get(position).getRoadName()+":"+data.get(position).getRsStart() + "-"
						+ data.get(position).getRsEnd()+" "+data.get(position).getDesc());
		} catch (Exception e)
		{

		}

		return textView;
	}

}
