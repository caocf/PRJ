package com.example.smarttraffic.smartBus;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.about.AboutActivity;
import com.example.smarttraffic.common.complain.ComplainActivity;
import com.example.smarttraffic.common.introduce.IntroduceActivity;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

public class SmartBusMoreActivity extends FragmentActivity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_more);

		initHead(savedInstanceState);
	}

	private void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null)
		{
			HeadNameFragment headFragment = new HeadNameFragment();

			String nameString = "更多";
			headFragment.setTitleName(nameString);

			ManagerFragment.replaceFragment(this, R.id.smart_bus_more_head,
					headFragment);
		}
	}

	public void click(View v)
	{
		switch (v.getId())
		{
		case R.id.smart_bus_more_about:
			Bundle bundle = new Bundle();
			bundle.putInt(AboutActivity.ABOUT_ID, 9);
			StartIntent.startIntentWithParam(this, AboutActivity.class, bundle);
			break;

		case R.id.smart_bus_more_suggestion:
			Bundle suggesiontBundle = new Bundle();
			suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 9);
			StartIntent.startIntentWithParam(this, SuggestionActivity.class,
					suggesiontBundle);
			break;

		case R.id.smart_bus_more_complain:
			Bundle complainBundle = new Bundle();
			complainBundle.putInt(ComplainActivity.COMPLAIN_NAME, 9);
			StartIntent.startIntentWithParam(this, ComplainActivity.class,
					complainBundle);
			break;

		case R.id.smart_bus_more_info:
			Bundle b = new Bundle();
			b.putInt(IntroduceActivity.INTRODUCE_ID, 0);
			StartIntent.startIntentWithParam(this, IntroduceActivity.class, b);
			break;
		}
	}
}
