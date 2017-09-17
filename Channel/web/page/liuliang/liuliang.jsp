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
          href="page/liuliang/liuliang.css">

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
            src="page/liuliang/liuliang.js"></script>
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
                    <li class="active" style="height:35px;">
                        <a style="height:35px;"
                           onclick="window.location.href=$('#basePath').val()+'/page/liuliang/liuliang.jsp'">按天</a>
                    </li>
                    <li style="height:35px;">
                        <a style="height:35px;"
                           onclick="window.location.href=$('#basePath').val()+'/page/liuliang/liuliangbymonth.jsp'">按月</a>
                    </li>
                    <li style="height:35px;">
                        <a style="height:35px;"
                           onclick="window.location.href=$('#basePath').val()+'/page/liuliang/liuliangbyship.jsp'">按船舶</a>
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
                    <p class="nomargin nopadding navcontenttitle">按天流量观测</p>
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
                            <th width="5%" rowspan="2" class="borderright" style="text-align: center;"><input
                                    id="cbselall" type="checkbox">&nbsp;序号
                            </th>
                            <th width="10%" rowspan="2" class="borderright" style="text-align: center;">观测点</th>
                            <th width="10%" rowspan="2" class="borderright" style="text-align: center;">观测类型</th>
                            <th width="10%" rowspan="2" class="borderright" style="text-align: center;">观测日期</th>
                            <th width="25%" colspan="3" class="borderright borderbottom" style="text-align: center;">
                                上行
                            </th>
                            <th width="25%" colspan="3" class="borderright borderbottom" style="text-align: center;">
                                下行
                            </th>
                            <th width="15%" rowspan="2" style="text-align: center;">操作</th>
                        </tr>
                        <tr>
                            <th class="borderright" style="text-align: center;">船舶艘数(艘次)</th>
                            <th class="borderright" style="text-align: center;">船舶吨位(吨)</th>
                            <th class="borderright" style="text-align: center;">货物运量(吨)</th>

                            <th class="borderright" style="text-align: center;">船舶艘数(艘次)</th>
                            <th class="borderright" style="text-align: center;">船舶吨位(吨)</th>
                            <th class="borderright" style="text-align: center;">货物运量(吨)</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal -->
    <div class="modal fade" id="modalnew" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document" style="width:1000px;">
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
                            <div class="col-xs-1 text-right" style="width:10%;"><h4
                                    style="margin-top:5px;font-size:16px;">观测点</h4>
                            </div>
                            <div class="col-xs-3 text-center" style="width:20%;padding-right: 0;" id="divnewselhdao">
                            </div>
                            <div class="col-xs-3 text-center" style="width:20%;padding-left: 0;" id="divnewselgcz">
                            </div>
                            <div class="col-xs-1 text-right" style="width:10%;"><h4
                                    style="margin-top:5px;font-size:16px;">观测日期</h4></div>
                            <div class="col-xs-3 text-left" style="width:25%;" id="divnewtimeregion">
                                <script>
                                    var now = getTimeFmt(new Date(), 'yyyy-MM-dd')
                                    $("#divnewtimeregion").addday({
                                        id: 'seldaynew',
                                        hint: '请选择日期',
                                        validators: {notempty: {msg: "请选择观测日期"}},
                                        defaultval: now
                                    })
                                    ;
                                </script>
                            </div>
                        </div>

                        <div class="row">
                            <table class="table bordertop" style="margin-top: 10px;">
                                <thead>
                                <tr>
                                    <th width="320px;" height="40px" colspan="2"
                                        class="text-center borderright borderleft"
                                        style="padding:0 0 0 0;">
                                        <div class="corn" style="width:100%;height:100%;">
                                            <label style="position: relative;left:-75px;top:16px;font-weight: bold;">统计指标</label>
                                            <label style="position: relative;left:30px;top:0px;font-weight: bold;">方向</label>
                                        </div>
                                    </th>
                                    <th width="30%" class="borderright text-center">上行</th>
                                    <th width="30%" class="borderright text-center">下行</th>
                                </tr>
                                </thead>
                                <tbody>
                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderright text-center" rowspan="5"
                                        style="line-height: 180px;">船舶艘数(艘次)
                                    </td>
                                    <td class="borderright text-center">拖轮</td>
                                    <td class="borderright text-center">
                                        <input id="add1" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.uptlnum" onchange="statCbUpNum()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add2" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downtlnum" onchange="statCbBotNum()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">机动驳</td>
                                    <td class="borderright text-center">
                                        <input id="add3" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upjdbnum" onchange="statCbUpNum()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add4" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downjdbnum"
                                               onchange="statCbBotNum()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">非机动驳</td>
                                    <td class="borderright text-center">
                                        <input id="add5" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upfjdbnum" onchange="statCbUpNum()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add6" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downfjdbnum"
                                               onchange="statCbBotNum()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它船</td>
                                    <td class="borderright text-center">
                                        <input id="add7" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upqtcnum" onchange="statCbUpNum()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add8" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downqtcnum"
                                               onchange="statCbBotNum()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">合计</td>
                                    <td class="borderright text-center">
                                        <input id="add9" type="text" class="form-control noborder"
                                               autoajax=true autoajax-name="hdllgc.upcbnum" disabled
                                               style="background: white;">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add10" type="text" class="form-control noborder"
                                               autoajax=true autoajax-name="hdllgc.downcbnum" disabled
                                               style="background: white;">
                                    </td>
                                </tr>

                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderright text-center" rowspan="5"
                                        style="line-height: 180px;">船舶吨位(吨)
                                    </td>
                                    <td class="borderright text-center">拖轮</td>
                                    <td class="borderright text-center">
                                        <input id="add11" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.uptlton" onchange="statCbUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add12" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downtlton" onchange="statCbBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">机动驳</td>
                                    <td class="borderright text-center">
                                        <input id="add13" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upjdbton" onchange="statCbUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add14" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downjdbton"
                                               onchange="statCbBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">非机动驳</td>
                                    <td class="borderright text-center">
                                        <input id="add15" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upfjdbton" onchange="statCbUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add16" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downfjdbton"
                                               onchange="statCbBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它船</td>
                                    <td class="borderright text-center">
                                        <input id="add17" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upqtcton" onchange="statCbUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add18" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downqtcton"
                                               onchange="statCbBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">合计</td>
                                    <td class="borderright text-center">
                                        <input id="add19" type="text" class="form-control noborder"
                                               autoajax=true autoajax-name="hdllgc.upcbton" disabled
                                               style="background: white;">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add20" type="text" class="form-control noborder"
                                               autoajax=true autoajax-name="hdllgc.downcbton" disabled
                                               style="background: white;">
                                    </td>
                                </tr>

                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderbottom borderright text-center" rowspan="5"
                                        style="line-height: 180px;">货物运量(吨)
                                    </td>
                                    <td class="borderright text-center">煤炭</td>
                                    <td class="borderright text-center">
                                        <input id="add21" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upmtton" onchange="statGoodsUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add22" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downmtton"
                                               onchange="statGoodsBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">矿建材料</td>
                                    <td class="borderright text-center">
                                        <input id="add23" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upkjclton"
                                               onchange="statGoodsUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add24" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downkjclton"
                                               onchange="statGoodsBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它</td>
                                    <td class="borderright text-center">
                                        <input id="add25" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.upqtton" onchange="statGoodsUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="add26" type="text" class="form-control noborder" placeholder="请输入数量"
                                               autoajax=true autoajax-name="hdllgc.downqtton"
                                               onchange="statGoodsBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright borderbottom text-center">合计</td>
                                    <td class="borderright borderbottom text-center">
                                        <input id="add27" type="text" class="form-control noborder"
                                               autoajax=true autoajax-name="hdllgc.upgoodston" disabled
                                               style="background: white;">
                                    </td>
                                    <td class="borderright borderbottom text-center">
                                        <input id="add28" type="text" class="form-control noborder"
                                               autoajax=true autoajax-name="hdllgc.downgoodston" disabled
                                               style="background: white;">
                                    </td>
                                </tr>
                                </tbody>
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
        <div class="modal-dialog" role="document" style="width:1000px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">修改观测记录</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" id="formupdate">
                        <div class="row">
                            <div class="row hide">
                                <div class="col-xs-12" id="divupdateid"></div>
                            </div>
                            <div class="col-xs-1 text-right" style="width:10%;"><h4
                                    style="margin-top:5px;font-size:16px;">观测点</h4></div>
                            <div class="col-xs-3 text-left" style="width:20%;padding-right: 0;" id="divupdateselhdao">
                            </div>
                            <div class="col-xs-3 text-left" style="width:20%;padding-left: 0;"
                                 id="divupdateselgcz"></div>
                            <div class="col-xs-1 text-right" style="width:10%;"><h4
                                    style="margin-top:5px;font-size:16px;">观测日期</h4></div>
                            <div class="col-xs-3 text-left" style="width:25%;" id="divupdatetimeregion">
                                <script>
                                    $("#divupdatetimeregion").addday({
                                        id: 'seldayupdate',
                                        hint: '请选择日期',
                                        validators: {notempty: {msg: "请选择观测日期"}},
                                    });
                                </script>
                            </div>
                        </div>

                        <div class="row">
                            <table class="table bordertop" style="margin-top: 10px;">
                                <thead>
                                <tr>
                                    <th width="320px;" height="40px" colspan="2"
                                        class="text-center borderright borderleft"
                                        style="padding:0 0 0 0;">
                                        <div class="corn" style="width:100%;height:100%;">
                                            <label style="position: relative;left:-75px;top:16px;font-weight: bold;">统计指标</label>
                                            <label style="position: relative;left:30px;top:0px;font-weight: bold;">方向</label>
                                        </div>
                                    </th>
                                    <th width="30%" class="borderright text-center">上行</th>
                                    <th width="30%" class="borderright text-center">下行</th>
                                </tr>
                                </thead>
                                <tbody>
                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderright text-center" rowspan="5"
                                        style="line-height: 180px;">船舶艘数(艘次)
                                    </td>
                                    <td class="borderright text-center">拖轮</td>
                                    <td class="borderright text-center">
                                        <input id="update1" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.uptlnum"
                                               onchange="updateCbUpNum()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update2" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downtlnum"
                                               onchange="updateCbBotNum()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">机动驳</td>
                                    <td class="borderright text-center">
                                        <input id="update3" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upjdbnum"
                                               onchange="updateCbUpNum()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update4" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downjdbnum"
                                               onchange="updateCbBotNum()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">非机动驳</td>
                                    <td class="borderright text-center">
                                        <input id="update5" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upfjdbnum"
                                               onchange="updateCbUpNum()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update6" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downfjdbnum"
                                               onchange="updateCbBotNum()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它船</td>
                                    <td class="borderright text-center">
                                        <input id="update7" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upqtcnum"
                                               onchange="updateCbUpNum()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update8" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downqtcnum"
                                               onchange="updateCbBotNum()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">合计</td>
                                    <td class="borderright text-center">
                                        <input id="update9" type="text" class="form-control noborder"
                                               disabled autoajax=true autoajax-name="hdllgc.upcbnum"
                                               style="background: white;">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update10" type="text" class="form-control noborder"
                                               disabled autoajax=true autoajax-name="hdllgc.downcbnum"
                                               style="background: white;">
                                    </td>
                                </tr>

                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderright text-center" rowspan="5"
                                        style="line-height: 180px;">船舶吨位(吨)
                                    </td>
                                    <td class="borderright text-center">拖轮</td>
                                    <td class="borderright text-center">
                                        <input id="update11" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.uptlton"
                                               onchange="updateCbUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update12" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downtlton"
                                               onchange="updateCbBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">机动驳</td>
                                    <td class="borderright text-center">
                                        <input id="update13" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upjdbton"
                                               onchange="updateCbUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update14" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downjdbton"
                                               onchange="updateCbBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">非机动驳</td>
                                    <td class="borderright text-center">
                                        <input id="update15" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upfjdbton"
                                               onchange="updateCbUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update16" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downfjdbton"
                                               onchange="updateCbBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它船</td>
                                    <td class="borderright text-center">
                                        <input id="update17" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upqtcton"
                                               onchange="updateCbUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update18" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downqtcton"
                                               onchange="updateCbBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">合计</td>
                                    <td class="borderright text-center">
                                        <input id="update19" type="text" class="form-control noborder"
                                               disabled autoajax=true autoajax-name="hdllgc.upcbton"
                                               style="background: white;">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update20" type="text" class="form-control noborder"
                                               disabled autoajax=true autoajax-name="hdllgc.downcbton"
                                               style="background: white;">
                                    </td>
                                </tr>

                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderbottom borderright text-center" rowspan="5"
                                        style="line-height: 180px;">货物运量(吨)
                                    </td>
                                    <td class="borderright text-center">煤炭</td>
                                    <td class="borderright text-center">
                                        <input id="update21" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upmtton"
                                               onchange="updateGoodsUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update22" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downmtton"
                                               onchange="updateGoodsBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">矿建材料</td>
                                    <td class="borderright text-center">
                                        <input id="update23" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upkjclton"
                                               onchange="updateGoodsUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update24" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downkjclton"
                                               onchange="updateGoodsBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它</td>
                                    <td class="borderright text-center">
                                        <input id="update25" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.upqtton"
                                               onchange="updateGoodsUpTon()">
                                    </td>
                                    <td class="borderright text-center">
                                        <input id="update26" type="text" class="form-control noborder"
                                               placeholder="请输入数量" autoajax=true autoajax-name="hdllgc.downqtton"
                                               onchange="updateGoodsBotTon()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright borderbottom text-center">合计</td>
                                    <td class="borderright borderbottom text-center">
                                        <input id="update27" type="text" class="form-control noborder"
                                               disabled autoajax=true autoajax-name="hdllgc.upgoodston"
                                               style="background: white;">
                                    </td>
                                    <td class="borderright borderbottom text-center">
                                        <input id="update28" type="text" class="form-control noborder"
                                               disabled autoajax=true autoajax-name="hdllgc.downgoodston"
                                               style="background: white;">
                                    </td>
                                </tr>
                                </tbody>
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
                            你确定要删除该航道流量吗？
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
        <div class="modal-dialog" role="document" style="width:1200px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">查看观测记录</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-2 text-right"><h4
                                    style="font-size:16px;">航道名称:</h4></div>
                            <div class="col-xs-2 text-left">
                                <h4 id="divviewselhdao" style="font-size:16px;"></h4>
                            </div>
                            <div class="col-xs-1 text-right"><h4
                                    style="font-size:16px;">观测点:</h4></div>
                            <div class="col-xs-2 text-left"><h4 id="divviewselgcz" style="font-size:16px;"></h4></div>
                            <div class="col-xs-2 text-right"><h4
                                    style="font-size:16px;">观测日期:</h4></div>
                            <div class="col-xs-3 text-left"><h4 id="divviewtimeregion" style="font-size:16px;"></h4>
                            </div>
                        </div>

                        <div class="row">
                            <table class="table bordertop" style="margin-top: 10px;">
                                <thead>
                                <tr>
                                    <th width="320px;" height="40px" colspan="2"
                                        class="text-center borderright borderleft"
                                        style="padding:0 0 0 0;">
                                        <div class="corn" style="width:100%;height:100%;">
                                            <label style="position: relative;left:-75px;top:16px;font-weight: bold;">统计指标</label>
                                            <label style="position: relative;left:30px;top:0px;font-weight: bold;">方向</label>
                                        </div>
                                    </th>
                                    <th width="30%" class="borderright text-center">上行</th>
                                    <th width="30%" class="borderright text-center">下行</th>
                                </tr>
                                </thead>
                                <tbody>
                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderright text-center" rowspan="5"
                                        style="line-height: 180px;">船舶艘数(艘次)
                                    </td>
                                    <td class="borderright text-center">拖轮</td>
                                    <td class="borderright text-center" id="view1">
                                    </td>
                                    <td class="borderright text-center" id="view2">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">机动驳</td>
                                    <td class="borderright text-center" id="view3">
                                    </td>
                                    <td class="borderright text-center" id="view4">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">非机动驳</td>
                                    <td class="borderright text-center" id="view5">
                                    </td>
                                    <td class="borderright text-center" id="view6">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它船</td>
                                    <td class="borderright text-center" id="view7">
                                    </td>
                                    <td class="borderright text-center" id="view8">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">合计</td>
                                    <td class="borderright text-center" id="view9">
                                    </td>
                                    <td class="borderright text-center" id="view10">
                                    </td>
                                </tr>

                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderright text-center" rowspan="5"
                                        style="line-height: 180px;">船舶吨位(吨)
                                    </td>
                                    <td class="borderright text-center">拖轮</td>
                                    <td class="borderright text-center" id="view11">
                                    </td>
                                    <td class="borderright text-center" id="view12">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">机动驳</td>
                                    <td class="borderright text-center" id="view13">
                                    </td>
                                    <td class="borderright text-center" id="view14">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">非机动驳</td>
                                    <td class="borderright text-center" id="view15">
                                    </td>
                                    <td class="borderright text-center" id="view16">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它船</td>
                                    <td class="borderright text-center" id="view17">
                                    </td>
                                    <td class="borderright text-center" id="view18">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">合计</td>
                                    <td class="borderright text-center" id="view19">
                                    </td>
                                    <td class="borderright text-center" id="view20">
                                    </td>
                                </tr>

                                <!----------------------------------------->
                                <tr>
                                    <td class="borderleft borderbottom borderright text-center" rowspan="5"
                                        style="line-height: 180px;">货物运量(吨)
                                    </td>
                                    <td class="borderright text-center">煤炭</td>
                                    <td class="borderright text-center" id="view21">
                                    </td>
                                    <td class="borderright text-center" id="view22">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">矿建材料</td>
                                    <td class="borderright text-center" id="view23">
                                    </td>
                                    <td class="borderright text-center" id="view24">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright text-center">其它</td>
                                    <td class="borderright text-center" id="view25">
                                    </td>
                                    <td class="borderright text-center" id="view26">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="borderright borderbottom text-center">合计</td>
                                    <td class="borderright borderbottom text-center" id="view27">
                                    </td>
                                    <td class="borderright borderbottom text-center" id="view28">
                                    </td>
                                </tr>
                                </tbody>
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
