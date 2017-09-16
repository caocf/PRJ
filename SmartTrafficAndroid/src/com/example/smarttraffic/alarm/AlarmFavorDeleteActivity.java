package com.example.smarttraffic.alarm;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class AlarmFavorDeleteActivity extends FragmentActivity
{	
	TextView upTextView;
	TextView downTextView;
	
	ViewPager contentPager;
	
	public static final String SELECT_DEFAULT="select_default";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_alarm_favor_delete);
		
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("已选择0项目");
		fragment.setRightName("全选");
		fragment.setRightListen(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
			}
		});
		ManagerFragment.replaceFragment(this, R.id.alarm_delete_favor_head, fragment);	
		
		initView();
		
		initViewPager(getIntent().getIntExtra(SELECT_DEFAULT, 0));
	}
	
	private void initView()
	{
		upTextView=(TextView)findViewById(R.id.alarm_delete_favor_up);
		downTextView=(TextView)findViewById(R.id.alarm_delete_favor_down);
	}
	
	AlarmFavorFragment upFragment;
	AlarmFavorFragment downFragment;
	
	public void initViewPager(int i)
	{
		contentPager=(ViewPager)findViewById(R.id.alarm_delete_favor__content);
		
		List<Fragment> fragments=new ArrayList<Fragment>();
		
		upFragment=new AlarmFavorFragment();
		upFragment.setType(0);
		upFragment.setDelete(true);
		
		downFragment=new AlarmFavorFragment();
		downFragment.setType(1);
		downFragment.setDelete(true);
		
		fragments.add(upFragment);
		fragments.add(downFragment);
			
		contentPager.setAdapter(new ContentFragmentPagerAdapter(this.getSupportFragmentManager(), fragments));
		contentPager.setCurrentItem(i);
		
		contentPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		changeImage(new TextView[]{upTextView,downTextView},i);
	}
	
	public void change(View v)
	{
		switch (v.getId())
		{
			case R.id.alarm_delete_favor_up:
				contentPager.setCurrentItem(0);
				break;
				
			case R.id.alarm_delete_favor_down:
				contentPager.setCurrentItem(1);
				break;
		}
	}
	
	public void delete(View v)
	{
		if(contentPager.getCurrentItem()==0)
			upFragment.deleteData();
		else if(contentPager.getCurrentItem()==1)
			downFragment.deleteData();
	}
	
	public void changeImage(TextView[] textView,int id)
	{
		for(int i=0;i<textView.length;i++)
		{
			if(i==id)
			{
				textView[i].setTextColor(getResources().getColor(R.color.news_select_item_color));
				
				Drawable drawable=getResources().getDrawable(R.drawable.check_under_line);
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
}
