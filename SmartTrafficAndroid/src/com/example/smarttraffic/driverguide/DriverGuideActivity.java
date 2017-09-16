package com.example.smarttraffic.driverguide;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 行车诱导
 * 
 * @author Administrator
 * 
 */
public class DriverGuideActivity extends FragmentActivity
{
	TextView item1;
	TextView item2;
	TextView item3;

	ImageView image1;
	ImageView image2;
	ImageView image3;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_driver_guide);

		initHead();
		loadView();

		initViewPager(0);
	}

	HeadNameFragment fragment;

	private void initHead()
	{
		if (fragment == null)
		{
			fragment = new HeadNameFragment();
			fragment.setTitleName("行车诱导");
		}

		ManagerFragment.replaceFragment(this, R.id.driver_guide_head, fragment);
	}

	private void loadView()
	{
		item1 = (TextView) findViewById(R.id.driver_guide_1);
		item2 = (TextView) findViewById(R.id.driver_guide_2);
		item3 = (TextView) findViewById(R.id.driver_guide_3);

		image1 = (ImageView) findViewById(R.id.driver_guide_1_underline);
		image2 = (ImageView) findViewById(R.id.driver_guide_2_underline);
		image3 = (ImageView) findViewById(R.id.driver_guide_3_underline);
	}

	public void onclick(View v)
	{
		switch (v.getId())
		{
		case R.id.driver_guide_1:
			onPageSelected(0);
			break;

		case R.id.driver_guide_2:
			startMiddle();
			break;

		case R.id.driver_guide_3:
			onPageSelected(1);
			break;
		}
	}
	
	private void startMiddle()
	{
		StartIntent.startIntentForResult(this, DriverGuide2Activity.class, 1);
	}
	
	private int resultcode=0;
	
	protected void onActivityResult(int arg0, int arg1, android.content.Intent arg2) 
	{
		resultcode=arg1;
	};
	

	DrivingGuideRoadFragment roadFragment;
	DrivingGuideIndexListFragment indexListFragment;

	List<Fragment> fragments;
	Fragment current;

	public void initViewPager(int i)
	{
		// contentPager = (ViewPager) findViewById(R.id.driver_guide_Content);

		fragments = new ArrayList<Fragment>();

		roadFragment = new DrivingGuideRoadFragment();
		indexListFragment = new DrivingGuideIndexListFragment();

		fragments.add(roadFragment);
		fragments.add(indexListFragment);

		current = fragments.get(i);

		ManagerFragment.replaceFragment(this, R.id.driver_guide_content,
				fragments.get(i));

		switch (i)
		{
		case 0:
			image2.setImageDrawable(null);
			image3.setImageDrawable(null);
			break;

		case 1:
			image1.setImageDrawable(null);
			image3.setImageDrawable(null);
			break;

		case 2:
			image1.setImageDrawable(null);
			image2.setImageDrawable(null);
			break;
		}


	}


	public void onPageSelected(int id)
	{
		switch (id)
		{

		case 0:
			item1.setTextColor(getResources().getColor(
					R.color.news_select_item_color));
			item2.setTextColor(getResources().getColor(
					R.color.news_unselect_item_color));

			image1.setImageDrawable(getResources().getDrawable(R.drawable.line));
			image2.setImageDrawable(null);

			item3.setTextColor(getResources().getColor(
					R.color.news_unselect_item_color));
			image3.setImageDrawable(null);

			findViewById(R.id.driver_guide_head).setVisibility(View.VISIBLE);

			break;

		case 1:
			item3.setTextColor(getResources().getColor(
					R.color.news_select_item_color));
			item1.setTextColor(getResources().getColor(
					R.color.news_unselect_item_color));

			image3.setImageDrawable(getResources().getDrawable(R.drawable.line));
			image1.setImageDrawable(null);

			item2.setTextColor(getResources().getColor(
					R.color.news_unselect_item_color));
			image2.setImageDrawable(null);

			findViewById(R.id.driver_guide_head).setVisibility(View.VISIBLE);

			break;
		}

		if (current != fragments.get(id))
		{
			ManagerFragment.switchContent(this, R.id.driver_guide_content,
					current, current, fragments.get(id));
			current = fragments.get(id);
		}
	}

//	public void changeScreen(int type)
//	{
//		if (type == 0
//				&& getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//		{
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		} else if (type == 1
//				&& getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
//		{
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		}
//	}

	@Override
	protected void onPause()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.start();
		
		if(resultcode==1)
			onPageSelected(0);
		else if(resultcode==2)
			onPageSelected(1);
		
		resultcode=0;
		
		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}
