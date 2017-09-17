<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    CXtDpt shiju = (CXtDpt) session.getAttribute("shiju");
    int szshiju = -2;
    if (shiju != null)
        szshiju = shiju.getId();

    CXtDpt chuju = (CXtDpt) session.getAttribute("chuju");
    int szchuju = -2;
    if (chuju != null)
        szchuju = chuju.getId();

    CXtDpt szjudpt = (CXtDpt) session.getAttribute("dpt");
    int szju = -2;
    if (szjudpt != null)
        szju = szjudpt.getId();

    CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.getAttribute("dptshixzqh");
    int szshixzqh = -2;
    if (dptshixzqh != null)
        szshixzqh = dptshixzqh.getId();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>法定报表</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css"
          href="page/common/css/common.css">
    <link rel="stylesheet" type="text/css"
          href="page/tongjibaobiao/fading_framework.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/tongjibaobiao/fading_framework.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/unauth.js"></script>
</head>

<body style="min-width: 1200px;overflow-x:hidden;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2">
            <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
                 class="pull-left" style="margin-top:10px;"
                 onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
            <label class="pull-left">报表统计</label>
        </div>
        <div class="col-xs-7">
            <c:if test="${ui.hasPerm('VIEW_SHENGGYHBB')||ui.hasPerm('VIEW_SHIGGYHBB')||ui.hasPerm('VIEW_DPTGGYHBB')
            || ui.hasPerm('VIEW_SHENZXYHBB') || ui.hasPerm('VIEW_SHIZXYHBB') || ui.hasPerm('VIEW_DPTZXYHBB')
            || ui.hasPerm('VIEW_SHENZXGCBB') || ui.hasPerm('VIEW_SHIZXGCBB') || ui.hasPerm('VIEW_DPTZXGCBB')
            || ui.hasPerm('VIEW_JSYBB')}">
                <a class="btn btn-primary active" id="tabfading">法定报表</a>
            </c:if>
            <c:if test="${ui.hasPerm('VIEW_SHENHDBB')||ui.hasPerm('VIEW_SHIHDBB')}">
                <a class="btn btn-primary" id="tabsheding">设定报表</a>
            </c:if>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>
</div>

<div style="float:left;width:320px;">
    <div id="divleft" class="navcontentleft borderright"
         style="overflow:auto;background: white;">
        <div id="baobiaotree" style="overflow: auto;">
        </div>
    </div>
</div>
<div class="nopadding nomargin"
     style="float:left;width:15px;">
    <div id="leftmenu" expand="1" class="menuleftshouqi"
         style="height:57px;width:11px;margin-left:-1px;margin-top:300px;">
        <!-- 左侧航道导航展开折叠按钮图标 -->
    </div>
</div>
<div id="divright" style="margin-left:335px;">
    <div class="nopadding nomargin" style="background:#f0f0f0;float:left;width:100%;margin-top:15px;">
        <div class="col-xs-12"
             style="width:100%;margin-left:0px;padding-left:0px;">
            <iframe id="iframewin" style="overflow:hidden;border:none;height:100%;width:100%;"
                    src="">
            </iframe>
        </div>
    </div>
</div>
</body>
</html>
