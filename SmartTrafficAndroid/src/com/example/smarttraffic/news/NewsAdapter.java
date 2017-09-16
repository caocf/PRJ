package com.example.smarttraffic.news;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.util.DoString;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter
{
	private Context context;
	private List<News> data;
	public NewsAdapter(Context c,List<News> data)
	{
		this.context=c;
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
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = LayoutInflater.from(context).inflate(R.layout.news_adapter_list, null);
		
		Holder holder=new Holder();
//		holder.news_image=(ImageView)convertView.findViewById(R.id.news_image);
		holder.news_title=(TextView)convertView.findViewById(R.id.news_title);
		holder.news_content=(TextView)convertView.findViewById(R.id.news_content);
		holder.news_date=(TextView)convertView.findViewById(R.id.news_date);
		
		if(data.get(position).getImage()!=null)
		{
//			holder.news_image.setImageBitmap(data.get(position).getImage());
//			holder.news_image.setVisibility(View.VISIBLE);
		}
		else
//			holder.news_image.setVisibility(View.INVISIBLE);
		
		if(data.get(position).getTitle()!=null)
			holder.news_title.setText(data.get(position).getTitle());
		
		if(data.get(position).getContent()!=null)
			holder.news_content.setText(data.get(position).getContent());
		
		if(data.get(position).getDate()!=null)
		{
			try
			{
				String d=data.get(position).getDate().toString();
				d=d.replace("T", " ");
				
				Date date=DoString.StringToDate(d, "yyyy-MM-dd HH:mm:ss");
				
				d=DoString.DateToString(date, "yyyy-MM-dd HH:mm");
				
				holder.news_date.setText(d);
			}
			catch(Exception e)
			{
				
			}
		}
		
		return convertView;
	}
	
	public void clear()
	{
		this.data=new ArrayList<News>();
		notifyDataSetChanged();
	}
	
	public void update(List<News> data)
	{
		this.data=null;
		this.data=data;
		notifyDataSetChanged();
	}
	
	class Holder
	{
//		public ImageView news_image;
		public TextView news_title;
		public TextView news_content;
		public TextView news_date;
	}
}
