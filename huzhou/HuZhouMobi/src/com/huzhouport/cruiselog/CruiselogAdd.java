package com.huzhouport.cruiselog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.mapapi.map.MapView;
import com.example.huzhouport.R;
import com.huzhouport.common.Log;
import com.huzhouport.common.WaitingDialog;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.illegal.IllegalSee;
import com.huzhouport.main.User;
import com.huzhouport.time.WheelMain;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class CruiselogAdd extends Activity
{
	private String status;
	private String[] userNameList;
	private String[] userIdList;
	private Query query = new Query();
	private List<Map<String, Object>> parentList;
	private ArrayList<ArrayList<HashMap<String, Object>>> allchildList;
	HashMap<String, Object> mapCheckbox = new HashMap<String, Object>(); // id
	private ArrayList<HashMap<String, Object>> illegallist;
	private ListView lv;
	private TextView illidText; // 取证id
	// private TextView locaidText;
	private TextView beginTime;
	// private String name;
	private String illid = ""; // 取证id
	private String locaid = "";// 地址id
	private String cruiselogid;
	private String actionUrl, param1;
	private User user;
	MapView mapView; // 布局地图
	private String locationurl = "";
	private String locationparam = "";
	private String declareTime;
	private PopupWindow dialog;
	private ImageButton img_start, img_end, img_adduser;
	private String username, userid;
	private TextView tv_username, tv_userid;
	private boolean isback = true;

	// 定时器
	protected static final String ACTIONServiceTOActivity = "Service-Activity";// TimeService把时间变化告诉CruiselogAdd
	protected static final String ACTIONActivityToService = "Activity-Service";// CruiselogAdd告知TimeService停止、开始、清零的广播
	// 定义控件 private TextView hourTxt;
	private TextView time;
	private TextView hourTxt;
	private TextView minTxt;
	private TextView secTxt;
	private int hour;
	private int min;
	private int sec;
	private IntentFilter filter; // 广播
	private FromServiceReceiver receiver;// 接收TimeService传来的时间变化数据

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cruiselog_add);

		user = (User) getIntent().getSerializableExtra("User");
		username = user.getName();
		userid = user.getUserId() + "";
		cruiselogid = getIntent().getStringExtra("cruiselogid");

		tv_username = (TextView) findViewById(R.id.cruiselog_add_cruiseLogUserNameNeirong);
		tv_userid = (TextView) findViewById(R.id.cruiselog_add_cruiseLogUserNameID);
		tv_username.setText(username);
		tv_userid.setText(userid);

		// 获取时间
		TimeTask timetask = new TimeTask(); // 异步
		timetask.execute();

		img_adduser = (ImageButton) findViewById(R.id.cruiselog_add_cruiseLogUserNameimg);
		img_adduser.setOnClickListener(new AddUser());

		img_start = (ImageButton) findViewById(R.id.cruiselog_start);
		img_end = (ImageButton) findViewById(R.id.cruiselog_end);
		img_start.setOnClickListener(new StartSumbit());
		img_end.setOnClickListener(new EndSumbit());

		parentList = new ArrayList<Map<String, Object>>();
		allchildList = new ArrayList<ArrayList<HashMap<String, Object>>>();

		// 时间选择
		beginTime = (TextView) findViewById(R.id.cruiselog_add_startTimeNeirong);
		beginTime.setOnClickListener(new ShowDate());

		ImageButton back = (ImageButton) findViewById(R.id.cruiselog_leaveaddback);
		back.setOnClickListener(new Back());

		// 添加违章取证
		illidText = (TextView) findViewById(R.id.cruiselog_add_illlistid);
		lv = (ListView) findViewById(R.id.cruiselog_add_illlist);
		ImageButton addbig = (ImageButton) findViewById(R.id.cruiselog_add_addbig);
		addbig.setOnClickListener(new AddBig());

		// 计时器
		receiver = new FromServiceReceiver(); // 初始化广播
		filter = new IntentFilter();
		filter.addAction(ACTIONServiceTOActivity);
		hourTxt = (TextView) findViewById(R.id.cruiselog_add_time1);
		minTxt = (TextView) findViewById(R.id.cruiselog_add_time2);
		secTxt = (TextView) findViewById(R.id.cruiselog_add_time3);
		time = (TextView) findViewById(R.id.cruiselog_add_time);
		hourTxt.setText("00");
		minTxt.setText("00");
		secTxt.setText("00");
		time.setText("00 : 00 : 00");

		// 判断是新建还是 修改
		if (cruiselogid.equals("0"))
		{ // 是新建

		} else
		{
			img_start.setVisibility(View.GONE);
			img_end.setVisibility(View.VISIBLE);

			ListTask task = new ListTask(this); // 异步
			task.execute();

			// ListTaskselectloca task1 = new ListTaskselectloca(this); // 异步
			// task1.execute();
		}
	}

	protected void onResume()
	{
		super.onResume();
		registerReceiver(receiver, filter); // 注册广播
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		unregisterReceiver(receiver); // 注销广播
	}

	/**
	 * 后退按钮事件监听
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (isback)
			{
				finish();
			} else
			{
				Intent intent = new Intent(CruiselogAdd.this,
						CruiselogList.class);
				intent.putExtra("User", user);
				startActivityForResult(intent, 100);
				setResult(20);
				finish();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1000 && resultCode == 1001)
		{

			// 从子窗口中获取返回结果
			String result_value = data.getStringExtra("result");
			System.out.println("restul===" + result_value);
			if (illid == "")
			{
				illid = result_value;
			} else
			{
				illid = illid + "," + result_value;
			}
			findViewById(R.id.cruiselog_shijian1).setVisibility(View.VISIBLE);
			illidText.setText(illid);
			ListTask5 task = new ListTask5(this); // 异步
			task.execute();
		}

		if (resultCode == 30)
		{
			String adduser_name = data.getStringExtra("adduser_name");
			String adduser_id = data.getStringExtra("adduser_id");
			userid = userid + "," + adduser_id;
			username = username + "," + adduser_name;
			tv_username.setText(username);
			tv_userid.setText(userid);
		}
	}

	public class Back implements View.OnClickListener
	{
		public void onClick(View v)
		{
			// 销毁定时器
			// timer.cancel();
			if (isback)
			{
				finish();
			} else
			{
				Intent intent = new Intent(CruiselogAdd.this,
						CruiselogList.class);
				intent.putExtra("User", user);
				startActivityForResult(intent, 100);
				setResult(20);
				finish();
			}
		}
	}

	public void GetUserListByDepartment(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray = data.getJSONArray("list");
			System.out.println("jsonArray:  " + jsonArray);
			System.out.println("jsonArray.length()" + jsonArray.length());
			ArrayList<HashMap<String, Object>> childlist = null;

			if (jsonArray.length() == 0)
			{
				Toast.makeText(CruiselogAdd.this, R.string.addresslist_nofind,
						Toast.LENGTH_LONG).show();
			}
			{
				int dId = 0; // 部门id
				userNameList = new String[jsonArray.length()];
				userIdList = new String[jsonArray.length()];

				int j = 0; // 计数
				String total = null; // 部门名字
				for (int i = 0; i < jsonArray.length(); i++)
				{
					HashMap<String, Object> map = new HashMap<String, Object>();
					JSONArray jsonArray2 = (JSONArray) jsonArray
							.getJSONArray(i);
					JSONObject jsonObject_Deaprtment = (JSONObject) jsonArray2
							.opt(0);
					JSONObject jsonObject_User = (JSONObject) jsonArray2.opt(1);

					HashMap<String, Object> childmap = new HashMap<String, Object>();
					// if(!jsonObject_User.getString("userId").equals("4")){
					// 还需要判断下是否是第一个
					if (i == 0)
					{
						childlist = new ArrayList<HashMap<String, Object>>();
						dId = Integer.parseInt(jsonObject_Deaprtment
								.getString("departmentId"));

						childmap.put("child_name",
								jsonObject_User.getString("name"));
						childmap.put("child_userId",
								jsonObject_User.getString("userId"));
						childlist.add(childmap);
						System.out.println("childlist" + childlist);

						total = jsonObject_Deaprtment
								.getString("departmentName");
						j = 1;

						userNameList[i] = jsonObject_User.getString("name");
						userIdList[i] = jsonObject_User.getString("userId");
					} else
					{
						if (Integer.parseInt(jsonObject_Deaprtment
								.getString("departmentId")) == dId)
						{

							childmap.put("child_name",
									jsonObject_User.getString("name"));
							childmap.put("child_userId",
									jsonObject_User.getString("userId"));
							childlist.add(childmap);

							total = jsonObject_Deaprtment
									.getString("departmentName");
							j++;

							userNameList[i] = jsonObject_User.getString("name");
							userIdList[i] = jsonObject_User.getString("userId");
						} else
						{

							map.put("parent", total + "(" + j + ")");
							parentList.add(map);
							j = 1;

							total = jsonObject_Deaprtment
									.getString("departmentName");

							allchildList.add(childlist);
							childlist = new ArrayList<HashMap<String, Object>>();
							dId = Integer.parseInt(jsonObject_Deaprtment
									.getString("departmentId"));
							childmap.put("child_name",
									jsonObject_User.getString("name"));
							childmap.put("child_userId",
									jsonObject_User.getString("userId"));
							childlist.add(childmap);

							userNameList[i] = jsonObject_User.getString("name");
							userIdList[i] = jsonObject_User.getString("userId");
						}
					}
					// }

					if (i == (jsonArray.length() - 1))
					{
						//
						map = new HashMap<String, Object>();
						map.put("parent", total + "(" + j + ")");
						parentList.add(map);
						allchildList.add(childlist);
					}

					// System.out.println("parentList"+i+"  "+parentList);
				}
				// }

			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class MyAdapter extends BaseExpandableListAdapter
	{

		Context context;
		LayoutInflater mlayoutInflater;

		MyAdapter(Context context)
		{
			this.context = context;
			mlayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition)
		{

			return allchildList.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition)
		{

			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent)
		{

			convertView = mlayoutInflater.inflate(
					R.layout.schedule_add_item_child, null);

			final CheckBox box = (CheckBox) convertView
					.findViewById(R.id.schedule_add_checkbox);
			TextView textView1 = (TextView) convertView
					.findViewById(R.id.schedule_add_username);
			TextView textView2 = (TextView) convertView
					.findViewById(R.id.schedule_add_userid);

			textView1.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_name").toString());
			textView2.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_userId").toString());
			final String msg = textView2.getText().toString();

			if (mapCheckbox == null)
			{

			} else
			{
				if (mapCheckbox.size() > 0)
				{
					if (mapCheckbox.containsKey(msg))
					{
						box.setChecked(true);
					} else
					{
						box.setChecked(false);
					}
				}
			}
			box.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener()
			{

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked)
				{
					if (isChecked)
					{
						mapCheckbox.put(msg, "true");

					} else
					{
						mapCheckbox.remove(msg);

					}
				}

			});
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition)
		{

			return allchildList.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition)
		{

			return allchildList.get(groupPosition);
		}

		@Override
		public int getGroupCount()
		{

			return allchildList.size();
		}

		@Override
		public long getGroupId(int groupPosition)
		{

			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent)
		{

			convertView = mlayoutInflater.inflate(R.layout.schedule_user_group,
					null);

			TextView textParent = (TextView) convertView
					.findViewById(R.id.schedule_user_group);

			textParent.setText(parentList.get(groupPosition).get("parent")
					.toString());

			return convertView;
		}

		@Override
		public boolean hasStableIds()
		{

			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition)
		{
			return false;
		}

	}

	// 显示日期
	class ShowDate implements OnClickListener
	{

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v)
		{

			WindowManager wm = (WindowManager) CruiselogAdd.this
					.getSystemService(Context.WINDOW_SERVICE);
			int width = wm.getDefaultDisplay().getWidth();//

			dialog = new PopupWindow(v, width, 350);

			// 找到dialog的布局文件
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.leaveandovertime_time_layout,
					null);

			final WheelMain main = new WheelMain(dialog, view);
			main.showDateTimePicker(v);

			Button btn_sure = (Button) view
					.findViewById(R.id.btn_datetime_sure);
			Button btn_cancel = (Button) view
					.findViewById(R.id.btn_datetime_cancel);
			// 确定
			btn_sure.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{

					dialog.dismiss();
					System.out.println("time===" + main.getTime());
					beginTime.setText(main.getTime());
				}
			});
			// 取消
			btn_cancel.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					dialog.dismiss();
				}
			});

		}

	}

	public class Stu1 implements View.OnClickListener
	{
		public void onClick(View v)
		{

			ImageButton age = (ImageButton) findViewById(R.id.cruiselog_end);

			if (status.equals("1"))
			{
				age.setImageResource(R.drawable.weiwancheng);
				status = "0";
			} else
			{
				age.setImageResource(R.drawable.wancheng);
				status = "1";
			}

		}
	}

	class AddBig implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(CruiselogAdd.this,
					CruiselogIllegalAdd.class);
			intent.putExtra("User", user);
			intent.putExtra("cruiselogid", cruiselogid);
			startActivityForResult(intent, 1000); // 用于获取返回值
		}

	}

	public void getNeirong(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONArray jsonArray1 = data.getJSONArray("illegallist");
			if (jsonArray1.length() == 0)
			{

			}
			{
				illegallist = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < jsonArray1.length(); i++)
				{
					HashMap<String, Object> illegallistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray1.opt(i);
					String name = "违章描述："
							+ jsonObject.getString("illegalContent");
					String time = jsonObject.getString("illegalTime");
					String illegalId = jsonObject.getString("illegalId");
					illegallistmap.put("name", name);
					illegallistmap.put("time", time);
					illegallistmap.put("illegalId", illegalId);
					illegallist.add(illegallistmap);
				}
			}

		} catch (Exception e)
		{

			e.printStackTrace();
		}
	}

	public void showListview()
	{
		System.out.println("illegallist==" + illegallist);

		if (illegallist.size() == 0)
		{
		} else
		{
			SimpleAdapter adapter = new SimpleAdapter(
					CruiselogAdd.this,
					illegallist,
					R.layout.cruiselog_viewfinshitem,
					new String[] { "name", "time", "illegalId" },
					new int[] { R.id.cruiselog_finsh_name,
							R.id.cruiselog_finsh_time, R.id.cruiselog_finsh_id });
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new Illegal());
		}
	}

	class Illegal implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{

			TextView tv_id = (TextView) arg1
					.findViewById(R.id.cruiselog_finsh_id);
			Intent intent = new Intent(CruiselogAdd.this, IllegalSee.class);
			intent.putExtra("illegalId", tv_id.getText().toString());
			intent.putExtra("User", user);

			// startActivityForResult(intent,100);
			startActivity(intent);
			// Toast.makeText(DangerousgoodsportlnList.this,
			// tv_id.getText().toString(), Toast.LENGTH_SHORT).show();
		}

	}

	class StartSumbit implements OnClickListener
	{

		public void onClick(View v)
		{
			Editor oEditor = getSharedPreferences("TimeService", 0).edit();
			oEditor.putBoolean("isexceptionexit", false);// 异常结束
			oEditor.commit();

			// 获得要传的值
			EditText cruiseLogLoactionText = (EditText) findViewById(R.id.cruiselog_add_cruiseLogLoactionNeirong);
			TextView cruiseLogUserText = (TextView) findViewById(R.id.cruiselog_add_cruiseLogUserNameID);
			EditText shipNameText = (EditText) findViewById(R.id.cruiselog_add_shipNameNeirong);
			TextView startTimeText = (TextView) findViewById(R.id.cruiselog_add_startTimeNeirong);
			String cruiseLogLoaction = cruiseLogLoactionText.getText()
					.toString();
			String cruiseLogUser = cruiseLogUserText.getText().toString();
			String shipName = shipNameText.getText().toString();
			String startTime = startTimeText.getText().toString();

			if (cruiseLogLoaction.equals(""))
			{
				Toast.makeText(CruiselogAdd.this, "请填写巡航区域", Toast.LENGTH_SHORT)
						.show();
			} else if (cruiseLogUser.equals(""))
			{
				Toast.makeText(CruiselogAdd.this, "请填写巡航人员", Toast.LENGTH_SHORT)
						.show();
			} else if (shipName.equals(""))
			{
				Toast.makeText(CruiselogAdd.this, "请填写交通工具", Toast.LENGTH_SHORT)
						.show();
			} else if (startTime.equals(""))
			{
				Toast.makeText(CruiselogAdd.this, "请填写开始时间", Toast.LENGTH_SHORT)
						.show();
			} else
			{
				actionUrl = HttpUtil.BASE_URL + "newCruiseLog";
				param1 = "cruiselogbean.cruiseLogUser=" + cruiseLogUser
						+ "&cruiselogbean.cruiseLogUserName=" + illid
						+ "&cruiselogbean.cruiseLogLoaction="
						+ cruiseLogLoaction + "&cruiselogbean.shipName="
						+ shipName + "&cruiselogbean.startTime=" + startTime
						+ "&cruiselogbean.state=0";
				ListTask4 task = new ListTask4(CruiselogAdd.this); // 异步
				task.execute();
				img_start.setVisibility(View.GONE);
				img_end.setVisibility(View.VISIBLE);
				isback = false;
			}

			// 开启服务
			/*
			 * Intent intent=new Intent(CruiselogAdd.this,
			 * CruiselogService.class); startService(intent);
			 * img_start.setVisibility(View.GONE);
			 * img_end.setVisibility(View.VISIBLE);
			 */
		}

	}

	class EndSumbit implements OnClickListener
	{
		public void onClick(View v)
		{
			Editor oEditor = getSharedPreferences("TimeService", 0).edit();
			oEditor.putBoolean("isexceptionexit", true);// 正常结束
			oEditor.commit();

			Intent intent = new Intent(CruiselogAdd.this,
					CruiselogService.class);
			stopService(intent);
			Intent intent1 = new Intent(CruiselogAdd.this, TimeService.class);
			stopService(intent1);

			// 修改状态
			UpdateStatus task = new UpdateStatus(CruiselogAdd.this); // 异步
			task.execute();
			/*
			 * Intent intent1=new Intent(CruiselogAdd.this,
			 * CruiselogList.class); intent.putExtra("User", user);
			 * startActivityForResult(intent, 100); setResult(20);
			 */

		}
	}

	public void getNeirongUpdate(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONObject jsonArray = data.getJSONObject("cruiselogbean");
			System.out.println("jsonArray===" + jsonArray);

			String cruiseLogUser = jsonArray.getString("cruiseLogUser");
			String cruiseLogLoaction = jsonArray.getString("cruiseLogLoaction");
			String cruiseLogUserName = jsonArray.getString("cruiseLogUserName");
			String shipName = jsonArray.getString("shipName");
			String startTime = jsonArray.getString("startTime").substring(0,
					jsonArray.getString("startTime").indexOf("."));
			// String endTime=jsonArray.getString("endTime").substring(0,
			// jsonArray.getString("endTime").indexOf("."));

			TextView cruiseLogUserNameIDText = (TextView) findViewById(R.id.cruiselog_add_cruiseLogUserNameID);
			cruiseLogUserNameIDText.setText(cruiseLogUser);
			TextView cruiseLogLoactionText = (TextView) findViewById(R.id.cruiselog_add_cruiseLogLoactionNeirong);
			cruiseLogLoactionText.setText(cruiseLogLoaction);
			TextView cruiseLogUserNameText = (TextView) findViewById(R.id.cruiselog_add_cruiseLogUserNameNeirong);
			cruiseLogUserNameText.setText(cruiseLogUserName);
			TextView shipNameText = (TextView) findViewById(R.id.cruiselog_add_shipNameNeirong);
			shipNameText.setText(shipName);
			TextView startTimeText = (TextView) findViewById(R.id.cruiselog_add_startTimeNeirong);
			startTimeText.setText(startTime);
			// TextView
			// endTimeText=(TextView)findViewById(R.id.cruiselog_add_endTimeNeirong);
			// endTimeText.setText(endTime);

			JSONArray jsonArray1 = data.getJSONArray("illegallist");
			if (jsonArray1.length() == 0)
			{
				findViewById(R.id.cruiselog_shijian1).setVisibility(View.GONE);
			}
			{
				findViewById(R.id.cruiselog_shijian1).setVisibility(
						View.VISIBLE);
				illegallist = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < jsonArray1.length(); i++)
				{
					HashMap<String, Object> illegallistmap = new HashMap<String, Object>();
					JSONObject jsonObject = (JSONObject) jsonArray1.opt(i);
					String name = "违章描述："
							+ jsonObject.getString("illegalContent");
					String time = jsonObject.getString("illegalTime");
					String illegalId = jsonObject.getString("illegalId");
					illegallistmap.put("name", name);
					illegallistmap.put("time", time);
					illegallistmap.put("illegalId", illegalId);
					illegallist.add(illegallistmap);

					if (illid == "")
					{
						illid = illegalId;
					} else
					{
						illid = illid + "," + illegalId;
					}

				}
			}

		} catch (Exception e)
		{
			Toast.makeText(CruiselogAdd.this, "数据异常", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class ListTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask(Context context)
		{
			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异

			return query.showCruiseLogAndIllegal(cruiselogid);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getNeirongUpdate(result);// 获得数据
			showListview();
			super.onPostExecute(result);
		}

	}

	class ListTask4 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask4(Context context)
		{
			pDialog = ProgressDialog.show(context, "提示", "正在提交中，请稍候。。。", true);
		}

		@Override
		protected String doInBackground(String... params)
		{

			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = null;
			try
			{

				result = hfu.sendPost1(actionUrl, param1);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return result;

		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();

			JSONTokener jsonParser_User = new JSONTokener(result);
			try
			{
				JSONObject data = (JSONObject) jsonParser_User.nextValue();
				cruiselogid = data.getString("cruiseLogID");

			} catch (Exception e)
			{
				e.printStackTrace();
			}

			Intent intent = new Intent(CruiselogAdd.this,
					CruiselogService.class);
			intent.putExtra("cruiselogid", cruiselogid);
			startService(intent);

			if (user != null)
			{
				Log log = new Log(user.getName(), "添加巡航日志", GlobalVar.LOGSAVE,
						""); // 日志
				log.execute();
			}

			Intent intent1 = new Intent(CruiselogAdd.this, TimeService.class);
			startService(intent1);

			super.onPostExecute(result);
		}

	}

	class ListTask5 extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public ListTask5(Context context)
		{
			pDialog = ProgressDialog.show(context, "提示", "正在加载中，请稍候。。。", true);
		}

		@Override
		protected String doInBackground(String... params)
		{

			return query.showCruiseLogIllegalList(illid);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			getNeirong(result); // 获得数据
			showListview();// 显示数据
			super.onPostExecute(result);
		}

	}

	class ListTasklocation extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		@Override
		protected String doInBackground(String... params)
		{

			HttpFileUpTool hfu = new HttpFileUpTool();
			String result = null;
			try
			{

				result = hfu.sendPost1(locationurl, locationparam);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject data;
			try
			{
				data = (JSONObject) jsonParser.nextValue();
				String resultvalue = data.getString("locationid");
				if (locaid == "")
				{
					locaid = resultvalue;
				} else
				{
					locaid = locaid + "," + resultvalue;
				}

				// locaidText.setText(locaid);
				System.out.println("locationlocaid=" + locaid);
			} catch (Exception e)
			{

				e.printStackTrace();
			}
			super.onPostExecute(result);
		}

	}

	class ListTaskselectloca extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params)
		{

			return query.showMap(cruiselogid);
		}

		@Override
		protected void onPostExecute(String result)
		{
			getlocationlist(result);// 获得数据
			showListview();
			super.onPostExecute(result);
		}

	}

	public void getlocationlist(String result)
	{

		JSONTokener jsonParser_User = new JSONTokener(result);
		try
		{
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			JSONObject jsonArray = data.getJSONObject("locationList");
			if (jsonArray.length() == 0)
			{
			}
			{
				for (int i = 0; i < jsonArray.length(); i++)
				{

					String locationID = jsonArray.getString("locationID");

					if (locaid == "")
					{
						locaid = locationID;
					} else
					{
						locaid = locaid + "," + locationID;
					}

				}
			}

		} catch (Exception e)
		{
			Toast.makeText(CruiselogAdd.this, "数据异常", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	class TimeTask extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		@Override
		protected String doInBackground(String... params)
		{
			if (isCancelled())
				return null;// 取消异步

			return query.GetServiceTime();
		}

		@Override
		protected void onPostExecute(String result)
		{
			GetTime(result);// 获得数据
			super.onPostExecute(result);
		}

	}

	public void GetTime(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			declareTime = data.getString("serverTime").substring(0,
					data.getString("serverTime").lastIndexOf(":"));
			beginTime.setText(declareTime);

		} catch (Exception e)
		{
			Toast.makeText(CruiselogAdd.this, "时间获取失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	public class AddUser implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			Intent intent = new Intent(CruiselogAdd.this,
					CruiselogAddUSer.class);
			intent.putExtra("departmentId", "-1");
			intent.putExtra("userid", tv_userid.getText().toString());
			startActivityForResult(intent, 100);
		}
	}

	class UpdateStatus extends AsyncTask<String, Integer, String>
	{
		ProgressDialog pDialog = null;

		public UpdateStatus(Context context)
		{

			pDialog = new WaitingDialog().createDefaultProgressDialog(context,
					this, "正在结束中，请稍候。。。");
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{

			if (isCancelled())
				return null;// 取消异步

			query.updateCruiseLogByID(cruiselogid);

			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (pDialog != null)
				pDialog.dismiss();
			hourTxt.setText("00");
			minTxt.setText("00");
			secTxt.setText("00");
			time.setText("00 : 00 : 00");

			Intent intent = new Intent(CruiselogAdd.this, CruiselogList.class);
			intent.putExtra("User", user);
			startActivityForResult(intent, 100);
			setResult(20);
			finish();

			super.onPostExecute(result);
		}
	}

	class FromServiceReceiver extends BroadcastReceiver // 接收TimeService.java返回的时间信息
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Bundle bundle1 = intent.getExtras();
			hour = bundle1.getInt("hour", 0);
			min = bundle1.getInt("min", 0);
			sec = bundle1.getInt("sec", 0);
			// 把时间数据setText
			if (hour < 10)
				hourTxt.setText("0" + hour);
			else
				hourTxt.setText(hour + "");
			if (min < 10)
				minTxt.setText("0" + min);
			else
				minTxt.setText(min + "");
			if (sec < 10)
				secTxt.setText("0" + sec);
			else
				secTxt.setText(sec + "");

			String strtime = hourTxt.getText().toString() + " : "
					+ minTxt.getText().toString() + " : "
					+ secTxt.getText().toString();
			time.setText(strtime);

		}
	}
}
