package com.example.smarttraffic.tripPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.nearby.NearBy;
import com.example.smarttraffic.nearby.NearbyDetailListAdapter;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.fragment.SmartBusTransferFragment;
import com.example.smarttraffic.tripPlan.bean.DrivingPlan;
import com.example.smarttraffic.tripPlan.fragment.TripDrivingSelfFragment;
import com.example.smarttraffic.util.StartIntent;

import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 起终点选择页面
 * @author Administrator zhou
 *
 */
public class SelectPointActivity extends FragmentActivity
{	
		
	public static final String SELECT_POINT_ID="select_point_id";
	
	int id;									//是起点还是终点
	ListView content;
	
	DrivingPlan drivingPlan;
	
	MapControl mapControl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_point);
		
		drivingPlan=(DrivingPlan)getIntent().getSerializableExtra(TripDrivingSelfFragment.DRIVING_INFO);
		id=getIntent().getIntExtra(SELECT_POINT_ID,0);
		
		if(id==0)
			initHead(drivingPlan.getStart());
		else if(id==1)
		{
			initHead(drivingPlan.getEnd());
		}
		
		initListView();
		
		if(id==0 && drivingPlan.getStart().equals(SmartBusTransferFragment.MY_LOCATION))
		{
			mapControl=new MapControl(this);
			mapControl.setLocationListener(new ICNMKLocationListener()
			{
				
				@Override
				public void onLocationChanged(CNMKLocation arg0)
				{
					mapControl.removeLocationWithoutView();
					
					Bundle bundle=new Bundle();
					drivingPlan.setLan1((int)(arg0.getLatitude()*1e6));
					drivingPlan.setLon1((int)(arg0.getLongitude()*1e6));
					
					bundle.putInt(SELECT_POINT_ID, 1);
					bundle.putSerializable(TripDrivingSelfFragment.DRIVING_INFO, drivingPlan);
					
					StartIntent.startIntentWithParam(SelectPointActivity.this, SelectPointActivity.class, bundle);
					SelectPointActivity.this.finish();
					
				}
				
				@Override
				public void onGPSStatusChanged(GpsStatus arg0)
				{
				}
			});
			mapControl.addLocationWithoutView();
		}
		else if(id==1 && drivingPlan.getEnd().equals(SmartBusTransferFragment.MY_LOCATION))
		{
			mapControl=new MapControl(this);
			mapControl.setLocationListener(new ICNMKLocationListener()
			{
				
				@Override
				public void onLocationChanged(CNMKLocation arg0)
				{
					mapControl.removeLocationWithoutView();
					
					Bundle bundle=new Bundle();
					
					drivingPlan.setLan2((int)(arg0.getLatitude()));
					drivingPlan.setLon2((int)(arg0.getLongitude()));
					bundle.putSerializable(TripDrivingSelfFragment.DRIVING_INFO, drivingPlan);
					
					StartIntent.startIntentWithParam(SelectPointActivity.this, TripLineMapActivity.class, bundle);
					SelectPointActivity.this.finish();
				}
				
				@Override
				public void onGPSStatusChanged(GpsStatus arg0)
				{
				}
			});
			mapControl.addLocationWithoutView();
		}
		else 
		{
			request();
		}
		
	}
	
	private void initListView()
	{
		content=(ListView)findViewById(R.id.smart_bus_select_point_content);
		content.setOnItemClickListener(contentClickListener);
	}
	
	private void initHead(String str)
	{
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("\""+str+"\"相关地点");
		ManagerFragment.replaceFragment(this,R.id.smart_bus_select_point_head, fragment);
	}
	
	private void request()
	{
		new HttpThread(new BaseSearch(){

			@Override
			public Object parse(String data)
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
			}}, new BaseRequest(){
					@Override
					public PostParams CreatePostParams()
					{
						PostParams param=new PostParams();
						param.setUrl(HttpUrlRequestAddress.SMART_BUS_CIRCLE_BY_NAME_URL);
						
						Map<String, Object> p=new HashMap<String, Object>();
						
						if(id==0)
							p.put("name", drivingPlan.getStart());
						else
							p.put("name", drivingPlan.getEnd());
						param.setParams(p);
						
						return param;
					}
				}, new UpdateView()
		   {
			
			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				content.setAdapter(new NearbyDetailListAdapter(SelectPointActivity.this, (List<NearBy>)data,false));
				
			}
		},this,R.string.error_trip_point_info).start();
	}
	
	OnItemClickListener contentClickListener =new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long ids)
		{
			NearBy nearBy=((NearbyDetailListAdapter)parent.getAdapter()).getData().get(position);
			
			Bundle bundle=new Bundle();
			
			
			if(id==0)
			{
				
				drivingPlan.setStart(nearBy.getName());
				drivingPlan.setLan1((int)(nearBy.getLan()*1e6));
				drivingPlan.setLon1((int)(nearBy.getLon()*1e6));
				
				bundle.putSerializable(TripDrivingSelfFragment.DRIVING_INFO, drivingPlan);
				bundle.putInt(SELECT_POINT_ID, 1);
				
				StartIntent.startIntentWithParam(SelectPointActivity.this, SelectPointActivity.class, bundle);
				SelectPointActivity.this.finish();
			}
			else if(id==1)
			{
				drivingPlan.setEnd(nearBy.getName());
				drivingPlan.setLan2((int)(nearBy.getLan()*1e6));
				drivingPlan.setLon2((int)(nearBy.getLon()*1e6));
				
				bundle.putSerializable(TripDrivingSelfFragment.DRIVING_INFO, drivingPlan);
				StartIntent.startIntentWithParam(SelectPointActivity.this, TripLineMapActivity.class, bundle);
				SelectPointActivity.this.finish();
			}
			
		}
	};
	
	@Override
	protected void onPause() {
		CenMapApiDemoApp app = (CenMapApiDemoApp)this.getApplication();
		app.mCNMKAPImgr.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		CenMapApiDemoApp app = (CenMapApiDemoApp)this.getApplication();
		app.mCNMKAPImgr.start();
		super.onResume();
	}
	
}
