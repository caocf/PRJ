package com.example.smarttraffic.common.suggestion;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 历史记录列表适配器
 * @author Administrator zhou
 *
 */
public class SuggestionHistoryAdapter extends BaseAdapter
{
	List<Suggestion> data;
	Context context;

	public SuggestionHistoryAdapter(Context context,List<Suggestion> data)
	{
		this.data=data;
		if(this.data==null)
			data=new ArrayList<Suggestion>();
		this.context=context;
	}
		
	public List<Suggestion> getData()
	{
		return data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		ViewHolder holder;

		convertView = LayoutInflater.from(context).inflate(R.layout.listview_suggestion,null);
		holder = new ViewHolder();
		
		holder.contact=(TextView)convertView.findViewById(R.id.listview_suggestion_contact);
		holder.content=(TextView)convertView.findViewById(R.id.listview_suggestion_content);
		holder.date=(TextView)convertView.findViewById(R.id.listview_suggestion_date);
		holder.person=(TextView)convertView.findViewById(R.id.listview_suggestion_persion);
		holder.title=(TextView)convertView.findViewById(R.id.listview_suggestion_title);

		holder.contact.setText("联系方式："+data.get(position).getContact());
		holder.content.setText("建议内容："+data.get(position).getContent());
		holder.date.setText("日期："+data.get(position).getDate().toString());
		holder.person.setText("联系人："+data.get(position).getPersion());
		holder.title.setText("标题："+data.get(position).getTitle());
		
		return convertView;
	}
	
	public void update()
	{
		notifyDataSetChanged();
	}
	
	class ViewHolder
	{
		TextView title;
		TextView content;
		TextView contact;
		TextView person;
		TextView date;
	}
}

