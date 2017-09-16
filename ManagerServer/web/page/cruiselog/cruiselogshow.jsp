<%@page import="java.util.*"  pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath1%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="css/common/style.css" />
<link rel="stylesheet" type="text/css" href="css/leave/see.css"/>
<script src="js/common/jquery-1.5.2.min.js"></script>
<script src="js/cruiselog/cruiselogshow.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
  </head>
  
  <body>
  <input type="hidden" value="<%=basePath1%>" id="basePath"/>
  <input id="ID" name="ID" type="hidden" value="<%=request.getParameter("ID") %>" />
  <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
  
         <div id="layer1">
				<img src="<%=basePath1%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
</div>
    <table width="100%" border="0" cellspacing="0" class="see"
	   id="CruiseLogshow">
	<col width="100px" /><col />
  
  </table>
  
  
<div id="illegalList">
<div id="approvaldiv" class="seedivapproval"><p>巡航中的事件</p></div>
  <table width="100%" border="0" cellspacing="0" class="see1"
	   id="illegalListshow">
	   <col width="80%" /><col/>
  </table>

</div>  

<div id="allmap" style="width: 780px; border:1px solid #3d9dd7; height: 170px;overflow: hidden;margin:10px;"></div>
  </body>
</html>
