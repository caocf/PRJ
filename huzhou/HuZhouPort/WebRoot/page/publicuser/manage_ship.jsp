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

		<title>船舶资料管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css"
			href="css/system/managebook.css" />
		<link rel="stylesheet" type="text/css"
			href="css/common/smallDialog.css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		<script type="text/javascript" src="js/common/inputValidator.js"></script>
		<script type="text/javascript" src="js/publicuser/manage_ship.js"></script>
		<script type="text/javascript" src="js/common/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/common/Operation.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
	</head>

	<body>
		<input type="hidden" value="<%=session.getAttribute("username")%>"
			id="LoginUser" />
		<input type="hidden" value="<%=basePath%>" id="basePath" />
		<div id="layer1">
			<img src="<%=basePath%>image/operation/back_small_normal.png"
				onclick="javascript:window.history.go(-1);"
				onmouseover="BackSmallOver(this)" onMouseOut="BackSmallOut(this)"
				title="返回" />
		</div>
		<div class="totaldiv">

			<label id="title">
				船舶资料管理
			</label>
			<a onclick="OpenAddDialog()" id="a_add">[新增]</a>
			<div id="booklist"></div>

			<div id="AddBook" style="display: none; height: 160px;">
				<div class="nameDiv">
					资料名称：
					<input type="text" class="input_box" id="name" />
				</div>
				<div class="changeUserStatus_font">
					<img src="image/operation/sure_normal.png"
						onClick="AddBookSubmit()" onmouseover="SureOver(this)"
						onMouseOut="SureOut(this)" />
					<img src="image/operation/reset_small_normal.png" onClick="hiden()"
						onmouseover="ResetSmallOver(this)"
						onMouseOut="ResetSmallOut(this)" title="取消" />
				</div>
			</div>
		</div>
	</body>
</html>
