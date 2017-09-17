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
  
  <body>
   <h4 style="color: rgb(92,92,92);">嘉兴客运中心
   <!--  <span style="float: right;font-size: 12px">
   <a  class="main_left_select" onclick="SelectItem_main_left(this,'WebPage/page/LongTansport/ThePassengerTransportationCenter_JX.jsp')">车站简介&nbsp;|</a>换乘引导&nbsp;|车站交通</span> -->
   </h4>
   <hr style="color: rgb(192,202,229);">
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
        汽车客运中心位于我市经济开发区西南新区内，西北至320国道，西南至万国路，东南至横一路，东北至空地处。按一级客运站标准设计，设计年度平均日旅客发送量2.2万人，总建筑面积约3.7万平方米，总用地面积约8.3万平方米，工程总投资约2.7亿元。新建成的客运中心预设售票窗口21个，检票窗口31个，并配备完善的餐饮、住宿、商场、地下社会停车场等相关设施，规划了完善的公共交通接驳系统，共有十条公交线路与之配套，市区大部分地区市民都可以坐公交快捷地前往新车站。</p>
    <p align="center"><img style="width: 530px;" alt="" src="WebPage/image/LongTansport/ThePassengerTransportationCenter_JX.png"/></p>
  </body>
</html>
