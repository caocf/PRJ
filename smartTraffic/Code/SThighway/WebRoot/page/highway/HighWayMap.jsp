<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公路地图</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>logo2.png" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/User.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/highway/HighWayMap.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/SelectOption.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/common/button.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/main/paging.js"></script>		
	<script type="text/javascript" src="js/highway/Mapbarurl.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/highway/mapheight.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/highway/HighWayMap.js"></script>
	
	
	

  </head>
  
  <body>
    <div class="highway_out" id="allheight">
    <input type="hidden" value="<%=request.getParameter("bridgebm")%>" id="bridgebm"/>
    <input type="hidden" value="<%=request.getParameter("routebm")%>" id="routebm"/>
    	<jsp:include page="../system/top.jsp" flush="true" />
				<div class="highway_left" id="highway_left_select" style="width:199px;">
					<div class="map_mark_div">
						<img  style="cursor: pointer;" onmouseover="mapLDXXOver(this)" onmouseout="mapLDXXOut(this)" 
						src="image/map/ldxx_normal.png" onclick="showhighwayleftinfo(1)" />
						<div class="map_mark_text">路网信息</div>
					</div>
					<div class="map_mark_div">
						<img  style="cursor: pointer;" onmouseover="mapSPJKOver(this)" onmouseout="mapSPJKOut(this)" 
						src="image/map/spjk_normal.png" onclick="showhighwayleftinfo(2)" />
						<div class="map_mark_text">视频监控</div>
					</div>
					<div class="map_mark_div">
						<img  style="cursor: pointer;" onmouseover="mapFWSSOver(this)" onmouseout="mapFWSSOut(this)" 
						src="image/map/fwss_normal.png" onclick="showhighwayleftinfo(3)" />
						<div class="map_mark_text">服务设施</div>
					</div>
					<div class="map_mark_div">
						<img  style="cursor: pointer;" onmouseover="mapQLXXOver(this)" onmouseout="mapQLXXOut(this)" 
						src="image/map/qlxx_normal.png" onclick="showhighwayleftinfo(4)" />
						<div class="map_mark_text">桥梁信息</div>
					</div>
					<div class="map_mark_div">
						<img  style="cursor: pointer;" onmouseover="mapBZBXOver(this)" onmouseout="mapBZBXOut(this)" 
						src="image/map/bzbx_normal.png" onclick="showhighwayleftinfo(5)" />
						<div class="map_mark_text">标志标线</div>
					</div>
					<div class="map_mark_div">
						<img  style="cursor: pointer;" onmouseover="mapGLZOver(this)" onmouseout="mapGLZOut(this)" 
						src="image/map/glz_normal.png" onclick="showhighwayleftinfo(6)" />
						<div class="map_mark_text">公路站</div>
					</div>
					<div class="map_mark_div">
						<img  style="cursor: pointer;" onmouseover="mapGLJGOver(this)" onmouseout="mapGLJGOut(this)" 
						src="image/map/gljg_normal.png" onclick="showhighwayleftinfo(7)" />
						<div class="map_mark_text">管理机构</div>
					</div>
					<div class="map_mark_div">
						<img  style="cursor: pointer;" onmouseover="mapJTLOver(this)" onmouseout="mapJTLOut(this)" 
						src="image/map/jtl_normal.png" onclick="showhighwayleftinfo(8)" />
						<div class="map_mark_text">交通量</div>
					</div>
					
				</div>
				<div class="image_size" onclick="showOrhideleft()" style="left:200px;"><img class="highway_image"  id="arrowleft_" alt="" src="image/map/arrowleft_normal.png" ></div>
	    	<div class="highway_right" id="highway_right" style="margin:0 0 0 201px;">
		    	<!-- <div class="highway_right_top">
		    		<label style="float:left;font-size: 16px;margin:15px 0 0 15px;">嘉兴市</label>
		    		<label style="float:right;font-size: 16px;margin:15px 30px 0 15px;">全屏</label>
		    		<img src="image/main/full_normal.png" onclick="showfullMap()" style="float:right;margin-top:16px;cursor: pointer;" 
		    		onmouseover="fullOver(this)" onmouseout="fullOut(this)">
		    	</div> -->
		    	<div class="highway_right_map" id="highwaymap">
		    		
		    	</div>
	    	</div>
	    <jsp:include page="../main/bottom.jsp" flush="true" />
    </div>
  </body>
</html>
