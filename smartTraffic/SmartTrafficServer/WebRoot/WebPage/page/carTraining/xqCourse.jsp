<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-驾校详</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/training.css" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/carTraining/menu.css" />
	<link rel="stylesheet" type="text/css" href="WebPage/css/common/CRselectBox.css" />
	
	<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/carTraining.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/nav.js"></script>
	<script type="text/javascript" src="WebPage/js/carTraining/jquery-1.9.1.min.js"></script>
	
  </head>
  <body>
	              <div class="xqCont1_top3_title" style="font-size: 12px;margin-left: -8px">
	                 <p style="margin-left: 15px">课程</p>
	                 <p>训练车型</p>
	                 <p style="margin-left: 40px">训练时间</p>
	                 <p style="margin-left: 22px">价格</p>
	              </div>
	              <div class="xqCont1_top3_title" style="background-color:#ffffff;margin-top: 0px;border-top: none;font-size: 12px;margin-left: -8px">
	                 <p style="margin-left: 15px;font-weight: normal;">C1照</p>
	                 <p style="font-weight: normal;">普桑</p>
	                 <p style="margin-left: 40px;font-weight: normal;">周一至周日</p>
	                 <p style="margin-left: 22px;font-weight: normal;">￥4380</p>
	              </div>
  </body>
</html>