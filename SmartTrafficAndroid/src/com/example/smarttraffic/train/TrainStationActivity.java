package com.example.smarttraffic.train;

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
import android.widget.Toast;

/**
 * 铁路信息主界面
 * @author Administrator zhou
 *
 */
public class TrainStationActivity extends FragmentActivity {

	SearchTicketFragment searchTicketFragment;
	SearchTrainFragment searchBusFragment;
	SearchStationFragment searchStationFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_train_station);
		
		initHead(savedInstanceState);
		
		changeFragment(1);		
	}

	public void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null) 
        {
    		HeadFragment headFragment=new HeadFragment();
    		
    		String nameString;
    		nameString=getResources().getStringArray(R.array.array_main_content)[7];
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
				searchBusFragment=new SearchTrainFragment();
			}
			replaceTheFragment(searchBusFragment);
			break;	
		case 3:
//			Toast.makeText(this, R.string.kaifatishi, Toast.LENGTH_LONG).show();
			if(searchStationFragment==null)
			{
				searchStationFragment=new SearchStationFragment();
			}
			replaceTheFragment(searchStationFragment);
			break;
		case 4:
			Toast.makeText(this, R.string.kaifatishi, Toast.LENGTH_LONG).show();
			break;
		case 5:
			StartIntent.startIntent(this,TrainMoreActivity.class);
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
				break;
			case R.id.bus_station_by_name:
				changeFragment(3);
				changeImage(2);
				break;
			case R.id.bus_tickets_order:
				changeFragment(4);
				break;
			case R.id.bus_tickets_more:
				changeFragment(5);
				break;
		}
	}
	
	public void changeImage(int id)
	{
		int[] press={R.drawable.train_station_station_pressed,R.drawable.train_no_search_pressed,R.drawable.train_station_search_pressed,R.drawable.train_tickets_order_pressed,R.drawable.more_pressed_1};
		int[] normal={R.drawable.train_station_station_normal,R.drawable.train_no_search_normal,R.drawable.train_station_search_normal,R.drawable.train_tickets_order_normal,R.drawable.more_normal_1};
		
		ImageView[] imageViews=new ImageView[5];
		imageViews[0]=(ImageView)findViewById(R.id.trip_driving_self_date);
		imageViews[1]=(ImageView)findViewById(R.id.bus_ticket_by_no);
		imageViews[2]=(ImageView)findViewById(R.id.bus_station_by_name);
		imageViews[3]=(ImageView)findViewById(R.id.bus_tickets_order);
		imageViews[4]=(ImageView)findViewById(R.id.bus_tickets_more);
		
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
