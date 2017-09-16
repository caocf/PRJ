package com.hztuen.gh.activity.Adapter;

import java.util.ArrayList;
import java.util.List;

import com.gh.modol.AroundSearchHistory;
import com.hxkg.ghpublic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午4:07:14
 * 搜索历史适配器
 */
public class AroundSerachHistoryAdapter extends BaseAdapter{
	private final static String TAG = AroundSerachHistoryAdapter.class.getSimpleName();
	private Context context;
	private List<AroundSearchHistory> list = new ArrayList<AroundSearchHistory>();
	public AroundSerachHistoryAdapter(Context context,List<AroundSearchHistory> list){
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView ==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.around_serach_history_item, null);
		}
		TextView history = (TextView) convertView.findViewById(R.id.history);
		history.setText(list.get(position).getAreaName());
		return convertView;
	}
}
