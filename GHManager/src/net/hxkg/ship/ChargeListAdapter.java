package net.hxkg.ship;
import java.util.ArrayList;
import java.util.List;

import net.hxkg.ghmanager.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChargeListAdapter extends BaseAdapter
{
	
	private Context context;
	private List<ChargeModol> modellist;
	final String items[]={"警告","罚款","暂扣证照","吊销证照","没收船舶","没收非法财物","没收违法所得",
						"责令停产停业","行政拘留","其他"};
	public ChargeListAdapter(Context context, List<ChargeModol> modellist) {
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
		// TODO Auto-generated method stub
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context, R.layout.activity_chargelist_item, null);
        holder.JFXMMC=(TextView)convertView.findViewById(R.id.JFXM);
        holder.YXQQ=(TextView)convertView.findViewById(R.id.YXRQ);
       
        
    	final ChargeModol model = modellist.get(position);
       
    	holder.JFXMMC.setText(model.getJFXMMC());
    	holder.YXQQ.setText(model.getKPRQ());      
        
        convertView.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				Intent in = new Intent();
				in.setClass(context, ChargeDetail.class);
				in.putExtra("ChargeModol", model);
				
				
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 跳转界面  跳转到船货圈找船详情界面
				context.startActivity(in);
				
			}
		});
        
        
		return convertView;
	}

	class ViewHolder 
	{
		
		TextView JFXMMC;//受理时间	
		TextView YXQQ;//法案地点

	}
}
