package com.example.smarttraffic.smartBus;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteResult;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.PointTraslation;
import com.example.smarttraffic.map.SearchListener;

import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class GoThereListActivity extends FragmentActivity
{
	public static final String GO_THERE_LANTITUDE = "go_there_lantitude";
	public static final String GO_THERE_LONGTITUDE = "go_there_longtitude";

	double lan1;
	double lon1;
	double lan2;
	double lon2;

	MapControl mapControl;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_detail_list);

		initHead();

		mapControl = new MapControl(this);
		mapControl.setSearchDriving(new drivingListener());

		lan2 = getIntent().getDoubleExtra(GO_THERE_LANTITUDE, -1);
		lon2 = getIntent().getDoubleExtra(GO_THERE_LONGTITUDE, -1);

		endGeoPoint = new GeoPoint(lan2, lon2);
		
		endGeoPoint=PointTraslation.transform(endGeoPoint);
		
		listView=(ListView)findViewById(R.id.transfer_detail_list_content);

		mapControl.setLocationListener(new ICNMKLocationListener()
		{

			@Override
			public void onLocationChanged(CNMKLocation location)
			{
				if (location != null)
				{
					lan1 = location.getLatitude();
					lon1 = location.getLongitude();

					startGeoPoint = new GeoPoint(lan1, lon1);
					
					search();
				}

				mapControl.deleteLocation();
			}

			@Override
			public void onGPSStatusChanged(GpsStatus arg0)
			{
			}
		});
		mapControl.addLocation();

	}
	
	class drivingListener implements SearchListener
	{
		@Override
		public void searchListener(Object oResult, int id, boolean error,
				String arg3)
		{
			CNMKDriveRouteResult res=(CNMKDriveRouteResult)oResult;
			if (error || res == null || res.getRouteCount() ==0||res.getRoute(0).getDistsum().getValue()==0) 
			{
				Toast.makeText(GoThereListActivity.this, "未查到该线路", Toast.LENGTH_SHORT).show();
				return;
			}	
			
			try
			{		
			    res.getRoute(0);
			}
			catch(Exception e)
			{
	}
		}
	}

	private void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("路线规划");

		ManagerFragment.replaceFragment(this, R.id.transfer_detail_list_head,
				fragment);
	}

	private GeoPoint startGeoPoint;
	private GeoPoint endGeoPoint;
	
	public void search()
	{
//		System.out.println("lan1:"+lan1+" lon1:"+lon1+" lan2:"+lan2+" lon2:"+lon2);
		
		mapControl.searchDriving(new GeoPoint(lan1, lon1),endGeoPoint,2);
	}

	@Override
	protected void onPause()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();

		super.onPause();
	}

	@Override
	protected void onResume()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.start();

		super.onResume();
	}
}
