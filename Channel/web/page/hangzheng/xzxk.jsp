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

    <title>行政许可</title>

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
    <link rel="stylesheet" type="text/css"
          href="common/common/css/datatable.css">
    <link rel="stylesheet" type="text/css"
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hangzheng/xzxk.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/wdatepicker/WdatePicker.js"></script>

    <style>
        .tableview td {
            font-size: 14px;;
        }
    </style>
</head>

<body style="min-width:1400px;">
<jsp:useBean id="pui" class="com.channel.permission.UI" scope="page"></jsp:useBean>
<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2">
            <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
                 class="pull-left" style="margin-top:10px;"
                 onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
            <label class="pull-left">航政管理</label>
        </div>
        <div class="col-xs-7">
            <c:if test="${pui.hasPerm('VIEW_XZXK')||pui.hasPerm('MAN_XZXK')}">
                <a class="btn btn-primary active" id="tabxzxk">行政许可</a>
            </c:if>
            <c:if test="${pui.hasPerm('VIEW_XZCF')||pui.hasPerm('MAN_XZCF')}">
                <a class="btn btn-primary" id="tabxzcf">行政处罚</a>
            </c:if>
            <c:if test="${pui.hasPerm('VIEW_THLZ')||pui.hasPerm('MAN_THLZ')}">
                <a class="btn btn-primary" id="tabthlz">通航论证</a>
            </c:if>
            <c:if test="${pui.hasPerm('VIEW_CZPC')||pui.hasPerm('MAN_CZPC')}">
                <a class="btn btn-primary" id="tabczpc">重置赔偿</a>
            </c:if>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>

    <div class="row navcontent shadow">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">行政许可</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-5">
                    <div class="col-xs-6">
                        <div class="row">
                            <div class="col-xs-5 text-right">
                                <h4 style="margin-top:22px;font-size:16px;">项目类型:</h4>
                            </div>
                            <div class="col-xs-7 text-left" style="margin: 15px 0 15px 0"
                                 id="divselxmlx">
                                <script>
                                    $("#divselxmlx").addselect({
                                        id: 'selxmlx',
                                        data: {
                                            1: '桥梁',
                                            2: '架空缆线',
                                            3: '水下管线',
                                            4: '隧道',
                                            5: '取排水',
                                            6: '闸坝'
                                        }
                                    });
                                    $("#selxmlx").change(function () {
                                        loadxzxk();
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6">
                        <div class="row">
                            <div class="col-xs-6 text-right">
                                <h4 style="margin-top:22px;font-size:16px;">建筑物性质:</h4>
                            </div>
                            <div class="col-xs-6 text-left" id="divseljzwxz"
                                 style="margin: 15px 0 0 0">
                                <script>
                                    $("#divseljzwxz").addselect({
                                        id: 'seljzwxz',
                                        data: {
                                            1: '新建',
                                            2: '改扩建'
                                        }
                                    });
                                    $("#seljzwxz").change(function () {
                                        loadxzxk();
                                    });
                                </script>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="col-xs-4">
                    <div class="row">
                        <div class="col-xs-3 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">许可日期:</h4>
                        </div>
                        <div class="col-xs-9 text-left" id="divtimeregion"
                             style="margin: 15px 0 0 0">
                            <script>
                                $("#divtimeregion").adddayregion({
                                    idstart: 'seldaystart',
                                    hintstart: '请选择开始时间',
                                    hintend: '请选择结束时间',
                                    idend: 'seldayend',
                                    onpickedstart: 'loadxzxk',
                                    onpickedend: 'loadxzxk'
                                });
                            </script>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="input-group" style="margin: 15px 0 0 0">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false" id="selsearchtype" searchtype="0"><label
                                    class="nomargin" id="selsearchlabel">请选择</label><span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li>
                                    <a onclick="$('#selsearchtype').attr('searchtype',1);$('#selsearchlabel').text($(this).text())">项目申请单位</a>
                                </li>
                                <li>
                                    <a onclick="$('#selsearchtype').attr('searchtype',2);$('#selsearchlabel').text($(this).text())">许可编号</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /btn-group -->
                        <input type="text" class="form-control" aria-label="..." id="inputcontent">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="btnsearch" onclick="loadxzxk();">搜索
                            </button>
                        </span>
                    </div>
                    <!-- /input-group -->
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <table id="xzxktable" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th style="text-align: center;">序号</th>
                            <th style="text-align: center;">许可编号</th>
                            <th style="text-align: center;">项目名称</th>
                            <th style="text-align: center;">项目申请单位</th>
                            <th style="text-align: center;">项目类型</th>
                            <th style="text-align: center;">建筑物类型</th>
                            <th style="text-align: center;">许可日期</th>
                            <th style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modalview" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">查看行政许可详情</h4>
                </div>
                <div class="modal-body" id="modalviewbody">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
