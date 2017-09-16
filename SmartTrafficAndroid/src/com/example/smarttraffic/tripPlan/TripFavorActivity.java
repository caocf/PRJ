package com.example.smarttraffic.tripPlan;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;
import com.example.smarttraffic.tripPlan.fragment.FavorFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 收藏夹界面
 * 包括自驾和公共交通两类收藏信息
 * @author Administrator zhou
 *
 */
public class TripFavorActivity extends FragmentActivity
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
		setContentView(R.layout.activity_trip_favor);
		
		HeadFavorFragment headFavorFragment=new HeadFavorFragment();
		headFavorFragment.setTitleName("收藏夹");
		headFavorFragment.setRightName("删除");
		headFavorFragment.setRightListen(new onclick());
		ManagerFragment.replaceFragment(this, R.id.trip_favor_head, headFavorFragment);
		
		contentPager=(ViewPager)findViewById(R.id.trip_favor_viewpager);
		selfTextView=(TextView)findViewById(R.id.trip_driving_self_favor);
		commonTextView=(TextView)findViewById(R.id.trip_driving_common_favor);
		
		selfImageView=(ImageView)findViewById(R.id.trip_driving_self_underline);
		commonImageView=(ImageView)findViewById(R.id.trip_driving_common_favor_underline);
		
		initViewPager(0);
	}
	
	
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			StartIntent.startIntent(TripFavorActivity.this, DeleteTripFavorActivity.class);
		}
	}
	
	/**
	 * 切换收藏夹内容页面
	 * @param v
	 */
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
	
	/**
	 * 初始化左右滑动界面
	 * @param i 界面编号
	 */
	public void initViewPager(int i)
	{		
		List<Fragment> fragments=new ArrayList<Fragment>();
		
		FavorFragment selfFavorFragment=new FavorFragment();
		selfFavorFragment.setFavorKind(0);
		FavorFragment commonFavorFragment=new FavorFragment();
		commonFavorFragment.setFavorKind(1);
		
		fragments.add(selfFavorFragment);
		fragments.add(commonFavorFragment);
		
		contentPager.setAdapter(new ContentFragmentPagerAdapter(this.getSupportFragmentManager(), fragments));
		contentPager.setCurrentItem(i);
		
		commonImageView.setVisibility(View.INVISIBLE);
		contentPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int id) {
			
			switch (id) {
			case 0:
					selfTextView.setTextColor(getResources().getColor(R.color.news_select_item_color));
					commonTextView.setTextColor(getResources().getColor(R.color.news_unselect_item_color));
					selfImageView.setVisibility(View.VISIBLE);;
					commonImageView.setVisibility(View.INVISIBLE);
					break;
				case 1:
					commonTextView.setTextColor(getResources().getColor(R.color.news_select_item_color));
					selfTextView.setTextColor(getResources().getColor(R.color.news_unselect_item_color));
					commonImageView.setVisibility(View.VISIBLE);
					selfImageView.setVisibility(View.INVISIBLE);
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
