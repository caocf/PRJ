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

public class CarMyApActivity extends Activity  implements OnCheckedChangeListener,OnItemClickListener,onResult
{
	User user;
	
	RadioGroup rg;
	RadioButton r1,r2,r3,r4;
	ListView listView;
	CarAdapter meetingAdapter;
	List<CarApplication> list=new ArrayList<>();
	
	final int STATUS_PROCESSING=1;
	final int STATUS_POLLBACK=4;
	final int STATUS_APPROVED=2;
	final int STATUS_REJECT=3;
	
	int status=1;
	
	TextView tipTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carmyap_activity);
		user=(User) getIntent().getSerializableExtra("User");
		
		initView() ;
		getData(STATUS_PROCESSING);
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
		meetingAdapter=new CarAdapter(this, list);
		listView.setAdapter(meetingAdapter);
		listView.setOnItemClickListener(this);
		
		tipTextView=(TextView) findViewById(R.id.tip);
		
	}
	
	private void  getData(int status)
	{
		HttpRequest httpRequest=new HttpRequest(this);
		Map<String, Object> map=new HashMap<>();
		map.put("user.userId", user.getUserId());
		map.put("status",status);
		httpRequest.post(HttpUtil.BASE_URL+"findMyCarAps", map);
		//httpRequest.post("http://192.168.1.135:6080/HuZhouPort/"+"findMyCarAps", map);
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
		CarApplication map=list.get(position);
		Intent intent=new Intent(this,CarDetailActivity.class);
		//intent.putExtra("status", status);
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
					
				JSONObject app1=dataArray.getJSONObject(1);
				String name=app1.getString("name");
				int userId=app1.getInt("userId");
								
				CarApplication car=new CarApplication();
				car.setUserid(user.getUserId());
				car.setUsername(user.getName());
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
				car.setApprover1id(userId);
				car.setApname(name);
				
				if(car.getStatus1id()!=3&&car.getStatus2id()==1)
					this.status=1;
				else if(car.getStatus1id()==2&&car.getStatus2id()==2)
					this.status=2;
				else if(car.getStatus1id()==3||car.getStatus2id()==3)
					this.status=3;
				else {
					this.status=4;
				}
				car.setStatusShow(status);
				
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
		
	}

	@Override
	public void onError(int httpcode) {
		// TODO Auto-generated method stub
		
	}
}
