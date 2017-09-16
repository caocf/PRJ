package com.example.smarttraffic.taxi;

import java.util.ArrayList;
import java.util.List;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.drivingSchool.OnPopInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 周边出租车地图
 * 
 * @author Administrator
 * 
 */
public class TaxiMapActivity extends FragmentActivity
{

	public static final String TAXI_DATA="taxi_data";
	public static final String LAN="lan";
	public static final String LON="lon";
	private List<Taxi> taxis;
	private double lan;
	private double lon;
	
	MapControl mapControl;
	CNMKMapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_taxi_map);

		initHead();
		
		taxis=(List<Taxi>)getIntent().getSerializableExtra(TAXI_DATA);
		lan=getIntent().getDoubleExtra(LAN, 0);
		lon=getIntent().getDoubleExtra(LON, 0);
		
		mapView = (CNMKMapView) findViewById(R.id.base_mapview);

		mapControl = new MapControl(this);
		mapControl.initMap(mapView);
		
		if(lan>0&&lon>0)
			mapControl.setCenter(new GeoPoint(lan, lon));
		else 
			mapControl.addLocation();
		
		if(taxis!=null)
			show();
	}

	private void show()
	{
		if(taxis==null || taxis.size()==0)
			return;
		
		List<GeoPoint> geoPoint=new ArrayList<GeoPoint>();
		
		for(int i=0;i<taxis.size();i++)
		{
			GeoPoint p=new GeoPoint(taxis.get(i).getWd()*1.0/1e6, taxis.get(i).getJd()*1.0/1e6);
			geoPoint.add(p);
		}
		
		mapControl.createMapOverItem(
				TaxiMapActivity.this,
				getLayoutInflater().inflate(R.layout.map_pop_layout,
						null), geoPoint, new onPopClick());
	}
	
	class onPopClick implements OnPopInterface
	{
		int i;

		@Override
		public View onPop(int i, View v)
		{
			this.i = i;
			if (taxis != null)
			{
				TextView name = (TextView) v
						.findViewById(R.id.driving_school_name);
				TextView info = (TextView) v
						.findViewById(R.id.driving_school_information);
				ImageView goView = (ImageView) v
						.findViewById(R.id.driving_get_there);

				name.setText(taxis.get(i).getSbid());

				
				info.setText("距离"+taxis.get(i).getDistance()+"米");
				goView.setVisibility(View.GONE);

			}

			return v;
		}
	}
	
	private void initHead()
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("周边出租车");

		ManagerFragment.replaceFragment(this, R.id.taxi_list_head, fragment);
	}

	@Override
	protected void onDestroy()
	{
		if (mapView != null)
		{
			mapView.destory();
			mapView = null;
		}
		
		super.onDestroy();
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
