<%@ page import="com.channel.model.user.CXtUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
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

    <title>重置赔偿</title>

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

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hangzheng/czpc.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/wdatepicker/WdatePicker.js"></script>

    <script>
        function delpre(ths) {
            $precontainer = $(ths).parent().prev();
            if ($precontainer.prev().is('label')) {
                $(ths).parent().next().removeClass('col-xs-offset-3');
            }
            $precontainer.remove();
            $(ths).parent().remove();
        }

        function addmorezylx(ths, sel1, sel2, sel3, sl, islast) {
            var $container = $('.zylxcontainer', $(ths).parent().parent()).last();
            var $delbtn = $('<div class="col-xs-1" style="margin-top:15px;">' +
                    '<button class="btn btn-primary" onclick="delpre(this)">删除</button>' +
                    '</div>');
            if (islast == null || islast == 0) {
                var $copycontainer = $container.clone();
                $copycontainer.addClass('col-xs-7 col-xs-offset-3');
                $copycontainer.addClass('addedcontainer');
                $delbtn.addClass('addedcontainer');
                $container.after($copycontainer);
                $container.after($delbtn);
            }

            if (sel1 != null && sel2 != null && sel3 != null) {
                $("#lx1sel", $container).find("option[value='" + sel1 + "']").attr("selected", true);
                dochange1($("#lx1sel", $container)[0]);
                if (parseInt(sel1) == 3) {
                    $("#lx2sel", $container).find("option[value='" + sel2 + "']").attr("selected", true);
                    dochange2($("#lx2sel", $container)[0]);
                    $("#lx3sel" + sel2, $container).find("option[value='" + sel3 + "']").attr("selected", true);
                    if (parseInt(sel2) != 4 && parseInt(sel2) != 5) {
                        eval("dochange3" + sel2 + '($("#lx3sel" + sel2, $container)[0])');
                    }
                }
                $("#ipt" + sel1 + sel2 + sel3, $container).val(sl);
            } else {
                //初始化 选项
                var op = $("#lx1sel", $copycontainer)[0];
                $(op).find("option[value='1']").attr("selected", true);
                var lxz = $("#ipt100", $copycontainer)[0];
                $(lxz).val("");
                dochange1(op);
            }
        }
        function dochange1(ths) {
            $container = $(ths).parents(".zylxcontainer");
            var sel = $(ths, $container).val();
            $("#divmt", $container).hide();
            $("#divha", $container).hide();
            $("#divlh", $container).hide();
            switch (parseInt(sel)) {
                case 1:
                    $("#divmt", $container).show();
                    break;
                case 2:
                    $("#divha", $container).show();
                    break;
                case 3:
                    $("#divlh", $container).show();
                    break;
            }
        }
        function dochange2(ths) {
            $container = $(ths).parents(".zylxcontainer");
            var sel = $(ths, $container).val();
            $("#divlhxz", $container).hide();
            $("#divlhls", $container).hide();
            $("#divlhssy", $container).hide();
            $("#divlhhtq", $container).hide();
            $("#divlhqm", $container).hide();
            $("#divlhgm", $container).hide();
            switch (parseInt(sel)) {
                case 1:
                    $("#divlhxz", $container).show();
                    break;
                case 2:
                    $("#divlhls", $container).show();
                    break;
                case 3:
                    $("#divlhssy", $container).show();
                    break;
                case 4:
                    $("#divlhhtq", $container).show();
                    break;
                case 5:
                    $("#divlhqm", $container).show();
                    break;
                case 6:
                    $("#divlhgm", $container).show();
                    break;
            }
        }
        function dochange31(ths) {
            $container = $(ths).parents(".zylxcontainer");
            var sel = $(ths, $container).val();
            $("#divlhxz1", $container).hide();
            $("#divlhxz2", $container).hide();
            $("#divlhxz3", $container).hide();
            switch (parseInt(sel)) {
                case 1:
                    $("#divlhxz1", $container).show();
                    break;
                case 2:
                    $("#divlhxz2", $container).show();
                    break;
                case 3:
                    $("#divlhxz3", $container).show();
                    break;
            }
        }
        function dochange32(ths) {
            $container = $(ths).parents(".zylxcontainer");
            var sel = $(ths, $container).val();
            $("#divlhls1", $container).hide();
            $("#divlhls2", $container).hide();
            $("#divlhls3", $container).hide();
            switch (parseInt(sel)) {
                case 1:
                    $("#divlhls1", $container).show();
                    break;
                case 2:
                    $("#divlhls2", $container).show();
                    break;
                case 3:
                    $("#divlhls3", $container).show();
                    break;
            }
        }
        function dochange33(ths) {
            $container = $(ths).parents(".zylxcontainer");
            var sel = $(ths, $container).val();
            $("#divlhssy1", $container).hide();
            $("#divlhssy2", $container).hide();
            switch (parseInt(sel)) {
                case 1:
                    $("#divlhssy1", $container).show();
                    break;
                case 2:
                    $("#divlhssy2", $container).show();
                    break;
            }
        }
        function dochange34(ths) {
            $container = $(ths).parents(".zylxcontainer");
            var sel = $(ths, $container).val();
            $("#divlhhtq1", $container).hide();
            $("#divlhhtq2", $container).hide();
            switch (parseInt(sel)) {
                case 1:
                    $("#divlhhtq1", $container).show();
                    break;
                case 2:
                    $("#divlhhtq2", $container).show();
                    break;
            }
        }
    </script>
