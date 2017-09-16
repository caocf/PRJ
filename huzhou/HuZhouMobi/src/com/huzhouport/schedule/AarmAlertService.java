package com.huzhouport.schedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.huzhouport.common.sql.MySQLiteHelper;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.IBinder;
/**
 * 闹钟轮询服务
 * 沈丹丹
***/
public class AarmAlertService extends Service {
	private User user;

	private Timer timer;
	private TimerTask task;
	List<Map<String, Object>> alermList;

	public void onCreate() {
		// TODO 自动生成的方法存根
		task = new TimerTask() {
			public void run() {
				cancelAlarm();
			}
		};
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 开始运行 
		timer = new Timer();
		if (timer == null) {
			timer.schedule(task, GlobalVar.SERVICE_TIME, GlobalVar.SERVICE_TIME);
		}
		if(intent!=null)
		{	user = (User) intent.getSerializableExtra("User");
			saveHostUser();
			cancelAlarm();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void onDestroy() {
		if (timer != null) {
			timer.cancel(); // 将原任务从队列中移除
		}
		super.onDestroy();
	}

	private void saveHostUser() {
		MySQLiteHelper oHelper = new MySQLiteHelper(getApplicationContext());
		SQLiteDatabase db = oHelper.getWritableDatabase();
		// 删除原用户
		Boolean iFlag = oHelper.DeleteHostUser(db);
		if (iFlag) {
			Object[] array_objObjects = new Object[3];
			array_objObjects[0] = user.getUserId();
			array_objObjects[1] = user.getUserName();
			array_objObjects[2] = user.getName();
			oHelper.InsertHostUser(db, array_objObjects);
		}
		if (db != null)
			db.close();
	}

	private int searchHostUser() {
		MySQLiteHelper oHelper = new MySQLiteHelper(getApplicationContext());
		SQLiteDatabase db = oHelper.getWritableDatabase();
		// 删除原用户
		int ret = oHelper.SearchHostUser(db);

		if (db != null)
			db.close();
		
		return ret;
	}

	public void addAlarm() {
		for (int i = 0; i < alermList.size(); i++) {
			int id = (Integer) alermList.get(i).get("id");
			long timelong = (Long) alermList.get(i).get("time");
			String alarmType = (String) alermList.get(i).get("type");
			Intent intent = new Intent(AarmAlertService.this,
					AlarmReceiver.class);
			intent.putExtra("alarmType", alarmType);
			intent.putExtra("alarmName", (String) alermList.get(i).get("name"));
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					AarmAlertService.this, id, intent,
					android.app.PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, timelong, pendingIntent);
		}
	}

	class getScheduleByTime extends AsyncTask<Integer, Void, String> {

		@Override
		protected String doInBackground(Integer... params) {
			Query query = new Query();
			return query.EventClickListByUserId(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = (JSONObject) jsonParser.nextValue();
				JSONArray jsonArray = data.getJSONArray("list");
				alermList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject3 = (JSONObject) jsonArray.opt(i);

					int iRemind = getClockTime(jsonObject3
							.getInt("eventRemind"));
					try {
						if (iRemind != 0) {
							String daytimeString = CountTime
									.countClickTime(
											jsonObject3.getString("eventTime"),
											iRemind);
							if (CountTime.distanceBetweenCurren(daytimeString) < 0) {
								int eventId=jsonObject3.getInt("eventId");
								String eventName=jsonObject3.getString("eventName");
								Long time=CountTime.string2Timestamp(daytimeString);
								String type=jsonObject3.getString("eventRemindType");
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", eventId);
								map.put("name", eventName);
								map.put("time",time);
								map.put("type", type);
								alermList.add(map);

							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			saveSchedule();
			addAlarm();
			super.onPostExecute(result);
		}

	}

	public int getClockTime(int iremind) {
		if (iremind == 1) {
			iremind = 60;
		}
		if (iremind == 2) {
			iremind = 120;
		}
		return iremind;

	}

	public void cancelAlarm() {
		//删除元闹钟
		MySQLiteHelper oHelper = new MySQLiteHelper(getApplicationContext());
		SQLiteDatabase db = oHelper.getReadableDatabase();
		List<Map<String, Object>> alarmList=oHelper.SearchSchedule(db);
		if(db!=null)
			db.close();
		for (int i = 0; i < alarmList.size(); i++) {
			int id=(Integer) alarmList.get(i).get("EventID");
			Intent intent = new Intent(AarmAlertService.this,
					AlarmReceiver.class);
			PendingIntent sender = PendingIntent.getBroadcast(this, id,
					intent, 0);
			AlarmManager am;
			am = (AlarmManager) getSystemService(ALARM_SERVICE);
			am.cancel(sender);
		}
		
		
		if (user.getUserId() != 0)
			new getScheduleByTime().execute(user.getUserId());
		else {
			if (searchHostUser() != 0)
				new getScheduleByTime().execute(searchHostUser());
		}
	}
	
	private void saveSchedule() {
		MySQLiteHelper oHelper = new MySQLiteHelper(getApplicationContext());
		SQLiteDatabase db = oHelper.getWritableDatabase();
		// 删除原日程
		oHelper.DeleteSchedule(db);
		for(int i=0;i<alermList.size();i++){
			Object[] array_objObjects = new Object[4];
			array_objObjects[0] = alermList.get(i).get("id");
			array_objObjects[1] = alermList.get(i).get("time");
			array_objObjects[2] = alermList.get(i).get("name");
			array_objObjects[3] = alermList.get(i).get("type");
			oHelper.InsertSchedule(db, array_objObjects);
		}
		if (db != null)
			db.close();
	}

}
