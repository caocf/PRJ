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

		<title>违章</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/law.css" />
		<link rel="stylesheet" type="text/css"
			href="css/common/table_show.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/business/illegal_show.js"></script>
		<script type="text/javascript" src="js/common/paging.js"></script>
		<script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=session.getAttribute("userId")%>" id="userId"/>
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="layer3">
			<div id="layer4">
				<div id="layer4_left">
					<img src="image/operation/add_know_hover.png" onmouseover="addkonwOver(this)"
					 onmouseout="addkonwOut(this)" onClick="AddNewIllegal()" /><a 
					 onClick="showIllegalList('showIllegalList',1)">待审核</a>|<a onClick="IllegalListByCategories(this,1)">港政</a>|<a 
					 onClick="IllegalListByCategories(this,3)">航政</a>|<a onClick="IllegalListByCategories(this,2)">运政</a>|<a 
					 onClick="IllegalListByCategories(this,4)">海事</a>|<a onClick="showAllIllegalList('showIllegalList',1)">全部</a>
				</div>
				<div id="layer4_right">
					<input type="text" value="" id="searchContent" class="input_box" /><img alt="搜索" 
					src="image/operation/search_normal.png"
						class="u3_img" onClick="search('showIllegalList',1)"
						onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)" />
				</div>
			</div>
			<div id="layer5">
				
				<table width="100%" border="0" cellspacing="0" class="listTable"
					id="illegaltable">
					<col width="7%" />
					<col width="15%" />
					<col width="15%" />
					<col width="15%" />
					<col width="18%" />
					<col width="12%" />
					<col width="18%" />
					<tr>
						<th>
							序号
						</th>
						<th>
							船名
						</th>
						<th>
							执法人员
						</th>
						<th>
							违章案由
						</th>
						<th>
							执法时间
						</th>
						<th>
							状态
						</th>
						<th>
							<label class="operation">
								操作
							</label>
						</th>
					</tr>
				</table>
				<div class="loadingData">
					数据加载中·····
				</div>
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
