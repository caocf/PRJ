package com.example.smarttraffic.smartBus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.adapter.HistoryBaseAdapter;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.smartBus.bean.BusLine;

/**
 * 线路历史记录适配器
 * @author Administrator zhou
 *
 */
public class LineHistoryAdapter extends HistoryBaseAdapter
{
	List<BusLine> data;
	Context context;
	
	public LineHistoryAdapter(Context context,List<BusLine> data)
	{
		this.data=data;
		this.context=context;
	}
	
	public List<BusLine> getData()
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

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_history_smart_bus_line,null);
		holder = new ViewHolder();
		
		try
		{
			holder.name=(TextView) convertView.findViewById(R.id.smart_bus_history_line_name);
			holder.start = (TextView) convertView.findViewById(R.id.smart_bus_history_line_start);
			holder.end=(TextView)convertView.findViewById(R.id.smart_bus_history_line_end);
			
			holder.name.setText(data.get(position).getName());
			
			if(data.get(position).getDirect()==2)
			{
				holder.start.setText(""+data.get(position).getDownStartStationName().replaceAll("\\d+", ""));
				holder.end.setText(data.get(position).getDownEndStationName().replaceAll("\\d+", "")+"");
			}
			else
			{
				holder.start.setText(""+data.get(position).getUpStartStationName().replaceAll("\\d+", ""));
				holder.end.setText(data.get(position).getUpEndStationName().replaceAll("\\d+", "")+"");
			}
		}
		catch(Exception e)
		{
		}
		return convertView;
	}
	
	public void update(List<BusLine> data)
	{
		this.data=null;
		if(data==null)
			this.data=new ArrayList<BusLine>();
		else
			this.data=data;
		notifyDataSetChanged();
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	@Override
	public int getHID(int position)
	{
		return data.get(position).getHistoryID();
	}
	
	@Override
	public int getHType()
	{
		return ContentType.SMART_BUS_LINE_HISTORY;
	}
	
	@Override
	public void removeByID(int id)
	{
		data.remove(id);
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView name;
		TextView start;
		TextView end;
	}
}