package com.example.smarttraffic.news;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFragment;
import com.example.smarttraffic.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsHomeActivity extends FragmentActivity
{

	private ViewPager contentPager;

	TextView news1;
	TextView news2;
	TextView news3;
//	TextView news4;
	ImageView image1;
	ImageView image2;
	ImageView image3;
//	ImageView image4;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_news_home);

		initFragment();

		loadView();

		initViewPager(0);
	}

	public void onclick(View v)
	{
		switch (v.getId())
		{
		case R.id.news_1:
			contentPager.setCurrentItem(0);
			break;

		case R.id.news_2:
			contentPager.setCurrentItem(1);
			break;

		case R.id.news_3:
			contentPager.setCurrentItem(2);
			break;
			
//		case R.id.news_4:
//			contentPager.setCurrentItem(3);
//			break;
		}
	}

	private void loadView()
	{
		news1 = (TextView) findViewById(R.id.news_1);
		news2 = (TextView) findViewById(R.id.news_2);
		news3 = (TextView) findViewById(R.id.news_3);
//		news4 = (TextView) findViewById(R.id.news_4);

		image1 = (ImageView) findViewById(R.id.news_1_underline);
		image2 = (ImageView) findViewById(R.id.news_2_underline);
		image3 = (ImageView) findViewById(R.id.news_3_underline);
//		image4 = (ImageView) findViewById(R.id.news_4_underline);

	}

	private void initFragment()
	{

		HeadFragment headFragment = new HeadFragment();

		String nameString;
		nameString = getResources().getStringArray(R.array.array_main_content)[5];
		headFragment.setTitleName(nameString);

		getSupportFragmentManager().beginTransaction()
				.add(R.id.news_head, headFragment).commit();
	}

	List<Fragment> fragments;
	public void initViewPager(int i)
	{
		contentPager = (ViewPager) findViewById(R.id.News_Content);

		fragments = new ArrayList<Fragment>();

		NewsListFragment news1 = new NewsListFragment();
		news1.setId(0);
		fragments.add(news1);

		NewsListFragment news2 = new NewsListFragment();
		news2.setId(1);
		fragments.add(news2);
		
		NewsListFragment news3 = new NewsListFragment();
		news3.setId(2);
		fragments.add(news3);
		
//		fragments.add(new Fragment());
		
//		NewsListFragment news3 = new NewsListFragment();
//		news3.setId(2);
//		fragments.add(news3);

		contentPager.setAdapter(new ContentFragmentPagerAdapter(this
				.getSupportFragmentManager(), fragments));
		contentPager.setCurrentItem(i);

		image2.setImageDrawable(null);
		image3.setImageDrawable(null);
//		image4.setImageDrawable(null);
		contentPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener
	{

		@Override
		public void onPageSelected(int id)
		{

			switch (id)
			{
			case 0:
				news1.setTextColor(getResources().getColor(
						R.color.news_select_item_color));
				news2.setTextColor(getResources().getColor(
						R.color.news_unselect_item_color));
				news3.setTextColor(getResources().getColor(
						R.color.news_unselect_item_color));
//				news4.setTextColor(getResources().getColor(
//						R.color.news_unselect_item_color));
				image1.setImageDrawable(getResources()
						.getDrawable(R.drawable.line));
				image2.setImageDrawable(null);
				image3.setImageDrawable(null);
//				image4.setImageDrawable(null);
				break;
				
			case 1:
				news2.setTextColor(getResources().getColor(
						R.color.news_select_item_color));
				news1.setTextColor(getResources().getColor(
						R.color.news_unselect_item_color));
				news3.setTextColor(getResources().getColor(
						R.color.news_unselect_item_color));
//				news4.setTextColor(getResources().getColor(
//						R.color.news_unselect_item_color));
				image2.setImageDrawable(getResources()
						.getDrawable(R.drawable.line));
				image1.setImageDrawable(null);
				image3.setImageDrawable(null);
//				image4.setImageDrawable(null);
				break;
				
			case 2:
				news3.setTextColor(getResources().getColor(
						R.color.news_select_item_color));
				news2.setTextColor(getResources().getColor(
						R.color.news_unselect_item_color));
				news1.setTextColor(getResources().getColor(
						R.color.news_unselect_item_color));
//				news4.setTextColor(getResources().getColor(
//						R.color.news_unselect_item_color));
				image3.setImageDrawable(getResources()
						.getDrawable(R.drawable.line));
				image2.setImageDrawable(null);
				image1.setImageDrawable(null);
//				image4.setImageDrawable(null);
				break;
				
//			case 3:
//				news4.setTextColor(getResources().getColor(
//						R.color.news_select_item_color));
//				news2.setTextColor(getResources().getColor(
//						R.color.news_unselect_item_color));
//				news1.setTextColor(getResources().getColor(
//						R.color.news_unselect_item_color));
//				news3.setTextColor(getResources().getColor(
//						R.color.news_unselect_item_color));
//				image4.setImageDrawable(getResources()
//						.getDrawable(R.drawable.line));
//				image2.setImageDrawable(null);
//				image1.setImageDrawable(null);
//				image3.setImageDrawable(null);
//				break;
			}
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
