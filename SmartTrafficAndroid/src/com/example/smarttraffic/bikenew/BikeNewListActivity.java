package com.example.smarttraffic.bikenew;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cennavi.cenmapsdk.android.GeoPoint;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.BikeStationDetailActivity;
import com.example.smarttraffic.bike.BikeStationRequestByLocation;
import com.example.smarttraffic.bike.BikeStationSearch;
import com.example.smarttraffic.bike.adapter.BikeStationInfoListAdapter;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfo;
import com.example.smarttraffic.bike.bean.BikeParkingPlaceInfoTemp;
import com.example.smarttraffic.bike.bean.BikeStation;
import com.example.smarttraffic.bike.fragment.BikeStationInfoListFragment;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.map.PointTraslation;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BikeNewListActivity extends FragmentActivity
{
	public static final String LAN = "lan";
	public static final String LON = "lon";

	ListView content;
	BikeStationInfoListAdapter listAdapter;

	public int distance=500;
	
	public double lan;
	public double lon;

	List<BikeStation> dataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_parking_list);

		initHead();

		lan = getIntent().getDoubleExtra(LAN, -1);System.out.println("dsagdsyherbbb");
		lon = getIntent().getDoubleExtra(LON, -1);System.out.println(lon);

		content = (ListView) findViewById(R.id.parking_list_content);
		content.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				BikeStation bikeStation=listAdapter.getData().get(position);
				Bundle bundle=new Bundle();
				bundle.putSerializable(BikeStationInfoListFragment.BIKE_STATION_INFO, bikeStation);
				
				StartIntent.startIntentWithParam(BikeNewListActivity.this, BikeStationDetailActivity.class,bundle);	
			}
		});
		
		if(lan>0 && lon>0)
		{
			transfor();
			search(lan, lon);
		}
	}
	
	/**
	 * 02转84
	 */
	private void transfor()
	{
		GeoPoint point1= PointTraslation.gcj_To_Gps84(lan, lon);

		lan=point1.getLatitudeE6()*1.0/1e6;
		lon=point1.getLongitudeE6()*1.0/1e6;
	}

	long clickTime=-1;
	
	private void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("搜索结果");
		fragment.setRightName("地图");
		fragment.setRightListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				
				long now =new Date().getTime();
				
				if((now-clickTime)<1000)
				{
					return;
				}
				
				clickTime=now;
				 
				Bundle bundle=new Bundle();
				
				bundle.putDouble(BikeNewMapActivity.LAN, lan);
				bundle.putDouble(BikeNewMapActivity.LON, lon);
				bundle.putSerializable(BikeNewMapActivity.DATA, (Serializable)dataList);
				
				StartIntent.startIntentWithParam(BikeNewListActivity.this, BikeNewMapActivity.class, bundle);
			}
		});
		ManagerFragment.replaceFragment(this, R.id.parking_list_head, fragment);
	}

	private void search(double lan, double lon)
	{
		BikeStationRequestByLocation request=new BikeStationRequestByLocation();
		request.setLan(lan);
		request.setLon(lon);
		request.setCount(300);
		request.setDistance(distance);

		new HttpThread(new BikeStationSearch(), request, new UpdateView()
		{
			
			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				dataList = (List<BikeStation>) data;
				
				if((dataList==null || dataList.size()==0) && distance==500)
				{
					distance=2000;
					
					search(BikeNewListActivity.this.lan, BikeNewListActivity.this.lon);
				}
				else if(dataList!=null && dataList.size()>0)
					searchReal();
				else 
					Toast.makeText(BikeNewListActivity.this,"附近无自行车点", Toast.LENGTH_SHORT).show();
			}
		}, this,
				"查询自行车点失败!").start();
	}
	
	private void searchReal()
	{
		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String d)
			{
				BikeParkingPlaceInfoTemp temp = JSON.parseObject(
						DoString.parseThreeNetString(d),
						BikeParkingPlaceInfoTemp.class);

				for (BikeParkingPlaceInfo b : temp.getList())
				{
					for (BikeStation s : dataList)
					{
						if (b.getStationid() == s.getId())
						{
							s.setLeft(b.getCount());
							break;
						}
					}
				}

				return dataList;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				String result = HttpUrlRequestAddress.SMART_BIKE_STATION_REAL_BY_ID_URL
						+ "?bikeList=";

				for (int i = 0; i < dataList.size(); i++)
				{
					if (i > 0)
						result += ",";
					result += dataList.get(i).getId();
				}

				return result;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object d)
			{
				show(dataList);
			}
		}, this, "查找实时数据失败").start();
	}
	
	
	private void show(List<BikeStation> data)
	{
		if(listAdapter==null)
		{
			listAdapter=new BikeStationInfoListAdapter(BikeNewListActivity.this, data);
			content.setAdapter(listAdapter);
		}
		else
		{
			listAdapter.refreshList(data);
		}
	}
}
