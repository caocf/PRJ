<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    int szju = 1;
    int deptxzqh = 1;
    if (szjudpt != null) {
        szju = szjudpt.getId();
        deptxzqh = szjudpt.getXzqh();
    }
    CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.getAttribute("dptshixzqh");
    int szshixzqh = -2;
    if (dptshixzqh != null)
        szshixzqh = dptshixzqh.getId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>航道巡查</title>

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
            src="page/xuncha/xuncha.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/xuncha/xunchatable.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
</head>

<body>
<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="xcid" value="">
<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">
<input type="hidden" id="deptxzqh" value="<%=deptxzqh%>">
<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>
<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2">
            <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
                 class="pull-left" style="margin-top:10px;"
                 onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
            <label class="pull-left">航道巡查</label>
        </div>
        <div class="col-xs-7">
            <c:if test="${ui.hasPerm('VIEW_XCJL')||ui.hasPerm('MAN_XCJL')}">
                <a class="btn btn-primary active" id="tabxuncha">巡查记录</a>
            </c:if>
            <c:if test="${ui.hasPerm('VIEW_YHLX')||ui.hasPerm('MAN_YHLX')}">
                <a class="btn btn-primary" id="tabyanghulianxi">养护联系</a>
            </c:if>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>

    <div class="row navcontent shadow border">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">巡查记录</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-2">
                    <c:if test="${ui.hasPerm('MAN_XCJL')}">
                        <a class="btn btn-primary" style="margin: 15px 0 15px 0"
                           data-toggle="modal" data-target="#modalnew">新建巡查记录</a>
                        <a class="btn btn-default" style="margin: 15px 0 15px 0"
                           onclick="delmulti();">删除</a>
                    </c:if>
                </div>
                <div class="col-xs-4 col-xs-offset-1">
                    <div class="row">
                        <div class="col-xs-4 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">巡查航道:</h4>
                        </div>
                        <div class="col-xs-8 text-left" style="margin: 15px 0 15px 0;max-width:300px;" id="divselhdao">

                        </div>
                    </div>
                </div>

                <div class="col-xs-5">
                    <div class="row">
                        <div class="col-xs-3 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">巡查时间:</h4>
                        </div>
                        <div class="col-xs-9 text-right" id="divtimeregion"
                             style="margin: 15px 0 0 0">
                            <script>
                                $("#divtimeregion").adddayregion({
                                    idstart: 'seldaystart',
                                    hintstart: '请选择开始时间',
                                    hintend: '请选择结束时间',
                                    idend: 'seldayend',
                                    onpickedstart: 'loadxc',
                                    onpickedend: 'loadxc'
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <table id="xctable" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th width="5%" style="text-align: center;"><input
                                    id="cbselall" type="checkbox">&nbsp;序号
                            </th>
                            <th width="10%" style="text-align: center;">航道名称</th>
                            <th width="10%" style="text-align: center;">起讫点</th>
                            <th width="10%" style="text-align: center;">巡查桩号</th>
                            <th width="5%" style="text-align: center;">总体情况</th>
                            <th width="15%" style="text-align: center;">处理部门</th>
                            <th width="20%" style="text-align: center;">巡查时间</th>
                            <th width="15%" style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!--添加巡查记录-->
<div class="modal fade" id="modalnew" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width:1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" style="font-size: 18px;color:#333333;">新建航道巡查现场记录表</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid nomargin nopadding" id="divaddnew">
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            一、巡查概况
                        </div>
                    </div>
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <table class="table table-bordered">
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        航道名称:
                                    </td>
                                    <td style="width:310px;" id="divnewselhdao"></td>
                                    <td style="text-align: right;background: #f0f0f0;width:150px;line-height: 32px;">
                                        总体情况:
                                    </td>
                                    <td style="padding-top:15px;">
                                        <input type="radio" name="ztqk" value="1" style="margin-left:15px;" checked>&nbsp;好
                                        <input type="radio" name="ztqk" value="2" style="margin-left:15px;">&nbsp;较好
                                        <input type="radio" name="ztqk" value="3" style="margin-left:15px;">&nbsp;一般
                                        <input type="radio" name="ztqk" value="4" style="margin-left:15px;">&nbsp;差
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡查时间:
                                    </td>
                                    <td colspan="3" id="selnewtime">
                                        <script>
                                            $("#selnewtime").addtimeregion({
                                                idstart: 'selnewtimestart',
                                                hintstart: '年-月-日-时-分-秒',
                                                idend: 'selnewtimeend',
                                                hintend: '年-月-日-时-分-秒',
                                                validatorsstart: {
                                                    notempty: {
                                                        msg: '请输入开始时间'
                                                    }
                                                },
                                                validatorsend: {
                                                    notempty: {
                                                        msg: '请输入结束时间'
                                                    }
                                                },
                                                autoajaxstart: {name: 'gk.starttime'},
                                                autoajaxend: {name: 'gk.endtime'}
                                            })
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        起讫点:
                                    </td>
                                    <td colspan="3">
                                        <input id="addqd" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入起点" autoajax="true" autoajax-name="gk.qd">
                                        &nbsp;至&nbsp;
                                        <input id="addzd" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入终点" autoajax="true" autoajax-name="gk.zd">
                                        <span id="errspan" style="margin-left:20px;"></span>
                                        <script>
                                            $("#addqd").validateTargetBind({
                                                settings: {posin: "errspan"},
                                                notempty: {msg: "请输入起点"}
                                            });
                                            $("#addzd").validateTargetBind({
                                                settings: {posin: "errspan"},
                                                notempty: {msg: "请输入终点"}
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡查桩号:
                                    </td>
                                    <td colspan="3">
                                        <input id="addqdzh" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入起点桩号" autoajax="true" autoajax-name="gk.qdzh">
                                        &nbsp;至&nbsp;
                                        <input id="addzdzh" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入终点桩号" autoajax="true" autoajax-name="gk.zdzh">
                                        <span id="errspan2" style="margin-left:20px;"></span>
                                        <%--                    <script>
                                                                $("#addqdzh").validateTargetBind({
                                                                    settings: {posin: "errspan2"},
                                                                    notempty: {msg: "请输入起点桩号"}
                                                                });
                                                                $("#addzdzh").validateTargetBind({
                                                                    settings: {posin: "errspan2"},
                                                                    notempty: {msg: "请输入终点桩号"}
                                                                });
                                                            </script>--%>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡航艇号:
                                    </td>
                                    <td>
                                        <input id="addxhth" class="form-control noborder" type="text"
                                               style="width:100%;float:left;"
                                               placeholder="请输入巡航艇号" autoajax="true"
                                               autoajax-name="gk.xhth">
                                    </td>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        参加人员:
                                    </td>
                                    <td>
                                        <input id="addcyr" class="form-control noborder" type="text"
                                               style="width:60%;float:left;"
                                               placeholder="请输入参加人员姓名，不同姓名之间用分号隔开" autoajax="true"
                                               autoajax-name="gk.cyr">
                                        <span id="errspan3" style="margin-left:20px;"></span>
                                        <script>
                                            $("#addxhth").validateTargetBind({
                                                settings: {posin: "errspan3"},
                                                notempty: {msg: "请输入巡航艇号"}
                                            });
                                            $("#addcyr").validateTargetBind({
                                                settings: {posin: "errspan3"},
                                                notempty: {msg: "请输入参与人"}
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        处理部门:
                                    </td>
                                    <td colspan="3">
                                        <div class="col-xs-4" id="deptdiv">
                                            <script>
                                                var szju = $("#szju").val();
                                                if (szju < 1) {
                                                    szju = 1;
                                                }
                                                $("#deptdiv").addseldpt({
                                                    id: 'addxcdept',
                                                    autoajax: {
                                                        name: 'gk.dept'
                                                    },
                                                    validators: {
                                                        notempty: {
                                                            msg: '请输入处理部门'
                                                        }
                                                    }, defaultval: szju
                                                }, szju);
                                            </script>
                                        </div>
                                        <span id="errspan4" style="margin-left:20px;"></span>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            二、巡查情况
                        </div>
                    </div>
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <div>
                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active"><a href="#ha" aria-controls="ha"
                                                                              role="tab" data-toggle="tab">1.护岸情况</a>
                                    </li>
                                    <li role="presentation"><a href="#mt" aria-controls="mt" role="tab"
                                                               data-toggle="tab">2.码头情况</a></li>
                                    <li role="presentation"><a href="#hb" aria-controls="hb" role="tab"
                                                               data-toggle="tab">3.航标情况</a></li>
                                    <li role="presentation"><a href="#lh" aria-controls="lh" role="tab"
                                                               data-toggle="tab">4.绿化情况</a></li>
                                    <li role="presentation"><a href="#ah" aria-controls="ah" role="tab"
                                                               data-toggle="tab">5.碍航情况</a></li>
                                    <li role="presentation"><a href="#wz" aria-controls="wz" role="tab"
                                                               data-toggle="tab">6.违章情况</a></li>
                                    <li role="presentation"><a href="#qt" aria-controls="qt" role="tab"
                                                               data-toggle="tab">7.其它情况</a></li>
                                </ul>
                                <div class="tab-content">
                                    <%--护岸情况--%>
                                    <div role="tabpanel" class="tab-pane active" id="ha">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="hatbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--码头情况--%>
                                    <div role="tabpanel" class="tab-pane" id="mt">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="mttbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--航标情况--%>
                                    <div role="tabpanel" class="tab-pane" id="hb">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="hbtbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--绿化情况--%>
                                    <div role="tabpanel" class="tab-pane" id="lh">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="lhtbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--碍航情况--%>
                                    <div role="tabpanel" class="tab-pane" id="ah">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="ahtbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--违章情况--%>
                                    <div role="tabpanel" class="tab-pane" id="wz">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="wztbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--其它情况--%>
                                    <div role="tabpanel" class="tab-pane" id="qt" cnt="0">
                                        <button class="btn btn-primary" style="margin: 15px;"
                                                onclick="newqt();">新增其它问题类别
                                        </button>
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="qttbody">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            三、附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                            <i class="icon-paper-clip" style="color:rgb(35, 82, 124);"></i>
                            <input type="button" class="btn btn-link" value="添加附件" style="padding-left:0px;"
                                   onclick="addpicture('divaddnew');">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="pull-left text-left">
                <span>
                    <input type="checkbox" style="folat:left;" id="createyhlxd">
                    <label style="float:right;font-size: 14px;color:#333333;padding-top:0px;">&nbsp;保存时同时生成养护联系单</label>
                </span><br>
                    <span><label style="font-size: 12px;color:#999999;">养护联系单可以在<a>养护联系</a>中查看</label></span>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="addnew();">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 删除巡查记录-->
<div class="modal fade" id="modaldel" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">删除巡查记录</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <p>
                        你确定要删除巡查记录&nbsp;<label id="lbmodaldelnames"></label>&nbsp;吗？
                    </p>

                    <p id="pErrMsg" class="text-danger"></p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btndelxc" type="button" class="btn btn-primary">删除</button>
            </div>
        </div>
    </div>
</div>

<!-- 显示巡查记录-->
<div class="modal fade" id="modalview" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width:1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" style="font-size: 18px;color:#333333;">显示航道巡查现场记录表</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid nomargin nopadding" id="divform">
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            一、巡查概况
                        </div>
                    </div>
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <table class="table table-bordered">
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        航道名称:
                                    </td>
                                    <td style="width:310px;" id="tdhdmc"></td>
                                    <td style="text-align: right;background: #f0f0f0;width:150px;line-height: 32px;">
                                        总体情况:
                                    </td>
                                    <td id="tdztqk">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡查时间:
                                    </td>
                                    <td colspan="3" id="tdtime">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        起讫点:
                                    </td>
                                    <td colspan="3" id="tdaddr">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡查桩号:
                                    </td>
                                    <td colspan="3" id="tdaddrzh">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡航艇号:
                                    </td>
                                    <td style="width:310px;" id="tdxhth"></td>
                                    <td style="text-align: right;background: #f0f0f0;width:150px;line-height: 32px;">
                                        参加人员:
                                    </td>
                                    <td id="tdcyr">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        处理部门:
                                    </td>
                                    <td colspan="3" id="tddept">
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            二、巡查情况
                        </div>
                    </div>
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <div>
                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active"><a href="#hav" aria-controls="hav" role="tab"
                                                                              data-toggle="tab">1.护岸情况</a>
                                    </li>
                                    <li role="presentation"><a href="#mtv" aria-controls="mtv" role="tab"
                                                               data-toggle="tab">2.码头情况</a></li>
                                    <li role="presentation"><a href="#hbv" aria-controls="hbv" role="tab"
                                                               data-toggle="tab">3.航标情况</a></li>
                                    <li role="presentation"><a href="#lhv" aria-controls="lhv" role="tab"
                                                               data-toggle="tab">4.绿化情况</a></li>
                                    <li role="presentation"><a href="#ahv" aria-controls="ahv" role="tab"
                                                               data-toggle="tab">5.碍航情况</a></li>
                                    <li role="presentation"><a href="#wzv" aria-controls="wzv" role="tab"
                                                               data-toggle="tab">6.违章情况</a></li>
                                    <li role="presentation"><a href="#qtv" aria-controls="qtv" role="tab"
                                                               data-toggle="tab">7.其它情况</a></li>
                                </ul>
                                <div class="tab-content">
                                    <%--护岸情况--%>
                                    <div role="tabpanel" class="tab-pane active" id="hav">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="havbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--码头情况--%>
                                    <div role="tabpanel" class="tab-pane" id="mtv">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="mtvbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--航标情况--%>
                                    <div role="tabpanel" class="tab-pane" id="hbv">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="hbvbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--绿化情况--%>
                                    <div role="tabpanel" class="tab-pane" id="lhv">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="lhvbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--碍航情况--%>
                                    <div role="tabpanel" class="tab-pane" id="ahv">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="ahvbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--违章情况--%>
                                    <div role="tabpanel" class="tab-pane" id="wzv">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="wzvbody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--其它情况--%>
                                    <div role="tabpanel" class="tab-pane" id="qtv" cnt="0">
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="qtvbody">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div class="col-xs-12" style="background:#f0f0f0;height:40px;line-height: 40px;">
                            附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                            <i class="icon-paper-clip" style="color:rgb(35, 82, 124);"></i>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--更新巡查记录-->
<div class="modal fade" id="modalupdate" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width:1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" style="font-size: 18px;color:#333333;">更新航道巡查现场记录表</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid nomargin nopadding" id="divupdate">
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            一、巡查概况
                        </div>
                    </div>
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <table class="table table-bordered">
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        航道名称:
                                    </td>
                                    <td style="width:310px;" id="divupdateselhdao"></td>
                                    <td style="text-align: right;background: #f0f0f0;width:150px;line-height: 32px;">
                                        总体情况:
                                    </td>
                                    <td style="padding-top:15px;">
                                        <input type="radio" name="updateztqk" value="1" style="margin-left:15px;"
                                               checked>&nbsp;好
                                        <input type="radio" name="updateztqk" value="2" style="margin-left:15px;">&nbsp;较好
                                        <input type="radio" name="updateztqk" value="3" style="margin-left:15px;">&nbsp;一般
                                        <input type="radio" name="updateztqk" value="4" style="margin-left:15px;">&nbsp;差
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡查时间:
                                    </td>
                                    <td colspan="3" id="updateseltime">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        起讫点:
                                    </td>
                                    <td colspan="3">
                                        <input id="updateqd" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入起点" autoajax="true" autoajax-name="updategk.qd">
                                        &nbsp;至&nbsp;
                                        <input id="updatezd" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入终点" autoajax="true" autoajax-name="updategk.zd">
                                        <span id="updateerrspan" style="margin-left:20px;"></span>
                                        <script>
                                            $("#updateqd").validateTargetBind({
                                                settings: {posin: "updateerrspan"},
                                                notempty: {msg: "请输入起点"}
                                            });
                                            $("#updatezd").validateTargetBind({
                                                settings: {posin: "updateerrspan"},
                                                notempty: {msg: "请输入终点"}
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡查桩号:
                                    </td>
                                    <td colspan="3">
                                        <input id="updateqdzh" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入起点桩号" autoajax="true" autoajax-name="updategk.qdzh"
                                               value="">
                                        &nbsp;至&nbsp;
                                        <input id="updatezdzh" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入终点桩号" autoajax="true" autoajax-name="updategk.zdzh"
                                               value="">
                                        <span id="updateerrspan2" style="margin-left:20px;"></span>
                                        <%--                 <script>
                                                             $("#updateqdzh").validateTargetBind({
                                                                 settings: {posin: "updateerrspan2"},
                                                                 notempty: {msg: "请输入起点桩号"}
                                                             });
                                                             $("#updatezdzh").validateTargetBind({
                                                                 settings: {posin: "updateerrspan2"},
                                                                 notempty: {msg: "请输入终点桩号"}
                                                             });
                                                         </script>--%>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        巡航艇号:
                                    </td>
                                    <td>
                                        <input id="updatexhth" class="form-control noborder" type="text"
                                               style="width:100%;float:left;"
                                               placeholder="请输入巡航艇号" autoajax="true"
                                               autoajax-name="updategk.xhth">
                                    </td>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        参加人员:
                                    </td>
                                    <td >
                                        <input id="updatecyr" class="form-control noborder" type="text"
                                               style="width:60%;float:left;"
                                               placeholder="请输入参加人员姓名，不同姓名之间用分号隔开" autoajax="true"
                                               autoajax-name="updategk.cyr">
                                        <span id="updateerrspan3" style="margin-left:20px;"></span>
                                        <script>
                                            $("#updatexhth").validateTargetBind({
                                                settings: {posin: "updateerrspan3"},
                                                notempty: {msg: "请输入巡航艇号"}
                                            });
                                            $("#updatecyr").validateTargetBind({
                                                settings: {posin: "updateerrspan3"},
                                                notempty: {msg: "请输入参与人"}
                                            });
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        处理部门:
                                    </td>
                                    <td colspan="3">
                                        <div class="col-xs-4" id="updatedeptdiv">
                                            <script>
                                                var szju = $("#szju").val();
                                                if (szju < 1) {
                                                    szju = 1;
                                                }
                                                $("#updatedeptdiv").addseldpt({
                                                    id: 'updatexcdept',
                                                    autoajax: {
                                                        name: 'updategk.dept'
                                                    },
                                                    validators: {
                                                        notempty: {
                                                            msg: '请输入处理部门'
                                                        }
                                                    }, defaultval: szju
                                                }, szju);

                                            </script>
                                        </div>
                                        <span id="errspan4" style="margin-left:20px;"></span>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            二、巡查情况
                        </div>
                    </div>
                    <div class="row" style="margin-top: 20px;">
                        <div class="col-xs-12">
                            <div>
                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active"><a href="#hau" aria-controls="hau"
                                                                              role="tab" data-toggle="tab">1.护岸情况</a>
                                    </li>
                                    <li role="presentation"><a href="#mtu" aria-controls="mtu" role="tab"
                                                               data-toggle="tab">2.码头情况</a></li>
                                    <li role="presentation"><a href="#hbu" aria-controls="hbu" role="tab"
                                                               data-toggle="tab">3.航标情况</a></li>
                                    <li role="presentation"><a href="#lhu" aria-controls="lhu" role="tab"
                                                               data-toggle="tab">4.绿化情况</a></li>
                                    <li role="presentation"><a href="#ahu" aria-controls="ahu" role="tab"
                                                               data-toggle="tab">5.碍航情况</a></li>
                                    <li role="presentation"><a href="#wzu" aria-controls="wzu" role="tab"
                                                               data-toggle="tab">6.违章情况</a></li>
                                    <li role="presentation"><a href="#qtu" aria-controls="qtu" role="tab"
                                                               data-toggle="tab">7.其它情况</a></li>
                                </ul>
                                <div class="tab-content">
                                    <%--护岸情况--%>
                                    <div role="tabpanel" class="tab-pane active" id="hau">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="haubody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--码头情况--%>
                                    <div role="tabpanel" class="tab-pane" id="mtu">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="mtubody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--航标情况--%>
                                    <div role="tabpanel" class="tab-pane" id="hbu">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="hbubody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--绿化情况--%>
                                    <div role="tabpanel" class="tab-pane" id="lhu">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="lhubody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--碍航情况--%>
                                    <div role="tabpanel" class="tab-pane" id="ahu">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="ahubody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--违章情况--%>
                                    <div role="tabpanel" class="tab-pane" id="wzu">
                                        <table class="table table-bordered" style="margin: 15px;">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="wzubody">
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--其它情况--%>
                                    <div role="tabpanel" class="tab-pane" id="qtu" cnt="0">
                                        <button class="btn btn-primary" style="margin: 15px;"
                                                onclick="updateqt();">新增其它问题类别
                                        </button>
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">序号</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">巡查问题</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">操作</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">具体位置</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">左/右岸</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">描述</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理意见</th>
                                                <th style="font-size: 14px;color:#333333;text-align: center;">处理结果</th>
                                            </tr>
                                            </thead>
                                            <tbody id="qtubody">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            三、附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                            <i class="icon-paper-clip" style="color:rgb(35, 82, 124);"></i>
                            <input type="button" class="btn btn-link" value="添加附件" style="padding-left:0px;"
                                   onclick="addpicture('divupdate');">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="pull-left text-left">
                    <span><input type="checkbox" style="folat:left;" id="updateyhlxd">
                                  <label style="float:right;font-size: 14px;color:#333333;padding-top:0px;">&nbsp;修改时同时生成养护联系单</label>
                              </span><br>
                    <span><label style="font-size: 12px;color:#999999;">养护联系单可以在<a>养护联系</a>中查看</label></span>
                </div>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="editxc();">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 查看图片 -->
<div class="modal fade" id="modalpicview" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelpicview">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div id="imgviewbody" class="modal-body" style="padding:0 0 0 0;">
            </div>
        </div>
    </div>
</div>
</body>
</html>
