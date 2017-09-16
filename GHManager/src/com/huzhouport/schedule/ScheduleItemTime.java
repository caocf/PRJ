package com.huzhouport.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hxkg.ghmanager.R;
import net.hxkg.user.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huzhouport.leaveandovertime.PushMsg;
import com.huzhouport.leaveandovertime.Query;
import com.huzhouport.leaveandovertime.WaitingDialog;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/*
 * ���鰲���б�
 * �����ˣ����Ľ�
 * �޸��ˣ��򵤵�
 */

public class ScheduleItemTime extends Activity {
	private SimpleAdapter adapter = null;
	private ImageButton img_addImage, img_back;
	private String week = "";
	private int userId;// �û�Id

	List<Map<String, Object>> list;
	private String hiddenTime = "";
	private ListView listvist;
	private Query query = new Query();
	AlarmManager am;// ������
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_main_listview_huiyi);

		img_back = (ImageButton) findViewById(R.id.schedule_main_listview_imgback);
		listvist = (ListView) findViewById(R.id.schedule_listview);
		img_addImage = (ImageButton) findViewById(R.id.schedule_main_listview__add);

		Intent intent = getIntent();
		userId = User.id;

		Calendar c = Calendar.getInstance();
		String scheduleDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String scheduleYear = String.valueOf(c.get(Calendar.YEAR));
		String scheduleMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
		String scheduleWeek = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(scheduleWeek)) {
			scheduleWeek = "��";
		} else if ("2".equals(scheduleWeek)) {
			scheduleWeek = "һ";
		} else if ("3".equals(scheduleWeek)) {
			scheduleWeek = "��";
		} else if ("4".equals(scheduleWeek)) {
			scheduleWeek = "��";
		} else if ("5".equals(scheduleWeek)) {
			scheduleWeek = "��";
		} else if ("6".equals(scheduleWeek)) {
			scheduleWeek = "��";
		} else if ("7".equals(scheduleWeek)) {
			scheduleWeek = "��";
		}

		hiddenTime = TimeNotFull(scheduleYear + "-" + scheduleMonth + "-"
				+ scheduleDay);

		//creatListView(hiddenTime);
		img_addImage.setOnClickListener(new AddSchedule());
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	class AddSchedule implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("time", hiddenTime);
			//intent.putExtra("User", user);
			intent.setClass(ScheduleItemTime.this, ScheduleAddTime.class);
			startActivity(intent);
		}

	}

	// �б�
	public void creatListView(String hiddenTime) {
		list = new ArrayList<Map<String, Object>>();

		new getScheduleByTime(this).execute(hiddenTime);

	}

	public void checkifread() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = list.get(i);
						String id = (String) map.get("eventId");

						String result = new Query().getPushMsgStatus(userId,PushMsg.MODULERID_MEETING,
								Integer.parseInt(id));
						try {
							JSONTokener jsonParser = new JSONTokener(result);
							JSONObject data = (JSONObject) jsonParser
									.nextValue();

							// �������ľ���JSON����Ĳ�����
							JSONArray jsonArray = data.getJSONArray("list");
							if (jsonArray!= null && jsonArray.length() > 0){
								JSONObject jsonObject = jsonArray.getJSONObject(0);
								
								if (null != jsonObject){
									int status = jsonObject.getInt("msgstatus");
									if(status == PushMsg.MSGSTATUS_NOTPUSH_NOTREAD 
											|| status == PushMsg.MSGSTATUS_PUSHED_NOTREAD)
										map.put("ifread", "*");
									else
										map.put("ifread", "");
								}
							}
						} catch (Exception e) {

						}
					}
				}
				return null;
			}

			protected void onPostExecute(String result) {
				if (adapter != null)
					adapter.notifyDataSetChanged();
			};

		}.execute();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		creatListView(hiddenTime);
		checkifread();
	}

	class getScheduleByTime extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog = null;

		public getScheduleByTime(Context context) {
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// ȡ���첽
			return query.queryScheduleByTimeInfo(params[0], userId);
		}

		protected void onPostExecute(String result) {
			try {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = (JSONObject) jsonParser.nextValue();

				// �������ľ���JSON����Ĳ�����
				JSONArray jsonArray = data.getJSONArray("list");
				for (int i = 0; i < jsonArray.length(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					JSONArray jsonArray2 = (JSONArray) jsonArray
							.getJSONArray(i);

					// �¼���
					JSONObject jsonObject3 = (JSONObject) jsonArray2.opt(0);
					map.put("eventId", jsonObject3.getString("eventId"));
					map.put("name", jsonObject3.getString("eventName"));
					map.put("content", jsonObject3.getString("eventContent"));
					String eventTimeString = jsonObject3.getString("eventTime");
					if (eventTimeString.length() != 0)
						eventTimeString = eventTimeString.split(":")[0] + ":"
								+ eventTimeString.split(":")[1];
					map.put("time", eventTimeString);
					map.put("timename",
							getResources().getString(
									R.string.schedule_meeting_time));

					// Ĭ��Ϊδ��
					map.put("ifread", "");

					list.add(map);

				}

				ScheduleItemTime.this.checkifread();
			} catch (Exception e) {
				Toast.makeText(getApplication(), "�����쳣�������³���",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

			adapter = new ExtSimpleAdapter(ScheduleItemTime.this, list,
					R.layout.schedule_main_itemtime, new String[] { "ifread",
							"name", "content", "time", "timename" }, new int[] {
							R.id.schedule_item_ifread, R.id.schedule_item_name,
							R.id.schedule_item_content,
							R.id.schedule_item_time,
							R.id.schedule_item_timename });
			listvist.setAdapter(adapter);
			listvist.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					Intent intent = new Intent(ScheduleItemTime.this,
							ScheduleSeeTime.class);
					intent.putExtra("week", week);
					intent.putExtra("eventId", list.get(arg2).get("eventId")
							+ "");
					//intent.putExtra("User", user);
					startActivity(intent);


				}
			});

			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}

	}
	
	class ExtSimpleAdapter extends SimpleAdapter{

		public ExtSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = super.getView(position, convertView, parent);
			TextView tv = (TextView) v.findViewById(R.id.schedule_item_name);
			TextView tvifnew = (TextView)v.findViewById(R.id.schedule_item_ifread);
			
			if (tvifnew.getText().toString().trim().equals("*")) {
					tv.setTextColor(Color.parseColor("#333333"));
				}
				else{
					tv.setTextColor(Color.parseColor("#999999"));
				}
			tvifnew.setText("");
			return v;
		}
		
	}

	// 2012-01-01 xxxxת��2012-1-1
	public String TimeFull(String stime) {
		String res;
		String[] seventTimeList = stime.split(" ");
		String[] sTime = seventTimeList[0].split("-");
		res = sTime[0] + "-" + Integer.parseInt(sTime[1]) + "-"
				+ Integer.parseInt(sTime[2]);
		return res;
	}

	// 2012-1-1xת��2012-01-01
	public String TimeNotFull(String stime) {

		String[] seventTimeList = stime.split("-");
		String res = seventTimeList[0];
		for (int j = 1; j < seventTimeList.length; j++) {
			if (Integer.parseInt(seventTimeList[j]) < 10)
				seventTimeList[j] = "0" + seventTimeList[j];
			res = res + "-" + seventTimeList[j];
		}

		return res;
	}

}