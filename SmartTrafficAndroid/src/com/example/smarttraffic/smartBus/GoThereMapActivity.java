package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayRoute;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteResult;
import cennavi.cenmapsdk.android.search.driver.CNMKPaintStyle;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.PointTraslation;
import com.example.smarttraffic.map.SearchListener;
import com.example.smarttraffic.smartBus.bean.Transfer;
import com.example.smarttraffic.view.ExchangeView;

import android.graphics.Color;
import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;

public class GoThereMapActivity extends FragmentActivity
{

	public static final String GO_THERE_LANTITUDE = "go_there_lantitude";
	public static final String GO_THERE_LONGTITUDE = "go_there_longtitude";

	double lan1;
	double lon1;
	double lan2;
	double lon2;

	CNMKMapView mapView;
	MapControl mapControl;

	ExchangeView exchangeView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_detail_map);

		initHead();

		mapView = (CNMKMapView) findViewById(R.id.base_mapview);

		exchangeView = (ExchangeView) findViewById(R.id.transfer_detail_map_exchange);

		mapControl = new MapControl(this);
		mapControl.initMap(mapView);
		mapControl.setSearchDriving(new drivingListener());

		lan2 = getIntent().getDoubleExtra(GO_THERE_LANTITUDE, -1);
		lon2 = getIntent().getDoubleExtra(GO_THERE_LONGTITUDE, -1);

		endGeoPoint = new GeoPoint(lan2, lon2);
		
		endGeoPoint=PointTraslation.transform(endGeoPoint);

		mapControl.setLocationListener(new ICNMKLocationListener()
		{

			@Override
			public void onLocationChanged(CNMKLocation location)
			{
				if (location != null)
				{
					lan1 = location.getLatitude();
					lon1 = location.getLongitude();

					startGeoPoint = new GeoPoint(lan1, lon1);

					GeoPoint myLocation = new GeoPoint((int) (location
							.getLatitude() * (1e6 * AA.LV)), (int) (location
							.getLongitude() * (1e6 * AA.LV)));
					mapView.getController().setCenter(myLocation);
					
					search();
					
					System.out.println("jjjj:"+lan1+" "+lon1+" "+lan2+" "+lon2);
				}

				mapControl.deleteLocation();
			}

			@Override
			public void onGPSStatusChanged(GpsStatus arg0)
			{

			}
		});
		mapControl.addLocation();

	}
	
	class drivingListener implements SearchListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void searchListener(Object oResult, int id, boolean error,
				String arg3)
		{
			CNMKDriveRouteResult res=(CNMKDriveRouteResult)oResult;
			if (error || res == null || res.getRouteCount() ==0||res.getRoute(0).getDistsum().getValue()==0) 
			{
				Toast.makeText(GoThereMapActivity.this, "未查到该线路", Toast.LENGTH_SHORT).show();
				return;
			}	
			
			try
			{
//				CNMKDriveRouteResult routeResult;
				CNMKOverlayRoute routeOverlay;
				
//				routeResult=res;
						
			    routeOverlay = new CNMKOverlayRoute(GoThereMapActivity.this, mapView);
	
				routeOverlay.setPaintStyle(new CNMKPaintStyle(true,10,Color.rgb(58, 107, 189),-1));
				
			    routeOverlay.setData(res.getRoute(0));
			    mapView.getOverlays().clear();
			    mapView.getOverlays().add(routeOverlay);
			    mapView.postInvalidate();
			    
			    
			}
			catch(Exception e)
			{
				System.out.println("jjjj:"+e);
				
//				Toast.makeText(TripLineMapActivity.this, "查找结果出错", Toast.LENGTH_LONG).show();
			}
		}
	}

	private void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("路线规划");

		ManagerFragment.replaceFragment(this, R.id.transfer_detail_map_head,
				fragment);
	}

	private GeoPoint startGeoPoint;
	private GeoPoint endGeoPoint;
	
	public void search()
	{
//		System.out.println("lan1:"+lan1+" lon1:"+lon1+" lan2:"+lan2+" lon2:"+lon2);
		
		mapControl.searchDriving(new GeoPoint(lan1, lon1),endGeoPoint,2);
	}

//	private void addPoint()
//	{
//		mapControl.createMapOverItem(this,
//				getLayoutInflater().inflate(R.layout.map_pop_layout, null),
//				new GeoPoint[] { startGeoPoint }, null,
//				R.drawable.map_start_point);
//		mapControl.createMapOverItem(this,
//				getLayoutInflater().inflate(R.layout.map_pop_layout, null),
//				new GeoPoint[] { endGeoPoint }, null, R.drawable.map_end_point);
//	}
//
//	@SuppressWarnings("unchecked")
//	private void showLine(List<Transfer> data)
//	{
//		CNMKOverlayRoute route = new CNMKOverlayRoute(this, mapView);
//		route.setPaintStyle(new CNMKPaintStyle(true, 5,
//				Color.rgb(58, 107, 189), -1));
//
//		CNMKRoute cnmkRoute = new CNMKRoute();
//
//		CNMKDriveRouteInfo info = new CNMKDriveRouteInfo();
//
//		Transfer transfer = data.get(0);
//		List<GeoPoint> points = new ArrayList<GeoPoint>();
//
//		for (int i = 0; i < transfer.getTransferLines().size(); i++)
//		{
//			points.addAll(transfer.getTransferLines().get(i).getGeoPoints());
//		}
//
//		for (int i = 0; i < points.size(); i++)
//		{
//			info.addClist(points.get(i));
//		}
//
//		cnmkRoute.addStepList(info);
//
//		route.setData(cnmkRoute);
//
//		mapView.getOverlays().add(route);
//		mapView.postInvalidate();
//	}
	
	

