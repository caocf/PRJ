package com.huzhouport.slidemenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import net.hxkg.channel.HttpRequest;

import com.example.huzhouport.R;
import com.huzhouport.abnormal.PushAbInfo;
import com.huzhouport.car.CarHomeActivity;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;
import com.huzhouport.common.util.PackageInstaller;
import com.huzhouport.equipment.WebActivity;
import com.huzhouport.knowledge.KnowledgeNewView;
import com.huzhouport.main.User;
import com.huzhouport.portdynmicnews.ComprehensiveMain;
import com.huzhouport.portdynmicnews.PortdynmicnewsActivity;
import com.huzhouport.pushmsg.NotifyPushMsgManger;
import com.huzhouport.pushmsg.PushMsg;
import com.huzhouport.schedule.CalendarActivity;
import com.huzhouport.schedule.ScheduleItemTime;
import com.huzhouport.slidemenu.MainActivity.login;
import com.hxkg.meeting.MeetingRoomHomeActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 移动办公界面
 * 
 * @author Administrator
 * 
 */
public class MobileOfficeFragment extends Fragment 
{
	private SimpleAdapter nineAdapter = null;
	private GridView _gridView1;
	private User user;	
	SimpleDateFormat sdDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 登录用户九宫格内容图片
	 */
	private Integer[] images = 
	{ 
		R.drawable.mm_portdynamic,
		/*R.drawable.mm_industry,*/ R.drawable.mm_notice,
		/*R.drawable.mm_leaveandovertime,*/ R.drawable.mm_schedule,
		R.drawable.mm_acknowledge, R.drawable.mm_scheduleadd,
		R.drawable.videomoditor, R.drawable.icon_ctrix ,
		R.drawable.videomoditor,R.drawable.home_ic_meeting,R.drawable.home_ic_car
	};
	/**
	 * 非登录用户九宫格内容图片
	 */
	private Integer[] images1 = { R.drawable.mm_portdynamic,
			/*R.drawable.mm_industry,*/ R.drawable.mm_notice };

	/**
	 * 登录用户九宫格内容文字
	 */
	private String[] texts = { "港航动态",/* "行业资讯", */"通知公告",/* "考勤管理",*/ "日程安排", "知识库",
			"公务通知", "视频监控", "办公自动化" ,"我的设备","会议室管理","用车管理"};

	/**
	 * 非登录用户九宫格内容文字
	 */
	private String[] texts1 = { "港航动态", /*"行业资讯",*/ "通知公告" };

	/**
	 * 权限
	 */
	private Integer[] permissionCode = { 88, /*88,*/ 88,/* 10,*/ 12, 13, 12, 37, 38,39,88 ,88};

	/**
	 * 九宫格跳转页面地址
	 */
	private Class<?>[] actionPage = 
	{ 
		PortdynmicnewsActivity.class,
		/*IndustryInfoActivity.class,*/ KnowledgeNewView.class,
		/*leaveandovertimeNewListMain.class, */CalendarActivity.class,
		ComprehensiveMain.class, ScheduleItemTime.class,
		MainActivity.class, login.class,WebActivity.class,MeetingRoomHomeActivity.class,CarHomeActivity.class 
	};

	ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();

	/**
	 * VMS APK包名
	 */
	private final String PACKAGE_VMS = "com.android.iVMS_5060";

	/**
	 * VMS本地资源名
	 */
	private final String ASSETS_VMS = "iVMS-5060(Android) V2.00.01 build20111210.apk";

	/**
	 * CITRIX APK包名
	 */
	private final String PACKAGE_CITRIX = "com.citrix.Receiver";

	/**
	 * CITRIX本地资源名
	 */
	private final String ASSETS_CITRIX = "EMASS-market-upload_3.9.apk";

	
	// 找到通知公告，并更新界面
	public void refreshnotifymsg() 
	{
		new AsyncTask<Void, Void, Integer>() 
		{
			@Override
			protected Integer doInBackground(Void... param) 
			{
				int notifymsgcnt = new NotifyPushMsgManger(getActivity()).getUserUnreadNotifyCnt(user);
				return notifymsgcnt;
			}

			protected void onPostExecute(Integer result) 
			{
				int unreadcnt = result;
				
				for (int i = 0; i < lst.size(); i++) 
				{
					HashMap<String, Object> map = lst.get(i);
					if (map.get("itemText").equals("通知公告")) 
					{
						if (unreadcnt > 0)
						{
							map.put("itemImage", R.drawable.notifyunread);
						} else 
						{
							map.put("itemImage", R.drawable.mm_notice);
						}
					}
					nineAdapter.notifyDataSetChanged();
				}
			}
		}.execute();
	}

