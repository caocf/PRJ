package com.example.smarttraffic.bike;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.ChangeHead;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.fragment.FavorFragment;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BikeFavorDeleteActivity extends FragmentActivity
{
	ViewPager contentPager;
	TextView selfTextView;
	TextView commonTextView;
	ImageView selfImageView;
	ImageView commonImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bike_favor);
		
		HeadFavorFragment headFavorFragment=new HeadFavorFragment();
		headFavorFragment.setTitleName("已选择0项");
		headFavorFragment.setRightName("全选");
		headFavorFragment.setRightListen(new onclick());
		ManagerFragment.replaceFragment(this, R.id.trip_favor_head, headFavorFragment);
		
		contentPager=(ViewPager)findViewById(R.id.trip_favor_viewpager);
		selfTextView=(TextView)findViewById(R.id.trip_driving_self_favor);
		commonTextView=(TextView)findViewById(R.id.trip_driving_common_favor);
		
		selfImageView=(ImageView)findViewById(R.id.trip_driving_self_underline);
		commonImageView=(ImageView)findViewById(R.id.trip_driving_common_favor_underline);
		
		initViewPager(0);
	}

	ChangeHead changeHead=new ChangeHead()
	{
		
		@Override
		public void changeHead(int i)
		{
			((TextView)findViewById(R.id.common_head_title)).setText("已选择"+i+"项");
		}
	};
	
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if(contentPager.getCurrentItem()==0)
			{
				selfFavorFragment.selectAll();
			}
			else 
			{
				commonFavorFragment.selectAll();
			}
		}
	}
	
	public void onclick(View v)
	{
		switch (v.getId()) 
		{
			case R.id.trip_driving_common_favor:
				contentPager.setCurrentItem(1);
				break;

			case R.id.trip_driving_self_favor:
				contentPager.setCurrentItem(0);
				break;			
		}
	}
	
	FavorFragment selfFavorFragment;
	FavorFragment commonFavorFragment;
	
	public void initViewPager(int i)
	{		
		List<Fragment> fragments=new ArrayList<Fragment>();
		
		selfFavorFragment=new FavorFragment();
		selfFavorFragment.setFavorKind(0);
		selfFavorFragment.setDelete(true);
		selfFavorFragment.setChangeHead(changeHead);
		
		commonFavorFragment=new FavorFragment();
		commonFavorFragment.setFavorKind(1);
		commonFavorFragment.setDelete(true);
		commonFavorFragment.setChangeHead(changeHead);
		
		fragments.add(selfFavorFragment);
		fragments.add(commonFavorFragment);
		
		contentPager.setAdapter(new ContentFragmentPagerAdapter(this.getSupportFragmentManager(), fragments));
		contentPager.setCurrentItem(i);
		
		commonImageView.setImageDrawable(null);
		contentPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int id) {
			
			switch (id) {
			case 0:
					selfTextView.setTextColor(getResources().getColor(R.color.news_select_item_color));
					commonTextView.setTextColor(getResources().getColor(R.color.news_unselect_item_color));
					selfImageView.setImageDrawable(getResources().getDrawable(R.drawable.check_under_line));
					commonImageView.setImageDrawable(null);
					break;
				case 1:
					commonTextView.setTextColor(getResources().getColor(R.color.news_select_item_color));
					selfTextView.setTextColor(getResources().getColor(R.color.news_unselect_item_color));
					commonImageView.setImageDrawable(getResources().getDrawable(R.drawable.check_under_line));
					selfImageView.setImageDrawable(null);
					break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
}
