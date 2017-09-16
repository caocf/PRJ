package com.huzhouport.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络状态管理类
 * @author Administrator zwc
 *
 */
public class NetStateManager
{
	/**
	 * WiFi是否连接
	 * @param context 
	 * @return 是否连接
	 */
	public static boolean isWifiConnect(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
		
		return wifiInfo.isConnected();
	}
	
	/**
	 * Mobile移动网络是否连接
	 * @param context
	 * @return 是否连接
	 */
	public static boolean isMobileConnect(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		
		return mobileInfo.isConnected();
	}
	
	/**
	 * 判断网络是否连接，WiFi和移动网络有一个连接即为真
	 * @param context
	 * @return 是否连接
	 */
	public static boolean isNetConnect(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
		NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		
		return wifiInfo.isConnected() || mobileInfo.isConnected();
	}
}
