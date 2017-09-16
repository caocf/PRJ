package com.hztuen.gh.activity.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.gh.modol.TipSearch;
import com.hxkg.ghpublic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TipListAdapter extends BaseAdapter{
	private Context context;
	private List<TipSearch> tipsearch;
	public TipListAdapter(Context context,List<TipSearch> tipsearch){
		this.context = context;
		this.tipsearch = tipsearch;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tipsearch.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tipsearch.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.tiplist_item, null);
		}
		TextView tips = (TextView) convertView.findViewById(R.id.tips);
		tips.setText(tipsearch.get(position).getName());
		return convertView;
	}

}
