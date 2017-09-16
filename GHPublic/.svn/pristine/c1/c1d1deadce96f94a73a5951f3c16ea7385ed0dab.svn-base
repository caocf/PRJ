package com.hztuen.gh.activity.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gh.modol.NewsCommentModel;
import com.hxkg.ghpublic.R;

/**
 * @author zzq
 * @DateTime 2016年7月12日 下午4:08:27
 */
public class NewsCommentAdapter extends BaseAdapter{
	private final static String TAG = NewsCommentAdapter.class.getSimpleName();
	private Context context;
	private List<NewsCommentModel> commentmodel = new ArrayList<NewsCommentModel>();
	public NewsCommentAdapter(Context context,List<NewsCommentModel> commentmodel){
		this.context = context;
		this.commentmodel = commentmodel;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentmodel.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commentmodel.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.news_comment_item, null);
		}
		TextView comment_user = (TextView) convertView.findViewById(R.id.comment_user);
		TextView comment_time = (TextView) convertView.findViewById(R.id.comment_time);
		TextView comment_info = (TextView) convertView.findViewById(R.id.comment_info);
		comment_user.setText("用户 :"+commentmodel.get(position).getUsername());
		comment_time.setText(commentmodel.get(position).getSumbtime());
		comment_info.setText(commentmodel.get(position).getContent());
		return convertView;
	}
}
