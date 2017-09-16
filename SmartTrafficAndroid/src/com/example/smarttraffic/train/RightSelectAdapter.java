package com.example.smarttraffic.train;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarttraffic.R;

/**
 * 铁路信息右侧筛选信息列表适配器
 * @author Administrator zhou
 *
 */
public class RightSelectAdapter extends BaseAdapter
{
	List<SelectString> data;
	Context context;
	boolean isCanMutiSelect;
	
	public RightSelectAdapter(Context context,List<String> data)
	{
		this(context, data,true);
	}
	
	public RightSelectAdapter(Context context,String[] data,boolean isCanMutiSelect)
	{
		this.data=new ArrayList<SelectString>();
		for(int i=0;i<data.length;i++)
		{
			SelectString temp=new SelectString();
			temp.setData(data[i]);
			temp.setSelect(false);
			this.data.add(temp);
		}

		this.context=context;
		this.isCanMutiSelect=isCanMutiSelect;
	}
	
	public RightSelectAdapter(Context context,List<String> data,boolean isCanMutiSelect)
	{
		this.data=new ArrayList<SelectString>();
		for(int i=0;i<data.size();i++)
		{
			SelectString temp=new SelectString();
			temp.setData(data.get(i));
			temp.setSelect(false);
			this.data.add(temp);
		}

		this.context=context;
		this.isCanMutiSelect=isCanMutiSelect;
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

		convertView = LayoutInflater.from(context).inflate(R.layout.can_select_one_item_listview,null);
		holder = new ViewHolder();
		
		holder.content = (TextView) convertView.findViewById(R.id.select_one_point_list_item_text);
		holder.content.setTextColor(context.getResources().getColor(R.color.back_groud_white));
		holder.select=(ImageView)convertView.findViewById(R.id.select_list_item_image);
		
		holder.content.setText(data.get(position).getData());
		
		if(data.get(position).isSelect())
		{
			holder.select.setImageResource(R.drawable.bus_filter_choose_press);
		}
		else
		{
			holder.select.setImageResource(R.drawable.bus_filter_choose_normal);
		}
		
		return convertView;
	}
	
	public int getSelectForIntResult()
	{
		int result=0;
		
		for(int i=0;i<data.size();i++)
		{
			if(i==0 && data.get(i).isSelect())
			{
				return result;
			}
						
			if(data.get(i).isSelect())
			{
				result+=1;
			}
			
			if(i>0 && i<data.size()-1)
				result*=10;
		}
		
		return result;
	}
	
	public void changeCheckAndClearFirst(int position)
	{	
		data.get(0).setSelect(false);
		changeCheck(position);
	}
	
	public void changeCheck(int position)
	{
		data.get(position).setSelect(!data.get(position).isSelect());
		notifyDataSetChanged();
	}
	
	public void selectAll(boolean select)
	{
		for(int i=0;i<data.size();i++)
		{
			data.get(i).setSelect(select);
		}
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView content;
		ImageView select;
	}
}

class SelectString
{
	private String data;
	private boolean isSelect;
	
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	public boolean isSelect()
	{
		return isSelect;
	}
	public void setSelect(boolean isSelect)
	{
		this.isSelect = isSelect;
	}	
}