//	public void search()
//	{
//		new HttpThread(new BaseSearch()
//		{
//			@Override
//			public Object parse(String data)
//			{
//				System.out.println(data);
//
//				List<Transfer> result = parseData(data);
//				return result;
//			}
//		}, new BaseRequest()
//		{
//			@Override
//			public String CreateUrl()
//			{
//				String url = "http://115.231.73.253:8091/zhjtapi/bus/queryBusTransfer?";
//				url += "startPointLat=" + lan1;
//				url += "&startPointLon=" + lon1;
//				url += "&endPointLat=" + lan2;
//				url += "&endPointLon=" + lon2;
//
//				System.out.println(url);
//
//				return url;
//			}
//		}, new UpdateView()
//		{
//			@SuppressWarnings("unchecked")
//			@Override
//			public void update(Object data)
//			{
//				showLine((List<Transfer>) data);
//				addPoint();
//			}
//		}, this, R.string.error_smart_bus_go_there_info).start();
//	}

	public List<Transfer> parseData(String data)
	{
		List<Transfer> result = new ArrayList<Transfer>();
//
//		try
//		{
//			JSONObject jsonObject = new JSONObject(data);
//			JSONArray array = jsonObject.getJSONArray("busTransfers");
//
//			for (int i = 0; i < array.length(); i++)
//			{
//				jsonObject = array.getJSONObject(i);
//				Transfer temp = new Transfer();
//
//				temp.setRouteName(jsonObject.getString("routeName"));
//				temp.setTotalDistance(jsonObject.getDouble("totalDistance"));
//				temp.setTotalPrice(jsonObject.getDouble("totalPrice"));
//				temp.setTotalTime(jsonObject.getDouble("totaltime"));
//
//				JSONArray lineJsonArray = jsonObject
//						.getJSONArray("busTransferContents");
//
//				List<TransferLine> transferLines = new ArrayList<TransferLine>();
//				for (int j = 0; j < lineJsonArray.length(); j++)
//				{
//					JSONObject lineJsonObject = lineJsonArray.getJSONObject(j);
//					TransferLine transferLine = new TransferLine();
//
//					String geo = lineJsonObject.getString("coordinate");
//					List<GeoPoint> geoPoints = new ArrayList<GeoPoint>();
//
//					String[] strs1 = geo.split(";");
//
//					for (int z = 0; z < strs1.length; z++)
//					{
//						String[] strs2 = strs1[z].split(",");
//						double lan = Double.valueOf(strs2[1]);
//						double lon = Double.valueOf(strs2[0]);
//						GeoPoint tempGeoPoint = new GeoPoint(lan, lon);
//						geoPoints.add(tempGeoPoint);
//					}
//
//					transferLine.setGeoPoints(geoPoints);
//
//					transferLine.setDistance(lineJsonObject
//							.getDouble("distance"));
//					transferLine.setPrice(lineJsonObject.getDouble("price"));
//					transferLine.setRouteName(lineJsonObject
//							.getString("routeName"));
//					transferLine.setStationCount(lineJsonObject
//							.getInt("stationCount"));
//					transferLine.setStationLan(lineJsonObject
//							.getDouble("stationLatitude"));
//					transferLine.setStationLon(lineJsonObject
//							.getDouble("stationLongitude"));
//					transferLine.setStationName(lineJsonObject
//							.getString("stationName"));
//
//					transferLines.add(transferLine);
//				}
//
//				temp.setTransferLines(transferLines);
//
//				JSONArray walkJsonArray = jsonObject
//						.getJSONArray("walkContents");
//
//				List<WalkLine> walkLines = new ArrayList<WalkLine>();
//				for (int k = 0; k < walkJsonArray.length(); k++)
//				{
//					WalkLine walkLine = new WalkLine();
//					JSONObject walkObject = walkJsonArray.getJSONObject(k);
//
//					walkLine.setDirection(walkObject.getString("direction"));
//					walkLine.setDistance(walkObject.getDouble("distance"));
//					walkLine.setStationName(walkObject.getString("stationName"));
//					walkLine.setStationLatitude(walkObject
//							.getDouble("stationLatitude"));
//					walkLine.setStationLongitude(walkObject
//							.getDouble("stationLongitude"));
//
//					walkLines.add(walkLine);
//				}
//
//				temp.setWalkLines(walkLines);
//
//				result.add(temp);
//			}
//		} catch (JSONException e)
//		{
//			e.printStackTrace();
//		}
//
		return result;
	}

	@Override
	protected void onPause()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();
		
		if (mapView != null)
		{
			mapView.destory();
			mapView = null;
		}
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
