package net.hxkg.simple;

import java.util.ArrayList;
import java.util.List;
import net.hxkg.ghmanager.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleAdapt extends BaseAdapter
{
	List<SimpleModel> dateList=new ArrayList<>();
	Context context;
	
	public SimpleAdapt(Context context,List<SimpleModel> dateList)
	{
		this.dateList=dateList;
		this.context=context;
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return dateList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		return dateList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView=LinearLayout.inflate(context, R.layout.activity_simplelist_item, null);
		TextView target=(TextView) convertView.findViewById(R.id.target);
		TextView reason=(TextView) convertView.findViewById(R.id.reason);
		TextView time=(TextView) convertView.findViewById(R.id.time);
		TextView penalty=(TextView) convertView.findViewById(R.id.penalty);
		TextView status=(TextView) convertView.findViewById(R.id.status);
		
		SimpleModel smModel=dateList.get(position);
		target.setText(smModel.getTarget());
		reason.setText(smModel.getReason());
		time.setText(smModel.getSumbdate());
		penalty.setText("处罚:"+smModel.penaltyString);
		status.setText(smModel.getStatus());
		
		return convertView;
	}

}
