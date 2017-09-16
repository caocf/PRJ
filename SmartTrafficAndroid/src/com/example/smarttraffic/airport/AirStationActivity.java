package com.example.smarttraffic.airport;

import com.example.smarttraffic.HeadFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class AirStationActivity extends FragmentActivity {

	SearchTicketFragment searchTicketFragment;
	SearchAirFragment searchBusFragment;
	SearchAirportFragment searchStationFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_air_station);
		
		initHead(savedInstanceState);
		
		changeFragment(1);		
	}

	public void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null) 
        {
    		HeadFragment headFragment=new HeadFragment();
    		
    		String nameString;
    		nameString=getResources().getStringArray(R.array.array_main_content)[8];
    		headFragment.setTitleName(nameString);
    		
    		ManagerFragment.replaceFragment(this, R.id.busstation_head, headFragment);
        }
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if(searchTicketFragment!=null)
			searchTicketFragment.onActivityResult(arg0, arg1, arg2);
	}

	public void changeFragment(int fragmentID)
	{
		switch (fragmentID) {
		case 1:
			if(searchTicketFragment==null)
			{
				searchTicketFragment=new SearchTicketFragment();
			}
			replaceTheFragment(searchTicketFragment);
			break;

		case 2:
			if(searchBusFragment==null)
			{
				searchBusFragment=new SearchAirFragment();
			}
			replaceTheFragment(searchBusFragment);
			break;	
		case 3:
			if(searchStationFragment==null)
			{
				searchStationFragment=new SearchAirportFragment();
			}
			replaceTheFragment(searchStationFragment);
			break;

		case 4:
			StartIntent.startIntent(this,AirMoreActivity.class);
			break;
		}	   
	}
	
	public void replaceTheFragment(Fragment fragment)
	{
		ManagerFragment.replaceFragment(this, R.id.busstation_content, fragment);
	}
	
	public void select_onclick(View v)
	{
		switch (v.getId()) 
		{
			case R.id.trip_driving_self_date:
				changeFragment(1);
				changeImage(0);
				break;
			case R.id.bus_ticket_by_no:
				changeFragment(2);
				changeImage(1);
				
//				Toast.makeText(this, R.string.kaifatishi, Toast.LENGTH_SHORT).show();
				break;
			case R.id.bus_station_by_name:
				changeFragment(3);
				changeImage(2);
				
//				Toast.makeText(this, R.string.kaifatishi, Toast.LENGTH_SHORT).show();
				break;
			case R.id.bus_tickets_more:
				changeFragment(4);
//				changeImage(3);
				break;
		}
	}
	
	public void changeImage(int id)
	{
		int[] press={R.drawable.air_station_station_pressed,R.drawable.air_no_pressed,R.drawable.air_port_pressed,R.drawable.air_more__pressed};
		int[] normal={R.drawable.air_station_station_normal,R.drawable.air_no_normal,R.drawable.air_port_normal,R.drawable.air_more_normal};
		
		ImageView[] imageViews=new ImageView[4];
		imageViews[0]=(ImageView)findViewById(R.id.trip_driving_self_date);
		imageViews[1]=(ImageView)findViewById(R.id.bus_ticket_by_no);
		imageViews[2]=(ImageView)findViewById(R.id.bus_station_by_name);
		imageViews[3]=(ImageView)findViewById(R.id.bus_tickets_more);
		
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
