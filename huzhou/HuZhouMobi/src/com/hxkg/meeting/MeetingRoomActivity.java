package com.hxkg.meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import net.hxkg.channel.HttpRequest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.main.User;

public class MeetingRoomActivity extends Activity implements OnDateSetListener,OnTimeSetListener
{
	int roomid=-1;
	TextView meetingroom,date,start,end;
	EditText topic,tel;
	int ispicking=1;
	String starttime="",endtime="";
	User user;
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	Button button;
	
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingroom_activity);
		user=(User) getIntent().getSerializableExtra("User");
		initView();	
		
		db=this.openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
	}
	
	private void initView()
	{
		meetingroom=(TextView) findViewById(R.id.meetingroom);
		date=(TextView) findViewById(R.id.date);
		start=(TextView) findViewById(R.id.start);
		end=(TextView) findViewById(R.id.end);
		topic=(EditText) findViewById(R.id.topic);
		tel=(EditText) findViewById(R.id.tel);
		button=(Button) findViewById(R.id.submit);
	}
	
	public void onViewClick(View view)
	{
		switch (view.getId()) 
		{
		case R.id.meetingroom:
			if(date.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
				return;
			}
			if(!isTimeRight())
			{				
				return;
			}
			Intent intent=new Intent(this,MeetingRoomChooseActivity.class);
			intent.putExtra("date", date.getText().toString().trim());
			intent.putExtra("time", starttime+"-"+endtime);
			startActivityForResult(intent, 1);
			break;
		case R.id.setup_back:
		case R.id.title:
					finish();		
					break;
		case R.id.submit:
			if(roomid==-1)
			{
				Toast.makeText(this, "请选择会议室", Toast.LENGTH_SHORT).show();
				return;
			}
			/*if(date.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
				return;
			}
			if(!isTimeRight())
			{				
				return;
			}*/
			if(topic.getText().toString().trim().equals(""))
			{
				Toast.makeText(this, "请输入会议主题", Toast.LENGTH_SHORT).show();
				return;
			}
			submit();
			break;
		case R.id.record:
			if(roomid==-1)
			{
				Toast.makeText(this, "请选择会议室", Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intentrecord=new Intent(this,MeetingRecordActivity.class);
			intentrecord.putExtra("roomid", roomid);
			
			String nameString= meetingroom.getText().toString();
			intentrecord.putExtra("roomname",nameString.substring(0,nameString.indexOf("(")));
			startActivity(intentrecord);
			break;
		case R.id.date:
			pickDate();
			break;
		case R.id.start:
			pickTime(1);
			break;
		case R.id.end:
			pickTime(2);
			break;
		default:
			break;
		}
	}
	
	private void  submit() 
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{
			
			@Override
			public void onSuccess(String result) 
			{
				try {
					JSONObject object=new JSONObject(result.trim());
					JSONObject mb=object.getJSONObject("meetingBasic");
					int id =mb.getInt("id");
					int status=1;
					Calendar calendar=Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					//db.execSQL("REPLACE INTO meetingrecord VALUES (?, ?,?,?,?)",new Object[]{id,calendar.getTime(),status,1,user.getUserId()});
					MeetingRoomActivity.this.finish();
					
				} catch (Exception e) {
					Toast.makeText(MeetingRoomActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
				}
				//
				
			}
			
			@Override
			public void onError(int httpcode)
			{
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("meetingBasic.applicaionor", user.getUserId());
		map.put("meetingBasic.meetingroom", roomid);
		map.put("meetingBasic.topic", topic.getText().toString().trim());
		map.put("meetingBasic.meetingdate", date.getText().toString().trim());
		map.put("meetingBasic.meetingtime", starttime+"-"+endtime);
		map.put("meetingBasic.tel", tel.getText().toString().trim());
		map.put("user.partOfDepartment", user.getPartOfDepartment());
		httpRequest.post(HttpUtil.BASE_URL+"applyroom", map);
		button.setEnabled(false);
	}
	
	private void  pickDate() 
	{
		Calendar calendar=Calendar.getInstance();
		  DatePickerDialog dpd=new DatePickerDialog(this,this,calendar.get(Calendar.YEAR)
				  ,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
          dpd.show();
	}
	
	private void  pickTime(int ispicking) 
	{
		this.ispicking=ispicking;
		Calendar calendar=Calendar.getInstance();
		TimePickerDialog dpd=new TimePickerDialog(this,this,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
          dpd.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==100)
		{
			roomid=data.getIntExtra("id", -1);
			String nameString=data.getStringExtra("name");
			meetingroom.setText(nameString);
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
	{
		date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
		
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		if(ispicking==1)
		{
			start.setText("会议开始时间:\n\n"+String.format("%1$02d", hourOfDay)+":"+String.format("%1$02d", minute));
			starttime=String.format("%1$02d", hourOfDay)+":"+String.format("%1$02d", minute);
		}else {
			end.setText("会议结束时间:\n\n"+String.format("%1$02d", hourOfDay)+":"+String.format("%1$02d", minute));
			endtime=String.format("%1$02d", hourOfDay)+":"+String.format("%1$02d", minute);
		}
		
	}
	
	private boolean isTimeRight() 
	{
		String timeString=date.getText().toString().trim()+" "+starttime;
		Date date;
		try {
			date = simpleDateFormat.parse(timeString);
			if(date.before(new Date()))
			{
				Toast.makeText(this, "所选时间已过期", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		
		if(!starttime.equals("")&&!endtime.equals(""))
		{			
			Calendar calendar=Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(starttime.split(":")[0]));
			calendar.set(Calendar.MINUTE, Integer.valueOf(starttime.split(":")[1]));
			Date d1=calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(endtime.split(":")[0]));
			calendar.set(Calendar.MINUTE, Integer.valueOf(endtime.split(":")[1]));
			Date d2=calendar.getTime();
			
			if(!d1.before(d2))
			{
				Toast.makeText(this, "结束时间不能早于时间", Toast.LENGTH_SHORT).show();
				return false;
			}
			return true;
		}
		
		else
		{
			Toast.makeText(this, "请选择起止时间", Toast.LENGTH_SHORT).show();
			return false;
		}
		
	}	
}
