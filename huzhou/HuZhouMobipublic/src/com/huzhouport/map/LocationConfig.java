package com.huzhouport.map;

import com.baidu.location.LocationClientOption.LocationMode;

public class LocationConfig
{
	public LocationConfig()
	{
		iniDefault();
	}

	/**
	 * Ĭ�϶�λ������ʼ��
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
	 * ���Ͳ���ѡ��
	 */
	private LocationMode	locationMode;	// ��λģʽ
	private String			sCoorType;		// ����ϵģʽ
	private int				iTimeSpan;		// ʱ��
	private boolean			bReturnAddress; // ���ص�ַ��Ϣ
	private boolean			bReturnDirect;	// ���ط�����Ϣ

	/**
	 * Hight_Accuracy:��ȷ Device_Sensors:�豸����GPS Battery_Saving:ʡ��
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
	 * bd0911:�ٶȾ�γ������ϵ bd09:�ٶ�ī��������ϵ gcj02:����־�γ������ϵ gps:GPS
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
	 * ���ز���ѡ��
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
	 * ��Χ���Ѳ���
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
