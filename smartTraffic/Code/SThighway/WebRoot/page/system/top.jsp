<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'top.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/common/style.css">
	<link rel="stylesheet" type="text/css" href="css/common/loadingDiv.css">
	<link rel="stylesheet" type="text/css" href="css/system/top.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/ajaxfileupload.js"></script>
	<script type="text/javascript" src="js/common/cookie.js"></script>
	<script type="text/javascript" src="js/main/top.js"></script>
  </head>
  
  <body>
   <!-- token -->
    <input type="hidden" value="<%=session.getAttribute("token")%>" id="roadtoken"/>
    <div id="fullbg"></div>
    <input type="hidden" value="<%=basePath%>" id="basePath"/>
	<div class="top_u1">
		<div>
			<!-- <img class="top_u2" src="image/main/back_normal.png" onmouseover="hoverOver(this)" 
				onmouseout="hoverOut(this)"/> -->
			<span class="top_text" id="top_text" onclick="showLinkDiv()"></span>
		</div>
	</div>
	<div class="navigation" style="display:none;">
		<ul class="navigation_ul" >
			<li onclick="goToHome()"><img src="image/main/top_home.png" class="top_link_image"><span
				class="top_link_image">返回首页</span></li>
			<li onclick="goToLDXX()"><img src="image/top/ldxx.png"
				class="top_link_image"><span class="top_link_image">路网信息</span></li>
			<li><img src="image/top/spjk.png" class="top_link_image"><span
				class="top_link_image">视频监控</span></li>
			<li onclick="goToQLXX()"><img src="image/top/qlxx.png"
				class="top_link_image"><span class="top_link_image">桥梁信息</span></li>
			<li onclick="goToFWSS()"><img src="image/top/fwss.png" class="top_link_image"><span
				class="top_link_image">服务设施</span></li>
			<li onclick="goToBZBX()" ><img src="image/top/bzbx.png" class="top_link_image"><span
				class="top_link_image">标志标线</span></li>
			<li onclick="goToTZXX()"><img src="image/top/tzxx.png"
				class="top_link_image"><span class="top_link_image">通阻信息</span></li>
			<li onclick="goToGLZ()"><img src="image/top/glz.png" class="top_link_image"><span
				class="top_link_image">公路站</span></li>
			<li><img src="image/top/jtl.png" class="top_link_image"><span
				class="top_link_image">交通量</span></li>
			<li><img src="image/top/tjfx.png" class="top_link_image"><span
				class="top_link_image">统计分析</span></li>
			<li><img src="image/top/sjtb.png" class="top_link_image"><span
				class="top_link_image">数据同步</span></li>
			<li onclick="goToGLJG()"><img src="image/top/gljg.png"
				class="top_link_image"><span class="top_link_image">管理机构</span></li>
			<li onclick="goToGLDT()"><img src="image/top/gldt.png" class="top_link_image"><span
				class="top_link_image">公路地图</span></li>
		</ul>
	</div>
	<div class="loadingDiv">
		<img alt="" src="image/common/loading.gif">
	</div>
</body>
</html>
