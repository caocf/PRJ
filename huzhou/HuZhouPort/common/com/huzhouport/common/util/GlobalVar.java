package com.huzhouport.common.util;

import java.awt.Color;

public final class GlobalVar {
	public static final int PAGESIZE = 10;
	public static final int PAGESIZEAPP = 15;
	public static final int PHOTOPAGESIZE = 18;

	public static final String FilePATH = "File";
	// 查询条件
	public static final String PAIRCONTAIN = "%%";// 包含

	public static final String PAIRNOCONTAIN = "!%%";// 不包含

	public static final String PAIRSTART = "%_";// 以..开头

	public static final String PAIREND = "%_";// 以..结尾

	public static final String PAIRCONTAINMANY = "in_";// 包含多個


	// 新世纪二期

	public static final String PORTDATABASE_IP = "http://202.107.242.115:10001/";// 请求的IP地址
	//原地址：http://10.100.70.2/，阿里云地址：http://192.168.168.1:10001/

	public static final String PORTDATABASE_LOGIN = "etl/api/authentication/authenticate";// 身份验证的路径

	public static final String PORTDATABASE_LOGIN_USER = "login=apiuser_ydyy&password=ydyy";// 身份验证的路径

	public static final String PORTDATABASE_PATH = "etl/api/generaldata/fetch";// 请求数据的路径

	public static final String PORTDATABASE_SERVICECODE = "serviceCode=";// servicecode

	public static final String PORTDATABASE_SERVICECODE_JB = "CDP_JC_CBJBXX";// 船舶基本信息servicecode

	public static final String PORTDATABASE_SERVICECODE_WZ = "CDP_CF_CBWZCFXX";// 船舶违章信息servicecode

	public static final String PORTDATABASE_SERVICECODE_QZ = "CDP_JG_CBQZXX";// 船舶签证信息servicecode

	public static final String PORTDATABASE_SERVICECODE_JF = "CDP_JZ_CBJFXX";// 船舶缴费信息servicecode

	public static final String PORTDATABASE_SERVICECODE_JY = "CDP_CJ_CBJYXX";// 船舶检验信息servicecode

	public static final String PORTDATABASE_SERVICECODE_CZ = "CDP_ZH_CBCZXX";// 船舶持证信息servicecode

	public static final String PORTDATABASE_SERVICECODE_HYQY = "CDP_XK_YZ_HYQYXX";// 航运企业信息servicecode

	public static final String PORTDATABASE_PAGE = "pageIndex=";// 请求页数

	public static final int HOST_CONNECT_TIME = 10000;// 设置连接主机超时（单位：毫秒）

	public static final int HOST_READ_TIME = 10000;// 设置读取主机超时（单位：毫秒）

	public static final String AWAITING_APPROVAL = "待审批"; // 待审批
	public static final String ISPENDING = "正在审批"; // 正在审批
	public static final String REJECT = "驳回"; // 驳回
	public static final String APPROVED = "通过审批"; // 通过审批

	public static final String[] QUARTER = { "第一季", "第二季", "第三季", "第四季" };
	public static final String[] MONTH = { "1月", "2月", "3月", "4月", "5月", "6月",
			"7月", "8月", "9月", "10月", "11月", "12月" };
	// 颜色
	public static final Color XYLINE = new Color(102, 121, 154);// x.y轴线颜色
	public static final Color CPLINE = new Color(221, 221, 221);// 虚线颜色
	public static final Color CategoryPlot_ONE_COLOR = new Color(71, 129, 226);// 柱体1
	public static final Color CategoryPlot_TWO_COLOR = new Color(136, 200, 78);// 柱体2
	public static final Color CategoryPlot_THREE_COLOR = new Color(245, 93, 170);// 柱体3
	public static final Color CategoryPlot_FOUTH_COLOR = new Color(239, 147, 62);// 柱体4

	public static final String REPORT_ILLEGAL_IMAGE = "Report/illegal.png";// 违章取证图片

	public static final String REPORT_ELECT_IMAGE = "Report/elect.png";// 船舶航行电子报告图片

	public static final String REPORT_FIX_IMAGE = "Report/fix.png";// 定期签证航次报告图片

	public static final String REPORT_LEAVE_IMAGE = "Report/leave.png";// 请假加班图片
	
	public static final String REPORT_DRAFT_IMAGE = "Report/draft.png";// 油耗图片

	public static final String REPORT_ILLEGAL_EXCEL = "Report/违章取证统计表.xls";// 违章取证excel

	public static final String REPORT_ELECT_EXCEL = "Report/船舶航行电子报告统计表.xls";// 船舶航行电子报告excel

	public static final String REPORT_FIX_EXCEL = "Report/定期签证航次报告统计表.xls";// 定期签证航次报告excel

	public static final String REPORT_LEAVE_EXCEL = "Report/考勤管理统计表.xls";// 考勤管理excel
	
	public static final String REPORT_DRAFT_EXCEL = "Report/油耗统计表.xls";// 油耗excel

	public static final int REPORT_WIDTH = 600;// 报表图片宽度

	public static final int REPORT_HEIGHT = 350;// 报表图片高度
	public static final String COLLEGEBASEPATH = "/temp/"; // 数据基本路径
	public static final String EXCELTEMPLETEPATH = "/File/modlExcl/"; // 用于EXCEL数据导出模板文件夹

	public static final int LOGLOGIN = 1;// 登录

	public static final int LOGDELETE = 2;// 删除

	public static final int LOGSAVE = 3;// 添加

	public static final int LOGUPDATE = 4;// 修改

	public static final int LOGEXIT = 5;// 退出

	public static final int LOGSEE = 6;// 查看
	
	public static final int LOGAPP = 1;// app日志
	
	public static final int LOGPC = 0;// pc日志
	
	//权限
	public static final int PERMISSION_LEAVE = 10;// 考勤管理权限

	// Wedbservice

	public static final int HOST_CONNECT_HUZGETDQSHIP_TIME = 120 * 1000;// 设置连接定期签证主机超时（单位：毫秒）

	public static final int HOST_READ_HUZGETDQSHIP_TIME = 120 * 1000;// 设置读取定期签证主机超时（单位：毫秒）

	public static final int HOST_CONNECT_LONG_TIME = 60 * 1000;// 设置链接主机超时（单位：毫秒）

	public static final int HOST_READ_LONG_TIME = 60 * 1000;// 设置读取主机超时（单位：毫秒）
	
	public static final String WEBSERVICE_STATUS="status";//返回的结果界点
	
	public static final String WEBSERVICE_SUCCESS="success";//返回的结果
	
	//综合监管系统
	public static String WEBSERVICE_URL = "http://202.107.242.115:10002/webservice/Webservice?wsdl";
	//原地址：http://10.100.70.44/webservice/Webservice?wsdl，阿里云：http://192.168.168.1:10002/webservice/Webservice?wsdl 
	
	//码头webservice
	public static String WHARFWEBSERVICE_URL = "http://172.21.24.15/PortWebService/WebService.asmx";
	//原地址：http://172.21.24.15/PortWebService/WebService.asmx，阿里云：http://202.107.242.115:10003/PortWebService/WebService.asmx
	public static String WHARFWEBSERVICE_NAMESPACE = "http://172.21.24.15/PortWebService/";
	//原地址：http://172.21.24.15/PortWebService/，阿里云：http://172.21.24.15/PortWebService/
}
