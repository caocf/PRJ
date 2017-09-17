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

		<title>综合数据库数据维护库客户端_表信息导入</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
$(document).ready(function() {
	if($("#sc").val()=="1"){
	$("#info").text("导入成功！");
	}else{
	$("#info").text("导入失败！");
	}
});</script>
	</head>

	<body>
		<div id="page_content">
		<jsp:include page="top.jsp" flush="true" />
		<!-- 页面内容 -->
		<div id="main_content">
			<input type="hidden" value="<%=request.getParameter("sc") %>" id="sc"/>
		<label id="info" style="display: block;width: 99%;text-align: center;font-size: 16px;font-weight: bold;color: #000"></label>
		
		<a href="javascript:window.history.go(-1)" style="display: block;width: 99%;text-align: center;font-size: 16px;font-weight: bold;color: #FC157A;margin-top: 20px">返回上一个页面</a>
		
		
		
		
		
		
		
		</div>
		<!-- 页面内容-end -->
		</div>
	</body>
</html>
