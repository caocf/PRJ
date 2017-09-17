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
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/graphical/parkingstyle.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>WebPage/css/common/map.css" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/common/CRselectBox.css" />
		
		<script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
			<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/XMapHelper.js"></script>
		<script type="text/javascript" src="WebPage/js/XMap/XMap.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/Graphical/parking.js"></script>
		<script type="text/javascript" src="WebPage/js/common/div_left_handle.js"></script>
	</head>

	<body>
		<div id="page_content">
		<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true" >
		<jsp:param name="menu_number" value="11,1"/> 
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
								<td>
									<img style="margin-left: 10px" src="WebPage/image/search_normal.png" onMouseOut="SearchOut(this)" onMouseOver="SearchOver(this)"  onclick="searchParking('searchParkingByNameAndTypes', 1)" />
								</td>
							</tr>
							<tr>
								<td>
								<!-- onclick="searchParkingByType('queryParkingFromCGByNameAndType', 1)" -->
									<div class="CRselectBox" id="parkType">
										<input type="hidden" value="1" class="abc" />
										<input type="hidden" value="路侧停车点" class="abc_CRtext" />
										<a class="CRselectValue" style="max-width:200px;">路侧停车点</a>
										<ul class="CRselectBoxOptions">
											<li class="CRselectBoxItem">
												<a  rel="1" class="selected">路侧停车点</a>
											</li>
											<li class="CRselectBoxItem">
												<a rel="2">广场式停车场</a>
											</li>
											<li class="CRselectBoxItem">
												<a rel="3">地下停车场</a>
											</li>
											<li class="CRselectBoxItem">
												<a rel="4">室内立体停车场</a>
											</li>
											<li class="CRselectBoxItem">
												<a rel="5">升降式停车场</a>
											</li>
											<li class="CRselectBoxItem">
												<a rel="6">其他停车场</a>
											</li>
										</ul>
									</div>
								</td>
							</tr>
						</table>
						
						
						
					
						  <div class="layout1_left_datalist">
							 	<div class="layout1_left_datalist_title" style="visibility: hidden;">
									<img src="WebPage/image/graphical/select.png" style="float: left" />
									<span style="float: left;">搜索结果</span>
									<span style="float: right;margin-right:10px;">共<label
											id="searchReasultCount"></label>条</span>
								</div>
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
						<!-- <div class="simpleMap_top_item">
							<span class="simpleMap_top_map" onClick="addNavType_self()">地图</span><span
								class="simpleMap_top_satellite" onClick="addNavType_self()">卫星</span>
						</div> -->
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
		<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
