package com.huzhouport.car;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.huzhouport.R;
import com.huzhouport.main.User;

public class CarHomeActivity extends Activity 
{
	User user;
	final String tb_CarRecord="CREATE TABLE IF NOT EXISTS " 
			+ "CarRecord"+ " (id  INTEGER,recordtime TEXT,status INTEGER,type INTEGER,userid INTEGER)";
	SQLiteDatabase db;
	
	TextView tx1,tx2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carhome_activity);
		user=(User) getIntent().getSerializableExtra("User");
	}
	
	@Override
	public void onResume()
	{
		//initDB();
		super.onResume();
	}
	
	private void  initDB() 
	{
		db=this.openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
		db.execSQL(tb_CarRecord);
		
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Cursor cursor=db.query("CarRecord", null, 
				"type=1 and recordtime='"+calendar.getTime()+"'"+
				" and status="+1+" and userid="+user.getUserId(),
				null, null, null, null);
		
		int status1= cursor.getCount();
		cursor=db.query("CarRecord", null, 
				"type=1 and recordtime='"+calendar.getTime()+"'"+
				" and status="+2+" and userid="+user.getUserId(),
				null, null, null, null);
		int status2=cursor.getCount();
		cursor=db.query("CarRecord", null, 
				"type=1 and recordtime='"+calendar.getTime()+"'"+
				" and status="+3+" and userid="+user.getUserId(),
				null, null, null, null);
		int status3=cursor.getCount();
		
		String s1=status1>0?"今日新增申请:"+status1+"条\n":"\n";
		String s2=status2+status3>0?"今日收到"+(status2+status3)+"条审批回复":"";
		
		tx1.setText(s1+s2);	
		//审批者
		cursor=db.query("CarRecord", null, 
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
		Intent intent=new Intent(this,NewApplyActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	
	public void onMyAp(View view)
	{
		Intent intent=new Intent(this,CarMyApActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	public void onMyCheck(View view)
	{
		Intent intent=new Intent(this,CarMyCheckActivity.class);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	
}
