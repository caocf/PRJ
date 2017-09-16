package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;
import com.example.smarttraffic.smartBus.fragment.SmartBusFavorFragment;
import com.example.smarttraffic.user.UserControl;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class SmartBusFavorActivity extends FragmentActivity
{
	TextView transferTextView;
	TextView lineTextView;
	TextView stationTextView;

	ViewPager contentPager;

	public static final String SMART_BUS_FAVOR_FIRST_PAGE = "smart_bus_favor_first_page";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_favor);

		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("收藏夹");
		fragment.setRightName("删除");
		fragment.setRightListen(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				
				if (UserControl.getUser() == null)
				{
					Toast.makeText(SmartBusFavorActivity.this, "您尚未登录", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				int i = contentPager.getCurrentItem();
				Bundle bundle = new Bundle();
				bundle.putInt(SmartBusFavorDeleteActivity.SELECT_DEFAULT, i);
				StartIntent.startIntentWithParam(SmartBusFavorActivity.this,
						SmartBusFavorDeleteActivity.class, bundle);
			}
		});
		ManagerFragment.replaceFragment(this, R.id.smart_bus_favor_head,
				fragment);

		initView();

		initViewPager(getIntent().getIntExtra(SMART_BUS_FAVOR_FIRST_PAGE, 0));
	}

	private void initView()
	{
		transferTextView = (TextView) findViewById(R.id.smart_bus_favor_transfer);
		lineTextView = (TextView) findViewById(R.id.smart_bus_favor_line);
		stationTextView = (TextView) findViewById(R.id.smart_bus_favor_station);
	}

	public void initViewPager(int i)
	{
		contentPager = (ViewPager) findViewById(R.id.smart_bus_favor_content);

		List<Fragment> fragments = new ArrayList<Fragment>();

		SmartBusFavorFragment smartBusLineFragment = new SmartBusFavorFragment();
		smartBusLineFragment.setType(1);

		SmartBusFavorFragment smartBusStationFragment = new SmartBusFavorFragment();
		smartBusStationFragment.setType(2);

		SmartBusFavorFragment smartBusTransferFragment = new SmartBusFavorFragment();
		smartBusTransferFragment.setType(0);

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

	// public void changeImage(TextView[] textView,int id)
	// {
	// int[] ids=new
	// int[]{R.id.smart_bus_favor_transfer_imageview,R.id.smart_bus_favor_line_imageview,R.id.smart_bus_favor_station_imageview};
	// for(int i=0;i<textView.length;i++)
	// {
	// if(i==id)
	// {
	// textView[i].setTextColor(getResources().getColor(R.color.news_select_item_color));
	//
	// Drawable drawable=getResources().getDrawable(R.drawable.line);
	// drawable.setBounds(0, 0, drawable.getMinimumWidth(),
	// drawable.getMinimumHeight());
	//
	// textView[i].setCompoundDrawables(null, null, null, drawable);
	// }
	// else
	// {
	// textView[i].setTextColor(getResources().getColor(R.color.news_unselect_item_color));
	// textView[i].setCompoundDrawables(null, null, null, null);
	// }
	// }
	// }

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
