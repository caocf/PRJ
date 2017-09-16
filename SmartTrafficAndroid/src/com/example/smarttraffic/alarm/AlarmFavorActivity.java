package com.example.smarttraffic.alarm;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;
import com.example.smarttraffic.util.StartIntent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class AlarmFavorActivity extends FragmentActivity
{	
	TextView upTextView;
	TextView downTextView;
	
	ViewPager contentPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_alarm_favor);
		
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("提醒清单");
		fragment.setRightName("删除");
		fragment.setRightListen(new View.OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				int i=contentPager.getCurrentItem();
				Bundle bundle=new Bundle();
				bundle.putInt(AlarmFavorDeleteActivity.SELECT_DEFAULT, i);
				StartIntent.startIntentWithParam(AlarmFavorActivity.this, AlarmFavorDeleteActivity.class, bundle);
			}
		});
		ManagerFragment.replaceFragment(this, R.id.alarm_favor_head, fragment);	
		
		initView();
		
		initViewPager(0);
	}
	
	private void initView()
	{
		upTextView=(TextView)findViewById(R.id.alarm_favor_up);
		downTextView=(TextView)findViewById(R.id.alarm_favor_dowm);
	}
	
	public void initViewPager(int i)
	{
		contentPager=(ViewPager)findViewById(R.id.alarm_favor_content);
		
		List<Fragment> fragments=new ArrayList<Fragment>();
		
		AlarmFavorFragment smartBusLineFragment=new AlarmFavorFragment();
		smartBusLineFragment.setType(1);
		
		
		AlarmFavorFragment smartBusTransferFragment=new AlarmFavorFragment();
		smartBusTransferFragment.setType(0);
		
		fragments.add(smartBusTransferFragment);
		fragments.add(smartBusLineFragment);
			
		contentPager.setAdapter(new ContentFragmentPagerAdapter(this.getSupportFragmentManager(), fragments));
		contentPager.setCurrentItem(i);
		
		contentPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		changeImage(new TextView[]{upTextView,downTextView},i);
	}
	
	public void change(View v)
	{
		switch (v.getId())
		{
			case R.id.alarm_favor_up:
				contentPager.setCurrentItem(0);
				break;
				
			case R.id.alarm_favor_dowm:
				contentPager.setCurrentItem(1);
				break;
				
		}
	}
	
	public void changeImage(TextView[] textView,int id)
	{
		for(int i=0;i<textView.length;i++)
		{
			if(i==id)
			{
				textView[i].setTextColor(getResources().getColor(R.color.news_select_item_color));
				
				Drawable drawable=getResources().getDrawable(R.drawable.line);
				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
				
				textView[i].setCompoundDrawables(null, null, null, drawable);
			}
			else 
			{
				textView[i].setTextColor(getResources().getColor(R.color.news_unselect_item_color));
				textView[i].setCompoundDrawables(null, null, null, null);
			}
		}
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int id) 
		{	
			changeImage(new TextView[]{upTextView,downTextView},id);
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
	
	public void onclick(View v)
	{
		switch (v.getId())
		{
//			case R.id.button1:
//				this.startService(new Intent(this, UpService.class));
//				this.startService(new Intent(this,DownService.class));
//				break;
//				
//			case R.id.button2:
//				this.stopService(new Intent(this,UpService.class));
//				this.stopService(new Intent(this,DownService.class));
//				break;

		}
	}
}
