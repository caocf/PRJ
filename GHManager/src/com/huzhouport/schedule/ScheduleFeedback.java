package com.huzhouport.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hxkg.ghmanager.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huzhouport.leaveandovertime.Query;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ScheduleFeedback extends Activity {

	private ImageButton img_back;
	private ListView lv;
	private String eventId;
	private Query query = new Query();
	List<Map<String, Object>> list_User;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_feedback);

		img_back = (ImageButton) findViewById(R.id.schedule_feedback_back);
		lv = (ListView) findViewById(R.id.schedule_feedback_listview);

		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		if(intent.getStringExtra("isMeeting")!=null){
			if(intent.getStringExtra("isMeeting").equals("true"))
				((TextView)findViewById(R.id.schedule_feedback_title)).setText("���鷴��");
		}
		new GetDate().execute();
		
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

	private void intDate() {
		String result = query.FindFeedbackByEventId(eventId);
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			list_User = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				int userAgreeStruts = Integer.parseInt(jsonArray2.get(1).toString());
				int msgstatus = Integer.parseInt(jsonArray2.get(3).toString());
				String msgstatusString="δ��"; 
				if(msgstatus==2||msgstatus==4){
					msgstatusString=",���Ѷ�";
				}else{
					userAgreeStruts=7;
				}
				switch (userAgreeStruts) {
				case 1:
					map.put("name", jsonArray2.get(0));
					map.put("agree", "����"+NotString(jsonArray2.get(2).toString()));
					list_User.add(map);
					break;
				case 2:
					map.put("name", jsonArray2.get(0));
					map.put("agree", "������"+NotString(jsonArray2.get(2).toString()));
					list_User.add(map);
					break;
				case 3:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", "�޻���"+msgstatusString);
					list_User.add(map);
					break;
				case 4:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", "���룬��ת��"+NotString(jsonArray2.get(2).toString()));
					list_User.add(map);
					break;
				case 5:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", "�����룬��ת��"+NotString(jsonArray2.get(2).toString()));
					list_User.add(map);
					break;
				case 6:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", "ת��");
					list_User.add(map);
					break;
				case 7:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", "δ��");
					list_User.add(map);
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	class GetDate extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			intDate();
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			SimpleAdapter adapter = new SimpleAdapter(ScheduleFeedback.this,
					list_User, R.layout.schedule_see_item, new String[] { "name",
							"agree" }, new int[] {
							R.id.schedule_item_see_name,
							R.id.schedule_item_see_agree });
			lv.setAdapter(adapter);
			super.onPostExecute(result);
		}

	}

	private String NotString(String result) {
		if (result.equals("null"))
			return "";
		else
			return "\n"+result;
	}
}
