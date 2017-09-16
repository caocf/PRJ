package com.example.smarttraffic.train;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class TrainNoTicketsDetailActivity extends FragmentActivity implements UpdateView
{
	@Override
	public void update(Object data)
	{
		if(data instanceof TrainNoTickets)
		{
			 TrainNoTickets tickets=(TrainNoTickets)data;
			 firCitytextView.setText(tickets.getFirstCity());
			 latCitytextView.setText(tickets.getLastCity());
			 startCitytextView.setText(tickets.getStartCity());
			 endCitytextView.setText(tickets.getEndCity());
			 startTimetextView.setText(tickets.getStartTime());
			 endtIMEtextView.setText(tickets.getEndTime());
			 
			 fisNumtextView.setText(String.valueOf(tickets.getFirLeftNum()));
			 firPricetextView.setText(String.valueOf(tickets.getFirPrice()));
			 secNumtextView.setText(String.valueOf(tickets.getSecLeftNum()));
			 secPricetextView.setText(String.valueOf(tickets.getSecPrice()));
			 busNumtextView.setText(String.valueOf(tickets.getBusLeftNum()));
			 busPricetextView.setText(String.valueOf(tickets.getBusPrice()));
		}
	}

	
	public static final String TRAIN_SEARCH_NO="train_search_no";
	public static final String TRAIN_SEARCH_START_POINT="train_search_start_point";
	public static final String TRAIN_SEARCH_END_POINT="train_search_end_point";
	
	TrainNoTicketsRequest request;
	
	TextView firCitytextView;
	TextView latCitytextView;
	TextView startCitytextView;
	TextView endCitytextView;
	TextView startTimetextView;
	TextView endtIMEtextView;
	TextView fisNumtextView;
	TextView firPricetextView;
	TextView secNumtextView;
	TextView secPricetextView;
	TextView busNumtextView;
	TextView busPricetextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_train_no_tickets_detail);
		
		dealParam();
		
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName(request.getTrainNo());
		fragment.setRightName("时刻表");
		fragment.setRightListen(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				TrainNoRequest request1=new TrainNoRequest();
				request1.setBusNo(request.getTrainNo());
				
				Bundle bundle=new Bundle();
				bundle.putSerializable(SearchTrainFragment.SEARCH_NAME, request1);
				
				StartIntent.startIntentWithParam(TrainNoTicketsDetailActivity.this, TrainRealActivity.class, bundle);
			}
		});
		
		ManagerFragment.replaceFragment(this, R.id.train_no_head, fragment);
		
		initView();
		
		new HttpThread(new TrainNoTicketsSearch(),request,this,this,R.string.error_train_no_info).start();
	}
	
	private void initView()
	{
		 firCitytextView=(TextView)findViewById(R.id.train_no_first_city);
		 latCitytextView=(TextView)findViewById(R.id.train_no_last_city);
		 startCitytextView=(TextView)findViewById(R.id.train_no_start_city);
		 endCitytextView=(TextView)findViewById(R.id.train_no_end_city);
		 startTimetextView=(TextView)findViewById(R.id.train_no_start_time);
		 endtIMEtextView=(TextView)findViewById(R.id.train_no_end_time);
		 fisNumtextView=(TextView)findViewById(R.id.train_no_first_left);
		 firPricetextView=(TextView)findViewById(R.id.train_no_first_price);
		 secNumtextView=(TextView)findViewById(R.id.train_no_second_left);
		 secPricetextView=(TextView)findViewById(R.id.train_no_second_price);
		 busNumtextView=(TextView)findViewById(R.id.train_no_bus_left);
		 busPricetextView=(TextView)findViewById(R.id.train_no_bus_price);
	}
	
	private void dealParam()
	{
		request=new TrainNoTicketsRequest();
		request.setTrainNo(getIntent().getStringExtra(TRAIN_SEARCH_NO));
		request.setStart(getIntent().getStringExtra(TRAIN_SEARCH_START_POINT));
		request.setEnd(getIntent().getStringExtra(TRAIN_SEARCH_END_POINT));
	}
}
