package com.sts.common.util;

import java.math.BigDecimal;

import com.sts.common.model.Degree;

public class GPSToMeter {
	public static double EARTH_RADIUS = 6378.137;// 地球半径

	public static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	private static double degrees(double d) {
		return d * (180 / Math.PI);
	}

	/**
	 * 
	 * @param value
	 * @param scale
	 * @return
	 */
	public static double round(double value, int scale) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

	/**
	 * 
	 * @param Degree1
	 * @param distance
	 * @return
	 */
	public static Degree[] GetDegreeCoordinates(Degree Degree1, double distance) {
		double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS))
				/ Math.cos(Degree1.getX()));
		dlng = degrees(dlng);//
		double dlat = distance / EARTH_RADIUS;
		dlat = degrees(dlat);//
		return new Degree[] {
				new Degree(round(Degree1.getX() + dlat, 6), round(
						Degree1.getY() - dlng, 6)),// left-top
				new Degree(round(Degree1.getX() - dlat, 6), round(
						Degree1.getY() - dlng, 6)),// left-bottom
				new Degree(round(Degree1.getX() + dlat, 6), round(
						Degree1.getY() + dlng, 6)),// right-top
				new Degree(round(Degree1.getX() - dlat, 6), round(
						Degree1.getY() + dlng, 6)) // right-bottom
		};
	}
}
