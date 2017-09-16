package com.example.smarttraffic.alarm;

import com.example.smarttraffic.HeadNameFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.smartBus.bean.BusLineDetail;
import com.example.smarttraffic.smartBus.bean.StationOnLine;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class AlarmStationSelectActivity extends FragmentActivity
{

	ListView content;
	BusLineDetail busLineDetail;
	AlarmStationSelectAdapter adapter;
	
	public static String STATION_INFO="station_info";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_alarm_station_select);
		
		initHead();
		content=(ListView)findViewById(R.id.alarm_station_list);
		busLineDetail=(BusLineDetail)getIntent().getSerializableExtra(STATION_INFO);
		
		InitListView();
	}

	private void initHead()
	{
		HeadNameFragment fragment=new HeadNameFragment();
		fragment.setTitleName("选择提醒站点");
		
		ManagerFragment.replaceFragment(this, R.id.alarm_station_select_head, fragment);
	}
	
	private void  InitListView()
	{
		if(busLineDetail.getUpList()!=null && busLineDetail.getUpList().size()>0)
			adapter=new AlarmStationSelectAdapter(this, busLineDetail.getUpList());
		else if(busLineDetail.getDownList()!=null && busLineDetail.getDownList().size()>0)
			adapter=new AlarmStationSelectAdapter(this, busLineDetail.getDownList());
			
		content.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				adapter.changeCheck(position);
			}
		});
		
		content.setAdapter(adapter);
	}
	
	public void next(View v)
	{
		Bundle bundle=new Bundle();
		
		if(busLineDetail==null)
			return;
		
		String ids="";
		String names="";
		
		for(StationOnLine l:adapter.getData())
		{
			if(l.isSelect())
			{
				ids+=l.getStationId()+",";
				names+=l.getStationName()+",";
			}
		}
		
		
		if(ids.equals(""))
		{
			Toast.makeText(this, "请选择一个或多个站点", Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		bundle.putBoolean(AlarmUpActivity.ALARM_FROM_LINE, true);
		bundle.putString(AlarmUpActivity.ALARM_LINE_ID,String.valueOf(busLineDetail.getLine().getId()));
		bundle.putString(AlarmUpActivity.ALARM_STATITION_ID, ids);
		bundle.putString(AlarmUpActivity.ALARM_STATITION_NAME, names);
		bundle.putString(AlarmUpActivity.ALARM_LINE_NAME,busLineDetail.getLine().getName());
		
		StartIntent.startIntentWithParam(this, AlarmUpActivity.class,bundle);
		this.finish();
	}
}
	
	
