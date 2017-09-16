package net.hxkg.ship;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.hxkg.ghmanager.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CheckInfoAdapter extends BaseAdapter 
{

	private Context context;
	private List<CheckInfo> modellist;
	SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-MM-dd");

	public CheckInfoAdapter(Context context, List<CheckInfo> modellist) {
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
		convertView = LinearLayout.inflate(context, R.layout.activity_checklist_item, null);
        holder.LX=(TextView)convertView.findViewById(R.id.JFXM);
        holder.RQ=(TextView)convertView.findViewById(R.id.YXRQ);
        
        final CheckInfo modelCheckInfo=modellist.get(position);
        
        holder.LX.setText(modelCheckInfo.getJYZL());
        holder.RQ.setText(modelCheckInfo.getJYKSRQ());
        
        convertView.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				Intent in = new Intent();
				in.setClass(context, CheckDetail.class);
				in.putExtra("CheckInfo", modelCheckInfo);
				
				
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 跳转界面  跳转到船货圈找船详情界面
				context.startActivity(in);
				
			}
		});        
        
		return convertView;
	}

	class ViewHolder {

		
		TextView LX;//证书名称
		TextView RQ;//发证日期

	}
}
