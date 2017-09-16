//package com.example.smarttraffic.maptdt;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.smarttraffic.R;
//import com.example.smarttraffic.drivingSchool.OnPopInterface;
//import com.tianditu.android.maps.GeoPoint;
//import com.tianditu.android.maps.MapView;
//import com.tianditu.android.maps.MyLocationOverlay;
//import com.tianditu.android.maps.Overlay;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.location.LocationManager;
//import android.view.View;
//
//public class MapControl
//{
//	private Activity activity;
//	
//	public MapControl(Activity activity)
//	{
//		this.activity=activity;
//		registerBoot();
//		registerSearch();
//	}
//	
//	CenMapApiDemoApp app;
//	
//	public CenMapApiDemoApp getApp()
//	{
//		return app;
//	}
//
//	MapView mapView;
//	
//	public void registerBoot()
//	{
//	}
//	
//	public void registerSearch()
//	{
//	}
//	
//	public static final int DEFULT_LEVEL=14;
//	public static final GeoPoint DEFAULT_CENTER=new GeoPoint(30756001,120763494);
//	
//	public void initMap(MapView mapview)
//	{
//		this.mapView=mapview;
//		
//		this.mapView.setBuiltInZoomControls(false);
//		this.mapView.setPlaceName(true);
//
//		this.mapView.getController().setZoom(DEFULT_LEVEL);
//		this.mapView.getController().setCenter(DEFAULT_CENTER);
//		this.locationListener = new DefaultMyLocation(activity,mapView);
//	}
//	
//	MyLocationOverlay locationListener; 
//	
//	public void setCenter(GeoPoint g)
//	{
//		mapView.getController().setCenter(g);
//	}
//	
//	public void setLocationListener(MyLocationOverlay locationListener)
//	{
//		this.locationListener=locationListener;
//	}
//
//	public void addLocation()
//	{		
//		List<Overlay> list = mapView.getOverlays();
//		locationListener.enableCompass();
//		locationListener.enableMyLocation();
//		list.add(locationListener);
//
//		LocationManager    m_locationManager = ( LocationManager ) activity.getSystemService( Context.LOCATION_SERVICE );
//		if(m_locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//		    m_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener); 
//		}
//	}
//	
//	public void deleteLocation()
//	{
//	}
//	
//	
//
//	
//	public void searchDriving(GeoPoint start,GeoPoint end,int mode)
//	{
//
//	}
//
//	private final int DEFAULT_COLOR=Color.rgb(58, 107, 189);
//	
//	public void zoomIn()
//	{
//		mapView.getController().zoomIn();
//	}
//	
//	public void zoomOut()
//	{
//		mapView.getController().zoomOut();
//	}
//	
//	private MapOverItem mapOverItem;
//	private final int MARKERID=R.drawable.map_new_red;
//	
//	public void createMapOverItem(Context context,View view,List<GeoPoint> points,OnPopInterface popInterface,int pic)
//	{
//		Drawable marker = context.getResources().getDrawable(pic);
//		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
//		
//		
//		mapOverItem=new MapOverItem(marker, context, mapView, view,points,popInterface);
//		
//		mapView.getOverlays().add(mapOverItem);
//	}
//	
//	public void createMapOverItem(Context context,View view,GeoPoint[] points,OnPopInterface popInterface,int pic)
//	{
//		List<GeoPoint> temp=new ArrayList<GeoPoint>();
//		for(GeoPoint g:points)
//		{
//			temp.add(g);
//		}
//		
//		createMapOverItem(context, view, temp, popInterface,pic);
//	}
//	
//	public void createMapOverItem(Context context,View view,List<GeoPoint> points,OnPopInterface popInterface)
//	{
//		createMapOverItem(context, view, points, popInterface, MARKERID);
//	}
//	
//	public void createMapOverItem(Context context,View view,GeoPoint[] points,OnPopInterface popInterface)
//	{
//		createMapOverItem(context, view, points, popInterface, MARKERID);
//	}
//	
//	public void clearOverlays()
//	{
//		mapView.getOverlays().clear();
//	}
//}
