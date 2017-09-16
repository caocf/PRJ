package com.hztuen.gh.activity.Adapter;

import java.util.List;

import com.gh.modol.ShipBoughtModel; 
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.ShipBoughtDetailsActivity; 
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShipBoughtAdapter extends BaseAdapter 
{
	private Context context;
	private List<ShipBoughtModel> modellist;

	public ShipBoughtAdapter(Context context, List<ShipBoughtModel> modellist) {
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context, R.layout.ship_bought_item,
				null);

		holder.title = (TextView) convertView.findViewById(R.id.tv_title); // 标题
		holder.shiptype = (TextView) convertView.findViewById(R.id.shiptype); // 船种类
		holder.tons = (TextView) convertView.findViewById(R.id.tons); // 重量
		holder.price = (TextView) convertView.findViewById(R.id.price); // 售船价格
		holder.postdate = (TextView) convertView.findViewById(R.id.tv_date); // 提交时间
		holder.shipage = (TextView) convertView.findViewById(R.id.shipage); // 船龄

		final ShipBoughtModel model = modellist.get(position);

		holder.title.setText(model.gettitle());
		holder.shiptype.setText(model.getshiptype());
		holder.tons.setText(model.gettons()+"吨");
		if("".equals(model.getrentprice())){
			holder.price.setText("面议");
		}else{
			holder.price.setText(model.getrentprice());
		}
		
		holder.postdate.setText(model.getpostdate());
		holder.shipage.setText(model.getshipage() + "年");

		 convertView.setOnClickListener(new OnClickListener() {
		
		 @Override
		 public void onClick(View v) {
		 // TODO Auto-generated method stub
		 Intent in = new Intent();
		 in.setClass(context, ShipBoughtDetailsActivity.class);
		 in.putExtra("title", model.gettitle());
		 in.putExtra("shiptype", model.getshiptype());
		 in.putExtra("shipname", model.getshipname());
		 in.putExtra("load", model.gettons());
		 
		 if("".equals(model.getrentprice())){
			 in.putExtra("price","面议"); 
		 }else{
			 in.putExtra("price", model.getrentprice());
		 }
		
		 in.putExtra("linkman", model.getlinkman());
		 in.putExtra("tel", model.gettel());
		 in.putExtra("remark", model.getremark());
		 in.putExtra("postdate", model.getpostdate());
		 in.putExtra("shipage", model.getshipage());
		 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 // 跳转界面
		 context.startActivity(in);
		 }
		 });

		return convertView;
	}

	class ViewHolder {
		// "id": 0,
		// "title": "油船出售",
		// "shiptype": "油船",
		// "shipname": "浙嘉兴油560",
		// "shipage": "4",
		// "tons": "10",
		// "price": "8900",
		// "linkman": "沈磊",
		// "tel": "137",
		// "remark": "备注",
		// "postdate": "2016-06-02 15:35:02"
		TextView id; //
		TextView title; //
		TextView shiptype; //
		TextView shipname; //
		TextView tons; //
		TextView price; //
		TextView linkman; //
		TextView tel; //
		TextView remark; //
		TextView postdate; //
		TextView shipage; //

	}

}
