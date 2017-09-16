package com.huzhouport.schedule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;
import com.huzhouport.upload.UploadActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

@SuppressWarnings("unused")
public class ScheduleDropoutTime extends Activity {

	private String eventId, week, userId;
	private ImageButton img_back, img_finish;
	private EditText et_content;
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private int userAgree, eventRemind, eventRemindType;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_dropout);

		img_back = (ImageButton) findViewById(R.id.schedule_dropout_back);
		img_finish = (ImageButton) findViewById(R.id.schedule_dropout_finish);
		et_content = (EditText) findViewById(R.id.schedule_dropout_content);

		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		user = (User) intent.getSerializableExtra("User");
		userId = String.valueOf(user.getUserId());
		week = intent.getStringExtra("week");// 星期x

		new GetDate().execute();

		img_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeActivity();
			}
		});
		img_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new UploadScheduleNo().execute();
			}
		});
	}
	private void closeActivity() {
		Intent intent = new Intent(ScheduleDropoutTime.this,
				ScheduleSeeTime.class);
		intent.putExtra("week", week);
		intent.putExtra("eventId", eventId);
		intent.putExtra("User", user);
		startActivity(intent);
		finish();
	}
	class UploadScheduleNo extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			paramsDate.put("scheduleEventUser.eventId", eventId);
			paramsDate.put("scheduleEventUser.userId", userId);
			paramsDate.put("scheduleEventUser.userAgree", 2);
			paramsDate.put("scheduleEventUser.agreeReason", et_content
					.getText().toString());
			System.out.println("bb"+et_content.getText().toString());
			paramsDate.put("scheduleEventUser.eventRemind", eventRemind);
			paramsDate
					.put("scheduleEventUser.eventRemindType", eventRemindType);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "AddAgreeReason";
			
//			UploadActivity.tool.addFile("日程回复",actionUrl, paramsDate,null, null);
			String result = null;
			try {
				result = hfu.post(actionUrl, paramsDate);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			/*if (result != null) {
				try {
					JSONTokener jsonParser = new JSONTokener(result);
					JSONObject data;

					data = (JSONObject) jsonParser.nextValue();
					String jsonObject0 = data.getString("ieventId");
					if (jsonObject0.equals("1")) {
						Toast.makeText(ScheduleDropout.this, "提交成功",
								Toast.LENGTH_SHORT).show();
						closeActivity();
					}

					else
						Toast.makeText(ScheduleDropout.this, "提交失败",
								Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Toast.makeText(ScheduleDropout.this, "发生错误",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			} else
				Toast.makeText(ScheduleDropout.this, "提交失败", Toast.LENGTH_SHORT)
						.show();*/
			closeActivity();
			super.onPostExecute(result);
		}

	}

	public void intDate(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				// 事件用户关联表
				JSONObject jsonObject6 = (JSONObject) jsonArray2.opt(1);
				if (jsonObject6.getString("userId").equals(userId)) {// 当前用户信息
					userAgree = jsonObject6.getInt("userAgree");
					eventRemind = jsonObject6.getInt("eventRemind");
					eventRemindType = jsonObject6.getInt("eventRemindType");
					if (!jsonObject6.getString("agreeReason").equals("null"))
						et_content.setText(jsonObject6.getString("agreeReason"));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class GetDate extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			Query query = new Query();
			String result = query.queryScheduleByEventId(eventId);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				intDate(result);
			} else
				Toast.makeText(ScheduleDropoutTime.this, "数据获取失败",
						Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

	}
}
