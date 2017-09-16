package com.example.smarttraffic.smartBus;

import com.example.smarttraffic.HeadFavorFragment;
import com.example.smarttraffic.R;
import com.example.smarttraffic.fragment.ManagerFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class SmartBusTransferDetailActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_smart_bus_transfer_detail);

		HeadFavorFragment fragment = new HeadFavorFragment();
		fragment.setTitleName("换乘方案详情");
		fragment.setRightName("地图");
		ManagerFragment.replaceFragment(this,
				R.id.smart_bus_transfer_detail_head, fragment);
	}
}
