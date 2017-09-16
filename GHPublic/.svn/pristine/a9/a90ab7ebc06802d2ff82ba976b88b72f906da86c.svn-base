package com.hztuen.gh.activity.Adapter;

import java.util.List;
import com.gh.modol.WaterInfo;
import com.hxkg.ghpublic.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午4:09:27
 */
public class WaterListAdapter extends BaseAdapter{
	public static final String TAG = WaterListAdapter.class.getSimpleName();
	private Context context;
	private List<WaterInfo> waterInfoList;
	public WaterListAdapter(Context context,List<WaterInfo> waterInfoList){
		this.context = context;
		this.waterInfoList = waterInfoList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return waterInfoList.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return waterInfoList.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.water_list_info, null);
		}
		double syl = waterInfoList.get(position).getSyl();//警戒水位
		double ss = syl - waterInfoList.get(position).getSwsz();
		TextView watchpoint = (TextView) convertView.findViewById(R.id.watchpoint);
		TextView swsz = (TextView) convertView.findViewById(R.id.swsz);
		watchpoint.setText(waterInfoList.get(position).getWhatchpoint());
		swsz.setText(waterInfoList.get(position).getSwsz()+"米");
		if(ss<0){
			swsz.setTextColor(context.getResources().getColor(R.color.over));
		}else if(ss>=0&&ss<=1.0){
			swsz.setTextColor(context.getResources().getColor(R.color.near));
		}
		else{
			swsz.setTextColor(context.getResources().getColor(R.color.normal));
		}
		return convertView;
	}
	public void pre(List<WaterInfo> waterInfoList){
		this.waterInfoList = waterInfoList;
		notifyDataSetChanged();
	}
}
