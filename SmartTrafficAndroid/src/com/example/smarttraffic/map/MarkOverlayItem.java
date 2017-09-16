package com.example.smarttraffic.map;

import cennavi.cenmapsdk.android.GeoPoint;

public class MarkOverlayItem
{
	private GeoPoint geoPoint;
	
	private int markid;
	
	public void setGeoPoint(GeoPoint geoPoint)
	{
		this.geoPoint = geoPoint;
	}
	public void setMarkid(int markid)
	{
		this.markid = markid;
	}
	public GeoPoint getGeoPoint()
	{
		return geoPoint;
	}
	public int getMarkid()
	{
		return markid;
	}
}
