package com.hztuen.gh.activity.Adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gh.modol.ShipModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.ShipDetailsActivity;
import com.hztuen.gh.activity.Adapter.ShipAdapter.ViewHolder;

public class SearchShipNameAdapter extends BaseAdapter {

	private Context context;
	private List<String> nameList;

	public SearchShipNameAdapter(Context context, List<String> nameList) {
		this.context = context;
		this.nameList = nameList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return nameList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return nameList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context, R.layout.search_ship_name_item, null);
        holder.shipname=(TextView)convertView.findViewById(R.id.text1_context);
        
        holder.shipname.setText(nameList.get(position));
        
		return convertView;
	}

	class ViewHolder {

		TextView shipname; //

	}

}
