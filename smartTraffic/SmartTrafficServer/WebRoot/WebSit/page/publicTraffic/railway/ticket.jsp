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

		<title>智慧出行-铁路-余票查询</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebSit/css/publicTraffic/ticket.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>WebSit/css/common/table.css" />
		<link rel="stylesheet" type="text/css"
			href="WebSit/css/common/CRselectBox.css" />

		<script type="text/javascript"
			src="WebSit/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebSit/js/common/Operation.js"></script>
		<script type="text/javascript"
			src="WebSit/js/common/formatConversion.js"></script>
		<script type="text/javascript"
			src="WebSit/js/publicTraffic/railway/ticket.js"></script>
		<script type="text/javascript" src="WebSit/js/common/paging.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../../WebSit/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="5" />
			</jsp:include>
			<!-- 页面内容 -->
			<div id="main_content">
				<div class="layout1">
					<a class="a_select">余票查询</a>
					<a href="<%=basePath%>WebSit/page/publicTraffic/railway/importport.jsp"
					class="layout1_a">列车时刻表查询</a>
				</div>
				<div class="layout2">
					<div class="layout2_left">
						<div class="layout2_left_layout1">
							<label class="layout2_label1">
								出发：
							</label>
							<input class="input_text3" value="中文/拼音/首字母" onBlur="TextBlur1(this,'中文/拼音/首字母')"
								onFocus="TextFocus1(this,'中文/拼音/首字母')"  id="startCity"/>
							<img src="WebSit/image/publicTraffic/chang_normal.png" onClick="ChangeText('中文/拼音/首字母')"
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
						<img src="WebSit/image/firstpage/search_normal.png"
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
						<input type="hidden" value="车次类型" class="abc_CRtext_small" />
						<a class="CRselectValue_small">车次类型</a>
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
						<input type="hidden" value="到站时间" class="abc_CRtext_small" />
						<a class="CRselectValue_small">到站时间</a>
						<ul class="CRselectBoxOptions_small">
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="1" title="仓位类型">仓位类型</a>
							</li>
						</ul>
					</div>
					<div class="CRselectBox_small" id="layout1_select03">
						<input type="hidden" value="" class="abc_small" />
						<input type="hidden" value="" class="abc_CRtext_small" />
						<a class="CRselectValue_small">列车类型</a>
						<ul class="CRselectBoxOptions_small">
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="11001" title="高铁">高铁</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="11002" title="动车">动车</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="11003" title="特快">特快</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="11004" title="快车">快车</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="11005" title="普通列车">普通列车</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="11006" title="高铁">高铁</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="11007" title="高铁">高铁</a>
							</li>
							<li class="CRselectBoxItem_small">
								<a class="selected_small" rel="11008" title="高铁">高铁</a>
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
			<jsp:include page="../../../../WebSit/page/main/foot.jsp" />
		</div>
	</body>
</html>
