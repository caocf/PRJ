package com.huzhouport.common.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CountTime {
	private final static String  SimpleDateFormat1="yyyy-MM-dd HH:mm:ss";
	//private final String  SimpleDateFormat2="yyyy-MM-dd HH:mm";
	 /**
    *
    * TODO:输入一个时间，获取该时间的时间戳
    * @param @param dateString
    * @param @return
    * @param @throws ParseException
    */
   public static long string2Timestamp(String dateString) throws ParseException{
       Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(dateString);
       long temp = date1.getTime();//JAVA的时间戳长度是13位
       return temp;
   }
    
   /**
    *
    * @Description:输入一个时间，获取与当前时间的相差秒数
    * @param @param dateString
    * @param @return
    */
   public static long distanceBetweenCurren(String dateString){
       Date date1 = null;
       try {
           date1 = new SimpleDateFormat(SimpleDateFormat1, Locale.getDefault()).parse(dateString);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       long temp = date1.getTime();//JAVA的时间戳长度是13位
       long current = System.currentTimeMillis();
       return (current-temp)/1000;
        
   }
    
   public static void time2TimeStamp(String timeStamp){
       SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss", Locale.getDefault());
       SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault());
       long unixLong = 0;
       String date = "";
       String date2 = "";
       try{
           unixLong = Long.parseLong(timeStamp)*1000;//时间戳是秒为单位
       }
       catch(Exception ex){
           ex.printStackTrace();
       }
       date = sdf1.format(unixLong);
       date2 = sdf2.format(unixLong);
       System.out.println(date);//输出格式：1973/12/10 08:12:02
       System.out.println(date2);//输出格式：1973-12-10 08:12:02
   }
    
   public static void main(String args[]) throws ParseException{
       String dateString = "2013-09-11 23:16:00";
       String dateString2 = "2013/09/11 23:16:00";

       System.out.println(CountTime.string2Timestamp(dateString));
       System.out.println(CountTime.distanceBetweenCurren(dateString2));
       CountTime.time2TimeStamp("111111122");
        
   }
   //计算闹钟时间
   public static String countClickTime(String date,int rate) throws ParseException{
	   SimpleDateFormat sdf1 = new SimpleDateFormat(SimpleDateFormat1, Locale.getDefault());
       Date date1 = sdf1.parse(date);
       long temp = date1.getTime();
       long clicktime=temp-rate*60*1000;
       String click=sdf1.format(clicktime);
       return click;
   }
 //格式化时间去掉.0
   public static String FormatTime(String date){	 
       String date1 = date.replace(".0", "");
       return date1;
   }
 //格式化时间xxxx年xx月xx日
   public static String FormatTimeToDay(String date){
	   if(date.length()!=0){
		   String[] date1 = date.split(" "); 
		   if(date1.length>0)
		     date=date1[0];
	   }
       return date;
   }
   //时间格式化x->xx
   public static String FormatTime2(String timeStamp){
	   if(timeStamp.trim().length()==0) return "";
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
       Date date1 = null;
	try
	{
		date1 = sdf.parse(timeStamp);
	}
	catch (ParseException e)
	{
		e.printStackTrace();
	}
       long temp = date1.getTime();
       String click=sdf.format(temp);
      return click;
   }
}
