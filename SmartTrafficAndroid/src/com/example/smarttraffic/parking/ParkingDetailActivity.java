package com.example.smarttraffic.parking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.smarttraffic.HeadLCRFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;
import com.example.smarttraffic.util.StartIntent;
import com.example.smarttraffic.util.TextViewUtil;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class ParkingDetailActivity extends FragmentActivity
{
	public static final String PARKING="parking";
	
	Parking parking;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_parking_detail);
		
		parking=(Parking)getIntent().getSerializableExtra(PARKING);
		initHead();
		initData();
	}
	
	private void initData()
	{
		if(parking!=null)
		{
			TextViewUtil.setText(this, R.id.textView1, parking.getPointname());
			TextViewUtil.setText(this, R.id.textView2, parking.getRoadname());
			TextViewUtil.setText(this, R.id.textView3, parking.getFreecount());
		}
	}
	
	private void initHead()
	{
		HeadLCRFragment fragment = new HeadLCRFragment();
		fragment.setTitleName("详情");
		fragment.setRightName("地图");
		fragment.setRightListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(parking==null)
					return;
				
				Bundle bundle=new Bundle();
				
				bundle.putDouble(ParkingMapActivity.LAN, parking.getGpsla());
				bundle.putDouble(ParkingMapActivity.LON, parking.getGpslo());
				
				List<Parking> r=new ArrayList<Parking>();
				r.add(parking);
				
				bundle.putSerializable(ParkingMapActivity.DATA, (Serializable)r);
				
				StartIntent.startIntentWithParam(ParkingDetailActivity.this, ParkingMapActivity.class, bundle);
			}
		});
		ManagerFragment.replaceFragment(this, R.id.parking_detail_head, fragment);
	}
}
