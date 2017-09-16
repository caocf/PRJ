package com.huzhouport.wharfwork;




import java.util.Timer;
import java.util.TimerTask;

import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import com.example.huzhouportpublic.R;

import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.HttpUtil;

import com.huzhouport.map.MapTool;

import android.app.Activity;
import android.os.Bundle;


public class WharfWorkView1 extends Activity {

    MapTool mapTool;
    MapView mapView;

	protected void onCreate(Bundle savedInstanceState)
	{
		
		// 声明在布局前
		mapTool = new MapTool(getApplication());
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wharfwork_view1);
		mapView = (MapView) findViewById(R.id.wharfwork_view1_map);
		
		// 初始化地图
		mapTool.iniMapView(mapView, WharfWorkView1.this);

		mapTool.locationDefault();
		mapTool.locationOnce();
		  GeoPoint gp= mapTool.getAddrLocation();	
			if(gp==null){
			System.out.println("location123定位失败，无法提交");
		}else{
			
		}
		
		

		

	}

	
}
