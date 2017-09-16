package com.huzhouport.slidemenu;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hxkg.channel.CruiseMapAcitvity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.baidu.mapapi.SDKInitializer;
import com.example.huzhouport.R;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.ManagerDialog;
import com.huzhouport.common.util.Query;
import com.huzhouport.main.Login;
import com.huzhouport.main.RolePermission;
import com.huzhouport.main.User;
import com.huzhouport.pushmsg.ServerPushService;
import com.huzhouport.slidemenu.MenuFragment.SLMenuListOnItemClickListener;
import com.huzhouport.upload.UploadActivity;
import com.huzhouport.version.CheckVersion;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主界面Activity
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends SlidingFragmentActivity implements SLMenuListOnItemClickListener
{
	/*---------------------IP sharepreference保存信息--------------------------*/

	/**
	 * sharepreference IP信息保存文件名
	 */
	public static final String SHARED_SAVE_CONNECT_FILE_NAME = "IpData";

	/**
	 * sharepreference 连接IP
	 */
	public static final String SHARED_SAVE_CONNECT_IP = "Ip1";

	/**
	 * sharepreference 连接端口
	 */
	public static final String SHARED_SAVE_CONNECT_PORT = "Port1";

	/**
	 * sharepreference 是否首次保存
	 */
	public static final String SHARED_SAVE_CONNECT_FIRST = "IsOne";

	/**
	 * sharepreference 消息推送时间
	 */
	public static final String SHARED_SAVE_PUSH_MSG_TIMER = "PushMsgTimer";

	/*--------------------用户 sharepreference 保存信息---------------------------------*/

	/**
	 * sharepreference 用户信息保存文件名
	 */
	public static final String SHARED_SAVE_USER_FILE_NAME = "UserData";

	/**
	 * sharepreference 用户是否自动登录
	 */
	public static final String SHARED_SAVE_USER_IS_AUTO = "IsAutoLogin";

	/**
	 * sharepreference 用户名
	 */
	public static final String SHARED_SAVE_USER_USERNAME = "Username";

	/**
	 * sharepreference 密码
	 */
	public static final String SHARED_SAVE_USER_PASSWORD = "Password";

	/**
	 * sharepreference 上次登录时间
	 */
	public static final String SHARED_SAVE_USER_LAST_LOGIN_TIME = "DeclareTime";

	/**
	 * sharepreference 保存密码
	 */
	public static final String SHARED_SAVE_USER_SAVE_PASSWORD = "checkboxvalue1";

	/**
	 * sharepreference 自动登录
	 */
	public static final String SHARED_SAVE_USER_AUTO_LOGIN = "checkboxvalue2";

	/*--------------------退出相关参数----------------------------*/

	/**
	 * 第一次按返回的时间
	 */
	private long firstExitTime = -1;

	/**
	 * 两次返回间隔有效时长
	 */
	private final long AVALABLE_EXIT_INTERVAL_TIME = 5000;

	/*-----------------------------------------------------*/

	private SlidingMenu mSlidingMenu;
	private User user;
	private ImageButton ivTitleBtnLeft, ivTitleBtnLogin;
	private TextView tv_title;
	private boolean bIsAutoLogin = false;

	private String sUserName, sPassWord,sTime;
	Map<String, Object> params = new HashMap<String, Object>();
	private Fragment fragment;
	private String declareTime;
	private Query query = new Query();
	private String checkboxvalue1, checkboxvalue2;
	private int num = 0;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setTitle("Home");
		setContentView(R.layout.frame_content);

		UploadActivity.tool.setContext(getApplicationContext());

		tv_title = (TextView) findViewById(R.id.ivTitleName1);
		tv_title.setText("移动办公");
		setBehindContentView(R.layout.frame_left_menu);
		
		user = (User) getIntent().getSerializableExtra("User");
		checkboxvalue1 = getIntent().getStringExtra("checkboxvalue1");
		checkboxvalue2 = getIntent().getStringExtra("checkboxvalue2");

		// 获取服务器时间
		new queryServerTimerTask().execute();
		//自动登录
		AutoLogin();
		// 初始化设置滑动栏内容
		initSlidingMenu();

		// 退出按钮按下次数
		num = 0;

		// 初始化判断连接IP
		initIPConnect();

		// 添加按钮监听事件
		addBtnListener();

		// 检查版本更新
		update(null);

		Intent regIntent = new Intent(MainActivity.this,ServerPushService.class);
		regIntent.putExtra("User", user);
		MainActivity.this.startService(regIntent);
		//百度地图初始化
		SDKInitializer.initialize(this.getApplicationContext());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 170)
		{
			Intent regIntent = new Intent(MainActivity.this,
					ServerPushService.class);
			stopService(regIntent);
			MainActivity.this.finish();
			Intent intent = new Intent(MainActivity.this, Login.class);
			startActivity(intent);
		}
	}

	/**
	 * 初始化设置滑动栏内容
	 */
	public void initSlidingMenu()
	{
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// 设置左右都可以划出SlidingMenu菜单
		mSlidingMenu.setSecondaryMenu(R.layout.frame_right_menu); // 设置右侧菜单的布局文件
		mSlidingMenu.setSecondaryShadowDrawable(R.drawable.drawer_shadow);

		mSlidingMenu.setShadowDrawable(R.drawable.drawer_shadow);// 设置阴影图片
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width); // 设置阴影图片的宽度
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset); // SlidingMenu划出时主页面显示的剩余宽度
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}

	/**
	 * 初始化判断连接IP 判断是否首次获取； 确定IP是默认值还是本地修改的值
	 */
	public void initIPConnect()
	{
		SharedPreferences oSharedPreferences = getSharedPreferences(SHARED_SAVE_CONNECT_FILE_NAME, 0);
		if (true/*oSharedPreferences.getBoolean(SHARED_SAVE_CONNECT_FIRST, false)*/)
		{
			HttpUtil.BASE_URL_IP = oSharedPreferences.getString(
					SHARED_SAVE_CONNECT_IP,  HttpUtil.BASE_URL_IP);
			HttpUtil.BASE_URL_PORT = oSharedPreferences.getString(
					SHARED_SAVE_CONNECT_PORT, HttpUtil.BASE_URL_PORT);
			GlobalVar.PUSHTIMER = Long.valueOf(oSharedPreferences.getString(
					SHARED_SAVE_PUSH_MSG_TIMER, String.valueOf(GlobalVar.PUSHTIMER)));
			HttpUtil.BASE_URL = "http://" + HttpUtil.BASE_URL_IP + ":"
					+ HttpUtil.BASE_URL_PORT + "/HuZhouPort/"; // iP 赋值*/
		} else
		{
			Editor oEditor = getSharedPreferences(
					SHARED_SAVE_CONNECT_FILE_NAME, 0).edit();
			oEditor.clear();
			oEditor.putString(SHARED_SAVE_CONNECT_IP, HttpUtil.BASE_URL_IP);
			oEditor.putString(SHARED_SAVE_CONNECT_PORT, HttpUtil.BASE_URL_PORT);
			oEditor.putString(SHARED_SAVE_PUSH_MSG_TIMER,
					Long.toString(GlobalVar.PUSHTIMER));
			oEditor.putBoolean(SHARED_SAVE_CONNECT_FIRST, true);
			oEditor.commit();
		}
	}

	/**
	 * 添加按钮监听事件
	 */
	public void addBtnListener()
	{
		ivTitleBtnLeft = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		ivTitleBtnLeft.setOnClickListener(new showMenu());

		ivTitleBtnLogin = (ImageButton) this.findViewById(R.id.ivTitleBtnlogin);
		ivTitleBtnLogin.setOnClickListener(new login());
	}

	/**
	 * 左侧栏打开按钮监听事件 点击后打开左侧菜单
	 * 
	 * @author Administrator
	 * 
	 */
	public class showMenu implements View.OnClickListener
	{
		public void onClick(View v)
		{
			mSlidingMenu.showMenu(true);
		}
	}

	/**
	 * 点击登录按钮事件
	 * 
	 * @author Administrator
	 * 
	 */
	public class login implements View.OnClickListener
	{
		public void onClick(View v)
		{
			Intent intent = new Intent(MainActivity.this,
					MainTopRightDialog.class);
			intent.putExtra("User", user);
			startActivityForResult(intent, 20);

		}
	}

	/**
	 * 检查更新事件 点击后检查最新的版本
	 * 
	 * @param view
	 */
	public void update(View view)
	{
		new CheckVersion(true, false, this).check();
	}

	/**
	 * 左侧栏选项选择事件
	 */
	@Override
	public void selectItem(int position, String title)
	{
		Fragment fragment = null;
		switch (position)
		{
		case 0:
			fragment = new MobileOfficeFragment();
			tv_title.setText("移动办公");
			break;
		case 1:
			if (user == null)
			{
				Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT)
						.show();
				break;
			} else
			{
				fragment = new BussinessProcessingFragment();
				tv_title.setText("行政执法");
				break;
			}
		case 2:
			if (user == null)
			{
				Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT)
						.show();
				break;
			} else
			{
				fragment = new IntegratedQueryFragment();
				tv_title.setText("综合查询");
				break;
			}
		case 3:
			if (user != null)
			{
				fragment = new PhoneBookFragment();
				tv_title.setText("通讯录");
			} else
				Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT)
						.show();

			break;

		case 4:
			fragment = new SettingFragment();
			tv_title.setText("设置");
			break;

		default:
			break;
		}

		if (fragment != null)
		{
			Bundle bundle = new Bundle();
			bundle.putSerializable("User", user);
			fragment.setArguments(bundle);
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content, fragment)
					.commit();
			setTitle(title);
			mSlidingMenu.showContent();
		} else
		{
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	/**
	 * 用户登录线程
	 * 
	 * @author Administrator
	 * 
	 */
	class Logining extends AsyncTask<Void, Void, Integer>
	{

		protected Integer doInBackground(Void... params)
		{
			return getLoginInfo();
		}

		protected void onPostExecute(Integer result)
		{

			if (result != 1)
			{
				switch (result)
				{
				case 2:
					Toast.makeText(MainActivity.this, "自动登录失败，用户或已删除",
							Toast.LENGTH_LONG).show();
					break;

				case 3:
					Toast.makeText(MainActivity.this, "自动登录失败，密码或已修改",
							Toast.LENGTH_LONG).show();
					break;

				case 0:
					Toast.makeText(MainActivity.this, "自动登录失败",
							Toast.LENGTH_LONG).show();
					break;
					default:

				}
				
				Editor oEditor = getSharedPreferences(
						SHARED_SAVE_USER_FILE_NAME, 0).edit();
				oEditor.putString(SHARED_SAVE_USER_PASSWORD,"");
				oEditor.putBoolean(SHARED_SAVE_USER_IS_AUTO, false);
				oEditor.commit();
				
				Intent regIntent = new Intent(MainActivity.this,
						ServerPushService.class);
				stopService(regIntent);
				MainActivity.this.finish();
				Intent intent = new Intent(MainActivity.this, Login.class);
				startActivity(intent);
			}

			addDefaultFragment();

			super.onPostExecute(result);
		}

	}

	/**
	 * 获取并解析用户信息
	 * 
	 * @return 登录结果状态
	 */
	private int getLoginInfo()
	{
		String username = sUserName;
		String pwd = sPassWord;

		System.out.println("sUserName===" + sUserName + "  " + sPassWord);
		int jsonObject1 = 0;
		String result = query(username, pwd);
		try
		{
			if (result != null)
			{
				
				JSONTokener jsonParser = new JSONTokener(result);
				JSONObject data = null;
				data = (JSONObject) jsonParser.nextValue();
				jsonObject1 = data.getInt("loginRes");

				if (jsonObject1 == 1)
				{
					JSONObject jsonObject2 = data.getJSONObject("model");
					System.out.println("userId==="
							+ jsonObject2.getInt("userId"));
					user = new User();
					user.setUserId(jsonObject2.getInt("userId"));
					user.setName(jsonObject2.getString("name"));
					user.setPartOfDepartment(jsonObject2.getInt("partOfDepartment"));
					user.setPartOfPost(jsonObject2.getInt("partOfPost"));
					// 权限
					JSONArray jsonObject3 = data.getJSONArray("list");
					List<RolePermission> list_rpList = new ArrayList<RolePermission>();
					for (int i = 0; i < jsonObject3.length(); i++)
					{
						JSONArray jsonObject4 = (JSONArray) jsonObject3.opt(i);
						JSONObject jObject1 = (JSONObject) jsonObject4.opt(0);
						RolePermission rp = new RolePermission();
						rp.setPermissionId(jObject1.getInt("permissionId"));
						rp.setStatus(jObject1.getInt("status"));
						list_rpList.add(rp);
					}
					user.setRpList(list_rpList);
				}
			}
		} catch (Exception e)
		{
			jsonObject1 = 0;
		}
		return jsonObject1;
	}

	/**
	 * 用户登录请求
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 登录结果返回JSon值
	 */
	private String query(String username, String password)
	{
		params.put("user.userName", username);
		params.put("user.password", password);
		HttpFileUpTool hfu = new HttpFileUpTool();
		String actionUrl = //"http://192.168.1.100:8080/HuZhouPort/login_mobi";
				HttpUtil.BASE_URL + "login_mobi";
		String result = null;
		try
		{
			result = hfu.post(actionUrl, params);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询服务器时间异步类
	 * 
	 * @author Administrator
	 * 
	 */
	class queryServerTimerTask extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
			return query.GetServiceTime();
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (result != null)
			{
				parseTimerJson(result);// 获得数据				
			} else
			{
				Toast.makeText(MainActivity.this, "网络异常", Toast.LENGTH_SHORT)
						.show();

				// fragment 布局
				fragment = new MobileOfficeFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable("User", user);
				fragment.setArguments(bundle);
				// 设置 SlidingMenu 内容
				FragmentTransaction fragmentTransaction = getSupportFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(R.id.left_menu, new MenuFragment());
				fragmentTransaction.replace(R.id.right_menu,
						new RightMenuFragment());
				fragmentTransaction.replace(R.id.content, fragment);
				fragmentTransaction.commit();
			}
			super.onPostExecute(result);
		}

	}

	/**
	 * 时间json数据解析方法
	 * 
	 * @param result
	 *            json返回数据
	 */
	public void parseTimerJson(String result)
	{
		JSONTokener jsonParser = new JSONTokener(result);
		JSONObject data;
		try
		{
			data = (JSONObject) jsonParser.nextValue();
			declareTime = data.getString("serverTime");
		} catch (Exception e)
		{
			Toast.makeText(MainActivity.this, "数据获取失败", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	@SuppressLint("SimpleDateFormat")
	public int Bigtime(String time2, String time1)
	{ // 上次登录的时间 ，这次登录的时间
		// System.out.println("time2==="+time2+" time1=="+time1);
		int r = 0;
		long quot = 0;
		long quot1 = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH");
		try
		{
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot1 = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24; // 计算几天
			quot1 = quot1 % (1000 * 24 * 60 * 60) / (1000 * 60 * 60);// 计算差多少小时

			int lt = (int) quot;
			if (lt <= 14)
			{
				r = 1;
			}
			// System.out.println("quot==="+quot+" r=="+r);

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return r;
	}

	/**
	 * 自动登录逻辑判断
	 * 
	 */
	public void AutoLogin()
	{
		SharedPreferences oSharedPreferences = getSharedPreferences(SHARED_SAVE_USER_FILE_NAME, 0);
		bIsAutoLogin = oSharedPreferences.getBoolean(SHARED_SAVE_USER_IS_AUTO,false);
		//第三种情况：已成功登录过至少一次后，打开程序时
		if (user == null && bIsAutoLogin)
		{
			// 获取本地保存数据
			String savePassword = oSharedPreferences.getString(SHARED_SAVE_USER_SAVE_PASSWORD, "");
			String autoLogin = oSharedPreferences.getString(SHARED_SAVE_USER_AUTO_LOGIN, "");			

			sUserName =oSharedPreferences.getString(SHARED_SAVE_USER_USERNAME,"");
			sPassWord =oSharedPreferences.getString(SHARED_SAVE_USER_PASSWORD,"");
			
			sTime = oSharedPreferences.getString(SHARED_SAVE_USER_LAST_LOGIN_TIME, "");
			
			//declareTime 是异步获取，到此还获取不到数据，暂时不用该功能
			//int trueandflase = Bigtime(sTime, declareTime);
			// 上次登录时间超过两周,清除数据			
			if (false)
			{
				Editor oEditor = getSharedPreferences(SHARED_SAVE_USER_FILE_NAME, 0).edit();
				oEditor.clear();
				oEditor.commit();
			}
			// 登录未超过两周且用户选择自动登录，自动登录
			else if (savePassword.equals("1") && autoLogin.equals("1"))
			{
				new Logining().execute();
				return;
			}
		} 
		//第二种情况：第一次由登录界面跳转
		else if (user != null)
		{
			Editor oEditor = getSharedPreferences(SHARED_SAVE_USER_FILE_NAME, 0).edit();
			oEditor.clear();
			oEditor.putString(SHARED_SAVE_USER_USERNAME, getIntent().getStringExtra("username"));
			oEditor.putString(SHARED_SAVE_USER_PASSWORD, getIntent().getStringExtra("password"));
			oEditor.putString(SHARED_SAVE_USER_LAST_LOGIN_TIME, declareTime);
			oEditor.putBoolean(SHARED_SAVE_USER_IS_AUTO, true);
			oEditor.putString(SHARED_SAVE_USER_SAVE_PASSWORD, checkboxvalue1);
			oEditor.putString(SHARED_SAVE_USER_AUTO_LOGIN, checkboxvalue2);
			oEditor.commit();
		}

		addDefaultFragment();
	}

	/**
	 * 添加默认fragment视图
	 */
	public void addDefaultFragment()
	{
		fragment = new MobileOfficeFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("User", user);
		fragment.setArguments(bundle);
		if(user!=null)
		UserMod.name=user.getName();

		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.left_menu, new MenuFragment());
		fragmentTransaction.replace(R.id.right_menu, new RightMenuFragment());
		fragmentTransaction.replace(R.id.content, fragment);
		fragmentTransaction.commitAllowingStateLoss();
	}

	/**
	 * 后退按钮事件监听
	 */
	/*public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			appExit();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}*/
	
	@Override
	public void onBackPressed()
	{
		appExit();
	}

	/**
	 * app退出事件判断
	 */
	public void appExit()
	{
		if(CruiseMapAcitvity.isRunning)
		{
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("您有一项巡航进行中");
			builder.setCancelable(false);
			builder.setPositiveButton("后台继续", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					moveTaskToBack(true);
					cruiseNotify();
					dialog.dismiss();
					
				}
			});
			builder.setNegativeButton("查看", new OnClickListener() 
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					Intent intent=new Intent(MainActivity.this,CruiseMapAcitvity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					startActivity(intent);
					dialog.dismiss();
				}
			});
			builder.create().show();
			
			//return;
		}
		else 
		{
			if (num == 0)
			{
				firstExitTime = new Date().getTime();

				Toast.makeText(this, "再按一次返回退出湖州港航", Toast.LENGTH_SHORT).show();
				num = 1;
			}
			else if (num == 1)
			{
				// 退出前的条件判断，如有其他条件，在此添加其他处理
				// 当前有两道处理，先处理先判断两次退出时间间隔，然后判断是否还有上次文件
				if (addExitTimeJudge() && addExitFileJudge())
				{
					//System.exit(0);
					this.finish();
				}
			}

			
		}
		
		
	}

	/**
	 * 添加两次退出事件判断
	 * 
	 * @return 是否可退出
	 */
	private boolean addExitTimeJudge()
	{
		long intervalTime = new Date().getTime() - firstExitTime;

		// 时间间隔在有效范围内
		if (intervalTime < AVALABLE_EXIT_INTERVAL_TIME)
			return true;
		else
		{
			// 超出时间范围，重置计算
			num = 0;
			return false;
		}

	}

	
	private boolean addExitFileJudge()
	{
		if (UploadActivity.tool != null
				&& UploadActivity.tool.getFaileUpFile().size() > 0)
		{
			ManagerDialog.messageDialog(this, "提示", "尚有文件还未上传成功，请确认操作?",
					"直接退出", new OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							System.exit(0);
						}
					}, "查看", new OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							Intent i = new Intent(MainActivity.this,
									UploadActivity.class);
							startActivity(i);
						}
					}).show();

			return false;
		} else
			return true;
	}
	
	private void cruiseNotify()
	{
		NotificationManager notifym=(NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
		 Notification.Builder buider=new Notification.Builder(this);
		 buider.setSmallIcon(R.drawable.leave_img);
		 buider.setContentTitle("您有一项巡航进行中");
		 buider.setContentText("");
		 buider.setTicker("您有一项巡航进行中");//第一次提示消息的时候显示在通知栏上
		 buider.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.drawer_logo));
		 buider.setDefaults(Notification.DEFAULT_SOUND);
		 //mBuilder.setAutoCancel(true);//自己维护通知的消失
		 
		 
		 Intent intent=new Intent(this,MainActivity.class);
		 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 PendingIntent pi=PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 buider.setContentIntent(pi);		 
		 Notification note=buider.getNotification();
		 notifym.notify(1001, note);
	}

}
