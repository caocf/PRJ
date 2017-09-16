package com.example.smarttraffic.smartBus;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.adapter.StationForRealAdapter;
import com.example.smarttraffic.smartBus.bean.BusArrive;
import com.example.smarttraffic.util.DoString;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class StationRealActivity extends FragmentActivity
{

	public static String STATION_REAL_STARIONT_ID = "station_real_station_id";
	public static String STATION_REAL_LINE_ID = "station_real_line_id";
	public static String STATION_REAL_ORIENT = "station_real_orient";

	public static String STATION_REAL_STARIONT_NAME = "station_real_station_name";
	public static String STATION_REAL_LINE_NAME = "station_real_line_name";

	int stationID;
	int lineID;
	int orient;
	String stationName;
	String lineName;

	private final int DEFAULT_COUTN = 10;

	TextView contentTextView;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_station_real);

		stationID = getIntent().getIntExtra(STATION_REAL_STARIONT_ID, 0);
		lineID = getIntent().getIntExtra(STATION_REAL_LINE_ID, 0);
		orient = getIntent().getIntExtra(STATION_REAL_ORIENT, 1);
		stationName = getIntent().getStringExtra(STATION_REAL_STARIONT_NAME);
		lineName = getIntent().getStringExtra(STATION_REAL_LINE_NAME);

		initHead();

		contentTextView = (TextView) findViewById(R.id.smart_bus_station_real_content);
		listView = (ListView) findViewById(R.id.smart_bus_station_real_list);

		search();
	}

	private void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("电子站牌");
		ManagerFragment.replaceFragment(this, R.id.smart_bus_station_real_head,
				fragment);
	}

	private void search()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				System.out.println("data:"+data);
				
				BusArrive busArrive = new BusArrive();
				busArrive = JSON.parseObject(
						DoString.parseThreeNetString(data), BusArrive.class);
				return busArrive;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{						
				return HttpUrlRequestAddress.SMART_BUS_STATION_REAL_INFO_URL
						+ "?stationID=" + stationID + "&lineID=" + lineID
						+ "&direct=" + orient + "&count=" + DEFAULT_COUTN;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				BusArrive arrive = (BusArrive) data;

				contentTextView.setText(lineName + "(" + stationName + ")");
				listView.setAdapter(new StationForRealAdapter(StationRealActivity.this, arrive.getBusLocationList()));

			}
		}, this, R.string.error_smart_bus_station_real).start();
	}

	public void onclick(View v)
	{
		switch (v.getId())
		{

		case R.id.smart_bus_station_real_refresh:
			search();
			break;

//		case R.id.smart_bus_station_real_exchange:
//			orient = orient == 1 ? 1 : 0;
//			search();
//			break;
		}
	}
}
