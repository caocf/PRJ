<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/Graphical/parking.js"></script>
	</head>

	<body>
		<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="2,1"/> 
		</jsp:include>
		<div id="main_content3">
		  <div class="layout1">
		   <div class="left">
		      <div class="left_top">
		         <a class="left_top_text1">热点搜索</a>
		         <a class="left_top_text2">公路网查询</a>
		         <a class="left_top_text2" style="margin-left: 30px">航道网查询</a>
		      </div>
		      <div class="left_bottom">
		         <div class="left_bottom_wb">
		         <input type="text" class="input"/><a class="input_rig">搜索</a>
		         </div>
		         <div class="tubiao"><img src="WebPage/image/graphical/select.png" style="float:left;margin:7px auto auto 10px;"/>首页</div>
		         <div class="left_bottom_lb">
		                                             数据区域
		         </div>
		      </div>
		     </div>
		    <div class="layout1_right" style="position: relative">
					  <div  id="simpleMap" class="simpleMap_top"></div>
					  
					  <div id="simpleMap_top">
							<label class="simpleMap_top_location">
								嘉兴市
							</label>
							
							<!-- <div class="simpleMap_top_item">
								<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
									class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
							</div> -->
						</div>				  
					 					
					
		  </div>
		    </div>
		  </div>
 <!-- 页面内容-end -->
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
