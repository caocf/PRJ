package com.example.smarttraffic.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import cennavi.cenmapsdk.android.AA;
import cennavi.cenmapsdk.android.GeoPoint;

/**
 * 字符串相关操作
 * @author Administrator zhou
 *
 */
@SuppressLint("SimpleDateFormat") public class DoString 
{
	//默认字符串分割符
	public static final String DEFAULT_SPLITSTRING_STRING=",";
	
	/**
	 * 字符串转输入流
	 * @param str 字符串
	 * @return 输入流
	 */
	public static InputStream String2InputStream(String str)
	{
	   ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
	   return stream;
	}

	/**
	 * 输入流转字符串
	 * @param is 输入流
	 * @return 字符串
	 */
	public static String inputStream2String(InputStream is)
	{
	   BufferedReader in = new BufferedReader(new InputStreamReader(is));
	   StringBuffer buffer = new StringBuffer();
	   String line = "";
	   try 
	   {
		   while ((line = in.readLine()) != null)
		   {
		     buffer.append(line);
		   }
	   } 
	   catch (IOException e)
	   {
		   e.printStackTrace();
	   }
	   return buffer.toString();
	}
	
	/**
	 * 分割字符串
	 * @param data 目标字符串
	 * @param splitString 分隔符
	 * @return 分割后字符串组
	 */
	public static String[] split(String data,String splitString)
	{
		return data.split(splitString);
	}
	
	/**
	 * 合并字符串
	 * @param data 字符串组
	 * @param mergerString 合并符
	 * @return 合并后字符串
	 */
	public static String merger(String[] data,String mergerString)
	{	
		if(data==null)
			return "";
		String result="";
		
		for(int i=0;i<data.length;i++)
		{
			result+=data[i];
			result+=mergerString;
		}
		
		return result;
	}
	
	
	/**
	 * 默认分割字符串
	 * @param data 目标字符串
	 * @return 分割后字符串
	 */
	public static String[] splitByDefault(String data)
	{
		return split(data, DEFAULT_SPLITSTRING_STRING);
	}
	
	/**
	 * 默认合并字符串
	 * @param data 字符串组
	 * @return 合并后字符串
	 */
	public static String mergerByDefault(String[] data)
	{
		return merger(data, DEFAULT_SPLITSTRING_STRING);
	}
	
	@SuppressLint("SimpleDateFormat") public static Date StringToDate(String date,String mode)
	{
		try
		{
			return new SimpleDateFormat(mode).parse(date);
		} 
		catch (Exception e)
		{
		}
		
		return null;
	}
	
	
	public static String DateToString(Date date,String mode)
	{
		SimpleDateFormat sdf=new SimpleDateFormat(mode);  
		return sdf.format(date);
	}
	
//	
//	public static Date ParseDateFromHour(String date,String mode)
//	{
//		try
//		{
//			return new SimpleDateFormat(mode).parse(date);
//		} 
//		catch (Exception e)
//		{
//		}
//		
//		return null;
//	}
//	
//	public static Date ParseDateFromHour(String date,int kind)
//	{	
//		try
//		{
//			if(kind==0)
//			{
//				return new SimpleDateFormat("hh:mm:ss").parse(date);
//			}
//			else if(kind==1)
//			{
//				return new SimpleDateFormat("hh:mm").parse(date);
//			}
//		} catch (ParseException e) 
//		{
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	
//	public static Date ParseDateFromHour(String date)
//	{
//		return ParseDateFromHour(date,0);
//	}
//	
//	public static long DateCompare(Date start,Date end)
//	{
//		return (end.getTime()-start.getTime())/1000/60;
//	}
	
//	public static long DateCompare(String start,String end,int parseKind)
//	{
//		if(parseKind==0)
//			return DateCompare(ParseDateFromHour(start), ParseDateFromHour(end));
//		else if(parseKind==1)
//			return DateCompare(ParseDateFromHour(start,1), ParseDateFromHour(end,1));
//		else 
//			return -1l;
//	}
//	
//	public static long DateCompare(String start,String end)
//	{
//		return DateCompare(start, end,0);
//	}
	
	/**
	 * 获取在字符串中指定字符前的所有字符
	 * @param data 目标字符串
	 * @param c 目标字符
	 * @return 目标字符前内容
	 */
	public static String splitSpecial(String data,char c)
	{
		char[] cData=data.toCharArray();
		for(int i=0;i<cData.length;i++)
		{
			if(cData[i]==c)
				return data.substring(0, i);
		}
		
		return data;
	}
	
	/**
	 * 格式化字符串
	 * @param str 字符串
	 * @param params 参数
	 * @return 格式化后字符串
	 */
	public static String format(String str,Object[] params)
	{
		return String.format(str, params);
	}

	
	/**
	 * url编码
	 * @param str  字符串
	 * @return 编码后字符串
	 */
	public static String urlEncode(String str)
	{
		String result="";
		
		try
		{
			URLEncoder.encode(str, "utf-8");
		} catch (Exception e)
		{
		}
		
		return result;
	}
	
	/**
	 * URL参数合并
	 * @param url url
	 * @param param 参数
	 * @param value 值
	 * @return 合并后字符串
	 */
	public static String URLJoint(String url,String param,String value)
	{
		return url+param+"="+value+"&";
	}
	
	/**
	 * url参数合并
	 * @param url url
	 * @param param 参数
	 * @param value 值
	 * @return 合并后字符串
	 */
	public static String URLJoint(String url,String param,int value)
	{
		return url+param+"="+value+"&";
	}
	
	/**
	 * 根据日期 获取中文星期
	 * @param c 日期
	 * @return 中文星期
	 */
	public static String GetWeekName(Calendar c)
	{
		String[] weeks={"日","一","二","三","四","五","六"};
		return weeks[c.get(Calendar.DAY_OF_WEEK)-1];
	}
	
	
	public static String parseThreeNetString(String data)
	{
		String data1=data;
		data1=data1.replace("\\", "");
		data1=data1.replace("\"{", "{");
		data1=data1.replace("}\"", "}");
		return data1;
	}
	
	public static GeoPoint[] parseGeoFrom86ToSiwei(GeoPoint[] geo)
	{
		if(geo==null)
			return null;
		GeoPoint[] result=new GeoPoint[geo.length];
		
		for(int i=0;i<result.length;i++)
		{
			result[i]=new GeoPoint(geo[i].getLatitudeE6()*AA.LV,geo[i].getLongitudeE6()*AA.LV);
		}
		
		return result;
	}
	
	
	
	public static final String LimitStrLength(String str,int length)
	{
		if(str.length()<=length)
			return str;
		else
			return str.substring(0, length);
	}
	
	public static final String OneWordOneLine(String s)
	{
		String result="";
		
		for(int i=0;i<s.length();i++)
		{
			result+=s.charAt(i)+"\n";
		}
		
		return result;
	}
	
}
