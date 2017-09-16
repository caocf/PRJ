package com.example.smarttraffic.taxi;

import java.io.Serializable;
import java.util.List;

import cennavi.cenmapsdk.android.location.CNMKLocation;
import cennavi.cenmapsdk.android.location.ICNMKLocationListener;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.CenMapApiDemoApp;
import com.example.smarttraffic.map.MapControl;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

import android.location.GpsStatus;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 周边出租车列表
 * 
 * @author Administrator
 * 
 */
public class TaxiListActivity extends FragmentActivity
{

	ListView listView;
	MapControl mapControl;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_taxi_list);

		initHead();
		listView = (ListView) findViewById(R.id.taxi_list_content);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				if(position>0)
				{
					String name=((Taxi)parent.getAdapter().getItem(position)).getSbid();
					
					Bundle bundle=new Bundle();
					bundle.putString(TaxiDetailActivity.TAXI_NAME, name);
					
					StartIntent.startIntentWithParam(TaxiListActivity.this, TaxiDetailActivity.class, bundle);
				}
			}
		});

		mapControl = new MapControl(this);

		location();
	}

	private void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("周边出租车");
		fragment.setRightName("地图");
		fragment.setRightListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Bundle bundle=new Bundle();
				bundle.putSerializable(TaxiMapActivity.TAXI_DATA, (Serializable)data);
				bundle.putDouble(TaxiMapActivity.LAN, lan);
				bundle.putDouble(TaxiMapActivity.LON, lon);
				
				StartIntent.startIntentWithParam(TaxiListActivity.this, TaxiMapActivity.class, bundle);
			}
		});

		ManagerFragment.replaceFragment(this, R.id.taxi_list_head, fragment);
	}

	double lan;
	double lon;
	
	List<Taxi> data;
	
	private void location()
	{
		mapControl.setLocationListener(new ICNMKLocationListener()
		{

			@Override
			public void onLocationChanged(CNMKLocation location)
			{
				mapControl.removeLocationWithoutView();

				lan=location.getLatitude();
				lon=location.getLongitude();
				
				request(location.getLatitude(), location.getLongitude());
			}

			@Override
			public void onGPSStatusChanged(GpsStatus arg0)
			{
			}
		});
		mapControl.addLocationWithoutView();
	}

	private void request(final double lan, final double lon)
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				return JSON.parseArray(JSON.parseObject(data).getJSONArray("results").toJSONString(),Taxi.class);
			}
		}, new BaseRequest()
		{
			public String CreateUrl()
			{				
				return HttpUrlRequestAddress.TAXI_NEARBY_URL+"?radius=5000&num=5&lan="+lon+"&lon="+lan;
			};
		}, new UpdateView()
		{
			@Override
			public void update(Object data)
			{
				TaxiListActivity.this.data=(List<Taxi>)data;
				
				listView.setAdapter(new TaxiAdapter(TaxiListActivity.this.data, TaxiListActivity.this));
			}
		},this,"附近无出租车信息").start();
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
