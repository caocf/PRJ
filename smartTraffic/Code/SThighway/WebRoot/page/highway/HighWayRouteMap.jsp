<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>路网详情</title>
    
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
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="js/highway/Mapbarurl.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/highway/HighWayRouteMap.js"></script>
	
	
	

  </head>
  
  <body>
    <div class="highway_out" id="allheight">
    <input type="hidden" value="<%=request.getParameter("routebm")%>" id="routebm" />
    <input type="hidden" value="<%=request.getParameter("amount")%>" id="amount"/>
    	<jsp:include page="../system/top.jsp" flush="true" />
    			<div class="highway_left" id="highway_left_info">
					<div class="highway_left_top">
						<div class="c3_button1" onclick="gobackToSelectPage()" style="margin:9px 15px 0 15px;">
							<div class="c3_button1_left" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
							<div class="c3_button1_center" onmouseover="buttonOver()" onmouseout="buttonOut()">返回</div>
							<div class="c3_button1_right" onmouseover="buttonOver()" onmouseout="buttonOut()" ></div>
						</div>
						<div style="height:49px;line-height: 49px;vertical-align: middle;float:right;margin-right:15px;">当前地图找到<label style="color:blue;" id="number"></label>条结果</div>
					</div>
					<!-- <div class="highway_route_top">
						<input type="checkbox" value="" name="routebox" class="checkbox_type" style="margin-left:30px;" onclick="showThisTypeRoute()" /><label class="checkbox_text">&nbsp;国道</label>
						<input type="checkbox" value="" name="routebox" class="checkbox_type" onclick="showThisTypeRoute()" /><label class="checkbox_text">&nbsp;省道</label>
						<input type="checkbox" value="" name="routebox" class="checkbox_type" onclick="showThisTypeRoute()" /><label class="checkbox_text">&nbsp;县道</label>
						<input type="checkbox" value="" name="routebox" class="checkbox_type" onclick="showThisTypeRoute()" /><label class="checkbox_text">&nbsp;乡道</label>
					</div> -->
					<div class="search_div">
						<input value='路线代码、路线简称' id='search_info' class="search_input" 
							onfocus="TextFocus(this)" onblur="TextBlur(this)" />
						<div class="image_div" onmousemove="imageOver()" onmouseout="imageOut()" onclick="showRouteInfo('lxjbxxlist/lxgklist',1)">
								<img src="image/main/search_normal.png" style="margin-top:9px;" id="search_image"></div>
					</div>
					<div class="highway_left_middle">
						
						<!-- <div class="highway_route_info"><label class='highway_route_name'>G15高速</label></div>
						<div class="highway_route_info_detail">
							<div class="highway_route_info_detail_top"><label class="detail_top_left">G15高速</label><label class="detail_top_right">详情</label></div>
							<div class="higgway_route_info_detail_body">
								<span class="detail_body_desc">起点：</span>
								<span class="detail_body_desc">终点：</span>
								<span class="detail_body_desc">里程：</span>
								<span class="detail_body_desc">设计时速：</span>
							</div>
						</div> -->
					</div>
					<div class="highway_left_bottom">
						<div class="User_S4" id="pageDiv" style="background:white;display:none;">
							<p>
								共<span id="allTotal"></span>条
								<span class="firstBtnSpan"></span>
								<span class="prevBtnSpan"></span>
								<span class="pageNoSpan"></span>页
								<span class="nextBtnSpan"></span>
								<span class="lastBtnSpan"></span>
								<span class="gotoPageSpan"></span>
							</p>
						</div>
					</div>
				</div>
				<div class="image_size" onclick="showOrhideleft()"><img class="highway_image" id="arrowleft_" alt="" src="image/map/arrowleft_normal.png" ></div>
	    	<div class="highway_right" id="highway_right">
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
