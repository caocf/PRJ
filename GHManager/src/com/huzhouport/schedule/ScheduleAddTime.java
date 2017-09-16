package com.huzhouport.schedule;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import net.hxkg.ghmanager.R;
import net.hxkg.user.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;


import com.huzhouport.leaveandovertime.HttpUtil;
import com.huzhouport.leaveandovertime.Query;
import com.huzhouport.leaveandovertime.WaitingDialog;
import com.huzhouport.upload.UploadActivity;

public class ScheduleAddTime extends Activity {

	private EditText et_name, et_content, et_location;
	private TextView tv_user, tv_day;
	private Spinner spinner1, spinner2;
	private GridView gv;
	private int toastNum = -1;
	private int toastNumber = -1;
	private Query query = new Query();
	private String[] kindNameList;
	private String[] kindIdList;;
	private ImageButton back, imguser, imgbt_finish, imgbt_addFile;
	private String userlist = "";// ��Ҫ������û�ID�б�
	// private int[] timeList = new int[5];
	Map<String, File> files = new HashMap<String, File>();
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private String userId, name;
	
	ArrayList<HashMap<String, Object>> usermapHashMaps = new ArrayList<HashMap<String, Object>>();
	ArrayList<HashMap<String, Boolean>> dmapHashMaps = new ArrayList<HashMap<String, Boolean>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_add_time);

		et_name = (EditText) findViewById(R.id.schedule_add_time_name);
		et_content = (EditText) findViewById(R.id.schedule_add_time_content);
		et_location = (EditText) findViewById(R.id.schedule_add_time_location);
		tv_user = (TextView) findViewById(R.id.schedule_add_time_user);
		tv_day = (TextView) findViewById(R.id.schedule_add_time_day);
		// spinner_kind = (Spinner) findViewById(R.id.schedule_add_kind);
		spinner1 = (Spinner) findViewById(R.id.schedule_add_time_spinner1);
		spinner2 = (Spinner) findViewById(R.id.schedule_add_time_spinner2);
		imguser = (ImageButton) findViewById(R.id.schedule_add_time_userImage);
		back = (ImageButton) findViewById(R.id.schedule_add_time_back);
		imgbt_finish = (ImageButton) findViewById(R.id.schedule_add_time_finish);
		imgbt_addFile = (ImageButton) findViewById(R.id.schedule_add_timeFile);
		gv = (GridView) findViewById(R.id.schedule_add_time_gridView);

		createRemind();
		Intent intent = getIntent();
		userId = String.valueOf(User.id);
		userlist = userId;
		name = User.name;
		tv_user.setText(name);

		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.getDefault());
		String date = sDate.format(new java.util.Date());
		tv_day.setText(date);

		// �ճ�����
		getScheduleKind();

		imguser.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				/*Intent intent = new Intent(ScheduleAddTime.this,
						ScheduleSelectUser.class);
				//intent.putExtra("User", user);
				intent.putExtra("userMap", usermapHashMaps);
				intent.putExtra("dMap", dmapHashMaps);
				startActivityForResult(intent, 80);*/
			}
		});
		tv_day.setOnClickListener(new ShowDate());
		back.setOnClickListener(new MyBack());
		imgbt_finish.setOnClickListener(new finishAdd());
		imgbt_addFile.setOnClickListener(new addFile());

	}

	private void createRemind() {
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.schedule_spinner01,
				android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.schedule_spinner02,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);
		spinner2.setAdapter(adapter2);
		spinner1.setSelection(3);
		spinner1.setOnItemSelectedListener(new spinner1Listener());
		spinner2.setOnItemSelectedListener(new spinner2Listener());

	}

	class addFile implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(ScheduleAddTime.this, GetSDTreeActivity.class);
			startActivityForResult(intent, 1);

		}

	}

	class finishAdd implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (et_name.getText().length() == 0) {
				Toast.makeText(ScheduleAddTime.this, "�������ճ�����",
						Toast.LENGTH_SHORT).show();
			} else {
				// �ύ��Ϣ
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

				paramsDate.put("scheduleEvent.eventName", et_name.getText());
				paramsDate.put("scheduleEvent.eventKind", 1);
				paramsDate.put("scheduleEvent.eventTime", tv_day.getText());
				paramsDate.put("scheduleEvent.eventLocation",
						et_location.getText());
				paramsDate.put("scheduleEvent.eventContent",
						et_content.getText());
				paramsDate.put("scheduleEventUser.userId", userId);
				paramsDate.put("scheduleEventUser.eventRemind", valueOfRemind);
				paramsDate.put("scheduleEventUser.eventRemindType", toastNum);
				paramsDate.put("scheduleEventUser.eventUserList", userlist);
				Post oPost = new Post(ScheduleAddTime.this);
				oPost.execute();
			}
		}

	}

	class Post extends AsyncTask<Void, Void, String> {

		ProgressDialog pDialog = null;


		public Post(Context context) {
			// /pDialog = ProgressDialog.show(context, "�����ճ�", "�ύ��,���Ժ򡤡���",
			// true);
			pDialog = new WaitingDialog().createDefaultProgressDialog(ScheduleAddTime.this, this,"�ύ��,���Ժ򡤡���");
			pDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			if (isCancelled())
				return null;// ȡ���첽

			String actionUrl = HttpUtil.BASE_URL + "addEvent_mobile.action";
			UploadActivity.tool.addFile("���" + et_name.getText() + "���ճ̰���",
					actionUrl, paramsDate, files, "scheduleAttachment.af");
			/*
			 * HttpFileUpTool hfu = new HttpFileUpTool(); String actionUrl =
			 * HttpUtil.BASE_URL + "addEvent_mobile.action"; try { return
			 * hfu.post(actionUrl, paramsDate, files, "scheduleAttachment.af");
			 * } catch (IOException e) { Toast.makeText(ScheduleAddTime.this,
			 * "��������", Toast.LENGTH_SHORT) .show(); e.printStackTrace(); }
			 * return "";
			 */
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			/*
			 * try { JSONTokener jsonParser = new JSONTokener(result);
			 * JSONObject data = (JSONObject) jsonParser.nextValue(); String
			 * jsonObject0 = data.getString("ieventId"); if
			 * (jsonObject0.equals("1")) { Toast.makeText(ScheduleAddTime.this,
			 * "����ɹ�", Toast.LENGTH_SHORT) .show(); Intent intent = new
			 * Intent(ScheduleAddTime.this, CalendarActivity.class);
			 * intent.putExtra("User", user); startActivity(intent); finish(); }
			 * else Toast.makeText(ScheduleAddTime.this, "����ʧ��",
			 * Toast.LENGTH_SHORT) .show(); } catch (JSONException e) {
			 * Toast.makeText(ScheduleAddTime.this, "��������", Toast.LENGTH_SHORT)
			 * .show(); e.printStackTrace(); }
			 */
			//Intent intent = new Intent(ScheduleAddTime.this,
			//		ScheduleItemTime.class);
			//intent.putExtra("User", user);
			//startActivity(intent);
			finish();

			
			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}

	}

	class MyBack implements OnClickListener {

		@Override
		public void onClick(View v) {
			finish();
		}

	}

	class ShowDate implements OnClickListener {

		@Override
		public void onClick(View v) {

			SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(
					ScheduleAddTime.this, v, tv_day);
			menuWindow.showAtLocation(v, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��

			/*
			 * 
			 * new DatePickerDialog(ScheduleAddTime.this, new
			 * OnDateSetListener() {
			 * 
			 * @Override public void onDateSet(DatePicker view, int year, int
			 * monthOfYear, int dayOfMonth) {
			 * 
			 * new TimePickerDialog(ScheduleAddTime.this, new
			 * OnTimeSetListener() {
			 * 
			 * @Override public void onTimeSet(TimePicker view, int hourOfDay,
			 * int minute) { timeList[3] = hourOfDay; timeList[4] = minute;
			 * String timeString=timeList[0] + "-" + timeList[1] + "-" +
			 * timeList[2]+" "+timeList[3] + ":" + timeList[4];
			 * tv_day.setText(CountTime.FormatTime2(timeString)); } },
			 * timeList[3], timeList[4], true).show();
			 * 
			 * 
			 * if (year < 1901 || year > 2049) { // ���ڲ�ѯ��Χ�� new
			 * AlertDialog.Builder(ScheduleAddTime.this) .setTitle("��������")
			 * .setMessage("��ת���ڷ�Χ(1901/1/1-2049/12/31)")
			 * .setPositiveButton("ȷ��", null).show(); } else { timeList[0] =
			 * year; timeList[1] = monthOfYear + 1; timeList[2] = dayOfMonth;
			 * 
			 * } } }, timeList[0], timeList[1] - 1, timeList[2]).show();
			 */}

	}

	public void getScheduleKind() {
		new getScheduleKindList().execute();

	}

	class getScheduleKindList extends AsyncTask<Void, Void, String> {

		/*
		 * ProgressDialog pDialog = null;
		 * 
		 * public getScheduleKindList() {
		 * 
		 * }
		 * 
		 * public getScheduleKindList(Context context) { pDialog =
		 * ProgressDialog .show(context, "���ݼ���", "���ݼ����У����Ժ򡤡���", true); }
		 */
		@Override
		protected String doInBackground(Void... params) {

			return query.EventKindList();
		}

		@Override
		protected void onPostExecute(String result) {

			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;
			try {
				data = (JSONObject) jsonParser.nextValue();
				JSONArray jsonArray = data.getJSONArray("list");
				kindNameList = new String[jsonArray.length()];
				kindIdList = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					kindNameList[i] = jsonObject.getString("scheduleKindName");
					kindIdList[i] = jsonObject.getString("scheduleKindId");
				}

			} catch (Exception e) {
				Toast.makeText(ScheduleAddTime.this, "���ݻ�ȡʧ��",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getBaseContext(), android.R.layout.simple_spinner_item,
					kindNameList);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// spinner_kind.setAdapter(adapter);
			/*
			 * if (pDialog != null) pDialog.dismiss();
			 */

			super.onPostExecute(result);
		}

	}

	class spinner2Listener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (toastNum != -1)
				Toast.makeText(ScheduleAddTime.this,
						"ѡ������ѷ�ʽ��" + arg0.getItemAtPosition(arg2).toString(),
						Toast.LENGTH_SHORT).show();
			toastNum = arg2 + 1;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	}

	class spinner1Listener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (toastNumber != -1)
				Toast.makeText(ScheduleAddTime.this,
						"��ǰ" + arg0.getItemAtPosition(arg2).toString(),
						Toast.LENGTH_SHORT).show();
			toastNumber = arg2;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (80 == resultCode) {
			usermapHashMaps = (ArrayList<HashMap<String, Object>>) data
					.getSerializableExtra("userMap");
			dmapHashMaps = (ArrayList<HashMap<String, Boolean>>) data
					.getSerializableExtra("dMap");
			if (usermapHashMaps.size() != 0) {
				String stext = name;
				userlist = userId;
				for (int i = 0; i < usermapHashMaps.size(); i++) {
					if ((Boolean) usermapHashMaps.get(i).get("oboolen")) {

						userlist += ","
								+ (String) usermapHashMaps.get(i).get("uId");
						stext += ","
								+ (String) usermapHashMaps.get(i).get("uName");
					}
				}
				tv_user.setText(stext);
			}

		} else {
			if (requestCode == 1 && resultCode == GetSDTreeActivity.RESULT_CODE)
			{
				if (data != null)
				{
					String path=data.getStringExtra(GetSDTreeActivity.RESULT_PATH);
					String name=data.getStringExtra(GetSDTreeActivity.RESULT_NAME);
					if(path!=null && !path.equals("")&&name!=null && !name.equals(""))
					{
						files.put(name, new File(path));
					}
						
				}
			}
			creatGV();
		}
	}

	@SuppressWarnings("rawtypes")
	public void creatGV() {
		ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		int[] at_image = new int[files.size()];
		String[] at_name = new String[files.size()];
		Iterator keyIteratorOfMap = files.keySet().iterator();
		for (int i = 0; i < files.size(); i++) {
			Object key = keyIteratorOfMap.next();
			at_name[i] = key.toString();
			String str = key.toString();
			int dot = str.lastIndexOf('.');
			String substring = str.substring(dot + 1);
			if (substring.equals("doc"))
				at_image[i] = R.drawable.doc;
			else if (substring.equals("xml"))
				at_image[i] = R.drawable.xls;
			else if (substring.equals("ppt"))
				at_image[i] = R.drawable.ppt;
			else if (substring.equals("pdf"))
				at_image[i] = R.drawable.pdf;
			else if (substring.equals("mp4"))
				at_image[i] = R.drawable.video;
			else if (substring.equals("3gp"))
				at_image[i] = R.drawable.video;
			else if (substring.equals("amr"))
				at_image[i] = R.drawable.audio;
			else if (substring.equals("3ga"))
				at_image[i] = R.drawable.audio;
			else if (substring.equals("wav"))
				at_image[i] = R.drawable.audio;
			else if (substring.equals("mp3"))
				at_image[i] = R.drawable.audio;
			else
				at_image[i] = R.drawable.other_file;

		}
		for (int i = 0; i < files.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", at_image[i]);
			map.put("itemText", at_name[i]);

			lst.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, lst, R.layout.menulist,
				new String[] { "itemImage", "itemText" }, new int[] {
						R.id.imageView_ItemImage, R.id.textView_ItemText });
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(new gridViewOnClickListener());
	}

	class gridViewOnClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			TextView tv = (TextView) arg1.findViewById(R.id.textView_ItemText);
			dialog_down((String) tv.getText());
		}

	}

	public void dialog_down(String kt) {
		final String kt2 = kt;
		new AlertDialog.Builder(this).setTitle("�Ƴ��ļ�")
				.setMessage("�Ƴ�" + kt2 + "�ļ���")
				.setPositiveButton("��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						files.remove(kt2);
						creatGV();
					}
				}).setNegativeButton("��", null).show();

	}


}
