package com.example.smarttraffic.tripPlan;

import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.tripPlan.fragment.CommonDrivingFragment;
import com.example.smarttraffic.tripPlan.fragment.TripDrivingSelfFragment;
import com.example.smarttraffic.util.StartIntent;

import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * 出行规划主界面
 * @author Administrator zhou
 *
 */
public class TripHomeActivity extends FragmentActivity {

	TripDrivingSelfFragment tripDrivingSelfFragment;
	CommonDrivingFragment commonDrivingFragment;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_trip_home);
		
		changeFragment(R.id.trip_driving_self);
	}
	
	public void changeFragment(View v)
	{
		changeFragment(v.getId());
	}
	
	public void changeFragment(int id)
	{
		switch (id) 
		{
			case R.id.trip_driving_self:
				if(tripDrivingSelfFragment==null)
				{
					tripDrivingSelfFragment=new TripDrivingSelfFragment();
				}
				ManagerFragment.replaceFragment(this, R.id.trip_content, tripDrivingSelfFragment);
				changeImage(0);
				break;
			case R.id.trip_driving_common:
				if(commonDrivingFragment==null)
				{
					commonDrivingFragment=new CommonDrivingFragment();
				}
				ManagerFragment.replaceFragment(this, R.id.trip_content, commonDrivingFragment);
				changeImage(1);
//				Toast.makeText(this, R.string.kaifatishi, Toast.LENGTH_SHORT).show();
				break;
			case R.id.trip_favor:
				StartIntent.startIntent(this, TripFavorActivity.class);
				break;

			case R.id.trip_more:
				StartIntent.startIntent(this, TripMoreActivity.class);
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int request, int response, Intent data)
	{
		if(request==1 && response==1)
		{
			tripDrivingSelfFragment.onActivityResult(request, response, data);
		}
		else if(response==2)
		{
			commonDrivingFragment.onActivityResult(request, response, data);
		}
	}
	
	/**
	 * 切换当前选项图标
	 * @param id 当前选项
	 */
	public void changeImage(int id)
	{
		int[] press={R.drawable.trip_self,R.drawable.trip_bus,R.drawable.trip_favor,R.drawable.trip_more};
		int[] normal={R.drawable.selector_trip_self,R.drawable.selector_trip_bus,R.drawable.selector_trip_favor,R.drawable.selector_trip_more};
		
		ImageView[] imageViews=new ImageView[4];
		imageViews[0]=(ImageView)findViewById(R.id.trip_driving_self);
		imageViews[1]=(ImageView)findViewById(R.id.trip_driving_common);
		imageViews[2]=(ImageView)findViewById(R.id.trip_favor);
		imageViews[3]=(ImageView)findViewById(R.id.trip_more);
		
		for(int i=0;i<4;i++)
		{
			if(id==i)
			{
				imageViews[i].setImageResource(press[i]);
			}
			else
			{
				imageViews[i].setImageResource(normal[i]);
			}
		}
	}
}
