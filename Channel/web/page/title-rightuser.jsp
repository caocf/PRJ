<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.channel.model.user.CXtUser" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CXtUser user = ((CXtUser) session.getAttribute("user"));
    String username = null;
    String uname = null;
    int userid = -1;
    if (user != null) {
        username = user.getUsername();
        uname=user.getName();
        userid = user.getId();
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>


</head>

<body>

<div class="col-xs-2 text-right pull-right">
    <div class="dropdown dropdown-menu-right">
        <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="true"
                style="font-size: 14px;">
            <%=uname%>
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu pull-right" aria-labelledby="dropdownMenu1" style="min-width: 130px;width:130px;">
            <li>
                <a class="text-center" onclick="window.open('page/personalinfo/personalinfo.jsp')"
                   style="font-size: 14px;height:30px;line-height:30px;">
                    个人设置
                </a>
            </li>
            <li>
                <a class="text-center" href="#" style="font-size: 14px;height:30px;line-height:30px;"
                   onclick="logout();">
                    退出系统
                </a>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
