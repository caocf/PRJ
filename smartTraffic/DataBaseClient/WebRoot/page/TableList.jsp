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

		<title>综合数据库数据维护库客户端_选表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/table.css" />
		
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/TableDate.js"></script>
		
	</head>

	<body>
		<div id="page_content">
		<jsp:include page="top.jsp" flush="true" />
		<!-- 页面内容 -->
		<div id="main_content">
		<table border="0" cellpadding="0" cellspacing="0" id="table_list" class="listTable">
		<tr><th>请选择数据表</th></tr>
		</table>
		
		
		</div>
		<!-- 页面内容-end -->
		</div>
	</body>
</html>
