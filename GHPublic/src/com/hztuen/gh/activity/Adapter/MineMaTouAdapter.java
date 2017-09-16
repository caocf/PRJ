package com.hztuen.gh.activity.Adapter;

import java.util.List;

import android.annotation.SuppressLint;
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



import com.gh.modol.MineMaTouItemStateModel;
import com.gh.modol.MineMatouMode;
import com.gh.modol.MineShipModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.MineShipDetailsActivity;
import com.hztuen.gh.activity.SearchDockListActivity;
import com.hztuen.gh.activity.StartActivity;
import com.hztuen.lvyou.utils.SystemStatic;

/**
 * @author zzq
 * @DateTime 2016年7月13日 下午3:26:57
 * MineMaTouAdapter
 */
public class MineMaTouAdapter extends BaseAdapter implements OnClickListener{
	private Context context;
	private List<MineMatouMode> modellist;
	 private String wharfname = "";
	private List<MineMaTouItemStateModel> modelliststate ;
	public MineMaTouAdapter(Context context, List<MineMatouMode> modellist,List<MineMaTouItemStateModel> modelliststate) {
		this.context = context;
		this.modellist = modellist;
		this.modelliststate = modelliststate;
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		if (convertView == null) {
			
		holder = new ViewHolder();
	
		convertView = LinearLayout.inflate(context, R.layout.activity_mine_matou_item, null);
		holder.wharf_name=(TextView)convertView.findViewById(R.id.wharfname);
	    holder.wharfnum=(TextView)convertView.findViewById(R.id.wharfnum);
	    holder.img_ship_state=(ImageView)convertView.findViewById(R.id.img_ship_state);
		
		convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
      
        
        
		wharfname = modellist.get(position).getWharfname();
		TextView text1_context = (TextView) convertView.findViewById(R.id.text1_context);
		TextView text2_context = (TextView) convertView.findViewById(R.id.text2_context);
       
        holder.wharf_name.setText(wharfname);
        holder.wharfnum.setText(modellist.get(position).getWharfnum());
        if(null != modelliststate && modelliststate.size() != 0){
        	text1_context.setText(modelliststate.get(position).getZhengshu());
            text2_context.setText(modelliststate.get(position).getZuoye());
        }
        
        
        String state=modellist.get(position).getOperations();
		
		if("".equals(state)){
			
		}else if("0".equals(state)){
			holder.img_ship_state.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shenhezhong));
			
		}else if("1".equals(state)){
			holder.img_ship_state.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.refer));
		}else if("2".equals(state)){
			holder.img_ship_state.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.check_details));
			
		}
        
        
        convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(context, SearchDockListActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("wharfname", modellist.get(position).getWharfname());
				context.startActivity(intent);
			}
		});
        
        
//		if (modelliststate.size() != 0 && modelliststate != null) {
//			String jiaofei = modelliststate.get(position).getjiaofei();
//			if ("0".equals(jiaofei)) {
//				holder.tv_jiaofei.setText("正常");
//				holder.tv_jiaofei.setTextColor(Color.BLACK);
//			} else if ("1".equals(jiaofei)) {
//				holder.tv_jiaofei.setText("警告");
//				holder.tv_jiaofei.setTextColor(Color.YELLOW);
//			} else if ("2".equals(jiaofei)) {
//				holder.tv_jiaofei.setText("异常");
//				holder.tv_jiaofei.setTextColor(Color.RED);
//			}
//
//			String weizhang = modelliststate.get(position).getweizhang();
//
//			if ("0".equals(weizhang)) {
//				holder.tv_weizhang.setText("正常");
//				holder.tv_weizhang.setTextColor(Color.BLACK);
//			} else if ("1".equals(weizhang)) {
//				holder.tv_weizhang.setText("警告");
//				holder.tv_weizhang.setTextColor(Color.YELLOW);
//			} else if ("2".equals(weizhang)) {
//				holder.tv_weizhang.setText("异常");
//				holder.tv_weizhang.setTextColor(Color.RED);
//			}
//
//			String zhengshu = modelliststate.get(position).getzhengshu();
//
//			if ("0".equals(zhengshu)) {
//				holder.tv_zhengshu.setText("正常");
//				holder.tv_zhengshu.setTextColor(Color.BLACK);
//			} else if ("1".equals(zhengshu)) {
//				holder.tv_zhengshu.setText("警告");
//				holder.tv_zhengshu.setTextColor(Color.YELLOW);
//			} else if ("2".equals(zhengshu)) {
//				holder.tv_zhengshu.setText("异常");
//				holder.tv_zhengshu.setTextColor(Color.RED);
//			}
//		}
//        
//    	final MineShipModel model = modellist.get(position);
//       
//        holder.shipname.setText(model.getshipname());
//       
//      
//        
//        convertView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				SystemStatic.searchShipName=model.getshipname();
//				Intent in = new Intent();
//				in.setClass(context, MineShipDetailsActivity.class);
//				
//				
//				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				
//				context.startActivity(in);
//			}
//		});
//        
//        
//        
//		
//        
		return convertView;
	}

	class ViewHolder {
		
		TextView wharf_name;	
		TextView wharfnum;
		TextView operations;
		
		LinearLayout whar_item;
		
		TextView tv_jiaofei;//未缴费
		TextView tv_weizhang;//未处理违章
		TextView tv_zhengshu;//证书到期
		
		
		ImageView img_ship_state;//审核状态
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

	
	
	
	
}
