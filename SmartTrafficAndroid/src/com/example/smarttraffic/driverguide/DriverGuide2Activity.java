package com.example.smarttraffic.driverguide;


import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 行车诱导2
 * 
 * 此activity的目的只是为了避免横竖屏切换间的资源重绘问题
 * 
 * @author Administrator
 * 
 */
public class DriverGuide2Activity extends FragmentActivity
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

		loadView();

		initFragment();
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
			StartIntent.resultActivity(this, 1);
			break;

		case R.id.driver_guide_2:
			break;

		case R.id.driver_guide_3:
			StartIntent.resultActivity(this, 2);
			break;
		}
	}

	Fragment current;

	public void initFragment()
	{
		current = new DrivingGuideIndexFragment();

		ManagerFragment.replaceFragment(this, R.id.driver_guide_content,
				current);

		item2.setTextColor(getResources().getColor(
				R.color.news_select_item_color));
		item1.setTextColor(getResources().getColor(
				R.color.news_unselect_item_color));

		image2.setImageDrawable(getResources().getDrawable(R.drawable.line));
		image1.setImageDrawable(null);

		item3.setTextColor(getResources().getColor(
				R.color.news_unselect_item_color));
		image3.setImageDrawable(null);
	}

}
