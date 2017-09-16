package com.hztuen.gh.activity.Adapter;

import java.util.HashMap;
import java.util.List; 
import java.util.Map;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;
import com.gh.modol.RecordLetIn;
import com.hxkg.ghpublic.R;  
import com.hztuen.gh.activity.DangersLetInActivity;
import com.hztuen.gh.activity.LetInRecordActivity;
import com.hztuen.gh.contact.contants;
import android.content.Context; 
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup; 
import android.widget.BaseAdapter;
import android.widget.LinearLayout; 
import android.widget.TextView;

public class RecordLetInAdapter extends BaseAdapter 
{
	public Context context;
	private List<RecordLetIn> recordletinlist;

	public RecordLetInAdapter(Context context, List<RecordLetIn> recordletinlist) 
	{
		this.context = context;
		this.recordletinlist = recordletinlist;
	}

	@Override
	public int getCount() 
	{
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
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		final ViewHolder holder;

		holder = new ViewHolder();
		convertView = LinearLayout.inflate(context,
				R.layout.activity_letin_record_item, null);
		final RecordLetIn model = recordletinlist.get(position);

		holder.tv_start = (TextView) convertView.findViewById(R.id.text3);
		holder.tv_target = (TextView) convertView.findViewById(R.id.text3_context);
		holder.tv_rank = (TextView) convertView.findViewById(R.id.text2_context);
		holder.tv_tons = (TextView) convertView.findViewById(R.id.goods_weight);
		holder.tv_unit = (TextView) convertView.findViewById(R.id.danwei);
		holder.tv_berthtime = (TextView) convertView.findViewById(R.id.text4);
		holder.tv_status = (TextView) convertView.findViewById(R.id.text_state1);	

		holder.tv_start.setText(model.getstart());
		holder.tv_target.setText(model.gettarget());
		holder.tv_rank.setText(model.getrank());
		holder.tv_tons.setText(model.gettons());
		holder.tv_unit.setText(model.getunit());
		holder.tv_berthtime.setText(model.getberthtime());	 
		if("1".equals(model.getstatus()))
		{
			holder.tv_status.setText("待审批");
			//holder.tv_status.setTextColor(Color.RED);
		}else if("2".equals(model.getstatus()))
		{
			holder.tv_status.setText("通过");
			holder.tv_status.setTextColor(Color.GREEN);
		}else if("3".equals(model.getstatus()))
		{
			holder.tv_status.setText("驳回");
			holder.tv_status.setTextColor(Color.RED);
		}
		else if("0".equals(model.getstatus())){
			holder.tv_status.setText("未提交");
			holder.tv_status.setTextColor(Color.GREEN);
		}
		
		TextView cancel=(TextView) convertView.findViewById(R.id.cancel);
		LinearLayout layout=(LinearLayout) convertView.findViewById(R.id.linea);
		/*if("0".equals(model.getstatus()))
		{
			layout.setVisibility(View.VISIBLE);
			cancel.setVisibility(View.GONE);
			
		}else if("1".equals(model.getstatus()))
		{
			layout.setVisibility(View.GONE);
			cancel.setVisibility(View.VISIBLE);
		}
		else
		{
			layout.setVisibility(View.GONE);
			cancel.setVisibility(View.GONE);
		}*/
		
		TextView deleTextView=(TextView) convertView.findViewById(R.id.deleTextView);
		TextView updaTextView=(TextView) convertView.findViewById(R.id.updaTextView);
		OnClick onClick=new OnClick(model);
		cancel.setOnClickListener(onClick);
		deleTextView.setOnClickListener(onClick);
		updaTextView.setOnClickListener(onClick);
		

		return convertView;
	}

	class ViewHolder 
	{
		TextView tv_start;
		TextView tv_target;
		TextView tv_goods;
		TextView tv_rank;
		TextView tv_tons;
		TextView tv_unit;
		TextView tv_berthtime;
		TextView tv_status;	
	}

	class OnClick implements OnClickListener,onResult
	{
		String idString;
		RecordLetIn model;

		public  OnClick(RecordLetIn model) 
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
				urlString="tx_cancel";
				hr.post(contants.baseUrl+urlString, map);
				break;
			case R.id.deleTextView:
				urlString="etl/temp/remove";
				hr.post(contants.baseUrl+urlString, map);
				break;
			case R.id.updaTextView:
				Intent intent=new Intent(context,DangersLetInActivity.class);
				intent.putExtra("RecordLetIn", model);//表示修改
				context.startActivity(intent);
				break;
			}
			
			
		}

		@Override
		public void onSuccess(String result)
		{
			LetInRecordActivity le=(LetInRecordActivity)RecordLetInAdapter.this.context;
			le.GetRecord();
		}

		@Override
		public void onError(int httpcode) 
		{
			// TODO Auto-generated method stub
			
		}
		
	}
}
