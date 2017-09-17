<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
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
    String szxzqhname="浙江省";
    if (dptshixzqh != null){
        szshixzqh = dptshixzqh.getId();
        szxzqhname=dptshixzqh.getName();
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
    <link rel="stylesheet" type="text/css" href="page/hdaoinfomap/hdaoinfo.css">
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
            src="page/hdaoinfomap/hdaoinfo.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
    <script type="text/javascript" language="javascript"
                src="http://172.20.24.105/WebtAPI/wapi.js"></script>
        <script type="text/javascript" language="javascript"
                src="page/hdaoinfomap/hdmap.js"></script>

</head>

<body style="min-width:1400px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="lxflag" value="">
<input type="hidden" id="hidetd" value="">
<input type="hidden" id="xzqhid" value="<%=szshixzqh%>">
<input type="hidden" id="xzqhname" value="<%=szxzqhname%>">
<%--<input type="hidden" id="maphdaoid" value="">
<input type="hidden" id="maphduanid" value="">--%>

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
            <a class="btn btn-primary active" id="tabmap">地图查询</a>
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
</div>
<div id="divleft" class="navcontentleft borderright nopadding"
     style="float:left;overflow-y:hidden;overflow-x:auto;width:350px;">
    <div class="bordertop" style="height:1px;"></div>
    <div class="row nvgrow" style="padding-left: 0px;">
        <a class="col-md-4 btn btn-default" onclick='showSearchDiv(1)' id="ahdao">航道</a>
        <a class="col-md-4 btn btn-default" onclick='showSearchDiv(2)' id="ahduan">航段</a>
        <a class="col-md-4 btn btn-default" onclick='showSearchDiv(3)' id="afsw">附属物</a>
    </div>
    <div id="hdaodiv" style="display:none;padding-top: 10px;background-color: white;">
        <div style="float: left;width: 130px;padding-left: 10px;padding-right: 10px" id="hdaosjbh">
            <select class="form-control middleinputgroup" id="selhdaosjbh">
                <script>
                    var xzqhname = $("#xzqhname").val();
                    ajax('querycity', {}, function (data) {
                        $("#selhdaosjbh").append('<option value="-1">浙江省</option>');
                        if (ifResultOK(data)) {
                            var records = getResultObj(data);
                            if (records) {
                                for (var i = 0; i < records.length; i++) {
                                    var dict = records[i];
                                    if (xzqhname == dict.name) {
                                        $("#selhdaosjbh").append('<option selected value="' + dict.id + '">' + dict.name + '</option>');
                                    } else {
                                        $("#selhdaosjbh").append('<option value="' + dict.id + '">' + dict.name + '</option>');
                                    }
                                }
                            }
                        }
                    });
                </script>
            </select>
        </div>
        <span><input type="text" class="form-control" style="float: left;width: 110px;" id="hdaoipt"
                     placeholder="请输入名称"></span>
        <span style="padding-left: 10px"><a class="btn btn-primary" onclick="hdaosearch()">搜索</a></span>
        <span style="padding-left: 10px"><div
                style="display: inline-block;width: 30px;height: 38px;float: right;margin-right: 5px;">
            <a style="color: black" onclick="loadsearchmodal()">高级<br/>搜索</a></div></span>
    </div>
    <div id="hduandiv" style="display:none;padding-top: 10px;background-color: white;">
        <div style="float: left;width: 100px;padding-left: 10px;padding-right: 10px" id="hduanxzqh">
            <script>
                var xzqhid = $("#xzqhid").val();
                $("#hduanxzqh").addselxzqh(
                        {
                            id: "selhduanxzqh",
                            defaultval:xzqhid,
                            selectfn: bindHdaolist
                        }
                );
            </script>
        </div>
          <span id="hdaolist">
            <select class="form-control middleinputgroup" style="float:left;width:110px;"
                    id="selhdaolist">
            </select>
        </span>
        <span id="hduansearchspan" style="padding-left: 5px;">
            <select class="form-control middleinputgroup" style="margin-left:10px;float:left;width:110px;"
                    id="selhduandj">
                <script>
                    ajax('dic/querydicattr', {
                        loginid: $("#userid").val(),
                        name: 'hddj'
                    }, function (data) {
                        $("#selhduandj").append(
                                '<option value="-1">航段等级</option>');
                        if (ifResultOK(data)) {
                            var records = getResultRecords(data);
                            if (records) {
                                for (var i = 0; i < records.data.length; i++) {
                                    var dict = records.data[i];
                                    $("#selhduandj").append(
                                            '<option value="' + dict.id + '">'
                                            + dict.attrdesc + '</option>');
                                }
                            }
                        }
                    });
                </script>
            </select>
        </span>
        <span><input type="text" class="form-control" style="margin-left:10px;margin-top:10px;float: left;width: 150px;"
                     id="hduanipt"
                     placeholder="请输入名称"></span>
        <span style="padding-left: 10px"><a class="btn btn-primary" style="margin-top:10px;"
                                            onclick="hduansearch()">搜索</a></span>
        <span style="padding-left: 10px"><div
                style="margin-top:7px;display: inline-block;width: 30px;height: 38px;float: right;margin-right: 85px;">
            <a style="color: black" onclick="loadsearchmodal()">高级<br/>搜索</a></div></span>
    </div>
    <div id="fswdiv" style="display:none;padding-top: 10px;background-color: white;">
        <div style="float: left;width: 100px;padding-left: 10px;" id="fswxzqh">
            <script>
                var xzqhid = $("#xzqhid").val();
                $("#fswxzqh").addselxzqh(
                        {
                            id: "selfswxzqh",
                            defaultval: xzqhid,
                            selectfn: bindFswHduan
                        }
                );
            </script>
        </div>
          <span id="fswhduan">
         <select class="form-control middleinputgroup" style="margin-left:10px;float:left;width:230px;"
                 id="selfswhduan">
         </select>
        </span>
        <span id="fswlx" style="padding-left: 5px;">
            <select class="form-control middleinputgroup"
                    style="margin-left:10px;margin-top:10px;float:left;width:110px;"
                    id="selfswlx">
                <script>
                    ajax('appurtenance/queryfsw', null, function (data) {
                        $("#selfswlx").append(
                                '<option value="-1">附属物</option>');
                        if (ifResultOK(data)) {
                            var records = getResultRecords(data);
                            if (records) {
                                for (var i = 0; i < records.data.length; i++) {
                                    var dict = records.data[i];
                                    $("#selfswlx").append(
                                            '<option value="' + dict.id + '">'
                                            + dict.name + '</option>');
                                }
                            }
                        } else {
                        }
                    });
                </script>
            </select>
        </span>
        <span><input type="text" class="form-control" style="margin-left:10px;margin-top:10px;float: left;width: 110px;"
                     id="fswipt"
                     placeholder="请输入名称"></span>
        <span style="padding-left: 8px"><a class="btn btn-primary" style="margin-top:10px;" onclick="fswsearch()">搜索</a></span>
        <span style="padding-left: 8px"><div
                style="margin-top:7px;display: inline-block;width: 30px;height: 38px;float: right;margin-right: 8px;">
            <a style="color: black" onclick="loadsearchmodal()">高级<br/>搜索</a></div></span>
    </div>

    <div style="margin-top: 15px;">
        <ol class="breadcrumb nomargin" style="background: transparent; " id="leftnavpath">
        </ol>
    </div>
    <div class="bordertop" style="height:1px;"></div>
    <div id="lefthdaohduantree" style="background:white;overflow-y:auto">
        <!-- 由js生成 -->

    </div>
    <div id="leftfswtree" style="background:white;overflow-y:auto">
        <!-- 由js生成 -->
    </div>
    <div id="leftsearchtree" style="background:white;overflow-y:auto">
        <!-- 由js生成 -->
    </div>


    <div class="bordertop" style="height:1px;"></div>
</div>
<div class="nopadding nomargin"
     style="float:left;width:15px;">
    <div id="leftmenu" expand="1" class="menuleftshouqi"
         style="height:57px;width:11px;margin-left:-1px;margin-top:300px;">
        <!-- 左侧航道导航展开折叠按钮图标 -->
    </div>
</div>
<div id="divright" class="shadow" style="background:#f0f0f0;margin: 10px 10px 10px 370px;">
    <div class="container-fluid">
        <div style="z-index:1">
            <div class="col-xs-12" id="divmap" style="height: 100%;">
                <%-- <button onclick="scrollto();">左侧定位</button>--%>
            </div>
        </div>
        <%--<div  style="z-index:2;height:auto;width:110px;position: fixed;right: 50px;top: 100px;">
            <div class="text-center" style="height: 34px;line-height: 34px;background: #1766b1;font-size: 14px;color:#ffffff; border-top-left-radius: 4px;border-bottom-left-radius:0 ;border-top-right-radius: 4px;border-bottom-right-radius:0;">新建
            </div>
            <div class="text-left" id="fswtree">
            </div>
        </div>--%>
    </div>
</div>

<!-- 搜索-->
<div class="modal fade" id="modalsearch" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width: 800px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">高级搜索</h4>
            </div>
            <div class="modal-body">
                <table id="searchtable">
                    <tr>
                        <td width="210px" id="headtd">
                        </td>
                        <td width="320px"><a class="icon-plus" onclick="addtr()"
                                             style="margin-left: 10px;text-decoration: none;color: green"></a></td>
                    </tr>
                </table>
                <div style="margin-top: 10px"><input type="button" class="btn btn-primary" value="搜索"
                                                     onclick="searchpro()"/></div>
            </div>
            <div class="navmap" style="display: none" id="navmapdiv">
                <label style="color: black;" id="labcount"></label>

                <div style="float: right;padding:10 35 0 0;"><a onclick="showMultiMap()">地图查看</a></div>
            </div>
            <div id="dtdiv" style="display: none">
                <table id="dt"></table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<%--
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

<!-- 删除附属物弹窗 -->
<div class="modal fade" id="modalfswdel" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelfswDel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelfswDel">删除航段</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <p>
                        你确定要删除该附属物&nbsp;<label id="lbmodalfswdel" delclass="" delid=""></label>&nbsp;吗？
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnmodalfswdel" type="button" class="btn btn-primary">删除</button>
            </div>
        </div>
    </div>
</div>--%>

</body>
</html>
