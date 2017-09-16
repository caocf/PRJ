package com.example.smarttraffic.smartBus;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.alarm.AlarmLineSelectActivity;
import com.example.smarttraffic.bike.BikeStationDetailActivity;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfo;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfoTemp;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.bike.bean.BikeStationTemp;
import com.example.smarttraffic.bike.fragment.BikeStationInfoListFragment;
import com.example.smarttraffic.common.debug.DebugActivity;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.nearby.NearByActivity;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.adapter.BikeStationHistoryAdapter;
import com.example.smarttraffic.smartBus.adapter.StationLineAdapter;
import com.example.smarttraffic.smartBus.adapter.StationNearByAdapter;
import com.example.smarttraffic.smartBus.bean.BusStationDetail;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByName;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByNameTemp;
import com.example.smarttraffic.smartBus.bean.StationInfo;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.ListviewControl;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SmartBusStationDetailActivity extends FragmentActivity
{

	public static final String SMART_BUS_STATTON_ID = "smart_bus_station_id";
	public static final String SMART_BUS_STATION_ORIENT = "smart_bus_station_orient";

	public static final String SMART_BUS_STATION_LANTITUDE = "smart_bus_station_lantitude";
	public static final String SMART_BUS_STATION_LONGTITUDE = "smart_bus_station_longtitude";

	private final int DEFAULT_DISTANCE = 300;
	private final int DEFAULT_COUNT = 5;

	int stationID;
	String stationName;
	int orint;
	double lan;
	double lon;

	TextView nameTextView;
	TextView addressTextView;

	ListView lineListView;
	ListView nearbyStationListView;
	ListView nearbyBikeListView;
	BusStationDetail station;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_station_detail);

		initHead();
		initview();

		stationID = getIntent().getIntExtra(SMART_BUS_STATTON_ID, 0);
		orint = getIntent().getIntExtra(SMART_BUS_STATION_ORIENT, 1);

		lan = getIntent().getDoubleExtra(SMART_BUS_STATION_LANTITUDE, -1);
		lon = getIntent().getDoubleExtra(SMART_BUS_STATION_LONGTITUDE, -1);

		if (stationID != 0)
		{
			QueryStation(stationID, orint);
		}
	}

	private void initview()
	{
		nameTextView = (TextView) findViewById(R.id.smart_bus_station_detail_name);
		addressTextView = (TextView) findViewById(R.id.smart_bus_station_detail_address);
		lineListView = (ListView) findViewById(R.id.smart_bus_station_detail_line_listview);
		nearbyStationListView = (ListView) findViewById(R.id.smart_bus_station_nearby_station_listview);

		nearbyStationListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				BusStationForQueryByName temp = ((StationNearByAdapter) parent
						.getAdapter()).getData().get(position);

				Bundle bundle = new Bundle();
				bundle.putInt(SMART_BUS_STATTON_ID, temp.getId());
				bundle.putDouble(SMART_BUS_STATION_LANTITUDE,
						temp.getLatitude());
				bundle.putDouble(SMART_BUS_STATION_LONGTITUDE,
						temp.getLongitude());
				StartIntent.startIntentWithParam(
						SmartBusStationDetailActivity.this,
						SmartBusStationDetailActivity.class, bundle);

			}
		});

		nearbyBikeListView = (ListView) findViewById(R.id.smart_bus_station_nearby_bike_listview);
		nearbyBikeListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				BikeStation bikeStation = ((BikeStationHistoryAdapter) parent
						.getAdapter()).getData().get(position);

				Bundle bundle = new Bundle();
				bundle.putSerializable(
						BikeStationInfoListFragment.BIKE_STATION_INFO,
						bikeStation);
				StartIntent.startIntentWithParam(
						SmartBusStationDetailActivity.this,
						BikeStationDetailActivity.class, bundle);

			}
		});
	}

	private void initHead()
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("电子站牌");
		fragment.setRightName("地图");
		fragment.setRightListen(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bundle bundle = new Bundle();
				bundle.putDouble(
						SmartBusNearbyStationMapActivity.SMART_BUS_LINE_MAP_LAN,
						lan);
				bundle.putDouble(
						SmartBusNearbyStationMapActivity.SMART_BUS_LINE_MAP_LON,
						lon);

				StartIntent.startIntentWithParam(
						SmartBusStationDetailActivity.this,
						SmartBusNearbyStationMapActivity.class, bundle);
			}
		});

		ManagerFragment.replaceFragment(this,
				R.id.smart_bus_station_detail_head, fragment);
	}

	private void QueryNearbyStation()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				String data1 = DoString.parseThreeNetString(data);

				BusStationForQueryByNameTemp result = JSON.parseObject(data1,
						BusStationForQueryByNameTemp.class);

				for(int i=0;i<result.getStationList().size();i++)
				{
					if(result.getStationList().get(i).getId()==stationID)
					{
						result.getStationList().remove(i);
						break;
					}
				}
				
				return result;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.SMART_BUS_STATION_NEARBY_STATION_URL
						+ "?longtitude="
						+ lon
						+ "&lantitude="
						+ lan
						+ "&distance=" + DEFAULT_DISTANCE;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				BusStationForQueryByNameTemp busStationForQueryByNameTemp = (BusStationForQueryByNameTemp) data;
				StationNearByAdapter stationHistoryAdapter = new StationNearByAdapter(
						SmartBusStationDetailActivity.this,
						busStationForQueryByNameTemp.getStationList());
				nearbyStationListView.setAdapter(stationHistoryAdapter);

				ListviewControl
						.setListViewHeightBasedOnChildren(nearbyStationListView);

				QueryNearbyBike();
			}
		}).start();
	}

	private void QueryNearbyBike()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				BikeStationTemp result = JSON.parseObject(
						DoString.parseThreeNetString(data),
						BikeStationTemp.class);

				return result;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.SMART_BUS_STATION_NEARBY_BIKE_URL
						+ "?longtitude=" + lon + "&lantitude=" + lan
						+ "&distance=" + DEFAULT_DISTANCE + "&count="
						+ DEFAULT_COUNT;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				bikeStations = ((BikeStationTemp) data)
						.getStationList();
				
				searchReal();
				
			}
		}).start();
	}
	
	List<BikeStation> bikeStations;
	
	private void searchReal()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String d)
			{
				BikeParkingPlaceInfoTemp temp = JSON.parseObject(
						DoString.parseThreeNetString(d),
						BikeParkingPlaceInfoTemp.class);

				for (BikeParkingPlaceInfo b : temp.getList())
				{
					for (BikeStation s : bikeStations)
					{
						if (b.getStationid() == s.getId())
						{
							s.setLeft(b.getCount());
							break;
						}
					}
				}

				return bikeStations;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				String result = HttpUrlRequestAddress.SMART_BIKE_STATION_REAL_BY_ID_URL
						+ "?bikeList=";

				for (int i = 0; i < bikeStations.size(); i++)
				{
					if (i > 0)
						result += ",";
					result += bikeStations.get(i).getId();
				}

				return result;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object d)
			{
				
				nearbyBikeListView
				.setAdapter(new BikeStationHistoryAdapter(
						SmartBusStationDetailActivity.this,
						bikeStations, true));

		ListviewControl
				.setListViewHeightBasedOnChildren(nearbyBikeListView);
			}
		}).start();
	}
	
	

	private void QueryStation(int id, int o)
	{
		this.stationID = id;
		this.orint = o;
		new HttpThread(new BaseSearch()
		{

			@Override
			public Object parse(String data)
			{
				System.out.println(data);
				
				BusStationDetail station = new BusStationDetail();

				station = JSON.parseObject(DoString.parseThreeNetString(data),
						BusStationDetail.class);

				orint=station.getLineList().get(0).getDirect();
				
				return station;

			}
		}, new BaseRequest()
		{

			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.SMART_BUS_STATION_BY_ID_URL
						+ "?stationID=" + stationID;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				station = (BusStationDetail) data;

				stationName = station.getStation().getName();
				nameTextView.setText(station.getStation().getName());
				addressTextView.setText(station.getStation().getName());
				lineListView.setAdapter(new StationLineAdapter(
						SmartBusStationDetailActivity.this, station
								.getLineList()));
				lineListView.setOnItemClickListener(new realOnclickListener());

				ListviewControl.setListViewHeightBasedOnChildren(lineListView);

				// 第一次进入时判定是否已收藏
				isFavor();

				QueryNearbyStation();
			}
		}, this, R.string.error_smart_bus_station_detail_info).start();
	}

	class realOnclickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			Bundle bundle = new Bundle();
			bundle.putInt(
					StationRealActivity.STATION_REAL_LINE_ID,
					((StationLineAdapter) parent.getAdapter()).getData()
							.get(position).getId());
			bundle.putInt(StationRealActivity.STATION_REAL_STARIONT_ID,
					stationID);
			bundle.putString(StationRealActivity.STATION_REAL_STARIONT_NAME,
					stationName);
			bundle.putString(
					StationRealActivity.STATION_REAL_LINE_NAME,
					((StationLineAdapter) parent.getAdapter()).getData()
							.get(position).getName());

			bundle.putInt(StationRealActivity.STATION_REAL_ORIENT, ((StationLineAdapter) parent.getAdapter()).getData()
					.get(position).getDirect());

			StartIntent.startIntentWithParam(
					SmartBusStationDetailActivity.this,
					StationRealActivity.class, bundle);
		}
	}

	private void isFavor()
	{
		StationInfo stationInfo = new StationInfo();
		stationInfo.setId(station.getStation().getId());
		stationInfo.setName(station.getStation().getName());

		String[] lines = new String[station.getLineList().size()];

		for (int i = 0; i < lines.length; i++)
		{
			lines[i] = station.getLineList().get(i).getName();
		}

		stationInfo.setLines(lines);

		FavorDBOperate dbOperate = new FavorDBOperate(this);
		if (dbOperate.isFavor(ContentType.SMART_BUS_STATION_FAVOR, stationInfo))
		{
			((ImageView) findViewById(R.id.smart_bus_station_detail_favor))
					.setImageResource(R.drawable.smart_bus_favor_checked);
		} else
		{
			((ImageView) findViewById(R.id.smart_bus_station_detail_favor))
					.setImageResource(R.drawable.smart_bus_favor);
		}
	}

	private void saveFavor()
	{
		StationInfo stationInfo = new StationInfo();
		stationInfo.setId(station.getStation().getId());
		stationInfo.setName(station.getStation().getName());

		String[] lines = new String[station.getLineList().size()];

		for (int i = 0; i < lines.length; i++)
		{
			lines[i] = station.getLineList().get(i).getName();
		}

		stationInfo.setLines(lines);

		FavorDBOperate dbOperate = new FavorDBOperate(this);
		if (dbOperate.isFavor(ContentType.SMART_BUS_STATION_FAVOR, stationInfo))
		{
			((ImageView) findViewById(R.id.smart_bus_station_detail_favor))
					.setImageResource(R.drawable.smart_bus_favor);
			dbOperate.deleteFavor(ContentType.SMART_BUS_STATION_FAVOR,
					stationInfo);
			Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
		} else
		{
			dbOperate.insertStation(ContentType.SMART_BUS_STATION_FAVOR,
					stationInfo);
			((ImageView) findViewById(R.id.smart_bus_station_detail_favor))
					.setImageResource(R.drawable.smart_bus_favor_checked);
			Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
		}
	}

	public void onclick(View v)
	{
		switch (v.getId())
		{
		case R.id.smart_bus_station_detail_go_there:
			Bundle bundle = new Bundle();
			bundle.putDouble(GoThereMapActivity.GO_THERE_LANTITUDE, lan);
			bundle.putDouble(GoThereMapActivity.GO_THERE_LONGTITUDE, lon);

			StartIntent.startIntentWithParam(this, GoThereMapActivity.class,
					bundle);
			break;

		case R.id.smart_bus_station_detail_refresh:
			QueryStation(stationID, orint);
			break;

//		case R.id.smart_bus_station_detail_exchange:
//			orint = orint == 1 ? 2 : 1;
//			QueryStation(stationID, orint);
//			break;

		case R.id.smart_bus_station_detail_clock:

			Bundle b = new Bundle();
			b.putInt(AlarmLineSelectActivity.SMART_BUS_STATTON_ID, stationID);
			StartIntent.startIntentWithParam(this,
					AlarmLineSelectActivity.class, b);

			break;

		case R.id.smart_bus_station_detail_circle:
			StartIntent.startIntent(this, NearByActivity.class);
			break;

		case R.id.smart_bus_station_detail_favor:
			saveFavor();
			break;

		case R.id.smart_bus_station_detail_suggestion:
			Bundle suggesiontBundle = new Bundle();
			suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 9);
			StartIntent.startIntentWithParam(this, SuggestionActivity.class,
					suggesiontBundle);
			break;

		case R.id.smart_bus_station_detail_debug:
			Bundle debugBundle = new Bundle();
			debugBundle.putInt(DebugActivity.DEBUG_NAME, 9);
			StartIntent.startIntentWithParam(this, DebugActivity.class,
					debugBundle);
			break;
		}
	}
}
