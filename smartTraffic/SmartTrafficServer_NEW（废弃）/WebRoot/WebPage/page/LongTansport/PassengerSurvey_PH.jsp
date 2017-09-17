<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
  
  <body >
   <h4 style="color: rgb(92,92,92);">平湖客运概况</h4>
   <hr style="color: rgb(192,202,229);">
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
    平湖汽车站是深圳市龙岗区平湖汽车站的简称，具有占地面积 6800平方木的 停车场以及具有建筑面积 2300㎡的车站建筑。具有班线数 57条。 接纳进站班车数 194， 10个发车位数 。年客运发送量 350000人次 高峰期间旅客聚集人数 3000人次的高登记汽车站。   
   </p>
   <h4 style="color: rgb(92,92,92);">平湖分布站简图</h4>
   <p align="center"><img style="width: 500px;" alt="" src="WebPage/image/LongTansport/PassengerSurvey_PH.png"/></p>
  </body>
</html>
