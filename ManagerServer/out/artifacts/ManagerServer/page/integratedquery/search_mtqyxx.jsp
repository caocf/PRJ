<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath%>">
    
    <title>My JSP 'datapost.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    
	
	<script src="<%=basePath%>js/common/jquery-1.5.2.min.js"></script>
     <script type="text/javascript" src="<%=basePath%>js/data/data.js"></script>
     <script type="text/javascript" src="<%=basePath%>js/js/WdatePicker.js"></script>
     <script type="text/javascript" src="js/common/CheckLogin.js"></script>
     
  </head>
  
  <body>
 <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
 <input type="hidden" value="<%=basePath%>" id="basePath" />
  </body>
</html>
