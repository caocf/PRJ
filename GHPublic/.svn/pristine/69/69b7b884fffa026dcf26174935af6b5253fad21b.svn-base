package com.hztuen.gh.activity.Adapter;

import java.util.List;

import com.ghpublic.entity.PortName;
import com.hxkg.ghpublic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TidePortAdapter extends BaseAdapter{
	private Context context;
	private List<PortName> portlist;
	public TidePortAdapter(Context context,List<PortName> portlist){
		this.context = context;
		this.portlist = portlist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return portlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return portlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.tide_port_list_item, null);
//			convertView = LinearLayout.inflate(R.layout.tide_port_list_item, null);
		}
		TextView port = (TextView) convertView.findViewById(R.id.port);
		port.setText(portlist.get(position).getPortName());
		return convertView;
	}

}
