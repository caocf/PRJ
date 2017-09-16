package com.example.smarttraffic.airport;

import java.util.List;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.news.ContentFragmentPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;

public class AirStationInfoActivity extends FragmentActivity implements UpdateView
{
	@SuppressWarnings("unchecked")
	@Override
	public void update(Object data)
	{
		if(data instanceof List<?>)
		{
			stationList=(List<AirStationInfo>)data;
			
			airStationInfoFragments=new AirStationInfoFragment[stationList.size()];
			String[] titles=new String[stationList.size()];
			
			for(int i=0;i<stationList.size();i++)
			{
				airStationInfoFragments[i]=new AirStationInfoFragment();
				airStationInfoFragments[i].setUrl(stationList.get(i).getUrl());
				
				titles[i]=stationList.get(i).getStationName();
			}
			
			contentFragmentPagerAdapter=new ContentFragmentPagerAdapter(this.getSupportFragmentManager(), airStationInfoFragments,titles);
			viewPager.setAdapter(contentFragmentPagerAdapter);
			viewPager.setCurrentItem(0);
			
			viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
			
		}
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int id) {
			
			if(airStationInfoFragments[id]==null)
			{
				airStationInfoFragments[id]=new AirStationInfoFragment();
				airStationInfoFragments[id].setUrl(stationList.get(id).getUrl());
				contentFragmentPagerAdapter.update();
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	public static final String AIR_STATION_THROUGHT_LINE="air_station_throught_line";
	
	ViewPager viewPager;
	PagerTitleStrip pagerTitleStrip;
	PagerTabStrip pagerTabStrip;
	ContentFragmentPagerAdapter contentFragmentPagerAdapter;
	List<AirStationInfo> stationList;
	AirStationInfoFragment[] airStationInfoFragments;
	int num;
	
	AirStationRequst requst;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_air_station_info);
		
		String airno=getIntent().getStringExtra(AIR_STATION_THROUGHT_LINE);
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName(airno);
		ManagerFragment.replaceFragment(this, R.id.air_station_head, fragment);
		
		viewPager=(ViewPager)findViewById(R.id.air_station_content);
		
		pagerTabStrip=(PagerTabStrip) findViewById(R.id.air_station_title); 
        pagerTabStrip.setTabIndicatorColor(0xff00ff00);  
        pagerTabStrip.setDrawFullUnderline(false); 
        pagerTabStrip.setBackgroundColor(0xffeeeeee); 
        pagerTabStrip.setTextSpacing(50); 
		
		requst=new AirStationRequst();
		requst.setAirNo(airno);
		
		new HttpThread(new AirStationSearch(), requst, this,this,R.string.error_air_station_info).start();		
	}
}
