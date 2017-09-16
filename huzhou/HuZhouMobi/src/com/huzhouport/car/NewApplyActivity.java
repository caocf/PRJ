package com.huzhouport.car;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import net.hxkg.channel.HttpRequest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

public class NewApplyActivity extends Activity implements OnDateChangedListener,OnTimeChangedListener
{
	User user;
	TextView starttime,endtime;
	EditText number,location,target,reason,tel;
		
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	Button button;
	int year,month,day,hour,mimute;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newapply_activity);
		user=(User) getIntent().getSerializableExtra("User");
		initView();
		db=openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
	}
	
	private void initView()
	{
		starttime=(TextView) findViewById(R.id.starttime);
		endtime=(TextView) findViewById(R.id.endtime);
		
		number=(EditText) findViewById(R.id.number);
		location=(EditText) findViewById(R.id.location);
		target=(EditText) findViewById(R.id.target);
		reason=(EditText) findViewById(R.id.reason);
		tel=(EditText) findViewById(R.id.tel);
		
		button=(Button) findViewById(R.id.submit);
	}
	
	public void onViewClick(View view)
	{
		switch (view.getId()) 
		{
		case R.id.setup_back:
		case R.id.title:
					finish();		
					break;
		case R.id.submit:
			if(starttime.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请选择用车时间", Toast.LENGTH_SHORT).show();
				return;
			}
			if(endtime.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请选择还车时间", Toast.LENGTH_SHORT).show();
				return;
			}
			if(!isTimeRight())return;
			if(number.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请填写用车人数", Toast.LENGTH_SHORT).show();
				return;
			}
			if(location.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请填写上车地点", Toast.LENGTH_SHORT).show();
				return;
			}
			if(target.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请填写目的地", Toast.LENGTH_SHORT).show();
				return;
			}
			if(reason.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请填写用车理由", Toast.LENGTH_SHORT).show();
				return;
			}
			submit();
			break;
		case R.id.starttime:
			showDateTime(starttime) ;
			break;
		case R.id.endtime:
			showDateTime(endtime) ;
			break;
		}
	}
	
	private boolean  isTimeRight()
	{
		try {
			Date date1=simpleDateFormat.parse(starttime.getText().toString().trim());
			Date date2=simpleDateFormat.parse(endtime.getText().toString().trim());
			if(date1.before(date2))
			{
				
				return true;
			}
		} catch (ParseException e) {
			Toast.makeText(this, "请先选择时间", Toast.LENGTH_SHORT).show();
			return false;
		}
		Toast.makeText(this, "用车时间不能晚于还车时间", Toast.LENGTH_SHORT).show();
		return false;
	}
	
	private void  submit() 
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{
			
			@Override
			public void onSuccess(String result)
			{
				try 
				{
					JSONObject object=new JSONObject(result.trim());
					JSONObject mb=object.getJSONObject("carApplication");
					int id =mb.getInt("id");
					int status=1;
					Calendar calendar=Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					db.execSQL("REPLACE INTO CarRecord VALUES (?, ?,?,?,?)",new Object[]{id,calendar.getTime(),status,1,user.getUserId()});
					
					
					Toast.makeText(NewApplyActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(NewApplyActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
				}
				NewApplyActivity.this.finish();
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("carApplication.userid", user.getUserId());
		map.put("user.partOfDepartment", user.getPartOfDepartment());
		map.put("d1", starttime.getText().toString().trim());
		map.put("d2", endtime.getText().toString().trim());
		
		map.put("carApplication.number", number.getText().toString().trim());
		map.put("carApplication.location", location.getText().toString().trim());
		map.put("carApplication.target", target.getText().toString().trim());
		map.put("carApplication.tel", tel.getText().toString().trim());
		map.put("carApplication.reason", reason.getText().toString().trim());
		httpRequest.post(HttpUtil.BASE_URL+"applyCar", map);
		button.setEnabled(false);
	}
	
	private void  showDateTime(final TextView text) 
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setView(View.inflate(this, R.layout.dialog_datetime, null));
		builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				String string=String.format("%4d-%02d-%02d %02d:%02d", year,month+1,day,hour,mimute);
				text.setText(string);
				
			}
		});
		builder.setNegativeButton("取消", null);
		AlertDialog dialog=builder.create();
		dialog.show();
		
		DatePicker datePicker=(DatePicker) dialog.findViewById(R.id.pdate);
		Calendar calendar=Calendar.getInstance();
		datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
	
		TimePicker timePicker=(TimePicker) dialog.findViewById(R.id.ptime);
		
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(this);
		
		year=calendar.get(Calendar.YEAR);
		month=calendar.get(Calendar.MONTH);
		day=calendar.get(Calendar.DAY_OF_MONTH);
		mimute=calendar.get(Calendar.MINUTE);
		hour=calendar.get(Calendar.HOUR_OF_DAY);
	}


	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth)
	{
		this.year=year;
		this.month=monthOfYear;
		this.day=dayOfMonth;	
		
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		this.hour=hourOfDay;
		this.mimute=minute;
		
	}	
}
