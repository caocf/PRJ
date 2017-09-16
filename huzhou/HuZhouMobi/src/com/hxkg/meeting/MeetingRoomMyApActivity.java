package com.hxkg.meeting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import net.hxkg.channel.HttpRequest;
import net.hxkg.channel.HttpRequest.onResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

public class MeetingRoomMyApActivity extends Activity  implements OnCheckedChangeListener,OnItemClickListener,onResult
{
	User user;
	
	RadioGroup rg;
	RadioButton r1,r2,r3,r4;
	ListView listView;
	MeetingAdapter meetingAdapter;
	List<MeetingRecord> list=new ArrayList<>();
	
	final int STATUS_PROCESSING=1;
	final int STATUS_POLLBACK=4;
	final int STATUS_APPROVED=2;
	final int STATUS_REJECT=3;
	
	int status=STATUS_PROCESSING;
	
	TextView tipTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingroommyap_activity);
		user=(User) getIntent().getSerializableExtra("User");
		
		initView() ;
		
	}
	
	private void  initView() 
	{
		rg=(RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(this);
		r1=(RadioButton) findViewById(R.id.r1);
		r2=(RadioButton) findViewById(R.id.r2);
		r3=(RadioButton) findViewById(R.id.r3);
		r4=(RadioButton) findViewById(R.id.r4);
		
		listView=(ListView) findViewById(R.id.listview);
		meetingAdapter=new MeetingAdapter(this, list);
		listView.setAdapter(meetingAdapter);
		listView.setOnItemClickListener(this);
		
		tipTextView=(TextView) findViewById(R.id.tip);
		
	}
	
	@Override
	public void onResume()
	{
		getData(status);
		super.onResume();
	}
	
	private void  getData(int status)
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("user.userId", user.getUserId());
		map.put("meetingBasic.status",status);
		httpRequest.post(HttpUtil.BASE_URL+"queryApplicationRecordByStatus", map);
	}
	
	public void onBack(View view)
	{
		finish();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		tipTextView.setVisibility(View.GONE);
		switch (checkedId)
		{
		case R.id.r1://处理中
			status=STATUS_PROCESSING;
			break;
		case R.id.r2://撤回
			status=STATUS_POLLBACK;
			break;
		case R.id.r3://同意
			status=STATUS_APPROVED;
			break;
		case R.id.r4://驳回
			status=STATUS_REJECT;
			break;

		default:
			break;
		}
		getData(status);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		MeetingRecord map=list.get(position);
		Intent intent=new Intent(this,MeetingRoomDetailActivity.class);
		//intent.putExtra("status", status);
		intent.putExtra("MeetingRecord", map);
		startActivity(intent);
	}

	@Override
	public void onSuccess(String result) 
	{
		list.clear();
		if("".equals(result.trim()))
		{
			meetingAdapter.notifyDataSetChanged();
			tipTextView.setVisibility(View.VISIBLE);
			return;
		}
		try {
			JSONObject object=new JSONObject(result.trim());
			JSONArray listArray=object.getJSONArray("list");
			for(int i=0;i<listArray.length();i++)
			{
				JSONArray dataArray=listArray.getJSONArray(i);
				
				JSONObject recordJsonObject=dataArray.getJSONObject(0);
				String meetingdate=recordJsonObject.getString("meetingdate");
				String meetingtime=recordJsonObject.getString("meetingtime");
				String topic=recordJsonObject.getString("topic");
				int id=recordJsonObject.getInt("id");
				int isread=recordJsonObject.getInt("isread");
				int status=recordJsonObject.getInt("status");
				String approvetime="";
				if(status==2||status==3)
				{
					approvetime=recordJsonObject.getString("approvetime");
				}
				String tel=recordJsonObject.getString("tel");
				String aptime=recordJsonObject.getString("applytime");
				int depstatus=recordJsonObject.getInt("depstatus");
				String depaptime="";
				if(depstatus==2||depstatus==3)
				{
					depaptime=recordJsonObject.getString("depaptime");
				}
				String approvemark= recordJsonObject.getString("approvemark");
				String depapmark= recordJsonObject.getString("depapmark");
				
				JSONObject roomObject=dataArray.getJSONObject(1);
				String roomname=roomObject.getString("typename");
				int roomid=roomObject.getInt("id");
				
				JSONObject userObject=dataArray.getJSONObject(2);//审批人信息
				int approveid=userObject.getInt("userId");
				String approvename=userObject.getString("userName");				
								
				MeetingRecord meetingRecord=new MeetingRecord();
				meetingRecord.setId(id);
				meetingRecord.setDate(meetingdate.substring(0,10));
				meetingRecord.setTime(meetingtime);
				meetingRecord.setTopic(topic);
				meetingRecord.setStatus(status);
				meetingRecord.setUsername(user.getName());
				meetingRecord.setTel(tel);
				meetingRecord.setAptime(aptime);
				meetingRecord.setRoomname(roomname);
				meetingRecord.setRoomid(roomid);
				meetingRecord.setDepstatus(depstatus);
				meetingRecord.setApprovetime(approvetime);
				meetingRecord.setDepaptime(depaptime);
				meetingRecord.setApprovemark(approvemark);
				meetingRecord.setDepapmark(depapmark);
				meetingRecord.setApproveid(approveid);
				meetingRecord.setApprovername(approvename);
				meetingRecord.setIsread(isread);
				
				if(meetingRecord.getStatus()!=3&&meetingRecord.getDepstatus()==1)
					this.status=1;
				else if(meetingRecord.getStatus()==2&&meetingRecord.getDepstatus()==2)
					this.status=2;
				else if(meetingRecord.getStatus()==3||meetingRecord.getDepstatus()==3)
					this.status=3;
				else {
					this.status=4;
				}
				meetingRecord.setStatusShow(this.status);
				
				list.add(meetingRecord);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		meetingAdapter.notifyDataSetChanged();
		
		if(list.size()<=0)
		{
			tipTextView.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
