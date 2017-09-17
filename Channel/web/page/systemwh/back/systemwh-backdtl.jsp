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

    <title>备份还原</title>

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
    <link rel="stylesheet" type="text/css" href="page/systemwh/back/back.css">
    <link rel="stylesheet" type="text/css"
          href="common/common/css/datatable.css">

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
            src="page/systemwh/systemwh.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/systemwh/back/backdtl.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
</head>

<body>

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="scheduleid" value="<%=request.getParameter("id")%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <jsp:include page="../systemwh-title.jsp"></jsp:include>
    <div class="row navline"></div>

    <div class="row">
        <ol class="breadcrumb" style="margin: 5px 5px 0 5px;">
            <li><a href="page/systemwh/back/systemwh-back.jsp">数据备份与还原</a></li>
            <li class="active">查看备份计划</li>
        </ol>
    </div>
    <div class="row shadow" style="background:white;margin: 0px 5px 0 5px;">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">备份信息</p>
                </div>
            </div>
            <br>

            <div class="row">
                <div class="col-xs-6">
                    <label class="text-primary">&nbsp;&nbsp;&nbsp;当前状态: &nbsp;&nbsp;&nbsp;</label>
                    <label class="text-primary" id="currstatus"></label>
                    <c:if test="${ui.hasPerm('MAN_BACK')}">
                        <button class="btn btn-primary btn-sm hide" id="btnenable" onclick="enableschedule();">启用计划
                        </button>
                        <button class="btn btn-primary btn-sm hide" id="btndisable" onclick="disableschedule();">禁用计划
                        </button>
                        <button class="btn btn-warning btn-sm " onclick="backup();" id="btnbackup">立即备份</button>
                    </c:if>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备份名: &nbsp;&nbsp;&nbsp;</label>
                    <label class="text-muted" id="backname"></label>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <label>&nbsp;&nbsp;&nbsp;备份内容: &nbsp;&nbsp;&nbsp;</label>
                    <label class="text-muted" id="backcontent"></label>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <label>&nbsp;&nbsp;&nbsp;备份计划: &nbsp;&nbsp;&nbsp;</label>
                    <label class="text-muted" id="backschedule"></label>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <label>下一次备份: &nbsp;&nbsp;&nbsp;</label>
                    <label class="text-muted" id="nextbacktime"></label>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <label>上一次备份: &nbsp;&nbsp;&nbsp;</label>
                    <label class="text-muted" id="prebacktime"></label>
                </div>
            </div>
            <c:if test="${ui.hasPerm('MAN_BACK')}">
                <div class="row">
                    <div class="col-xs-6">
                        <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <a class="btn btn-link" onclick="showupdateload();">更改备份计划</a>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    <br>

    <div class="row shadow" style="background:white;margin: 0px 5px 10px 5px;">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">备份记录</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <table id="tablehistory" class="" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th width="10%">序号</th>
                            <th width="20%">备份时间</th>
                            <th width="20%">备份文件名</th>
                            <th width="10%">备份文件大小</th>
                            <th width="10%">备份文件状态</th>
                            <th width="30%">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="modaldel" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除备份记录</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-12">
                                <label>您确定要删除该备份记录&nbsp;<label id="delname"></label>&nbsp;吗？</label>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="deletehistory();">删除</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="modalrestore" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">还原</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-12">
                                <label>您确定要用该备份记录&nbsp;<label id="restorename"></label>&nbsp;还原数据库吗？</label>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="restorehistory();">还原</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="modalupdate" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">修改备份计划</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" id="bodystep1">
                        <div class="row">
                            <div class="col-xs-12" style="color:#428bca;">请输入备份名</div>
                        </div>
                        <div class="row" style="margin-top:5px;">
                            <div class="col-xs-12"><input type="text form-control" id="updatebackname"></div>
                        </div>
                        <div class="row" style="margin-top:5px;">
                            <div class="col-xs-12" style="color:#428bca;">请选择要备份的数据</div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12" style="height:400px;overflow-y: scroll;">
                                <table class="table" id="seltable">
                                    <thead>
                                    <tr>
                                        <th style="width:20%;"><input type="checkbox" id="seltableall"
                                                                      onclick="selalltable();">序号
                                        </th>
                                        <th>表名</th>
                                        <th>描述</th>
                                        <th>数据条数</th>
                                    </tr>
                                    </thead>
                                    <tbody id="seltables">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="container-fluid" id="bodystep2">
                        <div class="row">
                            <div class="col-xs-12"><label style="color:#428bca;">您希望多久备份一次</label>
                                <small>根据您设置的计划，系统将会自动进行备份</small>
                            </div>
                        </div>
                        <br>


                        <div class="row">
                            <div class="col-xs-2 text-right" style="margin-top: 5px;">
                                频率:
                            </div>
                            <div class="col-xs-3 text-left" style="color:#428bca;">
                                <select class="form-control" id="backtype" onchange="showscheduletype();">
                                    <option value="1">每月</option>
                                    <option value="2">每周</option>
                                    <option value="3">每天</option>
                                    <option value="4">每隔</option>
                                </select>
                            </div>
                        </div>
                        <br>

                        <div class="row" id="rowmonth">
                            <div class="col-xs-2 text-right" style="margin-top: 5px;">
                                哪几天：
                            </div>
                            <div class="col-xs-6 text-left" id="1212asdf">
                                <script>
                                    for (var i = 1; i <= 31; i++) {
                                        $("#1212asdf").append('<div class="col-xs-2"><input type="checkbox" style="float:left" value="' + i + '"><label style="float:left">' + i + "</label></div>");
                                    }
                                </script>
                            </div>
                        </div>

                        <div class="row" id="rowweek">
                            <div class="col-xs-2 text-right" style="margin-top: 5px;">
                                星期几：
                            </div>
                            <div class="col-xs-6 text-left" id="xxxxxx">
                                <script>
                                    $("#xxxxxx").append('<div class="col-xs-4"><input type="checkbox" style="float:left" value="' + 1 + '"><label style="float:left">' + '星期一' + "</label></div>");
                                    $("#xxxxxx").append('<div class="col-xs-4"><input type="checkbox" style="float:left" value="' + 2 + '"><label style="float:left">' + '星期二' + "</label></div>");
                                    $("#xxxxxx").append('<div class="col-xs-4"><input type="checkbox" style="float:left" value="' + 3 + '"><label style="float:left">' + '星期三' + "</label></div>");
                                    $("#xxxxxx").append('<div class="col-xs-4"><input type="checkbox" style="float:left" value="' + 4 + '"><label style="float:left">' + '星期四' + "</label></div>");
                                    $("#xxxxxx").append('<div class="col-xs-4"><input type="checkbox" style="float:left" value="' + 5 + '"><label style="float:left">' + '星期五' + "</label></div>");
                                    $("#xxxxxx").append('<div class="col-xs-4"><input type="checkbox" style="float:left" value="' + 6 + '"><label style="float:left">' + '星期六' + "</label></div>");
                                    $("#xxxxxx").append('<div class="col-xs-4"><input type="checkbox" style="float:left" value="' + 7 + '"><label style="float:left">' + '星期七' + "</label></div>");
                                </script>
                            </div>
                        </div>

                        <div class="row" id="rowtimeregion">
                            <div class="col-xs-2 text-right" style="margin-top: 5px;">
                                间隔时间：
                            </div>
                            <div class="col-xs-3 text-left">
                                <div class="input-group">
                                    <input type="text" class="form-control" aria-describedby="basic-addon2"
                                           id="timeregion">
                                    <span class="input-group-addon" id="basic-addon2">秒</span>
                                </div>
                            </div>
                        </div>

                        <br>

                        <div class="row" id="rowtime">
                            <div class="col-xs-2 text-right" style="margin-top: 5px;">
                                时间：
                            </div>
                            <div class="col-xs-3 text-left" id="asfx">
                                <script>
                                    $('#asfx').addonlytime({
                                        id: 'seltime'
                                    })
                                    ;
                                </script>
                            </div>
                        </div>

                        <div class="row" id="rowdaytime">
                            <div class="col-xs-2 text-right" style="margin-top: 5px;">
                                开始时间：
                            </div>
                            <div class="col-xs-4 text-left" id="asfxxx">
                                <script>
                                    $('#asfxxx').addtime({
                                        id: 'seldaytime',
                                        defaultval: getTimeFmt(new Date())
                                    })
                                    ;
                                </script>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer" id="footstep1">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="showupdatestep2();">下一步</button>
                </div>
                <div class="modal-footer" id="footstep2">
                    <button type="button" class="btn btn-primary" onclick="showupdatestep1();">上一步</button>
                    <button type="button" class="btn btn-default" onclick="updatebackschedule();">修改计划</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
