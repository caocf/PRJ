package com.example.smarttraffic.smartBus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.drivingSchool.OnPopInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.smartBus.adapter.TransferAdapter;
import com.example.smarttraffic.smartBus.bean.Transfer;
import com.example.smarttraffic.view.ExchangeView;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TransferDetailMapActivity extends FragmentActivity
{
	public static final String TRANSFER_DETAIL_MAP_DATA = "transfer_detail_map_data";
	public static final String TRANSFER_DETAIL_MAP_DATA_ID = "transfer_detail_map_data_id";

	String data;
	int id;

	CNMKMapView mapView;
	MapControl mapControl;

	TransferAdapter adapter;

	ExchangeView exchangeView;

	private double lan1;
	private double lon1;
	private double lan2;
	private double lon2;

	List<Transfer> lineList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transfer_detail_map);

		initHead();

		mapView = (CNMKMapView) findViewById(R.id.base_mapview);

		exchangeView = (ExchangeView) findViewById(R.id.transfer_detail_map_exchange);

		mapControl = new MapControl(this);
		mapControl.initMap(mapView);
		mapControl.addLocation();

		data = getIntent().getStringExtra(TRANSFER_DETAIL_MAP_DATA);
		id = getIntent().getIntExtra(TRANSFER_DETAIL_MAP_DATA_ID, 0);

		lan1 = getIntent()
				.getDoubleExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
						0);
		lon1 = getIntent()
				.getDoubleExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
						0);
		lan2 = getIntent()
				.getDoubleExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LAN,
						0);
		lon2 = getIntent()
				.getDoubleExtra(
						SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LON,
						0);

		startGeoPoint = new GeoPoint(lan1, lon1);
		endGeoPoint = new GeoPoint(lan2, lon2);

		try
		{
			lineList = parse(data);
			adapter = new TransferAdapter(this, lineList);
			exchangeView.setAdapter(adapter);
			exchangeView.update(id);
			exchangeView.select(id);
			exchangeView.setListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					showLine(lineList, position);
				}
			});

			showLine(lineList, id);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void initHead()
	{
		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("换乘线路");
		fragment.setRightName("列表");
		fragment.setRightListen(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				TransferDetailMapActivity.this.finish();
			}
		});

		ManagerFragment.replaceFragment(this, R.id.transfer_detail_map_head,
				fragment);
	}

	private GeoPoint startGeoPoint;
	private GeoPoint endGeoPoint;

	private void addPoint()
	{
		mapControl.createMapOverItem(this,
				null,
				new GeoPoint[] { startGeoPoint }, null,
				R.drawable.map_start_point);
		mapControl.createMapOverItem(this,
				null,
				new GeoPoint[] { endGeoPoint }, null, R.drawable.map_end_point);
	}
	
	Transfer transfer ;

	private void showLine(List<Transfer> data, int id)
	{
		mapView.getOverlays().clear();

		 transfer = data.get(id);
		List<GeoPoint> points = new ArrayList<GeoPoint>();

		List<GeoPoint> stationGeoPoints=new ArrayList<GeoPoint>();
		
		for (int i = 0; i < transfer.getTransferLines().size(); i++)
		{
			if (transfer.getTransferLines().get(i).getType() == 1)
				points.addAll(transfer.getTransferLines().get(i).getGeoPoints());
			
			stationGeoPoints.add(new GeoPoint(transfer.getTransferLines().get(i).getLan(), transfer.getTransferLines().get(i).getLon()));
			
		}
		
		mapControl.createLine(points);
		
		mapControl.createMapOverItem(this, getLayoutInflater().inflate(R.layout.map_pop_layout, null), stationGeoPoints, new onPopClick());

		addPoint();
	}
	
	class onPopClick implements OnPopInterface
	{
		int i;

		@Override
		public View onPop(int i, View v)
		{
			this.i = i;
			if (transfer != null)
			{
				TextView name = (TextView) v
						.findViewById(R.id.driving_school_name);
				TextView info = (TextView) v
						.findViewById(R.id.driving_school_information);
				ImageView goView = (ImageView) v
						.findViewById(R.id.driving_get_there);
				goView.setVisibility(View.GONE);
				info.setVisibility(View.GONE);
				name.setText(transfer.getTransferLines().get(i).getStationName());

				//info.setText(transfer.getTransferLines().get(i).get);
				
			}

			return v;
		}
	}

	private List<Transfer> parse(String data)
	{
		List<Transfer> result = new ArrayList<Transfer>();

		try
		{
			JSONObject jsonObject = new JSONObject(data);
			JSONArray array = jsonObject.getJSONArray("List");

			int count = array.length();

			for (int i = 0; i < count; i++)
			{
				result.add(TransferDetailActivity.parse(data, i));
			}

		} catch (Exception e)
		{

		}

		return result;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return exchangeView.onTouchEvent(event);
	}

	@Override
	protected void onPause()
	{
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
	protected void onResume()
	{
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.start();

		super.onResume();
	}
}
