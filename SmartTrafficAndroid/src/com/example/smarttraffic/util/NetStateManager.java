package com.example.smarttraffic.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ����״̬������
 * @author Administrator zwc
 *
 */
public class NetStateManager
{
	/**
	 * WiFi�Ƿ�����
	 * @param context 
	 * @return �Ƿ�����
	 */
	public static boolean isWifiConnect(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
		
		return wifiInfo.isConnected();
	}
	
	/**
	 * Mobile�ƶ������Ƿ�����
	 * @param context
	 * @return �Ƿ�����
	 */
	public static boolean isMobileConnect(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
		NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		
		return mobileInfo.isConnected();
	}
	
	/**
	 * �ж������Ƿ����ӣ�WiFi���ƶ�������һ�����Ӽ�Ϊ��
	 * @param context
	 * @return �Ƿ�����
	 */
	public static boolean isNetConnect(Context context)
	{
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
		NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); 
		
		return wifiInfo.isConnected() || mobileInfo.isConnected();
	}
}
