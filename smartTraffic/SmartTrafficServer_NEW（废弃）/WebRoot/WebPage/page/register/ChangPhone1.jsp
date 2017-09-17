<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-绑定手机</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="WebPage/css/register/ChangPhone.css" />
		
        <script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	    <script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	    <script type="text/javascript" src="WebPage/js/common/inputValidator.js"></script>
	    <script type="text/javascript" src="WebPage/js/register/ChangPhone.js"></script>
	</head>

	<body>
		<div id="page_content">
			<jsp:include page="../../../WebPage/page/main/top.jsp" />

			<div id="content1" style="background-image: url('WebPage/image/register/find1.png')">
				<p class="content_title">
					绑定手机
				</p>

				<div style="margin: 150px 0 0 290px;">
					您的手机号码
					<input type="text" class="find_text" id="phone" style="width: 295px;" />
					<label style="display: none" id="phoneerr"></label>
				</div>
				<img src="WebPage/image/register/next1.png" class="text2"
					style="cursor: pointer; margin: 45px 0 0 382px;"
					onClick="forgetPassword()" />
			</div>
			<jsp:include page="../../../WebPage/page/main/foot.jsp" />
		</div>
	</body>
</html>
