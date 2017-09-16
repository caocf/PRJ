package com.huzhouport.schedule;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.sql.MySQLiteHelper;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;
import com.huzhouport.upload.UploadActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class ScheduleClockTime extends Activity {
	private String eventId, week;
	private ImageButton img_back;
	private Spinner sp1, sp2;
	private Button bt_finish;
	private int toastNum = -1;
	private int toastNumber = -1;
	private int eventRemindType, eventRemind;
	private String userAgree, agreeReason;
	Map<String, Object> paramsDtae = new HashMap<String, Object>();
	private User user;
	private String EventTime,EventName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_clock);

		img_back = (ImageButton) findViewById(R.id.schedule_clock_back);
		sp1 = (Spinner) findViewById(R.id.schedule_clock_spinner1);
		sp2 = (Spinner) findViewById(R.id.schedule_clock_spinner2);
		bt_finish = (Button) findViewById(R.id.schedule_clock_button);

		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.schedule_spinner01,
				android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.schedule_spinner02,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter1);
		sp2.setAdapter(adapter2);

		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		user = (User) intent.getSerializableExtra("User");
		week = intent.getStringExtra("week");// 星期x

		new GetDate().execute();

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(ScheduleClockTime.this,
						ScheduleSeeTime.class);
				intent.putExtra("week", week);
				intent.putExtra("eventId", eventId);
				intent.putExtra("User", user);
				startActivity(intent);
				finish();
			}
		});
		bt_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				new UploadDate().execute();
				
			}
		});
	}

	class spinner1Listener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (toastNumber != -1)
				Toast.makeText(ScheduleClockTime.this,
						"提前" + arg0.getItemAtPosition(arg2).toString(),
						Toast.LENGTH_SHORT).show();
			toastNumber = arg2;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	}

	class spinner2Listener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (toastNum != -1)
				Toast.makeText(ScheduleClockTime.this,
						"选择的提醒方式：" + arg0.getItemAtPosition(arg2).toString(),
						Toast.LENGTH_SHORT).show();
			toastNum = arg2 + 1;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	}

	public void intDate(String result) {

		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			JSONArray jsonArray1 = (JSONArray) jsonArray.getJSONArray(0);
			JSONObject jsonObject0 = (JSONObject) jsonArray1.opt(0);
			EventName=jsonObject0.getString("eventName");
			EventTime = jsonObject0.getString("eventTime");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				// 事件用户关联表
				JSONObject jsonObject6 = (JSONObject) jsonArray2.opt(1);
				if (jsonObject6.getInt("userId") == user.getUserId()) {// 当前用户信息
					userAgree = jsonObject6.getString("userAgree");
					eventRemind = jsonObject6.getInt("eventRemind");
					eventRemindType = jsonObject6.getInt("eventRemindType");
					agreeReason = jsonObject6.getString("agreeReason");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (eventRemind) {
		case 0:
			sp1.setSelection(0);
			break;
		case 5:
			sp1.setSelection(1);
			break;
		case 10:
			sp1.setSelection(2);
			break;
		case 15:
			sp1.setSelection(3);
			break;
		case 20:
			sp1.setSelection(4);
			break;
		case 30:
			sp1.setSelection(5);
			break;
		case 60:
			sp1.setSelection(6);
			break;
		case 120:
			sp1.setSelection(7);
			break;
		case 720:
			sp1.setSelection(8);
			break;
		case 1440:
			sp1.setSelection(9);
			break;
		default:
			break;

		}
		sp2.setSelection(eventRemindType - 1);
		sp1.setOnItemSelectedListener(new spinner1Listener());
		sp2.setOnItemSelectedListener(new spinner2Listener());
	}

	class GetDate extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			Query query = new Query();
			return query.queryScheduleByEventId(eventId);
		}

		@Override
		protected void onPostExecute(String result) {
			intDate(result);
			super.onPostExecute(result);
		}

	}
	//--------------alarm-start-----
		public void UpdateScheduleClock(int id, String time,
				int remind, String name, int type) {
			if (remind != 0) {
				try {
					String daytimeString = CountTime.countClickTime(time, remind);
					if (CountTime.distanceBetweenCurren(daytimeString) < 0) {
						Long timelong = CountTime.string2Timestamp(daytimeString);
						// 修改数据库
						updateScheduleTable(this, timelong, id, name, type);
						cancelAlarm(id);
						addAlarm( timelong, id, name, type);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		private void updateScheduleTable(Context context, Long timelong2, int id,
				String name, int type) {
			MySQLiteHelper oHelper = new MySQLiteHelper(context);
			SQLiteDatabase db = oHelper.getWritableDatabase();
			Object[] array_objObjects = new Object[4];
			array_objObjects[0] = id;
			array_objObjects[1] = String.valueOf(timelong2);
			array_objObjects[2] = name;
			array_objObjects[3] = type;
			if(oHelper.SearchScheduleById(db,id)==0){
				oHelper.UpdateScheduleTable(db, array_objObjects);
			}
			else{			
				oHelper.InsertSchedule(db, array_objObjects);
			}
			

			if (db != null)
				db.close();
		}

		public void addAlarm(Long timelong, int id, String name,int type) {
			Intent intent = new Intent(this, AlarmReceiver.class);
			intent.putExtra("alarmType", String.valueOf(type));
			intent.putExtra("alarmName", name);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id,
					intent, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, timelong, pendingIntent);

		}

		public void cancelAlarm(int id) {

			Intent intent = new Intent(this, AlarmReceiver.class);
			PendingIntent sender = PendingIntent.getBroadcast(this, id, intent, 0);
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.cancel(sender);

		}
		//--------------alarm-end-----
	class UploadDate extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// 保存信息
			toastNumber=sp1.getSelectedItemPosition();
			toastNum=sp2.getSelectedItemPosition()+1;
			int valueOfRemind;
			switch (toastNumber) {
				case 0:
					valueOfRemind = 0;
					break;
				case 1:
					valueOfRemind = 5;
					break;
				case 2:
					valueOfRemind = 10;
					break;
				case 3:
					valueOfRemind = 15;
					break;
				case 4:
					valueOfRemind = 20;
					break;
				case 5:
					valueOfRemind = 30;
					break;
				case 6:
					valueOfRemind = 60;
					break;
				case 7:
					valueOfRemind = 120;
					break;
				case 8:
					valueOfRemind = 720;
					break;
				case 9:
					valueOfRemind = 1440;
					break;
				default:
					valueOfRemind = 0;
					break;
				}
			paramsDtae.put("scheduleEventUser.eventId", eventId);
			paramsDtae.put("scheduleEventUser.userId", user.getUserId());
			paramsDtae.put("scheduleEventUser.userAgree", userAgree);
			paramsDtae.put("scheduleEventUser.agreeReason", agreeReason);
			paramsDtae.put("scheduleEventUser.eventRemind", valueOfRemind);
			paramsDtae.put("scheduleEventUser.eventRemindType", toastNum);
			
			UpdateScheduleClock(Integer.parseInt(eventId),EventTime,
					valueOfRemind,EventName,toastNum);
			//String actionUrl = HttpUtil.BASE_URL + "AddAgreeReason";
			String actionUrl = HttpUtil.BASE_URL + 
					"upDateRemind.action?scheduleEventUser.eventId="+eventId+
					"&scheduleEventUser.userId="+user.getUserId()+"&scheduleEventUser.eventRemind="+valueOfRemind+"&scheduleEventUser.eventRemindType="+toastNum;
			/*HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "AddAgreeReason";
			String result = null;		
			try {
				result = hfu.post(actionUrl, paramsDtae);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;*/
			//UploadActivity.tool.addFile("设置闹钟",actionUrl, paramsDtae,null, null);
			HttpUtil.queryStringForPost(actionUrl);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			/*try {

				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = (JSONObject) jsonParser.nextValue();
				String jsonObject0 = data.getString("ieventId");
				if (jsonObject0.equals("1")) {
					Toast.makeText(ScheduleClock.this, "保存成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ScheduleClock.this,
							ScheduleSee.class);
					intent.putExtra("week", week);
					intent.putExtra("eventId", eventId);
					intent.putExtra("User", user);
					startActivity(intent);
					finish();
				}

				else
					Toast.makeText(ScheduleClock.this, "保存失败",
							Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				Toast.makeText(ScheduleClock.this, "发生错误", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}*/
			/*Intent intent = new Intent(ScheduleClockTime.this,
					ScheduleSee.class);
			intent.putExtra("week", week);
			intent.putExtra("eventId", eventId);
			intent.putExtra("User", user);
			startActivity(intent);*/
			finish();
			super.onPostExecute(result);
		}

	}
}
