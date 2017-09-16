//package com.example.smarttraffic.maptdt;
//
//import android.content.Context;
//import android.location.Location;
//
//import com.tianditu.android.maps.GeoPoint;
//import com.tianditu.android.maps.MapView;
//import com.tianditu.android.maps.MyLocationOverlay;
//
//public class DefaultMyLocation extends MyLocationOverlay
//{
//	Context context;
//	MapView mapView;
//
//	public DefaultMyLocation(Context context, MapView mapView)
//	{
//		super(context, mapView);
//		this.context=context;
//		this.mapView=mapView;
//	}
//
//	/*
//	 * 处理在"我的位置"上的点击事件
//	 */
//	protected boolean dispatchTap()
//	{
//		return true;
//	}
//
//	@Override
//	public void onLocationChanged(Location location)
//	{
//		super.onLocationChanged(location);
//		if (location != null)
//		{
//			GeoPoint point = new GeoPoint((int)location.getLatitude(), (int)location.getLongitude());
//			if (point != null)
//				mapView.getController().animateTo(point);
//		}
//		
//	}
//}
