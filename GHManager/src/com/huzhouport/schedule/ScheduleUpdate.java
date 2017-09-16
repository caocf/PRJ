package com.huzhouport.schedule;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.hxkg.ghmanager.MySQLiteHelper;
import net.hxkg.ghmanager.R;
import net.hxkg.user.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.huzhouport.leaveandovertime.HttpUtil;
import com.huzhouport.leaveandovertime.Query;
import com.huzhouport.leaveandovertime.WaitingDialog;
import com.huzhouport.upload.UploadActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("rawtypes")
public class ScheduleUpdate extends Activity {

	private EditText et_name, et_content, et_location;
	private TextView tv_user, tv_day;
	private Spinner spinner_kind, spinner1, spinner2;
	private ImageButton imguser, imgbt_back, imgbt_finish, imgbt_addFile;
	private GridView gv;
	private int toastNum = -1, wzpost;
	private int toastNumber = -1;
	private Query query = new Query();
	private String[] kindNameList, listOfattachmentId;
	private String[] kindIdList;
	private int[] timeList = new int[5];
	private String eventId;
	private String userlist = "";// ��Ҫ������û�ID�б�
	private String userId, week,name;
	Map<String, File> files = new HashMap<String, File>();
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	Map<String, String> old_files = new HashMap<String, String>();
	private int[] at_image;
	private String[] at_name;
	
