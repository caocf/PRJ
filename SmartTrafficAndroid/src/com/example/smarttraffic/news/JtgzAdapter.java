package com.example.smarttraffic.news;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.util.Filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JtgzAdapter extends BaseAdapter
{
	private Context context;
	private List<JTGZ> data;

	public JtgzAdapter(Context c, List<JTGZ> data)
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
				R.layout.news_adapter_list, null);

		try
		{
			Holder holder = new Holder();
			// holder.news_image=(ImageView)convertView.findViewById(R.id.news_image);
			holder.news_title = (TextView) convertView
					.findViewById(R.id.news_title);
			holder.news_content = (TextView) convertView
					.findViewById(R.id.news_content);
			holder.news_date = (TextView) convertView
					.findViewById(R.id.news_date);

			holder.news_title.setText(data.get(position).getBT());

			holder.news_content.setText(Filter.filterHTML(data.get(position).getNR())
					.substring(0, 50));

			holder.news_date.setText(data.get(position).getFBSJ());
		} catch (Exception e)
		{
		}

		return convertView;
	}

	public void clear()
	{
		this.data = new ArrayList<JTGZ>();
		notifyDataSetChanged();
	}

	public void update(List<JTGZ> data)
	{
		this.data = null;
		this.data = data;
		notifyDataSetChanged();
	}

	class Holder
	{
		public TextView news_title;
		public TextView news_content;
		public TextView news_date;
	}
}
