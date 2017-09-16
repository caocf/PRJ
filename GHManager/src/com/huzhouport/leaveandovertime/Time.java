package com.huzhouport.leaveandovertime;

import java.util.Calendar;
import java.util.Date;

public class Time 
{
	public Calendar getBeginTime(Date date)
	{
		Calendar calendar=null;
		try {
			
			calendar=Calendar.getInstance();
			calendar.setTime(date);
			
			if(calendar.get(calendar.MINUTE)>30)
			{
				calendar.set(calendar.MINUTE, 0);
				calendar.add(calendar.HOUR_OF_DAY, 1);
			}
			
			
			if(calendar.get(calendar.HOUR_OF_DAY)<9)
			{
				calendar.set(calendar.HOUR_OF_DAY,9);
				calendar.set(calendar.MINUTE, 0);
			}
			else if(calendar.get(calendar.HOUR_OF_DAY)>17)
			{
				calendar.add(calendar.DAY_OF_YEAR,1);
				calendar.set(calendar.HOUR_OF_DAY,9);
				calendar.set(calendar.MINUTE, 0);
			}
			
			if(calendar.get(calendar.DAY_OF_WEEK)==7)//周六
			{
				calendar.add(calendar.DAY_OF_WEEK, 2);//加2天
				calendar.set(calendar.HOUR_OF_DAY,9);
			}else if(calendar.get(calendar.DAY_OF_WEEK)==1)//周日
			{
				calendar.add(calendar.DAY_OF_WEEK, 1);//加1天
				calendar.set(calendar.HOUR_OF_DAY,9);
			}			
			
			
			
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}
	public Calendar getEndTime(Date date)
	{
		Calendar calendar=null;
		try {
			
			calendar=Calendar.getInstance();
			calendar.setTime(date);
			
			if(calendar.get(calendar.MINUTE)>30)
			{
				calendar.set(calendar.MINUTE, 0);
				calendar.add(calendar.HOUR_OF_DAY, 1);
			}
			
			if(calendar.get(calendar.HOUR_OF_DAY)<9)
			{
				calendar.add(calendar.DAY_OF_YEAR,-1);
				calendar.set(calendar.HOUR_OF_DAY,17);
				calendar.set(calendar.MINUTE, 0);
			}
			else if(calendar.get(calendar.HOUR_OF_DAY)>17)
			{
				calendar.set(calendar.HOUR_OF_DAY,17);
				calendar.set(calendar.MINUTE, 0);
			}
			
			if(calendar.get(calendar.DAY_OF_WEEK)==7)//周六
			{
				calendar.add(calendar.DAY_OF_WEEK, -1);//减1天
				calendar.set(calendar.HOUR_OF_DAY,17);
			}else if(calendar.get(calendar.DAY_OF_WEEK)==1)//周日
			{
				calendar.add(calendar.DAY_OF_WEEK, -2);//减2天
				calendar.set(calendar.HOUR_OF_DAY,17);
			}		
			
			
			
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}
	
	public int getPerid(Calendar calendarbegin,Calendar calendarend)
	{
		if(calendarbegin.get(calendarbegin.DAY_OF_YEAR)==calendarend.get(calendarbegin.DAY_OF_YEAR))//同一天
		{
			return calendarend.get(calendarbegin.HOUR_OF_DAY)-calendarbegin.get(calendarbegin.HOUR_OF_DAY);
		}
		if(calendarbegin.get(calendarbegin.WEEK_OF_YEAR)==calendarend.get(calendarbegin.WEEK_OF_YEAR))//同一周
		{
			int days=calendarend.get(calendarbegin.DAY_OF_WEEK)-calendarbegin.get(calendarbegin.DAY_OF_WEEK);
			int hours=calendarend.get(calendarbegin.HOUR_OF_DAY)-calendarbegin.get(calendarbegin.HOUR_OF_DAY);
			return days*8+hours;
		}
		if(true)//隔周
		{
			int weeks=calendarend.get(calendarbegin.WEEK_OF_YEAR)-calendarbegin.get(calendarbegin.WEEK_OF_YEAR);
			int days=calendarend.get(calendarbegin.DAY_OF_YEAR)-calendarbegin.get(calendarbegin.DAY_OF_YEAR);
			int hours=calendarend.get(calendarbegin.HOUR_OF_DAY)-calendarbegin.get(calendarbegin.HOUR_OF_DAY);
			return (days-weeks*2)*8+hours;
		}
		
		return 0;
	}
}
