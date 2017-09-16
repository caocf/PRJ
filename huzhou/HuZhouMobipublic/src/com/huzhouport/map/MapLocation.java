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
	 * ��λ����ı���
	 */
	private MapView				mapView;		// ��ͼ����
	private LocationClient		locationClient; // ��λ����
	public LocationConfig		locationConfig; // ��λ����
	private BDNotifyListener	notifyListener; // ֪ͨ����
	// ȫ��������
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
	 * ��ʼ��,ʹ��Ĭ�ϼ�������
	 */
	private void iniLocation()
	{
		locationClient = new LocationClient(context);

		// Toast.makeText(context,
		// locationClient.getAccessKey(),Toast.LENGTH_LONG).show();

		locationClient.registerLocationListener(locationListener);
	}
	
	/**
	 * ��ʼ��������ⲿ��������
	 * @param bdLocationListener ��������
	 */
	public void addListener(BDLocationListener bdLocationListener)
	{
		// Toast.makeText(context,
		// locationClient.getAccessKey(),Toast.LENGTH_LONG).show();

		locationClient.registerLocationListener(bdLocationListener);
		locationListener=bdLocationListener;
	}
	

	/**
	 * ���ö�λѡ��
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
	 * ��λ��ʼ�ر�
	 * 
	 * @param start
	 *            �������رն�λ
	 */
	public void locationStart(boolean start)
	{
		if (start)
			locationClient.start();
		else
			locationClient.stop();

	}

	/**
	 * �������� 1����λ 2��poi��λ 3�����߶�λ
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
	 * �Ƴ�������
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
	 * ��Ӽ�����
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
	 * ���֪ͨ
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
	 * �Ƴ�֪ͨ
	 */
	public void removeNotifyListener()
	{
		locationClient.removeNotifyEvent(notifyListener);
	}
}

class MyNotifyListener extends BDNotifyListener
{
	/**
	 * ֪ͨ�¼���Ӧ
	 */
	public void onNotify(BDLocation mlocation,float distance)
	{
	}
}
