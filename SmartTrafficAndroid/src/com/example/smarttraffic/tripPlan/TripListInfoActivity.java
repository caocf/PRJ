package com.example.smarttraffic.tripPlan;

import java.util.List;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.debug.DebugActivity;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.nearby.NearByActivity;
import com.example.smarttraffic.tripPlan.adapter.LineInfoAdapter;
import com.example.smarttraffic.tripPlan.bean.TripListInfo;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 出行规划详情界面
 * @author Administrator zhou
 *
 */
public class TripListInfoActivity extends FragmentActivity
{
	ListView detailInfoListView;
	TextView mainInfoTextView;
	
	TripListInfo info;
	LineInfoAdapter adapter;
	
	ImageView favorImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_trip_list_info);
		
		detailInfoListView=(ListView)findViewById(R.id.trip_list_detail_list_info);
		mainInfoTextView=(TextView)findViewById(R.id.trip_list_main_info);
		
		HeadFavorFragment fragment=new HeadFavorFragment();
		fragment.setTitleName("自驾线路规划");
		fragment.setRightName("地图");
		fragment.setRightListen(new onToMap());
		
		ManagerFragment.replaceFragment(this, R.id.trip_list_head, fragment);
		
		info=(TripListInfo)getIntent().getSerializableExtra(TripMapActivity.LIST_INFO);
		
		mainInfoTextView.setText( String.format("%.2f ",info.getLenght())+"公里");
		
		List<String> tempList=info.getLineData();
		tempList.add(0, info.getStart());
		tempList.add(info.getEnd());
		adapter=new LineInfoAdapter(this,  tempList);
		
		detailInfoListView.setAdapter(adapter);	
		
		favorImageView=(ImageView)findViewById(R.id.trip_list_favor);
		FavorDBOperate dbOperate=new FavorDBOperate(this);
		if(dbOperate.isFavor(ContentType.TRIP_SELF_FAVOR, info.getStart(), info.getEnd()))
		{
			favorImageView.setImageResource(R.drawable.selector_trip_has_favor);
		}
		dbOperate.CloseDB();
	}
	
	class onToMap implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			TripListInfoActivity.this.finish();
		}
	}
	
	public void onclick(View v)
	{
		switch (v.getId())
		{
			case R.id.trip_list_favor:
				FavorDBOperate dbOperate=new FavorDBOperate(this);
				if(!dbOperate.isFavor(ContentType.TRIP_SELF_FAVOR, info.getStart(), info.getEnd()))
				{
					dbOperate.insert(ContentType.TRIP_SELF_FAVOR, info.getStart(), info.getEnd(),info.getLan1(),info.getLon1(),info.getLan2(),info.getLon2());
					favorImageView.setImageResource(R.drawable.selector_trip_has_favor);
				}
				else
				{
					int id=dbOperate.getFavorID(ContentType.TRIP_SELF_FAVOR, info.getStart(), info.getEnd());
					if(id!=-1)
					{
						dbOperate.delete(id);
						favorImageView.setImageResource(R.drawable.selector_trip_info_favor);
					}
				}
				dbOperate.CloseDB();
				break;
				
			case R.id.trip_list_suggest:
				Bundle suggesiontBundle=new Bundle();
				suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 1);
				StartIntent.startIntentWithParam(this, SuggestionActivity.class,suggesiontBundle);
				break;
			case R.id.trip_list_debug:
				Bundle debugBundle=new Bundle();
				debugBundle.putInt(DebugActivity.DEBUG_NAME, 1);
				StartIntent.startIntentWithParam(this, DebugActivity.class,debugBundle);
				break;
				
			case R.id.trip_list_circle:
				StartIntent.startIntent(this, NearByActivity.class);
				break;
		}
	}
}
