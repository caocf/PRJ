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
import com.gh.modol.ShipRentModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.ShipDetailsActivity;
import com.hztuen.gh.activity.ShipRentDetailsActivity;
import com.hztuen.gh.activity.Adapter.ShipRentAdapter.ViewHolder;

public class ShipAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<ShipModel> modellist;
	
	
	public ShipAdapter(Context context,List<ShipModel> modellist){
		this.context = context;
		this.modellist = modellist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return modellist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return modellist.get(position);
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
		convertView=LinearLayout.inflate(context, R.layout.ship_item, null);
		
		holder.title = (TextView)convertView.findViewById(R.id.tv_title); //标题
		holder.route = (TextView)convertView.findViewById(R.id.tv_title_content); 
		holder.shiptype = (TextView) convertView.findViewById(R.id.tv_ship_type); // 船种类
	
		holder.price = (TextView) convertView.findViewById(R.id.tv_goods_money); // 租船价格
		holder.postdate = (TextView) convertView.findViewById(R.id.tv_date); // 提交时间
		
		
		final ShipModel model = modellist.get(position);
		
		holder.title.setText(model.gettitle());
		holder.shiptype.setText(model.getshiptype());
		
		holder.price.setText(model.getprice()+"元");
		holder.postdate.setText(model.getpostdate());
		holder.route.setText(model.getroute());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(context, ShipDetailsActivity.class);
				in.putExtra("title", model.gettitle());
				in.putExtra("shiptype", model.getshiptype());
				in.putExtra("shipname", model.getshipname());
				in.putExtra("tons", model.gettons());
				in.putExtra("rentprice", model.getprice());
				in.putExtra("linkman", model.getlinkman());
				in.putExtra("tel", model.gettel());
				in.putExtra("remark", model.getremark());
				in.putExtra("postdate", model.getpostdate());
				
				in.putExtra("postdate", model.getpostdate()); 
				in.putExtra("targetdock", model.gettargetdock());
				in.putExtra("route", model.getroute());
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 跳转界面  跳转到船货圈找船详情界面
				context.startActivity(in);
			}
		});
		
		return convertView;
	}
	
	
	class ViewHolder {
//		 "id": 3,
//	      "title": "杭州货运",
//	      "shiptype": "c",
//	      "shipname": "浙杭州货001",
//	      "tons": "15",
//	      "emptydock": "a",
//	      "targetdock": "b",
//	      "route": "杭州水道",
//	      "price": "1000",
//	      "linkman": "李思",
//	      "postdate": "2016-06-02 15:29:39",
//	      "tel": "168",
//	      "remark": "备注"
		
		TextView id; // 
		TextView title; // 
		TextView shiptype; // 
		TextView shipname; // 
		TextView tons; // 
		
		TextView emptydock; // 
		TextView targetdock; // 
		TextView route; // 
		
		TextView price; // 
		TextView linkman; // 
		TextView tel; // 
		TextView remark; // 
		TextView postdate; // 

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}