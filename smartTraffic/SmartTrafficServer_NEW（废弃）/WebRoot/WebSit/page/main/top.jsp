<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>


		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebSit/css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebSit/css/main/top.css" />
		<link rel="stylesheet" type="text/css" href="WebSit/css/common/loadingDiv.css" />

	
		<script type="text/javascript" src="<%=basePath%>WebSit/js/main/top.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/main/top_common.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/common/cookie.js"></script>
	</head>

	<body>
		<div id="top_container">
			<input type="hidden"
				value="<%=session.getAttribute("user_session_id")%>" id="LoginUser" />
			<input type="hidden" value="<%=basePath%>" id="basePath" />
			<input type="hidden" value="<%=request.getParameter("menu_number")%>"
				id="menu_number" />
			<div id="fullbg"></div>
			<div id="top_layout1">
				<div id="top_layout1_content">
					<div id="top_layout1_left">
						<a onclick="SetHome()">设为首页</a>|
						<a onClick="AddFavorite()">加入收藏</a>|
						<a id="weather"></a>
					</div>

				
					
				</div>
			</div>
			<div id="top_layout2">
				<div id="top_layout2_content">
					<img alt="嘉兴智慧交通出行网"
						src="<%=basePath%>WebSit/image/main/top_title.png" style="float: left"/>
					<img title="返回嘉兴交通运输局"
						src="<%=basePath%>WebSit/image/main/traffice_web_normal.png"
						onClick="OpenTrafficMain()" onmouseout="TrafficeWebOut(this)"
						onMouseOver="TrafficeWebOver(this)" id="top_layout2_traffice_web" />
				</div>
			</div>
			<div id="top_layout3">
				<div id="main_menu">
					<ul id="top_menu">
						<li>
							<a onClick="CreatePage1()" class="m_1" title="公交">公交</a>
						</li>
						<li>
							<a onClick="CreatePage2()" class="m_2" title="公共自行车">公共自行车</a>
						</li>
						<li>
							<a onClick="CreatePage3()" class="m_3" title="长途客运">长途客运</a>
						</li>
						<li>
							<a onClick="CreatePage4()" class="m_4" title="民航">民航</a>
						</li>
						<li>
							<a onClick="CreatePage5()" class="m_5" title="铁路">铁路</a>
						</li>
						<li>
							<a onClick="CreatePage6()" class="m_6" title="自驾出行">自驾出行</a>
						</li>
						<li>
							<a onClick="CreatePage7()" class="m_7" title="实时路况">实时路况</a>
						</li>
						<li>
							<a onClick="CreatePage8()" class="m_8" title="出租车">出租车</a>
						</li>
						<li>
							<a onClick="CreatePage9()" class="m_9" title="交通常识">交通常识</a>
						</li>
						<!--<li>
							<a onClick="CreatePage7()" class="m_7" title="停车诱导">停车诱导</a>
						</li>
					--></ul>
				</div>
			</div>
		</div>
		<!-- 正在加载中 -->
			<div id="ShowLoadingDiv">
				<div id="img_ShowLoadingDiv"><img src='WebSit/image/main/loading.gif'/></div> 
			</div> 
	</body>
</html>
