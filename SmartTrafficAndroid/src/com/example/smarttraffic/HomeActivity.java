package com.example.smarttraffic;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.about.AboutActivity;
import com.example.smarttraffic.common.suggestion.SuggestionNewActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.system.CheckVersion;
import com.example.smarttraffic.user.UserControl;
import com.example.smarttraffic.user.UserLoginActivity;
import com.example.smarttraffic.user.UserMainActivity;
import com.example.smarttraffic.util.StartIntent;

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 禾行通主页面
 * 
 * @author Administrator zhou
 * 
 */
public class HomeActivity extends FragmentActivity
{

	ListView leftItemListView; // 侧栏
	ListLeftItemAdapter listLeftItemAdapter; // 侧栏适配器

	DrawerLayout drawerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);

		initLeftItem();
		initFragment(savedInstanceState);

		drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);

		HeadFragment fragment = new HeadFragment();
		fragment.setImageDrawableID(R.drawable.selector_main_left);
		fragment.setClickListener(new OpenLeftItemClick());
		fragment.setTitleName(getResources().getString(R.string.string_app_name));

		ManagerFragment.replaceFragment(this, R.id.main_head, fragment);
	}

	/**
	 * 点击打开左侧栏事件
	 * 
	 * @author Administrator
	 * 
	 */
	class OpenLeftItemClick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			drawerLayout.openDrawer(Gravity.LEFT);
		}
	}

	/**
	 * 初始化左侧栏内容 计算左侧栏宽度。2/3
	 */
	private void initLeftItem()
	{
		leftItemListView = (ListView) findViewById(R.id.left_drawer);

		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		ViewGroup.LayoutParams params = leftItemListView.getLayoutParams();
		params.width = width * 2 / 3;
		leftItemListView.setLayoutParams(params);

		listLeftItemAdapter = new ListLeftItemAdapter(this);

		leftItemListView.setAdapter(listLeftItemAdapter);
		leftItemListView.setOnItemClickListener(leftItemClickListener);
	}

	/**
	 * 加载主界面fragment
	 * 
	 * @param savedInstanceState
	 *            保存实例
	 */
	private void initFragment(Bundle savedInstanceState)
	{
		if (savedInstanceState == null)
		{
			ManagerFragment.replaceFragment(this, R.id.container,
					new HomeFragment());
		}
	}

	/**
	 * 左侧栏选项点击事件 0:个人中心 1:投诉 2:建议 3:分析 4:版本更新 5:关于 6:帮助 7:退出
	 */
	OnItemClickListener leftItemClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			switch (position)
			{
			case 0:
				if (UserControl.getUser() == null)
					StartIntent.startIntent(HomeActivity.this,
							UserLoginActivity.class);
				else
				{
					Bundle bundle = new Bundle();
					bundle.putSerializable(UserLoginActivity.USER_DATA,
							UserControl.getUser());

					StartIntent.startIntentWithParam(HomeActivity.this,
							UserMainActivity.class, bundle);

				}
				break;
			// case 1:
			// Bundle complainBundle = new Bundle();
			// complainBundle.putInt(ComplainActivity.COMPLAIN_NAME, 0);
			// StartIntent.startIntentWithParam(HomeActivity.this,
			// ComplainActivity.class, complainBundle);
			// break;
			case 1:

				StartIntent.startIntent(HomeActivity.this,
						SuggestionNewActivity.class);
				break;

			case 2:
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "好友推荐");
				intent.putExtra(Intent.EXTRA_TEXT, "您的好友向您推荐一款应用——禾行通,下载地址为"
						+ HttpUrlRequestAddress.UPDATE_DOWNLOAD_VERSION);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(Intent.createChooser(intent, getTitle()));

				break;
			case 3:
				new CheckVersion(HomeActivity.this).check();
				break;

			case 4:
				Bundle bundle = new Bundle();
				bundle.putInt(AboutActivity.ABOUT_ID, 0);
				StartIntent.startIntentWithParam(HomeActivity.this,
						AboutActivity.class, bundle);
				break;

			// case 4:
			// StartIntent.startIntent(HomeActivity.this, HelpActivity.class);
			// break;
			case 5:
				System.exit(0);
				break;
			}
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}

}
