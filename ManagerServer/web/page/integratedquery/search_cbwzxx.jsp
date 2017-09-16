<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'datapost.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/common/style.css" />
		<link rel="stylesheet" type="text/css" href="css/integratedquery/query_wz.css" />
		<link href="js/common/jquery-ui-1.8.16.custom.css"
			rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="js/common/jquery-1.5.2.min.js"></script>
		 <script type="text/javascript" src="js/common/Operation.js"></script>
		 <script type="text/javascript" src="js/common/paging.js"></script>
		 <script type="text/javascript" src="js/common/formatConversion.js"></script>
		<script type="text/javascript" src="js/integratedquery/cbwzxx.js"></script>
		<script type="text/javascript" src="js/common/CheckLogin.js"></script>
		<script type="text/javascript" src="js/common/jquery-ui-1.8.16.custom.min.js"></script>
		<script type="text/javascript" src="js/integratedquery/SearchShipName.js"></script>

	</head>

	<body><input type="hidden" value="<%=session.getAttribute("username")%>" id="LoginUser" />
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<div id="content">
		<div id="search_div">
		<input type="text" value="" id="cbname" class="input_box" onkeyup="ChangeTextValue(this)"/>
		<img src="image/operation/query_normal.png" onclick="showBaseInfo('GetAndPostDate',1)" onmouseover="QueryOver(this)" onmouseout="QueryOut(this)"/>
		</div>
		<table id="Infor" cellpadding="0" cellspacing="0">
		<col width="100px"/><col width="*"/>
		</table>
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
	<div id="autocomplete_div" style="z-index: 1; top: 290px; left: 30px;position: absolute;width: 330px; "></div>
	</body>
</html>
