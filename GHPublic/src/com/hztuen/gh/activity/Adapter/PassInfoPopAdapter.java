package com.hztuen.gh.activity.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gh.modol.PassInfoPop;
import com.gh.modol.PassInfoShipMode;
import com.hxkg.ghpublic.R;

public class PassInfoPopAdapter extends BaseAdapter{
	private Context context;
	private List<PassInfoPop> pip;
	public PassInfoPopAdapter(Context context,List<PassInfoPop> pip){
		this.context = context;
		this.pip = pip;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pip.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pip.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.pop_passinfo_ship_item, null);
		}
		TextView ship_item = (TextView) convertView.findViewById(R.id.ship_item);
		ship_item.setText(pip.get(position).getName());
		return convertView;
	}

}
