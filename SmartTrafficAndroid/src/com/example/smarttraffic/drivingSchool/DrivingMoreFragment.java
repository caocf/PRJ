package com.example.smarttraffic.drivingSchool;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.about.AboutActivity;
import com.example.smarttraffic.common.complain.ComplainActivity;
import com.example.smarttraffic.common.introduce.IntroduceActivity;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class DrivingMoreFragment extends Fragment
{

	TextView suggestTextView;
	TextView complainTextView;
	TextView baseTextView;
	TextView aboutTextView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView=inflater.inflate(R.layout.fragment_driving_more, container, false);
		
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("更多");
		ManagerFragment.replaceFragment(getActivity(), R.id.driving_more_head, fragment);
		
		initView(rootView);
		
		return rootView;
	}
	
	private void initView(View rootView)
	{
		suggestTextView=(TextView)rootView.findViewById(R.id.driving_more_suggest);
		complainTextView=(TextView)rootView.findViewById(R.id.driving_more_complain);
		baseTextView=(TextView)rootView.findViewById(R.id.driving_more_base);
		aboutTextView=(TextView)rootView.findViewById(R.id.driving_more_about);
		
		onclick click=new onclick();
		suggestTextView.setOnClickListener(click);
		complainTextView.setOnClickListener(click);
		baseTextView.setOnClickListener(click);
		aboutTextView.setOnClickListener(click);
	}
	
	class onclick implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
	
			case R.id.driving_more_complain:
				Bundle complainBundle=new Bundle();
				complainBundle.putInt(ComplainActivity.COMPLAIN_NAME, 6);
				StartIntent.startIntentWithParam(getActivity(), ComplainActivity.class,complainBundle);

				break;
				
			case R.id.driving_more_base:
				Bundle infoBundle=new Bundle();
				infoBundle.putInt(IntroduceActivity.INTRODUCE_ID, 2);
				StartIntent.startIntentWithParam(getActivity(), IntroduceActivity.class, infoBundle);
				break;
	
			case R.id.driving_more_about:
				Bundle bundle=new Bundle();
				bundle.putInt(AboutActivity.ABOUT_ID, 6);
				StartIntent.startIntentWithParam(getActivity(),AboutActivity.class,bundle);
				break;
				
			case R.id.driving_more_suggest:
				Bundle suggesiontBundle=new Bundle();
				suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 6);
				StartIntent.startIntentWithParam(getActivity(), SuggestionActivity.class,suggesiontBundle);
				break;
		}
		}
		
	}
	
	
}
