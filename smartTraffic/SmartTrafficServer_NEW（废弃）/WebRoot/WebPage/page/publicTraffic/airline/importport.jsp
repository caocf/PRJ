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

		<title>智慧出行-进出港实况信息查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/publicTraffic/importport.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/common/table.css" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/common/CRselectBox.css" />

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript"
			src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript"
			src="WebPage/js/publicTraffic/airline/importport.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebPage/page/main/top.jsp"
				flush="true">
				<jsp:param name="menu_number" value="2,1,6" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content">
				<div class="layout1">
					<a
						href="<%=basePath%>WebPage/page/publicTraffic/airline/ticket.jsp"
						class="layout1_a">余票查询</a>
					<a class="a_select">进出港实况信息查询</a>
				</div>
				<div class="layout2">
					<label class="layout2_label1">
						机场：
					</label>
					<input class="input_text3" value="请输入机场名称" onBlur="TextBlur(this)"
						onFocus="TextFocus(this)" id="airportName"/>
					<div class="CRselectBox" id="layout1_select01">
						<input type="hidden" value="0" class="abc" />
						<input type="hidden" value="全部" class="abc_CRtext" />
						<a class="CRselectValue" href="#">全部</a>
						<ul class="CRselectBoxOptions">
							<li class="CRselectBoxItem">
								<a class="selected" rel="0">全部</a>
							</li>
							<li class="CRselectBoxItem">
								<a  rel="1">进港</a>
							</li>
							<li class="CRselectBoxItem">
								<a rel="2">出港</a>
							</li>
						</ul>
					</div>

					<label class="layout2_label2">
						航班：
					</label>
					<input class="input_text3" value="请输入航班名称" onBlur="TextBlur(this)"
						onFocus="TextFocus(this)" />
					<div class="CRselectBox" id="layout1_select02">
						<input type="hidden" value="1" class="abc" />
						<input type="hidden" value="南方航空" class="abc_CRtext" />
						<a class="CRselectValue">南方航空</a>
						<ul class="CRselectBoxOptions">
							<li class="CRselectBoxItem">
								<a class="selected" rel="1">南方航空</a>
							</li>
						</ul>
					</div>

					<img src="WebPage/image/search_normal.png"
						onclick="ShowDataByPage('GetRealTimeAir', 1)"
						onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" />

				</div>
				<div class="layout3">
					<table cellpadding="0" cellspacing="0" border="0"
						class="listTable2">
						<col width="2%" />
						<col width="7%" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
						<col width="13%" />
						<col width="13%" />
						<col width="13%" />
						<col width="13%" />
						<col width="7%" />
						<tr>
							<th>&nbsp;
								
							</th>
							<th>
								航班号
							</th>
							<th>
								航空公司
							</th>
							<th>
								始发地
							</th>
							<th>
								目的地
							</th>
							<th>
								计划起飞
							</th>
							<th>
								计划到达
							</th>
							<th>
								实际起飞
							</th>
							<th>
								实际到达
							</th>
							<th>
								状态
							</th>
						</tr>
					</table>
					
					
				
				</div>
				<div id="pageDiv">
					<p>
						<span class="prevBtnSpan"></span>
						<span class="pageNoSpan"></span>
						<span class="nextBtnSpan"></span>
						<span class="gotoPageSpan"></span>
					</p>
				</div>
			</div>
			<!-- 页面内容-end -->
			<jsp:include page="../../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
