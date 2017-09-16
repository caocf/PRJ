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

		<title>内河船户_绑定信息审核</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/profession/profession_information.css" />
		<link rel="stylesheet" type="text/css" href="css/user_department_show.css" />
		<link rel="stylesheet" type="text/css" href="css/sailing/newdialog.css" />
		<link rel="stylesheet" type="text/css" href="css/common/table_show.css" />
		<link href="css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
		<link href="css/common/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link href="css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/AddressList/neibuuser_bangding_list.js"></script>
		 <script type="text/javascript" src="js/common/CheckLogin.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/bootpaging.js"></script>
	</head>

	<body>
	<input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="content">
			<div id="u2">
				<div class="profession_top">
					<img alt="搜索" src="image/operation/search_normal.png" class="profession_searchbutton"
					onclick="SearchBoatmanKind('ShipListByTips',1)" onMouseOver="SearchOver(this)" onMouseOut="SearchOut(this)"/>
					<input type="text" id="content_search"  class="profession_search_input"/>
				</div>
				<table class="listTable" cellpadding="0" cellspacing="0">
					<col width="5%" /><col width="10%" /><col width="15%" /><col width="10%" />
					<col width="30%" /><col width="15%" /><col width="5%" /><col width="10%" />
					<tr>
						<th>序号</th>
						<th>用户名</th>
						<th>手机号</th>
						<th>用户类型</th>
						<th>船舶信息</th>
						<th>时间</th>
						<th>审批状态</th>
						<th>操作</th>
					</tr>
				</table>
				<div class='bootpagediv' style='width:100%;display: inline-block;'>
					<nav style='float:right;display:none' id='pageshow'>
						<ul class="pagination" id='pagination'>
						</ul>
					</nav>
				</div>
			</div>
		</div>
	</body>
</html>
