package com.sts.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoString 
{
	
	public static final String DEFAULT_SPLITSTRING_STRING=",";
	
	public static InputStream String2InputStream(String str)
	{
	   ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
	   return stream;
	}


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
	
	public static String[] split(String data,String splitString)
	{
		return data.split(splitString);
	}
	
	public static String merger(String[] data,String mergerString)
	{
		String result="";
		
		for(int i=0;i<data.length;i++)
		{
			result+=data[i];
			result+=mergerString;
		}
		
		return result;
	}
	
	public static String[] splitByDefault(String data)
	{
		return split(data, DEFAULT_SPLITSTRING_STRING);
	}
	
	public static String mergerByDefault(String[] data)
	{
		return merger(data, DEFAULT_SPLITSTRING_STRING);
	}
	
	public static Date ParseDateFromHour(String date,int kind)
	{	
		try
		{
			if(kind==0)
			{
				return new SimpleDateFormat("HH:mm:ss").parse(date);
			}
			else if(kind==1)
			{
				return new SimpleDateFormat("HH:mm").parse(date);
			}
		} catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Date ParseDateFromHour(String date)
	{
		return ParseDateFromHour(date,0);
	}
	
	public static long DateCompare(Date start,Date end)
	{
		return (end.getTime()-start.getTime())/1000/60;
	}
	
	public static long DateCompare(String start,String end,int parseKind)
	{
		if(parseKind==0)
			return DateCompare(ParseDateFromHour(start), ParseDateFromHour(end));
		else if(parseKind==1)
			return DateCompare(ParseDateFromHour(start,1), ParseDateFromHour(end,1));
		else 
			return -1l;
	}
	
	public static long DateCompare(String start,String end)
	{
		return DateCompare(start, end,0);
	}
	
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
}
