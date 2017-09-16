package com.huzhouport.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.hxkg.ghmanager.R;
import net.hxkg.user.User;
import org.json.JSONArray;
import org.json.JSONObject;
import com.huzhouport.leaveandovertime.Query;
import com.huzhouport.leaveandovertime.WaitingDialog;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ScheduleItem extends Activity
{

	private int year_c = 0;

	private TextView tv_time;
	private ImageButton img_addImage, img_back;
	private String week = "";

	List<Map<String, Object>> list;
	private String hiddenTime = "";
	private ListView listvist;
	private Query query = new Query();
	AlarmManager am;
	

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_main_listview);

		img_back = (ImageButton) findViewById(R.id.schedule_main_listview_imgback);
		listvist = (ListView) findViewById(R.id.schedule_listview);
		tv_time = (TextView) findViewById(R.id.schedule_time);
		img_addImage = (ImageButton) findViewById(R.id.schedule_addImage);

		Intent intent = getIntent();
		week = intent.getStringExtra("week");
		tv_time.setText(intent.getStringExtra("tv_time"));
		String scheduleDay = intent.getStringExtra("tv_day");
		String scheduleYear = intent.getStringExtra("tv_year");
		String scheduleMonth = intent.getStringExtra("tv_mouth");
		hiddenTime = TimeNotFull(scheduleYear + "-" + scheduleMonth + "-"
				+ scheduleDay);

		creatListView(hiddenTime);
		img_addImage.setOnClickListener(new AddSchedule());
		img_back.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ScheduleItem.this,
						CalendarActivity.class);
				//intent.putExtra("User", user);
				startActivity(intent);
				finish();
			}
		});

	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Intent intent = new Intent(ScheduleItem.this,
					CalendarActivity.class);
			//intent.putExtra("User", user);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class AddSchedule implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.putExtra("time", hiddenTime);
			//intent.putExtra("User", user);
			intent.setClass(ScheduleItem.this, ScheduleAdd.class);
			startActivity(intent);
			finish();

		}

	}

	public void creatListView(String hiddenTime)
	{
		list = new ArrayList<Map<String, Object>>();

		new getScheduleByTime(this).execute(hiddenTime);

	}

	class getScheduleByTime extends AsyncTask<String, Void, String>
	{

		ProgressDialog pDialog = null;

		public getScheduleByTime(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(
					ScheduleItem.this, this);
			pDialog.show();
		}

		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;
			return query.queryScheduleByTime(params[0], User.uidString);
		}

		protected void onPostExecute(String result)
		{
			try
			{

				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++)
				{
					Map<String, Object> map = new HashMap<String, Object>();
					

					JSONObject jsonObject3 = (JSONObject) jsonArray.opt(i);
					map.put("eventId", jsonObject3.getString("id"));
					map.put("name", jsonObject3.getString("name"));
					map.put("content", jsonObject3.getString("content"));
					String eventTimeString = jsonObject3.getString("scheduletime");
					if (eventTimeString.length() != 0)
						eventTimeString = eventTimeString.split(":")[0] + ":"
								+ eventTimeString.split(":")[1];
					map.put("time", eventTimeString);
					map.put("timename",
							getResources()
									.getString(R.string.schedule_all_time));

					list.add(map);

				}
			} catch (Exception e)
			{
				Toast.makeText(getApplication(), "数据加载异常",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			SimpleAdapter adapter = new SimpleAdapter(ScheduleItem.this, list,
					R.layout.schedule_main_itemtime, new String[] { "name",
							"content", "time", "timename" }, new int[] {
							R.id.schedule_item_name,
							R.id.schedule_item_content,
							R.id.schedule_item_time,
							R.id.schedule_item_timename });

			listvist.setAdapter(adapter);
			listvist.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3)
				{

					Intent intent = new Intent(ScheduleItem.this,
							ScheduleSee.class);
					intent.putExtra("week", week);
					intent.putExtra("eventId", list.get(arg2).get("eventId")
							+ "");
					//intent.putExtra("User", user);
					startActivity(intent);
					finish();


				}
			});

			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}

	}

	// 2012-01-01 ת��2012-1-1
	public String TimeFull(String stime)
	{
		String res;
		String[] seventTimeList = stime.split(" ");
		String[] sTime = seventTimeList[0].split("-");
		res = sTime[0] + "-" + Integer.parseInt(sTime[1]) + "-"
				+ Integer.parseInt(sTime[2]);
		return res;
	}

	public String TimeNotFull(String stime)
	{

		String[] seventTimeList = stime.split("-");
		String res = seventTimeList[0];
		for (int j = 1; j < seventTimeList.length; j++)
		{
			if (Integer.parseInt(seventTimeList[j]) < 10)
				seventTimeList[j] = "0" + seventTimeList[j];
			res = res + "-" + seventTimeList[j];
		}

		return res;
	}

	public String countDay(int ijyear, int ijmonth, int iyear, int imouth)
	{
		int stepYear = iyear + ijyear;
		int stepMonth = imouth + ijmonth;
		if (stepMonth > 0)
		{
			
			if (stepMonth % 12 == 0)
			{
				stepYear = year_c + stepMonth / 12 - 1;
				stepMonth = 12;
			} else
			{
				stepYear = year_c + stepMonth / 12;
				stepMonth = stepMonth % 12;
			}
		} else
		{
			stepYear = year_c - 1 + stepMonth / 12;
			stepMonth = stepMonth % 12 + 12;
		}
		return stepYear + "-" + stepMonth;
	}
}