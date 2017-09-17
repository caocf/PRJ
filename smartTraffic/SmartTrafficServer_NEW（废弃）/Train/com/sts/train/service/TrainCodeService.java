package com.sts.train.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.sts.train.model.TrainCode;
import com.sts.util.DoString;

public class TrainCodeService 
{
	public static List<TrainCode> parse(SoapObject object)
	{
		SoapObject parse=object;
		SoapObject data;
		List<TrainCode> result=new ArrayList<TrainCode>();
		TrainCode temp;
		
		parse=(SoapObject)parse.getProperty(1);
		parse=(SoapObject)parse.getProperty(0);
			
		for(int i=0;i<parse.getPropertyCount() && parse.getPropertyCount()>1;i++)
		{
			data=(SoapObject)parse.getProperty(i);
			
			temp=new TrainCode();
			
			if(i!=0)
			{
				temp.setStation(data.getProperty(0).toString());
			}
			else
			{
				String tempStation=data.getProperty(0).toString();
				temp.setStation(tempStation.split("（")[0]);			
			}
			
			Date startDate = null;
			Date endDate=null;
			
			if(i!=0)
			{
				String tempStartString=data.getProperty(1).toString();
				temp.setPlanStart(DateFromString(tempStartString));

				startDate = DoString.ParseDateFromHour(tempStartString);
	
			}
			else
			{
				temp.setPlanStart(null);
			}
			
			if(i!=parse.getPropertyCount()-1)
			{
				String tempEndString=data.getProperty(2).toString();
				temp.setPlanEnd(DateFromString(tempEndString));

				endDate = DoString.ParseDateFromHour(tempEndString);
			}
			else
				temp.setPlanEnd(null);
				
			temp.setLength(Double.valueOf(data.getProperty(3).toString()));
			
			if(startDate!=null && endDate!=null)
			{
				int dTime=(int)(DoString.DateCompare(startDate, endDate));
				temp.setRetain(dTime);
			}	
			result.add(temp);
		}

		return result;
	}
	
	public static Date DateFromString(String dateString)
	{
		return DateFromString(dateString,"hh:mm:ss");	
	}
	
	public static Date DateFromString(String dateString,String format)
	{
		SimpleDateFormat dateFormat=new SimpleDateFormat(format);
		try 
		{
			Date result  = dateFormat.parse(dateString);
			return result;
		} catch (ParseException e) 
		{
			return null;
		}
	}
}
