package com.hxkg.meeting;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import net.hxkg.channel.HttpRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

public class MeetingRoomHomeActivity extends Activity 
{
	User user;
	final String CREATE_tbM = "CREATE TABLE IF NOT EXISTS " 
			+ "meetingrecord"+ " (id  INTEGER,recordtime TEXT,status INTEGER,type INTEGER,userid INTEGER)";
	SQLiteDatabase db;
	
	TextView tx1,tx2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingroomhome_activity);
		user=(User) getIntent().getSerializableExtra("User");	
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
	}
	@Override
	public void onResume()
	{
		initTip() ;
		super.onResume();
	}
	
	public  void  initTip() 
	{
		tx1.setText("");
		tx2.setText("");
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() 
		{			
			@Override
			public void onSuccess(String result) 
			{
				try 
				{
					JSONObject jsonObject=new JSONObject(result.trim());
					int ap=jsonObject.getInt("ap");
					int check=jsonObject.getInt("check");
					int officecheck=jsonObject.getInt("officecheck");
					
					if(ap>0)
						tx1.setText("您有"+ap+"条申请已批复");
					if(check>0)
						tx2.setText("您有"+check+"条申请待审批");
					if(officecheck>0)
						tx2.setText("您有"+officecheck+"条申请待审批");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			@Override
			public void onError(int httpcode) 
			{
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("user.userId", user.getUserId());
		httpRequest.post(HttpUtil.BASE_URL +"countAp", map);
	}
	
	
	private void  initDB()
	{
		db=this.openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
		db.execSQL(CREATE_tbM);
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Cursor cursor=db.query("meetingrecord", null, 
				"type=1  and recordtime='"+calendar.getTime()+"'"+
				" and status="+1+" and userid="+user.getUserId(),
				null, null, null, null);
		
		int status1= cursor.getCount();
		cursor=db.query("meetingrecord", null, 
				"type=1 and recordtime='"+calendar.getTime()+"'"+
				" and status="+2+" and userid="+user.getUserId(),
				null, null, null, null);
		int status2=cursor.getCount();
		cursor=db.query("meetingrecord", null, 
				"type=1 and recordtime='"+calendar.getTime()+"'"+
				" and status="+3+" and userid="+user.getUserId(),
				null, null, null, null);
		int status3=cursor.getCount();
		
		String s1=status1>0?"今日新增申请:"+status1+"条\n":"\n";
		String s2=status2+status3>0?"今日收到"+(status2+status3)+"条审批回复":"";
		
		tx1.setText(s1+s2);		
		
		//审批者
				cursor=db.query("meetingrecord", null, 
						"type=2 and recordtime='"+calendar.getTime()+"'"+
						" and status="+1+" and userid="+user.getUserId(),
						null, null, null, null);
				int statuscheck=cursor.getCount();
				String s3=statuscheck>0?"今日收到"+(statuscheck)+"条申请需审批":"";
				tx2.setText(s3);
	}
	
	public void onBack(View view)
	{
		finish();
	}
	
	public void onNewAp(View view)
	{
		Intent intent=new Intent(this,MeetingRoomActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	
	public void onMyAp(View view)
	{
		Intent intent=new Intent(this,MeetingRoomMyApActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	public void onMyCheck(View view)
	{
		Intent intent=new Intent(this,MeetingRoomMyCheckActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	
}
