package net.hxkg.channel;

import java.util.List;

import com.example.huzhouport.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChannelAdepter extends BaseAdapter
{
	List<String> list;
	Context context;
	
	public ChannelAdepter(Context context,List<String> list)
	{
		this.list=list;
		this.context=context;
	}
	
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView=View.inflate(context, R.layout.channelitem, null);
		TextView tView=(TextView) convertView.findViewById(R.id.text);
		tView.setText(list.get(position));
		return convertView;
	}

}
