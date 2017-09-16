package com.huzhouport.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;
import com.huzhouport.pushmsg.PushMsg;
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
 * 会议安排列表
 * 创建人：唐文杰
 * 修改人：沈丹丹
 */

public class ScheduleItemTime extends Activity 
{
	private SimpleAdapter adapter = null;
	private ImageButton img_addImage, img_back;
	private String week = "";
	private int userId;// 用户Id

	List<Map<String, Object>> list;
	private String hiddenTime = "";
	private ListView listvist;
	private Query query = new Query();
	AlarmManager am;// 管理器
	private User user;
	TextView tipTextView;
	SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_main_listview_huiyi);

		img_back = (ImageButton) findViewById(R.id.schedule_main_listview_imgback);
		listvist = (ListView) findViewById(R.id.schedule_listview);
		img_addImage = (ImageButton) findViewById(R.id.schedule_main_listview__add);

		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		userId = user.getUserId();

		Calendar c = Calendar.getInstance();
		String scheduleDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String scheduleYear = String.valueOf(c.get(Calendar.YEAR));
		String scheduleMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
		
		hiddenTime = TimeNotFull(scheduleYear + "-" + scheduleMonth + "-"
				+ scheduleDay);

		img_addImage.setOnClickListener(new AddSchedule());
		img_back.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				finish();
			}
		});
		
		tipTextView=(TextView) findViewById(R.id.tip);	

	}

	class AddSchedule implements OnClickListener 
	{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("time", hiddenTime);
			intent.putExtra("User", user);
			intent.setClass(ScheduleItemTime.this, ScheduleAddTime.class);
			startActivity(intent);
		}

	}

	// 列表
	public void creatListView(String hiddenTime) {
		list = new ArrayList<Map<String, Object>>();

		new getScheduleByTime(this).execute(hiddenTime);

	}

	public void checkifread()
	{
		new AsyncTask<Void, Void, String>() 
		{
			@Override
			protected String doInBackground(Void... params) 
			{
				if (list != null) 
				{
					for (int i = 0; i < list.size(); i++) 
					{
						Map<String, Object> map = list.get(i);
						String id = (String) map.get("eventId");

						String result = new Query().getPushMsgStatus(userId,PushMsg.MODULERID_MEETING,
								Integer.parseInt(id));
						try {
							JSONTokener jsonParser = new JSONTokener(result);
							JSONObject data = (JSONObject) jsonParser
									.nextValue();

							// 接下来的就是JSON对象的操作了
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
	protected void onResume() 
	{
		creatListView(hiddenTime);
		//checkifread();
		super.onResume();		
	}

	class getScheduleByTime extends AsyncTask<String, Void, String> 
	{
		ProgressDialog pDialog = null;

		public getScheduleByTime(Context context) 
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context, this);
			pDialog.show();
		}

		protected String doInBackground(String... params) 
		{
			if (isCancelled())
				return null;// 取消异步
			return query.queryScheduleByTimeInfo(params[0], userId);
		}

		protected void onPostExecute(String result) 
		{
			try {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = (JSONObject) jsonParser.nextValue();

				// 接下来的就是JSON对象的操作了
				JSONArray jsonArray = data.getJSONArray("list");
				list.clear();
				for (int i = 0; i < jsonArray.length(); i++) 
				{
					Map<String, Object> map = new HashMap<String, Object>();
					JSONArray jsonArray2 = (JSONArray) jsonArray
							.getJSONArray(i);

					// 事件表
					JSONObject jsonObject3 = (JSONObject) jsonArray2.opt(0);
					JSONObject jsonObjectxxJsonObject=(JSONObject) jsonArray2.opt(1);
					map.put("eventId", jsonObject3.getString("eventId"));
					map.put("name", jsonObject3.getString("eventName"));
					map.put("content", jsonObject3.getString("eventContent"));
					String eventTimeString = jsonObject3.getString("eventTime");
					String timeString=eventTimeString.substring(0,eventTimeString.indexOf("."));
					if (eventTimeString.length() != 0)
						eventTimeString = eventTimeString.split(":")[0] + ":"
								+ eventTimeString.split(":")[1];
					map.put("time", eventTimeString);
					map.put("timename",
							getResources().getString(
									R.string.schedule_meeting_time));

					// 默认为未读
					map.put("ifread", "");
					int agree=jsonObjectxxJsonObject.getInt("userAgree");
					switch (agree) 
					{
					case 0:
						map.put("respond", "发起");
						
						break;
					case 1:
						map.put("respond", "已回复参与");
						break;
					case 2:	
						map.put("respond", "已回复不参与");
						break;
					case 3:
						map.put("respond", "未回复");
						map.put("ifread", "*");						
						
						if(sd.parse(timeString).getTime()<new Date().getTime())
						{
							map.put("respond", "未回复(已过期)");
							map.put("ifread", "");
						}
						break;
					default:
						break;
					}
					
					list.add(map);

				}

				ScheduleItemTime.this.checkifread();
			} catch (Exception e)
			{
				Toast.makeText(getApplication(), "发生异常！请重新尝试",Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			if(list.size()<=0)
			{
				tipTextView.setVisibility(View.VISIBLE);
				listvist.setVisibility(View.GONE);
			}else 
			{
				tipTextView.setVisibility(View.GONE);
				listvist.setVisibility(View.VISIBLE);
				adapter = new ExtSimpleAdapter(ScheduleItemTime.this, list,
						R.layout.schedule_main_itemtime, new String[] { "ifread",
								"name", "content", "time", "timename","respond" }, new int[] 
										{
								R.id.schedule_item_ifread, R.id.schedule_item_name,
								R.id.schedule_item_content,
								R.id.schedule_item_time,
								R.id.schedule_item_timename ,
								R.id.respond});
				listvist.setAdapter(adapter);
				listvist.setOnItemClickListener(new OnItemClickListener() 
				{
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						Intent intent = new Intent(ScheduleItemTime.this,
								ScheduleSeeTime.class);
						intent.putExtra("week", week);
						intent.putExtra("eventId", list.get(arg2).get("eventId")
								+ "");
						intent.putExtra("User", user);
						startActivity(intent);
						/*if (user != null) 
						{
							Log log = new Log(user.getName(), "查看日程安排", GlobalVar.LOGSEE, ""); // 日志
							log.execute();
						}*/

					}
				});
			}			

			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}

	}
	
	class ExtSimpleAdapter extends SimpleAdapter
	{
		public ExtSimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = super.getView(position, convertView, parent);
			TextView tv = (TextView) v.findViewById(R.id.schedule_item_name);
			TextView tvifnew = (TextView)v.findViewById(R.id.schedule_item_ifread);
			
			if (tvifnew.getText().toString().trim().equals("*")) {
					tv.setTextColor(Color.parseColor("#333333"));
					tvifnew.setVisibility(View.VISIBLE);
				}
				else{
					tv.setTextColor(Color.parseColor("#999999"));
					tvifnew.setVisibility(View.INVISIBLE);
				}
			//tvifnew.setText("");
			return v;
		}
		
	}

	// 2012-01-01 xxxx转成2012-1-1
	public String TimeFull(String stime) {
		String res;
		String[] seventTimeList = stime.split(" ");
		String[] sTime = seventTimeList[0].split("-");
		res = sTime[0] + "-" + Integer.parseInt(sTime[1]) + "-"
				+ Integer.parseInt(sTime[2]);
		return res;
	}

	// 2012-1-1x转成2012-01-01
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