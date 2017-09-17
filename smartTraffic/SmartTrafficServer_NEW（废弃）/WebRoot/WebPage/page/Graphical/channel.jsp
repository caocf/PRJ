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

		<title>智慧出行-热点查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/graphical/pot.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/graphical/parking.css" />
		
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/Graphical/channel.js"></script>
	</head>

	<body>
		<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="2,1"/> 
		</jsp:include>
		<!-- 页面内容 -->
		<div id="main_content3">
		  <div class="layout1">
		   <div class="left">
		      <div class="left_top">
		         <a href="WebPage/page/Graphical/pot.jsp" class="left_top_text2">热点搜索</a>
		         <a href="WebPage/page/Graphical/road.jsp" class="left_top_text2">公路网查询</a>
		         <a href="WebPage/page/Graphical/channel.jsp" class="left_top_text1" >航道网查询</a>
		      </div>
		       <div class="left_bottom">
		         <div class="left_bottom_wb" style="height: 48px">
		         <div class="left_bottom_wb_nav">
		             <a class="left_bottom_wb_nav1" onClick="SelectItem_top(this,'WebPage/page/Graphical/channel_cha.jsp')">航道</a>
		             <a class="left_bottom_wb_nav2" onClick="SelectItem_top(this,'WebPage/page/Graphical/channel_ser.jsp')">服务区</a>
		             <a class="left_bottom_wb_nav2" onClick="SelectItem_top(this,'WebPage/page/Graphical/channel_stp.jsp')">锚泊区</a>
		         </div>
		         </div>
		         <div id="">
		         <div class="xinbg">
		         <input type="text" class="input" style="margin-top: 0px" value="请输入查询的航道信息"  onBlur="TextBlur(this)" onFocus="TextFocus(this)"/>
		         <!--  <a class="input_rig" style="margin-top: 0px">搜索</a>-->
		         <img src="WebPage/image/search_normal.png"  onclick="searchpoibycatByName()"
						  onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" id="serarch_img" style="margin: 1px 0 0 12px;cursor:pointer"/>
		         </div>
		         <div class="tubiao"><img src="WebPage/image/graphical/select.png" style="float:left;margin:7px auto auto 10px;"/>首页</div>
		          <div class="left_bottom_lb">
		          <!--  <iframe class="down" src="WebPage/page/Graphical/channel_cha.jsp" width="1000px" height="100%" scrolling="no" frameborder="0"></iframe> -->
		          <!-- 数据内容 -->  
		          </div>
		          </div>
		       </div>
		    </div>
          <div class="layout1_right" style="position: relative">
						<div id="simpleMap" class="simpleMap_top"></div>
							<div id="simpleMap_top">
						<label class="simpleMap_top_location">
							嘉兴市
						</label>
					
						<div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div>
					</div>

							
						</div>
					
	   </div>	  
	</div>
		<!-- 页面内容-end -->
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>