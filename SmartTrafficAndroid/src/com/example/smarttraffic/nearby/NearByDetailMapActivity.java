package com.example.smarttraffic.nearby;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cennavi.cenmapsdk.android.GeoPoint;
import cennavi.cenmapsdk.android.map.CNMKMapView;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.drivingSchool.OnPopInterface;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class NearByDetailMapActivity extends FragmentActivity
{

	CNMKMapView mapview;
	MapControl mapControl;
	
	List<NearBy> dataList;
	
	public static final String NEAR_BY_DETATIL_STRING_DATA="near_by_detail_string_data";
	
	String data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_near_by_detail_map);
		
		init();
		
		data=getIntent().getStringExtra(NEAR_BY_DETATIL_STRING_DATA);
		
		
		if(data!=null && data.length()>0)
		{
			dataList=parse(data);
			
			addPoint(dataList);
		}
		
	}
	
	public void addPoint(List<NearBy> list)
	{
		if(list==null || list.size()==0)
			return;
		
		GeoPoint[] geoPoints=new GeoPoint[list.size()];
		for(int i=0;i<list.size();i++)
		{
			geoPoints[i]=new GeoPoint(list.get(i).getLan(), list.get(i).getLon());
		}
		
		mapControl.createMapOverItem(this, getLayoutInflater().inflate(R.layout.map_pop_layout,null), geoPoints, new onPopClick());
	}
	
	class onPopClick implements OnPopInterface
	{		
		int i;		
		@Override
		public View onPop(int i, View v)
		{
			this.i=i;
			if(dataList!=null)
			{
				TextView name=(TextView)v.findViewById(R.id.driving_school_name);
				TextView info=(TextView)v.findViewById(R.id.driving_school_information);
				
				name.setText(dataList.get(i).getName());
				info.setText(dataList.get(i).getAddress());
			}
			
			return v;
		}
	}
	
	public List<NearBy> parse(String data)
	{
		List<NearBy> result=new ArrayList<NearBy>();
		
		try
		{
			JSONObject jsonObject=new JSONObject(data);
			JSONArray array=jsonObject.getJSONArray("circles");
			
			for(int i=0;i<array.length();i++)
			{
				NearBy temp=new NearBy();
				
				jsonObject=array.getJSONObject(i);
				
				temp.setAddress(jsonObject.getString("address"));
				temp.setId(jsonObject.getString("id"));
				temp.setLan(jsonObject.getDouble("lan"));
				temp.setLon(jsonObject.getDouble("lon"));
				temp.setName(jsonObject.getString("name"));
				temp.setRegion(jsonObject.getString("region"));
				temp.setStreet(jsonObject.getString("street"));
				temp.setTime(jsonObject.getString("time"));
				
				result.add(temp);
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
						
		return result;
	}
	
	public void init()
	{
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("周边");
		fragment.setRightName("列表");
		fragment.setRightListen(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				NearByDetailMapActivity.this.finish();
			}
		});
		ManagerFragment.replaceFragment(this, R.id.circle_detail_map_head, fragment);
		
				
		if(mapview==null)
		{
			mapview=(CNMKMapView)findViewById(R.id.base_mapview);
						
			if(mapControl==null)
				mapControl=new MapControl(this);
			mapControl.initMap(mapview);
			mapControl.addLocation();
		}		
	}
	
	@Override
	protected void onPause() {
		CenMapApiDemoApp app = (CenMapApiDemoApp) this.getApplication();
		app.mCNMKAPImgr.stop();
		
		if (mapview != null)
		{
			mapview.destory();
			mapview = null;
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
