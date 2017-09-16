package com.huzhouport.map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;

import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.content.Context;

public class MapLocation
{
	public MapLocation(Context c, MapView m)
	{
		context = c;
		mapView = m;

		iniLocation();
		locationConfig = new LocationConfig();
	}

	
	/**
	 * 定位所需的变量
	 */
	private MapView				mapView;		// 地图对象
	private LocationClient		locationClient; // 定位对象
	public LocationConfig		locationConfig; // 定位配置
	private BDNotifyListener	notifyListener; // 通知监听
	// 全局上下文
	private Context				context;

	public void setcontext(Context c)
	{
		context = c;
	}

	public Context getcontext()
	{
		return context;
	}

	/**
	 * 初始化,使用默认监听函数
	 */
	private void iniLocation()
	{
		locationClient = new LocationClient(context);

		// Toast.makeText(context,
		// locationClient.getAccessKey(),Toast.LENGTH_LONG).show();

		locationClient.registerLocationListener(locationListener);
	}
	
	/**
	 * 初始化，添加外部监听函数
	 * @param bdLocationListener 监听函数
	 */
	public void addListener(BDLocationListener bdLocationListener)
	{
		// Toast.makeText(context,
		// locationClient.getAccessKey(),Toast.LENGTH_LONG).show();

		locationClient.registerLocationListener(bdLocationListener);
		locationListener=bdLocationListener;
	}
	

	/**
	 * 设置定位选项
	 */
	public void setLocationOption()
	{
		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(locationConfig.getlocationMode());
		option.setCoorType(locationConfig.getsCoorType());
		option.setScanSpan(locationConfig.getiTimeSpan());
		option.setIsNeedAddress(locationConfig.getbReturnAddress());
		option.setNeedDeviceDirect(locationConfig.getbReturnDirect());
		locationClient.setLocOption(option);

	}

	/**
	 * 定位开始关闭
	 * 
	 * @param start
	 *            ：开启关闭定位
	 */
	public void locationStart(boolean start)
	{
		if (start)
			locationClient.start();
		else
			locationClient.stop();

	}

	/**
	 * 发送请求 1、定位 2、poi定位 3、离线定位
	 */
	public void sendRequest(int mode)
	{

		if (locationClient != null)
		{
			switch (mode)
			{
				case 1:
					locationClient.requestLocation();
					break;
				case 2:
					locationClient.requestPoi();
					break;
				case 3:
					locationClient.requestOfflineLocation();
					break;

			}

		}
	}

	/**
	 * 移除监听器
	 */
	public void removeListener(BDLocationListener bdLocationListener)
	{
		if(bdLocationListener==null)
			locationClient.unRegisterLocationListener(locationListener);
		else
			locationClient.unRegisterLocationListener(bdLocationListener);
	}

	public void removeListener()
	{
		locationClient.unRegisterLocationListener(locationListener);
	}
	
	/**
	 * 添加监听器
	 */
	private BDLocationListener	locationListener	= new BDLocationListener()
													{

														@Override
														public void onReceivePoi(
																BDLocation location)
														{

														}

														@Override
														public void onReceiveLocation(
																BDLocation location)
														{

															if (location == null)
																return;

															locationConfig
																	.setsTime(location
																			.getTime());
															locationConfig
																	.setiLocType(location
																			.getLocType());
															locationConfig
																	.setdLantitude(location
																			.getLatitude());
															locationConfig
																	.setdlongtitude(location
																			.getLongitude());
															locationConfig
																	.setfRadius(location
																			.getRadius());

															if (location
																	.getLocType() == BDLocation.TypeGpsLocation)
															{
																locationConfig
																		.setfSpeed(location
																				.getSpeed());
																locationConfig
																		.setiSatelliteNumber(location
																				.getSatelliteNumber());
															}
															else if (location
																	.getLocType() == BDLocation.TypeNetWorkLocation)
															{
																locationConfig
																		.setsAddr(location
																				.getAddrStr());
															}
															
															if(mapView==null || mapView.getOverlays()==null)
																return;
															
															MyLocationOverlay myLocationOverlay = new MyLocationOverlay(
																	mapView);
															LocationData locationData = new LocationData();

															locationData.latitude = location
																	.getLatitude();
															locationData.longitude = location
																	.getLongitude();

															myLocationOverlay
																	.setData(locationData);
															mapView.getOverlays()
																	.add(myLocationOverlay);
															mapView.refresh();

															mapView.getController()
																	.animateTo(
																			new GeoPoint(
																					(int) (locationData.latitude * 1e6),
																					(int) (locationData.longitude * 1e6)));
															

														}
													};

	/**
	 * 添加通知
	 */
	public void addNotifyListener()
	{
		notifyListener = new MyNotifyListener();
		notifyListener.SetNotifyLocation(locationConfig.getdDstLantitude(),
				locationConfig.getdDstLongtitude(),
				locationConfig.getdDstRadius(), locationConfig.getsDstType());
		locationClient.registerNotify(notifyListener);
	}

	/**
	 * 移除通知
	 */
	public void removeNotifyListener()
	{
		locationClient.removeNotifyEvent(notifyListener);
	}
}

class MyNotifyListener extends BDNotifyListener
{
	/**
	 * 通知事件响应
	 */
	public void onNotify(BDLocation mlocation,float distance)
	{
	}
}
