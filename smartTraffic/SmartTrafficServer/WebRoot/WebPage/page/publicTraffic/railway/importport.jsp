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

		<title>智慧出行-铁路-列车时刻表查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebPage/css/publicTraffic/ticket.css" />
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
			src="WebPage/js/publicTraffic/railway/importport.js"></script>
		<script type="text/javascript" src="WebPage/js/common/paging.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebPage/page/main/top.jsp"
				flush="true">
				<jsp:param name="menu_number" value="9,5" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content">
				<div class="layout1">
					<a
						href="<%=basePath%>WebPage/page/publicTraffic/railway/ticket.jsp"
						class="layout1_a">余票查询</a>
					<a class="a_select">列车时刻表查询</a>
					<a href="<%=basePath%>WebPage/page/publicTraffic/railway/railwayinfo.jsp"
					class="layout1_a">订票热线和代售点</a>
					<label class="pagepath"><script>document.write(PATH_GRAPHICAL_PUBLICTRAFFIC_RIALWAY_TICKECT)</script></label>
				</div>
				<div class="layout2">
					<div class="layout2_left">
						<div class="layout2_left_layout1">
						<label class="layout2_label1">
							出发：
						</label>
						<input class="input_text3" value="中文/拼音/首字母" onBlur="TextBlur1(this,'中文/拼音/首字母')"
								onFocus="TextFocus1(this,'中文/拼音/首字母')"  id="startCity"/>
						<img src="WebPage/image/publicTraffic/chang_normal.png" onClick="ChangeText('中文/拼音/首字母')"
						 onMouseOut="ChangeOut(this)" onMouseOver="ChangeOver(this)"/>
						<label class="layout2_label1">
							到达：
						</label>
						<input class="input_text3" value="中文/拼音/首字母" onBlur="TextBlur1(this,'中文/拼音/首字母')"
								onFocus="TextFocus1(this,'中文/拼音/首字母')" id="endCity"/>
							</div>
						<div class="layout2_left_layout1">
						<label class="layout2_label1">
							出发时间：
						</label>
						<input type="checkbox" name="leaveTime" class="checkbox1"/>
							<label class="layout2_label5">
							上午
							</label>
						<input type="checkbox" name="leaveTime" class="checkbox1"/>
						<label class="layout2_label5">
							中午
							</label>
						<input type="checkbox" name="leaveTime" class="checkbox1"/>
						<label class="layout2_label5">
							下午
							</label>
						<input type="checkbox" name="leaveTime" class="checkbox1"/>
						<label class="layout2_label5">
							晚上
							</label>
							
						<label class="layout2_label4">
							列车类别：
						</label>
						<input type="checkbox" name="type" class="checkbox1"/>
							<label class="layout2_label5">
							高铁
							</label>
						<input type="checkbox" name="type" class="checkbox1"/>
						<label class="layout2_label5">
							动车
							</label>
						<input type="checkbox" name="type" class="checkbox1"/>
						<label class="layout2_label5">
							特快
							</label>
						<input type="checkbox" name="type" class="checkbox1"/>
						<label class="layout2_label5">
							快车
							</label>
							<input type="checkbox" name="type" class="checkbox1"/>
						<label class="layout2_label5">
							普通列车
							</label>
							</div>
					</div>
						<img src="WebPage/image/search_normal.png"
						onclick="ShowDataBySearch('GetTrainSchedule', 1)"
						onmouseout="SearchOut(this)" onMouseOver="SearchOver(this)" class="layout2_right"/>

				</div>
				<div class="layout3" id="TimeItem">
				</div>
				<div class="layout4">
				<label class="layout4_label1"></label><label class="layout4_label2">(共<a id="alltotal"></a>个车次)</label>
				</div>
				<!--<div class="layout5">
				<label>筛选条件：</label>
				<div class="CRselectBox_small" id="layout1_select01">
						<input type="hidden" value="1" class="abc" />
						<input type="hidden" value="航空公司" class="abc_CRtext_small" />
						<a class="CRselectValue_small">航空公司</a>
						<ul class="CRselectBoxOptions_small">
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="1" title="航空公司">航空公司</a>
							</li><li class="CRselectBoxItem_small">
								<a rel="2" title="南方航空">南方航空</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a rel="3" title="上海航空">上海航空</a>
							</li>
						</ul>
					</div>
					<div class="CRselectBox_small" id="layout1_select02">
						<input type="hidden" value="1" class="abc_small" />
						<input type="hidden" value="仓位类型" class="abc_CRtext_small" />
						<a class="CRselectValue_small">仓位类型</a>
						<ul class="CRselectBoxOptions_small">
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="1" title="仓位类型">仓位类型</a>
							</li>
						</ul>
					</div>
					<div class="CRselectBox_small" id="layout1_select03">
						<input type="hidden" value="1" class="abc_small" />
						<input type="hidden" value="起飞机场" class="abc_CRtext_small" />
						<a class="CRselectValue_small">起飞机场</a>
						<ul class="CRselectBoxOptions_small">
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="1" title="起飞机场">起飞机场</a>
							</li>
						</ul>
					</div>
					<div class="CRselectBox_small" id="layout1_select04">
						<input type="hidden" value="1" class="abc_small" />
						<input type="hidden" value="抵达机场" class="abc_CRtext_small" />
						<a class="CRselectValue_small">抵达机场</a>
						<ul class="CRselectBoxOptions_small">
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="1" title="抵达机场">抵达机场</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a rel="2" title="上海虹桥">上海虹桥</a>
							</li>
						</ul>
					</div>
				</div>
				--><div class="layout6">
					<table cellpadding="0" cellspacing="0" border="0"
						class="listTable2">
						<col width="5%" />
						<col width="10%" />
						<col width="19%" />
						<col width="19%" />
						<col width="19%" />
						<col width="18%" />
						<col width="10%" />
						<tr>
							<th>&nbsp;
								
							</th>
							<th>
								车次
							</th>
							<th>
								起抵车站
							</th>
							<th>
								起抵时间
							</th>
							<th>
								历时
							</th>
							<th>
								参考票价
							</th>
							<th>
								里程（公里）
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