	/*// 9宫格检查请假加班未读通知信息
	public void refreshlaomsg() {
		if (user != null) {
			new AsyncTask<Void, Void, Integer>() {
				@Override
				protected Integer doInBackground(Void... param) {
					int msgcnt = new LAOPushMsgManager()
							.getPushedUnReadMsgCnt(user.getUserId());
					return msgcnt;
				}

				protected void onPostExecute(Integer result) {
					int msgcnt = result;
					// 找到请假加班，并更新界面
					for (int i = 0; i < lst.size(); i++) {
						HashMap<String, Object> map = lst.get(i);
						if (map.get("itemText").equals("考勤管理")) {
							if (msgcnt > 0) {
								map.put("itemImage", R.drawable.overtimeunread);
							} else {
								map.put("itemImage",
										R.drawable.mm_leaveandovertime);
							}
						}
						if (nineAdapter != null)
							nineAdapter.notifyDataSetChanged();
					}
				}
			}.execute();
		}
	}

		

	public void refreshindustryinfo() 
	{
		new AsyncTask<Void, Void, Integer>() {
			@Override
			protected Integer doInBackground(Void... param) {
				int msgcnt = new IndustryInfoPushMsgManager(getActivity())
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
					if (nineAdapter != null)
						nineAdapter.notifyDataSetChanged();
				}
			}
		}.execute();
	}*/

	public class NotifyBroadcastReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			int moduler = intent.getIntExtra("moduler", -1);

			if (moduler == PushMsg.MODULERID_NOTIFY)
				refreshnotifymsg();

