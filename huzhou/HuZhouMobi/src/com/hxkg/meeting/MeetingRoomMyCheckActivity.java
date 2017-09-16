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
import android.app.ProgressDialog;
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

public class MeetingRoomMyCheckActivity extends Activity  implements OnCheckedChangeListener,OnItemClickListener,onResult
{
	User user;
	
	RadioGroup rg;
	RadioButton r1,r2;
	ListView listView;
	MeetingAdapter meetingAdapter;
	List<MeetingRecord> list=new ArrayList<>();
	
	final int STATUS_PROCESSING=0;
	final int STATUS_PROCESSED=1;
	
	String urlString=HttpUtil.BASE_URL+"queryApplicationRecordByApprover";
	int status=STATUS_PROCESSING;
	
	TextView tipTextView;
	ProgressDialog progressDialog=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingroommycheck_activity);
		user=(User) getIntent().getSerializableExtra("User");
		
		initView() ;
		
	}
	
	@Override
	public void onResume()
	{
		getData(status);
		super.onResume();
	} 
	
	private void  initView() 
	{
		rg=(RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(this);
		r1=(RadioButton) findViewById(R.id.r1);
		r2=(RadioButton) findViewById(R.id.r2);
		
		listView=(ListView) findViewById(R.id.listview);
		meetingAdapter=new MeetingAdapter(this, list);
		listView.setAdapter(meetingAdapter);
		listView.setOnItemClickListener(this);
		tipTextView=(TextView) findViewById(R.id.tip);
	}
	
	private void  getData(int status)
	{		
		urlString=user.getUserId()==2988?HttpUtil.BASE_URL+"queryApplicationRecordByOffice":
										 HttpUtil.BASE_URL+"queryApplicationRecordByApprover";		
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("user.userId", user.getUserId());
		map.put("meetingBasic.status",status);
		map.put("meetingBasic.depstatus",status);
		httpRequest.post(urlString, map);
		tipTextView.setVisibility(View.GONE);
		progressDialog=new ProgressDialog(this);
		progressDialog.setMessage("数据加载中");
		progressDialog.show();
	}
	
	public void onBack(View view)
	{
		finish();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{		
		switch (checkedId)
		{
		case R.id.r1://处理中
			
			status=STATUS_PROCESSING;
			getData(STATUS_PROCESSING);
			
			break;
		case R.id.r2://已处理
			status=STATUS_PROCESSED;
			getData(STATUS_PROCESSED);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		MeetingRecord map=list.get(position);
		Intent intent;
		if(status==0)
			intent=new Intent(this,MeetingRoomCheckDetailActivity.class);
		else {
			intent=new Intent(this,MeetingRoomDetailActivity.class);
		}
		intent.putExtra("User", user);
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
			getOfficeCheck();
			return;
		}
		try 
		{
			JSONObject object=new JSONObject(result.trim());
			JSONArray listArray=object.getJSONArray("list");
			for(int i=0;i<listArray.length();i++)
			{
				JSONArray dataArray=listArray.getJSONArray(i);
				
				JSONObject recordJsonObject=dataArray.getJSONObject(0);
				int recordid=recordJsonObject.getInt("id");
				String meetingdate=recordJsonObject.getString("meetingdate");
				String meetingtime=recordJsonObject.getString("meetingtime");
				String topic=recordJsonObject.getString("topic");
				int status=recordJsonObject.getInt("status");
				int officeid=recordJsonObject.getInt("officeapprover");
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
				
				JSONObject userObject=dataArray.getJSONObject(2);//申请人信息				
				String applicationor=userObject.getString("userName");	
				
				JSONObject approver=dataArray.getJSONObject(3);//审批人信息				
				String approverstring=approver.getString("userName");
				int approveid=approver.getInt("userId");
								
				MeetingRecord meetingRecord=new MeetingRecord();
				meetingRecord.setId(recordid);
				meetingRecord.setDate(meetingdate.substring(0,10));
				meetingRecord.setTime(meetingtime);
				meetingRecord.setTopic(topic);
				meetingRecord.setStatus(status);
				meetingRecord.setUsername(applicationor);
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
				meetingRecord.setApprovername(approverstring);
				meetingRecord.setOfficeid(officeid);
				if(MeetingRoomMyCheckActivity.this.status==STATUS_PROCESSING)
				meetingRecord.setIsread(0);//审核列表屏蔽此字段
				else {
					meetingRecord.setIsread(1);//审核列表屏蔽此字段
				}
				if(user.getUserId()!=2988)
					meetingRecord.setStatusShow(status);
				else 
				{
					meetingRecord.setStatusShow(depstatus);
				}
				
				list.add(meetingRecord);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}		
		
		if(progressDialog!=null)progressDialog.dismiss();
		if(list.size()<=0)
		{
			tipTextView.setVisibility(View.VISIBLE);			
		}
		meetingAdapter.notifyDataSetChanged();
	}

	@Override
	public void onError(int httpcode) {
		if(progressDialog!=null)progressDialog.dismiss();
		
	}
	
	public void getOfficeCheck()
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("user.partOfDepartment", user.getPartOfDepartment());
		map.put("meetingBasic.depstatus",status);
		httpRequest.post(HttpUtil.BASE_URL+"queryApplicationRecordByOffice", map);
		
	}
}
