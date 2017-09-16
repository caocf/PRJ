package com.example.smarttraffic.bike;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.bike.adapter.BikeRideLineDetailAdapter;
import com.example.smarttraffic.bike.bean.BikeFavor;
import com.example.smarttraffic.bike.bean.BikeRidingLineInfo;
import com.example.smarttraffic.bike.db.BikeFavorDB;
import com.example.smarttraffic.common.debug.DebugActivity;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BikeRidingDetailActivity extends FragmentActivity
{
	public static final String BIKE_RIDING_DETATIL_INFO="bike_riding_detail+info";
	
	BikeRidingLineInfo info;
	
	TextView nameTextView;
	TextView infoTextView;
	ListView contentListView;
	
	Button searchButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bike_riding_detail);
		
		info=(BikeRidingLineInfo)getIntent().getSerializableExtra(BIKE_RIDING_DETATIL_INFO);
		
		initHead();
		initView();
	}
	
	public void initView()
	{
		nameTextView=(TextView)findViewById(R.id.listview_bike_riding_detail_name);
		infoTextView=(TextView)findViewById(R.id.listview_bike_riding_detail_info);
		contentListView=(ListView)findViewById(R.id.listview_bike_riding_detail_list);
		
		if(info!=null)
		{
			nameTextView.setText(info.getStationListName());
			infoTextView.setText("全程"+info.getLength()+"米  耗时"+info.getTime()+"分");
		}
		
		searchButton=(Button)findViewById(R.id.listview_bike_riding_detail_button);
		searchButton.setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		contentListView.setAdapter(new BikeRideLineDetailAdapter(this, info.getStationInfos()));
		contentListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				((BikeRideLineDetailAdapter)parent.getAdapter()).change(position);
			}
		});
	}
	
	public void initHead()
	{
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("详情");
		fragment.setRightName("地图");
		fragment.setRightListen(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				StartIntent.startIntent(BikeRidingDetailActivity.this, BikeRidingDetailMapActivity.class);
			}
		});
		
		ManagerFragment.replaceFragment(this, R.id.bike_riding_detail_head, fragment);
	}
	
	public void onclick(View v)
	{
		switch (v.getId())
		{
			case R.id.listview_bike_riding_detail_favor:
				
				BikeFavorDB db=new BikeFavorDB(this);
				BikeFavor bikeFavor=new BikeFavor();
				bikeFavor.setEnd(info.getBikeRiding().getEnd());
				bikeFavor.setLan1(info.getBikeRiding().getLan1());
				bikeFavor.setLan2(info.getBikeRiding().getLan2());
				bikeFavor.setLength(info.getLength());
				bikeFavor.setLon1(info.getBikeRiding().getLon1());
				bikeFavor.setLon2(info.getBikeRiding().getLon2());
				bikeFavor.setStart(info.getBikeRiding().getStart());
				bikeFavor.setTime(info.getTime());
				
				if(!db.isFavorRiding(bikeFavor))
				{
					db.insertRiding(bikeFavor);
					Toast.makeText(this,"收藏成功", Toast.LENGTH_SHORT).show();
				}
				else
				{
					db.delete(bikeFavor);
					Toast.makeText(this,"取消成功", Toast.LENGTH_SHORT).show();
				}
				
				db.CloseDB();
				
				break;
	
			case R.id.listview_bike_riding_detail_suggestion:
				Bundle suggesiontBundle=new Bundle();
				suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 8);
				StartIntent.startIntentWithParam(this, SuggestionActivity.class,suggesiontBundle);
				break;
				
			case R.id.listview_bike_riding_detail_debug:
				Bundle debugBundle=new Bundle();
				debugBundle.putInt(DebugActivity.DEBUG_NAME, 8);
				StartIntent.startIntentWithParam(this, DebugActivity.class,debugBundle);
				break;

		}
	}
}
