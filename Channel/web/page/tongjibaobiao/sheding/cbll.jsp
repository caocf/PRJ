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

    <title>船舶流量汇总表</title>

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
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet"
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">

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
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" src="common/common/js/tree.js"></script>
    <script type="text/javascript" src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" src="page/tongjibaobiao/sheding/cbll/cbll.js"></script>
</head>

<body style="background:#f0f0f0;padding-bottom: 20px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">
<input type="hidden" id="flagipt" value="1">

<div class="container-fluid  shadow border" style="background:white;">
    <div class="row navcontenttitle borderbottom">
        <div class="col-xs-12">核查系统船舶流量汇总表</div>
    </div>
    <div class="row borderbottom" style="padding:10px 0 10px 0;">
        <div class="col-xs-2">
            <label style="float:left;margin-top:8px;margin-right:8px;">观测点</label>

            <div id="selgcddiv" style="float:left;">
                <script>
                    $('selgcddiv').css('width', 'auto');
                </script>
            </div>
        </div>
        <div class="col-xs-7">
            <div style="float:left;width:auto">
                <label style="float:left;margin-top:8px;margin-right:8px;width: auto">查询时间</label>
                <input type="button" class="btn btn-default" id="btnday" value="当天" onclick="searchCbll(1)">
                <input type="button" class="btn btn-default" id="btnweek" value="一周内" onclick="searchCbll(7)">
                <input type="button" class="btn btn-default" id="btnmonth" value="一月内" onclick="searchCbll(30)">
                <input type="button" class="btn btn-default" id="btnyeear" value="一年内" onclick="searchCbll(365)">
                <input type="button" class="btn btn-default" id="loadcus" value="自定义" onclick="loadcus()">
            </div>
            <div id="seldaydiv" style="float:left;width: 50%;margin-left: 10px;display: none">
                <label style="float:left;margin-top:8px;margin-right:8px;width: auto">自定义时间</label>
                <script>
                    var now = new Date();
                    now = getTimeFmt(new Date(), 'yyyy-MM-dd');
                    $("#seldaydiv").addtimeregion({
                        idstart: 'selstarttime',
                        idend: 'selendtime',
                        hintstart: '年-月-日',
                        hintend: '年-月-日',
                        validatorsstart: {
                            notempty: {
                                msg: '请输入开始时间'
                            }
                        },
                        validatorsend: {
                            notempty: {
                                msg: '请输入结束时间'
                            }
                        }
                    });
                    $('selday').css('width', 'auto');
                </script>
                <input type="button" class="btn btn-primary" id="btncus" value="搜索" onclick="searchCbll(10)">
            </div>
        </div>
        <c:if test="${ui.hasPerm('EXPORT_BB') || ui.hasPerm('PRINT_BB')}">
            <div class="col-xs-2">
                <c:if test="${ui.hasPerm('EXPORT_BB')}">
                    <button class="btn btn-default" onclick="exportcblltable()">导出</button>
                </c:if>
                <c:if test="${ui.hasPerm('PRINT_BB')}">
                    <button class="btn btn-default"
                            onclick="document.getElementById('baobiaoiframe').contentWindow.print();">
                        打印
                    </button>
                </c:if>
            </div>
        </c:if>
    </div>

    <div class="row text-center">
        <iframe id="baobiaoiframe" class="noborder"
                style="border:none;width:98%;overflow:hidden;" onload="reinitIframeHeight('baobiaoiframe')"></iframe>
    </div>
</div>
</body>
</html>
