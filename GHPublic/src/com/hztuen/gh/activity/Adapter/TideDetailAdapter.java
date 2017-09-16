package com.hztuen.gh.activity.Adapter;

import java.util.List;

import com.ghpublic.entity.TideDetail;
import com.hxkg.ghpublic.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TideDetailAdapter extends BaseAdapter{
private Context context;
private List<TideDetail> mTideDetail_list;
public TideDetailAdapter(Context context,List<TideDetail> mTideDetail_list){
	this.context = context;
	this.mTideDetail_list = mTideDetail_list;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTideDetail_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mTideDetail_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = LinearLayout.inflate(context, R.layout.tide_detail_item, null);
		}
		TextView hrs = (TextView) convertView.findViewById(R.id.hrs);
		TextView cm = (TextView) convertView.findViewById(R.id.cm);
		hrs.setText(mTideDetail_list.get(position).getFreshdate().substring(11, 16));
		cm.setText(mTideDetail_list.get(position).getStandard());
		return convertView;
	}

}
