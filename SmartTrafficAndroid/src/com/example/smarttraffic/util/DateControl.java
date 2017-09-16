package com.example.smarttraffic.util;

import java.util.Calendar;

/**
 * 关于日期的操作函数
 * @author Administrator
 *
 */
public class DateControl
{
	/**
	 * 获取日期的具体时间内容
	 * @param c 日期
	 * @param i 选项1:年2:月3:日4:时5：分
	 * @return 具体时间内容
	 */
	public static int GetDate(Calendar c,int i)
	{
		int result=-1;
		switch (i)
		{
			case 1:
				result= c.get(Calendar.YEAR);
				break;

			case 2:
				result= c.get(Calendar.MONTH)+1;
				break;
				
			case 3:
				result= c.get(Calendar.DAY_OF_MONTH);
				break;
				
			case 4:
				result= c.get(Calendar.HOUR_OF_DAY);
				break;
				
			case 5:
				result= c.get(Calendar.MINUTE);
				break;

		}
		
		return result;
	}
}
