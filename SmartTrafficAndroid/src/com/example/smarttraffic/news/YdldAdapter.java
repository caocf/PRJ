package com.example.smarttraffic.news;

import java.util.List;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class YdldAdapter extends BaseAdapter
{
	private Context context;
	private List<Ydld> data;

	public YdldAdapter(Context c, List<Ydld> data)
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
		convertView = LayoutInflater.from(context).inflate(
				R.layout.news_ydld_adapter_list, null);

		try
		{
			Holder holder = new Holder();
			holder.name = (TextView) convertView.findViewById(R.id.news_road);
			holder.kind = (TextView) convertView.findViewById(R.id.news_kind);
			holder.startend = (TextView) convertView
					.findViewById(R.id.news_start_end);

			holder.name.setText(data.get(position).getRoadName()+"("+data.get(position).getLocalKind()+")");

			holder.kind.setText(data.get(position).getDesc());

			holder.startend.setText(data.get(position).getRsStart() + "-"
					+ data.get(position).getRsEnd());
		} catch (Exception e)
		{

		}

		return convertView;
	}

	class Holder
	{
		public TextView name;
		public TextView kind;
		public TextView startend;
	}
}
