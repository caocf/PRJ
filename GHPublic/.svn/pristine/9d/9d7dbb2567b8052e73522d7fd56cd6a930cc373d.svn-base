package com.hztuen.gh.activity.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.gh.modol.SearchListItem;
import com.hxkg.ghpublic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午4:08:52
 */
public class SearchListAdapter extends BaseAdapter{
	private Context context;
	private List<SearchListItem> list = new ArrayList<SearchListItem>();
	
	public SearchListAdapter(Context context,List<SearchListItem> list){
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
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
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.search_list_item, null);
		}
		TextView search_point = (TextView) convertView.findViewById(R.id.search_point);
		TextView search_distance = (TextView) convertView.findViewById(R.id.search_distance);
		search_point.setText(list.get(position).getPoint().toString());
		search_distance.setText(list.get(position).getDistance().toString());
		return convertView;
	}
}
