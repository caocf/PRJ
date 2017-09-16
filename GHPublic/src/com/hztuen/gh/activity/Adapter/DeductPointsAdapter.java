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

import com.gh.modol.BreakRulesModel;
import com.gh.modol.DeductPointsModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.BreakRulesDetailActivity;
import com.hztuen.gh.activity.Adapter.BreakRulesAdapter.ViewHolder;

public class DeductPointsAdapter extends BaseAdapter{
	
	private Context context;
	private List<DeductPointsModel> modellist;

	public DeductPointsAdapter(Context context, List<DeductPointsModel> modellist) {
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
		convertView = LinearLayout.inflate(context, R.layout.activity_deduct_points_item, null);
        holder.penalty=(TextView)convertView.findViewById(R.id.tv_title);
        holder.reason=(TextView)convertView.findViewById(R.id.tv_reason);
        holder.time=(TextView)convertView.findViewById(R.id.tv_time);
      
        
    	final DeductPointsModel model = modellist.get(position);
       
        holder.penalty.setText(model.getpenalty());
        holder.reason.setText(model.getreason());
        holder.time.setText(model.gettime());
      
        
       
		
        
		return convertView;
	}

	class ViewHolder {

		//{"data":[{"id":2,"penalty":-5,"reason":"分数","time":null}],"total":1,"page":1,"rows":1,"pages":1}
		
		
		TextView penalty;	
		TextView reason;
		TextView time;
		
		

	}

}
