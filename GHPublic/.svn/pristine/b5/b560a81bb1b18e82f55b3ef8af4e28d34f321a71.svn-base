package com.hztuen.gh.activity.Adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gh.modol.CredCardModel;
import com.gh.modol.PayInfoModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.CredCardAdapter.ViewHolder;

public class PayInfoAdapter extends BaseAdapter{
	
	private Context context;
	private List<PayInfoModel> modellist;

	public PayInfoAdapter(Context context, List<PayInfoModel> modellist) {
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
		convertView = LinearLayout.inflate(context, R.layout.activity_pay_info_item, null);
        holder.YJZE=(TextView)convertView.findViewById(R.id.tv_tv_pay_should_detail);
        holder.SJZE=(TextView)convertView.findViewById(R.id.tv_pay_now_detail);
        holder.YXQQ=(TextView)convertView.findViewById(R.id.tv_have_use_detail);
        holder.YXQZ=(TextView)convertView.findViewById(R.id.tv_have_end_details);
        holder.JFXMMC=(TextView)convertView.findViewById(R.id.tv_title);
        holder.qian=(TextView)convertView.findViewById(R.id.tv_qian_detail);
        
       
        holder.YJZE.setText(modellist.get(position).getYJZE());
        holder.SJZE.setText(modellist.get(position).getSJZE());
        holder.YXQQ.setText(modellist.get(position).getYXQQ());
        holder.YXQZ.setText(modellist.get(position).getYXQZ());
        holder.JFXMMC.setText(modellist.get(position).getJFXMMC());
        //如果应缴金额大与实缴金额，则欠费，字体改为红色
        if(Integer.valueOf( holder.YJZE.getText().toString()).intValue()>Integer.valueOf( holder.SJZE.getText().toString()).intValue())
        {
        	holder.qian.setText("是");
        	holder.qian.setTextColor(Color.parseColor("#ff2323"));   
        }
		return convertView;
	}

	class ViewHolder {

//		YJZE	应缴金额
//		SJZE	实缴金额
//		YXQQ	有效期起
//		YXQZ	有效期止
//		JFXMMC	缴费项目名称
		
		TextView YJZE;
		TextView SJZE;
		TextView YXQQ;
		TextView YXQZ;
		TextView JFXMMC;
		TextView qian;//是否欠费
		

	}

}
