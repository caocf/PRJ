<%@ page import="com.channel.model.user.CXtUser" %>
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
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>综合查询</title>

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
    <link rel="stylesheet" type="text/css" href="page/hdaoinfomap/hdaolist.css">
    <link rel="stylesheet" type="text/css" href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">
    <link rel="stylesheet" type="text/css" href="common/common/css/datatable.css">

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
            src="page/hdaoinfomap/hdaoinfolist.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>

</head>

<body style="min-width:1400px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="gotoflag" value="">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2" style="min-width: 200px;">
            <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
                 class="pull-left" style="margin-top:10px;"
                 onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
            <label class="pull-left">综合查询</label>
        </div>
        <div class="col-xs-7">
            <a class="btn btn-primary" id="tabmap">地图查询</a>

            <div class="btn-group">
                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false" id="btnperm">
                    列表查询<span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li class="active" style="height:35px;">
                        <a style="height:35px;"
                           onclick="window.location.href=$('#basePath').val()+'/page/hdaoinfomap/hdaolist.jsp'">航道</a>
                    </li>
                    <li style="height:35px;">
                        <a style="height:35px;"
                           onclick="gotohduan()">航段</a>
                    </li>
                    <li style="height:35px;">
                        <a style="height:35px;"
                           onclick="window.location.href=$('#basePath').val()+'/page/hdaoinfomap/applist.jsp'">附属物</a>
                    </li>
                </ul>
            </div>

        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>
    <div class="row navcontent shadow border">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12" style="background-color: #dddddd">
                    <p class="nomargin nopadding navcontenttitle">航道信息</p>
                </div>
            </div>
            <div class="row borderbottom" style="padding:10px 0 10px 0;">
                <div class="col-xs-1 text-right">
                    <label style="margin-top:8px;">行政区划</label>
                </div>
                <div class="col-xs-1" id="selxzqhdiv">
                </div>
                <div class="text-right">
                    <label style="float:left;margin-top:8px;">航道类型</label>
                </div>
                <div class="col-xs-1">
                    <select class="form-control" id="selsfgg">
                        <option value="-1">全部航道</option>
                        <option value="1">骨干航道</option>
                        <option value="0">支线航道</option>
                    </select>
                </div>
                <div class="text-right">
                    <label style="float:left;margin-top:8px;">航道名称</label>
                </div>
                <div class="col-xs-2">
                    <input class="form-control" id="hdname" name="hdname">
                </div>
                <div class="col-xs-4">
                    <button class="btn btn-primary" onclick="loadhdaoinfolist()">搜索</button>
                </div>
            </div>

            <div class="row" id="printcontent">
                <div class="row">
                    <%--<div class="col-xs-12">
                            <center>
                                <h2 class="bbtitlenosize" id="bbtitle">航道信息列表<br><br>
                                </h2>
                            </center>
                    </div>--%>
                </div>
                <div id="divtable" class="col-xs-12 nomargin nopadding">
                    <table class="table text-center" border="1px solid #dddddd" style="border: 1px solid #dddddd"
                           cellspacing="0" width="100%" id="dt">
                        <thead>
                        <tr>
                            <th width="6%" rowspan="2" style="text-align: center;vertical-align: middle">
                                航道编号
                            </th>
                            <th width="6%" rowspan="2" style="text-align: center;vertical-align: middle">
                                航道名称
                            </th>
                            <%--<th width="8%" colspan="2" rowspan="2" style="text-align: center;vertical-align: middle">
                                航道起点
                            </th>
                            <th width="8%" colspan="2" rowspan="2" style="text-align: center;vertical-align: middle">
                                航道终点
                            </th>--%>
                            <th width="6%" rowspan="2" style="text-align: center;vertical-align: middle">
                                总里程
                            </th>
                            <th width="6%" rowspan="2" style="text-align: center;vertical-align: middle">
                                重复里程
                            </th>
                            <th width="6%" rowspan="2" style="text-align: center;vertical-align: middle">
                                航段数量
                            </th>
                            <th width="6%" colspan="2">一级航段</th>
                            <th width="6%" colspan="2">二级航段</th>
                            <th width="6%" colspan="2">三级航段</th>
                            <th width="6%" colspan="2">四级航段</th>
                            <th width="6%" colspan="2">五级航段</th>
                            <th width="6%" colspan="2">六级航段</th>
                            <th width="6%" colspan="2">七级航段</th>
                            <th width="6%" colspan="2">准七级航段</th>
                            <th width="4%" colspan="2" rowspan="2" style="text-align: center;vertical-align: middle">
                                详情
                            </th>
                        </tr>
                        <tr>
                            <th width="3%">数量</th>
                            <th width="3%">里程</th>
                            <th width="3%">数量</th>
                            <th width="3%">里程</th>
                            <th width="3%">数量</th>
                            <th width="3%">里程</th>
                            <th width="3%">数量</th>
                            <th width="3%">里程</th>
                            <th width="3%">数量</th>
                            <th width="3%">里程</th>
                            <th width="3%">数量</th>
                            <th width="3%">里程</th>
                            <th width="3%">数量</th>
                            <th width="3%">里程</th>
                            <th width="3%">数量</th>
                            <th width="3%">里程</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modalview" role="dialog">
    <div class="modal-dialog" role="document" style="width: 50%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">详细信息</h4>
            </div>
            <div class="modal-body" style="overflow:auto;background:white;">
                <div class="col-xs-12" id="detailinfodiv"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