	ArrayList<HashMap<String, Object>> usermapHashMaps= new ArrayList<HashMap<String,Object>>();
	ArrayList<HashMap<String, Boolean>> dmapHashMaps= new ArrayList<HashMap<String,Boolean>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_update);

		et_name = (EditText) findViewById(R.id.schedule_update_name);
		et_content = (EditText) findViewById(R.id.schedule_update_content);
		et_location = (EditText) findViewById(R.id.schedule_update_location);
		tv_user = (TextView) findViewById(R.id.schedule_update_user);
		tv_day = (TextView) findViewById(R.id.schedule_update_day);
		spinner_kind = (Spinner) findViewById(R.id.schedule_update_kind);
		spinner1 = (Spinner) findViewById(R.id.schedule_update_spinner1);
		spinner2 = (Spinner) findViewById(R.id.schedule_update_spinner2);
		imguser = (ImageButton) findViewById(R.id.schedule_update_userImage);
		imgbt_back = (ImageButton) findViewById(R.id.schedule_update_back);
		imgbt_finish = (ImageButton) findViewById(R.id.schedule_update_finish);
		imgbt_addFile = (ImageButton) findViewById(R.id.schedule_updateFile);
		gv = (GridView) findViewById(R.id.schedule_update_gridView);
		
		createRemind();

		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		userId = String.valueOf(User.id);
		name=User.name;
		userlist = userId;
		week = intent.getStringExtra("week");
		// �ճ�����
		new getScheduleKindAsyncTask().execute();
		// �ճ���Ϣ
		new GetDate().execute();
		// ����
		new getAttachmentAsyncTask().execute();

		imguser.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				/*linear01.setVisibility(View.GONE);
				linear02.setVisibility(View.VISIBLE);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0); // ǿ�����ؼ���
				layoutTool = 1;*/
				/*Intent intent = new Intent(ScheduleUpdate.this,
						ScheduleSelectUser.class);
				//intent.putExtra("User", user);
				intent.putExtra("userMap", usermapHashMaps);
				intent.putExtra("dMap", dmapHashMaps);
				startActivityForResult(intent, 80);*/
			}
		});
		tv_day.setOnClickListener(new ShowDate());
		imgbt_back.setOnClickListener(new gotoSee());
		imgbt_finish.setOnClickListener(new finishUpdate());
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
	}



	public void showListView() {}

	class getAttachmentAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			return query.FindAttachmentByEventId(eventId);
		}

		@Override
		protected void onPostExecute(String result) {
			getAttachment(result);
			super.onPostExecute(result);
		}

	}

	public void getAttachment(String result) {
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			// �������ľ���JSON����Ĳ�����
			JSONArray jsonArray = data.getJSONArray("list");
			listOfattachmentId = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
				listOfattachmentId[i] = jsonObject.getString("attachmentId");
				old_files.put(jsonObject.getString("attachmentName"),
						jsonObject.getString("attachmentPath"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		creatGV();
	}





	class addFile implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(ScheduleUpdate.this, GetSDTreeActivity.class);
			startActivityForResult(intent, 1);

		}

	}

	class gotoSee implements OnClickListener {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ScheduleUpdate.this, ScheduleSee.class);
			intent.putExtra("week", week);
			intent.putExtra("eventId", eventId);
			//intent.putExtra("User", user);
			startActivity(intent);
			finish();
		}

	}

	class UploadDate extends AsyncTask<Void, Void, String> {
		ProgressDialog pDialog = null;

		public UploadDate() {
		
			pDialog = new WaitingDialog().createDefaultProgressDialog(ScheduleUpdate.this, this);
			pDialog.show();	
		}
		@Override
		protected String doInBackground(Void... params) {
			if(isCancelled()) return null;//ȡ���첽
			//String result = null;
			if (et_name.getText().length() == 0) {
				Toast.makeText(ScheduleUpdate.this, "�������ճ�����",
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

				paramsDate.put("scheduleEvent.eventId", eventId);
				paramsDate.put("scheduleEvent.eventName", et_name.getText());
				paramsDate.put("scheduleEvent.eventKind",
						spinner_kind.getSelectedItemPosition() + 1);
				paramsDate.put("scheduleEvent.eventTime", tv_day.getText());
				paramsDate.put("scheduleEvent.eventLocation",
						et_location.getText());
				paramsDate.put("scheduleEvent.eventContent",
						et_content.getText());
				paramsDate.put("scheduleEventUser.userId", userId);
				paramsDate.put("scheduleEventUser.eventRemind", valueOfRemind);
				paramsDate.put("scheduleEventUser.eventRemindType", toastNum);
				paramsDate.put("scheduleEventUser.eventUserList", userlist);
				
				String actionUrl = HttpUtil.BASE_URL
						+ "updateByEventId_mobile.action";
				/*HttpFileUpTool hfu = new HttpFileUpTool();
				String actionUrl = HttpUtil.BASE_URL
						+ "updateByEventId_mobile.action";
				try {
					result = hfu.post(actionUrl, paramsDate, files,
							"scheduleAttachment.af");
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				UploadActivity.tool.addFile("�޸�"+et_name.getText()+"���ճ�",actionUrl, paramsDate,files, "scheduleAttachment.af");
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			/*if (result != null) {

				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data;
				try {
					data = (JSONObject) jsonParser.nextValue();
					String jsonObject0 = data.getString("ieventId");
					if (jsonObject0.equals("1")) {
						Toast.makeText(ScheduleUpdate.this, "�޸ĳɹ�",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(ScheduleUpdate.this,
								ScheduleSee.class);
						intent.putExtra("week", week);
						intent.putExtra("eventId", eventId);
						intent.putExtra("User", user);
						startActivity(intent);
						finish();
					}

					else
						Toast.makeText(ScheduleUpdate.this, "�޸�ʧ��",
								Toast.LENGTH_SHORT).show();
				} catch (JSONException e) {
					Toast.makeText(ScheduleUpdate.this, "��������",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			}*/
			Intent intent = new Intent(ScheduleUpdate.this,
					ScheduleSee.class);
			intent.putExtra("week", week);
			intent.putExtra("eventId", eventId);
			//intent.putExtra("User", user);
			startActivity(intent);
			finish();
			
			
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
					// �޸����ݿ�
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
	class finishUpdate implements OnClickListener {

		@Override
		public void onClick(View v) {

			if (et_name.getText().length() == 0) {
				Toast.makeText(ScheduleUpdate.this, "�������ճ�����",
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
					valueOfRemind = 40;
					break;
				case 7:
					valueOfRemind = 60;
					break;
				case 8:
					valueOfRemind = 120;
					break;
				default:
					valueOfRemind = 0;
					break;
				}

				paramsDate.put("scheduleEvent.eventId", eventId);
				paramsDate.put("scheduleEvent.eventName", et_name.getText());
				paramsDate.put("scheduleEvent.eventKind",
						spinner_kind.getSelectedItemPosition() + 1);
				paramsDate.put("scheduleEvent.eventTime", tv_day.getText());
				paramsDate.put("scheduleEvent.eventLocation",
						et_location.getText());
				paramsDate.put("scheduleEvent.eventContent",
						et_content.getText());
				paramsDate.put("scheduleEventUser.userId", userId);
				paramsDate.put("scheduleEventUser.eventRemind", valueOfRemind);
				paramsDate.put("scheduleEventUser.eventRemindType", toastNum);
				paramsDate.put("scheduleEventUser.eventUserList", userlist);
				new UploadDate().execute();
				UpdateScheduleClock(Integer.parseInt(eventId),tv_day.getText()+":00",
						valueOfRemind,et_name.getText().toString(),toastNum);
			}
		
			

		}
	}

	class GetDate extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return query.queryScheduleByEventId(eventId);
		}

		@Override
		protected void onPostExecute(String result) {
			initDate(result);
			spinner1.setOnItemSelectedListener(new spinner1Listener());
			spinner2.setOnItemSelectedListener(new spinner2Listener());
			super.onPostExecute(result);
		}

	}

	public void initDate(String result) {
		String sUser = "";
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			// �������ľ���JSON����Ĳ�����
			JSONArray jsonArray = data.getJSONArray("list");

			JSONArray jsonArray1 = (JSONArray) jsonArray.getJSONArray(0);
			// �¼���
			JSONObject jsonObject3 = (JSONObject) jsonArray1.opt(0);
			et_name.setText(jsonObject3.getString("eventName"));
			et_content.setText(jsonObject3.getString("eventContent"));
			int kind = Integer.parseInt(jsonObject3.getString("eventKind"));
			spinner_kind.setSelection(kind - 1);
			et_location.setText(jsonObject3.getString("eventLocation"));

			String time = jsonObject3.getString("eventTime");
			String[] sub = time.split(" ");
			String[] substring = sub[0].split("-");
			timeList[0] = Integer.parseInt(substring[0]);
			timeList[1] = Integer.parseInt(substring[1]);
			timeList[2] = Integer.parseInt(substring[2]);
			String[] substring2 = sub[1].split(":");
			timeList[3] = Integer.parseInt(substring2[0]);
			timeList[4] = Integer.parseInt(substring2[1]);

			tv_day.setText(sub[0]+" "+timeList[3] + ":" + timeList[4]);

			for (int i = 0; i < jsonArray.length(); i++) {

				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				// �¼��û�������
				JSONObject jsonObject4 = (JSONObject) jsonArray2.opt(1);
				if (jsonObject4.getString("userId").equals(userId)) {
					int iremind = Integer.parseInt(jsonObject4
							.getString("eventRemind"));
					switch (iremind) {
					case 0:
						spinner1.setSelection(0);
						break;
					case 5:
						spinner1.setSelection(1);
						break;
					case 10:
						spinner1.setSelection(2);
						break;
					case 15:
						spinner1.setSelection(3);
						break;
					case 20:
						spinner1.setSelection(4);
						break;
					case 30:
						spinner1.setSelection(5);
						break;
					case 60:
						spinner1.setSelection(6);
						break;
					case 120:
						spinner1.setSelection(7);
						break;
					case 720:
						spinner1.setSelection(8);
						break;
					case 1440:
						spinner1.setSelection(9);
						break;
					default:
						break;

					}

					int remindType = Integer.parseInt(jsonObject4
							.getString("eventRemindType"));
					spinner2.setSelection(remindType - 1);

				}
				// �¼��û���
				JSONObject jsonObject5 = (JSONObject) jsonArray2.opt(2);
				String id=jsonObject5.getString("userId");
				String nameString=jsonObject5.getString("name");
				String did=jsonObject5.getString("partOfDepartment");
				if (sUser.length() == 0) {
					sUser = nameString;
					userlist = id;
				} else {
					
					sUser += "," + nameString;
					userlist += "," + id;
				}
				if(!userId.equals(id)){
					HashMap<String, Object> usermap=new HashMap<String, Object>();
					usermap.put("uId", id);
					usermap.put("dId", did);
					usermap.put("uName", nameString);
					usermap.put("oboolen", true);
					usermapHashMaps.add(usermap);
				}
			}
			tv_user.setText(sUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class ShowDate implements OnClickListener {

		@Override
		public void onClick(View v) {
			SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(
					ScheduleUpdate.this, v, tv_day);
			menuWindow.showAtLocation(
					v,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
		/*	new TimePickerDialog(ScheduleUpdate.this, new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					timeList[3] = hourOfDay;
					timeList[4] = minute;
					String timeString=timeList[0] + "-" + timeList[1] + "-"
							+ timeList[2]+" "+timeList[3] + ":" + timeList[4];
					tv_day.setText(CountTime.FormatTime2(timeString));
				}
			}, timeList[3], timeList[4], true).show();

		

			new DatePickerDialog(ScheduleUpdate.this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {

					if (year < 1901 || year > 2049) {
						// ���ڲ�ѯ��Χ��
						new AlertDialog.Builder(ScheduleUpdate.this)
								.setTitle("��������")
								.setMessage("��ת���ڷ�Χ(1901/1/1-2049/12/31)")
								.setPositiveButton("ȷ��", null).show();
					} else {
						timeList[0] = year;
						timeList[1] = monthOfYear + 1;
						timeList[2] = dayOfMonth;
						
					}
				}
			}, timeList[0], timeList[1], timeList[2]).show();*/
		}

	}



	class getScheduleKindAsyncTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return query.EventKindList();
		}

		@Override
		protected void onPostExecute(String result) {
			getScheduleKind(result);
			super.onPostExecute(result);
		}

	}

	public void getScheduleKind(String result) {
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
			Toast.makeText(ScheduleUpdate.this, "���ݻ�ȡʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, kindNameList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_kind.setAdapter(adapter);

	}

	class spinner2Listener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (toastNum != -1)
				Toast.makeText(ScheduleUpdate.this,
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
				Toast.makeText(ScheduleUpdate.this,
						"��ǰ" + arg0.getItemAtPosition(arg2).toString(),
						Toast.LENGTH_SHORT).show();
			toastNumber = arg2;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (80 == resultCode)
		{
			usermapHashMaps=(ArrayList<HashMap<String, Object>>) data.getSerializableExtra("userMap");
			dmapHashMaps=(ArrayList<HashMap<String, Boolean>>) data.getSerializableExtra("dMap");
			if(usermapHashMaps.size()!=0){
				String stext = name;
				userlist = userId;			
				for(int i=0;i<usermapHashMaps.size();i++){
					if((Boolean) usermapHashMaps.get(i).get("oboolen")){
						
							userlist += "," + (String) usermapHashMaps.get(i).get("uId");
							stext += "," + (String) usermapHashMaps.get(i).get("uName");
					}
				}
				tv_user.setText(stext);
			}
				
		}
		else {if (requestCode == 1 && resultCode == GetSDTreeActivity.RESULT_CODE)
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

	public void creatGV() {
		ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		int maxLength = files.size() + old_files.size();
		at_image = new int[maxLength];
		at_name = new String[maxLength];
		Iterator keyIteratorOfMap = files.keySet().iterator();
		Iterator old_keyIteratorOfMap = old_files.keySet().iterator();

		for (int i = 0; i < old_files.size(); i++) {
			Object key = old_keyIteratorOfMap.next();
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

		for (int j = 0; j < files.size(); j++) {
			Object key = keyIteratorOfMap.next();
			int num = old_files.size() + j;
			at_name[num] = key.toString();
			String str = key.toString();
			int dot = str.lastIndexOf('.');
			String substring = str.substring(dot + 1);
			if (substring.equals("doc"))
				at_image[num] = R.drawable.doc;
			else if (substring.equals("xml"))
				at_image[num] = R.drawable.xls;
			else if (substring.equals("ppt"))
				at_image[num] = R.drawable.ppt;
			else if (substring.equals("pdf"))
				at_image[num] = R.drawable.pdf;
			else if (substring.equals("mp4"))
				at_image[num] = R.drawable.video;
			else if (substring.equals("3gp"))
				at_image[num] = R.drawable.video;
			else if (substring.equals("amr"))
				at_image[num] = R.drawable.audio;
			else if (substring.equals("3ga"))
				at_image[num] = R.drawable.audio;
			else if (substring.equals("wav"))
				at_image[num] = R.drawable.audio;
			else if (substring.equals("mp3"))
				at_image[num] = R.drawable.audio;
			else
				at_image[num] = R.drawable.other_file;

		}
		for (int i = 0; i < maxLength; i++) {
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
			int oldOrnew = 0;
			TextView tv = (TextView) arg1.findViewById(R.id.textView_ItemText);
			Iterator old_keyIteratorOfMap = old_files.keySet().iterator();
			for (int i = 0; i < old_files.size(); i++) {
				Object key = old_keyIteratorOfMap.next();
				if (tv.getText().equals(key.toString()))
					oldOrnew = 1;
			}
			if (oldOrnew == 1) {
				wzpost = arg2;
				dialog_download();
			} else
				dialog_down((String) tv.getText());
		}

	}

	public void dialog_download() {
		new AlertDialog.Builder(this).setTitle("ɾ��ԭ���ĵ�").setMessage("ɾ�����ĵ���")
				.setPositiveButton("��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						new DeleteAttachment().execute();
					}
				}).setNegativeButton("��", null).show();

	}

	class DeleteAttachment extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return query.DeleteAttachmentByEventId(eventId,
					listOfattachmentId[wzpost]);
		}

		@Override
		protected void onPostExecute(String result) {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;
			try {
				data = (JSONObject) jsonParser.nextValue();
				String jsonObject0 = data.getString("ieventId");
				if (jsonObject0.equals("1"))
					Toast.makeText(ScheduleUpdate.this, "ɾ���ɹ�",
							Toast.LENGTH_SHORT).show();
				old_files.remove(at_name[wzpost]);
				creatGV();
			} catch (Exception e) {
				Toast.makeText(ScheduleUpdate.this, "��������", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}

			super.onPostExecute(result);
		}

	}

	public void dialog_down(String kt) {
		final String kt2 = kt;
		new AlertDialog.Builder(this).setTitle("�Ƴ��ļ�").setMessage("�Ƴ����ļ���")
				.setPositiveButton("��", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						files.remove(kt2);
						creatGV();
					}
				}).setNegativeButton("��", null).show();

	}


}
