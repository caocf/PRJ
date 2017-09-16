package net.hxkg.lawexcut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;
import cennavi.cenmapsdk.android.map.CNMKMapView;
import cennavi.cenmapsdk.android.map.CNMKOverlayItem;
import cennavi.cenmapsdk.android.search.CNMKAdminResult;
import cennavi.cenmapsdk.android.search.CNMKCategoryResult;
import cennavi.cenmapsdk.android.search.CNMKCityResult;
import cennavi.cenmapsdk.android.search.CNMKEncryptionResult;
import cennavi.cenmapsdk.android.search.CNMKGeocodingResult;
import cennavi.cenmapsdk.android.search.CNMKPOIAroundInfo;
import cennavi.cenmapsdk.android.search.CNMKPOIAroundResult;
import cennavi.cenmapsdk.android.search.CNMKPoiResult;
import cennavi.cenmapsdk.android.search.CNMKReverseGeocodingResult;
import cennavi.cenmapsdk.android.search.CNMKSearch;
import cennavi.cenmapsdk.android.search.CNMKStepWalkResult;
import cennavi.cenmapsdk.android.search.ICNMKSearchListener;
import cennavi.cenmapsdk.android.search.driver.CNMKDriveRouteResult;
import cennavi.cenmapsdk.android.search.driver.CNMKNewDriveRouteResult;
import cennavi.cenmapsdk.android.search.poi.CNMKPoiAroundReqParam;
import net.hxkg.cruise.MarkOverLay;
import net.hxkg.ghmanager.CenApplication;
import net.hxkg.ghmanager.R;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class PoiArroundActivity extends FragmentActivity implements ICNMKSearchListener,ICNMKLocationListener,OnItemClickListener
{
	CNMKMapView mMapView;
	//poi地址
	CNMKSearch mSearch;
	ListView listView;
	SimpleAdapter adapter;
	List<Map<String, Object>> listdata=new ArrayList<>();
	//地图定位
	MarkOverLay mayplaceLay;
	GeoPoint point;
	public static int RESULTCODE=600;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poiarround);
		
		initMap();
		
		initListView();
	}
	
	public void onBack(View view)
	{
		this.finish();
	}
	
	private void initListView()
	{
		listView=(ListView) findViewById(R.id.list);
		adapter=new SimpleAdapter(this,listdata,R.layout.item_locationlist,new String[]{"text"},new int[]{R.id.text});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	
	public void poisearch(GeoPoint point)
	{
		CNMKPoiAroundReqParam p=new CNMKPoiAroundReqParam(); 
		p.setGeoPoint(point);
		p.setCount(3);
		p.setRadius(10000);
		p.setLanguage(0);
		p.setSearchType("poi");
		mSearch.poiAround(p);
	}
	
	private void initMap()
	{
		mMapView = (CNMKMapView)findViewById(R.id.cnmapView);
		mMapView.setBuiltInZoomControls(false); 	    
	    Drawable drawable=getResources().getDrawable(R.drawable.ic_directio2);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
	    mayplaceLay=new MarkOverLay(drawable,this,mMapView);
		mMapView.getOverlays().add(mayplaceLay);
		
		mSearch = CenApplication.mCNMKAPImgr.getSearcher();
	    mSearch.regListener(this);
	    CenApplication.mCNMKAPImgr.getLocationManager().requestLocationUpdates(this);
	    CenApplication.mCNMKAPImgr.getLocationManager().start(3);
	}

	@Override
	public void onGetAdminResult(CNMKAdminResult arg0, int arg1, boolean arg2, String arg3) 
	{
		
		
	}

	@Override
	public void onGetCategorySearchResult(CNMKCategoryResult arg0, int arg1,boolean arg2, String arg3)
	{
		
		
	}

	@Override
	public void onGetCityResult(CNMKCityResult arg0, int arg1, boolean arg2,String arg3) {
		
	}

	@Override
	public void onGetDriverRouteResult(CNMKDriveRouteResult arg0, int arg1,
			boolean arg2, String arg3) {
		
	}

	@Override
	public void onGetEncryptionResult(CNMKEncryptionResult arg0, int arg1,
			boolean arg2, String arg3) {
		
	}

	@Override
	public void onGetGeoCodingResult(CNMKGeocodingResult arg0, int arg1,
			boolean arg2, String arg3) {
		
	}

	@Override
	public void onGetPOIAroundResult(CNMKPOIAroundResult result, int arg1,boolean arg2, String arg3)
	{
		if(result==null)
		{
			Toast.makeText(this, "暂无定位数据", Toast.LENGTH_SHORT).show();
			return;
		}
		ArrayList<CNMKPOIAroundInfo> list=result.getPoiByAroundInfo();
		listdata.clear();
		for(CNMKPOIAroundInfo in:list)
		{
			String string=in.getAddr();
			if(string==null||"".equals(string))
			{
				continue;
			}
			Map<String, Object> map=new HashMap<>();
			map.put("text", string);
			listdata.add(map);
		}
		
		adapter.notifyDataSetChanged();
		
	}

	@Override
	public void onGetPoiResult(CNMKPoiResult arg0, int arg1, boolean arg2,
			String arg3) {
		
	}

	@Override
	public void onGetReverseGeoCodingResult(CNMKReverseGeocodingResult arg0,
			int arg1, boolean arg2, String arg3) {
		
	}

	@Override
	public void onGetTransitCityResult(CNMKCityResult arg0, int arg1,
			boolean arg2, String arg3) {
		
	}

	@Override
	public void onGPSStatusChanged(GpsStatus arg0) {
		
	}

	@Override
	public void onLocationChanged(CNMKLocation location) 
	{
		if(location==null)
			return;
		point=new GeoPoint((int)(location.getLatitude()*(1e6*AA.LV)),
				(int)(location.getLongitude()*(1e6*AA.LV)));
		
		mayplaceLay.Fresh(new CNMKOverlayItem(point,"1","1"));
		mMapView.getController().setCenter(point);
		poisearch(point);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
	{
		Intent intent=new Intent();
		intent.putExtra("location", (String)listdata.get(position).get("text"));
		intent.putExtra("lat", (point.lat*1e-6)/AA.LV);
		intent.putExtra("lon", (point.lon*1e-6)/AA.LV);
		setResult(RESULTCODE, intent);
		finish();
		
	}

	@Override
	public void onGetNewDriverRouteResult(CNMKNewDriveRouteResult arg0,
			int arg1, boolean arg2, String arg3) {
		
	}

	@Override
	public void onGetStepWalkResult(CNMKStepWalkResult arg0, int arg1,
			boolean arg2, String arg3) {
		
	}
	
	@Override
	protected void onDestroy() 
	{
		 CenApplication.mCNMKAPImgr.getLocationManager().removeUpdates(this);
		super.onDestroy();
	}

}
