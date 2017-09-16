package net.hxkg.cruise;

import net.hxkg.ghmanager.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapFragment;
import cennavi.cenmapsdk.android.map.CNMKMapView;

public class MapFragment extends CNMKMapFragment
{
	CNMKMapView mMapView = null;	// 地图View
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rooView= inflater.inflate(R.layout.map_view, container, false);
		mMapView = (CNMKMapView)rooView.findViewById(R.id.cnmapView);
		//mMapView.changeLanguage("zh");
		super.setMapView(mMapView);		
		initMapActivity();
		
		//设置在缩放动画过程中也显示overlay,默认为不绘制
        mMapView.setDrawOverlayWhenZooming(true);
        //GeoPoint point =new GeoPoint((int)(30.77*1e6*AA.LV), (int)(120.76*1e6*AA.LV));//嘉兴市
        //GeoPoint point =new GeoPoint((int)(30.00*1e6*AA.LV), (int)(122.20*1e6*AA.LV));//舟山市
        GeoPoint point =new GeoPoint((int)(30.26*1e6*AA.LV), (int)(120.19*1e6*AA.LV));//杭州市
        mMapView.getController().setCenter(point);
        mMapView.setZoomLevel(11);
        mMapView.displayZoomControls(false);
        mMapView.setTraffic(false);
		
		return rooView;
	}
}
