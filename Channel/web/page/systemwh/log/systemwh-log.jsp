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

    <title>日志管理</title>

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
    <link rel="stylesheet" type="text/css" href="page/systemwh/log/log.css">
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
            src="page/systemwh/systemwh.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/systemwh/log/log.js"></script>
</head>

<body>

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>
<input type="hidden" id="man_log_perm" value=${ui.hasPerm('MAN_LOG')}>

<div class="container-fluid">
    <jsp:include page="../systemwh-title.jsp"></jsp:include>
    <div class="row navline"></div>

    <div class="row navcontent shadow">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">日志管理</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-6">
                    <c:if test="${ui.hasPerm('MAN_LOG')}">
                        <a class="btn btn-primary" style="margin: 15px 0 15px 0"
                           onclick="delmultilogload();">删除日志</a>
                    </c:if>
                </div>

                <div class="col-xs-6">
                    <div class="row">
                        <div class="col-xs-4 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">记录时间:</h4>
                        </div>
                        <div class="col-xs-8 text-left" id="divtimeregion"
                             style="margin: 15px 0 0 0">
                            <script>
                                $("#divtimeregion").adddayregion({
                                    idstart: 'seldaystart',
                                    hintstart: '请选择开始时间',
                                    hintend: '请选择结束时间',
                                    idend: 'seldayend',
                                    onpickedstart: 'loadlog',
                                    onpickedend: 'loadlog'
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12 nopadding nomargin">
                    <!-- datatable -->
                    <table id="tableLog" class="" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th width="8%"><input id="cbselall" type="checkbox" onchange="selall();">&nbsp;序号</th>
                            <th width="10%">操作人</th>
                            <th width="10%">操作IP</th>
                            <th width="15%">日志模块</th>
                            <th width="8%">日志操作</th>
                            <th width="20%">备注</th>
                            <th width="15%">操作时间</th>
                            <c:if test="${ui.hasPerm('MAN_LOG')}">
                                <th width="10%">操作</th>
                            </c:if>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${ui.hasPerm('MAN_LOG')}">
    <div class="modal fade" id="modaldel" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除日志</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            你确定要删除所选的日志吗？
                        </p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary"
                            onclick="var delidsstr=$('#modaldel').attr('delidsstr');dellogs(delidsstr);">删除
                    </button>
                </div>
            </div>
        </div>
    </div>
</c:if>
</body>
</html>
