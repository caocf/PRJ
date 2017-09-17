<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String name="";
if(session.getAttribute("username")!=null){
name=session.getAttribute("username").toString();
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	

		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/top.css" />

	</head>

	<body>
		<div id="top_container"> 
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="fullbg"></div>
			<div id="top_layout1">
				<div id="top_layout1_content">
					<div id="top_layout1_right_login">
						<b class="top_logionname">您好！<%=name%></b>
						<a onclick="exit()" class="operation">退出</a>
					</div>
				</div>
			</div>
		</div>
		<!-- 正在加载中 -->
			<div id="ShowLoadingDiv">
				<div id="img_ShowLoadingDiv"><img src='image/loading.gif'/></div> 
			</div> 
	</body>
</html>
