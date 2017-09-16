package com.example.smarttraffic.airport;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.TextView;


public class AirNoRealActivity extends FragmentActivity implements UpdateView
{
	
	public void update(Object data)
	{
		if(data instanceof AirNoReal)
		{
			 AirNoReal airNoReal=(AirNoReal)data;
			
			 startCityTextView.setText(airNoReal.getStartCity());
			 endCityTextView.setText(airNoReal.getEndCity());
			 startAirportTextView.setText(airNoReal.getStartAirport());
			 endAirportTextView.setText(airNoReal.getEndAirport());
//			 dateTextView.setText(airNoReal.get);
			 airKindTextView.setText(airNoReal.getAirKind());
			 startPlanTextView.setText(airNoReal.getStartPlanTime());
			 startRealTextView.setText(airNoReal.getStartRealTime());
			 endPlanTextView.setText(airNoReal.getEndPlanTime());
			 endRealTextView.setText(airNoReal.getEndRealTime());
		}
	}

	TextView startCityTextView;
	TextView endCityTextView;
	TextView startAirportTextView;
	TextView endAirportTextView;
	TextView dateTextView;
	TextView airKindTextView;
	TextView startPlanTextView;
	TextView startRealTextView;
	TextView endPlanTextView;
	TextView endRealTextView;
	
	public static final String AIR_REAL_NO_PARAM="air_real_no_param";
	
	AirNoRealRequest request;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_air_no_real);
		
		String airno=getIntent().getStringExtra(AIR_REAL_NO_PARAM);
		
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName(airno);
		ManagerFragment.replaceFragment(this, R.id.air_no_real_head, fragment);
		
		request=new AirNoRealRequest();
		request.setAirNo(airno);
		
		new HttpThread(new AirNoRealSearch(), request, this,this,R.string.error_air_no_real_info).start();
		
		initView();
		
	}
	
	public void initView()
	{
		 startCityTextView=(TextView)findViewById(R.id.air_no_real_start_city);
		 endCityTextView=(TextView)findViewById(R.id.air_no_real_end_city);
		 startAirportTextView=(TextView)findViewById(R.id.air_no_real_start_airport);
		 endAirportTextView=(TextView)findViewById(R.id.air_no_real_end_airport);
		 dateTextView=(TextView)findViewById(R.id.air_no_real_time);
		 airKindTextView=(TextView)findViewById(R.id.air_no_real_kind);
		 startPlanTextView=(TextView)findViewById(R.id.air_no_real_plan_starttime);
		 startRealTextView=(TextView)findViewById(R.id.air_no_real_real_starttime);
		 endPlanTextView=(TextView)findViewById(R.id.air_no_real_plan_endtime);
		 endRealTextView=(TextView)findViewById(R.id.air_no_real_real_endtime);
	}
}
