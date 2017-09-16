package com.example.smarttraffic.tripPlan.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.HistoryBaseAdapter;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.tripPlan.bean.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 历史记录列表适配器
 * @author Administrator zhou
 *
 */
public class HistoryAdapter extends HistoryBaseAdapter
{
	List<History> data;
	Context context;

	public HistoryAdapter(Context context,List<History> data)
	{
		this.data=data;
		if(this.data==null)
			data=new ArrayList<History>();
		this.context=context;
	}
		
	public List<History> getData()
	{
		return data;
	}

	@Override
	public int getCount() {
		return data.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private View.OnClickListener clearClickListener;
	
	public void setClearClickListener(View.OnClickListener clearClickListener)
	{
		this.clearClickListener = clearClickListener;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if(position==getCount()-1)
		{	
			View clear=CreateClearView(context);

			if(clearClickListener!=null)
				clear.setOnClickListener(clearClickListener);
			
			
			if(position==0)
				clear.setVisibility(View.GONE);
			
			return clear;
		}
		
		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_history_trip,null);
		holder = new ViewHolder();
		
		holder.startPoint = (TextView) convertView.findViewById(R.id.start_point_list_item_text);
		holder.endPoint=(TextView)convertView.findViewById(R.id.end_point_list_item_text);		
		
		holder.startPoint.setText(data.get(position).getStart());
		holder.endPoint.setText(data.get(position).getEnd());
				
		return convertView;
	}

	@Override
	public int getHID(int position)
	{
		return data.get(position).getId();
	}
	
	@Override
	public int getHType()
	{
		return ContentType.TRIP_PLAN_SELF_DRIVING;
	}
	
	@Override
	public void removeByID(int id)
	{
		this.data.remove(id);
		this.notifyDataSetChanged();
	}
	
	public void update(List<History> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<History>();
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
		TextView startPoint;
		TextView endPoint;
	}
}

