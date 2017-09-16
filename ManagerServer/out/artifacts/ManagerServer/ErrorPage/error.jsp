<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>error page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>

  <body>
  <div style='width:100%;float:left;vertical-align: middle;display: table-cell;text-align: center;padding-top:100px;'>
    <img alt="" src="image/common/error_picture.png">
    <h3 style='color:#57738c'>很抱歉你访问的页面出错了</h3>
    <div style="width:150px;
			    height:40px;
			    border-radius:4px;
			    background:#3188f2;
			    color:white;
			    text-align: center;
			    line-height: 40px;
			    cursor:pointer;
			    margin:10px auto"
		 onclick="window.location.href='<%=basePath%>'"
	>
    	返回首页
    </div>
  </div> 
  </body>
</html>
