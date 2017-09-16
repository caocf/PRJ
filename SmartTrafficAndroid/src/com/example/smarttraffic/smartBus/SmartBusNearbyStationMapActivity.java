package com.example.smarttraffic.smartBus;

import java.util.List;

import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.drivingSchool.OnPopInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.PointTraslation;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByName;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByNameTemp;
import com.example.smarttraffic.smartBus.bean.LineOnStation;
import com.example.smarttraffic.smartBus.parse.StationNearbyRequest;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;

import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 周边公交站点 默认定位当前 或传入指定中心点
 * 
 * @author Administrator zhou
 * 
 */
public class SmartBusNearbyStationMapActivity extends FragmentActivity
{
	MapControl mapControl;
	CNMKMapView mapView;

	private int DEFAULT_COUNT = 500;

	String dataTemp;
	double lan;
	double lon;

	public static final String SMART_BUS_LINE_MAP_DATA = "smart_bus_line_map_data";
	public static final String SMART_BUS_LINE_MAP_LAN = "smart_bus_line_map_lan";
	public static final String SMART_BUS_LINE_MAP_LON = "smart_bus_line_map_lon";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_line_map);

		initHead();

		mapView = (CNMKMapView) findViewById(R.id.base_mapview);

		mapControl = new MapControl(this);
		mapControl.initMap(mapView);
		
		mapView.getController().setZoomLevel(16);

		lan = getIntent().getDoubleExtra(SMART_BUS_LINE_MAP_LAN, -1);
		lon = getIntent().getDoubleExtra(SMART_BUS_LINE_MAP_LON, -1);

		if (lan > 0 && lon > 0)
		{
			request();
			mapView.getController().setCenter(new GeoPoint(lan, lon));
		} else
		{
			mapControl.setLocationListener(new ICNMKLocationListener()
			{

				@Override
				public void onLocationChanged(CNMKLocation location)
				{
					if (location != null)
					{
						lan = location.getLatitude();
						lon = location.getLongitude();

						GeoPoint myLocation = new GeoPoint((int) (location
								.getLatitude() * (1e6 * AA.LV)),
								(int) (location.getLongitude() * (1e6 * AA.LV)));
						mapView.getController().setCenter(myLocation);
					}
					mapControl.deleteLocation();

					transfor();

					request();
				}

				@Override
				public void onGPSStatusChanged(GpsStatus arg0)
				{

				}
			});
			mapControl.addLocation();
		}

	}

	/**
	 * 02转84
	 */
	private void transfor()
	{
		GeoPoint point1 = PointTraslation.gcj_To_Gps84(lan, lon);

		lan = point1.getLatitudeE6() * 1.0 / 1e6;
		lon = point1.getLongitudeE6() * 1.0 / 1e6;
	}

	private void request()
	{
		new HttpThread(new BaseSearch()
		{

			@Override
			public Object parse(String data)
			{
				String data1 = DoString.parseThreeNetString(data);

				dataTemp = data1;

				BusStationForQueryByNameTemp result = JSON.parseObject(data1,
						BusStationForQueryByNameTemp.class);

				return result;
			}
		}, new StationNearbyRequest(lan, lon, DEFAULT_COUNT), new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				BusStationForQueryByNameTemp result = (BusStationForQueryByNameTemp) data;

				busList = result.getStationList();

				if ((busList == null || busList.size() == 0)&& DEFAULT_COUNT==500)
				{
					DEFAULT_COUNT=2000;
					
					request();
				} else
				{

					GeoPoint[] geoPoint = new GeoPoint[busList.size()];

					for (int i = 0; i < busList.size(); i++)
					{
						geoPoint[i] = new GeoPoint(
								busList.get(i).getLatitude(), busList.get(i)
										.getLongitude());
					}

					mapControl.createMapOverItem(
							SmartBusNearbyStationMapActivity.this,
							getLayoutInflater().inflate(
									R.layout.map_pop_layout, null), geoPoint,
							new onPopClick());
					mapControl.createMapOverItem(
							SmartBusNearbyStationMapActivity.this,
							getLayoutInflater().inflate(
									R.layout.map_pop_layout, null),
							new GeoPoint[] { new GeoPoint(lan, lon) }, null,
							R.drawable.map_new_location_1);
				}
			}
		}).start();
	}

	List<BusStationForQueryByName> busList;

	class onPopClick implements OnPopInterface
	{
		int i;

		@Override
		public View onPop(int i, View v)
		{
			this.i = i;
			if (busList != null)
			{
				TextView name = (TextView) v
						.findViewById(R.id.driving_school_name);
				TextView info = (TextView) v
						.findViewById(R.id.driving_school_information);
				ImageView goView = (ImageView) v
						.findViewById(R.id.driving_get_there);

				name.setText(busList.get(i).getName());

				String lineName = "";
				for (int j = 0; j < busList.get(i).getList().size(); j++)
				{
					if (j == 5)
					{
						lineName += "....";
						break;
					}

					if (j > 0)
						lineName += ",";
					LineOnStation l = busList.get(i).getList().get(j);
					lineName += l.getName();

				}

				info.setText(lineName);
				
				//goView.setVisibility(View.GONE);
				goView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Bundle bundle = new Bundle();
						bundle.putDouble(GoThereMapActivity.GO_THERE_LANTITUDE,
								busList.get(onPopClick.this.i).getLatitude());
						bundle.putDouble(
								GoThereMapActivity.GO_THERE_LONGTITUDE, busList
										.get(onPopClick.this.i).getLongitude());

						StartIntent.startIntentWithParam(
								SmartBusNearbyStationMapActivity.this,
								GoThereMapActivity.class, bundle);
					}
				});
			}

			return v;
		}
	}

	private void initHead()
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("附近站点");
		fragment.setRightName("列表");
		fragment.setRightListen(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Bundle bundle = new Bundle();
				bundle.putString(SmartBusNearbyStationListActivity.DATA,
						dataTemp);
				StartIntent.startIntentWithParam(
						SmartBusNearbyStationMapActivity.this,
						SmartBusNearbyStationListActivity.class, bundle);
			}
		});

		ManagerFragment.replaceFragment(this, R.id.smart_bus_line_map_head,
				fragment);
	}
	
	@Override
	protected void onDestroy()
	{
		if (mapView != null)
		{
			mapView.destory();
			mapView = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();
		
		
		
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.start();

		super.onResume();
	}
}
