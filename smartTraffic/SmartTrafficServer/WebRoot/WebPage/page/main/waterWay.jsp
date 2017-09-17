<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-水路交通</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />

		<script type="text/javascript"
			src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="WebPage/js/common/Operation.js"></script>



	</head>
	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" flush="true">
				<jsp:param name="menu_number" value="2,2" />
			</jsp:include>
				<!-- 页面内容 -->
			<div id="main_content2"
				style="height: 400px; margin: 0 auto; text-align: center; padding: 50px 0 0 0; font-size: 14px;">
				抱歉，水路交通正在建设中······
			</div>

			<!-- 页面内容-end -->
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />

		</div>
	</body>
</html>



