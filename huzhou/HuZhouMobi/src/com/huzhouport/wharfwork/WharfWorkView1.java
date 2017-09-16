package com.huzhouport.wharfwork;


import java.util.Timer;
import java.util.TimerTask;

import com.baidu.mapapi.map.MapView;

import com.example.huzhouport.R;

import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;

import android.app.Activity;
import android.os.Bundle;


public class WharfWorkView1 extends Activity {

    MapView mapView;

	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wharfwork_view1);
		mapView = (MapView) findViewById(R.id.wharfwork_view1_map);
		

	}

	
}
