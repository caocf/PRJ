<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CXtUser user = ((CXtUser) session.getAttribute("user"));
    String username = null;
    int userid = -1;
    if (user != null) {
        username = user.getUsername();
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

<jsp:useBean id="uit" class="com.channel.permission.UI" scope="page"></jsp:useBean>

<div class="row navrow" id="title">
    <div class="col-xs-2">
        <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
             class="pull-left" style="margin-top:10px;"
             onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
        <label class="pull-left">系统维护</label>
    </div>
    <div class="col-xs-8">
        <c:if test="${uit.groupHasPerms('用户管理')}">
            <a class="btn btn-primary" id="tabuser">用户管理</a>
        </c:if>
        <c:if test="${uit.groupHasPerms('组织机构管理')}">
            <a class="btn btn-primary" id="tabdpt">组织机构</a>
        </c:if>

        <c:if test="${uit.groupHasPerms('角色权限')}">
            <div class="btn-group">
                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false" id="btnperm">
                    角色权限<span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <c:if test="${uit.hasPerm('VIEW_ROLEPERM') ||
                    uit.hasPerm('MAN_ROLEUSER') ||
                    uit.hasPerm('VIEW_ROLEUSER') ||
                    uit.hasPerm('MAN_ROLEPERM')}">
                        <li id="liperm" style="height:35px;">
                            <a id="tabperm" style="height:35px;">角色管理</a>
                        </li>
                    </c:if>
                    <c:if test="${uit.hasPerm('MAN_PERM')}">
                        <li id="lipermman" style="height:35px;">
                            <a style="height:35px;" onclick="window.open('toPermManager');">权限维护</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </c:if>

        <c:if test="${uit.groupHasPerms('数据字典')}">
            <a class="btn btn-primary" id="tabdict">数据字典</a>
        </c:if>
        <c:if test="${uit.groupHasPerms('日志管理')}">
            <a class="btn btn-primary " id="tablog">日志管理</a>
        </c:if>

        <c:if test="${uit.groupHasPerms('备份还原')}">
            <a class="btn btn-primary" id="tabbackup">备份还原</a>
        </c:if>

    </div>
    <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
</div>
</body>
</html>
