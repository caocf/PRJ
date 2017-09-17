<%@ page import="com.channel.model.user.CXtUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
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
    CXtDpt szjudpt = (CXtDpt) session.getAttribute("dpt");
    int szju = 1;
    int deptxzqh = 1;
    if (szjudpt != null) {
        szju = szjudpt.getId();
        deptxzqh = szjudpt.getXzqh();
    }
    String defaultpagestr = request.getParameter("defaultpage");
    int defaultpage = 1;
    if (defaultpagestr != null) {
        try {
            defaultpage = Integer.parseInt(defaultpagestr);
        } catch (Exception e) {

        }
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>流量观测</title>

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
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">
    <link rel="stylesheet" type="text/css"
          href="common/common/css/datatable.css">
    <link rel="stylesheet" type="text/css"
          href="page/liuliang/liuliangbyship.css">

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
            src="page/liuliang/liuliangbyship.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/unauth.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" src="common/datatable/media/js/jquery.dataTables.min.js"></script>

</head>

<body style="min-width:1300px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="llid" value="">
<input type="hidden" id="deptxzqh" value="<%=deptxzqh%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2">
            <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
                 class="pull-left" style="margin-top:10px;"
                 onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
            <div class="btn-group">
                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false" id="btnperm">
                    流量观测<span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li style="height:35px;">
                        <a style="height:35px;"
                           onclick="window.location.href=$('#basePath').val()+'/page/liuliang/liuliang.jsp'">按天</a>
                    </li>
                    <li style="height:35px;">
                        <a style="height:35px;"
                           onclick="window.location.href=$('#basePath').val()+'/page/liuliang/liuliangbymonth.jsp'">按月</a>
                    </li>
                    <li class="active" style="height:35px;">
                        <a style="height:35px;"
                           onclick="window.location.href=$('#basePath').val()+'/page/liuliang/liuliang.jsp'">按船舶</a>
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
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">按船舶流量观测</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-2">
                    <a class="btn btn-primary" style="margin: 15px 0 15px 0"
                       data-toggle="modal" data-target="#modalnew">新建记录</a>
                    <a class="btn btn-default" style="margin: 15px 0 15px 0"
                       onclick="delmulti();">删除</a>
                </div>
                <div class="col-xs-6">
                    <div class="row">
                        <div class="col-xs-4 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">观测站:</h4>
                        </div>
                        <div class="col-xs-4 text-center" style="margin: 15px 0 0 0;max-width:300px;padding-right: 0px;"
                             id="divselhdao">
                        </div>
                        <div class="col-xs-4 text-center" id="divselgcz"
                             style="margin: 15px 0 0 0;max-width:300px;padding-left: 0px;">
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="row">
                        <div class="col-xs-3 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">观测日期:</h4>
                        </div>
                        <div class="col-xs-9 text-right" id="divtimeregion"
                             style="margin: 15px 0 0 0">
                            <script>
                                $("#divtimeregion").adddayregion({
                                    idstart: 'seldaystart',
                                    hintstart: '请选择开始时间',
                                    hintend: '请选择结束时间',
                                    idend: 'seldayend',
                                    onpickedstart: 'loadliuliang',
                                    onpickedend: 'loadliuliang'
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <table id="tableRecords" cellspacing="0" width="100%"
                           page="<%=defaultpage%>">
                        <thead>
                        <tr>
                            <th style="text-align: center;"><input
                                    id="cbselall" type="checkbox">&nbsp;序号
                            </th>
                            <th style="text-align: center;">观测点</th>
                            <th style="text-align: center;">船舶名称</th>
                            <th style="text-align: center;">船舶方向</th>
                            <th style="text-align: center;">船舶类型</th>
                            <th style="text-align: center;">船舶吨位</th>
                            <th style="text-align: center;">是否空载</th>
                            <th style="text-align: center;">载货种类</th>
                            <th style="text-align: center;">货物运量</th>
                            <th style="text-align: center;">观测时间</th>
                            <th style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modalnew" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新建观测记录</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" id="formnew">
                        <div class="row">
                            <table class="table" style="margin-top: 10px;">
                                <tr>
                                    <td class="text-right" style="width:20%;">观测点</td>
                                    <td class="text-left">
                                        <div class="pull-left" style="width:50%;padding-right: 0;"
                                             id="divnewselhdao">
                                        </div>
                                        <div class="pull-left" style="width:50%;padding-left: 0;"
                                             id="divnewselgcz">
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">观测时间</td>
                                    <td class="text-left">
                                        <div class="" style="" id="divnewtimeregion">
                                            <script>
                                                var now = getTimeFmt(new Date(), 'yyyy-MM-dd')
                                                $("#divnewtimeregion").addday({
                                                    id: 'seldaynew',
                                                    hint: '请选择时间',
                                                    validators: {notempty: {msg: "请选择观测日期"}},
                                                    defaultval: now
                                                });
                                            </script>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶名称</td>
                                    <td class="text-left"><input class="form-control" type="text" autoajax="true"
                                                                 autoajax-name="cbllgc.shipname" id="addshipname"></td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶方向</td>
                                    <td class="text-left">
                                        <select class="form-control" id="addfx">
                                            <option value="1">上行</option>
                                            <option value="2">下行</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶类型</td>
                                    <td class="text-left">
                                        <select class="form-control" id="selcblx">
                                            <script>
                                                ajax('dic/querydicattr', {
                                                    loginid: $("#userid").val(),
                                                    name: "cblx"
                                                }, function (data) {
                                                    if (ifResultOK(data)) {
                                                        var records = getResultRecords(data);
                                                        $("#selcblx").empty();
                                                        if (records) {
                                                            for (var i = 0; i < records.data.length; i++) {
                                                                var dict = records.data[i];
                                                                $("#selcblx").append(
                                                                        '<option value="' + dict.id + '">'
                                                                        + dict.attrdesc + '</option>');
                                                            }
                                                        }
                                                    }
                                                });
                                            </script>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶吨位</td>
                                    <td class="text-left">
                                        <input type="text" class="form-control" autoajax="true"
                                               autoajax-name="cbllgc.shipton" id="addshipton">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-right">是否空载</td>
                                    <td class="text-left">

                                        <select class="form-control" id="addshipempty">
                                            <option value="1">是</option>
                                            <option value="2">否</option>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-right">载货种类</td>
                                    <td class="text-left">
                                        <select class="form-control" id="selzhzl">
                                            <script>
                                                ajax('dic/querydicattr', {
                                                    loginid: $("#userid").val(),
                                                    name: "zhzl"
                                                }, function (data) {
                                                    if (ifResultOK(data)) {
                                                        var records = getResultRecords(data);
                                                        $("#selzhzl").empty();
                                                        if (records) {
                                                            for (var i = 0; i < records.data.length; i++) {
                                                                var dict = records.data[i];
                                                                $("#selzhzl").append(
                                                                        '<option value="' + dict.id + '">'
                                                                        + dict.attrdesc + '</option>');
                                                            }
                                                        }
                                                    }
                                                });
                                            </script>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">货物运量</td>
                                    <td class="text-left">
                                        <input type="text" class="form-control" autoajax="true"
                                               autoajax-name="cbllgc.goodston" id="addgoodston">
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="addnew();">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modalupdate" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">更新观测记录</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" id="formupdate">
                        <div class="row">
                            <table class="table" style="margin-top: 10px;">
                                <tr>
                                    <td class="text-right" style="width:20%;">观测点</td>
                                    <td class="text-left">
                                        <div class="pull-left" style="width:50%;padding-right: 0;"
                                             id="updatehdao">
                                        </div>
                                        <div class="pull-left" style="width:50%;padding-left: 0;"
                                             id="updategcd">
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">观测时间</td>
                                    <td class="text-left">
                                        <div class="" style="" id="updatetimeregion">
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶名称</td>
                                    <td class="text-left"><input class="form-control" type="text" autoajax="true"
                                                                 autoajax-name="cbllgc.shipname" id="updateshipname">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶方向</td>
                                    <td class="text-left">
                                        <select class="form-control" id="updatefx">
                                            <option value="1">上行</option>
                                            <option value="2">下行</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶类型</td>
                                    <td class="text-left">
                                        <select class="form-control" id="updateshiptype">
                                            <script>
                                                ajax('dic/querydicattr', {
                                                    loginid: $("#userid").val(),
                                                    name: "cblx"
                                                }, function (data) {
                                                    if (ifResultOK(data)) {
                                                        var records = getResultRecords(data);
                                                        $("#updateshiptype").empty();
                                                        if (records) {
                                                            for (var i = 0; i < records.data.length; i++) {
                                                                var dict = records.data[i];
                                                                $("#updateshiptype").append(
                                                                        '<option value="' + dict.id + '">'
                                                                        + dict.attrdesc + '</option>');
                                                            }
                                                        }
                                                    }
                                                });
                                            </script>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶吨位</td>
                                    <td class="text-left">
                                        <input type="text" class="form-control" autoajax="true"
                                               autoajax-name="cbllgc.shipton" id="updateshipton">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-right">是否空载</td>
                                    <td class="text-left">
                                        <select class="form-control" id="updateshipempty">
                                            <option value="1">是</option>
                                            <option value="2">否</option>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-right">载货种类</td>
                                    <td class="text-left">
                                        <select class="form-control" id="updategoodstype">
                                            <script>
                                                ajax('dic/querydicattr', {
                                                    loginid: $("#userid").val(),
                                                    name: "zhzl"
                                                }, function (data) {
                                                    if (ifResultOK(data)) {
                                                        var records = getResultRecords(data);
                                                        $("#updategoodstype").empty();
                                                        if (records) {
                                                            for (var i = 0; i < records.data.length; i++) {
                                                                var dict = records.data[i];
                                                                $("#updategoodstype").append(
                                                                        '<option value="' + dict.id + '">'
                                                                        + dict.attrdesc + '</option>');
                                                            }
                                                        }
                                                    }
                                                });
                                            </script>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">货物运量</td>
                                    <td class="text-left">
                                        <input type="text" class="form-control" autoajax="true"
                                               autoajax-name="cbllgc.goodston" id="updategoodston">
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="update();">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modaldelete" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除航道流量观测</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            你确定要删除该观测流量吗？
                        </p>

                        <p id="pErrMsg" class="text-danger"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btndelll" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="modalview" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">显示观测记录</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" id="formview">
                        <div class="row">
                            <table class="table" style="margin-top: 10px;border-bottom: hidden">
                                <tr>
                                    <td class="text-right" style="width:20%;">观测点</td>
                                    <td class="text-left">
                                        <div class="pull-left" style="width:50%;padding-right: 0;"
                                             id="viewgcd">
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">观测时间</td>
                                    <td class="text-left">
                                        <div class="" style="" id="viewtimeregion">
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶名称</td>
                                    <td class="text-left" id="viewshipname"></td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶方向</td>
                                    <td class="text-left" id="viewfx">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶类型</td>
                                    <td class="text-left" id="viewshiptype">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">船舶吨位</td>
                                    <td class="text-left" id="viewshipton">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-right">是否空载</td>
                                    <td class="text-left" id="viewshipempty">
                                    </td>
                                </tr>

                                <tr>
                                    <td class="text-right">载货种类</td>
                                    <td class="text-left" id="viewgoodstype">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-right">货物运量</td>
                                    <td class="text-left" id="viewgoodston">
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
