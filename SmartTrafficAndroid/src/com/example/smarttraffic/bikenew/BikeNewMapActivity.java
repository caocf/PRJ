package com.example.smarttraffic.bikenew;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.drivingSchool.OnPopInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.MarkOverlayItem;
import com.example.smarttraffic.smartBus.GoThereMapActivity;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Administrator zhou
 * 
 */
public class BikeNewMapActivity extends FragmentActivity
{
	public static final String DATA = "data";
	public static final String LAN = "lan";
	public static final String LON = "lon";

	MapControl mapControl;
	CNMKMapView mapView;
	
	int random=new Random().nextInt(100000);
	

	List<BikeStation> data;
	private double lan;
	private double lon;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_line_map);

		System.out.println("mapsss create "+random);
		
		initHead();

		mapView = (CNMKMapView) findViewById(R.id.base_mapview);

		mapControl = new MapControl(this);
		mapControl.initMap(mapView);

		data = (List<BikeStation>) getIntent().getSerializableExtra(DATA);
		lan = getIntent().getDoubleExtra(LAN, -1);
		lon = getIntent().getDoubleExtra(LON, -1);

		if (lan > 0 && lon > 0)
			mapView.getController().setCenter(new GeoPoint(lan, lon));

		show();
	}

	private void show()
	{
		if (data == null || data.size() == 0)
			return;

		List<MarkOverlayItem> geoPoint = new ArrayList<MarkOverlayItem>();

		for (int i = 0; i < data.size(); i++)
		{
			
			MarkOverlayItem markOverlayItem=new MarkOverlayItem();
			GeoPoint p = new GeoPoint(data.get(i).getLatitude(), data.get(i)
					.getLongitude());
			markOverlayItem.setGeoPoint(p);
			
			double rate=data.get(i).getBorrowed()*100.0/data.get(i).getCount();
			if(data.get(i).getBorrowed()==0 || data.get(i).getBorrowed()==data.get(i).getCount())
				markOverlayItem.setMarkid(R.drawable.bike_gray_1);
			else if(rate<20 || rate>80)
				markOverlayItem.setMarkid(R.drawable.bike_red_1);
			else if(rate<30 || rate>70)
				markOverlayItem.setMarkid(R.drawable.bike_yellow_1);
			else 
				markOverlayItem.setMarkid(R.drawable.bike_green_1);
			
			geoPoint.add(markOverlayItem);
		}

		mapControl.createMarkMapOverItem(BikeNewMapActivity.this,
				getLayoutInflater().inflate(R.layout.map_pop_layout, null),
				geoPoint, new onPopClick());
	}

	class onPopClick implements OnPopInterface
	{
		int i;

		@Override
		public View onPop(int i, View v)
		{
			this.i = i;
			if (data != null)
			{
				TextView name = (TextView) v
						.findViewById(R.id.driving_school_name);
				TextView info = (TextView) v
						.findViewById(R.id.driving_school_information);
				ImageView goView = (ImageView) v
						.findViewById(R.id.driving_get_there);

				
				name.setText(data.get(i).getName());

				info.setText("可还：" + data.get(i).getBorrowed() + " 可借："
						+ data.get(i).getLeft());
				
				//goView.setVisibility(View.GONE);
				goView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Bundle bundle = new Bundle();
						bundle.putDouble(GoThereMapActivity.GO_THERE_LANTITUDE,
								data.get(onPopClick.this.i).getLatitude());
						bundle.putDouble(
								GoThereMapActivity.GO_THERE_LONGTITUDE, data
										.get(onPopClick.this.i).getLongitude());

						StartIntent.startIntentWithParam(
								BikeNewMapActivity.this,
								GoThereMapActivity.class, bundle);
					}
				});
			}

			return v;
		}
	}

	private void initHead()
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("搜索结果");
		fragment.setRightName("列表");
		fragment.setRightListen(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				BikeNewMapActivity.this.finish();
			}
		});

		ManagerFragment.replaceFragment(this, R.id.smart_bus_line_map_head,
				fragment);
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
