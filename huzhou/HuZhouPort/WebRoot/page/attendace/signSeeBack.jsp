<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>查看详情</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/common/sigin.css">
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/law_see.css" />
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js">
</script>

		<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4">
</script>
<script type="text/javascript" src="js/attendace/signSeeBack.js">
</script>
<script type="text/javascript" src="js/common/CheckLogin.js">
</script>
	</head>
	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<div id="see_container">
			<div id="layer1">
				<img src="<%=basePath%>image/operation/back_small_normal.png"
					onclick="javascript:history.back(-1);" onMouseOver="BackSmallOver(this)" 
					onMouseOut="BackSmallOut(this)" title="返回" />
			</div>	<input type="hidden" value="<%=request.getParameter("signLocation") %>" id="signLocation"/>
		<div id="allmap"
			style="width: 800px; height: 300px; overflow: hidden; margin: margin: 54px 10px 10px 10px; ">
			</div>
			<div style="width: 840px;position: static;float: none;clear: both">
			<table cellspacing="5" class='listTable1' >
		
		<tr>
				<td>
				
					<input id="signID" type="hidden" name="signID" value="<%=request.getParameter("signID") %>"/>
				</td>
				
			</tr>
			<tr>
				<td >
					签退人：
				</td>
				<td>
					<input id="signUser" type="hidden" name="signUser" />
					<input id="signUserName" readonly="readonly" name="signUserName"  style="border: 0"/>
				</td>
				
			</tr>
			<tr>
				<td>
					签退时间：
				</td>
				<td>
					<input type="text" readonly="readonly" id="signTime" name="signTime" style="border: 0" />
				</td>
				
			</tr>
			<tr>
				<td>
					签退地点：
				</td>
				<td id="signLocationName">
				
				</td>
			</tr>
		</table>
			</div>
			</div>
			
	</body>
		
</html>
