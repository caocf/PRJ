package com.hztuen.gh.activity.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.gh.modol.GoodsMode; 
import com.gh.modol.ShipBoughtModel;
import com.gh.modol.ShipCircleListModel;
import com.gh.modol.ShipModel; 
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.GoodsDetailsAdcivity;
import com.hztuen.gh.activity.ShipBoughtDetailsActivity;
import com.hztuen.gh.activity.ShipDetailsActivity; 
import com.hztuen.gh.activity.ShipRentDetailsActivity;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.Util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter; 
import android.widget.ImageView;
import android.widget.LinearLayout; 
import android.widget.TextView;

public class ShipGoodsCircleListAdapter extends BaseAdapter implements OnClickListener
{
	private Context context;
	private List<ShipCircleListModel> modellist;
	
	
	public ShipGoodsCircleListAdapter(Context context,List<ShipCircleListModel> modellist){
		this.context = context;
		this.modellist = modellist;
	}
	@Override
	public int getCount() 
	{ 
		return modellist.size();
	}

	@Override
	public Object getItem(int position)
	{ 
		return modellist.get(position);
	}

	@Override
	public long getItemId(int position) 
	{ 
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{ 
		final ViewHolder holder;		
		holder = new ViewHolder();
		convertView=LinearLayout.inflate(context, R.layout.activity_ship_goods_circle_item, null);
		
		holder.title = (TextView)convertView.findViewById(R.id.tv_title); //标题
		holder.content = (TextView) convertView.findViewById(R.id.content); // 备注
		holder.price = (TextView) convertView.findViewById(R.id.tv_goods_money); // 货物价格 
		holder.postime = (TextView) convertView.findViewById(R.id.tv_date); // 提交时间
		holder.img_case = (ImageView) convertView.findViewById(R.id.img_case); // 图片 
		
		
		
		final ShipCircleListModel model = modellist.get(position);
		
		
		holder.title.setText(model.gettitle());
		holder.content.setText(model.getcontent());
		holder.price.setText(model.getprice()); 
		
		if("".equals(model.getpostime())||model.getpostime()==null){
			holder.postime.setText(model.getpostime());
		}else{
			String mypostime=model.getpostime().substring(0, 16);
			holder.postime.setText(mypostime);
		}
		 
		int typeid=Integer.valueOf(model.gettradetype());
		
		if(typeid==1)//货源
		{
			holder.img_case.setBackgroundResource(R.drawable.zhaohuo);
		}else if(typeid==2)//船源
		{
			holder.img_case.setBackgroundResource(R.drawable.zhaochuan);
			
		}else if(typeid==3)//租船
		{
			holder.img_case.setBackgroundResource(R.drawable.zuchuan);
		}else  //售船
		{
			holder.img_case.setBackgroundResource(R.drawable.maichuan);
		}
		
		
		convertView.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{
				if ("3".endsWith(model.gettradetype())) //出租
				{
					Intent in = new Intent();
					in.setClass(context,ShipRentDetailsActivity.class);
					in.putExtra("title", model.gettitle()); 
					in.putExtra("shiptype",model.shiptypename);
					in.putExtra("shipname", model.shipname);
					in.putExtra("load", model.load);
					in.putExtra("rentprice",model.getprice());
					in.putExtra("linkman", model.getcontent());
					in.putExtra("tel", model.tel);
					in.putExtra("remark", model.remark);
					
					in.putExtra("postdate", model.getpostime()); 
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(in);
					
				}
				
				// 我的发布记录，售船详情
				else if("4".endsWith(model.gettradetype())) //出售
				{
					Intent in = new Intent();
					 in.setClass(context, ShipBoughtDetailsActivity.class);
					 in.putExtra("title", model.gettitle());
					
						in.putExtra("shiptype",model.shiptypename);
					 in.putExtra("shipname", model.shipname);
					 in.putExtra("load", model.load);
					 in.putExtra("price", model.getprice());
					 in.putExtra("linkman", model.getcontent());
					 in.putExtra("tel", model.tel);
					 in.putExtra("remark", model.remark);
					 in.putExtra("postdate", model.getpostime());
					 in.putExtra("shipage", model.age); 
					 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// 跳转界面
					context.startActivity(in);					
					
				}
				// 我的发布记录，船源详情
				else if("2".endsWith(model.gettradetype()))
				{	
					Intent in = new Intent();
					in.setClass(context, ShipDetailsActivity.class);
					
					in.putExtra("title", model.gettitle()); 
					in.putExtra("shiptype",model.shiptypename);
					in.putExtra("shipname", model.shipname);
					in.putExtra("load", model.load);
					in.putExtra("rentprice", model.getprice());
					in.putExtra("linkman", model.getcontent());
					in.putExtra("tel", model.tel);
					in.putExtra("remark", model.remark);
					in.putExtra("postdate", model.getpostime()); 
					in.putExtra("emptydock", model.fromString);
					in.putExtra("targetdock",model.to);
					in.putExtra("route", model.route); 
					 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// 跳转界面
					context.startActivity(in);				
					
				}
				// 我的发布记录，货源详情
				else if("1".endsWith(model.gettradetype()))
				{
					Intent in = new Intent();
					in.setClass(context, GoodsDetailsAdcivity.class);
					in.putExtra("title", model.gettitle()); 
					in.putExtra("type", model.goodstypename);
					in.putExtra("name", model.goodsname);
					in.putExtra("tons", model.tons);
					in.putExtra("packaging", model.packaging);
					in.putExtra("linkman", model.getcontent());
					in.putExtra("tel", model.tel);
					in.putExtra("remark", model.remark);
					in.putExtra("postdate", model.getpostime());
					in.putExtra("price", model.getprice()); 
					in.putExtra("startport", model.fromString);
					in.putExtra("unloadport",model.to);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					
					context.startActivity(in);
				}				
			}
		});
		
		return convertView;
	}
	
	
	class ViewHolder
	{
		TextView id; // 
		TextView title; // 
		TextView content; // 
		TextView price; // 
		TextView tradetype; // 
		TextView postime; // 
		ImageView img_case;
		TextView tv_sourceid;

	}


	@Override
	public void onClick(View v) 
	{		
	}

}
