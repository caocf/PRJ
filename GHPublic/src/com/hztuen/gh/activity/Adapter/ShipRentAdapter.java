package com.hztuen.gh.activity.Adapter;

import java.util.List;

import com.gh.modol.ShipCircleListModel;
import com.gh.modol.ShipRentModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.ShipRentDetailsActivity;
import com.hztuen.gh.activity.StartActivity;
import com.hztuen.gh.activity.Adapter.ShipGoodsCircleListAdapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShipRentAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<ShipRentModel> modellist;
	
	
	public ShipRentAdapter(Context context,List<ShipRentModel> modellist){
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
		convertView=LinearLayout.inflate(context, R.layout.ship_rent_item, null);
		
		holder.title = (TextView)convertView.findViewById(R.id.tv_title); //标题
		holder.shiptype = (TextView) convertView.findViewById(R.id.tv_ship_type); // 船种类
		holder.tons = (TextView) convertView.findViewById(R.id.tv_goods_weight); // 重量
		holder.rentprice = (TextView) convertView.findViewById(R.id.tv_goods_money); // 租船价格
		holder.postdate = (TextView) convertView.findViewById(R.id.tv_date); // 提交时间
		
		
		final ShipRentModel model = modellist.get(position);
		
		holder.title.setText(model.gettitle());
		holder.shiptype.setText(model.getshiptype());
		holder.tons.setText(model.gettons()+"吨");
		
		
		if("".equals(model.getrentprice())){
			holder.rentprice.setText("面议");
		}else{
		holder.rentprice.setText(model.getrentprice());
		}
		holder.postdate.setText(model.getpostdate());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(context, ShipRentDetailsActivity.class);
				in.putExtra("title", model.gettitle());
				in.putExtra("shiptype", model.getshiptype());
				in.putExtra("shipname", model.getshipname());
				in.putExtra("load", model.gettons());
				if("".equals( model.getrentprice())){
					in.putExtra("rentprice", "面议");
				}else{
				in.putExtra("rentprice", model.getrentprice());
				}
				in.putExtra("linkman", model.getlinkman());
				in.putExtra("tel", model.gettel());
				in.putExtra("remark", model.getremark());
				in.putExtra("postdate", model.getpostdate());
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 跳转界面
				context.startActivity(in);
			}
		});
		
		return convertView;
	}
	
	
	class ViewHolder {
//
//		 "id": 0,
//	      "title": "货船出租",
//	      "shiptype": "货船",
//	      "shipname": "浙湖州货1670",
//	      "tons": "10",
//	      "rentprice": "1000",
//	      "linkman": "焦明",
//	      "tel": "159",
//	      "remark": "备注",
//	      "postdate": "2016-06-02 15:32:06"
		
		TextView id; // 
		TextView title; // 
		TextView shiptype; // 
		TextView shipname; // 
		TextView tons; // 
		TextView rentprice; // 
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
