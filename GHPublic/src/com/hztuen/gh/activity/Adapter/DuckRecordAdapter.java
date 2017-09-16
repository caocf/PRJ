package com.hztuen.gh.activity.Adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.gh.modol.DuckDangerRecordModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.DangerDuckRecordDetailActivity;
import com.hztuen.gh.activity.DuckDangersActivity;
import com.hztuen.gh.activity.DuckDangersRecordActivity;
import com.hztuen.gh.contact.contants;

public class DuckRecordAdapter extends BaseAdapter
{	
	private Context context;
	private List<DuckDangerRecordModel> recordletinlist;
	
	public PopupWindow popupWindowArea;

	public DuckRecordAdapter(Context context, List<DuckDangerRecordModel> recordletinlist) 
	{
		this.context = context;
		this.recordletinlist = recordletinlist;
	}

	@Override
	public int getCount() {

		return recordletinlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return recordletinlist.get(position);
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

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context,R.layout.activity_danger_duck_record_item, null);
		
		holder.tv_goods = (TextView) convertView.findViewById(R.id.text2_context);
		holder.tv_goodsweight = (TextView) convertView.findViewById(R.id.goods_weight);
		holder.tv_tuin=(TextView)convertView.findViewById(R.id.danwei);			
		holder.tv_start = (TextView) convertView.findViewById(R.id.text3);
		holder.tv_target = (TextView) convertView.findViewById(R.id.text3_context);	
		holder.tv_portime = (TextView) convertView.findViewById(R.id.text4);
		holder.tv_status = (TextView) convertView.findViewById(R.id.text_state1);
		holder.btn_cancel=(TextView)convertView.findViewById(R.id.cancel); 
		LinearLayout layout=(LinearLayout) convertView.findViewById(R.id.linea);	
		holder.llLayout=(LinearLayout) convertView.findViewById(R.id.ll);
        
		final DuckDangerRecordModel model = recordletinlist.get(position);
		
		holder.tv_start.setText(model.getstartport());
		holder.tv_target.setText(model.gettargetport());
		holder.tv_goods.setText(model.getgoods());
		holder.tv_goodsweight.setText(model.getgoodsweight());
		holder.tv_portime.setText(model.getportime());
		holder.tv_tuin.setText(model.getunit());
		
		if("1".equals(model.statusidString))
		{
			holder.tv_status.setText("待审批");
		}else if("2".equals(model.statusidString))
		{
			holder.tv_status.setText("通过");
			holder.tv_status.setTextColor(Color.GREEN);
		}else if("3".equals(model.statusidString))
		{
			holder.tv_status.setText("驳回");
			holder.tv_status.setTextColor(Color.RED);
		}else{
			holder.tv_status.setText("未提交");
			holder.tv_status.setTextColor(Color.GREEN);
		}
		if("0".equals(model.statusidString))
		{
			layout.setVisibility(View.VISIBLE);
			holder.btn_cancel.setVisibility(View.GONE);
			
		}else if("1".equals(model.statusidString))
		{
			layout.setVisibility(View.GONE);
			holder.btn_cancel.setVisibility(View.VISIBLE);
		}
		else
		{
			layout.setVisibility(View.GONE);
			holder.btn_cancel.setVisibility(View.GONE);
		}
		
		OnClick onClick=new OnClick(model);
		holder.btn_cancel.setOnClickListener(onClick);
		TextView deleTextView=(TextView) convertView.findViewById(R.id.deleTextView);
		TextView updaTextView=(TextView) convertView.findViewById(R.id.updaTextView);
		deleTextView.setOnClickListener(onClick);
		updaTextView.setOnClickListener(onClick);
		
		holder.llLayout.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(context, DangerDuckRecordDetailActivity.class);
				
				intent.putExtra("wharf", model.wharfnameString);
				intent.putExtra("ship", model.getship());
				intent.putExtra("startport", model.getstartport());
				intent.putExtra("goods", model.getgoods());
				intent.putExtra("targetport", model.gettargetport());
				intent.putExtra("goodsweight", model.getgoodsweight());
				
				intent.putExtra("goodstype", model.getgoodstype());
				
				intent.putExtra("portime", model.getportime());
				intent.putExtra("status", model.getstatus());
				
				intent.putExtra("endtime", model.getendtime());
				
				intent.putExtra("unit", model.getunit());
				
				
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				
			}
		});

		return convertView;
	}

	class ViewHolder
	{
		TextView tv_start;
		TextView tv_target;
		TextView tv_goods;
		TextView tv_goodsweight;
		TextView tv_portime;		
		TextView tv_status;		
		TextView tv_tuin;		
		TextView btn_cancel;
		LinearLayout llLayout;
	}
	
	class OnClick implements OnClickListener,onResult
	{
		String idString;
		DuckDangerRecordModel model;

		public  OnClick(DuckDangerRecordModel model) 
		{
			this.idString=model.getid();
			this.model=model;
		}
		
		@Override
		public void onClick(View v) 
		{
			HttpRequest hr=new HttpRequest(this);	
			Map<String, Object> map=new HashMap<>();
			
			map.put("id", idString);
			String urlString="";
			
			switch(v.getId())
			{
			case R.id.cancel:
				urlString="com/etl/te_cancel";
				hr.post(contants.baseUrl+urlString, map);
				break;
			case R.id.deleTextView:
				urlString="etl/temp/remove1";
				hr.post(contants.baseUrl+urlString, map);
				break;
			case R.id.updaTextView:
				Intent intent=new Intent(context,DuckDangersActivity.class);
				intent.putExtra("DuckDangerRecordModel", model);//表示修改
				context.startActivity(intent);
				break;
			}			
		}

		@Override
		public void onSuccess(String result)
		{
			DuckDangersRecordActivity le=(DuckDangersRecordActivity)DuckRecordAdapter.this.context;
			le.GetRecord();
		}

		@Override
		public void onError(int httpcode) 
		{
			
		}
		
	}
}
