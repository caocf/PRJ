package com.example.smarttraffic.bike;

import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.fragment.BikeCallFragment;
import com.example.smarttraffic.bike.fragment.BikeMoreFragment;
import com.example.smarttraffic.bike.fragment.BikeRidingFragment;
import com.example.smarttraffic.bike.fragment.BikeStationInfoListFragment;
import com.example.smarttraffic.bike.fragment.BikeStationInfoMapFragment;
import com.example.smarttraffic.drivingSchool.ReplaceByParentLayoutInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class BikeHomeActivity extends FragmentActivity
{
	BikeMoreFragment bikeMoreFragment;
	BikeCallFragment bikeCallFragment;
	BikeStationInfoListFragment bikeStationInfoListFragment;
	BikeStationInfoMapFragment bikeStationInfoMapFragment;
	BikeRidingFragment bikeRidingFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bike_home);
		currentID=-1;
		change(0);
	}
	
	int currentID;
	
	public void select(View v)
	{
		switch (v.getId())
		{
			case R.id.bike_item_image_1:
				change(0);
				break;
				
			case R.id.bike_item_image_2:
				change(1);
				break;
				
			case R.id.bike_item_image_3:
				change(2);
				break;
	
			case R.id.bike_item_image_4:
				change(3);
				break;
	
			case R.id.bike_item_image_5:
				change(4);
				break;
		}
	}
	
	public void change(int id)
	{
		if(currentID==id)
			return;
		
		currentID=id==2?currentID:id;
		
		switch (id)
		{
			case 0:
				bikeStationInfoMapFragment=null;
				bikeStationInfoMapFragment=new BikeStationInfoMapFragment();
				bikeStationInfoMapFragment.setReplaceByParentLayout(new OnReplace());
				replaceTheFragment(bikeStationInfoMapFragment);
				break;
				
			case 1:
				if(bikeRidingFragment==null)
					bikeRidingFragment=new BikeRidingFragment();
				replaceTheFragment(bikeRidingFragment);
				break;
				
			case 2:
				StartIntent.startIntent(this, BikeFavorActivity.class);
				break;
			case 3:
				if(bikeCallFragment==null)
					bikeCallFragment=new BikeCallFragment();
				replaceTheFragment(bikeCallFragment);
				
				break;
		
			case 4:
				if(bikeMoreFragment==null)
					bikeMoreFragment=new BikeMoreFragment();
				replaceTheFragment(bikeMoreFragment);				
				break;
		}
		
		if(id!=2)
			changeImage(id);
	}
	
	public void replaceTheFragment(Fragment fragment)
	{
		ManagerFragment.replaceFragment(this, R.id.bike_main_content, fragment);
	}
	
	class OnReplace implements ReplaceByParentLayoutInterface
	{
		@Override
		public void replace(int id)
		{
			if(id==1)
			{
				bikeStationInfoListFragment=null;
				
				bikeStationInfoListFragment=new BikeStationInfoListFragment();
				bikeStationInfoListFragment.setData(bikeStationInfoMapFragment.getDataList());
				bikeStationInfoListFragment.setReplaceByParentLayout(new OnReplace());
				
				replaceTheFragment(bikeStationInfoListFragment);
				
				currentID=5;
			}
			else if(id==2)
			{
				change(0);
			}
			else if(id==3)
			{
				change(2);
			}
		}
	}
	
	public void changeImage(int id)
	{
		int[] press={R.drawable.bike_station_pressed,R.drawable.bike_riding_pressed,R.drawable.bike_my_favorites_pressed,R.drawable.bike_service_hotline_pressed,R.drawable.bike_more_normal};
		int[] normal={R.drawable.selector_bike_station,R.drawable.selector_bike_ride,R.drawable.selector_bike_favor,R.drawable.selector_bike_call,R.drawable.selector_bike_more};
		
		ImageView[] imageViews=new ImageView[5];
		imageViews[0]=(ImageView)findViewById(R.id.bike_item_image_1);
		imageViews[1]=(ImageView)findViewById(R.id.bike_item_image_2);
		imageViews[2]=(ImageView)findViewById(R.id.bike_item_image_3);
		imageViews[3]=(ImageView)findViewById(R.id.bike_item_image_4);
		imageViews[4]=(ImageView)findViewById(R.id.bike_item_image_5);
		
		for(int i=0;i<5;i++)
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
