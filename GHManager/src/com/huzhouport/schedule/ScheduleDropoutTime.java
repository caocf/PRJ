package com.huzhouport.schedule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.hxkg.ghmanager.R;
import net.hxkg.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huzhouport.leaveandovertime.HttpFileUpTool;
import com.huzhouport.leaveandovertime.HttpUtil;
import com.huzhouport.leaveandovertime.Query;
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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_dropout);

		img_back = (ImageButton) findViewById(R.id.schedule_dropout_back);
		img_finish = (ImageButton) findViewById(R.id.schedule_dropout_finish);
		et_content = (EditText) findViewById(R.id.schedule_dropout_content);

		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		userId = String.valueOf(User.id);
		week = intent.getStringExtra("week");// ����x

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
		//intent.putExtra("User", user);
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
			
//			UploadActivity.tool.addFile("�ճ̻ظ�",actionUrl, paramsDate,null, null);
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
						Toast.makeText(ScheduleDropout.this, "�ύ�ɹ�",
								Toast.LENGTH_SHORT).show();
						closeActivity();
					}

					else
						Toast.makeText(ScheduleDropout.this, "�ύʧ��",
								Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Toast.makeText(ScheduleDropout.this, "��������",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			} else
				Toast.makeText(ScheduleDropout.this, "�ύʧ��", Toast.LENGTH_SHORT)
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
				// �¼��û�������
				JSONObject jsonObject6 = (JSONObject) jsonArray2.opt(1);
				if (jsonObject6.getString("userId").equals(userId)) {// ��ǰ�û���Ϣ
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
				Toast.makeText(ScheduleDropoutTime.this, "���ݻ�ȡʧ��",
						Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}

	}
}
