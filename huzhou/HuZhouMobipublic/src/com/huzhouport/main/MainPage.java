package com.huzhouport.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.example.huzhouportpublic.R;
import com.huzhouport.common.tool.CountTime;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.Query;
import com.huzhouport.complain.activity.ComplainMainActivity;
import com.huzhouport.dangerousgoodsjob.DangerousgoodsjobAddList;
import com.huzhouport.dangerousgoodsportln.DangerousgoodsportlnAddList;
import com.huzhouport.electricreport.ElectricReportNewList;
import com.huzhouport.integratedquery.CBJFXX;
import com.huzhouport.integratedquery.CBWZXX;
import com.huzhouport.knowledge.KnowledgeNewView;
import com.huzhouport.model.ShipBinding;
import com.huzhouport.model.User;
import com.huzhouport.model.WharfBinding;
import com.huzhouport.portdynmicnews.ComprehensiveMain;
import com.huzhouport.portdynmicnews.IndustryInfoActivity;
import com.huzhouport.portdynmicnews.PortdynmicnewsActivity;
import com.huzhouport.pushmsg.IndustryInfoPushMsgManager;
import com.huzhouport.pushmsg.NotifyPushMsgManger;
import com.huzhouport.pushmsg.PushMsg;
import com.huzhouport.pushmsg.ServerPushService;
import com.huzhouport.setup.SetBoatman;
import com.huzhouport.setup.SetUpMain;
import com.huzhouport.upload.UploadActivity;
import com.huzhouport.version.CheckVersion;
import com.huzhouport.wharfwork.WharfWorkList;

