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
     <script type="text/javascript" src="<%=basePath%>js/integratedquery/integratedquery.js"></script>
     <script type="text/javascript" src="<%=basePath%>js/js/WdatePicker.js"></script>
     
  </head>
  
  <body>
  <form action="testPost.action" method="post" > 
  <input type="hidden" name="info" value="2" /> 
    <input type="submit" value="获取令牌"/> <br />
   </form>
   <div>
    <select id="mentd">
    <option value="1">船舶基本信息</option>
    <option value="2">船舶证书信息</option>
    </select>
    <input type="text" value="" id="parameters_name"/>
     <input type="button" value="显示" onclick="showBaseInfo()"/>
     <table id="Infor">
     </table>
     <a href="servlet/GetAndPostDate">textservlet</a>
  </div>
  </body>
</html>
