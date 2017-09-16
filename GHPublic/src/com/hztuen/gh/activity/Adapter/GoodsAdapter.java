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

import com.gh.modol.GoodsMode;
import com.gh.modol.ShipModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.GoodsDetailsAdcivity;
import com.hztuen.gh.activity.ShipDetailsActivity;
import com.hztuen.gh.activity.Adapter.ShipAdapter.ViewHolder;

public class GoodsAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<GoodsMode> modellist;
	
	
	public GoodsAdapter(Context context,List<GoodsMode> modellist){
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
		holder.name = (TextView)convertView.findViewById(R.id.tv_title_content); 
		holder.packaging = (TextView) convertView.findViewById(R.id.tv_ship_type); 
	
		holder.price = (TextView) convertView.findViewById(R.id.tv_goods_money); // 租船价格
		holder.postdate = (TextView) convertView.findViewById(R.id.tv_date); // 提交时间
		
		
		final GoodsMode model = modellist.get(position);
		
		holder.title.setText(model.gettitle());
		holder.packaging.setText(model.getpackaging());
		
		holder.price.setText(model.getprice()+"元");
		holder.postdate.setText(model.getpostdate());
		holder.name.setText(model.getname());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(context, GoodsDetailsAdcivity.class);
				in.putExtra("title", model.gettitle());
				in.putExtra("type", model.gettype());
				in.putExtra("name", model.getname());
				in.putExtra("tons", model.gettons());
				in.putExtra("packaging", model.getpackaging());
				in.putExtra("linkman", model.getlinkman());
				in.putExtra("tel", model.gettel());
				in.putExtra("remark", model.getremark());
				in.putExtra("postdate", model.getpostdate());
				in.putExtra("price", model.getprice());
				
				in.putExtra("startport", model.getstartport());
				in.putExtra("unloadport", model.getunloadport());
				
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 跳转界面  跳转到船货圈找船详情界面
				context.startActivity(in);
			}
		});
		
		return convertView;
	}
	
	
	class ViewHolder {
//		 "id": 2,
//	      "titile": "嘉兴拉货",
//	      "type": "c",
//	      "name": "沙子",
//	      "tons": "5",
//	      "packaging": "散装",
//	      "startport": "a",
//	      "unloadport": "b",
//	      "price": "1000",
//	      "linkman": "王三",
//	      "tel": "139",
//	      "remark": "备注",
//	      "postdate": "2016-06-02 15:25:28"
		
		TextView id; // 
		TextView title; // 
		TextView type; // 
		TextView name; // 
		TextView tons; // 
		
		TextView packaging; // 
		TextView startport; // 
		TextView unloadport; // 
		
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