public class MainPage extends Activity 
{
	private SimpleAdapter adpter;
	GridView _gridView1;
	private User user = new User();
	UpdateAppManager oUpdateAppManager;
	private String declareTime;
	private Query query = new Query();
	private boolean bIsAutoLogin = false;
	private String sUserName, sPassWord, sTime;
	Map<String, Object> params = new HashMap<String, Object>();
	private String checkboxvalue1, checkboxvalue2;
	ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
	private int num = 0;// 退出标志
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UploadActivity.tool.setContext(getApplicationContext());
		_gridView1 = (GridView) findViewById(R.id.gridView1);
		title = (TextView) findViewById(R.id.activity_main_title);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		checkboxvalue1 = intent.getStringExtra("checkboxvalue1");
		checkboxvalue2 = intent.getStringExtra("checkboxvalue2");
		ListTask task = new ListTask(); // 获取当地时间
		task.execute();
		GetOther(null);
		Intent regIntent = new Intent(this, ServerPushService.class);
		regIntent.putExtra("User", user);
		this.startService(regIntent);
		onKeyDown(KeyEvent.KEYCODE_BACK, null);// 返回按钮
		num++;
		showMainpage();
	}

	public void GetOther(View view) {
		new CheckVersion(true, false, this).check();
	}

	private Integer[] images1 = { R.drawable.mm_portdynamic,R.drawable.mm_industry,
			R.drawable.mm_notice, R.drawable.mm_acknowledge,
			R.drawable.wharfwork, R.drawable.mm_dangerousgoodsjobcheck,
			R.drawable.complain };
	private Integer[] images2 = { R.drawable.mm_portdynamic,R.drawable.mm_industry,
			R.drawable.mm_notice, R.drawable.mm_dangerousgoodsreportin,
			R.drawable.mm_shippayment, R.drawable.mm_shipillegalinfor,
			R.drawable.mm_electricreport, R.drawable.mm_acknowledge,
			R.drawable.complain, R.drawable.mm_shippeople };
	private Integer[] images3 = { R.drawable.mm_portdynamic,R.drawable.mm_industry,
			R.drawable.mm_notice, R.drawable.mm_acknowledge };
	private String[] texts1 = { "港航动态","行业资讯", "通知公告", "知识库", "码头作业报告", "危险货物码头作业报告",
			"湖州港航客服" };
	private String[] texts2 = { "港航动态", "行业资讯","通知公告", "危险品船舶进港", "缴费记录查询", "违章记录查询",
			"航行电子报告", "知识库", "湖州港航客服", "船舶配员信息" };
	private String[] texts3 = { "港航动态", "行业资讯","通知公告", "知识库" };
	private Class<?>[] actionPage1 = { PortdynmicnewsActivity.class,IndustryInfoActivity.class,
			KnowledgeNewView.class, ComprehensiveMain.class,
			WharfWorkList.class, DangerousgoodsjobAddList.class,
			ComplainMainActivity.class };
	private Class<?>[] actionPage2 = { PortdynmicnewsActivity.class,IndustryInfoActivity.class,
			KnowledgeNewView.class, DangerousgoodsportlnAddList.class,
			CBJFXX.class, CBWZXX.class, ElectricReportNewList.class,
			ComprehensiveMain.class, ComplainMainActivity.class,
			SetBoatman.class };
	private Class<?>[] actionPage3 = { PortdynmicnewsActivity.class,IndustryInfoActivity.class,
			KnowledgeNewView.class, ComprehensiveMain.class };

	public void refreshnotifymsg() {
		new AsyncTask<Void, Void, Integer>() {
			@Override
			protected Integer doInBackground(Void... param) {
				int notifymsgcnt = new NotifyPushMsgManger(
						getApplicationContext()).getUserUnreadNotifyCnt(user);
				return notifymsgcnt;
			}

			protected void onPostExecute(Integer result) {
				int unreadcnt = result;
				// 找到通知公告，并更新界面
				for (int i = 0; i < lst.size(); i++) {
					HashMap<String, Object> map = lst.get(i);
					if (map.get("itemText").equals("通知公告")) {
						if (unreadcnt > 0) {
							map.put("itemImage", R.drawable.notifyunread);
						} else {
							map.put("itemImage", R.drawable.mm_notice);
						}
					}
					if (adpter != null)
						adpter.notifyDataSetChanged();
				}
			}
		}.execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		if (notifyBroadcastReceiver == null)
			notifyBroadcastReceiver = new NotifyBroadcastReceiver();

		filter.addAction(ServerPushService.PUSHMSGBROADCAST);
		registerReceiver(notifyBroadcastReceiver, filter);
		refreshnotifymsg();
		refreshindustryinfo();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(notifyBroadcastReceiver);
		super.onPause();
	}
	
	public void refreshindustryinfo() {
		new AsyncTask<Void, Void, Integer>() {
			@Override
			protected Integer doInBackground(Void... param) {
				int msgcnt = new IndustryInfoPushMsgManager(MainPage.this)
						.getPushedUnReadMsgCnt(-1);
				return msgcnt;
			}

			protected void onPostExecute(Integer result) {
				int msgcnt = result;
				// 找到请假加班，并更新界面
				for (int i = 0; i < lst.size(); i++) {
					HashMap<String, Object> map = lst.get(i);
					if (map.get("itemText").equals("行业资讯")) {
						if (msgcnt > 0) {
							map.put("itemImage", R.drawable.industryinfo_unread);
						} else {
							map.put("itemImage", R.drawable.mm_industry);
						}
					}
					if (adpter != null)
						adpter.notifyDataSetChanged();
				}
			}
		}.execute();
	}


	public class NotifyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int moduler = intent.getIntExtra("moduler", -1);

			if (moduler == PushMsg.MODULERID_NOTIFY)
				refreshnotifymsg();
			if (moduler == PushMsg.MODULERID_INDUSTRYINFO)
				refreshindustryinfo();
		}
	}

	private NotifyBroadcastReceiver notifyBroadcastReceiver = null;

	class gridView1OnClickListener implements OnItemClickListener {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (user == null) {
				Intent intent = null;
				intent = new Intent(MainPage.this, (Class<?>) lst.get(arg2)
						.get("actionPage"));
				intent.putExtra("User", user);
				intent.putExtra("departmentId", "-1");// 通知公告的id
				startActivity(intent);
			} else {
				if ((Class<?>) lst.get(arg2).get("actionPage") == DangerousgoodsportlnAddList.class
						|| (Class<?>) lst.get(arg2).get("actionPage") == ElectricReportNewList.class) {
					BlackList blacklist = new BlackList(MainPage.this); // 异步
					blacklist.execute();
				}
				Intent intent = null;
				intent = new Intent(MainPage.this, (Class<?>) lst.get(arg2)
						.get("actionPage"));
				intent.putExtra("User", user);
				intent.putExtra("departmentId", "-1");// 通知公告的id
				startActivity(intent);
			}

		}

	}

	class UpdateTask extends AsyncTask<UpdateAppManager, Integer, Integer> {

		ProgressDialog pDialog;

		public UpdateTask(Context context) {
			pDialog = ProgressDialog.show(context, "更新", "正在获取版本信息", true);
		}

		@Override
		protected Integer doInBackground(UpdateAppManager... params) {
			// TODO 自动生成的方法存根
			Integer iRet = params[0].GetNetVersion();
			return iRet;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO 自动生成的方法存根
			if (pDialog != null)
				pDialog.dismiss();

			if (result > oUpdateAppManager.getVerCode()) {
				oUpdateAppManager.checkUpdateInfo();
			}
			super.onPostExecute(result);

		}

	}

	public void OpenPerson(View view) 
	{

		Intent intent = new Intent(MainPage.this, SetUpMain.class);
		intent.putExtra("User", user);
		startActivity(intent);

	}

	public Boolean CheckBinding() {
		if (user.getShipBindingList() == null) {
			Toast.makeText(this, "未绑定相关船舶", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	public Boolean CheckBinding1() {
		if (user.getWharfBindingList() == null) {
			Toast.makeText(this, "未绑定相关码头", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	class ListTask extends AsyncTask<String, Integer, String> {


		@Override
		protected String doInBackground(String... params) {
			return query.GetServiceTime();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				GetTime(result);// 获得数据

				AutoLogin(); // 自动登录
			} else {
				lst = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < images3.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("itemImage", images3[i]);
					map.put("itemText", texts3[i]);
					map.put("actionPage", actionPage3[i]);
					lst.add(map);
				}

				adpter = new SimpleAdapter(MainPage.this, lst,
						R.layout.menulist, new String[] { "ifnew", "itemImage",
								"itemText" }, new int[] { R.id.textView_read,
								R.id.imageView_ItemImage,
								R.id.textView_ItemText });
				_gridView1.setAdapter(adpter);
				_gridView1
						.setOnItemClickListener(new gridView1OnClickListener());

			}
			super.onPostExecute(result);
		}

	}

	public void GetTime(String result) {
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			declareTime = data.getString("serverTime");
		} catch (Exception e) {
			Toast.makeText(MainPage.this, "数据获取失败", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public void AutoLogin() {
		if (user == null) {
			SharedPreferences oSharedPreferences = getSharedPreferences(
					"UserDataPublic", 0);
			bIsAutoLogin = oSharedPreferences.getBoolean("IsAutoLogin", false);
			if (bIsAutoLogin) {
				if (oSharedPreferences.getString("checkboxvalue1", "").equals(
						"1")
						|| oSharedPreferences.getString("checkboxvalue2", "")
								.equals("1")) {
					sUserName = oSharedPreferences.getString("Username", "");
					sPassWord = oSharedPreferences.getString("Password", "");
					if (oSharedPreferences.getString("checkboxvalue2", "")
							.equals("1")) {
						sTime = oSharedPreferences.getString("DeclareTime", "");
						int trueandflase = Bigtime(sTime, declareTime); // 是否超过2个星期没登了

						if (trueandflase == 1) {
							new Logining(this).execute();
						} else {
							Editor oEditor = getSharedPreferences(
									"UserDataPublic", 0).edit();
							oEditor.clear();
							oEditor.commit();
							lst = new ArrayList<HashMap<String, Object>>();
							for (int i = 0; i < images3.length; i++) {
								HashMap<String, Object> map = new HashMap<String, Object>();
								map.put("itemImage", images3[i]);
								map.put("itemText", texts3[i]);
								map.put("actionPage", actionPage3[i]);
								lst.add(map);
							}

							adpter = new SimpleAdapter(this, lst,
									R.layout.menulist, new String[] { "ifnew",
											"itemImage", "itemText" },
									new int[] { R.id.textView_read,
											R.id.imageView_ItemImage,
											R.id.textView_ItemText });
							_gridView1.setAdapter(adpter);
							_gridView1
									.setOnItemClickListener(new gridView1OnClickListener());
						}
					} else {
						lst = new ArrayList<HashMap<String, Object>>();
						for (int i = 0; i < images3.length; i++) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("itemImage", images3[i]);
							map.put("itemText", texts3[i]);
							map.put("actionPage", actionPage3[i]);
							lst.add(map);
						}

						adpter = new SimpleAdapter(this, lst,
								R.layout.menulist, new String[] { "ifnew",
										"itemImage", "itemText" }, new int[] {
										R.id.textView_read,
										R.id.imageView_ItemImage,
										R.id.textView_ItemText });
						_gridView1.setAdapter(adpter);
						_gridView1
								.setOnItemClickListener(new gridView1OnClickListener());
					}
				} else {
					lst = new ArrayList<HashMap<String, Object>>();
					for (int i = 0; i < images3.length; i++) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("itemImage", images3[i]);
						map.put("itemText", texts3[i]);
						map.put("actionPage", actionPage3[i]);
						lst.add(map);
					}

					adpter = new SimpleAdapter(this, lst, R.layout.menulist,
							new String[] { "ifnew", "itemImage", "itemText" },
							new int[] { R.id.textView_read,
									R.id.imageView_ItemImage,
									R.id.textView_ItemText });
					_gridView1.setAdapter(adpter);
					_gridView1
							.setOnItemClickListener(new gridView1OnClickListener());

				}

			} else {
				lst = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < images3.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("itemImage", images3[i]);
					map.put("itemText", texts3[i]);
					map.put("actionPage", actionPage3[i]);
					lst.add(map);
				}

				adpter = new SimpleAdapter(this, lst, R.layout.menulist,
						new String[] { "ifnew", "itemImage", "itemText" },
						new int[] { R.id.textView_read,
								R.id.imageView_ItemImage,
								R.id.textView_ItemText });
				_gridView1.setAdapter(adpter);
				_gridView1
						.setOnItemClickListener(new gridView1OnClickListener());

			}

		} else {
			Editor oEditor = getSharedPreferences("UserDataPublic", 0).edit();
			oEditor.clear();
			oEditor.putString("Username", getIntent()
					.getStringExtra("username"));
			oEditor.putString("Password", getIntent()
					.getStringExtra("password"));
			oEditor.putString("DeclareTime", declareTime);
			oEditor.putBoolean("IsAutoLogin", true);
			oEditor.putString("checkboxvalue1", checkboxvalue1);
			oEditor.putString("checkboxvalue2", checkboxvalue2);
			oEditor.commit();

			if (user.getWharfBindingList() != null) {
				lst = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < images1.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("itemImage", images1[i]);
					map.put("itemText", texts1[i]);
					map.put("actionPage", actionPage1[i]);
					lst.add(map);
				}

				adpter = new SimpleAdapter(this, lst, R.layout.menulist,
						new String[] { "ifnew", "itemImage", "itemText" },
						new int[] { R.id.textView_read,
								R.id.imageView_ItemImage,
								R.id.textView_ItemText });
				_gridView1.setAdapter(adpter);
				_gridView1
						.setOnItemClickListener(new gridView1OnClickListener());
			}
			if (user.getShipBindingList() != null) {
				lst = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < images2.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("itemImage", images2[i]);
					map.put("itemText", texts2[i]);
					map.put("actionPage", actionPage2[i]);
					lst.add(map);
				}

				adpter = new SimpleAdapter(this, lst, R.layout.menulist,
						new String[] { "ifnew", "itemImage", "itemText" },
						new int[] { R.id.textView_read,
								R.id.imageView_ItemImage,
								R.id.textView_ItemText });
				_gridView1.setAdapter(adpter);
				_gridView1
						.setOnItemClickListener(new gridView1OnClickListener());
			}

		}

		refreshnotifymsg();
	}

	public int Bigtime(String time2, String time1) { // 上次登录的时间 ，这次登录的时间
		int r = 0;
		long quot = 0;
		long quot1 = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH",
				Locale.getDefault());
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot1 = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24; // 计算几天
			quot1 = quot1 % (1000 * 24 * 60 * 60) / (1000 * 60 * 60);// 计算差多少小时

			int lt = (int) quot;
			if (lt <= 14) {
				r = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}

	class Logining extends AsyncTask<Void, Void, Integer> {
		public Logining() {
		}

		public Logining(Context content) {

		}

		protected Integer doInBackground(Void... params) {
			return getLoginInfo();
		}

		protected void onPostExecute(Integer result) {
			showMainpage();
			super.onPostExecute(result);
		}
	}

	private int getLoginInfo() {
		String username = sUserName;
		String pwd = sPassWord;
		int jsonObject1 = 0;
		String result = query(username, pwd);
		try {
			if (result != null) {
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = null;
				data = (JSONObject) jsonParser.nextValue();
				jsonObject1 = data.getInt("allTotal");
				if (jsonObject1 == 1) {
					GetModelDate(data);
				}

			}
		} catch (Exception e) {
			jsonObject1 = 0;
			e.printStackTrace();
		}
		return jsonObject1;
	}

	private String query(String username, String password) {
		// 获取imei号
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		params.put("publicUser.imei", tm.getDeviceId());
		params.put("publicUser.iccid", tm.getSimSerialNumber());
		params.put("publicUser.userName", username);
		params.put("publicUser.psd", password);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "LoginPublicMobi";
		String result = null;
		try {
			result = hfu.post(actionUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void GetModelDate(JSONObject data) {
		System.out.println("data===" + data);
		try {
			JSONObject sUser = data.getJSONObject("publicUser");
			JSONArray sBindingList = data.getJSONArray("shipBindingList");
			JSONArray wBindingList = data.getJSONArray("wharfBindingList");
			user = new User();
			user.setUserId(sUser.getInt("userId"));
			user.setUserName(sUser.getString("userName"));
			user.setPsd(sUser.getString("psd"));
			user.setPhoneNumber(sUser.getString("phoneNumber"));
			user.setImei(sUser.getString("imei"));

			if (sBindingList.length() != 0) {
				List<ShipBinding> sBindings = new ArrayList<ShipBinding>();
				for (int j = 0; j < sBindingList.length(); j++) {
					JSONObject sBinding = (JSONObject) sBindingList.opt(j);
					ShipBinding sBinding2 = new ShipBinding();
					sBinding2.setShipId(sBinding.getInt("shipId"));
					sBinding2.setShipNum(sBinding.getString("shipNum"));
					sBinding2.setShipUser(sBinding.getInt("shipUser"));
					sBinding2.setBindingTime(CountTime.FormatTimeToDay(sBinding
							.getString("bindingTime")));
					sBindings.add(sBinding2);
					if (sUser.getString("bindName").equals(
							sBinding.getString("shipNum"))) {
						user.setBindnum(j);
					}
				}

				user.setShipBindingList(sBindings);
			} else {
				user.setShipBindingList(null);
			}
			if (wBindingList.length() != 0) {
				List<WharfBinding> wBindings = new ArrayList<WharfBinding>();
				for (int j = 0; j < wBindingList.length(); j++) {
					JSONObject wBinding = (JSONObject) wBindingList.opt(j);
					WharfBinding wBinding2 = new WharfBinding();
					wBinding2.setWharfId(wBinding.getInt("wharfId"));
					wBinding2.setWharfNum(wBinding.getString("wharfNum"));
					wBinding2.setWharfUser(wBinding.getInt("wharfUser"));
					wBinding2.setBindingTime(CountTime.FormatTimeToDay(wBinding
							.getString("bindingTime")));
					wBinding2.setWharfNumber(wBinding.getString("wharfNumber"));
					wBinding2.setWharfWorkSurplus(wBinding
							.getString("wharfWorkSurplus"));
					wBindings.add(wBinding2);
					if (sUser.getString("bindName").equals(
							wBinding.getString("wharfNum"))) {
						user.setBindnum(j);
					}
				}
				user.setWharfBindingList(wBindings);
			} else {
				user.setWharfBindingList(null);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void showMainpage() {
		// 码头和船户不同的显示九宫格
		if (user != null) {
			if (user.getWharfBindingList() != null) {
				lst = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < images1.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("itemImage", images1[i]);
					map.put("itemText", texts1[i]);
					map.put("actionPage", actionPage1[i]);
					lst.add(map);
				}

				adpter = new SimpleAdapter(MainPage.this, lst,
						R.layout.menulist, new String[] { "ifnew", "itemImage",
								"itemText" }, new int[] { R.id.textView_read,
								R.id.imageView_ItemImage,
								R.id.textView_ItemText });
				_gridView1.setAdapter(adpter);
				_gridView1
						.setOnItemClickListener(new gridView1OnClickListener());

				title.setText(user.getWharfBindingList().get(user.getBindnum())
						.getWharfNum());
			}
			if (user.getShipBindingList() != null) {
				lst = new ArrayList<HashMap<String, Object>>();
				for (int i = 0; i < images2.length; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("itemImage", images2[i]);
					map.put("itemText", texts2[i]);
					map.put("actionPage", actionPage2[i]);
					lst.add(map);
				}

				adpter = new SimpleAdapter(MainPage.this, lst,
						R.layout.menulist, new String[] { "ifnew", "itemImage",
								"itemText" }, new int[] { R.id.textView_read,
								R.id.imageView_ItemImage,
								R.id.textView_ItemText });
				_gridView1.setAdapter(adpter);
				_gridView1
						.setOnItemClickListener(new gridView1OnClickListener());
				title.setText(user.getShipBindingList().get(user.getBindnum())
						.getShipNum());
			}
		} else {

			lst = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < images3.length; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", images3[i]);
				map.put("itemText", texts3[i]);
				map.put("actionPage", actionPage3[i]);
				lst.add(map);
			}

			adpter = new SimpleAdapter(MainPage.this, lst, R.layout.menulist,
					new String[] { "ifnew", "itemImage", "itemText" },
					new int[] { R.id.textView_read, R.id.imageView_ItemImage,
							R.id.textView_ItemText });
			_gridView1.setAdapter(adpter);
			_gridView1.setOnItemClickListener(new gridView1OnClickListener());

		}
	}

	class BlackList extends AsyncTask<String, Integer, String> {

		public BlackList() {

		}

		public BlackList(Context context) {

		}

		@Override
		protected String doInBackground(String... params) {
			if (isCancelled())
				return null;// 取消异步

			return getBlackList();
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null) {
				Toast.makeText(MainPage.this, "无法获取船舶信息", Toast.LENGTH_SHORT)
						.show();
			} else {
				showBlackList(result);// 获得数据
			}
			super.onPostExecute(result);
		}
	}

	private String getBlackList() {
		Map<String, Object> blacklistparams = new HashMap<String, Object>();
		blacklistparams.put("shipName",
				user.getShipBindingList().get(user.getBindnum()).getShipNum());// 注意只获得了第一个绑定的船舶名称
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = HttpUtil.BASE_URL + "CheckShipResult";
		String result = null;
		try {
			result = hfu.post(actionUrl, params);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void showBlackList(String result) {
		System.out.println("违章result=" + result);
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try {
			data = (JSONObject) jsonParser.nextValue();
			String blackname = data.getString("resultString");
			
			if (blackname==null || blackname.equals("") || blackname.equals("null")) {

			} else {
				Toast.makeText(MainPage.this, "你有违章未处理，请尽快处理！",
						Toast.LENGTH_SHORT).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 返回键完全退出
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (num == 2) {
				// 退出应用程序
				Intent exit = new Intent(Intent.ACTION_MAIN);
				exit.addCategory(Intent.CATEGORY_HOME);
				exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(exit);
				System.exit(0);
			}
			if (num == 1) {
				Toast.makeText(this, "再按一次返回退出港航服务站", Toast.LENGTH_SHORT)
						.show();
				num++;

			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
