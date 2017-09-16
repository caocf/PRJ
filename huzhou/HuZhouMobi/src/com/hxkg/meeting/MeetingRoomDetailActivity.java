package com.hxkg.meeting;
import java.util.HashMap;
import java.util.Map;

import net.hxkg.channel.HttpRequest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

public class MeetingRoomDetailActivity extends Activity
{
	TextView tx1,tx2, meetingroom,topic,tel,date,start,end,flow1,flow2,mark1,mark2,sign1,sign2;

	ImageView img,img1,img2;
	User user;
	MeetingRecord meetingRecord;
	LinearLayout container,flowl2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingroomdetail_activity);
		user=(User) getIntent().getSerializableExtra("User");
		meetingRecord=(MeetingRecord) getIntent().getSerializableExtra("MeetingRecord");
		initView();
		updateIsRead() ;
	}
	
	private void initView()
	{
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
		meetingroom=(TextView) findViewById(R.id.meetingroom);
		date=(TextView) findViewById(R.id.date);
		start=(TextView) findViewById(R.id.start);
		end=(TextView) findViewById(R.id.end);
		topic=(TextView) findViewById(R.id.topic);
		tel=(TextView) findViewById(R.id.tel);
		img=(ImageView) findViewById(R.id.img);
		img1=(ImageView) findViewById(R.id.img1);
		img2=(ImageView) findViewById(R.id.img2);
		flow1=(TextView) findViewById(R.id.flow1);
		flow2=(TextView) findViewById(R.id.flow2);
		mark1=(TextView) findViewById(R.id.mark1);
		mark2=(TextView) findViewById(R.id.mark2);
		sign1=(TextView) findViewById(R.id.sign1);
		sign2=(TextView) findViewById(R.id.sign2);
		container=(LinearLayout) findViewById(R.id.container);
		flowl2=(LinearLayout) findViewById(R.id.flowl2);
		
		if(meetingRecord==null)return;
		tx1.setText("申请人:  "+meetingRecord.getUsername());
		tx2.setText("申请时间:"+meetingRecord.getAptime().replace("T", " "));
		meetingroom.setText(meetingRecord.getRoomname());
		date.setText(meetingRecord.getDate());
		topic.setText(meetingRecord.getTopic());
		tel.setText(meetingRecord.getTel());
		start.setText("会议开始时间:\n"+meetingRecord.getTime().split("-")[0]);
		end.setText("会议结束时间:\n"+meetingRecord.getTime().split("-")[1]);
		
		if(meetingRecord.getStatus()!=3&&meetingRecord.getDepstatus()==1)
			img.setBackgroundResource(R.drawable.meeting_approving);
		else if(meetingRecord.getStatus()==2&&meetingRecord.getDepstatus()==2)
			img.setBackgroundResource(R.drawable.meeting_approved);
		else if(meetingRecord.getStatus()==3||meetingRecord.getDepstatus()==3)
			img.setBackgroundResource(R.drawable.meeting_reject);
		else {
			img.setBackgroundResource(R.drawable.pollback);
		}
		
		if(meetingRecord.getStatus()==2)
		{
			flow1.setText("同意");
			flow1.setTextColor(Color.GREEN);
			img1.setBackgroundResource(R.drawable.ic_state_agree);
			mark1.setVisibility(View.VISIBLE);
			mark1.setText(meetingRecord.getApprovemark());
			sign1.setVisibility(View.VISIBLE);
			sign1.setText(meetingRecord.getApprovername()+" "+meetingRecord.getApprovetime().replace("T", " "));
			
		}
		else if(meetingRecord.getStatus()==3)
		{
			flow1.setText("驳回");
			flow1.setTextColor(Color.RED);
			img1.setBackgroundResource(R.drawable.ic_state_reject);
			mark1.setVisibility(View.VISIBLE);
			mark1.setText(meetingRecord.getApprovemark());
			sign1.setVisibility(View.VISIBLE);
			sign1.setText(meetingRecord.getApprovername()+" "+meetingRecord.getApprovetime().replace("T", " "));
			
			flowl2.setVisibility(View.GONE);
		}
		
		if(meetingRecord.getDepstatus()==2&&meetingRecord.getStatus()!=3)
		{
			flow2.setText("同意");
			flow2.setTextColor(Color.GREEN);
			img2.setBackgroundResource(R.drawable.ic_state_agree);
			mark2.setVisibility(View.VISIBLE);
			mark2.setText(meetingRecord.getDepapmark());
			sign2.setVisibility(View.VISIBLE);
			sign2.setText("办公室"+" "+meetingRecord.getDepaptime().replace("T", " "));
			
		}else if(meetingRecord.getDepstatus()==3&&meetingRecord.getStatus()!=3)
		{
			flow2.setText("驳回");
			flow2.setTextColor(Color.RED);
			img2.setBackgroundResource(R.drawable.ic_state_reject);
			mark2.setVisibility(View.VISIBLE);
			mark2.setText(meetingRecord.getDepapmark());
			sign2.setVisibility(View.VISIBLE);
			sign2.setText("办公室"+" "+meetingRecord.getDepaptime().replace("T", " "));
		}
	}
	
	private void  updateIsRead() 
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
	
		});
		Map<String, Object> map=new HashMap<>();
		map.put("meetingBasic.id", meetingRecord.getId());
		httpRequest.post(HttpUtil.BASE_URL+"updateIsRead", map);
	}
	
	public void onViewClick(View view)
	{
		switch (view.getId()) 
		{
		case R.id.setup_back:
		case R.id.title:
					finish();		
					break;
		case R.id.record:
			if(meetingRecord==null)
			{
				return;
			}
			Intent intentrecord=new Intent(this,MeetingRecordActivity.class);
			intentrecord.putExtra("roomid", meetingRecord.getRoomid());
			String nameString= meetingroom.getText().toString();
			intentrecord.putExtra("roomname",nameString);
			startActivity(intentrecord);
			break;
		
		default:
			break;
		}
	}	
	
}
