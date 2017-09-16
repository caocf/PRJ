package com.example.smarttraffic.bike;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.fragment.FavorFragment;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;
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
public class BikeFavorActivity extends FragmentActivity
{
	ViewPager contentPager;
	TextView selfTextView;
	TextView commonTextView;
	ImageView selfImageView;
	ImageView commonImageView;
	
	public static final String INIT_PAGER="init_pager";
	int first;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bike_favor);
		
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
		
		first=getIntent().getIntExtra(INIT_PAGER, 0);
		
		initViewPager(first);
	}
	
	
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			StartIntent.startIntent(BikeFavorActivity.this, BikeFavorDeleteActivity.class);
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
		
		FavorFragment stationFragment=new FavorFragment();
		stationFragment.setFavorKind(0);
		FavorFragment rideFragment=new FavorFragment();
		rideFragment.setFavorKind(1);
		
		fragments.add(stationFragment);
		fragments.add(rideFragment);
		
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
					selfImageView.setImageDrawable(getResources().getDrawable(R.drawable.line));
					commonImageView.setImageDrawable(null);
					break;
				case 1:
					commonTextView.setTextColor(getResources().getColor(R.color.news_select_item_color));
					selfTextView.setTextColor(getResources().getColor(R.color.news_unselect_item_color));
					commonImageView.setImageDrawable(getResources().getDrawable(R.drawable.line));
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
