<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>出错啦！</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/common/error.css">
	</head>
	<body>
		<div class="errorImage">
			<img src="image/common/error.jpg">
		</div>
		<div class="errorInfo">
			<fieldset class="errorFieldset">
				<legend>
					错误提示
				</legend>
				<span class="error"> <s:if test="exception.message!=null">
						<s:property value="exception.message" />
					</s:if> <br> <s:if test="#session.loginInfo.error!=null">
						<s:property value="#session.loginInfo.error" />
					</s:if> <s:if test="#session.error!=null">
						<s:property value="#session.error" />
					</s:if> <s:if test="#session.functions.error!=null">
						<s:property value="#session.functions.error" />
					</s:if> <s:if test="errorMessages[0]!=null">
						<s:property value="errorMessages[0]" />
					</s:if> <s:else>
					</s:else> </span>
			</fieldset>			
		</div>

	</body>
</html>
