<%@page import="com.channel.model.user.CXtUser" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    int szju = -2;
    if (szjudpt != null)
        szju = szjudpt.getId();

    CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.getAttribute("dptshixzqh");
    int szshixzqh = 1;
    String szxzqhname = "浙江省";
    if (dptshixzqh != null) {
        szshixzqh = dptshixzqh.getId();
        szxzqhname = dptshixzqh.getName();
    }

    CZdXzqhdm dptxzqh = (CZdXzqhdm) session.getAttribute("dptxzqh");
    int szxzqh = 1;
    String szname = "浙江省";
    if (dptxzqh != null) {
        szxzqh = dptxzqh.getId();
        szname = dptxzqh.getName();
    }

    List<CZdXzqhdm> manxzqh = (List<CZdXzqhdm>) session.getAttribute("manxzqh");
    String xqstr = "";
    int defaultmanxzqh = -2;
    if (manxzqh != null) {
        for (int i = 0; i < manxzqh.size(); i++) {
            CZdXzqhdm dm = manxzqh.get(i);

            if (i == 0)
                defaultmanxzqh = dm.getId();
            xqstr += dm.getId() + "," + dm.getName() + "," + dm.getType();
            if (i != manxzqh.size() - 1)
                xqstr += ";";
        }
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>信息维护</title>

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
          href="page/hdaoinfo/datatable.css">
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet" type="text/css"
          href="page/hdaoinfo/hdaoinfomanager.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hdaoinfo/hdaoinfomanager.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="http://172.20.24.105/WebtAPI/wapi.js"></script>

</head>


<body style="min-width:1200px;overflow-y:hidden;background:#f0f0f0;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">
<input type="hidden" id="xzqhname" value="<%=szxzqhname%>">
<input type="hidden" id="szxzqh" value="<%=szxzqh%>">
<input type="hidden" id="szname" value="<%=szname%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2" style="min-width: 200px;">
            <img id="btnleftmenu" src="img/ic_fanhuishouye.png"
                 class="pull-left" style="margin-top:10px;"
                 onmouseover="event.stopPropagation();showleftpopmenu();">&nbsp;&nbsp;&nbsp;&nbsp;
            <label class="pull-left">信息维护</label>
        </div>
        <div class="col-xs-7">
            <a class="btn btn-primary active" id="tabhangdao">信息维护</a>
            <a class="btn btn-primary" id="tabmapinfo">物标编辑</a>
            <a class="btn btn-primary" id="tabimport">数据初始</a>
        </div>
        <jsp:include page="/page/title-rightuser.jsp"></jsp:include>
    </div>
    <div class="row navline"></div>
</div>

<div class="shadow" style="height:42px;background:white" id="searchbardiv">
    <div class="col-xs-2" id="divselsearchxzqh" style="margin-top:3px;">
        <script>
            <c:choose>
            <c:when test="${ui.hasPerm('VIEW_SHENHDAO')}">
            $("#divselsearchxzqh").addselxzqh({
                id: "selsearchxzqh",
                selectfn: function () {
                    initsearchhangdao();
                    loadhdaotree();
                },
                defaultval: 1
            }, null, false)
            </c:when>
            <c:otherwise>
            <c:choose>
            <c:when test="${ui.hasPerm('MAN_SHIHDAO') || ui.hasPerm('VIEW_SHIHDAO')}">
            $("#divselsearchxzqh").addselxzqh({
                id: "selsearchxzqh",
                selectfn: function () {
                    initsearchhangdao();
                    loadhdaotree();
                },
                defaultval:<%=szshixzqh%>
            }, <%=szshixzqh%>, false)
            </c:when>
            <c:otherwise>
            <c:choose>
            <c:when test="${ui.hasPerm('MAN_XQHDAO') || ui.hasPerm('VIEW_XQHDAO')}">
            $("#divselsearchxzqh").addselxqxzqh({
                id: "selsearchxzqh",
                selectfn: function () {
                    initsearchhangdao();
                    loadhdaotree();
                },
                defaultval:<%=defaultmanxzqh%>
            }, '<%=xqstr%>', false);
            $(document).ready(function () {
                initsearchhangdao();
                loadhdaotree();
            });
            </c:when>
            <c:otherwise>
            window.location.href = $('#basePath').val() + "/page/nopermPage.jsp";
            </c:otherwise>
            </c:choose>
            </c:otherwise>
            </c:choose>
            </c:otherwise>
            </c:choose>
        </script>
    </div>
    <div class="col-xs-10" style="margin-top:3px;">
        <select class="form-control leftinputgroup" style="float:left;display:inline;width:auto;" id="selsearchtype"
                onchange="if ($(this).val() == '0'){$('#hdaosearchspan').show();$('#hduansearchspan').hide();$('#fswsearchspan').hide();}
                else if ($(this).val() == '1'){$('#hdaosearchspan').hide();$('#hduansearchspan').show();$('#fswsearchspan').hide(); }
                else if ($(this).val() == '2'){$('#hdaosearchspan').hide();$('#hduansearchspan').hide();$('#fswsearchspan').show();}">
            <option value="0">航道
            </option>
            <option value="1">航段
            </option>
            <option value="2">附属物
            </option>
        </select>
        <span id="hdaosearchspan">
            <select class="form-control middleinputgroup" style="float:left;display:inline;width:auto;"
                    id="hdaosearchselecthdao">
                <option value="-1">全部航道</option>
                <option value="1">骨干航道</option>
                <option value="0">支线航道</option>
            </select>
        </span>
        <span id="hduansearchspan" style="display:none;">
            <select class="form-control middleinputgroup" style="float:left;display:inline;width:auto;"
                    id="selecthduansearchhdao">
            </select>

            <select class="form-control middleinputgroup" style="float:left;display:inline;width:auto;"
                    id="selecthduandj">
                <script>
                    ajax('dic/querydicattr', {
                        loginid: $("#userid").val(),
                        name: 'hddj'
                    }, function (data) {
                        $("#selecthduandj").append(
                                '<option value="-1">全部航道等级</option>');
                        if (ifResultOK(data)) {
                            var records = getResultRecords(data);

                            if (records) {
                                for (var i = 0; i < records.data.length; i++) {
                                    var dict = records.data[i];
                                    $("#selecthduandj").append(
                                            '<option value="' + dict.id + '">'
                                            + dict.attrdesc + '</option>');
                                }
                            }
                        } else {
                        }
                    });
                </script>
            </select>
        </span>
        <span id="fswsearchspan" style="display:none;">
            <select class="form-control middleinputgroup" style="float:left;display:inline;width:auto;"
                    id="selectfswsearchhdao" onchange="initsearchhangduan($(this).val())">
            </select>
            <select class="form-control middleinputgroup" style="float:left;display:inline;width:auto;"
                    id="selectfswsearchhduan">
                <option value="-1">全部航段</option>
            </select>
            <select class="form-control middleinputgroup" style="float:left;display:inline;width:auto;"
                    id="selectfswsearchfswsecondclass">
                <option value="-1">全部附属物</option>
                <script>
                    ajax('appurtenancetype/queryallfswclass', {
                        'loginid': $("#userid").val()
                    }, function (result) {
                        if (ifResultOK(result)) {
                            var records = getResultRecords(result).data;
                            if (records != null) {
                                var data = {};
                                for (var index in records) {
                                    var lx = records[index];

                                    var id = lx.id;
                                    var name = lx.name;

                                    $("#selectfswsearchfswsecondclass").append($('<option value="' + id + '">' + name + '</option>'));
                                }
                            }
                        } else {
                            alert(getResultDesc(result));
                        }
                    });
                </script>
            </select>
        </span>
        <input type="text" class="form-control middleinputgroup"
               style="margin-left: 0px;float:left;display:inline;width:300px"
               placeholder="请输入名字或编号"
               onmousedown="searchdropdowndialog();event.stopPropagation();"
               onkeydown="if (event.keyCode==13)searchdropdowndialog();event.stopPropagation();"
               id="searchcontent">
        <span class="input-group-btn rightinputgroup" style="float:left;display:inline;width:20px">
                <button class="btn btn-default" type="button" id="btnSearch"
                        style="height:34px;width:25px;padding-left:6px;"
                        onclick="searchdropdowndialog();event.stopPropagation();"
                        ><i class="icon-search"></i>
                </button>
        </span>
    </div>

    <div class="shadow"
         style="position:fixed;top:90px;max-height:400px;min-width:300px;max-width:600px;background: white;border-radius: 5px;overflow:auto;z-index: 999999"
         id="searchdropdowndialog">
        <div id="searchresulttree" style="margin: 0 5px 0 5px;">
            <!-- 由js生成结果数据并显示 -->
        </div>
    </div>
</div>

<div id="divleft" class="navcontentleft borderright bordertop"
     style="width:230px;float:left;">
    <div id="hdaotree" class="bordertop" style="overflow-y: auto;">
        <!-- 由js生成 -->
    </div>
    <div class="bordertop" style="height:1px;"></div>
    <c:if test="${ui.hasPerm('MAN_SHIHDAO') || ui.hasPerm('MAN_XQHDAO')}">
        <div class="divadd" style="height:64px" id="leftbottomdiv">

            <div class="btn-group" role="group" aria-label="...">
                <button class="btn btn-default">
                    <span class="icon-plus spanadd"></span> <a
                        class="nopadding nomargin aadd" id="btnnewgghdao"
                        style="">骨干航道</a>
                </button>
                <button class="btn btn-default">
                    <span class="icon-plus spanadd"></span> <a
                        class="nopadding nomargin aadd" id="btnnewzxhdao"
                        style="">支线航道</a>
                </button>
            </div>
        </div>
    </c:if>
</div>

<div class="nopadding nomargin"
     style="float:left;width:15px;z-index: 999">
    <div id="leftmenu" expand="1" class="menuleftshouqi"
         style="height:57px;width:10px;margin-left:-1px;margin-top:300px;">
        <!-- 左侧航道导航展开折叠按钮图标 -->
    </div>
</div>

<div id="divright" class="shadow navcontentright" style="margin: 10px 10px 10px 250px;background: #f0f0f0;">
    <div id="divrightleft" class="borderbottom" style="float:left;width:300px;">
        <div class="container-fluid">
            <div class="row" id="divrightlefttop">
                <div class="col-xs-12 borderbottom"
                     style="background: #f5f5f5;">
                    <p class="nomargin nopadding navcontenttitle"
                       style="width:95%;overflow:hidden;">航段</p>
                </div>
            </div>
            <div class="row">
                <div id="hduantree" class="borderbottom"
                     style="overflow:auto;background:white;">
                </div>
                <c:if test="${ui.hasPerm('MAN_SHIHDAO') || ui.hasPerm('MAN_XQHDAO')}">
                    <div class="divadd text-center" style="" id="divrightleftbottom">
                        <div class="btn-group" role="group" aria-label="...">
                            <button class="btn btn-default text-center" style="width:200px;" id="btnnewhduan">
                                <span class="icon-plus spanadd" style="margin-left:60px;"></span> <a
                                    class="nopadding nomargin aadd"
                                    style="">航段</a>
                            </button>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    <div id="divrightright" style="margin-left:300px;">
        <div class="container-fluid">
            <div class="row" style="height:40px;" id="divrightrighttop">
                <div class="col-xs-12 borderbottom borderleft"
                     style="background: #f5f5f5;">
                    <p id="pRightmc" class="nomargin nopadding navcontenttitle"></p>
                </div>
            </div>
            <div class="row borderbottom borderleft" style="overflow:auto;background:white;">
                <div class="col-xs-12" id="detailinfodiv"></div>
            </div>
            <div class="row"
                 style="position: fixed; top:100%;left:100%;margin: -100px 0 0 -160px;">
                <div class="col-xs-5 text-right">
                    <c:if test="${ui.hasPerm('MAN_SHIHDAO') || ui.hasPerm('MAN_XQHDAO')}">
                        <button class="btn btn-primary"
                                style="margin-top:10px;margin-bottom:10px;display: none;" id="btnEdit">
                            修改
                        </button>
                    </c:if>
                </div>
                <div class="col-xs-2"></div>
                <div class="col-xs-5 text-right">
                    <c:if test="${ui.hasPerm('MAN_SHIHDAO') || ui.hasPerm('MAN_XQHDAO')}">
                        <button class="btn btn-primary"
                                style="margin-top:10px;margin-bottom:10px;display: none;" id="btnExport">
                            导出
                        </button>
                    </c:if>
                </div>
            </div>

            <div class="row reshadow"
                 style="background: white;position: absolute; bottom:10px;left:566px;height:40%;display:none;"
                 id="fswtablediv">
                <div class="container-fluid" style="background: white;">
                    <c:if test="${ui.hasPerm('MAN_SHIHDAO') || ui.hasPerm('MAN_XQHDAO')}">
                        <div class="row" style="background: #f0f0f0;padding-top:5px;padding-bottom:5px;"
                             id="fswtableoperbar">
                            <div class="col-xs-12 text-center">
                                <a class="btn btn-primary" id="btnfswnew" style=""
                                   onclick="loadfswaddinfo($(this).getobj('fsw'),$(this).getobj('fsw').fswsecondclassid,$(this).getobj('fsw').fswsecondclassmc)">新增</a>
                                <a class="btn btn-default" id="btnfswmodify" style=""
                                   onclick="loadfsweditinfo($(this).getobj('fsw'))">修改</a>
                                <a class="btn btn-default" id="btnfswdel" style=""
                                   onclick="loaddelfswinfo($(this).getobj('fsw'))">删除</a>
                                <a class="btn btn-default" id="btnfswexport" style=""
                                   onclick="exportfswinfo($(this).getobj('fsw'))">导出</a>
                            </div>
                        </div>
                    </c:if>
                    <div class="row bordertop" style="height:auto;overflow: hidden;" id="fswtablerow">
                        <table id="tableFsw" class="" cellspacing="0" width="100%">
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${ui.hasPerm('MAN_SHIHDAO') || ui.hasPerm('MAN_XQHDAO')}">
    <!-- 删除航道弹窗 -->
    <div class="modal fade" id="modalhdaodel" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelhdaoDel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelhdaoDel">删除航道</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            <label class="text-warning">删除航道将同时删除航道下的航段及相关附属物信息!</label><br>
                            你确定要删除该航道&nbsp;<label id="lbmodalhdaodel" delid=""></label>&nbsp;吗？
                        </p>

                        <p id="pErrMsg" class="text-danger"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalhdaodel" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 删除航段弹窗 -->
    <div class="modal fade" id="modalhduandel" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelhduanDel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelhduanDel">删除航段</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            <label class="text-warning">删除航段将同时删除航段下的相关附属物信息!</label><br>
                            你确定要删除该航段&nbsp;<label id="lbmodalhduandel" delid=""></label>&nbsp;吗？
                        </p>

                        <p id="pErrMsg" class="text-danger"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalhduandel" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 删除附属物弹窗 -->
    <div class="modal fade" id="modalfswdel" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelfswDel">
        <div class="modal-dialog" role="document" style="width: auto">
            <div class="modal-content" style="width: 1000px;margin-left: 22%">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelfswDel">删除附属物</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <div class="row text-center">
                            <p style="width: 900px;">
                                你确定要删除该附属物&nbsp;<label id="lbmodalfswdel" delclass="" delid=""></label>&nbsp;吗？
                            </p>
                        </div>
                        <div class="row">
                            <div style="float: left"><p>
                                对照前:
                            </p>

                                <div id="delmapbefore" style="height:350px;width: 450px"></div>
                            </div>
                            <div style="float: left;margin-left: 50px;"><p>
                                对照后:
                            </p>

                                <div id="delmapafter" style="height: 350px;width: 450px"></div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalfswdel" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 新增航段弹窗 -->
    <div class="modal fade" id="modalhduannew" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelhduannew">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelhduannew">新增航段</h4>
                </div>
                <div class="modal-body" style="padding:0 0 15px 0;">
                    <div id="addhduanform" class="container-fluid auto-generate"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalhduannew" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 新增航道弹窗 -->
    <div class="modal fade" id="modalhdaonew" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelhdaonew">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelhdaonew">新增航道</h4>
                </div>
                <div class="modal-body" style="padding:0 0 15px 0;">
                    <div id="addhdaoform" class="container-fluid auto-generate"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalhdaonew" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 新增附属物弹窗 -->
    <div class="modal fade" id="modalfswnew" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelfswnew">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelfswnew"></h4>
                </div>
                <div class="modal-body" style="padding:0 0 15px 0;">
                    <div id="addfswform" class="container-fluid auto-generate"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalfswnew" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 编辑航段弹窗 -->
    <div class="modal fade" id="modalhduanedit" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelhduanedit">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelhduanedit">修改航段</h4>
                </div>
                <div class="modal-body" style="padding:0 0 15px 0;">
                    <div id="edithduanform" class="container-fluid auto-generate"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalhduanedit" type="button"
                            class="btn btn-primary">保存
                    </button>
                </div>
            </div>
        </div>
    </div>


    <!-- 编辑航道弹窗 -->
    <div class="modal fade" id="modalhdaoedit" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelhdaoedit">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelhdaoedit">修改航道</h4>
                </div>
                <div class="modal-body" style="padding:0 0 15px 0;">
                    <div id="edithdaoform" class="container-fluid auto-generate"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalhdaoedit" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 编辑附属物弹窗 -->
    <div class="modal fade" id="modalfswedit" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelfswedit">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelfswedit"></h4>
                </div>
                <div class="modal-body" style="padding:0 0 15px 0;">
                    <div id="editfswform" class="container-fluid auto-generate"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnmodalfswedit" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

</c:if>

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

<!-- 添加前后对照图弹窗 -->
<div class="modal fade" id="modaladdchart" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabeladdchart">
    <div class="modal-dialog" role="document" style="width: auto">
        <div class="modal-content" style="width: 1000px;margin-left: 22%">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabeladdchart">添加附属物</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="row text-center">
                        <p style="width: 900px;">
                            如未对附属物坐标信息(经纬度)作修改，请无视对照图！
                        </p>
                    </div>
                    <div class="row">
                        <div style="float: left"><p>
                            对照前:
                        </p>

                            <div id="addmapbefore" style="height:350px;width: 450px"></div>
                        </div>
                        <div style="float: left;margin-left: 50px;"><p>
                            对照后:
                        </p>

                            <div id="addmapafter" style="height: 350px;width: 450px"></div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnmodaladdchart" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 更新前后对照图弹窗 -->
<div class="modal fade" id="modalupdatechart" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelupdatechart">
    <div class="modal-dialog" role="document" style="width: auto">
        <div class="modal-content" style="width: 1000px;margin-left: 22%">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelupdatechart">修改附属物</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="row text-center">
                        <p style="width: 900px;">
                            如未对附属物坐标信息(经纬度)作修改，请无视对照图！
                        </p>
                    </div>
                    <div class="row">
                        <div style="float: left"><p>
                            对照前:
                        </p>

                            <div id="updatemapbefore" style="height:350px;width: 450px"></div>
                        </div>
                        <div style="float: left;margin-left: 50px;"><p>
                            对照后:
                        </p>

                            <div id="updatemapafter" style="height: 350px;width: 450px"></div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnmodalupdatechart" type="button" class="btn btn-primary">修改</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
