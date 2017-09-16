package com.example.smarttraffic.map;

import cennavi.cenmapsdk.android.GeoPoint;



/**
 * 地图选点结果监听类
 * @author Administrator zwc
 *
 */
public interface SelectPointResultListener
{
	public void onPointResultListener(GeoPoint geoPoint);
}
