package com.example.smarttraffic.map;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlay;


public class MapChoiceOverItem extends CNMKOverlay
{
	SelectPointResultListener listener;
	
	public MapChoiceOverItem(SelectPointResultListener listener)
	{
		this.listener=listener;
	}

	@Override
	public boolean onTap(GeoPoint arg0, CNMKMapView arg1)
	{
		if(listener!=null)
			listener.onPointResultListener(arg0);
		
		return super.onTap(arg0, arg1);
	}
	
	
}
