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

		<title>综合数据库数据维护库客户端_表信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/table.css" />
		
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/formatConversion.js"></script>
		<script type="text/javascript" src="js/paging.js"></script>
		<script type="text/javascript" src="js/TableInfo.js"></script>
		<script type="text/javascript" src="js/cookie.js"></script>
	</head>

	<body>
		<div id="page_content">
			<jsp:include page="top.jsp" flush="true" />

			<div id="main_content">
				
					<input type="hidden" value="<%=request.getParameter("tablename") %>" id="tablename" />
					<input type="hidden" value="<%=request.getParameter("pNum") %>" id="pNum" />
					<input type="hidden" value="<%=request.getParameter("tNum") %>" id="tNum" />
						<select id="SelectTable" onchange="SelectOneTable(false)"></select>
					<input type="button" value="新增" onclick="AddDate()" class="sbutton" id="AddButton" style="display:none;"/>
					
					<table border="0" cellpadding="0" cellspacing="0" id="table_data" class="listTable">
						
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
			</div>
		
	</body>
</html>
