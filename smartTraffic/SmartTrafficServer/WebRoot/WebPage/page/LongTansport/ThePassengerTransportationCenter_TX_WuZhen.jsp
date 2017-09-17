<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-欢迎进入行业基本信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/publicTraffic/PassengerSuvey.css"/>

  </head>
  
  <body style="margin: 0;">
   <h4 style="color: rgb(92,92,92);">桐乡乌镇汽车站</h4>
   <hr style="color: rgb(192,202,229);">
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
       乌镇汽车站占地面积16亩，建筑面积1500多平方米！投资近700万元的乌镇汽车新站经过9个月的建设正式投入运行。据了解，乌镇新车站是按三级汽车站标准建造，日发班次约180多班，日发送旅客量在2200人左右。
   </p>
    <p align="center"><img style="width: 560px; height: 224px" alt="" src="WebPage/image/LongTansport/ThePassengerTransportationCenter_TX_WuZhen.png"/></p>
  </body>
</html>
