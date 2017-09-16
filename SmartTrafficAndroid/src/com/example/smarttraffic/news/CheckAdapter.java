package com.example.smarttraffic.news;

import java.util.List;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckAdapter extends BaseAdapter
{
	public CheckAdapter(Context c,List<NewsType> list)
	{
		context=c;
		data=list;
	}

	private Context context;
	private List<NewsType> data;
	
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
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.check_adapter_grid, null);
		
		holder h=new holder();
		h.nameTextView=(TextView)convertView.findViewById(R.id.check_item_name);
		h.checkImageView=(ImageView)convertView.findViewById(R.id.check_item_select);
		
		h.nameTextView.setText(data.get(position).getTypeName());
		
		if(data.get(position).isCheck())
		{
			h.checkImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.item_check));
		}
		else
		{
			h.checkImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.item_uncheck));
		}
		
		return convertView;
	}

	public void setChange(int i)
	{
		data.get(i).setCheck(!data.get(i).isCheck());
		update();
	}
	
	public String getSelectForInt()
	{
		String result="";
		
		for(int i=0;i<data.size();i++)
		{					
			if(data.get(i).isCheck())
			{
				result+=(i+1);
				if(i<(data.size()-1))
					result+=",";
			}
			
			
		}
		
		return result;
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	public List<NewsType> getData() {
		return data;
	}

	class holder
	{
		public TextView nameTextView;
		public ImageView checkImageView;
	}
}
