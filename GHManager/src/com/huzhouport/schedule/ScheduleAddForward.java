package com.huzhouport.schedule;

/**
 * ת���ճ̰���
 * ���ߣ����Ľ�
 * �����򵤵�
 ***/
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleAddForward extends Activity
{

	private TextView tv_user, tv_day, tv_kind, et_name, et_content,
			et_location, tv_userinfo;
	private Spinner spinner1, spinner2;
	private GridView gv;
	private int toastNum = -1;
	private int toastNumber = -1;
	private Query query = new Query();
	private String[] kindNameList;
	private String[] kindIdList;
	private String actionUrl, param;
	private ImageButton back, imguser, imgbt_finish, imgbt_addFile;
	private String userlist = "", agereeUserIdlist = "";// ��Ҫ������û�ID�б�
	Map<String, File> files = new HashMap<String, File>();
	Map<String, Object> paramsDate = new HashMap<String, Object>();
	private String userId, kind, sname, timeinfo, location, content,
			userNamelist, eventId;
	
	ArrayList<HashMap<String, Object>> usermapHashMaps = new ArrayList<HashMap<String, Object>>();
	ArrayList<HashMap<String, Boolean>> dmapHashMaps = new ArrayList<HashMap<String, Boolean>>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_addforward);

		et_name = (TextView) findViewById(R.id.schedule_addforward_name);
		et_content = (TextView) findViewById(R.id.schedule_addforward_content);
		et_location = (TextView) findViewById(R.id.schedule_addforward_location);
		tv_user = (TextView) findViewById(R.id.schedule_addforward_user);
		tv_userinfo = (TextView) findViewById(R.id.schedule_addforward_canyu);
		tv_day = (TextView) findViewById(R.id.schedule_addforward_day);
		tv_kind = (TextView) findViewById(R.id.schedule_addforward_kind);
		spinner1 = (Spinner) findViewById(R.id.schedule_add_spinner1);
		spinner2 = (Spinner) findViewById(R.id.schedule_add_spinner2);
		imguser = (ImageButton) findViewById(R.id.schedule_addforward_userImage);
		back = (ImageButton) findViewById(R.id.schedule_addforward_back);
		imgbt_finish = (ImageButton) findViewById(R.id.schedule_addforward_finish);
		imgbt_addFile = (ImageButton) findViewById(R.id.schedule_addFile);
		gv = (GridView) findViewById(R.id.schedule_add_gridView);

		createRemind();
		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		userId = String.valueOf(User.id);
		sname = intent.getStringExtra("sname");
		et_name.setText(sname);
		timeinfo = intent.getStringExtra("time");
		tv_day.setText(timeinfo);
		location = intent.getStringExtra("location");
		et_location.setText(location);
		kind = intent.getStringExtra("kind");
		tv_kind.setText(kind);
		content = intent.getStringExtra("content");
		et_content.setText(content);
		userNamelist = intent.getStringExtra("userNamelist");
		agereeUserIdlist = intent.getStringExtra("userNamelistId");
		tv_userinfo.setText(userNamelist);
		userlist = userId;
		// �ճ�����
		getScheduleKind();

		imguser.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				/*Intent intent = new Intent(ScheduleAddForward.this,
						ScheduleSelectUserInfo.class);
				intent.putExtra("agereeUserIdlist", agereeUserIdlist);
				intent.putExtra("eventId", eventId);
				intent.putExtra("userMap", usermapHashMaps);
				intent.putExtra("dMap", dmapHashMaps);
				startActivityForResult(intent, 80);*/
			}
		});
		back.setOnClickListener(new MyBack());
		imgbt_finish.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finishAdd();

			}
		});
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
			intent.setClass(ScheduleAddForward.this, GetSDTreeActivity.class);
			startActivityForResult(intent, 1);
		}

	}

	public void finishAdd()
	{

		actionUrl = HttpUtil.BASE_URL + "AddUser";
		if (et_name.getText().length() == 0)
		{
			Toast.makeText(ScheduleAddForward.this, "�������ճ�����",
					Toast.LENGTH_SHORT).show();
		} else if (tv_user.getText().length() == 0)
		{
			Toast.makeText(ScheduleAddForward.this, "��ѡ��ת������",
					Toast.LENGTH_SHORT).show();
		} else
		{
			// �ύ��Ϣ
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
			param = "scheduleEventUser.eventId=" + eventId
					+ "&scheduleEventUser.eventRemind=" + valueOfRemind
					+ "&scheduleEventUser.eventRemindType=" + toastNum
					+ "&scheduleEventUser.userAgree=" + 3
					+ "&scheduleEventUser.eventUserList=" + userlist
					+ "&user.userId=" + userId;

			Post oPost = new Post(ScheduleAddForward.this);
			oPost.execute();
		}
	}

	class Post extends AsyncTask<Void, Void, String>
	{

		ProgressDialog pDialog = null;

		public Post(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(
					ScheduleAddForward.this, this, "�ύ��,���Ժ򡤡���");
			pDialog.show();
		}

		@Override
		protected String doInBackground(Void... params)
		{
			if (isCancelled())
				return null;// ȡ���첽

			UploadActivity.tool.addFile(et_name.getText() + "���ճ̰���", actionUrl,
					param);
			return "";
		}

		@Override
		protected void onPostExecute(String result)
		{

			Intent intent = new Intent(ScheduleAddForward.this,
					CalendarActivity.class);
			//intent.putExtra("User", user);
			startActivity(intent);
			finish();


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
			Intent intent = new Intent(ScheduleAddForward.this,
					CalendarActivity.class);
			//intent.putExtra("User", user);
			startActivity(intent);
			finish();

		}

	}

	class ShowDate implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{

			SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(
					ScheduleAddForward.this, v, tv_day);
			menuWindow.showAtLocation(v, Gravity.BOTTOM
					| Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��

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
				Toast.makeText(ScheduleAddForward.this, "���ݻ�ȡʧ��",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getBaseContext(), android.R.layout.simple_spinner_item,
					kindNameList);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
				Toast.makeText(ScheduleAddForward.this,
						"ѡ������ѷ�ʽ��" + arg0.getItemAtPosition(arg2).toString(),
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
				Toast.makeText(ScheduleAddForward.this,
						"��ǰ" + arg0.getItemAtPosition(arg2).toString(),
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
				String stext = "";
				userlist = "";
				for (int i = 0; i < usermapHashMaps.size(); i++)
				{
					if ((Boolean) usermapHashMaps.get(i).get("oboolen"))
					{
						if (userlist.length() == 0)
						{
							userlist += (String) usermapHashMaps.get(i).get(
									"uId");
							stext += (String) usermapHashMaps.get(i).get(
									"uName");
						} else
						{
							userlist += ","
									+ (String) usermapHashMaps.get(i)
											.get("uId");
							stext += ","
									+ (String) usermapHashMaps.get(i).get(
											"uName");
						}
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
		new AlertDialog.Builder(this).setTitle("�Ƴ��ļ�")
				.setMessage("�Ƴ�" + kt2 + "�ļ���")
				.setPositiveButton("��", new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int which)
					{
						files.remove(kt2);
						creatGV();
					}
				}).setNegativeButton("��", null).show();

	}

}
