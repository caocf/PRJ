package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayRoute;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteInfo;
import cennavi.cenmapsdk.android.search.driver.CNMKPaintStyle;
import cennavi.cenmapsdk.android.search.driver.CNMKRoute;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.drivingSchool.OnPopInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.PointTraslation;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.bean.BusLineDetail;
import com.example.smarttraffic.smartBus.bean.StationOnLine;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SmartBusLineMapActivity extends FragmentActivity
{
	MapControl mapControl;
	CNMKMapView mapView;

	String data;
	String name;
	int direct;
	int lineID;

	public static final String SMART_BUS_LINE_MAP_DATA = "smart_bus_line_map_data";
	public static final String SMART_BUS_LINE_MAP_NAME = "smart_bus_line_map_name";
	public static final String SMART_BUS_LINE_MAP_ID = "smart_bus_line_map_id";
	public static final String SMART_BUS_LINE_MAP_DIRECT = "smart_bus_line_map_dircet";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_line_map);

		data = getIntent().getStringExtra(SMART_BUS_LINE_MAP_DATA);
		name = getIntent().getStringExtra(SMART_BUS_LINE_MAP_NAME);
		direct = getIntent().getIntExtra(SMART_BUS_LINE_MAP_DIRECT, 1);
		lineID = getIntent().getIntExtra(SMART_BUS_LINE_MAP_ID, -1);
		initHead(name);

		mapView = (CNMKMapView) findViewById(R.id.base_mapview);

		mapControl = new MapControl(this);
		mapControl.initMap(mapView);
		mapControl.addLocation();

		if (lineID != -1)
			search();
	}

	private void search()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				String d = DoString.parseThreeNetString(data);

				System.out.println(d);

				try
				{
					JSONObject jsonObject = new JSONObject(d);
					String temp = jsonObject.getJSONObject("busCoordinate").getString("coordinate");

					points = new ArrayList<GeoPoint>();

					String[] strs1 = temp.split(";");

					for (int z = 0; z < strs1.length; z++)
					{
						String[] strs2 = strs1[z].split(",");
						double lan = Double.valueOf(strs2[1]);
						double lon = Double.valueOf(strs2[0]);
						GeoPoint tempGeoPoint = new GeoPoint(lan, lon);
						points.add(tempGeoPoint);
					}

					return points;
				} catch (JSONException e)
				{
					e.printStackTrace();
				}

				return null;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				String result = HttpUrlRequestAddress.SMART_BUS_QUERY_ORIGN_LINE_TRACK_URL
						+ "?lineID=" + lineID + "&direct=" + direct;

				System.out.println(result);

				return result;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object d)
			{
				parseData(data);
				addLineOverlay();
				addPointOverlay();
			}
		}, this, R.string.error_smart_bus_line_trace).start();
	}

	private void parseData(String data)
	{
		BusLineDetail busLineDetail = new BusLineDetail();
		busLineDetail = JSON.parseObject(data, BusLineDetail.class);

		busStations = direct == 1 ? busLineDetail.getUpList() : busLineDetail
				.getDownList();

		// points=new ArrayList<GeoPoint>();
		//
		// for(int i=0;i<busStations.size();i++)
		// {
		// points.add(new
		// GeoPoint(busStations.get(i).getLatitude(),busStations.get(i).getLongitude()));
		// }
	}

	List<GeoPoint> points;
	List<StationOnLine> busStations;

	private void addPointOverlay()
	{
		if (busStations == null)
			return;

		GeoPoint[] geoPoints = new GeoPoint[busStations.size()];
		for (int i = 0; i < busStations.size(); i++)
		{
			geoPoints[i] = new GeoPoint(busStations.get(i).getLatitude(),
					busStations.get(i).getLongitude());
		}

//		mapControl.createMapOverItem(this,
//				getLayoutInflater().inflate(R.layout.map_pop_layout, null),
//				geoPoints, new onPopClick(), R.drawable.smart_bus_point);
		mapControl.createMapOverItem(this,
				getLayoutInflater().inflate(R.layout.map_pop_layout, null),
				geoPoints, new onPopClick());
	}

	class onPopClick implements OnPopInterface
	{
		int i;

		@Override
		public View onPop(int i, View v)
		{
			this.i = i;
			if (busStations != null)
			{
				
				
				TextView name = (TextView) v
						.findViewById(R.id.driving_school_name);
				TextView info = (TextView) v
						.findViewById(R.id.driving_school_information);
				ImageView goView = (ImageView) v
						.findViewById(R.id.driving_get_there);

				info.setVisibility(View.GONE);
				
				name.setText(busStations.get(i).getStationName());

				//goView.setVisibility(View.GONE);
				goView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Bundle bundle = new Bundle();
						bundle.putDouble(GoThereMapActivity.GO_THERE_LANTITUDE,
								busStations.get(onPopClick.this.i)
										.getLatitude());
						bundle.putDouble(
								GoThereMapActivity.GO_THERE_LONGTITUDE,
								busStations.get(onPopClick.this.i)
										.getLongitude());

						StartIntent.startIntentWithParam(
								SmartBusLineMapActivity.this,
								GoThereMapActivity.class, bundle);
					}
				});
			}

			return v;
		}
	}

	@SuppressWarnings("unchecked")
	private void addLineOverlay()
	{
		if (points == null)
			return;

		CNMKOverlayRoute route = new CNMKOverlayRoute(this, mapView);
		route.setPaintStyle(new CNMKPaintStyle(true, 10,
				Color.rgb(58, 107, 189), -1));

		CNMKRoute cnmkRoute = new CNMKRoute();

		CNMKDriveRouteInfo info = new CNMKDriveRouteInfo();

		for (int i = 0; i < points.size(); i++)
		{
			info.addClist(points.get(i));
			//info.addClist(PointTraslation.transform(points.get(i)));
		}

		cnmkRoute.addStepList(info);

		route.setData(cnmkRoute);

		mapView.getOverlays().add(route);
		mapView.postInvalidate();
	}

	private void initHead(String name)
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName(name);
		fragment.setRightName("列表");
		fragment.setRightListen(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				SmartBusLineMapActivity.this.finish();
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
