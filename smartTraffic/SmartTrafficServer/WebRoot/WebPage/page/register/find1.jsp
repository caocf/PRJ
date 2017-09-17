<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>智慧出行-找回密码</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css"
			href="WebPage/css/register/style.css" />
        <script type="text/javascript" src="WebPage/js/XMap/jquery-1.10.2.min.js"></script>
	    <script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
	    <script type="text/javascript" src="WebPage/js/common/inputValidator.js"></script>
	    <script type="text/javascript" src="WebPage/js/register/FindPwd.js"></script>
	</head>

	<body>
	<jsp:include page="../../../WebPage/page/main/top.jsp" />
	
		<div id="content1"
			style="background-image: url('WebPage/image/register/find1.png')">
			<p class="cont_wz">
				找回密码
			</p>
			<div class="find1_cont">
				<div class="mod_mima_li" style="width: 470px; margin-left: 10px;">
					您绑定的手机号码
					<input type="text" class="text" id="phone"
						style="font-size: 15px; width: 295px;margin-left: 16px" value="手机号码"
						onBlur="TextBlur(this)" onFocus="TextFocus(this)" />
						<label style="display:none" id="phoneerr"></label>
				</div>
				<img src="WebPage/image/register/next1.png" class="text2" 
						style="cursor: auto; width: 310px;margin: 45px auto auto 163px;" onClick="forgetPassword()" />
			</div>



		</div>
		
		 <jsp:include page="../../../WebPage/page/main/foot.jsp" />
	</body>
</html>
