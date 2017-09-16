package com.example.smarttraffic.alarm;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.bean.BusStationDetail;
import com.example.smarttraffic.smartBus.bean.LineOnStation;
import com.example.smarttraffic.smartBus.bean.Station;
import com.example.smarttraffic.util.DoString;
import com.example.smarttraffic.util.ListviewControl;
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

public class AlarmLineSelectActivity extends FragmentActivity
{

	public static final String SMART_BUS_STATTON_ID="smart_bus_station_id";
	public static final String SMART_BUS_STATION_ORIENT="smart_bus_station_orient";
	
	int stationID;
	int orint;
	
	TextView nameTextView;
	
	ListView lineListView;
	Station station;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_alarm_line_select);
		
		initHead();
		initview();
		
		stationID=getIntent().getIntExtra(SMART_BUS_STATTON_ID, 0);
		orint=getIntent().getIntExtra(SMART_BUS_STATION_ORIENT, 1);
		
		if(stationID!=0)
		{
			QueryStation(stationID,orint);
		}
	}
	
	private void initview()
	{
		nameTextView=(TextView)findViewById(R.id.alarm_line_select_name);
		lineListView=(ListView)findViewById(R.id.alarm_line_select_listview);
		lineListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				((AlarmLineSelectAdapter)parent.getAdapter()).change(position);
			}
		});
		
	}

	private void initHead()
	{
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("电子站牌");
		
		ManagerFragment.replaceFragment(this, R.id.alarm_line_select_head, fragment);
	}
	
	private void QueryStation(int id,int orient)
	{
		this.stationID=id;
		this.orint=orient;
		new HttpThread(new BaseSearch(){

			@Override
			public Object parse(String data)
			{	
				BusStationDetail station=new BusStationDetail();
				
				station=JSON.parseObject(DoString.parseThreeNetString(data),BusStationDetail.class);
				
				return station;
				
			}}, new BaseRequest(){

			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.SMART_BUS_STATION_BY_ID_URL+"?stationID="+stationID;
			}}, new UpdateView()
		{
			
			@Override
			public void update(Object data)
			{
				busStationDetail=(BusStationDetail)data;
				
				nameTextView.setText(busStationDetail.getStation().getName());
				lineListView.setAdapter(new AlarmLineSelectAdapter(AlarmLineSelectActivity.this, busStationDetail.getLineList()));
					
				ListviewControl.setListViewHeightBasedOnChildren(lineListView);
			}
		},this,R.string.error_alarm_line_info).start();
	}
	
	BusStationDetail busStationDetail;
	
	class selectOnclickListener implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			((AlarmLineSelectAdapter)parent.getAdapter()).change(position);
		}
	}
	
	public void onclick(View v)
	{
		switch (v.getId())
		{
			case R.id.alarm_line_select_refresh:
				QueryStation(stationID, orint);		
				break;
							
			case R.id.alarm_line_select_exchange:
				orint=orint == 1?2:1;
				QueryStation(stationID, orint);
				break;
			case R.id.alarm_line_select_next:
				next();
				break;
			
		}
	}
	
	public void next()
	{
		if(busStationDetail==null)
			return;
		
		String ids="";
		String names="";
		
		for(LineOnStation l:busStationDetail.getLineList())
		{
			if(l.isSelect())
			{
				ids+=l.getId()+",";
				names+=l.getName()+",";
			}
		}
		
		if(ids.equals(""))
		{
			Toast.makeText(this, "请选择一条或多条线路", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Bundle bundle=new Bundle();
		
		bundle.putString(AlarmUpActivity.ALARM_LINE_ID,ids);
		bundle.putString(AlarmUpActivity.ALARM_STATITION_ID, String.valueOf(busStationDetail.getStation().getId()));
		bundle.putString(AlarmUpActivity.ALARM_STATITION_NAME, busStationDetail.getStation().getName());
		bundle.putString(AlarmUpActivity.ALARM_LINE_NAME, names);
		
		StartIntent.startIntentWithParam(this, AlarmUpActivity.class,bundle);
		this.finish();
	}
}
	
	
