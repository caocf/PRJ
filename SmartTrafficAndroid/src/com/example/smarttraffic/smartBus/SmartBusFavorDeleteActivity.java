package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.ChangeHead;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;
import com.example.smarttraffic.smartBus.fragment.SmartBusFavorFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SmartBusFavorDeleteActivity extends FragmentActivity
{
	TextView transferTextView;
	TextView lineTextView;
	TextView stationTextView;

	ViewPager contentPager;

	public static final String SELECT_DEFAULT = "select_default";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_favor_delete);

		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("已选择0项");
		fragment.setRightName("全选");
		fragment.setRightListen(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				int i = contentPager.getCurrentItem();

				switch (i)
				{
				case 0:
					smartBusTransferFragment.selectAll(0, true);
					break;

				case 1:
					smartBusLineFragment.selectAll(1, true);
					break;

				case 2:
					smartBusStationFragment.selectAll(2, true);
					break;
				}

			}
		});
		ManagerFragment.replaceFragment(this, R.id.smart_bus_favor_head,
				fragment);

		initView();

		initViewPager(getIntent().getIntExtra(SELECT_DEFAULT, 0));
	}

	ChangeHead changeHead = new ChangeHead()
	{

		@Override
		public void changeHead(int i)
		{
			((TextView) findViewById(R.id.common_head_title)).setText("已选择" + i
					+ "项");
		}
	};

	private void initView()
	{
		transferTextView = (TextView) findViewById(R.id.smart_bus_favor_transfer);
		lineTextView = (TextView) findViewById(R.id.smart_bus_favor_line);
		stationTextView = (TextView) findViewById(R.id.smart_bus_favor_station);
	}

	SmartBusFavorFragment smartBusLineFragment;
	SmartBusFavorFragment smartBusStationFragment;
	SmartBusFavorFragment smartBusTransferFragment;

	public void initViewPager(int i)
	{
		contentPager = (ViewPager) findViewById(R.id.smart_bus_favor_content);

		List<Fragment> fragments = new ArrayList<Fragment>();

		smartBusLineFragment = new SmartBusFavorFragment();
		smartBusLineFragment.setType(1);
		smartBusLineFragment.setDelete(true);
		smartBusLineFragment.setChangeHead(changeHead);

		smartBusStationFragment = new SmartBusFavorFragment();
		smartBusStationFragment.setType(2);
		smartBusStationFragment.setDelete(true);
		smartBusStationFragment.setChangeHead(changeHead);

		smartBusTransferFragment = new SmartBusFavorFragment();
		smartBusTransferFragment.setType(0);
		smartBusTransferFragment.setDelete(true);
		smartBusTransferFragment.setChangeHead(changeHead);

		fragments.add(smartBusTransferFragment);
		fragments.add(smartBusLineFragment);
		fragments.add(smartBusStationFragment);

		contentPager.setAdapter(new ContentFragmentPagerAdapter(this
				.getSupportFragmentManager(), fragments));
		contentPager.setCurrentItem(i);

		contentPager.setOnPageChangeListener(new MyOnPageChangeListener());

		changeImage(new TextView[] { transferTextView, lineTextView,
				stationTextView }, i);
	}

	public void change(View v)
	{
		switch (v.getId())
		{
		case R.id.smart_bus_favor_transfer:
			contentPager.setCurrentItem(0);
			break;

		case R.id.smart_bus_favor_line:
			contentPager.setCurrentItem(1);
			break;

		case R.id.smart_bus_favor_station:
			contentPager.setCurrentItem(2);
			break;
		}
	}

	public void changeImage(TextView[] textView, int id)
	{
		int[] ids = new int[] { R.id.smart_bus_favor_transfer_imageview,
				R.id.smart_bus_favor_line_imageview,
				R.id.smart_bus_favor_station_imageview };
		for (int i = 0; i < textView.length; i++)
		{
			if (i == id)
			{
				textView[i].setTextColor(getResources().getColor(
						R.color.news_select_item_color));

				findViewById(ids[i]).setVisibility(View.VISIBLE);
			} else
			{
				textView[i].setTextColor(getResources().getColor(
						R.color.news_unselect_item_color));
				findViewById(ids[i]).setVisibility(View.INVISIBLE);
			}
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int id)
		{
			changeImage(new TextView[] { transferTextView, lineTextView,
					stationTextView }, id);
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
}
