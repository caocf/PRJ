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
                <h2 class="bbtitle">专项养护工程完成情况季（年）报表</h2>
            </center>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-4 pull-left bbcontent">单位：${mapinfo.dpt }</div>
        <div class="col-xs-4 text-right pull-right bbcontent">${mapinfo.selyear }年${mapinfo.selseason }季度</div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <table class="table table-bordered">
                <tr>
                    <td rowspan="2" class="text-center bbcontentbold" style="height:50px;line-height:50px;">工程名称</td>
                    <td rowspan="2" class="text-center bbcontentbold" style="height:50px;line-height:50px;">投资（万元）</td>
                    <td rowspan="2" class="text-center bbcontentbold"
                        style="height:50px;line-height:50px;">${mapinfo.selyear }年工程进度情况
                    </td>
                    <td colspan="2" class="text-center bbcontentbold">完成金额（万元）</td>
                    <td rowspan="2" class="text-center bbcontentbold" style="height:50px;line-height:50px;">备注</td>
                </tr>
                <tr>
                    <td class="text-center bbcontentbold">本季度</td>
                    <td class="text-center bbcontentbold">自年初累计</td>
                </tr>
                <c:forEach items="${zxgc }" var="i" varStatus="name">
                    <tr class='text-center'>
                        <td class="text-center bbcontent"><c:out value="${i.name}"></c:out></td>
                        <td class="text-center bbcontent"><c:out value="${i.investment}"></c:out>
                        </td>
                        <td class="text-center bbcontent"><c:out value="${i.progress}"></c:out>
                        </td>
                        <td class="text-right bbcontent"><c:out value="${i.amount==0.0?'':i.amount}"></c:out></td>
                        <td class="text-right bbcontent"><c:out value="${i.totalamount==0.0?'':i.totalamount}"></c:out>
                        </td>
                        <td class="text-left bbcontent"><c:out value="${i.remark}"></c:out></td>
                    </tr>
                </c:forEach>
                <tr class='text-center'>
                    <td class="text-center bbcontent">合计</td>
                    <td class="text-center bbcontent"><c:out value="${mapinfo.tz==0.0?'':mapinfo.tz}"></c:out>
                    </td>
                    <td class="text-left bbcontent"></td>
                    <td class="text-right bbcontent"></td>
                    <td class="text-right bbcontent"></td>
                    <td class="text-left bbcontent"></td>
                </tr>
            </table>
        </div>
    </div>
    <div class="row" style="margin-bottom: 20px;">
        <div class="col-xs-12 pull-left bbcontent">注:1.凡列入年度预算的专项养护工程，均需填写本表，从前期工作开始填写。</div>
        <div class="col-xs-12 pull-left bbcontent">&nbsp;&nbsp;&nbsp;&nbsp;2.工程进度情况应按主要工程量(如护岸砌筑、陆上土方开挖、水下土方疏浚、管理码头桩基、上部结构等)详细说明，工程完工后其主要工程量均应填写完整。</div>
        <div class="col-xs-12 pull-left bbcontent">&nbsp;&nbsp;&nbsp;&nbsp;3.每年底各市需填写所有专项工程合计完成提高航道等级x公里，新建修复护岸xx米，陆上土方x万方，水下土方xx万方，增设标志标牌x座，新建管理码头x座。</div>
    </div>
</div>
</body>
</html>
