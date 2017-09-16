package com.example.smarttraffic.bike;

import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.view.ExchangeView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class BikeRidingDetailMapActivity extends FragmentActivity
{	
	CNMKMapView mapView;
	MapControl mapControl;
	
	ExchangeView exchangeView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_detail_map);
		
		initHead();
		
		mapView=(CNMKMapView)findViewById(R.id.base_mapview);
		
		exchangeView=(ExchangeView)findViewById(R.id.transfer_detail_map_exchange);
		
		mapControl=new MapControl(this);
		mapControl.initMap(mapView);
		mapControl.addLocation();
		
		Toast.makeText(this, "暂无线路规划", Toast.LENGTH_SHORT).show();
	}
	
	private void initHead()
	{
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("公共自行车");
		fragment.setRightName("列表");
		fragment.setRightListen(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				BikeRidingDetailMapActivity.this.finish();			
			}
		});
		
		ManagerFragment.replaceFragment(this, R.id.transfer_detail_map_head, fragment);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return exchangeView.onTouchEvent(event);
	}
	
	@Override
	protected void onPause() {
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();
		
		if (mapView != null)
		{
			mapView.destory();
			mapView = null;
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.start();

		super.onResume();
	}
}
