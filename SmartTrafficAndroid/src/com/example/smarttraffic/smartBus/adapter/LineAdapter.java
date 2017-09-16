package com.example.smarttraffic.smartBus.adapter;

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
import com.example.smarttraffic.smartBus.bean.LineInfo;

/**
 * 收藏线路适配器
 * @author Administrator zhou
 *
 */
public class LineAdapter extends BaseAdapter
{
	List<LineInfo> data;
	Context context;
	boolean isCanDelete;			//是否删除
	
	public LineAdapter(Context context,List<LineInfo> data)
	{
		this.data=data;
		this.context=context;
		isCanDelete=false;
	}
	
	public LineAdapter(Context context,List<LineInfo> data,boolean isCanDelete)
	{
		this.data=data;
		if(this.data==null)
			data=new ArrayList<LineInfo>();
		this.context=context;
		this.isCanDelete=isCanDelete;
	}
		
	public List<LineInfo> getData()
	{
		return data;
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

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_smart_bus_line,null);
		holder = new ViewHolder();
		
		try
		{
			holder.lineName=(TextView) convertView.findViewById(R.id.smart_bus_favor_line_name);
			holder.startPoint = (TextView) convertView.findViewById(R.id.smart_bus_favor_line_start);
			holder.endPoint=(TextView)convertView.findViewById(R.id.smart_bus_favor_line_end);
			holder.moreInfo=(TextView) convertView.findViewById(R.id.smart_bus_favor_line_info);
			
			holder.lineName.setText(data.get(position).getName());
			holder.startPoint.setText("（"+data.get(position).getStart().replaceAll("\\d+", ""));
			holder.endPoint.setText(data.get(position).getEnd().replaceAll("\\d+", "")+"）");
//			holder.moreInfo.setText("首末班车时间："+data.get(position).getStartTime()+"-"+data.get(position).getEndTime());
			
			if(isCanDelete)
			{
				holder.deleteImageView=(ImageView) convertView.findViewById(R.id.smart_bus_favor_line_delete);
				holder.deleteImageView.setVisibility(View.VISIBLE);
				
				if(data.get(position).isSelect())
				{
					holder.deleteImageView.setBackgroundResource(R.drawable.item_check);
				}
				else
				{
					holder.deleteImageView.setBackgroundResource(R.drawable.item_uncheck);
				}
			}
		}
		catch(Exception e)
		{
			
		}
		return convertView;
	}
	
	public int countSelect()
	{
		int c=0;
		for(int i=0;i<data.size();i++)
		{
			c+=(data.get(i).isSelect())?1:0;
		}
		
		return c;
	}
	
	public void changeCheck(int position)
	{
		if(isCanDelete)
			data.get(position).setSelect(!data.get(position).isSelect());
		notifyDataSetChanged();
	}
	
	public void selectALL(boolean select)
	{
		for(int i=0;i<data.size();i++)
		{
			data.get(i).setSelect(select);
		}
		notifyDataSetChanged();
	}
	
	
	public void update(List<LineInfo> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<LineInfo>();
		else
			this.data=data;
		notifyDataSetChanged();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView lineName;
		TextView startPoint;
		TextView endPoint;
		TextView moreInfo;
		ImageView deleteImageView;
	}
}