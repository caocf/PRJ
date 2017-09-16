package com.example.smarttraffic.map;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.R;
import com.example.smarttraffic.drivingSchool.OnPopInterface;

import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.control.CNMKAPImgr;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayMyLocation;
import cennavi.cenmapsdk.android.map.CNMKOverlayRoute;
import cennavi.cenmapsdk.android.search.CNMKAdminResult;
import cennavi.cenmapsdk.android.search.CNMKCategoryResult;
import cennavi.cenmapsdk.android.search.CNMKCityResult;
import cennavi.cenmapsdk.android.search.CNMKEncryptionResult;
import cennavi.cenmapsdk.android.search.CNMKGeocodingResult;
import cennavi.cenmapsdk.android.search.CNMKNewPoiResult;
import cennavi.cenmapsdk.android.search.CNMKPOIAroundResult;
import cennavi.cenmapsdk.android.search.CNMKPoiResult;
import cennavi.cenmapsdk.android.search.CNMKReverseGeocodingResult;
import cennavi.cenmapsdk.android.search.CNMKSearch;
import cennavi.cenmapsdk.android.search.CNMKStepWalkResult;
import cennavi.cenmapsdk.android.search.ICNMKSearchListener;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteInfo;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteReqParam;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteResult;
import cennavi.cenmapsdk.android.search.driver.CNMKNewDriveRouteResult;
import cennavi.cenmapsdk.android.search.driver.CNMKPaintStyle;
import cennavi.cenmapsdk.android.search.driver.CNMKPlanNode;
import cennavi.cenmapsdk.android.search.driver.CNMKRoute;
import cennavi.cenmapsdk.android.search.poi.CNMKNewPoiKeyReqParam;
import cennavi.cenmapsdk.android.search.poi.CNMKPoiKeyReqParam;
import cennavi.cenmapsdk.android.search.poi.CNMKReverseGeocodingReqParam;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class MapControl
{
	private final GeoPoint DEFAULT_GEO_POINT = new GeoPoint(30.758359,
			120.765362);

	private Activity activity;

	public MapControl(Activity activity)
	{
		this.activity = activity;
		registerBoot();
		registerSearch();
	}

	CenMapApiDemoApp app;

	public CenMapApiDemoApp getApp()
	{
		return app;
	}

	CNMKSearch search;
	CNMKMapView mapView;

	public void registerBoot()
	{
		app = (CenMapApiDemoApp) activity.getApplication();
		if (app.mCNMKAPImgr == null)
		{
			app.mCNMKAPImgr = CNMKAPImgr.createMgr(activity.getApplication());
			app.mCNMKAPImgr.init(app.mStrKey,
					new CenMapApiDemoApp.MyGeneralListener());
		}
		app.mCNMKAPImgr.start();
	}

	public void registerSearch()
	{
		search = app.mCNMKAPImgr.getSearcher();
		search.regListener(searchListener);
	}

	public static final int DEFULT_LEVEL = 14;

	public void initMap(CNMKMapView mapview)
	{
		this.mapView = mapview;

		this.mapView.setBuiltInZoomControls(false);
		this.mapView.setTraffic(false);
		this.mapView.setScale(true);
		// this.mapView.changeLanguage("zh");
		this.mapView.setDrawOverlayWhenZooming(true);
		this.mapView.getController().setCenter(DEFAULT_GEO_POINT);

		this.mapView.setZoomLevel(DEFULT_LEVEL);
	}

	ICNMKLocationListener locationListener = new ICNMKLocationListener()
	{
		public void onLocationChanged(CNMKLocation location)
		{
			if (location != null)
			{
				GeoPoint pt = new GeoPoint(
						(int) (location.getLatitude() * (1e6 * AA.LV)),
						(int) (location.getLongitude() * (1e6 * AA.LV)));
				mapView.getController().setCenter(pt);
			}
			deleteLocation();
		}

		public void onGPSStatusChanged(GpsStatus status)
		{
		}
	};

	public void setCenter(GeoPoint g)
	{
		mapView.getController().setCenter(g);

	}

	public void setLocationListener(ICNMKLocationListener locationListener)
	{
		this.locationListener = locationListener;
	}

	CNMKOverlayMyLocation mLocationOverlay;

	public void addLocationWithoutView()
	{
		app.mCNMKAPImgr.getLocationManager().requestLocationUpdates(
				locationListener);
	}

	public void removeLocationWithoutView()
	{
		app.mCNMKAPImgr.getLocationManager().removeUpdates(locationListener);
	}

	@SuppressWarnings("unchecked")
	public void addLocation()
	{
		mLocationOverlay = new CNMKOverlayMyLocation(activity, mapView);
		mLocationOverlay.enableMyLocation();
		mapView.getOverlays().add(mLocationOverlay);

		app.mCNMKAPImgr.getLocationManager().requestLocationUpdates(
				locationListener);
	}

	public void deleteLocation()
	{
		if (mLocationOverlay != null)
			mLocationOverlay.disableMyLocation();
		app.mCNMKAPImgr.getLocationManager().removeUpdates(locationListener);
	}

	// LocationClient client;
	// MapOverItem locationItem;
	// public void addLocation()
	// {
	// client=null;
	// client=new BaiduLocationClient(activity);
	//
	// client.location(new LocationListener()
	// {
	//
	// @Override
	// public void resultListener(LocationResult result)
	// {
	//
	// if(locationListener!=null)
	// {
	// CNMKLocation l=new CNMKLocation();
	// l.setLatitude(result.getLan());
	// l.setLongitude(result.getLon());
	//
	// locationListener.onLocationChanged(l);
	//
	// locationItem=createMapOverItem(activity, null, new GeoPoint[]{new
	// GeoPoint(result.getLan(), result.getLon())}, null,
	// R.drawable.map_new_location_1);
	// client.stop();
	// }
	//
	// }
	// });
	// }
	//
	// public void deleteLocation()
	// {
	// client.stop();
	// mapView.getOverlays().remove(locationItem);
	// }
	//
	// public void addLocationWithoutView()
	// {
	// client=null;
	// client=new BaiduLocationClient(activity);
	//
	// client.location(new LocationListener()
	// {
	//
	// @Override
	// public void resultListener(LocationResult result)
	// {
	//
	// if(locationListener!=null)
	// {
	// CNMKLocation l=new CNMKLocation();
	// l.setLatitude(result.getLan());
	// l.setLongitude(result.getLon());
	//
	// locationListener.onLocationChanged(l);
	// client.stop();
	// }
	//
	// }
	// });
	// }
	//
	// public void removeLocationWithoutView()
	// {
	// client.stop();
	// }
	//

	public void searchReverseGeo(GeoPoint geoPoint)
	{
		CNMKReverseGeocodingReqParam p = new CNMKReverseGeocodingReqParam();
		p.setLanguage(0);
		p.setSearchType(1);
		p.setRadius(100);
		p.setGeoPoint(geoPoint);

		search.reverseGeocoding(p);
	}

	// public int searchPOI(String name)
	// {
	// CNMKNewPoiKeyReqParam a_oParam1 =new CNMKNewPoiKeyReqParam();
	//
	// a_oParam1.setKey(name);
	// return search.newpoiSearch(a_oParam1);
	// }

	public void searchPOI(String POIName)
	{
		searchPOI(POIName, "330400", 1, 10, null, 0, "poi");
	}

	public void searchPOI(String POIName, String province, int page,
			int pageCount, ArrayList<Integer> catogy, int language, String type)
	{
		CNMKPoiKeyReqParam param = new CNMKPoiKeyReqParam();
		param.setAdcode(province);
		param.setCategory(catogy);
		param.setLanguage(language);
		param.setKey(POIName);
		param.setPageCount(pageCount);
		param.setPageNumber(page);
		param.setSearchtype(type);

		search.poiSearch(param);
	}

	public void searchDriving(GeoPoint start, GeoPoint end, int mode)
	{
		CNMKDriveRouteReqParam param = new CNMKDriveRouteReqParam();

		CNMKPlanNode node1 = new CNMKPlanNode();
		node1.setPos(start);

		CNMKPlanNode node2 = new CNMKPlanNode();
		node2.setPos(end);

		param.setStartPoint(node1);
		param.setEndPoint(node2);
		param.setCostModel(mode);

		search.drivingSearch(param);
	}

	SearchListener searchPoi;
	SearchListener searchDriving;
	SearchListener searchBus;
	SearchListener reverSearchListener;

	public void setReverSearchListener(SearchListener reverSearchListener)
	{
		this.reverSearchListener = reverSearchListener;
	}

	public void setSearchPoi(SearchListener searchPoi)
	{
		this.searchPoi = searchPoi;
	}

	public void setSearchDriving(SearchListener searchDriving)
	{
		this.searchDriving = searchDriving;
	}

	public void setSearchBus(SearchListener searchBus)
	{
		this.searchBus = searchBus;
	}

	ICNMKSearchListener searchListener = new ICNMKSearchListener()
	{
		@Override
		public void onGetTransitCityResult(CNMKCityResult arg0, int arg1,
				boolean arg2, String arg3)
		{

		}

		@Override
		public void onGetReverseGeoCodingResult(
				CNMKReverseGeocodingResult arg0, int arg1, boolean arg2,
				String arg3)
		{
			if (reverSearchListener != null)
				reverSearchListener.searchListener(arg0, arg1, arg2, arg3);
		}

		@Override
		public void onGetPoiResult(CNMKPoiResult arg0, int arg1, boolean arg2,
				String arg3)
		{
			if (searchPoi != null)
				searchPoi.searchListener(arg0, arg1, arg2, arg3);
		}

		@Override
		public void onGetPOIAroundResult(CNMKPOIAroundResult arg0, int arg1,
				boolean arg2, String arg3)
		{

		}

		@Override
		public void onGetGeoCodingResult(CNMKGeocodingResult arg0, int arg1,
				boolean arg2, String arg3)
		{

		}

		@Override
		public void onGetEncryptionResult(CNMKEncryptionResult arg0, int arg1,
				boolean arg2, String arg3)
		{

		}

		@Override
		public void onGetDriverRouteResult(CNMKDriveRouteResult arg0, int arg1,
				boolean arg2, String arg3)
		{
			if (searchDriving != null)
				searchDriving.searchListener(arg0, arg1, arg2, arg3);
		}

		@Override
		public void onGetCityResult(CNMKCityResult arg0, int arg1,
				boolean arg2, String arg3)
		{

		}

		@Override
		public void onGetCategorySearchResult(CNMKCategoryResult arg0,
				int arg1, boolean arg2, String arg3)
		{
		}

		@Override
		public void onGetAdminResult(CNMKAdminResult arg0, int arg1,
				boolean arg2, String arg3)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetNewDriverRouteResult(CNMKNewDriveRouteResult arg0,
				int arg1, boolean arg2, String arg3)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onGetStepWalkResult(CNMKStepWalkResult arg0, int arg1,
				boolean arg2, String arg3)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void onGetNewPoiResult(CNMKNewPoiResult arg0, int arg1,
				boolean arg2, String arg3)
		{
			if (searchPoi != null)
				searchPoi.searchListener(arg0, arg1, arg2, arg3);
		}
	};

	private final int DEFAULT_COLOR = Color.rgb(58, 107, 189);

	public void createLine(List<GeoPoint> data)
	{
		createLine(data, DEFAULT_COLOR);
	}

	@SuppressWarnings("unchecked")
	public void createLine(List<GeoPoint> data, int color)
	{
		CNMKOverlayRoute route = new CNMKOverlayRoute(activity, mapView);
		route.setPaintStyle(new CNMKPaintStyle(true, 10, color, -1));

		CNMKRoute cnmkRoute = new CNMKRoute();

		CNMKDriveRouteInfo info = new CNMKDriveRouteInfo();

		for (int i = 0; i < data.size(); i++)
		{
			info.addClist(data.get(i));
		}

		cnmkRoute.addStepList(info);

		route.setData(cnmkRoute);

		mapView.getOverlays().add(route);
		mapView.postInvalidate();

	}

	public void zoomIn()
	{
		mapView.getController().zoomIn();
	}

	public void zoomOut()
	{
		mapView.getController().zoomOut();
	}

	private MapOverItem mapOverItem;
	private final int MARKERID = R.drawable.map_new_red_1;

	public MapOverItem createMapOverItem(Context context, View view,
			GeoPoint[] points, OnPopInterface popInterface, int pic)
	{
		return createMapOverItem(context, view, points, popInterface, pic, true);
	}

	MarkMapOverItem markMapOverItem;
	
	public MarkMapOverItem createMarkMapOverItem(Context context, View view,
			List<MarkOverlayItem> points, OnPopInterface popInterface)
	{
		return createMarkMapOverItem(context, view, points, popInterface, true);
	}
	
	public MarkMapOverItem createMarkMapOverItem(Context context, View view,
			List<MarkOverlayItem> points, OnPopInterface popInterface,boolean translation)
	{
		Drawable marker = context.getResources().getDrawable(R.drawable.bike_green_1);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());

		if(translation)
		for (int i = 0; i < points.size(); i++)
		{
			GeoPoint temp = PointTraslation.transform(points.get(i)
					.getGeoPoint());

			points.get(i).setGeoPoint(temp);
		}

		markMapOverItem = new MarkMapOverItem(marker, context, mapView, view, points,popInterface);

		mapView.getOverlays().add(markMapOverItem);

		if (view != null)
		{
			view.setVisibility(View.GONE);
			mapView.addView(view, CNMKMapView.newLayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
					CNMKMapView.LayoutParams_TOP_LEFT));
		}
		return markMapOverItem;
	}

	@SuppressWarnings("unchecked")
	public MapOverItem createMapOverItem(Context context, View view,
			GeoPoint[] points, OnPopInterface popInterface, int pic,
			boolean translation)
	{
		Drawable marker = context.getResources().getDrawable(pic);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());

		GeoPoint[] result = null;
		if (points != null)
		{
			result = new GeoPoint[points.length];
			for (int i = 0; i < points.length; i++)
			{
				if (translation)
					result[i] = PointTraslation.transform(points[i]);
				else
					result[i] = points[i];
			}
		}

		mapOverItem = new MapOverItem(marker, context, mapView, view, result,
				popInterface);

		mapView.getOverlays().add(mapOverItem);

		if (view != null)
		{
			view.setVisibility(View.GONE);
			mapView.addView(view, CNMKMapView.newLayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
					CNMKMapView.LayoutParams_TOP_LEFT));
		}
		return mapOverItem;
	}

	public void createMapOverItem(Context context, View view,
			List<GeoPoint> points, OnPopInterface popInterface, int pic)
	{
		GeoPoint[] geoPoint = new GeoPoint[points.size()];

		for (int i = 0; i < points.size(); i++)
		{
			geoPoint[i] = points.get(i);
		}

		createMapOverItem(context, view, geoPoint, popInterface, pic, true);
	}

	public void createMapOverItem(Context context, View view,
			List<GeoPoint> points, OnPopInterface popInterface)
	{
		createMapOverItem(context, view, points, popInterface, MARKERID);
	}

	public void createMapOverItem(Context context, View view,
			GeoPoint[] points, OnPopInterface popInterface)
	{
		createMapOverItem(context, view, points, popInterface, MARKERID, true);
	}

	public void createMapOverItem(Context context, View view,
			GeoPoint[] points, OnPopInterface popInterface, boolean translation)
	{
		createMapOverItem(context, view, points, popInterface, MARKERID,
				translation);
	}

	public void createBikeMapOverItem(Context context, View view,
			GeoPoint[] points, OnPopInterface popInterface)
	{

	}

	@SuppressWarnings("unchecked")
	public void addMapChoicePoint(SelectPointResultListener listener)
	{
		mapView.getOverlays().add(new MapChoiceOverItem(listener));
	}

	public void clearOverlays()
	{
		mapView.getOverlays().clear();
	}
	
	public void onDestroy()
	{
		mapView=null;
	}
}
