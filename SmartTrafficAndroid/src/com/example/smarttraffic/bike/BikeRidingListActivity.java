package com.example.smarttraffic.bike;

import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.adapter.BikeRideLineListAdapter;
import com.example.smarttraffic.bike.bean.BikeRiding;
import com.example.smarttraffic.bike.bean.BikeRidingLineInfo;
import com.example.smarttraffic.bike.bean.BikeRidingStationOnLine;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.smartBus.SmartBusTransferListActivity;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BikeRidingListActivity extends FragmentActivity
{

	public static final String REQUEST_RIDING_INFO="request_riding_info";
	
	BikeRiding bikeRiding;
	BikeRideLineListAdapter adapter;
	
	TextView startTextView;
	TextView endTextView;
	ListView contentListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bike_riding_list);
		
		bikeRiding=(BikeRiding)getIntent().getSerializableExtra(REQUEST_RIDING_INFO);
		
		initHead();
		
		initView();
		
		search();
		
		Toast.makeText(this, "当前及相关页面为测试数据", Toast.LENGTH_SHORT).show();
	}
	
	private void initView()
	{
		startTextView=(TextView)findViewById(R.id.bike_riding_list_start);
		endTextView=(TextView)findViewById(R.id.bike_riding_list_end);
		contentListView=(ListView)findViewById(R.id.bike_riding_list_listview);
		
		contentListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id)
			{
				Bundle bundle=new Bundle();
				bundle.putSerializable(BikeRidingDetailActivity.BIKE_RIDING_DETATIL_INFO, adapter.getData().get(position));
				StartIntent.startIntentWithParam(BikeRidingListActivity.this, BikeRidingDetailActivity.class, bundle);
			}
		});
		
		setView();
	}
	
	public void initHead()
	{
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("公共自行车");
		fragment.setRightName("地图");
		fragment.setRightListen(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				StartIntent.startIntent(BikeRidingListActivity.this, BikeRidingDetailMapActivity.class);
			}
		});
		
		ManagerFragment.replaceFragment(this, R.id.bike_riding_list_head, fragment);
	}
	
	//模拟
	private void search()
	{
		List<BikeRidingLineInfo> result=new ArrayList<BikeRidingLineInfo>();
		for(int i=0;i<5;i++)
		{
			BikeRidingLineInfo bikeRidingLineInfo=new BikeRidingLineInfo();
			
			List<BikeRidingStationOnLine> list=new ArrayList<BikeRidingStationOnLine>();
			for(int j=0;j<6;j++)
			{
				BikeRidingStationOnLine temp=new BikeRidingStationOnLine();
				temp.setStationName("站点"+j);
				temp.setInfo("选中站点信息");
				list.add(temp);
			}
			
			bikeRidingLineInfo.setLength(1000);
			bikeRidingLineInfo.setTime(20);
			bikeRidingLineInfo.setStationInfos(list);
			bikeRidingLineInfo.setBikeRiding(bikeRiding);
			
			result.add(bikeRidingLineInfo);
		}
		
		adapter=new BikeRideLineListAdapter(this, result);
		contentListView.setAdapter(adapter);
	}
	
	
	
	public void ToBus(View v)
	{
		if(bikeRiding!=null)
		{		
			Bundle bundle=new Bundle();
			bundle.putString(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME, bikeRiding.getStart());
			bundle.putString(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME, bikeRiding.getEnd());
			
			bundle.putDouble(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,bikeRiding.getLan1());
			bundle.putDouble(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,bikeRiding.getLon1());
			
			bundle.putDouble(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LAN,bikeRiding.getLan2());
			bundle.putDouble(SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LON, bikeRiding.getLon2());
			
			StartIntent.startIntentWithParam(this, SmartBusTransferListActivity.class, bundle);
		}
	}
	
	public void setView()
	{
		if(bikeRiding!=null)
		{
			startTextView.setText(bikeRiding.getStart());
			endTextView.setText(bikeRiding.getEnd());
		}
	}
	
	public void Back(View v)
	{
		double tempLan;
		double tempLon;
		String tempName;
		
		tempLan=bikeRiding.getLan1();
		tempLon=bikeRiding.getLon1();
		tempName=bikeRiding.getStart();
		
		bikeRiding.setLan1(bikeRiding.getLan2());
		bikeRiding.setLon1(bikeRiding.getLon2());
		bikeRiding.setStart(bikeRiding.getEnd());
		
		bikeRiding.setLan2(tempLan);
		bikeRiding.setLon2(tempLon);
		bikeRiding.setEnd(tempName);
		
		search();
		
		setView();
	}
}
