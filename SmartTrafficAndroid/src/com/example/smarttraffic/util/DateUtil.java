package com.example.smarttraffic.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Administrator zwc
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil
{
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String DateToStringChina(Date d)
	{
		String result = "";

		result = new SimpleDateFormat("MM月dd HH:mm").format(d);

		return result;
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String DateToString(Date d)
	{
		return DateToString(d, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * @param d
	 * @param format
	 * @return
	 */
	public static String DateToString(Date d, String format)
	{
		String result = "";

		try
		{
			result = new SimpleDateFormat(format).format(d);
		} catch (Exception e)
		{
		}

		return result;
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public static Date StringToDate(String d)
	{
		Date date = null;
		try
		{
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public static Date StringToDateContainsT(String d)
	{
		return StringToDate(d.replaceAll("T", " "));
	}

}
