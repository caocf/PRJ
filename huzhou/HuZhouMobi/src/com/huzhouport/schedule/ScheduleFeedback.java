package com.huzhouport.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
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
				((TextView)findViewById(R.id.schedule_feedback_title)).setText("通知反馈");
		}
		new GetDate().execute();
		
		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}
	
	class Adapter extends SimpleAdapter
	{
		List<? extends Map<String, ?>> data;
		public Adapter(Context context, List<? extends Map<String, ?>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			this.data=data;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View rootView=super.getView(position, convertView, parent);
			TextView textView=(TextView) rootView.findViewById(R.id.schedule_item_see_agree);
			String string=textView.getText().toString();
			Map<String, ?> map=data.get(position);
			Spanned userAgreeStruts=  (Spanned) map.get("agree");
			textView.setText(userAgreeStruts);
			/*switch (Integer.valueOf(string)) 
			{
			case 1:
				
				break;
			case 2:
				//
				break;
			case 3:
	
				break;

			default:
				break;
			}*/
			//textView.setText(string);
			
			return rootView;
			
		}
		
	}

	private void intDate() 
	{
		String result = query.FindFeedbackByEventId(eventId);
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try 
		{
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			list_User = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < jsonArray.length(); i++) 
			{
				Map<String, Object> map = new HashMap<String, Object>();
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				int userAgreeStruts = Integer.parseInt(jsonArray2.get(1).toString());
				/*int msgstatus = Integer.parseInt(jsonArray2.get(3).toString());
				String msgstatusString="\n\n未读"; 
				if(msgstatus==2||msgstatus==4){
					msgstatusString="\n\n已读";
				}/*else{
					userAgreeStruts=7;
				}*/
				switch (userAgreeStruts) {
				case 1:
					map.put("name", jsonArray2.get(0));
					//+NotString(jsonArray2.get(2).toString());
					String s1= "<font color='#48c253'>参与<font><br><font>";
					map.put("agree", Html.fromHtml(s1));
					//map.put("agree", String.valueOf(userAgreeStruts));
					list_User.add(map);
					break;
				case 2:
					map.put("name", jsonArray2.get(0));
					String s2= "<font color='red'>不参与<font><br><br><font><font color=red>"+NotString(jsonArray2.get(2).toString())+"<font>";
					map.put("agree", Html.fromHtml(s2));
					list_User.add(map);
					break;
				case 3:
					map.put("name", jsonArray2.get(0).toString());
					String s3= "<font>无回馈<font><br><br><font><font>"+""+"<font>";
					map.put("agree", Html.fromHtml(s3));
					list_User.add(map);
					break;
				case 4:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", "参与，并转发"+NotString(jsonArray2.get(2).toString()));
					list_User.add(map);
					break;
				case 5:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", "不参与，并转发"+NotString(jsonArray2.get(2).toString()));
					list_User.add(map);
					break;
				case 6:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", "转发");
					list_User.add(map);
					break;
				case 7:
					map.put("name", jsonArray2.get(0).toString());
					map.put("agree", Html.fromHtml("未读"));
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
			Adapter adapter = new Adapter(ScheduleFeedback.this,
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
			return "\n\n"+result;
	}
}
