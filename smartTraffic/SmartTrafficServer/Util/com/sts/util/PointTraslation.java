package com.sts.util;


/**
 * 不同坐标系之间转换 84坐标 火星坐标 百度坐标
 * 
 * @author Administrator zwc
 * 
 */
public class PointTraslation
{
	public static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

	/**
	 * 火星坐标转百度坐标
	 * @param p 火星坐标
	 * @return 百度坐标
	 */
	public static GeoPoint bd_encrypt(GeoPoint p)
	{

		double x = p.getLon1E6() * 1.0 / 1e6;
		double y = p.getLan1E6() * 1.0 / 1e6;

		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);

		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);

		return new GeoPoint(z * Math.sin(theta) + 0.006, z * Math.cos(theta)
				+ 0.0065);
	}

	/**
	 * 百度坐标转火星坐标
	 * @param p 百度坐标
	 * @return 火星坐标
	 */
	public static GeoPoint bd_decrypt(GeoPoint p)
	{
		double x = p.getLon1E6() * 1.0 / 1e6 - 0.0065;
		double y = p.getLan1E6() * 1.0 / 1e6 - 0.006;

		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);

		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);

		return new GeoPoint(z * Math.sin(theta), z * Math.cos(theta));
	}

	public static final double pi = 3.14159265358979324;
	public static final double a = 6378245.0;
	public static final double ee = 0.00669342162296594323;

	/**
	 * 84转火星坐标
	 * @param p 84坐标
	 * @return 火星坐标
	 */
	public static GeoPoint _84To02(GeoPoint p)
	{
		double wgLat = p.getLan1E6() * 1.0 / 1e6;
		double wgLon = p.getLon1E6() * 1.0 / 1e6;

		if (outOfChina(wgLat, wgLon))
		{
			return p;
		}
		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat / 180.0 * pi;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);

		return new GeoPoint(wgLat + dLat, wgLon + dLon);
	}
	
	
	/** 
     * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return 
     */  
    public static GeoPoint gcj_To_Gps84(double lat, double lon) {  
    	GeoPoint gps = transform(lat, lon);  
        double lontitude = lon * 2 - gps.getLon1E6()*1.0/1e6;  
        double latitude = lat * 2 - gps.getLan1E6()*1.0/1e6;  
        return new GeoPoint(latitude, lontitude);  
    }  
	
    public static GeoPoint transform(double lat, double lon) {  
        if (outOfChina(lat, lon)) {  
            return new GeoPoint(lat, lon);  
        }  
        double dLat = transformLat(lon - 105.0, lat - 35.0);  
        double dLon = transformLon(lon - 105.0, lat - 35.0);  
        double radLat = lat / 180.0 * pi;  
        double magic = Math.sin(radLat);  
        magic = 1 - ee * magic * magic;  
        double sqrtMagic = Math.sqrt(magic);  
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);  
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);  
        double mgLat = lat + dLat;  
        double mgLon = lon + dLon;  
        return new GeoPoint(mgLat, mgLon);  
    }  
	

	// 中国境内
	public static boolean outOfChina(double lat, double lon)
	{
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	// 转lan
	public static double transformLat(double x, double y)
	{
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
				+ 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	// 转lon
	static double transformLon(double x, double y)
	{
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
				* Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
				* pi)) * 2.0 / 3.0;
		return ret;
	}

}
