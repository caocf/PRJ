package com.huzhouport.map;

import com.baidu.location.LocationClientOption.LocationMode;

public class LocationConfig
{
	public LocationConfig()
	{
		iniDefault();
	}

	/**
	 * 默认定位参数初始化
	 */
	public void iniDefault()
	{
		setlocationMode(BaseMapData.LOCATION_DEFAULT);
		setsCoorType(BaseMapData.COORTYPE_DEFAULT);
		setiTimeSpan(BaseMapData.TIMESPAN_DEFAULT);
		setbReturnAddress(BaseMapData.RETURN_ADD_DEFAULT);
		setbReturnDirect(BaseMapData.RETURN_DIRECT_DEFAULT);

		setdDstRadius(BaseMapData.DST_RADIUS_DEFAULT);
		setsDstType(BaseMapData.DST_TYPE_DEFAULT);
	}

	/**
	 * 发送参数选项
	 */
	private LocationMode	locationMode;	// 定位模式
	private String			sCoorType;		// 坐标系模式
	private int				iTimeSpan;		// 时间
	private boolean			bReturnAddress; // 返回地址信息
	private boolean			bReturnDirect;	// 返回方向信息

	/**
	 * Hight_Accuracy:精确 Device_Sensors:设备自身GPS Battery_Saving:省电
	 */
	public void setlocationMode(int mode)
	{
		switch (mode)
		{
			case 1:
				locationMode = LocationMode.Hight_Accuracy;
				break;
			case 2:
				locationMode = LocationMode.Device_Sensors;
				break;
			case 3:
				locationMode = LocationMode.Battery_Saving;
				break;
		}
	}

	/**
	 * bd0911:百度经纬度坐标系 bd09:百度墨卡托坐标系 gcj02:国测局经纬度坐标系 gps:GPS
	 * 
	 */
	public void setsCoorType(int type)
	{
		switch (type)
		{
			case 1:
				sCoorType = "bd09ll";
				break;
			case 2:
				sCoorType = "bd09";
				break;
			case 3:
				sCoorType = "gcj02";
				break;
		}

	}

	public void setiTimeSpan(int time)
	{
		iTimeSpan = time;
	}

	public void setbReturnAddress(boolean need)
	{
		bReturnAddress = need;
	}

	public void setbReturnDirect(boolean need)
	{
		bReturnDirect = need;
	}

	public LocationMode getlocationMode()
	{
		return locationMode;
	}

	public String getsCoorType()
	{
		return sCoorType;
	}

	public int getiTimeSpan()
	{
		return iTimeSpan;
	}

	public boolean getbReturnAddress()
	{
		return bReturnAddress;
	}

	public boolean getbReturnDirect()
	{
		return bReturnDirect;
	}

	// -----------------------------------------------------------

	/**
	 * 返回参数选项
	 */

	private String	sTime;
	private int		iLocType;
	private double	dLantitude;
	private double	dLongitude;
	private float	fRadius;

	private float	fSpeed;
	private int		iSatelliteNumber;
	private String	sAddr;

	public String getsTime()
	{
		return sTime;
	}

	public int getiLocType()
	{
		return iLocType;
	}

	public double getdLantitude()
	{
		return dLantitude;
	}

	public double getdlongtitude()
	{
		return dLongitude;
	}

	public float getfRadius()
	{
		return fRadius;
	}

	public float getfSpeed()
	{
		return fSpeed;
	}

	public int getiSatelliteNumber()
	{
		return iSatelliteNumber;
	}

	public String getsAddr()
	{
		return sAddr;
	}

	public void setsTime(String t)
	{
		sTime = t;
	}

	public void setiLocType(int type)
	{
		iLocType = type;
	}

	public void setdLantitude(double d)
	{
		dLantitude = d;
	}

	public void setdlongtitude(double d)
	{
		dLongitude = d;
	}

	public void setfRadius(float f)
	{
		fRadius = f;
	}

	public void setfSpeed(float f)
	{
		fSpeed = f;
	}

	public void setiSatelliteNumber(int n)
	{
		iSatelliteNumber = n;
	}

	public void setsAddr(String s)
	{
		sAddr = s;
	}

	// --------------------------------------------------------

	/**
	 * 范围提醒参数
	 * 
	 */
	private double	dDstLantitude;
	private double	dDstLongtitude;
	private float	dDstRadius;
	private String	sDstType;

	public void setdDstLantitude(double d)
	{
		dDstLantitude = d;
	}

	public void setdDstLongtitude(double d)
	{
		dDstLongtitude = d;
	}

	public void setdDstRadius(float f)
	{
		dDstRadius = f;
	}

	public void setsDstType(String s)
	{
		sDstType = s;
	}

	public double getdDstLantitude()
	{
		return dDstLantitude;
	}

	public double getdDstLongtitude()
	{
		return dDstLongtitude;
	}

	public float getdDstRadius()
	{
		return dDstRadius;
	}

	public String getsDstType()
	{
		return sDstType;
	}

}
