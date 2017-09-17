<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setCharacterEncoding("utf-8");
	String tableNameText = null;
	if (request.getParameter("tableNameText") != null) {
		tableNameText = new String(request.getParameter("tableNameText").getBytes(
				"ISO-8859-1"), "utf-8");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>综合数据库数据维护库客户端_表信息修改</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/table.css" />
		
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/inputValidator.js"></script>
		<script type="text/javascript" src="js/formatConversion.js"></script>
		<script type="text/javascript" src="js/TableValidator.js"></script>
		<script type="text/javascript" src="js/selectDate.js"></script>
		<script type="text/javascript" src="js/TableUpdate.js"></script>
	</head>

	<body>
		<div id="page_content">
			<input type="hidden" value="<%=request.getParameter("pNum") %>" id="pNum" />
			<input type="hidden" value="<%=request.getParameter("tNum") %>" id="tNum" />
			<input type="hidden" value="<%=basePath %>" id="basePath" />
			<jsp:include page="top.jsp" flush="true" />
			<div id="main_content_update">
				
				<form id="contentform">
				<input type="hidden"
						value="<%=request.getParameter("tNum") %>" name="tableName" />
					<input type="hidden"
						value="<%=request.getParameter("rowid") %>" id="rowid" name="rowid"/>
					<table border="0" cellpadding="0" cellspacing="0" id="table_data" class="listTable1">
						<tr><th colspan="2"><%=tableNameText %></th><td></td></tr>
					</table>
					<div class="submitdiv">
					<input type="button" value="提交" onclick="updateContent();" class="sbutton"/>
					<input type="reset" value="重置" class="sbutton"/>
					<input type="button" value="返回" onclick="back();" class="sbutton"/>
					</div>
					</form>
				</div>
				
			
			</div>
		
	</body>
</html>
