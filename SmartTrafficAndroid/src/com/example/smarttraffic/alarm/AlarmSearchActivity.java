package com.example.smarttraffic.alarm;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class AlarmSearchActivity extends FragmentActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_alarm_search);
		
		initHead();
		initContent();
	}
	
	private void initHead()
	{
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("提醒设置");
		fragment.setRightName("提醒清单");
		fragment.setRightListen(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				StartIntent.startIntent(AlarmSearchActivity.this, AlarmFavorActivity.class);
			}
		});
		
		ManagerFragment.replaceFragment(this, R.id.alarm_search_head, fragment);
	}
	
	private void initContent()
	{
		AlarmStationFragment fragment=new AlarmStationFragment();
		ManagerFragment.replaceFragment(this, R.id.alarm_search_content, fragment);
	}
	
	
}
