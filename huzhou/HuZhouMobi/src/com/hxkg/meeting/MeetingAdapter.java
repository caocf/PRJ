package com.hxkg.meeting;
import java.util.ArrayList;
import java.util.List;
import com.example.huzhouport.R;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MeetingAdapter extends BaseAdapter
{
	List<MeetingRecord> list=new ArrayList<>();
	Context context;
	
	public MeetingAdapter(Context context,List<MeetingRecord> list)
	{
		this.context=context;
		this.list=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
		// TODO Auto-generated method stubturn null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView=View.inflate(context, R.layout.item_meetingrecord, null);
		
		TextView title=(TextView) convertView.findViewById(R.id.text1);
		TextView topic=(TextView) convertView.findViewById(R.id.topic);
		TextView time=(TextView) convertView.findViewById(R.id.time);
		TextView status=(TextView) convertView.findViewById(R.id.status);
		TextView isread=(TextView) convertView.findViewById(R.id.isread);
		
		MeetingRecord map=list.get(position);
		String topicString=(String) map.getTopic();
		String timeString=(String) map.getTime();
		String dateString=(String) map.getDate();
		String usernameString=(String) map.getUsername();
		int statusint=(int) map.getStatusShow();
		
		switch (statusint) 
		{
		case 1:
			status.setText("待审核");
			status.setTextColor(Color.parseColor("#ff8400"));
			break;
		case 2:
			status.setText("同意");
			status.setTextColor(Color.GREEN);
			break;
		case 3:
			status.setText("驳回");
			status.setTextColor(Color.RED);
			break;
		case 4:
			status.setText("已撤回");
			status.setTextColor(Color.parseColor("#f9f9f9"));
			break;

		default:
			break;
		}
		
		if(map.getIsread()==0)
		{
			isread.setVisibility(View.VISIBLE);
			isread.setTextColor(Color.RED);
		}else {
			isread.setVisibility(View.INVISIBLE);
			isread.setTextColor(Color.RED);
		}
		
		title.setText(usernameString+"的申请");
		topic.setText(topicString);
		//time.setText(dateString+" "+timeString);
		time.setText(map.getAptime().replace("T", " "));
		
		return convertView;
	}

}
