package net.hxkg.lawexcut;

import java.util.List;

import net.hxkg.ghmanager.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ManAdapter extends BaseAdapter
{
	List<String> list;
	Context context;
	public ManAdapter(Context context,List<String> list) 
	{
		this.list=list;
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		convertView=View.inflate(context, R.layout.item_cruise_man, null);
		TextView textView=(TextView) convertView.findViewById(R.id.text);
		textView.setText(list.get(position));
		return convertView;
	}

}
