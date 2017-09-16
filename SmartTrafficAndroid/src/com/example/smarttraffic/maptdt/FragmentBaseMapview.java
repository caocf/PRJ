//package com.example.smarttraffic.maptdt;
//
//import java.util.List;
//
//import android.content.Context;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.example.smarttraffic.R;
//import com.tianditu.android.maps.MapView;
//import com.tianditu.android.maps.Overlay;
//
//public class FragmentBaseMapview extends Fragment
//{
//	MapView mMapView;
//
//	ImageView location;
//	ImageView reduce;
//	ImageView enlarge;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState)
//	{
//		View rootView = inflater.inflate(R.layout.fragment_tdt_mapview, container,false);
//
//		mMapView = (MapView) rootView.findViewById(R.id.base_mapview);
//
//		mMapView.setBuiltInZoomControls(false);
//
//		initview(rootView);
//
//		return rootView;
//	}
//
//	private void initview(View v)
//	{
//		location = (ImageView) v.findViewById(R.id.base_mapview_location);
//		reduce = (ImageView) v.findViewById(R.id.base_mapview_reduce);
//		enlarge = (ImageView) v.findViewById(R.id.base_mapview_enlarge);
//
//		location.setOnClickListener(listener);
//		reduce.setOnClickListener(listener);
//		enlarge.setOnClickListener(listener);
//	}
//
//	OnClickListener listener = new OnClickListener()
//	{
//
//		@Override
//		public void onClick(View v)
//		{
//			switch (v.getId())
//			{
//			case R.id.base_mapview_location:
//				addLocation();
//				break;
//
//			case R.id.base_mapview_reduce:
//				mMapView.getController().zoomOut();
//				break;
//
//			case R.id.base_mapview_enlarge:
//				mMapView.getController().zoomIn();
//				break;
//			}
//		}
//	};
//
//	public void addLocation()
//	{
//		DefaultMyLocation mMyLocation = new DefaultMyLocation(
//				this.getActivity(), mMapView);
//
//		List<Overlay> list = mMapView.getOverlays();
//		mMyLocation.enableCompass();
//		mMyLocation.enableMyLocation();
//		list.add(mMyLocation);
//
//		LocationManager m_locationManager = (LocationManager) this
//				.getActivity().getSystemService(Context.LOCATION_SERVICE);
//		if (m_locationManager
//				.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
//		{
//			m_locationManager.requestLocationUpdates(
//					LocationManager.NETWORK_PROVIDER, 1000, 0, mMyLocation);
//		}
//
//	}
//
//}
