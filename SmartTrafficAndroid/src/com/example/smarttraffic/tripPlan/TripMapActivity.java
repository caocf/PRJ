package com.example.smarttraffic.tripPlan;

import java.util.List;

import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayRoute;
import cennavi.cenmapsdk.android.search.CNMKPoiInfo;
import cennavi.cenmapsdk.android.search.CNMKPoiResult;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteResult;
import cennavi.cenmapsdk.android.search.driver.CNMKPaintStyle;

import com.example.smarttraffic.R;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.drivingSchool.DrivingInfoListFragment;
import com.example.smarttraffic.drivingSchool.DrivingSchool;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.map.SearchListener;
import com.example.smarttraffic.tripPlan.bean.DrivingPlan;
import com.example.smarttraffic.tripPlan.bean.TripListInfo;
import com.example.smarttraffic.tripPlan.fragment.TripDrivingSelfFragment;
import com.example.smarttraffic.util.StartIntent;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.location.GpsStatus;
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
public class TripMapActivity extends FragmentActivity
{	
	CNMKMapView mMapView;
	
	DrivingPlan plan;
	poiListener l;
	MapControl mapControl;
	TextView infoTextView;
	
	int index;
	int from;           //0:默认;1:驾培
	
	public static final String FROM_NAME="from";
	public static final String LANTITUDE="lantitude";
	public static final String LONTITUDE="longtitude";
	DrivingSchool drivingSchool;
	
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
		
