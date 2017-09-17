package com.SmartTraffic.common;

public class Constants {

	/**
	 * 交调数据查询公网地址
	 */
     public static String TRANSPORTDATA_PUBLIC_URL = "http://218.108.13.69:8888";
	
	/**
	 * 交调数据查询交通业务网接口
	 */
     public static String TRANSPORTDATA_INNER_URL ="http://10.100.20.40:8888/jd";
     
     /**
      * 交通量报表数据查询接口
      */
     public static String TRANSPORTDATA_ACTION_NEWREPORT ="/action/newreport!getSJQBJTLReport.action";
     
     /**
      * 交通量报表数据接口参数2：xzqbs="33040020110903140636263000261480"
      */
     public static String TRANSPORTDATA_ACTION_NEWREPORT_PARAM2="33040020110903140636263000261480";
     
     /**
      * 交调--实时拥挤度查询
      */
     public static String TRANSPORTDATA_ACTION_TIMECS ="/sum/test!getTimeCSByXzqhbs.action";
     
     /**
      * 交调--实时拥挤度参数1名字
      */
     public static String TRANSPORTDATA_ACTION_TIMECS_PARAM1_NAME ="xzqhbs";
     
     /**
      * 交调--实时拥挤度参数1值
      */
     public static String TRANSPORTDATA_ACTION_TIMECS_PARAM1_VALUE ="33040020110903140636263000261480";
     
     
     /**
      * 桩号API 连线返回结果
      */
     public static int KSTAKELINE_NULL_CODE = 300004;//没有连线结果
     public static int KSTAKELINE_EXITE_CODE = 300000;//成功
     public static int KSTAKELINE_NAMENULL_CODE = 300001;//k桩号为空
     public static int KSTAKELINE_COUNTLESS_CODE = 300002;//查询个数小于取值范围
     public static int KSTAKELINE_FAR_CODE = 300003;//经度点离桩号太远
     public static int KSTAKELINE_EXCEPTION_CODE = 300005;//结果异常
     public static int KSTAKELINE_FORMATERROR_CODE = 300006;//k桩号格式错误
     
     
     /**
      * 出租车API查询结果
      */
     public static int TAXI_RESULTNULL_CODE = 500000; //没有出租车信息
     
}
