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
   <h4 style="color: rgb(92,92,92);">海宁客运概况</h4>
   <hr style="color: rgb(192,202,229);">
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
    海宁客运中心占地120.655亩，建筑面积13152平方米，其中营运业务用房10504平方米，包括候车室、办公楼、综合楼等配套设施。此外，海宁客运中心还设立了1610平方米的一个汽车维修厂、140平方米的加油站、899平方米的长廊和2105平方米的半地下寝室。为方便旅客出行，旅客不出站就可从换乘长廊自由地选择需要换乘的交通工具，而在售票窗口新增的动态售票显示屏上，哪个班次还剩几张票旅客一清二楚。   
   </p>
   <h4 style="color: rgb(92,92,92);">海宁分布站简图</h4>
   <p align="center"><img style="width: 500px;" alt="" src="WebPage/image/LongTansport/PassengerSurvey_HN.png"/></p>
  </body>
</html>
