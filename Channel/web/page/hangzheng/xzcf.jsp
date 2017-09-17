<%@ page import="com.channel.model.user.CXtUser" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
<%@ page import="com.channel.model.user.CXtDpt" %>
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
    int szshixzqh = -2;
    if (dptshixzqh != null)
        szshixzqh = dptshixzqh.getId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>行政处罚</title>

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
            src="page/hangzheng/xzcf.js"></script>
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
<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">
<input type="hidden" id="szshixzqh" value="<%=szshixzqh%>">

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
                <a class="btn btn-primary active" id="tabxzcf">行政处罚</a>
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
                    <p class="nomargin nopadding navcontenttitle">行政处罚</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-9"></div>
               <%-- <div class="col-xs-4">
                   <div class="row">
                        <div class="col-xs-6 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">管理单位:</h4>
                        </div>
                        <div class="col-xs-6 text-left" style="margin: 15px 0 15px 0"
                             id="divseldpt">
                           <<script>
                                $("#divseldpt").addseldpt({
                                    id: 'searchdept',
                                    autoajax: {
                                        name: 'dept'
                                    },
                                    validators: {
                                        notempty: {
                                            msg: '请输入单位'
                                        }
                                    }, defaultval: <%=szshiju%>
                                });
                            </script>
                        </div>
                    </div>-
                </div>--%>
                <div class="col-xs-3">
                    <div class="input-group" style="margin: 15px 0 15px 0">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false" id="selsearchtype" searchtype="0"><label
                                    class="nomargin" id="selsearchlabel">请选择</label><span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li>
                                    <a onclick="$('#selsearchtype').attr('searchtype',1);$('#selsearchlabel').text($(this).text())">受理号</a>
                                </li>
                                <li>
                                    <a onclick="$('#selsearchtype').attr('searchtype',2);$('#selsearchlabel').text($(this).text())">中文船名</a>
                                </li>
                            </ul>
                        </div>
                        <input type="text" class="form-control" aria-label="..." id="inputcontent">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button" id="btnsearch" onclick="loadxzcf();">搜索
                            </button>
                        </span>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <table id="xzcftable" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th width="10%" style="text-align: center;">序号</th>
                            <th width="10%" style="text-align: center;">受理号</th>
                            <th width="30%" style="text-align: center;">中文船名</th>
                            <th width="30%" style="text-align: center;">案由</th>
                            <th width="10%" style="text-align: center;">受理日期</th>
                            <th width="10%" style="text-align: center;">操作</th>
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
                    <h4 class="modal-title">查看行政处罚详情</h4>
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
