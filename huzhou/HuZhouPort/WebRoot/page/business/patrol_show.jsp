<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>码头巡查</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/common/style.css" />
	<link rel="stylesheet" type="text/css" href="css/law.css" />
	<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
	
	<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" src="js/business/patrol_show.js"></script>
	<script type="text/javascript" src="js/common/paging.js"></script>
	<script type="text/javascript" src="js/common/formatConversion.js"></script>
	<script type="text/javascript" src="js/common/Operation.js"></script>
  </head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=basePath%>" id="basePath"/>
		<div id="layer3">
			<div id="layer4">
				<div id="layer4_left">
				<a onclick="showAllPatrolList('showPatrolList',1)">全部</a>|
				<a onclick="PatrolListByCategories(this,1)">港政</a>|
				<a onclick="PatrolListByCategories(this,3)">航政</a>|
				<a onclick="PatrolListByCategories(this,2)">运政</a>|
				<a onclick="PatrolListByCategories(this,4)">海事</a>
				</div>
				<div id="layer4_right">
				<input type="text" value="" id="searchContent" class="input_box"/><img alt="搜索" 
				src="image/operation/search_normal.png" class="u3_img" 
					 onclick="search('showPatrolList',1)"  onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
				</div>
				
			</div>
			<div id="layer5">
			 <label class="loadingData" style="display:none">数据加载中·····</label>
				<table width="100%" border="0" cellspacing="0" class="listTable" id="Patroltable">
					<col width="10%"/><col width="20%"/>
					<col width="20%"/><col width="25%"/><col width="25%"/>
					<tr>
					<th>序号</th>
					<th>码头</th>
					<th>巡查人员</th>
					<th>巡查时间</th>
					<th><label class="operation">操作</label></th>
					</tr>
				</table>
			</div>
		<div id="pageDiv">
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

	</body>
</html>
