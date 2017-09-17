<%@page import="com.channel.model.user.CXtUser" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:useBean id="ui1" scope="page" class="com.channel.permission.UI"></jsp:useBean>

<div id="leftpopmenu"
     style="z-index:999999;left:-173px;top:0px;position: fixed;width:168px;height: 100%;background: #347ab6;">
    <div class="btn btn-primary noborder"
         style="width:100%;height:49px;font-size:15px;padding:12px 0 0 0px;border-radius:0;"
         onclick="window.location.href = $('#basePath').val()+'page/home/home.jsp';">
        <img src="img/ic_fanhuishouye.png">&nbsp;&nbsp;返回首页
    </div>
    <c:choose>
        <c:when test="${ui1.groupHasPerms('综合查询')}">
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 onclick="tohdaoxinxi();">
                <img src="img/ic_zonghechaxun.png">&nbsp;&nbsp;综合查询
            </div>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 disabled>
                <img src="img/ic_zonghechaxun.png">&nbsp;&nbsp;综合查询
            </div>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${ui1.groupHasPerms('航道巡查')}">
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 onclick="tohdaoxuncha();"><img
                    src="img/ic_hangdaoxuncha.png">&nbsp;&nbsp;航道巡查
            </div>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 disabled><img
                    src="img/ic_hangdaoxuncha.png">&nbsp;&nbsp;航道巡查
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${ui1.groupHasPerms('航政管理')}">
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 onclick="tohangzheng();">
                <img src="img/ic_hangzhengguanli.png">&nbsp;&nbsp;航政管理
            </div>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 disabled>
                <img src="img/ic_hangzhengguanli.png">&nbsp;&nbsp;航政管理
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${ui1.groupHasPerms('航道信息维护')}">
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 onclick="tohangdaotubj();">
                <img src="img/ic_hangdaoxinxiweihu.png">&nbsp;&nbsp;信息维护
            </div>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 disabled>
                <img src="img/ic_hangdaoxinxiweihu.png">&nbsp;&nbsp;信息维护
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${ui1.groupHasPerms('流量观测')}">
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 onclick="toliuliang();">
                <img src="img/ic_liuliangguance.png">&nbsp;&nbsp;流量观测
            </div>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 disabled>
                <img src="img/ic_liuliangguance.png">&nbsp;&nbsp;流量观测
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${ui1.groupHasPerms('航道养护')}">
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 onclick="tohdaoyh();">
                <img src="img/ic_hangdaoyanghu.png">&nbsp;&nbsp;航道养护
            </div>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 disabled>
                <img src="img/ic_hangdaoyanghu.png">&nbsp;&nbsp;航道养护
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${ui1.groupHasPerms('统计报表')}">
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 onclick="totongji();">
                <img src="img/ic_tongjibaobiao.png">&nbsp;&nbsp;统计报表
            </div>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 disabled>
                <img src="img/ic_tongjibaobiao.png">&nbsp;&nbsp;统计报表
            </div>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${ui1.groupHasPerms('系统维护')}">
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 onclick="toxitongwh();">
                <img src="img/ic_xitongweihu.png">&nbsp;&nbsp;系统维护
            </div>
        </c:when>
        <c:otherwise>
            <div class="btn btn-primary noborder"
                 style="width:100%;height:70px;font-size:15px;padding:22px 0 0 0px;border-radius:0;"
                 disabled>
                <img src="img/ic_xitongweihu.png">&nbsp;&nbsp;系统维护
            </div>
        </c:otherwise>
    </c:choose>
    <div class="borderbottom" style="height:20px;"></div>
    <div class="btn btn-primary noborder"
         style="width:100%;height:60px;font-size:15px;padding:10px 0 10px 0px;border-radius:0;" onclick="logout();">
        <i class="icon-off icon-2x"></i>&nbsp;&nbsp;<label style="line-height:31px;">退出系统</label>
    </div>
</div>
</body>
</html>
