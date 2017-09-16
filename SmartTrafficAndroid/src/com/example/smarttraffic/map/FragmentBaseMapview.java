package com.example.smarttraffic.map;

import android.location.GpsStatus;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.smarttraffic.R;

import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapFragment;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayMyLocation;

public class FragmentBaseMapview extends CNMKMapFragment
{

	public static FragmentBaseMapview fragmentBaseMapview = new FragmentBaseMapview();

	public CNMKMapView mMapView;
	View rootView;

//	public MapControl mapControl;

	ImageView location;
	ImageView reduce;
	ImageView enlarge;

	CenMapApiDemoApp app;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
//		boolean isnull = false;
//
//		if (rootView == null)
//		{
			rootView = inflater.inflate(R.layout.fragment_mapview, null);
//
//			isnull = true;
//		}
//
//		ViewGroup viewGroup = (ViewGroup) rootView.getParent();
//		if (viewGroup != null)
//			viewGroup.removeView(rootView);

	
			mMapView = (CNMKMapView) rootView.findViewById(R.id.base_mapview);

			mMapView.changeLanguage("zh");
			super.setMapView(mMapView);
			initMapActivity();
			mMapView.setBuiltInZoomControls(false);
			mMapView.setDrawOverlayWhenZooming(true);

			initview(rootView);
			
//			mapControl = new MapControl(getActivity());
//			mapControl.initMap(mMapView);
//
//			mapControl.clearOverlays();
			
			app = (CenMapApiDemoApp) getActivity().getApplication();
		

		return rootView;
	}

	private void initview(View v)
	{
		location = (ImageView) v.findViewById(R.id.base_mapview_location);
		reduce = (ImageView) v.findViewById(R.id.base_mapview_reduce);
		enlarge = (ImageView) v.findViewById(R.id.base_mapview_enlarge);

		location.setOnClickListener(listener);
		reduce.setOnClickListener(listener);
		enlarge.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.base_mapview_location:
				addLocation();
				break;

			case R.id.base_mapview_reduce:
				mMapView.getController().zoomOut();
				break;

			case R.id.base_mapview_enlarge:
				mMapView.getController().zoomIn();
				break;
			}
		}
	};

	ICNMKLocationListener locationListener = new ICNMKLocationListener()
	{
		public void onLocationChanged(CNMKLocation location)
		{
			if (location != null)
			{
				GeoPoint pt = new GeoPoint(
						(int) (location.getLatitude() * (1e6 * AA.LV)),
						(int) (location.getLongitude() * (1e6 * AA.LV)));
				mMapView.getController().setCenter(pt);
			}
			deleteLocation();
		}

		public void onGPSStatusChanged(GpsStatus status)
		{
		}
	};

	CNMKOverlayMyLocation mLocationOverlay;

	@SuppressWarnings("unchecked")
	public void addLocation()
	{
		mLocationOverlay = new CNMKOverlayMyLocation(getActivity(), mMapView);
		mLocationOverlay.enableMyLocation();
		mMapView.getOverlays().add(mLocationOverlay);

		app.mCNMKAPImgr.getLocationManager().requestLocationUpdates(
				locationListener);
	}

	public void deleteLocation()
	{
		// if(mLocationOverlay!=null)
		// mLocationOverlay.disableMyLocation();
		app.mCNMKAPImgr.getLocationManager().removeUpdates(locationListener);
	}
}
