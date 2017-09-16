package com.example.smarttraffic.smartBus;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.smartBus.adapter.StationNearByAdapter;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByNameTemp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class SmartBusNearbyStationListActivity extends FragmentActivity
{

	public static final String DATA = "data";
	ListView listView;
	String data;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_nearby_station_list);

		data = getIntent().getStringExtra(DATA);

		initHead();
		initList();
	}

	private void initList()
	{
		listView = (ListView) findViewById(R.id.smart_bus_nearby_station_list);

		BusStationForQueryByNameTemp result = JSON.parseObject(data,
				BusStationForQueryByNameTemp.class);

		listView.setAdapter(new StationNearByAdapter(this, result
				.getStationList(), true, true));
	}

	private void initHead()
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("附近站点");
		fragment.setRightName("地图");
		fragment.setRightListen(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SmartBusNearbyStationListActivity.this.finish();

			}
		});

		ManagerFragment.replaceFragment(this,
				R.id.smart_bus_nearby_station_list_head, fragment);
	}
}
