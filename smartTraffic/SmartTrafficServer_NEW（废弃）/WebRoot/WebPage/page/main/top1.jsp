<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	

		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/main/top.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/loadingDiv.css" />

		<script type="text/javascript" src="<%=basePath%>WebPage/js/main/top1.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/main/top_common.js"></script>
		<script type="text/javascript" src="WebPage/js/common/cookie.js"></script>

	</head>

	<body>
		<div id="top_container">
		<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="fullbg"></div>
			<div id="top_layout1">
				<div id="top_layout1_content">
					<div id="top_layout1_left">
						<a onClick="SetHome()">设为首页</a>|<a onClick="AddFavorite()">加入收藏</a>|<a id="weather"></a>
					</div>
					<div id="top_layout1_right_nologin">
						<a>登录</a>|<a href="<%=basePath%>WebPage/page/register/register.jsp">注册</a>|
						<a href="<%=basePath%>WebPage/page/suggest/suggest.jsp">意见建议</a>|<a href="<%=basePath%>WebPage/page/help/help.jsp">帮助</a>
					</div>
					<div id="top_layout1_right_login">
						<a>summer<img src="<%=basePath%>WebPage/image/main/top_arrow_down_blue.png"/>
						</a>|<a><img src="<%=basePath%>WebPage/image/main/top_icon_hexingtong.png"/>嘉兴交通禾行通<img 
						 src="<%=basePath%>WebPage/image/main/top_arrow_down_black.png"/></a>|<a href="<%=basePath%>WebPage/page/help/help.jsp">帮助</a>
					</div>
				</div>
			</div>
			<div id="top_layout2">
				<div id="top_layout2_content">
				<img alt="嘉兴智慧交通出行网" src="<%=basePath%>WebPage/image/main/top_title.png" style="float: left" />
				<img title="返回嘉兴交通运输局" src="<%=basePath%>WebPage/image/main/traffice_web_normal.png" 
				onmouseout="TrafficeWebOut(this)" onMouseOver="TrafficeWebOver(this)" id="top_layout2_traffice_web"/>
				</div>
			</div>
		</div>
		<div id="openNew" style="display:none;">
			<iframe src="" id="LoginWindow" width="100%" height="422px" frameborder="0" scrolling="no"></iframe>
		</div>
		<!-- 正在加载中 -->
			<div id="ShowLoadingDiv">
				<div id="img_ShowLoadingDiv"><img src='WebPage/image/main/loading.gif'/></div> 
			</div> 
	</body>
</html>
