package com.huzhouport.car;
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

public class CarMyCheckActivity extends Activity  implements OnCheckedChangeListener,OnItemClickListener,onResult
{
	User user;
	
	RadioGroup rg;
	RadioButton r1,r2;
	ListView listView;
	CarAdapter meetingAdapter;
	List<CarApplication> list=new ArrayList<>();
	
	final int STATUS_PROCESSING=1;
	final int STATUS_PROCESSED=2;
	
	String urlString=HttpUtil.BASE_URL+"findMyCarCheck1";
			//"http://192.168.1.135:6080/HuZhouPort/findMyCarCheck1";
	int status=STATUS_PROCESSING;
	
	TextView tipTextView;
	ProgressDialog progressDialog=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carmycheck_activity);
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
		meetingAdapter=new CarAdapter(this, list);
		listView.setAdapter(meetingAdapter);
		listView.setOnItemClickListener(this);
		tipTextView=(TextView) findViewById(R.id.tip);
	}
	
	private void  getData(int status)
	{
		tipTextView.setVisibility(View.GONE);
		
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("carApplication.approver1id", user.getUserId());
		map.put("status",status);
		httpRequest.post(urlString, map);
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
		CarApplication map=list.get(position);
		Intent intent;
		if(status==1)//未审核
			intent=new Intent(this,CarCheckDetailActivity.class);
		else {
			intent=new Intent(this,CarDetailActivity.class);
		}
		intent.putExtra("User", user);
		intent.putExtra("CarApplication", map);
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
				int id=recordJsonObject.getInt("id");
				String aptime=recordJsonObject.getString("aptime");
				String starttime=recordJsonObject.getString("starttime");
				String endtime=recordJsonObject.getString("endtime");
				int number=recordJsonObject.getInt("number");
				String location=recordJsonObject.getString("location");
				String target=recordJsonObject.getString("target");
				String tel=recordJsonObject.getString("tel");
				String reason=recordJsonObject.getString("reason");
				String mark1=recordJsonObject.getString("mark1");
				String mark2=recordJsonObject.getString("mark2");
				int status1id=recordJsonObject.getInt("status1id");
				int status2id=recordJsonObject.getInt("status2id");	
				int officeid=recordJsonObject.getInt("approver2id");
				String aptime1="";
				if(status1id==2||status1id==3)
				{
					aptime1=recordJsonObject.getString("aptime1");
				}
				String aptime2="";
				if(status2id==2||status2id==3)
				{
					aptime2=recordJsonObject.getString("aptime2");
				}
				int carid=0;
				if(status2id==2)
				{
					carid=recordJsonObject.getInt("assignmentid");
				}
					
				JSONObject userobj=dataArray.getJSONObject(1);
				String name=userobj.getString("name");
				int userId=userobj.getInt("userId");
								
				CarApplication car=new CarApplication();
				car.setUserid(userId);
				car.setUsername(name);
				car.setAptime(aptime);
				car.setStarttime(starttime);
				car.setEndtime(endtime);
				car.setNumber(number);
				car.setLocation(location);
				car.setTarget(target);
				car.setTel(tel);
				car.setReason(reason);
				car.setMark1(mark1);
				car.setMark2(mark2);
				car.setStatus1id(status1id);
				car.setStatus2id(status2id);
				car.setAptime1(aptime1);
				car.setAptime2(aptime2);
				car.setAssignmentid(carid);
				car.setApprover1id(user.getUserId());
				car.setApname(user.getName());
				car.setId(id);
				car.setApprover2id(officeid);
				//if()
				car.setStatusShow(status1id);
				
				list.add(car);
			}
		} catch (Exception e) {
			if(progressDialog!=null)progressDialog.dismiss();
		}
		
		meetingAdapter.notifyDataSetChanged();
		if(list.size()<=0)
		{
			getOfficeCheck();			
		}else {
			if(progressDialog!=null)progressDialog.dismiss();
		}
	}

	@Override
	public void onError(int httpcode) {
		if(progressDialog!=null)progressDialog.dismiss();
		
	}
	
	public void getOfficeCheck()
	{
		HttpRequest httpRequest=new HttpRequest(new onResult() {
			
			@Override
			public void onSuccess(String result)
			{
				list.clear();
				try 
				{
					JSONObject object=new JSONObject(result.trim());
					JSONArray listArray=object.getJSONArray("list");
					for(int i=0;i<listArray.length();i++)
					{
						JSONArray dataArray=listArray.getJSONArray(i);
						
						JSONObject recordJsonObject=dataArray.getJSONObject(0);
						int id=recordJsonObject.getInt("id");
						String aptime=recordJsonObject.getString("aptime");
						String starttime=recordJsonObject.getString("starttime");
						String endtime=recordJsonObject.getString("endtime");
						int number=recordJsonObject.getInt("number");
						String location=recordJsonObject.getString("location");
						String target=recordJsonObject.getString("target");
						String tel=recordJsonObject.getString("tel");
						String reason=recordJsonObject.getString("reason");
						String mark1=recordJsonObject.getString("mark1");
						String mark2=recordJsonObject.getString("mark2");
						int status1id=recordJsonObject.getInt("status1id");
						int status2id=recordJsonObject.getInt("status2id");	
						int officeid=recordJsonObject.getInt("approver2id");
						String aptime1="";
						if(status1id==2||status1id==3)
						{
							aptime1=recordJsonObject.getString("aptime1");
						}
						String aptime2="";
						if(status2id==2||status2id==3)
						{
							aptime2=recordJsonObject.getString("aptime2");
						}
						int carid=0;
						if(status2id==2)
						{
							carid=recordJsonObject.getInt("assignmentid");
						}
							
						JSONObject userobj=dataArray.getJSONObject(1);
						String name=userobj.getString("name");
						int userId=userobj.getInt("userId");
						
						JSONObject apobj=dataArray.getJSONObject(2);
						String apname=apobj.getString("name");
						int apuserId=apobj.getInt("userId");
										
						CarApplication car=new CarApplication();
						car.setUserid(userId);
						car.setUsername(name);
						car.setAptime(aptime);
						car.setStarttime(starttime);
						car.setEndtime(endtime);
						car.setNumber(number);
						car.setLocation(location);
						car.setTarget(target);
						car.setTel(tel);
						car.setReason(reason);
						car.setMark1(mark1);
						car.setMark2(mark2);
						car.setStatus1id(status1id);
						car.setStatus2id(status2id);
						car.setAptime1(aptime1);
						car.setAptime2(aptime2);
						car.setAssignmentid(carid);
						car.setApprover1id(apuserId);
						car.setApname(apname);
						car.setId(id);
						car.setApprover2id(officeid);
						//if()
						car.setStatusShow(status2id);
						
						list.add(car);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				meetingAdapter.notifyDataSetChanged();
				if(list.size()<=0)
				{
					tipTextView.setVisibility(View.VISIBLE);
				}
				if(progressDialog!=null)progressDialog.dismiss();
			}
			
			@Override
			public void onError(int httpcode) {
				if(progressDialog!=null)progressDialog.dismiss();
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("carApplication.approver2id", user.getPartOfDepartment());
		map.put("status",status);
		httpRequest.post(HttpUtil.BASE_URL+"findMyCarCheck2", map);
		//httpRequest.post("http://192.168.1.135:6080/HuZhouPort/findMyCarCheck2", map);
	}
}
