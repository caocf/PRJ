package net.hxkg.lawexcut;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EvidenceAdapter extends BaseAdapter
{
	Context context;
	List<Map<String, Object>> datalist;
	int container;
	String key[];
	int id[];
	
	public EvidenceAdapter(Context context,List<Map<String, Object>> datalist,int container,
								String key[],int id[])
	{
		this.context=context;
		this.datalist=datalist;
		this.container=container;
		this.key=key;
		this.id=id;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.size();
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
		convertView=View.inflate(context, container, null);
		
		TextView targetTextView=(TextView) convertView.findViewById(id[0]);
		targetTextView.setText((String)datalist.get(position).get(key[0]));
		
		TextView statusTextView=(TextView) convertView.findViewById(id[1]);
		statusTextView.setText((String)datalist.get(position).get(key[1]));
		if("待审核".equals(statusTextView.getText().toString()))
		{
			statusTextView.setTextColor(Color.parseColor("#f89513"));
		}
		else
		{
			statusTextView.setTextColor(Color.parseColor("#50be67"));
		}
		
		TextView reasonTextView=(TextView) convertView.findViewById(id[2]);
		reasonTextView.setText((String)datalist.get(position).get(key[2]));		
		
		return convertView;
	}

}
