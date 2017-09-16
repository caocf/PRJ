package com.example.smarttraffic.drivingSchool;

import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class DrivingHomeActivity extends FragmentActivity 
{
	DrivingInfoMapFragment drivingInfoMapFragment;
	DrivingInfoListFragment drivingInfoListFragment;
	DrivingStudentInfoFragment drivingStudentInfoFragment;
	DrivingFavorFragment drivingFavorFragment;
	
	DrivingMoreFragment drivingMoreFragment;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_driving_home);
		onReplace=new OnReplace();
		
		changeFragment(1);		
	}

	
	public void itemOnclick(View v)
	{
		switch (v.getId()) {
		case R.id.driving_school_info:
			changeFragment(1);
			break;
		case R.id.drving_student_info:
			changeFragment(2);
			break;
		case R.id.driving_favor:
			changeFragment(3);
			break;
		case R.id.driving_more:
			changeFragment(4);
			break;

		}
	}
	
	private void changeFragment(int i)
	{
		switch (i) 
		{
			case 1:
//				drivingInfoMapFragment=null;
//				drivingInfoMapFragment=new DrivingInfoMapFragment();
//				drivingInfoMapFragment.setReplaceByParentLayout(onReplace);
//
//				replaceContentFragment(drivingInfoMapFragment);
//				changeImage(0);
				
//				if(drivingInfoListFragment==null)
//				{
					drivingInfoListFragment=new DrivingInfoListFragment();
					drivingInfoListFragment.setReplaceByParentLayout(onReplace);
//				}
				replaceContentFragment(drivingInfoListFragment);
				changeImage(0);
				
				break;
			case 2:
//				Toast.makeText(this, R.string.kaifatishi, Toast.LENGTH_LONG).show();
				if(drivingStudentInfoFragment==null)
					drivingStudentInfoFragment=new DrivingStudentInfoFragment();
				replaceContentFragment(drivingStudentInfoFragment);
				changeImage(1);
				break;
			case 3:
				if(drivingFavorFragment==null)
					drivingFavorFragment=new DrivingFavorFragment();
				replaceContentFragment(drivingFavorFragment);
				changeImage(2);
				break;
			case 4:
				if(drivingMoreFragment==null)
					drivingMoreFragment=new DrivingMoreFragment();
				replaceContentFragment(drivingMoreFragment);
				changeImage(3);
				break;

		}
	}
	
	private void replaceContentFragment(Fragment fragment)
	{
		ManagerFragment.replaceFragment(this, R.id.driving_content, fragment);
	}
	
	OnReplace onReplace;
	
	class OnReplace implements ReplaceByParentLayoutInterface
	{
		@Override
		public void replace(int id)
		{
			if(id==1)
			{
				changeFragment(1);
//				if(drivingInfoListFragment==null)
//				{
//					drivingInfoListFragment=new DrivingInfoListFragment();
//					drivingInfoListFragment.setReplaceByParentLayout(onReplace);
//					drivingInfoListFragment.setLocationPoint(drivingInfoMapFragment.getLocationGeoPoint());
//				}
//				replaceContentFragment(drivingInfoListFragment);
			}
			else if(id==2)
			{
				drivingInfoMapFragment=null;
				drivingInfoMapFragment=new DrivingInfoMapFragment();
				drivingInfoMapFragment.setReplaceByParentLayout(onReplace);

				replaceContentFragment(drivingInfoMapFragment);
				changeImage(0);
				
//				changeFragment(1);
			}
			else if(id==3)
			{
				changeFragment(3);
			}
		}
	}
	
	public void changeImage(int id)
	{
		int[] pressImages=new int[]{R.drawable.school_message_pressed,R.drawable.school_service_pressed,R.drawable.school_favorites_pressed,R.drawable.repair_more_pressed};
		int[] normalImages=new int[]{R.drawable.selector_school_message,R.drawable.selector_school_service,R.drawable.selector_school_favor,R.drawable.selector_repairl_more};
		
		ImageView[] imageViews=new ImageView[4];
		imageViews[0]=(ImageView)findViewById(R.id.driving_school_info);
		imageViews[1]=(ImageView)findViewById(R.id.drving_student_info);
		imageViews[2]=(ImageView)findViewById(R.id.driving_favor);
		imageViews[3]=(ImageView)findViewById(R.id.driving_more);
		
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
