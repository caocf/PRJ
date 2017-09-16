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
<script src="js/LeaveAndOvertime/LeaveAndOvertimeApproval.js"></script>
<script type="text/javascript" src="js/common/Operation.js"></script>
<script type="text/javascript" src="js/common/CheckLogin.js"></script>
<script type="text/javascript" src="js/common/formatConversion.js"></script>
  </head>
  
  <body>
  <input type="hidden" value="<%=basePath1%>" id="basePath"/>
  <input id="leaveOrOtID" name="leaveOrOtID" type="hidden" value="<%=request.getParameter("leaveOrOtID") %>" />
   <input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
   
   <div id="layer1">
				<img src="<%=basePath1%>image/operation/back_small_normal.png"
						onclick="javascript:window.history.go(-1);"
						onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
						title="返回" />
			</div> 
    
    <table width="100%" border="1" cellspacing="0" class="see" 
	   id="LeaveAndOvertimeApproval">
	<col width="130px" /><col />
    </table>
 
 </body>
</html>
