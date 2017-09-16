package com.huzhouport.schedule;

/**
 * 添加日程安排
 * 沈丹丹
 ***/
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.elc.GetSDTreeActivity;
import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.tool.SelectPicPopupWindow;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.User;
import com.huzhouport.upload.UploadActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
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

public class ScheduleAdd extends Activity
{

	private EditText et_name, et_content, et_location;
	private TextView tv_user, tv_day;
	private Spinner spinner_kind, spinner1, spinner2;
	private GridView gv;
	private int toastNum = -1;
	private int toastNumber = -1;
	private Query query = new Query();
	private String[] kindNameList;
	private String[] kindIdList;;
	private ImageButton back, imguser, imgbt_finish, imgbt_addFile;
	private String userlist = "";// 需要保存的用户ID列表
	Map<String, File> files = new HashMap<String, File>();
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private String userId, name;
	private User user;
	ArrayList<HashMap<String, Object>> usermapHashMaps = new ArrayList<HashMap<String, Object>>();
	ArrayList<HashMap<String, Boolean>> dmapHashMaps = new ArrayList<HashMap<String, Boolean>>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_add);

		et_name = (EditText) findViewById(R.id.schedule_add_name);
		et_content = (EditText) findViewById(R.id.schedule_add_content);
		et_location = (EditText) findViewById(R.id.schedule_add_location);
		tv_user = (TextView) findViewById(R.id.schedule_add_user);
		tv_day = (TextView) findViewById(R.id.schedule_add_day);
		spinner_kind = (Spinner) findViewById(R.id.schedule_add_kind);
		spinner1 = (Spinner) findViewById(R.id.schedule_add_spinner1);
		spinner2 = (Spinner) findViewById(R.id.schedule_add_spinner2);
		imguser = (ImageButton) findViewById(R.id.schedule_add_userImage);
		back = (ImageButton) findViewById(R.id.schedule_add_back);
		imgbt_finish = (ImageButton) findViewById(R.id.schedule_add_finish);
		imgbt_addFile = (ImageButton) findViewById(R.id.schedule_addFile);
		gv = (GridView) findViewById(R.id.schedule_add_gridView);

		createRemind();
		Intent intent = getIntent();
		String time = intent.getStringExtra("time");
		user = (User) intent.getSerializableExtra("User");
		userId = String.valueOf(user.getUserId());
		userlist = userId;
		name = user.getName();
		tv_user.setText(name);
		tv_day.setText(CountTime.FormatTime2(time + " 0:0"));
		// 日程类型
		getScheduleKind();

		imguser.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				Intent intent = new Intent(ScheduleAdd.this,
						ScheduleSelectUser.class);
				intent.putExtra("User", user);
				intent.putExtra("userMap", usermapHashMaps);
				intent.putExtra("dMap", dmapHashMaps);
				startActivityForResult(intent, 80);
			}
		});
		tv_day.setOnClickListener(new ShowDate());
		back.setOnClickListener(new MyBack());
		imgbt_finish.setOnClickListener(new finishAdd());
		imgbt_addFile.setOnClickListener(new addFile());

	}

	private void createRemind()
	{
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

	class addFile implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent();
			intent.setClass(ScheduleAdd.this, GetSDTreeActivity.class);
			startActivityForResult(intent, 1);
		}

	}

	class finishAdd implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			if (et_name.getText().length() == 0)
			{
				Toast.makeText(ScheduleAdd.this, "请输入日程名称", Toast.LENGTH_SHORT)
						.show();
			} else
			{
				// 提交信息
				int valueOfRemind;
				switch (toastNumber)
				{
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
				Post oPost = new Post(ScheduleAdd.this);
				oPost.execute();
			}
		}

	}

	class Post extends AsyncTask<Void, Void, String>
	{

		ProgressDialog pDialog = null;

		public Post(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(
					ScheduleAdd.this, this, "提交中,请稍候···");

			pDialog.show();
		}

		@Override
		protected String doInBackground(Void... params)
		{
			if (isCancelled())
				return null;// 取消异步

			String actionUrl = HttpUtil.BASE_URL + "addEvent_mobile.action";
			UploadActivity.tool.addFile("添加" + et_name.getText() + "的日程安排",
					actionUrl, paramsDate, files, "scheduleAttachment.af");
			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			Intent intent = new Intent(ScheduleAdd.this, CalendarActivity.class);
			intent.putExtra("User", user);
			startActivity(intent);
			finish();

			if (user != null)
			{
				new Log(user.getName(), "添加日程安排", GlobalVar.LOGSAVE, "")
						.execute();
			}

			if (pDialog != null)
				pDialog.dismiss();

			super.onPostExecute(result);
		}

	}

	class MyBack implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(ScheduleAdd.this, CalendarActivity.class);
			intent.putExtra("User", user);
			startActivity(intent);
			finish();

		}

	}

	/**
	 * 后退按钮事件监听
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Intent intent = new Intent(ScheduleAdd.this, CalendarActivity.class);
			intent.putExtra("User", user);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	class ShowDate implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(
					ScheduleAdd.this, v, tv_day);
			menuWindow.showAtLocation(v, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

		}

	}

	public void getScheduleKind()
	{
		new getScheduleKindList().execute();

	}

	class getScheduleKindList extends AsyncTask<Void, Void, String>
	{

		@Override
		protected String doInBackground(Void... params)
		{

			return query.EventKindList();
		}

		@Override
		protected void onPostExecute(String result)
		{

			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;
			try
			{
				data = (JSONObject) jsonParser.nextValue();
				JSONArray jsonArray = data.getJSONArray("list");
				kindNameList = new String[jsonArray.length()];
				kindIdList = new String[jsonArray.length()];
				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
					kindNameList[i] = jsonObject.getString("scheduleKindName");
					kindIdList[i] = jsonObject.getString("scheduleKindId");
				}

			} catch (Exception e)
			{
				Toast.makeText(ScheduleAdd.this, "数据获取失败", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getBaseContext(), android.R.layout.simple_spinner_item,
					kindNameList);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_kind.setAdapter(adapter);

			super.onPostExecute(result);
		}

	}

	class spinner2Listener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			if (toastNum != -1)
				Toast.makeText(ScheduleAdd.this,
						"选择的提醒方式：" + arg0.getItemAtPosition(arg2).toString(),
						Toast.LENGTH_SHORT).show();
			toastNum = arg2 + 1;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{

		}
	}

	class spinner1Listener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			if (toastNumber != -1)
				Toast.makeText(ScheduleAdd.this,
						"提前" + arg0.getItemAtPosition(arg2).toString(),
						Toast.LENGTH_SHORT).show();
			toastNumber = arg2;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{

		}

	}

	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		if (80 == resultCode)
		{
			usermapHashMaps = (ArrayList<HashMap<String, Object>>) data
					.getSerializableExtra("userMap");
			dmapHashMaps = (ArrayList<HashMap<String, Boolean>>) data
					.getSerializableExtra("dMap");
			if (usermapHashMaps.size() != 0)
			{
				String stext = name;
				userlist = userId;
				for (int i = 0; i < usermapHashMaps.size(); i++)
				{
					if ((Boolean) usermapHashMaps.get(i).get("oboolen"))
					{

						userlist += ","
								+ (String) usermapHashMaps.get(i).get("uId");
						stext += ","
								+ (String) usermapHashMaps.get(i).get("uName");
					}
				}
				tv_user.setText(stext);
			}

		} else
		{
			if (requestCode == 1 && resultCode == GetSDTreeActivity.RESULT_CODE)
			{
				if (data != null)
				{
					String path = data
							.getStringExtra(GetSDTreeActivity.RESULT_PATH);
					String name = data
							.getStringExtra(GetSDTreeActivity.RESULT_NAME);
					if (path != null && !path.equals("") && name != null
							&& !name.equals(""))
					{
						files.put(name, new File(path));
					}

				}
			}
			creatGV();
		}
	}

	@SuppressWarnings("rawtypes")
	public void creatGV()
	{
		ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		int[] at_image = new int[files.size()];
		String[] at_name = new String[files.size()];
		Iterator keyIteratorOfMap = files.keySet().iterator();
		for (int i = 0; i < files.size(); i++)
		{
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
		for (int i = 0; i < files.size(); i++)
		{
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

	class gridViewOnClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			TextView tv = (TextView) arg1.findViewById(R.id.textView_ItemText);
			dialog_down((String) tv.getText());
		}

	}

	public void dialog_down(String kt)
	{
		final String kt2 = kt;
		new AlertDialog.Builder(this).setTitle("移除文件")
				.setMessage("移除" + kt2 + "文件？")
				.setPositiveButton("是", new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int which)
					{
						files.remove(kt2);
						creatGV();
					}
				}).setNegativeButton("否", null).show();

	}

}
