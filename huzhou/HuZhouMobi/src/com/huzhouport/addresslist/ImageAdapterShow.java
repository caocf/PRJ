package com.huzhouport.addresslist;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.huzhouport.R;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

import android.widget.ImageButton;
import android.widget.TextView;



public class ImageAdapterShow extends BaseAdapter {
	private ArrayList<HashMap<String, Object>> userlist=new ArrayList<HashMap<String, Object>>() ;
	private LayoutInflater mlInflater;
	private Context mContext;
	private ViewHolder holder;

	public ImageAdapterShow(ArrayList<HashMap<String, Object>> userlist, Context mContext) {
		super();
		this.userlist = userlist;
		this.mContext = mContext;
		mlInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {

		return userlist != null ? userlist.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return userlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mlInflater.inflate(R.layout.addresslist_showitem, null);
			holder.name = (TextView) convertView.findViewById(R.id.address_show_name);
			holder.sms = (ImageButton) convertView.findViewById(R.id.address_show_sms);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(userlist.get(position).get("tel").toString());
	
		holder.sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + userlist.get(position).get("tel").toString()));
		        //sendIntent.putExtra("sms_body", body); //用于附带短信内容，可不加 
		        mContext.startActivity(sendIntent);
				//13857276680
			}
		});
	
		
		
		return convertView;
	}

	class ViewHolder {
		TextView name;
		ImageButton sms;
		
	}
}
