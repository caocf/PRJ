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

		<title>智慧出行-停车诱导</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebSit/css/graphical/parking.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebSit/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebSit/css/common/CRselectBox.css" />
		
		<script type="text/javascript" src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
			<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebSit/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="WebSit/js/common/paging.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebSit/js/Graphical/parking.js"></script>
		<script type="text/javascript" src="WebSit/js/common/div_left_handle.js"></script>
	</head>

	<body>
		<div id="page_content">
		<jsp:include page="../../../WebSit/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="6"/> 
		</jsp:include>
		<!-- 页面内容 -->
				<div id="main_content2">
				<div class="layout1">
					<div class="layout1_left" >
						<table cellpadding="0" cellspacing="0" style="margin-left: 10px;">
							<tr>
								<td>
									<input style="margin-left: 5px" type="text" class="input_text3" value="请输入停车场名称" onBlur="TextBlur(this)" onFocus="TextFocus(this)" id="searchContent" />
								</td>
								<td rowspan="2">
									<img style="margin-left: 10px" src="WebSit/image/firstpage/search_normal.png" onMouseOut="SearchOut(this)" onMouseOver="SearchOver(this)"  onclick="GetParking('queryParkingFromCGByNameAndType', 1)" />
								</td>
							</tr>
							<tr>
								<td>
									<div class="CRselectBox" id="parkType">
										<input type="hidden" value="0" class="abc" />
										<input type="hidden" value="停车场类型" class="abc_CRtext" />
										<a class="CRselectValue">停车场类型</a>
										<ul class="CRselectBoxOptions">
											<li class="CRselectBoxItem">
												<a  class="selected" rel="0">停车场类型</a>
											</li>
											<li class="CRselectBoxItem">
												<a  rel="1">路测</a>
											</li>
											<li class="CRselectBoxItem">
												<a  rel="2">封闭式</a>
											</li>
										</ul>
									</div>
								</td>
							</tr>
						</table>
						
						
						
					
						  <div class="layout1_left_datalist">
						 
						   </div>
		
						<div class="layout1_left2">
						<div id="pageDiv">
							<p>
								<span class="prevBtnSpan"></span>
								<span class="pageNoSpan"></span>
								<span class="nextBtnSpan"></span>
								<span class="gotoPageSpan"></span>
							</p>
						</div>
					</div>
					</div>
					<div id="div_left_close" onclick="toggleFooter();"></div>
						<div id="div_left_open" onclick="toggleFooter();"></div>
					<div class="layout1_right" style="position: relative">
						<div id="simpleMap" class="simpleMap_top"></div>
							<div id="simpleMap_top">
						<label class="simpleMap_top_location">
							嘉兴市
						</label>
						<label class="simpleMap_top_news">
							 
						</label>
						<div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div>
					</div>
					<table cellpadding="0" cellspacing="0"
						style="float: right;position: absolute;top: 680px;right: 10px;overflow: hidden;width: 184px;height: 64px;border: solid 3px #726E6E;background: #fff;text-align: center;">
						<tbody>
							<tr>
								<td>正常</td>
								<td>紧张</td>
								<td>无空位</td>
							</tr>
							<tr>
								<td align="center"><div
										style="background: #2cc92d;height: 15px;width: 40px;"></div>
								</td>
								<td align="center"><div
										style="background: #f2570c;  height: 15px;  width: 40px;"></div>
								</td>
								<td align="center"><div
										style="background: #f22c48;  height: 15px;  width: 40px;"></div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				</div>
			</div>
			<!-- 页面内容-end -->
		<jsp:include page="../../../WebSit/page/main/foot.jsp" />
		</div>
	</body>
</html>
