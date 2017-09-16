package com.hxkg.ereport;

import java.util.ArrayList;
import java.util.List;

import com.hxkg.ghpublic.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EAdapter extends BaseAdapter
{
	List<EReportModol> list=new ArrayList<>();
	Context context;
	
	public EAdapter(Context context, List<EReportModol> list)
	{
		this.list=list;
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2)
	{
		view=View.inflate(context, R.layout.ereportitem, null);
		TextView ship=(TextView) view.findViewById(R.id.ship);
		TextView goods=(TextView) view.findViewById(R.id.goods);
		TextView line=(TextView) view.findViewById(R.id.line);
		TextView inout=(TextView) view.findViewById(R.id.inout);
		TextView reporttype=(TextView) view.findViewById(R.id.reporttype);
		TextView time=(TextView) view.findViewById(R.id.time);
		
		final EReportModol modol=list.get(position);
		
		ship.setText(modol.shipname);
		goods.setText(modol.goods);
		line.setText(modol.from+"->"+modol.to);
		inout.setText(modol.inout);
		reporttype.setText(modol.reportid);
		time.setText(modol.time); 
		
		view.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) 
			{
				Intent intent=new Intent(context,ReportInfoActivity.class);
				
				intent.putExtra("EReportModol", modol);
				context.startActivity(intent);
			}
			
		});
		
		return view;
	}

}