			/*if (moduler == PushMsg.MODULERID_LEAVEANDOVERTIME)
				//refreshlaomsg();

			if (moduler == PushMsg.MODULERID_MEETING)refreshmeetingmsg();

			if (moduler == PushMsg.MODULERID_INDUSTRYINFO)
				//refreshindustryinfo();*/
		}
	}
	
	// 9宫格检查会议安排未读通知信息
	public void refreshmeetingmsg() 
	{
		if (user == null) return;
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() {
			
			@Override
			public void onSuccess(String result) 
			{
				
				try {
					JSONObject jsonObject=new JSONObject(result.trim());
					int count=jsonObject.getInt("userid_count");
					// 找到公务通知，并更新界面
					for (int i = 0; i < lst.size(); i++) {
						HashMap<String, Object> map = lst.get(i);
						if (map.get("itemText").equals("公务通知")) {
							if (count > 0) {
								map.put("itemImage", R.drawable.home_ic_work_message);
							} else {
								map.put("itemImage", R.drawable.mm_scheduleadd);
							}
						}
						if (nineAdapter != null)
							nineAdapter.notifyDataSetChanged();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("userid_count", user.getUserId());
		map.put("scheduleEvent.eventTime", sdDateFormat.format(new Date()));
		httpRequest.post(HttpUtil.BASE_URL +"unfeedCount", map);
	}
	//会议室申请
	public void refreshMeetingRoom() 
	{
		if (user == null) return;
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult() {
			
			@Override
			public void onSuccess(String result) 
			{
				
				try {
					JSONObject jsonObject=new JSONObject(result.trim());
					int ap=jsonObject.getInt("ap");
					int check=jsonObject.getInt("check");
					int officecheck=jsonObject.getInt("officecheck");
					
					// 找到会议室，并更新界面
					for (int i = 0; i < lst.size(); i++) 
					{
						HashMap<String, Object> map = lst.get(i);
						if (map.get("itemText").equals("会议室管理")) {
							if (ap+check+officecheck>0) //有提示消息
							{
								map.put("itemImage", R.drawable.home_ic_meeting_message);
							} else {
								map.put("itemImage", R.drawable.home_ic_meeting);
							}
						}
						if (nineAdapter != null)
							nineAdapter.notifyDataSetChanged();
					}
						
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
		});
		Map<String, Object> map=new HashMap<>();
		map.put("user.userId", user.getUserId());
		httpRequest.post(HttpUtil.BASE_URL +"countAp", map);
	}

	/**
	 * 消息通知接收者
	 */
	private NotifyBroadcastReceiver notifyBroadcastReceiver = null;

	@Override
	public void onPause() 
	{
		// 挂起是取消消息通知接受
		getActivity().unregisterReceiver(notifyBroadcastReceiver);
		super.onPause();
	}

	@Override
	public void onResume() 
	{
		// 恢复时开启消息通知接收
		IntentFilter filter = new IntentFilter();
		if (notifyBroadcastReceiver == null)
			notifyBroadcastReceiver = new NotifyBroadcastReceiver();

		filter.addAction(GlobalVar.PUSHMSGBROADCAST);
		getActivity().registerReceiver(notifyBroadcastReceiver, filter);
		refreshnotifymsg();
		refreshmeetingmsg();
		refreshMeetingRoom();
		/*refreshlaomsg();
		refreshmeetingmsg();
		refreshindustryinfo();*/
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.activity_main, container,false);

		_gridView1 = (GridView) rootView.findViewById(R.id.gridView1);		

		Bundle bundle = getArguments();
		if (bundle == null) return rootView;
		user = (User) bundle.getSerializable("User");
		if (user != null) 
		{
			for (int i = 0; i < images.length; i++) 
			{
				if (permissionCode[i] == 88) 
				{
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("itemImage", images[i]);
					map.put("itemText", texts[i]);
					map.put("textView_read", "");
					map.put("actionPage", actionPage[i]);
					lst.add(map);
				} 
				else 
				{
					for (int j = 0; j < user.getRpList().size(); j++) 
					{
						if (permissionCode[i] == user.getRpList().get(j)
								.getPermissionId()
								&& user.getRpList().get(j).getStatus() != 0) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("itemImage", images[i]);
							map.put("itemText", texts[i]);
							map.put("textView_read", "");
							map.put("actionPage", actionPage[i]);
							lst.add(map);
							break;
						}
					}
				}
			}

			nineAdapter = new SimpleAdapter(getActivity(), lst,
					R.layout.menulist_messagenumber, new String[] {
							"itemImage", "itemText", "textView_read" },
					new int[] { R.id.imageView_ItemImage,
							R.id.textView_ItemText, R.id.textView_read });

			_gridView1.setAdapter(nineAdapter);
			_gridView1.setOnItemClickListener(new gridView1OnClickListener());
			
			/*Intent regIntent = new Intent(getActivity(), ServerPushService.class);
			regIntent.putExtra("User", user);
			getActivity().startService(regIntent);*/
			
			Intent intent=new Intent(getActivity(),PushAbInfo.class);
			intent.putExtra("User", user);
			getActivity().startService(intent);
		} 
		else 
		{
			for (int i = 0; i < texts1.length; i++) 
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("itemImage", images1[i]);
				map.put("itemText", texts1[i]);

				map.put("textView_read", "");

				lst.add(map);
			}

			nineAdapter = new SimpleAdapter(getActivity(), lst,
					R.layout.menulist_messagenumber, new String[] {
							"itemImage", "itemText", "textView_read" },
					new int[] { R.id.imageView_ItemImage,
							R.id.textView_ItemText, R.id.textView_read });

			_gridView1.setAdapter(nineAdapter);
			_gridView1.setOnItemClickListener(new gridView1OnClickListener1());
		}
		
		return rootView;
	}	

	/**
	 * 非登录用户九宫格按钮事件
	 * 
	 * @author Administrator
	 * 
	 */
	class gridView1OnClickListener1 implements OnItemClickListener 
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
		{
			Intent intent = null;
			switch (arg2)
			{
			case 0:

				intent = new Intent(getActivity(), PortdynmicnewsActivity.class);
				startActivity(intent);
				break;
			/*case 1:
				intent = new Intent(getActivity(), IndustryInfoActivity.class);
				startActivity(intent);
				break;*/
			case 1:

				intent = new Intent(getActivity(), KnowledgeNewView.class);
				intent.putExtra("departmentId", "-1");
				startActivity(intent);
				break;
			case 2:
				if (!PackageInstaller.checkApkExist(getActivity(), PACKAGE_VMS))
					PackageInstaller.installFromAsset(getActivity(),
							ASSETS_VMS, "/sm/", "test.apk");
				break;
			case 3:
				if (!PackageInstaller.checkApkExist(getActivity(),
						PACKAGE_CITRIX))
					PackageInstaller.installFromAsset(getActivity(),
							ASSETS_CITRIX, "/sm/", "test.apk");
				break;

			}
		}
	}

	/**
	 * 登录用户九宫格按钮事件
	 * 
	 * @author Administrator
	 * 
	 */
	class gridView1OnClickListener implements OnItemClickListener 
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{
			Intent intent = null;
			if ((Class<?>) lst.get(arg2).get("actionPage") == MainActivity.class) {
				if (!PackageInstaller.checkApkExist(getActivity(), PACKAGE_VMS))
					PackageInstaller.installFromAsset(getActivity(),
							ASSETS_VMS, "/sm/", "test.apk");

			} else if ((Class<?>) lst.get(arg2).get("actionPage") == login.class) {
				if (!PackageInstaller.checkApkExist(getActivity(),
						PACKAGE_CITRIX))
					PackageInstaller.installFromAsset(getActivity(),
							ASSETS_CITRIX, "/sm/", "test.apk");
			} else {
				intent = new Intent(getActivity(), (Class<?>) lst.get(arg2)
						.get("actionPage"));
				intent.putExtra("User", user);
				intent.putExtra("departmentId", "-1");
				startActivity(intent);
			}
		}
	}

}
