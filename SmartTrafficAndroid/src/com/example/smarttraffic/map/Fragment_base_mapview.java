package com.example.smarttraffic.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smarttraffic.R;

import cennavi.cenmapsdk.android.map.CNMKMapFragment;
import cennavi.cenmapsdk.android.map.CNMKMapView;

public class Fragment_base_mapview extends CNMKMapFragment
{
	CNMKMapView mMapView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView=inflater.inflate(R.layout.fragment_mapview, container, false);
		mMapView=(CNMKMapView)rootView.findViewById(R.id.base_mapview);

		mMapView.changeLanguage("zh");
		super.setMapView(mMapView);
		initMapActivity();
		mMapView.setBuiltInZoomControls(true);
		mMapView.setDrawOverlayWhenZooming(true);
				
		return rootView;
	}
	
	
}
