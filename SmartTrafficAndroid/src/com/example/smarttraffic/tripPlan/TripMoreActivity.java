package com.example.smarttraffic.tripPlan;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.about.AboutActivity;
import com.example.smarttraffic.common.complain.ComplainActivity;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

/**
 * 出行规划更多界面
 * @author Administrator zhou
 *
 */
public class TripMoreActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_trip_more);
		
		HeadNameFragment fragment=new HeadNameFragment();
		
		fragment.setTitleName("更多");
		ManagerFragment.replaceFragment(this,R.id.trip_more_head, fragment);
	}
	
	public void click(View v)
	{
		switch (v.getId())
		{
			case R.id.trip_more_about:
				Bundle bundle=new Bundle();
				bundle.putInt(AboutActivity.ABOUT_ID, 1);
				StartIntent.startIntentWithParam(this, AboutActivity.class, bundle);
				break;
			case R.id.trip_more_suggestion:
				Bundle suggesiontBundle=new Bundle();
				suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 1);
				StartIntent.startIntentWithParam(this, SuggestionActivity.class,suggesiontBundle);
				break;
				
			case R.id.trip_more_complain:
				Bundle complainBundle=new Bundle();
				complainBundle.putInt(ComplainActivity.COMPLAIN_NAME, 1);
				StartIntent.startIntentWithParam(this, ComplainActivity.class,complainBundle);
				break;
		}
	}
}
