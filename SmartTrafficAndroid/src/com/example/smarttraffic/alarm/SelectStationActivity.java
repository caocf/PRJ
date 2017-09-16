package com.example.smarttraffic.alarm;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.PostParams;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.adapter.StationNearByAdapter;
import com.example.smarttraffic.smartBus.bean.BusStationForQueryByNameTemp;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 查询站点activity
 * @author Administrator zhou
 *
 */
public class SelectStationActivity extends FragmentActivity
{	
	String name;	
	ListView content;
	
	public static final String SELECT_STATION_NAME="select_station_name";

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_point);
		
		name=getIntent().getStringExtra(SELECT_STATION_NAME);
		
		initHead(name);
		initListView();
		request();
	}
	
	private void initListView()
	{
		content=(ListView)findViewById(R.id.smart_bus_select_point_content);
		content.setOnItemClickListener(contentClickListener);
	}
	
	private void initHead(String str)
	{
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("\""+str+"\"相关站点");
		ManagerFragment.replaceFragment(this,R.id.smart_bus_select_point_head, fragment);
	}
	
	private void request()
	{
		new HttpThread(
				new BaseSearch(){

				@Override
				public Object parse(String data)
				{					
					String data1=DoString.parseThreeNetString(data);
					
					BusStationForQueryByNameTemp result=JSON.parseObject(data1, BusStationForQueryByNameTemp.class);
					
					return result;
				}}, 
				new BaseRequest(){

					@Override
					public PostParams CreatePostParams()
					{
						PostParams postParams=new PostParams();
						postParams.setUrl(HttpUrlRequestAddress.SMART_BUS_STATION_SUUGESTION_URL);
						Map<String, Object> d=new HashMap<String, Object>();
						d.put("stationName", name);
						postParams.setParams(d);
						
						return postParams;				
					}

					},
				new UpdateView()	
				{	
						@Override
					public void update(Object data)
					{
							BusStationForQueryByNameTemp temp=(BusStationForQueryByNameTemp)data;
							StationNearByAdapter stationNearByAdapter=new StationNearByAdapter(SelectStationActivity.this, temp.getStationList());
							content.setAdapter(stationNearByAdapter);
					}
				}).start();
	}
	
	OnItemClickListener contentClickListener =new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long ids)
		{
			StationNearByAdapter adapter=((StationNearByAdapter)parent.getAdapter());
			
			Bundle bundle=new Bundle();
			bundle.putInt(AlarmLineSelectActivity.SMART_BUS_STATTON_ID, adapter.getData().get(position).getId());
		
			StartIntent.startIntentWithParam(SelectStationActivity.this, AlarmLineSelectActivity.class, bundle);
		}
	};
	
}
