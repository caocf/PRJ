package com.example.smarttraffic.smartBus;

import java.util.List;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.parse.PoiRequest;
import com.example.smarttraffic.common.parse.PoiSearch;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.nearby.NearBy;
import com.example.smarttraffic.nearby.NearbyDetailListAdapter;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.adapter.StationNearByAdapter;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByName;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByNameTemp;
import com.example.smarttraffic.smartBus.db.SmartBusHistoryDB;
import com.example.smarttraffic.smartBus.parse.StationNearbyRequest;
import com.example.smarttraffic.smartBus.parse.StationNearbySearch;
import com.example.smarttraffic.smartBus.parse.StationSuggestRequest;
import com.example.smarttraffic.smartBus.parse.StationSuggestSearch;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 查询站点activity
 * 先查相似点
 * 如无查poi点
 * poi查出后查周边点
 * 
 * @author Administrator zhou
 * 
 */
public class SelectStationActivity extends FragmentActivity
{
	String name;
	ListView content;

	public static final String SELECT_STATION_NAME = "select_station_name";
	public static final String SEQ = "seq";

	public static final String LAN = "lan";
	public static final String LON = "lon";

	public static final int DEFAULT = 500;

	int seq;
	double lan;
	double lon;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_point);

		name = getIntent().getStringExtra(SELECT_STATION_NAME);
		seq = getIntent().getIntExtra(SEQ, 1);

		lan = getIntent().getDoubleExtra(LAN, -1);
		lon = getIntent().getDoubleExtra(LON, -1);

		if (seq == 1)
		{
			initHead("\"" + name + "\"相关站点");
			requestSimilar();
		} else if (seq == 3)
		{
			seq = 1;
			initHead("\"" + name + "\"附近相关站点");

			if (lan > 0 && lon > 0)
				requestNearby();
		}

		initListView();
	}

	/**
	 * 初始化头部
	 * 
	 * @param str
	 */
	private void initHead(String str)
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName(str);
		ManagerFragment.replaceFragment(this, R.id.smart_bus_select_point_head,
				fragment);
	}

	/**
	 * 初始化列表
	 */
	private void initListView()
	{
		content = (ListView) findViewById(R.id.smart_bus_select_point_content);
		content.setOnItemClickListener(contentClickListener);
	}

	/**
	 * 请求相似站点
	 */
	private void requestSimilar()
	{
		new HttpThread(new StationSuggestSearch(), new StationSuggestRequest(
				name), new UpdateView()
		{
			@Override
			public void update(Object data)
			{
				if (data == null
						|| ((BusStationForQueryByNameTemp) data)
								.getStationList().size() == 0)
				{
					seq = 2;
					requestPOI();
				} else
				{
					BusStationForQueryByNameTemp temp = (BusStationForQueryByNameTemp) data;
					StationNearByAdapter stationNearByAdapter = new StationNearByAdapter(
							SelectStationActivity.this, temp.getStationList());
					content.setAdapter(stationNearByAdapter);
				}
			}
		}).start();
	}

	/**
	 * 请求周边
	 */
	private void requestNearby()
	{
		new HttpThread(new StationNearbySearch(), new StationNearbyRequest(lan,
				lon, DEFAULT), new UpdateView()
		{
			@Override
			public void update(Object data)
			{
				BusStationForQueryByNameTemp result = (BusStationForQueryByNameTemp) data;

				content.setAdapter(new StationNearByAdapter(
						SelectStationActivity.this, result.getStationList()));
			}
		},this,R.string.error_smart_bus_no_nearby).start();
	}

	/**
	 * 请求poi
	 */
	private void requestPOI()
	{
		new HttpThread(new PoiSearch(), new PoiRequest(name), new UpdateView()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				content.setAdapter(new NearbyDetailListAdapter(
						SelectStationActivity.this, (List<NearBy>) data, false));

			}
		}, this, R.string.error_smart_bus_station_info).start();
	}

	OnItemClickListener contentClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long ids)
		{
			if (seq == 2)
			{
				NearbyDetailListAdapter adapter = ((NearbyDetailListAdapter) parent
						.getAdapter());

				Bundle bundle = new Bundle();
				bundle.putString(SELECT_STATION_NAME,
						name);
				bundle.putDouble(LAN, adapter.getData().get(position).getLan());
				bundle.putDouble(LON, adapter.getData().get(position).getLon());
				bundle.putInt(SEQ, 3);

				System.out.println("lan=" + adapter.getData().get(position).getLan());
				System.out.println("lon=" + adapter.getData().get(position).getLon());

				StartIntent.startIntentWithParam(SelectStationActivity.this,
						SelectStationActivity.class, bundle);

			} else if (seq == 1)
			{
				StationNearByAdapter adapter = ((StationNearByAdapter) parent
						.getAdapter());

				saveHistory(adapter.getData().get(position));

				Bundle bundle = new Bundle();
				bundle.putInt(
						SmartBusStationDetailActivity.SMART_BUS_STATTON_ID,
						adapter.getData().get(position).getId());
				bundle.putDouble(
						SmartBusStationDetailActivity.SMART_BUS_STATION_LANTITUDE,
						adapter.getData().get(position).getLatitude());
				bundle.putDouble(
						SmartBusStationDetailActivity.SMART_BUS_STATION_LONGTITUDE,
						adapter.getData().get(position).getLongitude());
				StartIntent.startIntentWithParam(SelectStationActivity.this,
						SmartBusStationDetailActivity.class, bundle);

			}
		}
	};

	/**
	 * 保存历史记录
	 * 
	 * @param stationInfo
	 */
	private void saveHistory(BusStationForQueryByName stationInfo)
	{
		if (stationInfo != null)
		{
			SmartBusHistoryDB dbOperate = new SmartBusHistoryDB(this);

			if (!dbOperate.isHistory(stationInfo))
			{
				dbOperate.insert(stationInfo);
			}
			dbOperate.CloseDB();
		}
	}
}
