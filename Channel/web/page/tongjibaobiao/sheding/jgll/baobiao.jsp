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

    <title>Page</title>

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

</head>

<body style="background: white;overflow: hidden;font-family: 宋体;">
<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<div class="container-fluid">
    <c:choose>
        <c:when test="${mapinfo.emptyflag==0}">
            <div class="col-xs-12">
                <center>
                    <h3>暂无报表数据</h3>
                </center>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row">
                <div class="col-xs-12">
                    <center>
                        <h2 class="bbtitle">激光流量汇总表<br><br>
                            <small class="bbtitle"></small>
                        </h2>
                    </center>
                </div>
            </div>
            <c:if test="${cbll.name!=null}">
                <div class="row">
                    <div class="col-xs-12 text-right bbcontent">观测点:${cbll.name}</div>
                </div>
            </c:if>
            <div class="row">
                <div class="col-xs-12">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                rowspan="2" width="30%">查询时间
                            </th>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                rowspan="2" width="10%">上下行
                            </th>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                rowspan="2" width="10%">流量总计(艘)
                            </th>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                rowspan="2" width="10%">总吨位(万吨)
                            </th>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                colspan="4" width="40%">其中
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                width="10%">空载(艘)
                            </th>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                width="10%">空载总吨位(万吨)
                            </th>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                width="10%">重载(艘)
                            </th>
                            <th class="text-center bbcontentbold"
                                style="height:30px;line-height:30px;vertical-align: middle"
                                width="10%">重载总吨位(万吨)
                            </th>
                        </tr>

                        </thead>
                        <tbody>
                        <tr>
                            <td class="text-center bbcontent" style="vertical-align: middle"
                                rowspan="3">${mapinfo.starttime}---${mapinfo.endtime}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">上行</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.sxzsl}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.sxzdw}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.sxkzzsl}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.sxkzzdw}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.sxzzzsl}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.sxzzzdw}</td>
                        </tr>
                        <tr>
                            <td class="text-center bbcontent" style="vertical-align: middle">下行</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.xxzsl}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.xxzdw}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.xxkzzsl}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.xxkzzdw}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.xxzzzsl}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.xxzzzdw}</td>
                        </tr>
                        <tr>
                            <td class="text-center bbcontent" style="vertical-align: middle">合计</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.zsl}</td>
                            <td class="text-center bbcontent" style="vertical-align: middle">${cbll.zdw}</td>
                            <td class="text-center bbcontent"
                                style="vertical-align: middle">${cbll.sxkzzsl+cbll.xxkzzsl}</td>
                            <td class="text-center bbcontent"
                                style="vertical-align: middle">${cbll.sxkzzdw+cbll.xxkzzdw}</td>
                            <td class="text-center bbcontent"
                                style="vertical-align: middle">${cbll.sxzzzsl+cbll.xxzzzsl}</td>
                            <td class="text-center bbcontent"
                                style="vertical-align: middle">${cbll.sxzzzdw+cbll.xxzzzdw}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>