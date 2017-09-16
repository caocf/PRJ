package com.example.smarttraffic.busStation;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class BusRealActivity extends FragmentActivity implements UpdateView
{
	ListView listView;
	RealBusAdapter adapter;
	
	BusNoRequest request;
	
	TextView startCityTextView;
	TextView endCityTextView;
	TextView startStationTextView;
	TextView endStationTextView;
	TextView lengthTextView;
	
	@Override
	public void update(Object data) 
	{
		if(data instanceof Bus)
		{
			Bus bus=(Bus)data;
			
			startCityTextView.setText(bus.getStartCity());
			startStationTextView.setText(bus.getStartStation());
			endStationTextView.setText(bus.getEndStation());
			endCityTextView.setText(bus.getEndCity());
			
			if(listView.getAdapter()==null)
			{
				if(request.getType()==1)
				{
					adapter=new RealBusAdapter(this, bus.getOrders());
				}
				else if(request.getType()==0)
				{
					adapter=new RealBusAdapter(this, bus.getOrders(),2);
				}
				listView.setAdapter(adapter);
			}
			else 
			{
				adapter.update(((Bus) data).getOrders());
			}
			initHead(request.getBusNo());
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bus_real);
		
		Intent intent=this.getIntent();
		request=(BusNoRequest)intent.getSerializableExtra(SearchBusFragment.SEARCH_NAME);
		
		initHead(request.getBusNo());
		
		initView();
		
		new HttpThread(new BusNoSearch(), request, this,this,R.string.error_bus_real_info).start();
	}
	
//	public void showAndHidden()
//	{
//		if(request.getType()==0)
//		{
//			findViewById(R.id.bus_real_layout_1).setVisibility(View.GONE);
//			findViewById(R.id.bus_real_layout_2).setVisibility(View.VISIBLE);
//			
//		}
//		else
//		{
//			findViewById(R.id.bus_real_layout_2).setVisibility(View.GONE);
//			findViewById(R.id.bus_real_layout_1).setVisibility(View.VISIBLE);
//		}
//	}
	
	public void initHead(String name)
	{
		if(request.getType()==0)
		{
			HeadFavorFragment fragment=new HeadFavorFragment();
			fragment.setTitleName(name+"时刻表");
			fragment.setRightName("正晚点");
			fragment.setRightListen(new View.OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					request.setType(1);
					listView.setAdapter(null);
					new HttpThread(new BusNoSearch(), request,BusRealActivity.this,BusRealActivity.this,R.string.error_bus_real_info).start();
				}
			});
			
			ManagerFragment.replaceFragment(this, R.id.bus_no_real_time_head, fragment);
		}
		else
		{
			HeadNameFragment headFragment=new HeadNameFragment();
			headFragment.setTitleName(name+"正晚点信息");
			ManagerFragment.replaceFragment(this, R.id.bus_no_real_time_head, headFragment);
		}
	}
	
	private void initView()
	{
		listView=(ListView)findViewById(R.id.bus_no_listview);
		startCityTextView=(TextView)findViewById(R.id.train_no_first_city);
		endCityTextView=(TextView)findViewById(R.id.train_no_last_city);
		startStationTextView=(TextView)findViewById(R.id.train_no_start_city);
		endStationTextView=(TextView)findViewById(R.id.train_no_end_city);
		lengthTextView=(TextView)findViewById(R.id.air_no_cost_time);
		
	}
}
