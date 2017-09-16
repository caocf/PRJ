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
 * ������Activity
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends SlidingFragmentActivity implements SLMenuListOnItemClickListener
{
	/*---------------------IP sharepreference������Ϣ--------------------------*/

	/**
	 * sharepreference IP��Ϣ�����ļ���
	 */
	public static final String SHARED_SAVE_CONNECT_FILE_NAME = "IpData";

	/**
	 * sharepreference ����IP
	 */
	public static final String SHARED_SAVE_CONNECT_IP = "Ip1";

	/**
	 * sharepreference ���Ӷ˿�
	 */
	public static final String SHARED_SAVE_CONNECT_PORT = "Port1";

	/**
	 * sharepreference �Ƿ��״α���
	 */
	public static final String SHARED_SAVE_CONNECT_FIRST = "IsOne";

	/**
	 * sharepreference ��Ϣ����ʱ��
	 */
	public static final String SHARED_SAVE_PUSH_MSG_TIMER = "PushMsgTimer";

	/*--------------------�û� sharepreference ������Ϣ---------------------------------*/

	/**
	 * sharepreference �û���Ϣ�����ļ���
	 */
	public static final String SHARED_SAVE_USER_FILE_NAME = "UserData";

	/**
	 * sharepreference �û��Ƿ��Զ���¼
	 */
	public static final String SHARED_SAVE_USER_IS_AUTO = "IsAutoLogin";

	/**
	 * sharepreference �û���
	 */
	public static final String SHARED_SAVE_USER_USERNAME = "Username";

	/**
	 * sharepreference ����
	 */
	public static final String SHARED_SAVE_USER_PASSWORD = "Password";

	/**
	 * sharepreference �ϴε�¼ʱ��
	 */
	public static final String SHARED_SAVE_USER_LAST_LOGIN_TIME = "DeclareTime";

	/**
	 * sharepreference ��������
	 */
	public static final String SHARED_SAVE_USER_SAVE_PASSWORD = "checkboxvalue1";

	/**
	 * sharepreference �Զ���¼
	 */
	public static final String SHARED_SAVE_USER_AUTO_LOGIN = "checkboxvalue2";

	/*--------------------�˳���ز���----------------------------*/

	/**
	 * ��һ�ΰ����ص�ʱ��
	 */
	private long firstExitTime = -1;

	/**
	 * ���η��ؼ����Чʱ��
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
		tv_title.setText("�ƶ��칫");
		setBehindContentView(R.layout.frame_left_menu);
		
		user = (User) getIntent().getSerializableExtra("User");
		checkboxvalue1 = getIntent().getStringExtra("checkboxvalue1");
		checkboxvalue2 = getIntent().getStringExtra("checkboxvalue2");

		// ��ȡ������ʱ��
		new queryServerTimerTask().execute();
		//�Զ���¼
		AutoLogin();
		// ��ʼ�����û���������
		initSlidingMenu();

		// �˳���ť���´���
		num = 0;

		// ��ʼ���ж�����IP
		initIPConnect();

		// ��Ӱ�ť�����¼�
		addBtnListener();

		// ���汾����
		update(null);

		Intent regIntent = new Intent(MainActivity.this,ServerPushService.class);
		regIntent.putExtra("User", user);
		MainActivity.this.startService(regIntent);
		//�ٶȵ�ͼ��ʼ��
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
	 * ��ʼ�����û���������
	 */
	public void initSlidingMenu()
	{
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// �������Ҷ����Ի���SlidingMenu�˵�
		mSlidingMenu.setSecondaryMenu(R.layout.frame_right_menu); // �����Ҳ�˵��Ĳ����ļ�
		mSlidingMenu.setSecondaryShadowDrawable(R.drawable.drawer_shadow);

		mSlidingMenu.setShadowDrawable(R.drawable.drawer_shadow);// ������ӰͼƬ
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width); // ������ӰͼƬ�Ŀ��
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset); // SlidingMenu����ʱ��ҳ����ʾ��ʣ����
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}

	/**
	 * ��ʼ���ж�����IP �ж��Ƿ��״λ�ȡ�� ȷ��IP��Ĭ��ֵ���Ǳ����޸ĵ�ֵ
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
					+ HttpUtil.BASE_URL_PORT + "/HuZhouPort/"; // iP ��ֵ*/
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
	 * ��Ӱ�ť�����¼�
	 */
	public void addBtnListener()
	{
		ivTitleBtnLeft = (ImageButton) this.findViewById(R.id.ivTitleBtnLeft);
		ivTitleBtnLeft.setOnClickListener(new showMenu());

		ivTitleBtnLogin = (ImageButton) this.findViewById(R.id.ivTitleBtnlogin);
		ivTitleBtnLogin.setOnClickListener(new login());
	}

	/**
	 * ������򿪰�ť�����¼� ���������˵�
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
	 * �����¼��ť�¼�
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
	 * �������¼� ����������µİ汾
	 * 
	 * @param view
	 */
	public void update(View view)
	{
		new CheckVersion(true, false, this).check();
	}

	/**
	 * �����ѡ��ѡ���¼�
	 */
	@Override
	public void selectItem(int position, String title)
	{
		Fragment fragment = null;
		switch (position)
		{
		case 0:
			fragment = new MobileOfficeFragment();
			tv_title.setText("�ƶ��칫");
			break;
		case 1:
			if (user == null)
			{
				Toast.makeText(MainActivity.this, "���ȵ�¼", Toast.LENGTH_SHORT)
						.show();
				break;
			} else
			{
				fragment = new BussinessProcessingFragment();
				tv_title.setText("����ִ��");
				break;
			}
		case 2:
			if (user == null)
			{
				Toast.makeText(MainActivity.this, "���ȵ�¼", Toast.LENGTH_SHORT)
						.show();
				break;
			} else
			{
				fragment = new IntegratedQueryFragment();
				tv_title.setText("�ۺϲ�ѯ");
				break;
			}
		case 3:
			if (user != null)
			{
				fragment = new PhoneBookFragment();
				tv_title.setText("ͨѶ¼");
			} else
				Toast.makeText(MainActivity.this, "���ȵ�¼", Toast.LENGTH_SHORT)
						.show();

			break;

		case 4:
			fragment = new SettingFragment();
			tv_title.setText("����");
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
	 * �û���¼�߳�
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
					Toast.makeText(MainActivity.this, "�Զ���¼ʧ�ܣ��û�����ɾ��",
							Toast.LENGTH_LONG).show();
					break;

				case 3:
					Toast.makeText(MainActivity.this, "�Զ���¼ʧ�ܣ���������޸�",
							Toast.LENGTH_LONG).show();
					break;

				case 0:
					Toast.makeText(MainActivity.this, "�Զ���¼ʧ��",
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
	 * ��ȡ�������û���Ϣ
	 * 
	 * @return ��¼���״̬
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
					// Ȩ��
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
	 * �û���¼����
	 * 
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return ��¼�������JSonֵ
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
	 * ��ѯ������ʱ���첽��
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
				parseTimerJson(result);// �������				
			} else
			{
				Toast.makeText(MainActivity.this, "�����쳣", Toast.LENGTH_SHORT)
						.show();

				// fragment ����
				fragment = new MobileOfficeFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable("User", user);
				fragment.setArguments(bundle);
				// ���� SlidingMenu ����
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
	 * ʱ��json���ݽ�������
	 * 
	 * @param result
	 *            json��������
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
			Toast.makeText(MainActivity.this, "���ݻ�ȡʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	@SuppressLint("SimpleDateFormat")
	public int Bigtime(String time2, String time1)
	{ // �ϴε�¼��ʱ�� ����ε�¼��ʱ��
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
			quot = quot / 1000 / 60 / 60 / 24; // ���㼸��
			quot1 = quot1 % (1000 * 24 * 60 * 60) / (1000 * 60 * 60);// ��������Сʱ

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
	 * �Զ���¼�߼��ж�
	 * 
	 */
	public void AutoLogin()
	{
		SharedPreferences oSharedPreferences = getSharedPreferences(SHARED_SAVE_USER_FILE_NAME, 0);
		bIsAutoLogin = oSharedPreferences.getBoolean(SHARED_SAVE_USER_IS_AUTO,false);
		//������������ѳɹ���¼������һ�κ󣬴򿪳���ʱ
		if (user == null && bIsAutoLogin)
		{
			// ��ȡ���ر�������
			String savePassword = oSharedPreferences.getString(SHARED_SAVE_USER_SAVE_PASSWORD, "");
			String autoLogin = oSharedPreferences.getString(SHARED_SAVE_USER_AUTO_LOGIN, "");			

			sUserName =oSharedPreferences.getString(SHARED_SAVE_USER_USERNAME,"");
			sPassWord =oSharedPreferences.getString(SHARED_SAVE_USER_PASSWORD,"");
			
			sTime = oSharedPreferences.getString(SHARED_SAVE_USER_LAST_LOGIN_TIME, "");
			
			//declareTime ���첽��ȡ�����˻���ȡ�������ݣ���ʱ���øù���
			//int trueandflase = Bigtime(sTime, declareTime);
			// �ϴε�¼ʱ�䳬������,�������			
			if (false)
			{
				Editor oEditor = getSharedPreferences(SHARED_SAVE_USER_FILE_NAME, 0).edit();
				oEditor.clear();
				oEditor.commit();
			}
			// ��¼δ�����������û�ѡ���Զ���¼���Զ���¼
			else if (savePassword.equals("1") && autoLogin.equals("1"))
			{
				new Logining().execute();
				return;
			}
		} 
		//�ڶ����������һ���ɵ�¼������ת
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
	 * ���Ĭ��fragment��ͼ
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
	 * ���˰�ť�¼�����
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
	 * app�˳��¼��ж�
	 */
	public void appExit()
	{
		if(CruiseMapAcitvity.isRunning)
		{
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("����һ��Ѳ��������");
			builder.setCancelable(false);
			builder.setPositiveButton("��̨����", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					moveTaskToBack(true);
					cruiseNotify();
					dialog.dismiss();
					
				}
			});
			builder.setNegativeButton("�鿴", new OnClickListener() 
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

				Toast.makeText(this, "�ٰ�һ�η����˳����ݸۺ�", Toast.LENGTH_SHORT).show();
				num = 1;
			}
			else if (num == 1)
			{
				// �˳�ǰ�������жϣ����������������ڴ������������
				// ��ǰ�����������ȴ������ж������˳�ʱ������Ȼ���ж��Ƿ����ϴ��ļ�
				if (addExitTimeJudge() && addExitFileJudge())
				{
					//System.exit(0);
					this.finish();
				}
			}

			
		}
		
		
	}

	/**
	 * ��������˳��¼��ж�
	 * 
	 * @return �Ƿ���˳�
	 */
	private boolean addExitTimeJudge()
	{
		long intervalTime = new Date().getTime() - firstExitTime;

		// ʱ��������Ч��Χ��
		if (intervalTime < AVALABLE_EXIT_INTERVAL_TIME)
			return true;
		else
		{
			// ����ʱ�䷶Χ�����ü���
			num = 0;
			return false;
		}

	}

	
	private boolean addExitFileJudge()
	{
		if (UploadActivity.tool != null
				&& UploadActivity.tool.getFaileUpFile().size() > 0)
		{
			ManagerDialog.messageDialog(this, "��ʾ", "�����ļ���δ�ϴ��ɹ�����ȷ�ϲ���?",
					"ֱ���˳�", new OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							System.exit(0);
						}
					}, "�鿴", new OnClickListener()
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
		 buider.setContentTitle("����һ��Ѳ��������");
		 buider.setContentText("");
		 buider.setTicker("����һ��Ѳ��������");//��һ����ʾ��Ϣ��ʱ����ʾ��֪ͨ����
		 buider.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.drawer_logo));
		 buider.setDefaults(Notification.DEFAULT_SOUND);
		 //mBuilder.setAutoCancel(true);//�Լ�ά��֪ͨ����ʧ
		 
		 
		 Intent intent=new Intent(this,MainActivity.class);
		 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 PendingIntent pi=PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 buider.setContentIntent(pi);		 
		 Notification note=buider.getNotification();
		 notifym.notify(1001, note);
	}

}
