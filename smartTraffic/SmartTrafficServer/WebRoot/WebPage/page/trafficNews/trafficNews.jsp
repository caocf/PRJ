<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-交通快讯</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" 
			href="<%=basePath%>WebPage/css/common/table.css"/>
		<link rel="stylesheet" type="text/css" 
			href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css"/>

		<script type="text/javascript"
			src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
		<script type="text/javascript" src="WebPage/js/time/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>WebPage/js/common/paging.js"></script>
		<script type="text/javascript" src="WebPage/js/common/formatConversion.js"></script>
		<script type="text/javascript" src="WebPage/js/trafficNews/TypeList.js"></script>

	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="5,1" />
			</jsp:include>
			<%-- 界面内容 --%>
			<div id="main_content">
				<div style="width: 100%; height: 780px">
					<div class="main_left">
						<div class='main_left_one'><a  class='main_left_select' onclick="SearchTzInfoList(this,'QueryTzxxList',1,0)">通阻信息(交通事故)</a></div>
						<div class='main_left_one'><a  class='main_left_select_no' onclick="SearchTzInfoList(this,'QueryJTGZList',1,1)">交通管制信息</a></div>
						<!--  <div class='main_left_one'><a  class='main_left_select_no' onclick="SearchTrafficInfoList(this,1)">公交改道信息</a></div>
						<div class='main_left_one'><a  class='main_left_select_no' onclick="SearchTrafficInfoList(this,1)">交通事故信息</a></div>
						-->
					</div>
					
					<div class="main_right" id="main_right_list">
						<!-- <div style="width:100%;height:50px;" id="search_div">
							<span style="float:left;height:29px;line-height: 29px;vertical-align: middle;" >标题:</span><input id="search_input" class="input_text3" style="float:left;height:27px;line-height: 27px;color:black;" />
							<input id="beginTime" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly="readonly" class="Wdate" style="float:left;margin-left:10px;" />
							<span style="float:left;height:29px;line-height: 29px;vertical-align: middle;margin-left:10px;" >至</span>
							<input id="endTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly="readonly" class="Wdate" style="float:left;margin-left:10px;" />
							<img style="margin-left: 10px;float:left;" src="WebPage/image/search_normal.png" onMouseOut="SearchOut(this)" onMouseOver="SearchOver(this)"  onclick="showTzInfoList('QueryTzxx',1)" />
						</div> -->
						 <div id="NewsList" class="main_right_li">
						 	<!-- <table class="listTable" id="tzInfo" cellpadding="0" cellspacing="0">
						 	<col width="15%"/><col width="40%"/><col width="15%"/><col width="15%"/>
						 	<col width="15%"/>
							 	<tr>
							 		<th>标题</th>
							 		<th>内容</th>
							 		<th>类别名称</th>
							 		<th>发布人</th>
							 		<th>发布时间</th>
							 	</tr>
						 		
						 	</table> -->
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
					<div class="main_right" id="main_right_detail" style="display:none;">
						
					</div>
				</div>
			</div>
			
			
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>

	</body>
</html>
