package com.example.smarttraffic.tripPlan;

import java.util.List;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayRoute;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteResult;
import cennavi.cenmapsdk.android.search.driver.CNMKPaintStyle;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.SearchListener;
import com.example.smarttraffic.tripPlan.bean.CommonDrivingPlan;
import com.example.smarttraffic.tripPlan.bean.DrivingPlan;
import com.example.smarttraffic.tripPlan.bean.TripListInfo;
import com.example.smarttraffic.tripPlan.fragment.TripDrivingSelfFragment;
import com.example.smarttraffic.util.StartIntent;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 出行规划地图界面
 * @author Administrator zhou
 *
 */
public class TripLineMapActivity extends FragmentActivity
{	
	CNMKMapView mMapView;
	
	DrivingPlan plan;	
	CommonDrivingPlan commonDrivingPlan;
	int type;
	
	MapControl mapControl;
	TextView infoTextView;
	
	public static final String Map_SEARCH_TYPE="map_search_type";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_trip_map);
		
		mMapView=(CNMKMapView)findViewById(R.id.base_mapview);
		infoTextView=(TextView)findViewById(R.id.trip_map_info);
		
		mapControl=new MapControl(this);
		mapControl.initMap(mMapView);
		
		type=getIntent().getIntExtra(Map_SEARCH_TYPE, 0);
		if(type==0)
		{
			initSelfLine();
		}
		else
		{
			initCommonLine();
		}
		
	}
		
	private void initCommonLine()
	{
		
	}
	
	GeoPoint start;
	GeoPoint end;
	private void initSelfLine()
	{
		plan=(DrivingPlan)getIntent().getSerializableExtra(TripDrivingSelfFragment.DRIVING_INFO);
		
		start=new GeoPoint();
		start.setLatitudeE6(plan.getLan1());
		start.setLongitudeE6(plan.getLon1());
		end=new GeoPoint();
		end.setLatitudeE6(plan.getLan2());
		end.setLongitudeE6(plan.getLon2());
		
		mapControl.setSearchDriving(new drivingListener());
		mapControl.searchDriving(start, end, DrivingPlan.STATEGYS[plan.getStrategy()]);
		
		mMapView.getController().setCenter(new GeoPoint(plan.getLan1(),plan.getLon1()));
	}
	
	String[] infos;
	CNMKDriveRouteResult routeResult;
	CNMKOverlayRoute routeOverlay;
	
	
	/**
	 * 线路监听函数
	 * @author Administrator
	 *
	 */
	class drivingListener implements SearchListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void searchListener(Object oResult, int id, boolean error,
				String arg3)
		{
			CNMKDriveRouteResult res=(CNMKDriveRouteResult)oResult;
			if (error || res == null || res.getRouteCount() ==0||res.getRoute(0).getDistsum().getValue()==0) 
			{
				Toast.makeText(TripLineMapActivity.this, "未查到该线路", Toast.LENGTH_SHORT).show();
				return;
			}	
			
			try
			{
				routeResult=res;
						
			    routeOverlay = new CNMKOverlayRoute(TripLineMapActivity.this, mMapView);
	
				routeOverlay.setPaintStyle(new CNMKPaintStyle(true,5,Color.rgb(58, 107, 189),-1));
				
			    routeOverlay.setData(res.getRoute(0));
			    mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.postInvalidate();
			    
			    infoTextView.setText("规划信息：共行驶"+res.getRoute(0).getDistsum().value+"公里");
			    
			}
			catch(Exception e)
			{
//				Toast.makeText(TripLineMapActivity.this, "查找结果出错", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	public static final String LIST_INFO="list_info";
	public void onclick(View v)
	{
		switch (v.getId())
		{
			case R.id.trip_map_back:
				this.finish();
				break;
			case R.id.trip_map_info:
				
				TripListInfo resultInfo=getList();
				if(resultInfo!=null)
				{
					Bundle bundle=new Bundle();
					bundle.putSerializable(LIST_INFO, resultInfo);
					StartIntent.startIntentWithParam(this, TripListInfoActivity.class, bundle);
				}
				break;
			case R.id.trip_map_favor:
				GetDialog.getRadioDialog(this, "方案选择",R.array.array_trip_stategy,new OnFavorSelect(),"",null,plan.getStrategy()).show();
				break;
		}
	}
	
	/**
	 * 出行策略选择事件
	 * @author Administrator
	 *
	 */
	class OnFavorSelect implements OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			plan.setStrategy(which);	
			mapControl.searchDriving(start, end,DrivingPlan.STATEGYS[plan.getStrategy()]);
			dialog.cancel();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public TripListInfo getList()
	{
		try
		{		
			TripListInfo result=new TripListInfo();
			List<String> listCh = routeOverlay.getChGuideFragmentInfo();
			
			result.setLineData(listCh);
			result.setLenght(routeResult.getRoute(0).getDistsum().value);
			result.setStart(plan.getStart());
			result.setEnd(plan.getEnd());
			result.setLan1(plan.getLan1());
			result.setLon1(plan.getLon1());
			result.setLan2(plan.getLan2());
			result.setLon2(plan.getLon2());
			
			return result;
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	
	@Override
	protected void onPause() {
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();
		
		if (mMapView != null)
		{
			mMapView.destory();
			mMapView = null;
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
