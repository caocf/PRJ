package com.huzhouport.slidemenu;

import java.util.ArrayList;
import java.util.HashMap;
import net.hxkg.channel.Channel1Activity;
import net.hxkg.channel.CruiseMapAcitvity;
import com.example.huzhouport.R;
import com.huzhouport.electricreport.ElectricReportNewList;
import com.huzhouport.abnormal.AbNormalList;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.dangerousgoodsjob.DangerousgoodsjobList;
import com.huzhouport.dangerousgoodsportln.DangerousgoodsportlnList;
import com.huzhouport.illegal.IllegalAdd;
import com.huzhouport.illegal.IllegalCheckSearch;
import com.huzhouport.main.User;
import com.huzhouport.patrol.PatrolAdd;
import com.huzhouport.pushmsg.IllegalPushMsgManager;
import com.huzhouport.pushmsg.PushMsg;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("HandlerLeak")
public class BussinessProcessingFragment extends Fragment
{
	GridView _gridView1;
	ImageButton titleButton;
	private SimpleAdapter adapter;
	private User user;

	private Integer[] images = { // 九宫格图片的设置
	R.drawable.mm_electricreport, R.drawable.mm_dangerousgoodsreportin,
			R.drawable.mm_dangerousgoodsjobcheck,
			R.drawable.mm_illegalevidence, R.drawable.mm_shipillegalcheck,
			R.drawable.mm_wharfpatrol/*, R.drawable.mm_cruise */,R.drawable.ic_yichangxunluo_normal,R.drawable.ic_home_mobilecruising};
	private String[] texts = { // 九宫格图片下方文字的设置
	"航行电子报告信息", "危险品进港审批", "危险货物码头作业信息", "违章取证", "违章审核", "码头巡查"/*, "巡航"*/
	,"智慧监管执勤","移动巡航"};
	private Integer[] permissionCode = { 15, 16, 17, 18, 32, 18, /*19,*/20,21 };
	private Class<?>[] actionPage = 
	{ 
		ElectricReportNewList.class,
		DangerousgoodsportlnList.class, DangerousgoodsjobList.class,
		IllegalAdd.class, IllegalCheckSearch.class, PatrolAdd.class,
		/*CruiselogList.class,*/AbNormalList.class,Channel1Activity.class 
	};
	ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);

		super.onCreate(savedInstanceState);
	}

	private void refreshIlleagePushMsg()
	{
		if (user != null)
		{
			new AsyncTask<Void, Void, Integer>()
			{
				@Override
				protected Integer doInBackground(Void... param)
				{
					int msgcnt = new IllegalPushMsgManager()
							.getPushedUnReadMsgCnt(user.getUserId());
					return msgcnt;
				}

				protected void onPostExecute(Integer result)
				{
					int unreadcnt = result;
					// 找到通知公告，并更新界面
					for (int i = 0; i < lst.size(); i++)
					{
						HashMap<String, Object> map = lst.get(i);
						if (map.get("itemText").equals("违章审核"))
						{
							if (unreadcnt > 0)
							{
								map.put("itemImage",
										R.drawable.shipillegalcheck_unread);
							} else
							{
								map.put("itemImage",
										R.drawable.mm_shipillegalcheck);
							}
						}
						if (adapter != null)
							adapter.notifyDataSetChanged();
					}
				}
			}.execute();
		}
	}

	/**
	 * 消息通知接收者
	 */
	private NotifyBroadcastReceiver notifyBroadcastReceiver = null;

	public class NotifyBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			int moduler = intent.getIntExtra("moduler", -1);

			if (moduler == PushMsg.MODULERID_ILLEGAL)
			{
				refreshIlleagePushMsg();
			}
		}

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

		//refreshIlleagePushMsg();
		freshCruise();
		super.onResume();
	}

	@Override
	public void onPause()
	{
		// 挂起是取消消息通知接受
		getActivity().unregisterReceiver(notifyBroadcastReceiver);
		super.onPause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.activity_main, container,
				false);

		_gridView1 = (GridView) rootView.findViewById(R.id.gridView1);
		Bundle bundle = getArguments();
		if (bundle != null)
		{
			user = (User) bundle.getSerializable("User");
			if (user != null)
			{
				for (int i = 0; i < texts.length; i++)
				{
					loop: // 跳出循环用的
					for (int j = 0; j < user.getRpList().size(); j++)
					{
						if (permissionCode[i] == user.getRpList().get(j)
								.getPermissionId()
								&& user.getRpList().get(j).getStatus() == 1)
						{
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("itemImage", images[i]);
							map.put("itemText", texts[i]);
							map.put("actionPage", actionPage[i]);
							lst.add(map);
							break loop;
						}
					}
				}

				adapter = new SimpleAdapter(getActivity(), lst,
						R.layout.menulist, new String[] { "itemImage",
								"itemText" }, new int[] {
								R.id.imageView_ItemImage,
								R.id.textView_ItemText });

				_gridView1.setAdapter(adapter);
				_gridView1
						.setOnItemClickListener(new gridView1OnClickListener());
			}
		}
		return rootView;
	}

	class gridView1OnClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			Intent intent = null;
			intent = new Intent(getActivity(), (Class<?>) lst.get(arg2).get(
					"actionPage"));
			intent.putExtra("User", user);
			startActivity(intent);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_share:
			Toast.makeText(getActivity(), R.string.action_share,
					Toast.LENGTH_SHORT).show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public Boolean CheckPermission(int pID)
	{
		if (pID == 1)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 15)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		} else if (pID == 2)
		{

			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 16)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		} else if (pID == 3)
		{

			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 17)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		} else if (pID == 4)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 18)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		} else if (pID == 5)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 32)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}
		} else if (pID == 6)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 19)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		} else if (pID == 7)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 20)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		}
		return true;
	}
	
	private void freshCruise() 
	{
		
			for (int i = 0; i < lst.size(); i++) {
				HashMap<String, Object> map = lst.get(i);
				if (map.get("itemText").equals("移动巡航")) 
				{
					if(CruiseMapAcitvity.isRunning)
					{
						map.put("itemImage", R.drawable.ic_home_mobilecruising_news);
					}
					else {
						map.put("itemImage", R.drawable.ic_home_mobilecruising);
					}	
					
				}
				if (adapter != null)
					adapter.notifyDataSetChanged();
			}
	}
	
}
