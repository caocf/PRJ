package com.example.smarttraffic.airport;

import java.util.Calendar;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.ViewSetter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class AirNoActivity extends FragmentActivity implements UpdateView
{
	ListView listView;
	RealAirAdapter adapter;
	
	AirNoRequest request;
	
	@Override
	public void update(Object data) 
	{
		if(data instanceof AirNo)
		{
			 AirNo result=(AirNo)data;
			 
			 startCityTextView.setText(result.getStartCity());
			 startAirportTextView.setText(result.getStartAirport());
			 startTimeTextView.setText(result.getStartTime());
			 endCityTextView.setText(result.getEndCity());
			 endAirportTextView.setText(result.getEndAirport());
			 endTimeTextView.setText(result.getEndTime());
			 		 
			 costTimeTextView.setText(result.getCostTime());
			 
			 ViewSetter.setTextColor(ecoNumTextView, result.getEcoLeftNum());
			 ecoNumTextView.setText(result.getEcoLeftNum()+"张");
			 ecoPriceTextView.setText("￥"+result.getEcoPrice());
			 
			 ViewSetter.setTextColor(cusNumTextView, result.getCusLeftNum());
			 cusNumTextView.setText(result.getCusLeftNum()+"张");
			 cusPriceTextView.setText("￥"+result.getCusPrice());
			 
			 ViewSetter.setTextColor(firNumTextView, result.getFirLeftNum());
			 firNumTextView.setText(result.getFirLeftNum()+"张");
			 firPriceTextView.setText("￥"+result.getFirPrice());
		}
	}
	
	TextView startCityTextView;
	TextView startAirportTextView;
	TextView startTimeTextView;
	TextView endCityTextView;
	TextView endAirportTextView;
	TextView endTimeTextView;
	TextView costTimeTextView;
	TextView dateTextView;
	TextView ecoNumTextView;
	TextView ecoPriceTextView;
	TextView cusNumTextView;
	TextView cusPriceTextView;
	TextView firNumTextView;
	TextView firPriceTextView;
	
	public static final String SEARCH_AIR_NO="search_air_no";
	public static final String SEARCH_AIR_DATE="search_air_date";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_air_no_info);
		
		initParam();
		
		HeadFavorFragment headFragment=new HeadFavorFragment();
		headFragment.setTitleName(getIntent().getStringExtra(SEARCH_AIR_NO));
		headFragment.setRightName("正晚点");
		headFragment.setRightListen(realListener);
		ManagerFragment.replaceFragment(this, R.id.train_no_head, headFragment);
		
		initView();
		
		new HttpThread(new AirNoSearch(), request, this,this,R.string.error_air_no_info).start();
	}
	
	private OnClickListener realListener =new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			Bundle resultInfo=new Bundle();
			resultInfo.putString(AirNoRealActivity.AIR_REAL_NO_PARAM,request.getAirNo());
			StartIntent.startIntentWithParam(AirNoActivity.this, AirNoRealActivity.class,resultInfo);			
		}
	};
	
	public void initParam()
	{
		Intent intent=this.getIntent();
		String name=intent.getStringExtra(SEARCH_AIR_NO);
		Calendar calendar=(Calendar)intent.getSerializableExtra(SEARCH_AIR_DATE);
		request=new AirNoRequest();
		request.setAirNo(name);
		request.setCalendar(calendar);
	}
	
	private void initView()
	{
		 startCityTextView=(TextView)findViewById(R.id.train_no_first_city);
		 startAirportTextView=(TextView)findViewById(R.id.train_no_start_time);
		 startTimeTextView=(TextView)findViewById(R.id.train_no_start_city);
		 endCityTextView=(TextView)findViewById(R.id.train_no_last_city);
		 endAirportTextView=(TextView)findViewById(R.id.train_no_end_time);
		 endTimeTextView=(TextView)findViewById(R.id.train_no_end_city);
		 costTimeTextView=(TextView)findViewById(R.id.air_no_cost_time);
		 dateTextView=(TextView)findViewById(R.id.air_no_time);
		 ecoNumTextView=(TextView)findViewById(R.id.train_no_first_left);
		 ecoPriceTextView=(TextView)findViewById(R.id.train_no_first_price);
		 cusNumTextView=(TextView)findViewById(R.id.train_no_second_left);
		 cusPriceTextView=(TextView)findViewById(R.id.train_no_second_price);
		 firNumTextView=(TextView)findViewById(R.id.train_no_bus_left);
		 firPriceTextView=(TextView)findViewById(R.id.train_no_bus_price);
	}
}
