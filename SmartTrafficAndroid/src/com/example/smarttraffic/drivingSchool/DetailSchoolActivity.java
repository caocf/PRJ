package com.example.smarttraffic.drivingSchool;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.debug.DebugActivity;
import com.example.smarttraffic.common.dialog.GetDialog;
import com.example.smarttraffic.common.localDB.ContentType;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.common.suggestion.SuggestionActivity;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.tripPlan.TripMapActivity;
import com.example.smarttraffic.util.StartIntent;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DetailSchoolActivity extends FragmentActivity {

	TextView nameTextView;
	TextView addressTextView;
	TextView phoneNumberTextView;
	
	ImageView favorImageView;
	boolean isFavor;				//是否已收藏
	DrivingSchool drivingSchool;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_school);
		
		initHead(savedInstanceState);
		initView();
	}
	
	public void initView()
	{
		nameTextView=(TextView)findViewById(R.id.detail_name);
		addressTextView=(TextView)findViewById(R.id.detail_address);
		phoneNumberTextView=(TextView)findViewById(R.id.detail_phone_number);
		
		favorImageView=(ImageView)findViewById(R.id.detail_favor);
		
		drivingSchool=(DrivingSchool)getIntent().getSerializableExtra(DrivingInfoListFragment.DRIVING_SCHOOL_INFO);
		
		nameTextView.setText(drivingSchool.getName());
		addressTextView.setText(drivingSchool.getAddress());
		phoneNumberTextView.setText(drivingSchool.getPhone());
		FavorDBOperate favorDBOperate=new FavorDBOperate(this);
		
		isFavor=favorDBOperate.isFavor(ContentType.DRIBING_SCHOOL_FAVOR, drivingSchool.getName(),drivingSchool.getAddress());
		
		favorDBOperate.CloseDB();
	}
	
	public void initHead(Bundle savedInstanceState)
	{
		if (savedInstanceState == null) 
        {
    		HeadFavorFragment headLCRFragment=new HeadFavorFragment();
    		
    		headLCRFragment.setTitleName("详情");
    		headLCRFragment.setRightName("地图");
    		headLCRFragment.setRightListen(mapListener);
    		
    		ManagerFragment.replaceFragment(this, R.id.driving_detail_head, headLCRFragment);
        }
	}
	
	public static final String MAP_INTENT="map_intent";
	public static final String MAP_PARAMS="map_params";
	
	public OnClickListener mapListener =new OnClickListener()
	{	
		@Override
		public void onClick(View v)
		{
			Bundle bundle=new Bundle();
			bundle.putSerializable(MAP_PARAMS, drivingSchool);
			bundle.putInt(MAP_INTENT, 1);
			
			StartIntent.startIntentWithParam(DetailSchoolActivity.this, DrivingHomeActivity.class, bundle);
		}
	};
	
	public void call(View v)
	{
		GetDialog.messageDialog(this, "提示：", "是否拨号", "是", new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		}, "否", new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}
		}).show();
	}
	
	public void favor(View v)
	{
		if(isFavor)
		{
			Toast.makeText(this, "已收藏", Toast.LENGTH_LONG).show();
		}
		else
		{
			FavorDBOperate favorDBOperate=new FavorDBOperate(this);
			
			int lan=(int)(drivingSchool.getLantitude()*1e6);
			int lon=(int)(drivingSchool.getLongtitude()*1e6);
			favorDBOperate.insert(ContentType.DRIBING_SCHOOL_FAVOR,  drivingSchool.getName(),drivingSchool.getAddress(),lan,lon);
			favorDBOperate.CloseDB();
			Toast.makeText(this, "收藏成功", Toast.LENGTH_LONG).show();
			isFavor=true;
		}
	}
	
	public void gothere(View v)
	{
		Bundle bundle=new Bundle();
		bundle.putSerializable(DrivingInfoListFragment.DRIVING_SCHOOL_INFO, drivingSchool);
		bundle.putInt(TripMapActivity.FROM_NAME, 1);
		
		StartIntent.startIntentWithParam(this, TripMapActivity.class,bundle);
	}
	
	public void suggest(View v)
	{
		Bundle suggesiontBundle=new Bundle();
		suggesiontBundle.putInt(SuggestionActivity.SUGGESTION_NAME, 6);
		StartIntent.startIntentWithParam(this, SuggestionActivity.class,suggesiontBundle);
	}
	
	public void debug(View v)
	{
		Bundle debugBundle=new Bundle();
		debugBundle.putInt(DebugActivity.DEBUG_NAME, 6);
		StartIntent.startIntentWithParam(this, DebugActivity.class,debugBundle);
	}
}
