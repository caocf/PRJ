<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
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
    <title>流量预测</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- 页面js -->
    <script src="../js/smart/llyc.js" type="text/javascript"></script>
    <script type="text/javascript" src="../js/common/echarts-all.js"></script>
    <style type="text/css">
        .clickword {
            color: rgb(31, 116, 180);
            cursor: pointer;
        }
    </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header">
    <h1>
        流量预测
        <!-- <small>advanced tables</small> -->
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-cog"></i> 智慧监管</a></li>
        <li><a href="#">监测预警</a></li>
        <li><a href="#">流量预测</a></li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter"
                     style="text-align:left;margin-top: 5px;margin-left: 5px">
                    <select id='areaselect' class="form-control" onchange="gettb()"
                            style="float: left;width:100px;margin-left: 10px;height:30px;line-height: 30px;padding-top:0;padding-bottom:0;">
                    </select>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content no-margin"
             style="margin-left:8px;height:600px;padding:20px;">
            <div style="width: 100%;height:100%;" id="chartsdiv"></div>
        </div>
    </div>
</section>
</body>
</html>
