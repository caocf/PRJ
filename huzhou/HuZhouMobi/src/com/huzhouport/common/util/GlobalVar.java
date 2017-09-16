package com.huzhouport.common.util;

public class GlobalVar {
	public static final String FILE_SERVER_PATH="File/";//手机内存卡保存地址
	
	public static final String FILE_DOWNLOAD_PATH="download/";//手机内存卡保存地址
	
	public static final int SERVICE_TIME=5 * 60 * 60 * 1000;//5小时
	
	public static final String FILE_DOWNLOAD_SUCCESS="下载成功,下载到内存卡的download文件夹中";//手机内存卡保存地址
	
	public static final int RECODER_ICON=30;//视屏录制结束标志
	
	public static final int MAP_LOCATION_TIME=30*1000;//地图定位相隔时间
	public static final int MAP_LOCATIONSIGN_TIME=15*1000;//地图定位相隔时间
	public static final int Cruiselog_Time=10*1000;
	
	public static final int PORT_RANG=100;//码头范围
	
	public static final int LOGLOGIN = 1;// 登录

	public static final int LOGDELETE = 2;// 删除

	public static final int LOGSAVE = 3;// 添加

	public static final int LOGUPDATE = 4;// 修改

	public static final int LOGEXIT = 5;// 退出

	public static final int LOGSEE = 6;// 查看
	
	public static final int LOGCHECK=7;

	public static  int NUMBEROFUNREADSCHEDULE = 0;// 未读日程安排数量
	
	public static  long PUSHTIMER = 1;//单位分钟，默认1分钟

	public static String PUSHMSGBROADCAST = "com.huzhouport.pushmsg.broadcast";
	
	
	public static boolean  isexceptionexit=false; //timeService 是否异常退出 ：false异常退出
}
