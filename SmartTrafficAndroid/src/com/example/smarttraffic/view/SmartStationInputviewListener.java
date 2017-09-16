package com.example.smarttraffic.view;

import com.example.smarttraffic.R;

/**
 * 自定义驾校输入控件
 * @author Administrator zhou
 *
 */
public class SmartStationInputviewListener
{
	String[] dataStrings;
	int[] dataImages;
	InputListViewListener[] listeners;
	
	InputListView inputListView;
	
	public SmartStationInputviewListener(InputListView inputListView)
	{
		this.inputListView=inputListView;
		dataStrings=new String[]{"语音输入","收藏站点","附近站点","二维码扫一扫"};
		dataImages=new int[]{R.drawable.input_speech_icon,R.drawable.input_favorites_icon,R.drawable.smart_bus_nearby_station,R.drawable.smart_bus_qr};
		
	}
	
	public void setListener(InputListViewListener[] listeners)
	{
		this.listeners=listeners;
		
		inputListView.updateView(dataStrings, dataImages, listeners);
	}
}
