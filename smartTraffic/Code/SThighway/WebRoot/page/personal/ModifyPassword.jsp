<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/personal/PersonalData.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="js/system/setheight.js"></script>
	<script type="text/javascript" src="js/common/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/personal/ModifyPassword.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/cookie.js"></script>	
  </head>
  
  <body>
    <input type="hidden" value="<%=basePath%>" id="basePath"/>
    <div class="common_c0" id="allheight">
    	<jsp:include page="../system/top.jsp" flush="true" />
    	<jsp:include page="../system/system_left.jsp" flush="true" >
    		<jsp:param name="menu_number" value="2,3" />
	    </jsp:include>
	    	<div class="common_c1">
		    	<div class="common_c2" id="sss">
		    		<div class="style">
						<table class="personalTable" id="userTable" cellpadding="0" cellspacing="0" style="border-top: solid 1px #cccccc;">
						<col width="10%"/><col width="90%"/>
							<tr>
								<td style="text-align: right">原密码:</td>
								<td><input type="password" value="" id="mm" class="psaaword_input" /></td>
							</tr>
							<tr>
								<td style="text-align: right">新密码:</td>
								<td><input type="password" value="" id="newmm" class="psaaword_input"/></td>
							</tr>
							<tr>
								<td style="text-align: right">确认密码:</td>
								<td><input type="password" value="" id="checkmm" class="psaaword_input"/></td>
							</tr>
							<tr>
								<td style="text-align: right">&nbsp;</td>
								<td><button id="updatePass" class="passChange_button">保存</button></td>
							</tr>
						</table> 
	
					</div>	
				</div>
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
   
   			
  </body>
</html>
