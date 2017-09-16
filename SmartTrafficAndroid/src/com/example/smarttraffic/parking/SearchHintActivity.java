package com.example.smarttraffic.parking;


import java.util.ArrayList;
import java.util.List;

import cennavi.cenmapsdk.android.search.CNMKNewPoiInfo;
import cennavi.cenmapsdk.android.search.CNMKNewPoiResult;
import cennavi.cenmapsdk.android.search.CNMKPoiInfo;
import cennavi.cenmapsdk.android.search.CNMKPoiResult;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.SearchListener;
import com.example.smarttraffic.nearby.NearBy;
import com.example.smarttraffic.nearby.NearbyDetailListAdapter;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchHintActivity extends FragmentActivity
{
	ListView content;

	public static final String NAME="name";
	
	String str;
	MapControl mapControl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_point);

		str=getIntent().getStringExtra(NAME);
		
		initHead(str);
		content=(ListView)findViewById(R.id.smart_bus_select_point_content);
		
		addSuggestion(str);
	}

	private void initHead(String str)
	{
		HeadNameFragment fragment = new HeadNameFragment();
		fragment.setTitleName("\"" + str + "\"相关地点");
		ManagerFragment.replaceFragment(this, R.id.smart_bus_select_point_head,
				fragment);
	}

	private void addSuggestion(String str)
	{
		if (mapControl == null)
			mapControl = new MapControl(SearchHintActivity.this);

		mapControl.setSearchPoi(new SearchListener()
		{

			@Override
			public void searchListener(Object arg0, int arg1, boolean arg2,
					String arg3)
			{
				try
				{
					if (arg0 == null)
					{
						Toast.makeText(SearchHintActivity.this, "无数据",
								Toast.LENGTH_SHORT).show();
						return;
					}
//					CNMKNewPoiResult result = (CNMKNewPoiResult) arg0;
//
//					List<CNMKNewPoiInfo> list = result.getmPois();
//
//					List<NearBy> data = new ArrayList<NearBy>();
//
//					for (int i = 0; i < list.size(); i++)
//					{
//						NearBy temp = new NearBy();
//
//						CNMKNewPoiInfo temp1 = list.get(i);
//
//						temp.setAddress(temp1.getAddress());
//						temp.setId("");
//						temp.setLan(temp1.getGp().getLatitudeE6() * 1.0 / 1e6);
//						temp.setLon(temp1.getGp().getLongitudeE6() * 1.0 / 1e6);
//						temp.setName(temp1.getName());
//						temp.setPhone("");
//						temp.setRegion("");
//						temp.setStreet("");
//						temp.setTime("");
//
//						data.add(temp);
//					}
					
					CNMKPoiResult result = (CNMKPoiResult) arg0;

					List<CNMKPoiInfo> list = result.getPois();

					List<NearBy> data = new ArrayList<NearBy>();

					for (int i = 0; i < list.size(); i++)
					{
						NearBy temp = new NearBy();

						CNMKPoiInfo temp1 = list.get(i);

						temp.setAddress(temp1.getAddress());
						temp.setId("");
						temp.setLan(temp1.getGeoPoint().getLatitudeE6() * 1.0 / 1e6);
						temp.setLon(temp1.getGeoPoint().getLongitudeE6() * 1.0 / 1e6);
						temp.setName(temp1.getName());
						temp.setPhone("");
						temp.setRegion("");
						temp.setStreet("");
						temp.setTime("");

						data.add(temp);
					}

					content.setAdapter(new NearbyDetailListAdapter(
							SearchHintActivity.this, (List<NearBy>) data,
							false));
					
					content.setOnItemClickListener(new OnItemClickListener()
					{

						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id)
						{
							Bundle bundle = new Bundle();
							NearBy n = (NearBy) parent.getAdapter().getItem(position);

							bundle.putDouble(ParkingListActivity.LAN, n.getLan());
							bundle.putDouble(ParkingListActivity.LON, n.getLon());

							StartIntent.startIntentWithParam(SearchHintActivity.this,
									ParkingListActivity.class, bundle);
						}
					});

				} catch (Exception e)
				{
					Toast.makeText(SearchHintActivity.this, "无相似数据",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		mapControl.searchPOI(str);
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
