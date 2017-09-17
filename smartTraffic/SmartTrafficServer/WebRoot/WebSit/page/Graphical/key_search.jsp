<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("UTF-8");
	String KeyPoint = null;
	if (request.getParameter("KeyPoint") != null) {
		KeyPoint = new String(request.getParameter("KeyPoint").getBytes(
				"ISO-8859-1"), "UTF-8");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-汽车网点查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		
		<link rel="stylesheet" type="text/css" href="WebSit/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebSit/css/graphical/parking.css" />
		
		<script type="text/javascript" src="<%=basePath%>WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebSit/js/common/paging.js"></script>
		<script type="text/javascript" src="WebSit/js/common/formatConversion.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/Graphical/key_search.js"></script>
		<script type="text/javascript" src="WebSit/js/common/div_left_handle.js"></script>
		
	</head>

	<body><input type="hidden" value="<%=KeyPoint%>" id="sc_Point"/>
		<div id="page_content">
	<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="1,1"/> 
		</jsp:include>
		<!-- 页面内容 -->
			<div id="main_content3">
				<div class="layout1">
					<div class="layout1_left">
							<div class="layout1_left_search">
								<input type="text" class="input_text5" value="请输入关键字"
									onBlur="TextBlur(this)" onFocus="TextFocus(this)"
									id="searchContent" />
								<img src="WebSit/image/firstpage/search_normal.png"  onclick="searchpoibykey('searchpoibykey',1)"
						  			onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" id="serarch_img"/>
							</div>
							
						<div class="layout1_left_datalist_title">
							<img src="WebSit/image/graphical/select.png" style="float: left" />
							<span style="float: left;">搜索结果</span>
							<span style="float: right;">共<label
									id="searchReasultCount"></label>条搜索结果</span>
						</div>
							
							<div class="layout1_left_datalist"></div>
							<div id="pageDiv">
								<p>
									<span class="prevBtnSpan"></span>
									<span class="pageNoSpan"></span>
									<span class="nextBtnSpan"></span>
									<span class="gotoPageSpan"></span>
								</p>
							</div>
						</div>
						<div id="div_left_close" onclick="toggleFooter()"></div>
						<div id="div_left_open" onclick="toggleFooter()"></div>
					<div class="layout1_right">
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
		<jsp:include page="../../../WebSit/page/main/foot.jsp" />
		</div>
	</body>
</html>
