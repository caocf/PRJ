package com.example.clander.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import com.example.huzhouport.R;

/**
 * Created with IntelliJ IDEA.
 * **********************************
 * User: zhangshuai
 * Date: 2016年 03月 14日
 * Time: 下午6:33
 *
 * @QQ : 1234567890
 * **********************************
 */
public class TestAdapter extends BaseAdapter {

    private ArrayList<Map<String, Object>> mList;
    private Context mContext;

    public TestAdapter(ArrayList<Map<String, Object>> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout,null);
        TextView  tv = (TextView) convertView.findViewById(R.id.user);
        TextView  topic = (TextView) convertView.findViewById(R.id.topic);
        TextView  time = (TextView) convertView.findViewById(R.id.time);
        String topicString= (String) mList.get(position).get("topic");
        String meetingdate= (String) mList.get(position).get("meetingdate");
        String meetingtime= (String) mList.get(position).get("meetingtime");
        String username= (String) mList.get(position).get("username");
        tv.setText(username+"的会议室申请" );
        topic.setText(topicString );
        time.setText(meetingdate.substring(0,10)+" "+meetingtime );
        ImageView img= (ImageView) convertView.findViewById(R.id.img);
        int status= (int) mList.get(position).get("status");
        switch (status) {
		case 1://待审批
			img.setBackgroundResource(R.drawable.meeting_approving);
			break;
		case 2://同意
			img.setBackgroundResource(R.drawable.meeting_approved);
			break;
		case 3://驳回
			img.setBackgroundResource(R.drawable.meeting_reject);
			break;

		default:
			break;
		}
        return convertView;
    }
}
