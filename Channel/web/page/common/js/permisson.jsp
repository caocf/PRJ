<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
    <title></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
</head>

<body>

<jsp:useBean id="ui" scope="session" class="com.channel.permission.UI"></jsp:useBean>

<%--存入权限--%>
<c:forEach var="perm" items="${ui.getPerms()}">
    <input type="hidden" id="${perm.name}" value="true">
</c:forEach>
<%--存入分组是否有权限--%>
<c:forEach var="group" items="${ui.getGroups()}">
    <input type="hidden" id="${group.id}" value="${group.name}">
</c:forEach>

</body>
</html>
