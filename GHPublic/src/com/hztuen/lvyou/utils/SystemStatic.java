package com.hztuen.lvyou.utils;

import com.amap.api.services.core.LatLonPoint;

public class SystemStatic 
{
	public static long userId=0;
	public static String usertypeId = "";
	public static String userName=null;
	
	public static String shipStart=null;
	public static String shipTaget=null;
	public static String shipType=null;
	
	
	public static String goodsStart=null;
	public static String goodsTaget=null;
	public static String goodsType=null;
	
	
	public static String deleteUrl=null;  //删除我的发布记录的接口地址
	public static String sourceid=null;  //删除我的发布记录的Sourceid参数
	public static String sendid=null;  //删除我的发布记录的Id参数
	public static int removepostiion=0;
	
	public static String searchShipName=null;  //搜索船舶船名
	
	public static String phoneNum=null;  //当前账号手机号
	
	public static String Wharfname=null;  //码头名称
	
	public static String recordID=null;  //每项危货申报ID
	
	
	public static int currentDayIndex=0;
	
	public static LatLonPoint lp = null;//定位坐标
	
	public static LatLonPoint llp = null;//随时需要改变的搜索坐标
	
	public static String city = "";//定位获取的城市
	
}
