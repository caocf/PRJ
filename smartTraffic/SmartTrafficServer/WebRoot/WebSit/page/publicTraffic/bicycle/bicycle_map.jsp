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

		<title>智慧出行-公共自行车-网点查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<link rel="stylesheet" type="text/css"
			href="WebSit/css/graphical/pot.css" />
		<link rel="stylesheet" type="text/css"
			href="WebSit/css/common/map.css" />
		<link rel="stylesheet" type="text/css"
			href="WebSit/css/graphical/parking.css" />

		<script type="text/javascript"
			src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebSit/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebSit/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="WebSit/js/common/json2.js"></script>
		<script type="text/javascript"
			src="WebSit/js/publicTraffic/bicyccle/bicycle3.js"></script>
		<script type="text/javascript" src="WebSit/js/common/div_left_handle.js"></script>


	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebSit/page/main/top.jsp" flush="true">
			<jsp:param name="menu_number" value="2" />
		</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content3">
				<div class="layout1">
					<div class="left">
						<div class="left_top">
							<a href="WebSit/page/publicTraffic/bicycle/ConstructionSurvey.jsp"
								class="left_top_text2">建设概况</a>
							<a href="WebSit/page/publicTraffic/bicycle/bicycle_map.jsp"
								class="left_top_text1">网点查询</a>
						</div>
						<div class="left_bottom">
							<div class="left_bottom_wb">
								<input type="text" class="input" value="请输入查询的信息"
									onBlur="TextBlur(this)" onFocus="TextFocus(this)"
									id="searchContent" />
								<a class="input_rig" onClick="SearchBicycle2()">搜索</a>
								<label style="display: block;float: left;padding: 5px 0 0 0; width:300px;text-align: center;">在地图上鼠标右击也可搜索自行车点</label>
							</div>
							
						<div class="layout1_left_datalist_title">
							<img src="WebSit/image/graphical/select.png" style="float: left" />
							<span style="float: left;">自行车概况</span>
							<span style="float: right;">共<label
									id="searchReasultCount"></label>条搜索结果</span>
						</div>
							
							<div class="layout1_left_datalist2"></div>
							
						</div>
					</div>
					<div id="div_left_close" onclick="toggleFooter('left','layout1_right');"></div>
						<div id="div_left_open" onclick="toggleFooter('left','layout1_right');"></div>
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
						<table cellpadding="0" cellspacing="0"
							style="float: right;position: absolute;top: 680px;right: 10px;overflow: hidden;width: 184px;height: 64px;border: solid 3px #726E6E;background: #fff;text-align: center;">
						<tbody>
							<tr>
								<td>正常</td>
								<td>紧张</td>
								<td>无可借车辆</td>
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
		<jsp:include page="../../../../WebSit/page/main/foot.jsp" flush="true" />
		</div>
	</body>
</html>
