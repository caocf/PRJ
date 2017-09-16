package com.example.smarttraffic.carRepair;

import com.example.smarttraffic.R;
import com.example.smarttraffic.drivingSchool.ReplaceByParentLayoutInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class CarRepairHomeActivity extends FragmentActivity 
{
	CarRepairInfoMapFragment carRepairInfoMapFragment;
	CarRepairInfoListFragment carRepairInfoListFragment;
	CarRepairFavorFragment carRepairFavorFragment;
	
	CarRepairMoreFragment carRepairMoreFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_car_repair_home);
		
		changeFragment(1);		
	}

	
	public void itemOnclick(View v)
	{
		switch (v.getId()) {
		case R.id.car_repair_point_info:
			changeFragment(1);
			break;
		case R.id.car_repair_favor:
			changeFragment(2);
			break;
		case R.id.car_repair_call_help:
			changeFragment(3);
			break;
		case R.id.car_repair_more:
			changeFragment(4);
			break;

		}
	}
	
	private void changeFragment(int i)
	{
		switch (i) 
		{
			case 1:
////				if(carRepairInfoMapFragment==null)
////				{
//					carRepairInfoMapFragment=null;
//					carRepairInfoMapFragment=new CarRepairInfoMapFragment();
//					carRepairInfoMapFragment.setReplaceByParentLayout(new OnReplace());
////				}
//				replaceContentFragment(carRepairInfoMapFragment);	
//				changeImage(0);
				
				carRepairInfoListFragment=new CarRepairInfoListFragment();
				carRepairInfoListFragment.setReplaceByParentLayout(new OnReplace());
				replaceContentFragment(carRepairInfoListFragment);
				changeImage(0);
				
				break;
	
			case 3:
//				Toast.makeText(this, R.string.kaifatishi, Toast.LENGTH_LONG).show();
				StartIntent.startIntent(this, CallHelpActivity.class);
				break;
				
			case 2:
				if(carRepairFavorFragment==null)
					carRepairFavorFragment=new CarRepairFavorFragment();
				replaceContentFragment(carRepairFavorFragment);
				changeImage(1);
				break;
				
			case 4:
				if(carRepairMoreFragment==null)
					carRepairMoreFragment=new CarRepairMoreFragment();
				replaceContentFragment(carRepairMoreFragment);
				changeImage(3);
				break;

		}
	}
	
	private void replaceContentFragment(Fragment fragment)
	{
		ManagerFragment.replaceFragment(this, R.id.driving_content, fragment);
	}
	
	class OnReplace implements ReplaceByParentLayoutInterface
	{
		@Override
		public void replace(int id)
		{
			if(id==1)
			{
//				if(carRepairInfoListFragment==null)
//				{
//					carRepairInfoListFragment=new CarRepairInfoListFragment();
//					carRepairInfoListFragment.setReplaceByParentLayout(new OnReplace());
//				}
//				replaceContentFragment(carRepairInfoListFragment);
				
				changeFragment(1);
			}
			else if(id==2)
			{
//				changeFragment(1);
				
				carRepairInfoMapFragment=new CarRepairInfoMapFragment();
				carRepairInfoMapFragment.setReplaceByParentLayout(new OnReplace());
				replaceContentFragment(carRepairInfoMapFragment);
			}
			else if(id==3)
			{
				changeFragment(2);
			}
		}
	}
	
	public void changeImage(int id)
	{
		int[] pressImages=new int[]{R.drawable.repair_message_pressed,R.drawable.school_favorites_pressed,R.drawable.repair_call_pressed,R.drawable.repair_more_pressed};
		int[] normalImages=new int[]{R.drawable.selector_repairl_messager,R.drawable.selector_repairl_favor,R.drawable.selector_repairl_call,R.drawable.selector_repairl_more};
		
		ImageView[] imageViews=new ImageView[4];
		imageViews[0]=(ImageView)findViewById(R.id.car_repair_point_info);
		imageViews[1]=(ImageView)findViewById(R.id.car_repair_favor);
		imageViews[2]=(ImageView)findViewById(R.id.car_repair_call_help);
		imageViews[3]=(ImageView)findViewById(R.id.car_repair_more);
		
		for(int i=0;i<4;i++)
		{
			if(id==i)
			{
				imageViews[i].setImageResource(pressImages[i]);
			}
			else
			{
				imageViews[i].setImageResource(normalImages[i]);
			}
		}
	}
}
