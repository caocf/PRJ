package com.example.smarttraffic.map;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.search.CNMKReverseGeocodingResult;

import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * 地图选点界面
 * 
 * @author Administrator zwc
 * 
 */
public class MapSelectPointActivity extends FragmentActivity
{
	public static String MAP_SELECT_LAN = "map_select_lan";
	public static String MAP_SELECT_LON = "map_select_lon";
	public static String MAP_SELECT_ADDR = "map_select_addr";

	public static int MAP_RESULT_CODE = 1002;

	CNMKMapView mapView;
	MapControl mapControl;

	GeoPoint select;
	String name="";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map_select_point);

		mapView = (CNMKMapView) findViewById(R.id.base_mapview);

		mapControl = new MapControl(this);
		mapControl.initMap(mapView);

		initHead();

		mapControl.setReverSearchListener(new SearchListener()
		{
			
			@Override
			public void searchListener(Object arg0, int arg1, boolean arg2, String arg3)
			{
				if (arg2 || arg0 == null) {
//					Toast.makeText(MapSelectPointActivity.this, "解析坐标失败", Toast.LENGTH_LONG).show();
					return;
				}
				
				try
				{
					CNMKReverseGeocodingResult result=(CNMKReverseGeocodingResult)arg0;
					
					if(result.getReverseGeoCodeinfos().size()>0)
					{
						name=result.getReverseGeoCodeinfos().get(0).getRoadname()+" "+result.getReverseGeoCodeinfos().get(0).getRestname();
					}
				} catch (Exception e)
				{
				}
			}
		});

		mapControl.addMapChoicePoint(selectListener);
	}

	/**
	 * 选点结果监听器
	 */
	SelectPointResultListener selectListener = new SelectPointResultListener()
	{

		@Override
		public void onPointResultListener(GeoPoint geoPoint)
		{
			mapControl.clearOverlays();

			select = geoPoint;

			mapControl.createMapOverItem(MapSelectPointActivity.this, null,
					new GeoPoint[] { new GeoPoint(geoPoint.getLatitudeE6(),
							geoPoint.getLongitudeE6()) }, null, false);
			mapControl.addMapChoicePoint(selectListener);
			
			mapControl.searchReverseGeo(select);
		}
	};

	/**
	 * 头部处理
	 * 
	 */
	private void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("地图选点");
		fragment.setRightName("完成");
		fragment.setRightListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (select != null)
				{
					Intent intent = new Intent();
					intent.putExtra(MAP_SELECT_LAN, select.lat * 1.0 / 1e6);
					intent.putExtra(MAP_SELECT_LON, select.lon * 1.0 / 1e6);
					intent.putExtra(MAP_SELECT_ADDR, name);
					MapSelectPointActivity.this.setResult(MAP_RESULT_CODE,
							intent);

					MapSelectPointActivity.this.finish();
				}
			}
		});

		ManagerFragment.replaceFragment(this, R.id.transfer_detail_map_head,
				fragment);
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