</head>


<body>
<jsp:useBean id="pui" class="com.channel.permission.UI" scope="page"></jsp:useBean>
<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">
<input type="hidden" id="deptxzqh" value="<%=deptxzqh%>">
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
                <a class="btn btn-primary" id="tabxzxk">行政许可</a>
            </c:if>
            <c:if test="${pui.hasPerm('VIEW_XZCF')||pui.hasPerm('MAN_XZCF')}">
                <a class="btn btn-primary" id="tabxzcf">行政处罚</a>
            </c:if>
            <c:if test="${pui.hasPerm('VIEW_THLZ')||pui.hasPerm('MAN_THLZ')}">
                <a class="btn btn-primary" id="tabthlz">通航论证</a>
            </c:if>
            <c:if test="${pui.hasPerm('VIEW_CZPC')||pui.hasPerm('MAN_CZPC')}">
                <a class="btn btn-primary active" id="tabczpc">重置赔偿</a>
            </c:if>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>

    <div class="row navcontent shadow">
        <div class="col-xs-12">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">重置赔偿</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-4">
                    <c:if test="${pui.hasPerm('MAN_CZPC')}">
                        <button class="btn btn-primary" style="margin: 15px 0 0 0" onclick="newczpcload();">新建重置赔偿
                        </button>
                        <button class="btn btn-default" style="margin: 15px 0 0 0" onclick="delczpcsload();">删除</button>
                    </c:if>
                </div>
                <div class="col-xs-5">
                    <div class="row">
                        <div class="col-xs-3 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">受理日期:</h4>
                        </div>
                        <div class="col-xs-9 text-left" id="divtimeregion"
                             style="margin: 15px 0 0 0">
                            <script>
                                $("#divtimeregion").adddayregion({
                                    idstart: 'seldaystart',
                                    hintstart: '请选择开始时间',
                                    hintend: '请选择结束时间',
                                    idend: 'seldayend',
                                    onpickedstart: 'loadczpc',
                                    onpickedend: 'loadczpc'
                                });
                            </script>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="input-group" style="margin: 15px 0 0 0">
                        <!-- /btn-group -->
                        <input type="text" class="form-control" aria-label="..." id="inputcontent">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="btnsearch" onclick="loadczpc();">搜索
                            </button>
                        </span>
                    </div>
                    <!-- /input-group -->
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <table id="czpctable" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th style="text-align: center;"><input
                                    id="cbselall" type="checkbox">&nbsp;序号
                            </th>
                            <th style="text-align: center;">单位</th>
                            <th style="text-align: center;">项目名称</th>
                            <th style="text-align: center;">性质</th>
                            <th style="text-align: center;">赔补偿金额</th>
                            <th style="text-align: center;">受理单位</th>
                            <th style="text-align: center;">受理日期</th>
                            <th style="text-align: center;">操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
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
                <h4 class="modal-title">重置赔偿详情</h4>
            </div>
            <div class="modal-body" id="modalviewbody">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="modalnew" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增重置赔偿</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid nopadding">
                    <div class="form-horizontal" style="margin-top:-15px;" id="addczpcform">
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label"
                                       style="margin-top:15px;">占用（损坏）单位</label>

                                <div class="col-xs-4" style="margin-top:15px;" id="divadddw">
                                    <input type="text" class="form-control" autoajax-name="czpc.dw"
                                           autoajax="true" id="adddw">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label" style="margin-top:15px;">占用（损坏）项目名称</label>

                                <div class="col-xs-4" style="margin-top:15px;">
                                    <input type="text" class="form-control" autoajax-name="czpc.name"
                                           autoajax="true" id="addname">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label"
                                       style="margin-top:15px;">占用（损坏）位置</label>

                                <div class="col-xs-7" style="margin-top:15px;">
                                    <div id="divhdao">
                                        <select class="form-control leftradius"
                                                style="float:left;display:inline;width:auto;"
                                                id="selectfswsearchhdao"
                                                onchange="initsearchhangduan($(this).val())">
                                        </select>
                                    </div>
                                    <div id="divhduan">
                                        <select class="form-control rightradius"
                                                style="float:left;display:inline;width:auto;"
                                                id="selectfswsearchhduan">
                                            <option value="-1">全部航段</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group" id="zylxdiv">
                                <label class="col-xs-2 col-xs-offset-1 control-label"
                                       style="margin-top:15px;">占用（损坏）类型</label>

                                <div class="col-xs-7 zylxcontainer" style="margin-top:15px;">
                                    <span>
                                        <select class="form-control leftradius"
                                                style="float:left;width:18%;display:inline;"
                                                onchange="dochange1(this);" id="lx1sel">
                                            <option value="1" selected>码头</option>
                                            <option value="2">护岸</option>
                                            <option value="3">绿化</option>
                                        </select>
                                        <div class="input-group"<%--码头--%> style="width:40%;" id="divmt">
                                            <input type="text" class="form-control rightradius" id="ipt100"
                                                   name="czpclxz">
                                            <span class="input-group-addon">米</span>
                                        </div>

                                        <div class="input-group"<%--护岸--%> style="width:40%;display:none;" id="divha">
                                            <input type="text" class="form-control rightradius" id="ipt200"
                                                   name="czpclxz">
                                            <span class="input-group-addon">米</span>
                                        </div>
                                        <div <%--绿化--%> style="display:none;" id="divlh">
                                            <select class="form-control middleradius"
                                                    style="float:left;display:inline;width:auto;"
                                                    onchange="dochange2(this);" id="lx2sel">
                                                <option value="1" selected>香樟</option>
                                                <option value="2">柳树</option>
                                                <option value="3">速生杨</option>
                                                <option value="4">海桐球</option>
                                                <option value="5">观赏乔木</option>
                                                <option value="6">观赏灌木</option>
                                            </select>

                                            <div id="divlhxz"<%--香樟--%>>
                                                <select class="form-control middleradius"
                                                        style="float:left;display:inline;width:auto;"
                                                        onchange="dochange31(this);" id="lx3sel1">
                                                    <option value="1" selected>胸径￠10cm以下</option>
                                                    <option value="2">胸径￠10~20cm</option>
                                                    <option value="3">胸径￠20cm以上</option>
                                                </select>

                                                <div class="input-group"<%--胸径￠10cm以下--%> style="width:25%;"
                                                     id="divlhxz1">
                                                    <input type="text" class="form-control rightradius" id="ipt311"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠10~20cm--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhxz2">
                                                    <input type="text" class="form-control rightradius" id="ipt312"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠20cm以上--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhxz3">
                                                    <input type="text" class="form-control rightradius" id="ipt313"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhls"<%--柳树--%> style="display: none;">
                                                <select class="form-control middleradius"
                                                        style="float:left;display:inline;width:auto;"
                                                        onchange="dochange32(this);" id="lx3sel2">
                                                    <option value="1" selected>胸径￠10cm以下</option>
                                                    <option value="2">胸径￠10~20cm</option>
                                                    <option value="3">胸径￠20cm以上</option>
                                                </select>

                                                <div class="input-group"<%--胸径￠10cm以下--%> style="width:25%;"
                                                     id="divlhls1">
                                                    <input type="text" class="form-control rightradius" id="ipt321"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠10~20cm--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhls2">
                                                    <input type="text" class="form-control rightradius" id="ipt322"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠20cm以上--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhls3">
                                                    <input type="text" class="form-control rightradius" id="ipt323"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhssy"<%--速生杨--%> style="display: none;">
                                                <select class="form-control middleradius"
                                                        style="float:left;display:inline;width:auto;"
                                                        onchange="dochange33(this);" id="lx3sel3">
                                                    <option value="1" selected>胸径￠10~20cm</option>
                                                    <option value="2">胸径￠20cm以上</option>
                                                </select>

                                                <div class="input-group"<%--胸径￠10~20cm--%> style="width:25%;"
                                                     id="divlhssy1">
                                                    <input type="text" class="form-control rightradius" id="ipt331"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠20cm以上--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhssy2">
                                                    <input type="text" class="form-control rightradius" id="ipt332"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhhtq"<%--海桐球--%> style="display: none;">
                                                <select class="form-control middleradius"
                                                        style="float:left;display:inline;width:auto;"
                                                        onchange="dochange34(this);" id="lx3sel4">
                                                    <option value="1" selected>胸径￠50cm以下</option>
                                                    <option value="2">胸径￠50cm以上</option>
                                                </select>

                                                <div class="input-group"<%--胸径￠50cm以下--%> style="width:25%;"
                                                     id="divlhhtq1">
                                                    <input type="text" class="form-control rightradius" id="ipt341"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠50cm以上--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhhtq2">
                                                    <input type="text" class="form-control rightradius" id="ipt342"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhqm"<%--观赏乔木--%> style="display: none;">
                                                <div class="input-group" style="width:25%;">
                                                    <input type="text" class="form-control rightradius" id="ipt350"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhgm"<%--观赏灌木--%> style="display: none;">
                                                <div class="input-group" style="width:25%;">
                                                    <input type="text" class="form-control rightradius" id="ipt360"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">平方</span>
                                                </div>
                                            </div>
                                        </div>
                                    </span>
                                </div>
                                <div class="col-xs-1" style="margin-top:15px;">
                                    <button class="btn btn-primary" onclick="addmorezylx(this)">新增</button>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label"
                                       style="margin-top:15px;">占用（损坏）性质</label>

                                <div class="col-xs-4" style="margin-top:15px;">
                                    <input type="radio" name="addxz" value="1" checked style="margin-left:15px;">&nbsp;占用
                                    <input type="radio" name="addxz" value="2" style="margin-left:15px;">&nbsp;损坏
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label">赔补偿金额</label>

                                <div class="col-xs-4">
                                    <input type="text" class="form-control" autoajax="true"
                                           autoajax-name="czpc.pbcje" id="addpbcje">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label">受理单位</label>

                                <div class="col-xs-4">
                                    <input type="text" class="form-control" autoajax="true"
                                           autoajax-name="czpc.sldw" id="addsldw">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label">资产评估单位</label>

                                <div class="col-xs-4">
                                    <input type="text" class="form-control" autoajax="true"
                                           autoajax-name="czpc.zcpgdw" id="addzcpgdw">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label">受理日期</label>

                                <div class="col-xs-4" style="" id="divslrq">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="btnaddczpc" onclick="addczpc()">保存
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="modalupdate" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">更新重置赔偿</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid nopadding">
                    <div class="row form-horizontal" style="margin-top:-15px;" id="updateczpcform">
                        <div class="row hide">
                            <div class="col-xs-12" id="divupdateid">
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label"
                                       style="margin-top:15px;">占用（损坏）单位</label>

                                <div class="col-xs-4" style="margin-top:15px;" id="divupdatedw">
                                    <input type="text" class="form-control" autoajax-name="czpc.dw"
                                           autoajax="true" id="updatedw">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label" style="margin-top:15px;">占用（损坏）项目名称</label>

                                <div class="col-xs-4" style="margin-top:15px;" id="divupdatename">
                                    <input type="text" class="form-control" autoajax-name="czpc.name"
                                           autoajax="true" id="updatename">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label"
                                       style="margin-top:15px;">占用（损坏）位置</label>

                                <div class="col-xs-7" style="margin-top:15px;">
                                    <div id="divupdatehdao">
                                        <select class="form-control leftradius"
                                                style="float:left;display:inline;width:auto;"
                                                id="updatehdao"
                                                onchange="initupdatehangduan($(this).val())">
                                        </select>
                                    </div>
                                    <div id="divupdatehduan">
                                        <select class="form-control rightradius"
                                                style="float:left;display:inline;width:auto;"
                                                id="updatehduan">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group" id="zylxdivupdate">
                                <label class="col-xs-2 col-xs-offset-1 control-label"
                                       style="margin-top:15px;">占用（损坏）类型</label>

                                <div class="col-xs-7 zylxcontainer" style="margin-top:15px;">
                                    <span>
                                        <select class="form-control leftradius"
                                                style="float:left;width:18%;display:inline;"
                                                onchange="dochange1(this);" id="lx1sel">
                                            <option value="1" selected>码头</option>
                                            <option value="2">护岸</option>
                                            <option value="3">绿化</option>
                                        </select>
                                        <div class="input-group"<%--码头--%> style="width:40%;" id="divmt">
                                            <input type="text" class="form-control rightradius" id="ipt100"
                                                   name="czpclxz">
                                            <span class="input-group-addon">米</span>
                                        </div>

                                        <div class="input-group"<%--护岸--%> style="width:40%;display:none;" id="divha">
                                            <input type="text" class="form-control rightradius" id="ipt200"
                                                   name="czpclxz">
                                            <span class="input-group-addon">米</span>
                                        </div>
                                        <div <%--绿化--%> style="display:none;" id="divlh">
                                            <select class="form-control middleradius"
                                                    style="float:left;display:inline;width:auto;"
                                                    onchange="dochange2(this);" id="lx2sel">
                                                <option value="1" selected>香樟</option>
                                                <option value="2">柳树</option>
                                                <option value="3">速生杨</option>
                                                <option value="4">海桐球</option>
                                                <option value="5">观赏乔木</option>
                                                <option value="6">观赏灌木</option>
                                            </select>

                                            <div id="divlhxz"<%--香樟--%>>
                                                <select class="form-control middleradius"
                                                        style="float:left;display:inline;width:auto;"
                                                        onchange="dochange31(this);" id="lx3sel1">
                                                    <option value="1" selected>胸径￠10cm以下</option>
                                                    <option value="2">胸径￠10~20cm</option>
                                                    <option value="3">胸径￠20cm以上</option>
                                                </select>

                                                <div class="input-group"<%--胸径￠10cm以下--%> style="width:25%;"
                                                     id="divlhxz1">
                                                    <input type="text" class="form-control rightradius" id="ipt311"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠10~20cm--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhxz2">
                                                    <input type="text" class="form-control rightradius" id="ipt312"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠20cm以上--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhxz3">
                                                    <input type="text" class="form-control rightradius" id="ipt313"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhls"<%--柳树--%> style="display: none;">
                                                <select class="form-control middleradius"
                                                        style="float:left;display:inline;width:auto;"
                                                        onchange="dochange32(this);" id="lx3sel2">
                                                    <option value="1" selected>胸径￠10cm以下</option>
                                                    <option value="2">胸径￠10~20cm</option>
                                                    <option value="3">胸径￠20cm以上</option>
                                                </select>

                                                <div class="input-group"<%--胸径￠10cm以下--%> style="width:25%;"
                                                     id="divlhls1">
                                                    <input type="text" class="form-control rightradius" id="ipt321"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠10~20cm--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhls2">
                                                    <input type="text" class="form-control rightradius" id="ipt322"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠20cm以上--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhls3">
                                                    <input type="text" class="form-control rightradius" id="ipt323"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhssy"<%--速生杨--%> style="display: none;">
                                                <select class="form-control middleradius"
                                                        style="float:left;display:inline;width:auto;"
                                                        onchange="dochange33(this);" id="lx3sel3">
                                                    <option value="1" selected>胸径￠10~20cm</option>
                                                    <option value="2">胸径￠20cm以上</option>
                                                </select>

                                                <div class="input-group"<%--胸径￠10~20cm--%> style="width:25%;"
                                                     id="divlhssy1">
                                                    <input type="text" class="form-control rightradius" id="ipt331"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠20cm以上--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhssy2">
                                                    <input type="text" class="form-control rightradius" id="ipt332"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhhtq"<%--海桐球--%> style="display: none;">
                                                <select class="form-control middleradius"
                                                        style="float:left;display:inline;width:auto;"
                                                        onchange="dochange34(this);" id="lx3sel4">
                                                    <option value="1" selected>胸径￠50cm以下</option>
                                                    <option value="2">胸径￠50cm以上</option>
                                                </select>

                                                <div class="input-group"<%--胸径￠50cm以下--%> style="width:25%;"
                                                     id="divlhhtq1">
                                                    <input type="text" class="form-control rightradius" id="ipt341"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>

                                                <div class="input-group"<%--胸径￠50cm以上--%>
                                                     style="width:25%;display: none;"
                                                     id="divlhhtq2">
                                                    <input type="text" class="form-control rightradius" id="ipt342"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhqm"<%--观赏乔木--%> style="display: none;">
                                                <div class="input-group" style="width:25%;">
                                                    <input type="text" class="form-control rightradius" id="ipt350"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">颗</span>
                                                </div>
                                            </div>

                                            <div id="divlhgm"<%--观赏灌木--%> style="display: none;">
                                                <div class="input-group" style="width:25%;">
                                                    <input type="text" class="form-control rightradius" id="ipt360"
                                                           name="czpclxz">
                                                    <span class="input-group-addon">平方</span>
                                                </div>
                                            </div>
                                        </div>
                                    </span>
                                </div>
                                <div class="col-xs-1" style="margin-top:15px;">
                                    <button class="btn btn-primary" onclick="addmorezylx(this)" id="addlxupdate">新增
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 col-xs-offset-1 control-label">占用（损坏）性质</label>

                                <div class="col-xs-4">
                                    <input type="radio" name="updatexz" value="1" style="margin-left:15px;">&nbsp;占用
                                    <input type="radio" name="updatexz" value="2" style="margin-left:15px;">&nbsp;损坏
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label">赔补偿金额</label>

                                <div class="col-xs-4" id="divupdatepbcje">
                                    <input type="text" class="form-control" autoajax="true"
                                           autoajax-name="czpc.pbcje" id="updatepbcje">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label">受理单位</label>

                                <div class="col-xs-4" id="divupdatesldw">
                                    <input type="text" class="form-control" autoajax="true"
                                           autoajax-name="czpc.sldw" id="updatesldw">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label">资产评估单位</label>

                                <div class="col-xs-4" id="divupdatezcpgdw">
                                    <input type="text" class="form-control" autoajax="true"
                                           autoajax-name="czpc.zcpgdw" id="updatezcpgdw">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="col-xs-2 col-xs-offset-1 control-label">受理日期</label>

                                <div class="col-xs-4" id="divupdateslrq">
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="updateczpc()">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="modaldel" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">删除重置赔偿</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <p>
                        你确定要删除项目名称为&nbsp;<label id="lbmodaldelnames"></label>&nbsp;的重置赔偿吗？
                    </p>

                    <p id="pErrMsg" class="text-danger"></p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="btndelczpc" type="button" class="btn btn-primary">删除</button>
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