		from=getIntent().getIntExtra(FROM_NAME, 0);
		if(from==0)
		{		
			index=1;
	
			l=new poiListener();
			mapControl.setSearchPoi(l);
			mapControl.setSearchDriving(new drivingListener());
			
			plan=(DrivingPlan)getIntent().getSerializableExtra(TripDrivingSelfFragment.DRIVING_INFO);
			
//			if(plan.getLan1()!=0&&plan.getLan2()!=0&&plan.getLon1()!=0&&plan.getLon2()!=0)
//			{
//				mapControl.searchDriving(new GeoPoint(plan.getLan1(), plan.getLon1()),new GeoPoint(plan.getLan2(),plan.getLon2()), plan.getStrategy());
//			}
//			else
			{
				if(plan.getStart().equals(TripDrivingSelfFragment.MY_LOCATION))
				{
					mapControl.setLocationListener(new locationListener());
					mapControl.addLocation();
				}
				else
				{
					mapControl.searchPOI(plan.getStart());
				}
			}
		}	
		else if(from==1)
		{			
			mapControl.setSearchDriving(new drivingListener());
			drivingSchool=(DrivingSchool)getIntent().getSerializableExtra(DrivingInfoListFragment.DRIVING_SCHOOL_INFO);
			int lantitde=getIntent().getIntExtra(LANTITUDE, 0);
			int longtitude=getIntent().getIntExtra(LONTITUDE, 0);
			if(lantitde!=0 && longtitude!=0)
			{
				mapControl.searchDriving(new GeoPoint(lantitde, longtitude), new GeoPoint(drivingSchool.getLantitude(), drivingSchool.getLongtitude()), 1);
			}	
			else
			{
				mapControl.setLocationListener(new pointLocationListener());
			}
			mapControl.addLocation();
		}
	}
		
	String[] infos;
	CNMKPoiResult poiResult;
	CNMKDriveRouteResult routeResult;
	CNMKOverlayRoute routeOverlay;
	
	/**
	 * 兴趣点监听函数
	 * @author Administrator
	 *
	 */
	class poiListener implements SearchListener
	{
		@Override
		public void searchListener(Object oResult, int id, boolean error,
				String arg3)
		{
			CNMKPoiResult result=(CNMKPoiResult)oResult;
			
			if ( error || oResult == null || null==result.getPois()) 
			{
				Toast.makeText(TripMapActivity.this, "未查到该点相关信息", Toast.LENGTH_SHORT).show();
				return;
			}	

			try 
			{
				poiResult=result;
				infos=new String[result.getPois().size()];
				
				for(int i=0;i<result.getPois().size();i++)
				{
					infos[i]=result.getPoi(i).getName();
				}
				
				if(index==1)
					GetDialog.getListDialog(TripMapActivity.this, "起点("+plan.getStart()+")", infos, new dialogListener()).show();
				else if(index==2)
					GetDialog.getListDialog(TripMapActivity.this, "终点("+plan.getEnd()+")", infos, new dialogListener()).show();
			}
			catch ( Exception e )
			{
			}
		}
	}
	
	/**
	 * 从驾校或汽修跳转过来的定位监听函数
	 * @author Administrator
	 *
	 */
	class pointLocationListener implements ICNMKLocationListener
	{
		@Override
		public void onGPSStatusChanged(GpsStatus arg0)
		{
			
		}
		@Override
		public void onLocationChanged(CNMKLocation location)
		{
			if(location!=null)
			{
				GeoPoint start=new GeoPoint((int) (location.getLatitude() * (1e6 * AA.LV)),
								(int) (location.getLongitude() * (1e6 * AA.LV)));
				
				if(index<=2)
				{
					index=3;
					mapControl.searchDriving(start, new GeoPoint(drivingSchool.getLantitude(), drivingSchool.getLongtitude()), 1);
				}
				mMapView.getController().setCenter(start);				
			}
			
			mapControl.deleteLocation();
		}
	}
	
	/**
	 * 默认定位监听函数
	 * @author Administrator
	 *
	 */
	class locationListener implements ICNMKLocationListener
	{
		@Override
		public void onGPSStatusChanged(GpsStatus arg0)
		{
			
		}

		@Override
		public void onLocationChanged(CNMKLocation location)
		{
			if(location!=null)
			{
				CNMKPoiInfo info=new CNMKPoiInfo();
				info.setName(TripDrivingSelfFragment.MY_LOCATION);
				GeoPoint geopoint=new GeoPoint((int) (location.getLatitude() * (1e6 * AA.LV)),
								(int) (location.getLongitude() * (1e6 * AA.LV)));
				info.setGeoPoint(geopoint);
				if(index==1)
				{
					plan.setStartCnmkPoiInfo(info);
					index++;
										
					mapControl.searchPOI(plan.getEnd());
				}
				else if(index==2)
				{
					index++;
					plan.setEndCnmkPoiInfo(info);
					mapControl.searchDriving(plan.getStartCnmkPoiInfo().getGeoPoint(), plan.getEndCnmkPoiInfo().getGeoPoint(),DrivingPlan.STATEGYS[plan.getStrategy()]);
				}
				
				mMapView.getController().setCenter(geopoint);
				
			}
			mapControl.deleteLocation();
		}	
	}
	
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
				Toast.makeText(TripMapActivity.this, "未查到该线路", Toast.LENGTH_SHORT).show();
				return;
			}	
			
			try
			{
				routeResult=res;
						
			    routeOverlay = new CNMKOverlayRoute(TripMapActivity.this, mMapView);
	
				routeOverlay.setPaintStyle(new CNMKPaintStyle(true,5,Color.rgb(58, 107, 189),-1));
				
			    routeOverlay.setData(res.getRoute(0));
			    mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.postInvalidate();
			    
			    infoTextView.setText(res.getRoute(0).getDistsum().value+"公里");
			    mMapView.getController().setCenter(plan.getStartCnmkPoiInfo().getGeoPoint());
			}
			catch(Exception e)
			{
//				Toast.makeText(TripMapActivity.this, "查找结果出错", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	/**
	 * 内容选择对话框监听事件
	 * @author Administrator
	 *
	 */
	class dialogListener implements OnClickListener
	{
		
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			if(index==1)
			{
				plan.setStartCnmkPoiInfo(poiResult.getPoi(which));
				index++;
				if(plan.getEnd().equals(TripDrivingSelfFragment.MY_LOCATION))
				{
					mapControl.setLocationListener(new locationListener());
					mapControl.addLocation();
				}
				else
				{
					mapControl.searchPOI(plan.getEnd());
					mapControl.addLocation();
				}
				
			}
			else if(index==2)
			{
				plan.setEndCnmkPoiInfo(poiResult.getPoi(which));
				mapControl.searchDriving(plan.getStartCnmkPoiInfo().getGeoPoint(), plan.getEndCnmkPoiInfo().getGeoPoint(),DrivingPlan.STATEGYS[plan.getStrategy()]);
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
				if(from==0)
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
			mapControl.searchDriving(plan.getStartCnmkPoiInfo().getGeoPoint(), plan.getEndCnmkPoiInfo().getGeoPoint(),DrivingPlan.STATEGYS[plan.getStrategy()]);
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
			result.setStart(plan.getStartCnmkPoiInfo().getName());
			result.setEnd(plan.getEndCnmkPoiInfo().getName());
			result.setLan1(plan.getStartCnmkPoiInfo().getGeoPoint().getLatitudeE6());
			result.setLon1(plan.getStartCnmkPoiInfo().getGeoPoint().getLongitudeE6());
			result.setLan2(plan.getEndCnmkPoiInfo().getGeoPoint().getLatitudeE6());
			result.setLon2(plan.getEndCnmkPoiInfo().getGeoPoint().getLongitudeE6());
			
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
			mMapView .destory();
			mMapView  = null;
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
