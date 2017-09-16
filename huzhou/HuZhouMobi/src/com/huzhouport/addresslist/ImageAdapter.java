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



public class ImageAdapter extends BaseAdapter {
	private ArrayList<HashMap<String, Object>> userlist=new ArrayList<HashMap<String, Object>>() ;
	private LayoutInflater mlInflater;
	private Context mContext;
	private ViewHolder holder;

	public ImageAdapter(ArrayList<HashMap<String, Object>> userlist, Context mContext) {
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
			convertView = mlInflater.inflate(R.layout.addresslist_useritem, null);
			holder.name = (TextView) convertView.findViewById(R.id.address_useritem_name);
			holder.tel = (TextView) convertView.findViewById(R.id.address_useritem_tel);
			holder.id = (TextView) convertView.findViewById(R.id.address_useritem_id);
			holder.telimg = (ImageButton) convertView.findViewById(R.id.address_useritem_telimg);
			holder.sms = (ImageButton) convertView.findViewById(R.id.address_useritem_sms);
			holder.but=(ImageButton) convertView.findViewById(R.id.address_useritem_button1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(userlist.get(position).get("name").toString());
		holder.tel.setText(userlist.get(position).get("tel").toString());
		holder.id.setText(userlist.get(position).get("id").toString());
		holder.telimg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent intent=new Intent(); //创建一个意图
				 intent.setAction("android.intent.action.CALL");
				 intent.setData(Uri.parse("tel:"+userlist.get(position).get("tel").toString()));
				 mContext.startActivity(intent); //方法内部会自动为intent添加类别 ：android.intent.category.DEFAULT".
				
			}
		});
		holder.sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + userlist.get(position).get("tel").toString()));
		        //sendIntent.putExtra("sms_body", body); //用于附带短信内容，可不加 
		        mContext.startActivity(sendIntent);
				//13857276680
			}
		});
		/*holder.but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,AddresslistShowID.class);
				intent.putExtra("id",userlist.get(position).get("id").toString());
				intent.putExtra("name",userlist.get(position).get("name").toString());
				mContext.startActivity(intent);
			}
		});
		holder.name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,AddresslistShowID.class);
				intent.putExtra("id",userlist.get(position).get("id").toString());
				intent.putExtra("name",userlist.get(position).get("name").toString());
				mContext.startActivity(intent);
			}
		});
		holder.tel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,AddresslistShowID.class);
				intent.putExtra("id",userlist.get(position).get("id").toString());
				intent.putExtra("name",userlist.get(position).get("name").toString());
				mContext.startActivity(intent);
			}
		});*/
		
		
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView tel;
		TextView id;
		ImageButton telimg;
		ImageButton sms;
		ImageButton but;
	}
}
