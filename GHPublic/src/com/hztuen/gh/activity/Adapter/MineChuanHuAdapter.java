package com.hztuen.gh.activity.Adapter;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gh.modol.MineShipModel;
import com.gh.modol.MyShipItemStateModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.MineShipDetailsActivity;
import com.hztuen.lvyou.utils.SystemStatic;

public class MineChuanHuAdapter extends BaseAdapter {
	
	private Context context;
	private List<MineShipModel> modellist;
	
	
	private String jiaofei,weizhang,zhengshu;
	
	public MineChuanHuAdapter(Context context, List<MineShipModel> modellist,List<MyShipItemStateModel> modelliststate ) {
		this.context = context;
		this.modellist = modellist;
//		this.modelliststate=modelliststate;
	}
	
	public MineChuanHuAdapter(Context context, List<MineShipModel> modellist) {
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
	public View getView(final int position, View convertView, ViewGroup parent) 
	{ 
		final ViewHolder holder;
		if (convertView == null) 
		{
			holder = new ViewHolder();
			convertView = LinearLayout.inflate(context, R.layout.activity_mine_ship_item, null);
			convertView.setTag(holder);
	        holder.shipname=(TextView)convertView.findViewById(R.id.ship_name);
	        holder.tv_jiaofei=(TextView)convertView.findViewById(R.id.text1_context);
	        holder.tv_weizhang=(TextView)convertView.findViewById(R.id.text2_context);
	        holder.tv_zhengshu=(TextView)convertView.findViewById(R.id.text3_context);
	        holder.img_ship_state=(ImageView)convertView.findViewById(R.id.img_ship_state);
	        
	        holder.ll=(LinearLayout) convertView.findViewById(R.id.detail);
	        holder.ll.setVisibility(View.VISIBLE);
	        
	        
        }else
        {
        	holder = (ViewHolder) convertView.getTag();
        }
		convertView.setOnClickListener(null);
		//船舶状态
		
		
		
        
        MyShipItemStateModel modelliststate  = modellist.get(position).getmMyShipItemStateModels();
		if (modelliststate != null) 
		{
			 jiaofei = modelliststate.getjiaofei();
			if ("0".equals(jiaofei)) {
				holder.tv_jiaofei.setText("正常");
				holder.tv_jiaofei.setTextColor(Color.BLACK);
			} else if ("1".equals(jiaofei)) {
				holder.tv_jiaofei.setText("警告");
				holder.tv_jiaofei.setTextColor(Color.YELLOW);
			} else if ("2".equals(jiaofei)) {
				holder.tv_jiaofei.setText("异常");
				holder.tv_jiaofei.setTextColor(Color.RED);
			}else{
				holder.tv_jiaofei.setText("N/A");
				holder.tv_jiaofei.setTextColor(Color.RED);
			}

			 weizhang = modelliststate.getweizhang();

			if ("0".equals(weizhang)) {
				holder.tv_weizhang.setText("正常");
				holder.tv_weizhang.setTextColor(Color.BLACK);
			} else if ("1".equals(weizhang)) {
				holder.tv_weizhang.setText("警告");
				holder.tv_weizhang.setTextColor(Color.YELLOW);
			} else if ("2".equals(weizhang)) {
				holder.tv_weizhang.setText("异常");
				holder.tv_weizhang.setTextColor(Color.RED);
			}else{
				holder.tv_weizhang.setText("N/A");
				holder.tv_weizhang.setTextColor(Color.RED);
			}

			zhengshu = modelliststate.getzhengshu();

			if ("0".equals(zhengshu)) {
				holder.tv_zhengshu.setText("正常");
				holder.tv_zhengshu.setTextColor(Color.BLACK);
			} else if ("1".equals(zhengshu)) {
				holder.tv_zhengshu.setText("警告");
				holder.tv_zhengshu.setTextColor(Color.YELLOW);
			} else if ("2".equals(zhengshu)) {
				holder.tv_zhengshu.setText("异常");
				holder.tv_zhengshu.setTextColor(Color.RED);
			}else{
				holder.tv_zhengshu.setText("N/A");
				holder.tv_zhengshu.setTextColor(Color.RED);
			}
		}
		
		
		
		
		
		
		
		
		final MineShipModel model = modellist.get(position); 	       
        holder.shipname.setText(model.getshipname());
		//初始化审核状态
		String state=model.status.statusnameString;		
		if("".equals(state)){
			
		}else if("1".equals(state)){
			holder.img_ship_state.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shenhezhong));
			holder.ll.setVisibility(View.GONE);
			
		}else if("3".equals(state)){
			holder.img_ship_state.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.refer));
			holder.ll.setVisibility(View.GONE);
		}else if("2".equals(state)){
			holder.img_ship_state.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.check_details));
			holder.ll.setVisibility(View.VISIBLE);
		}else{
			
		}      
      
        //点击详情事件
		if("2".equals(state))
		{
			  convertView.setOnClickListener(new OnClickListener() 
		        {			
					@Override
					public void onClick(View v) 
					{
						SystemStatic.searchShipName=model.getshipname();
						Intent in = new Intent();
						in.setClass(context, MineShipDetailsActivity.class);
						String id=modellist.get(position).getshipid();
						in.putExtra("shipid", modellist.get(position).getshipname());
						in.putExtra("jiaofei", modellist.get(position).getmMyShipItemStateModels().getjiaofei());
						in.putExtra("weizhang", modellist.get(position).getmMyShipItemStateModels().getweizhang());
						in.putExtra("zhengshu", modellist.get(position).getmMyShipItemStateModels().getzhengshu());
						//in.putExtra("jiaofei_reason", modellist.get(position).getmMyShipItemStateModels().getjiaofei_reason());
						in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(in);
					}
				});
		}
      		
        
		return convertView;
	}

	class ViewHolder 
	{	
		TextView shipname;	
		TextView reason;
		TextView time;
		LinearLayout ll;
		TextView tv_jiaofei;//未缴费
		TextView tv_weizhang;//未处理违章
		TextView tv_zhengshu;//证书到期
		
		ImageView img_ship_state;
	}
}
