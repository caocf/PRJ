package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFragment;
import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.alarm.AlarmSearchActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.MapSelectPointActivity;
import com.example.smarttraffic.nearby.NearByActivity;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;
import com.example.smarttraffic.smartBus.fragment.SmartBusLineFragment;
import com.example.smarttraffic.smartBus.fragment.SmartBusStationFragment;
import com.example.smarttraffic.smartBus.fragment.SmartBusTransferFragment;
import com.example.smarttraffic.util.StartIntent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 智慧公交主页
 * 
 * @author Administrator zhou
 * 
 */
public class SmartBusHomeActivity extends FragmentActivity
{
	private ViewPager contentPager;
	TextView transferTextView;
	TextView lineTextView;
	TextView stationTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bus_home);

		initHead();
		initView();

		initViewPager(0);
	}

	/**
	 * 初始化头部内容
	 */
	private void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("智慧公交");
		fragment.setRightName("收藏夹");
		fragment.setRightListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				StartIntent.startIntent(SmartBusHomeActivity.this,SmartBusFavorActivity.class);
			}
		});
		ManagerFragment.replaceFragment(this, R.id.smart_bus_main_head,
				fragment);
	}

	/**
	 * 初始化视图
	 */
	private void initView()
	{
		transferTextView = (TextView) findViewById(R.id.smart_bus_main_transfer);
		lineTextView = (TextView) findViewById(R.id.smart_bus_main_line);
		stationTextView = (TextView) findViewById(R.id.smart_bus_main_station);
	}

	/**
	 * 选项卡事件
	 * 
	 * @param v
	 */
	public void select(View v)
	{
		switch (v.getId())
		{
		case R.id.smart_bus_main_clock:
			StartIntent.startIntent(this, AlarmSearchActivity.class);
			break;

		case R.id.smart_bus_main_circle:
			StartIntent.startIntent(this, NearByActivity.class);
			break;

		case R.id.smart_bus_main_favor:
			StartIntent.startIntent(this, SmartBusFavorActivity.class);
			break;

		case R.id.smart_bus_main_news:
			StartIntent.startIntent(this, SmartBusNewsActivity.class);
			break;

		case R.id.smart_bus_main_order:
			Toast.makeText(this, R.string.kaifatishi, Toast.LENGTH_SHORT)
					.show();
			break;

		case R.id.smart_bus_main_more:
			StartIntent.startIntent(this, SmartBusMoreActivity.class);
			break;
		}
	}

	SmartBusLineFragment smartBusLineFragment;
	SmartBusStationFragment smartBusStationFragment;
	SmartBusTransferFragment smartBusTransferFragment;
	

	/**
	 * 初始化viewpager
	 * 
	 * @param i
	 *            默认选中页
	 */
	public void initViewPager(int i)
	{
		contentPager = (ViewPager) findViewById(R.id.smart_bus_main_content);

		List<Fragment> fragments = new ArrayList<Fragment>();

		smartBusLineFragment = new SmartBusLineFragment();
		smartBusStationFragment = new SmartBusStationFragment();
		smartBusTransferFragment = new SmartBusTransferFragment();

		fragments.add(smartBusLineFragment);
		fragments.add(smartBusTransferFragment);
		fragments.add(smartBusStationFragment);

		contentPager.setAdapter(new ContentFragmentPagerAdapter(this
				.getSupportFragmentManager(), fragments));
		contentPager.setCurrentItem(i);

		contentPager.setOnPageChangeListener(new MyOnPageChangeListener());

		changeImage(0);
	}

	/**
	 * viewpager页面切换
	 * 
	 * @param v
	 */
	public void change(View v)
	{
		switch (v.getId())
		{
		case R.id.smart_bus_main_transfer:
			contentPager.setCurrentItem(0);
			break;

		case R.id.smart_bus_main_line:
			contentPager.setCurrentItem(1);
			break;

		case R.id.smart_bus_main_station:
			contentPager.setCurrentItem(2);
			break;
		}
	}

	/**
	 * 切换选项下拉线
	 * 
	 * @param textView
	 *            关联的textview组
	 * @param id
	 *            选中的textview id
	 */
	public void changeImage(int id)
	{
		int[] textID = new int[] { R.id.smart_bus_main_transfer,
				R.id.smart_bus_main_line, R.id.smart_bus_main_station };
		int[] imageID = new int[] { R.id.smart_bus_main_transfer_line,
				R.id.smart_bus_main_line_line, R.id.smart_bus_main_station_line };
		for (int i = 0; i < 3; i++)
		{
			if (id == i)
			{
				((TextView) findViewById(textID[id]))
						.setTextColor(getResources().getColor(
								R.color.news_select_item_color));
				findViewById(imageID[id]).setVisibility(View.VISIBLE);
			} else
			{
				findViewById(imageID[i]).setVisibility(View.INVISIBLE);
				((TextView) findViewById(textID[i]))
						.setTextColor(getResources().getColor(
								R.color.news_unselect_item_color));
			}
		}
	}

	/**
	 * viewpager切换事件
	 * 
	 * @author Administrator
	 * 
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int id)
		{
			changeImage(id);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		@Override
		public void onPageScrollStateChanged(int arg0)
		{
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2)
	{
		if (arg0 == 1 && arg1==1)
		{
				this.smartBusStationFragment.onActivityResult(arg0, arg1, arg2);
		}
		else if(arg1==MapSelectPointActivity.MAP_RESULT_CODE)
		{
			this.smartBusTransferFragment.onActivityResult(arg0, arg1, arg2);
		}
		
	}
}
