<%@page import="com.channel.model.user.CXtUser" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

    <title>报表</title>

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
            src="page/common/js/unauth.js"></script>

</head>

<body style="background: white;overflow: hidden;font-family: 宋体;">
<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
            <center>
                <h2 class="bbtitle">全航区其他航道例行养护工作统计表</h2>
            </center>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-4 pull-left bbcontent">单位：${mapinfo.dpt }</div>
        <div class="col-xs-4 text-right pull-right bbcontent">${mapinfo.selyear }年${mapinfo.selseason }季度</div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <table class="table table-bordered" style="table-layout: fixed;">
                <tr>
                    <td rowspan="2" class="text-center bbcontentbold"
                        style="width:150px;height:50px;line-height:50px;">项目
                    </td>
                    <td rowspan="2" class="text-center bbcontentbold"
                        style="height:50px;line-height:50px;">计量单位
                    </td>
                    <td colspan="2" class="text-center bbcontentbold">完成工程数量</td>
                    <td colspan="2" class="text-center bbcontentbold">完成金额（万元）</td>
                    <td rowspan="2" class="text-center bbcontentbold"
                        style="height:50px;line-height:50px;">备注
                    </td>
                </tr>
                <tr>
                    <td class="text-center bbcontentbold">本季度</td>
                    <td class="text-center bbcontentbold">自年初累计</td>
                    <td class="text-center bbcontentbold">本季度</td>
                    <td class="text-center bbcontentbold">自年初累计</td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">测量</td>
                    <td class="text-center bbcontent">平方公里</td>
                    <c:forEach items="${mapinfo.cl }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">碍航疏浚</td>
                    <td class="text-center bbcontent">立方米</td>
                    <c:forEach items="${mapinfo.ahsj }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">整治建筑物修复</td>
                    <td class="text-center bbcontent">平方米</td>
                    <c:forEach items="${mapinfo.zzjzwxf }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">维修管理码头(埠头)</td>
                    <td class="text-center bbcontent">座</td>
                    <c:forEach items="${mapinfo.wxglmt }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">航标维护</td>
                    <td class="text-center bbcontent">座/座次</td>
                    <c:forEach items="${mapinfo.hbwh }" var="i">
                        <td class="text-center bbcontent"><c:out
                                value="${i=='/'?'':i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">打捞沉船</td>
                    <td class="text-center bbcontent">艘/吨位</td>
                    <c:forEach items="${mapinfo.dlcc }" var="i">
                        <td class="text-center bbcontent"><c:out
                                value="${i=='/'?'':i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">其他清障</td>
                    <td class="text-center bbcontent">处/吨</td>
                    <c:forEach items="${mapinfo.qtqz }" var="i">
                        <td class="text-center bbcontent"><c:out
                                value="${i=='/'?'':i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">其他维护工程</td>
                    <td class="text-center bbcontent">处</td>
                    <c:forEach items="${mapinfo.qtwxgc }" var="i">
                        <td class="text-center bbcontent"><c:out value="${i}"></c:out></td>
                    </c:forEach>
                    <td></td>
                </tr>
                <tr>
                    <td class="text-center bbcontent">小计</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="text-center bbcontent">${mapinfo.totalmonth }</td>
                    <td class="text-center bbcontent">${mapinfo.totalmutimonth }</td>
                    <td></td>
                </tr>
            </table>
        </div>
    </div>
    <div class="row" style="margin-bottom: 20px;">
        <div class="col-xs-4 pull-left">注:1.内河骨干航道例行养护工作，不计列本表中。</div>
    </div>
</div>
</body>
</html>
