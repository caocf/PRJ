package com.huzhouport.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 格式转换
 * 
 */
public class ChangeType {
	/**
	 * 日期 获取当前时间
	 * 
	 * @return
	 */
	public Date getCurrentTime() {
		Date now = new Date();
		return now;

	}

	public Date getCurrentDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	/**
	 * 转换null为""
	 * 
	 * @param value要转换的值
	 * @return
	 */
	public String IsNull(String value) {
		if (!value.equals("null")) {
			return value;
		}
		return "";
	}

	/**
	 * 今天年-月-日
	 * 
	 * @return
	 */
	public static String YearMonthDay() {
		Date now = new Date();
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(now);

	}

	public static Date yearMonthDayDate() throws ParseException {
		Date now = new Date();
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		String nowString = myFmt.format(now);
		return myFmt.parse(nowString);
	}

	public static Date changeYMDDate(Date value) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		String nowString = myFmt.format(value);
		return myFmt.parse(nowString);

	}

	public static String changeYMD(Date value) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(value);

	}

	public static String changeYMDHMS(Date value) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return myFmt.format(value);
	}

	/**
	 * 取昨天的值 格式：年-月-日
	 * 
	 * @return
	 */
	public static String YesterdayYearMonthDay() {
		Date Yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60
				* 24);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(Yesterday);
	}

	/**
	 * 取明天的值,格式，年-月-日
	 * 
	 * @return
	 */
	public static String TomorrowYearMonthDay() {
		Date Yesterday = new Date(System.currentTimeMillis() + 1000 * 60 * 60
				* 24);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(Yesterday);
	}

	/**
	 * 取指定值的明天,格式，年-月-日
	 * 
	 * @return
	 */
	public static String TomorrowByValue(String value) throws Exception {
		Date Yesterday = new Date(StringToDate(value).getTime() + 1000 * 60
				* 60 * 24);
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		return myFmt.format(Yesterday);
	}

	/**
	 * 字符串转date
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date StringToDate(String value) throws ParseException {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
		// String nowString = myFmt.format(value);
		return myFmt.parse(value);
	}

	

	public static Map<Long, Long> curDateOrder(String startEndTime)
			throws ParseException {
		String startTime = startEndTime.split("-")[0];
		String endTime = startEndTime.split("-")[1];
		Map<Long, Long> map = new HashMap<Long, Long>();
		Date nowDate = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		startTime = ChangeType.YearMonthDay() + " " + startTime;
		String endDate = ChangeType.YearMonthDay() + " " + endTime;
		String yesterday = ChangeType.YesterdayYearMonthDay() + " " + endTime;
		String ymdhms = sdfDate.format(nowDate); // 年月日时分秒（2012-10-16 9:30:00）

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt1 = df.parse(ymdhms);
		Date dt2 = df.parse(startTime);
		Date dt3 = df.parse(endDate);
		Date dt4 = df.parse(yesterday);
		if (dt1.getTime() > dt2.getTime() && dt1.getTime() < dt3.getTime()) {
			map.put(dt4.getTime() / 1000, dt2.getTime() / 1000);
		} else {
			map.put(dt2.getTime() / 1000, dt3.getTime() / 1000);
		}
		return map;
	}

}
