package com.example.smarttraffic.view;

import com.example.smarttraffic.R;

/**
 * 自定义驾校输入控件
 * @author Administrator zhou
 *
 */
public class BikeInputviewListener
{
	String[] dataStrings;
	int[] dataImages;
	InputListViewListener[] listeners;
	
	InputListView inputListView;
	
	public BikeInputviewListener(InputListView inputListView)
	{
		this.inputListView=inputListView;
		dataStrings=new String[]{"当前附近","语音输入","地图选点"};
		dataImages=new int[]{R.drawable.input_mylocation_icon,R.drawable.input_speech_icon,R.drawable.input_icon_maplocation};
		
	}
	
	public void setListener(InputListViewListener[] listeners)
	{
		this.listeners=listeners;
		
		inputListView.updateView(dataStrings, dataImages, listeners);
	}
}
