<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
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
    int deptxzqh=1;
    if (szjudpt != null){
        szju = szjudpt.getId();
        deptxzqh=szjudpt.getXzqh();
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

    <title>养护联系单</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>
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
    <link rel="stylesheet" type="text/css"
          href="page/xuncha/cruise.css">

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
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/xuncha/yanghulianxi.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/xuncha/xunchatable.js"></script>
</head>

<body>
<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="yhlxdid" value="">
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
                <a class="btn btn-primary" id="tabxuncha">巡查记录</a>
            </c:if>
            <c:if test="${ui.hasPerm('VIEW_XCJL')||ui.hasPerm('MAN_XCJL')}">
                <a class="btn btn-primary active" id="tabyanghulianxi">养护联系</a>
            </c:if>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>

    <div class="row navcontent shadow border">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">养护联系</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-1">
                    <c:if test="${ui.hasPerm('MAN_XCJL')}">
                        <a class="btn btn-default" style="margin: 15px 0 15px 0"
                           onclick="delmulti();">删除</a>
                    </c:if>
                </div>
                <div class="col-xs-3 col-xs-offset-1">
                    <div class="row">
                        <div class="col-xs-3 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">航道名称:</h4>
                        </div>
                        <div class="col-xs-6 text-left" style="margin: 15px 0 15px 0;max-width:300px;" id="divselhdao">

                        </div>
                    </div>
                </div>

                <div class="col-xs-4">
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
                                    onpickedstart: 'loadyh',
                                    onpickedend: 'loadyh'
                                });
                            </script>
                        </div>
                    </div>
                </div>

                <div class="col-xs-2">
                    <div class="input-group" style="margin: 15px 0 0 0">
                        <!-- /btn-group -->
                        <input type="text" class="form-control" aria-label="..." placeholder="请输入起点或者终点"
                               id="searchContent">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="btnsearch" onclick="loadyh();">搜索
                            </button>
                        </span>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="col-xs-12">
                    <table id="yhtable" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th width="5%" style="text-align: center;"><input
                                    id="cbselall" type="checkbox">&nbsp;序号
                            </th>
                            <th width="10%" style="text-align: center;">航道名称</th>
                            <th width="10%" style="text-align: center;">起讫点</th>
                            <th width="25%" style="text-align: center;">巡查时间</th>
                            <th width="25%" style="text-align: center;">巡查报告部门</th>
                            <th width="10%" style="text-align: center;">养护联系单生成时间</th>
                            <th width="15%" style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 查看养护联系单 -->
<div class="modal fade" id="modalview" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">查看养护联系单</h4>
            </div>
            <div class="modal-body" id="printcontent">
                <div class="container-fluid form-horizontal">
                    <div>
                        <table class="table" id="tableview" cellspacing="0" width="100%">
                        </table>
                    </div>
                    <div class="row" id="yhfjdivrow">
                        <div class="col-xs-12" style="background:#f0f0f0;height:40px;line-height: 40px;" id="yhfjdiv">
                            附件信息
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="exportyh()">导出</button>
                <button type="button" class="btn btn-primary" onclick="printyh()">打印
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- 删除养护联系单-->
<div class="modal fade" id="modaldel" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">删除养护联系单</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <p>
                        你确定要删除养护联系单&nbsp;<label id="lbmodaldelnames"></label>&nbsp;吗？
                    </p>

                    <p id="pErrMsg" class="text-danger"></p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btndelyh" type="button" class="btn btn-primary">删除</button>
            </div>
        </div>
    </div>
</div>
<!--更新养护联系单-->
<div class="modal fade" id="modalupdate" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width:1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" style="font-size: 18px;color:#333333;">更新养护联系单</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid nomargin nopadding" id="divupdate">
                    <div class="row">
                        <div class="col-xs-12"
                             style="background: #f0f0f0;color:#666666;height:40px;line-height: 40px;font-size: 14px;font-weight: bold;">
                            一、养护概况
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
                                               placeholder="请输入起点" autoajax="true" autoajax-name="updateyhgk.qd">
                                        &nbsp;至&nbsp;
                                        <input id="updatezd" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入终点" autoajax="true" autoajax-name="updateyhgk.zd">
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
                                               placeholder="请输入起点桩号" autoajax="true" autoajax-name="updateyhgk.qdzh">
                                        &nbsp;至&nbsp;
                                        <input id="updatezdzh" class="form-control noborder" type="text"
                                               style="display:inline;width:30%;"
                                               placeholder="请输入终点桩号" autoajax="true" autoajax-name="updateyhgk.zdzh">
                                        <span id="updateerrspan2" style="margin-left:20px;"></span>
                          <%--              <script>
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
                                               style="width:60%;float:left;"
                                               placeholder="请输入巡航艇号" autoajax="true"
                                               autoajax-name="updateyhgk.xhth">
                                    </td>
                                    <td style="text-align: right;background: #f0f0f0;height:33px;line-height: 33px;width:150px;font-size: 14px;font-color:#666666;">
                                        参加人员:
                                    </td>
                                    <td>
                                        <input id="updatecyr" class="form-control noborder" type="text"
                                               style="width:60%;float:left;"
                                               placeholder="请输入参加人员姓名，不同姓名之间用分号隔开" autoajax="true"
                                               autoajax-name="updateyhgk.cyr">
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
                                                        name: 'updateyhgk.dept'
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
                            二、养护情况
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
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="edityh();">保存</button>
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
