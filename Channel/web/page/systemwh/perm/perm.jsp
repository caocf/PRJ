<%@ page import="com.channel.model.user.CXtUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
    CZdXzqhdm dptxzqh = (CZdXzqhdm) session.getAttribute("dptxzqh");
    int szshixzqh = 1;
    String szxzqhname = "浙江省";
    if (dptxzqh != null) {
        szshixzqh = dptxzqh.getId();
        szxzqhname = dptxzqh.getName();
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>角色权限</title>

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
    <link rel="stylesheet" type="text/css"
          href="common/common/css/datatable.css">
    <link rel="stylesheet" type="text/css" href="common/icheck/skins/square/blue.css" rel="stylesheet">

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
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/systemwh/perm/perm.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/icheck/icheck.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
</head>

<body style="min-width: 1200px;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="xzqhid" value="<%=szshixzqh%>">
<input type="hidden" id="xzqhname" value="<%=szxzqhname%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <jsp:include page="../systemwh-title.jsp"></jsp:include>
    <div class="row navline"></div>

    <div class="row navcontent shadow">
        <div class="col-xs-3" id="divleft">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">角色列表</p>
                </div>
            </div>
            <div id="divleftrole" class="row" style="overflow: auto;">
            </div>
            <c:if test="${ui.hasPerm('MAN_ROLEPERM')}">
                <div class="bordertop" style="margin-top:20px;">
                    <button class="btn btn-link" style="margin-top:5px;font-weight: bold;" data-toggle="modal"
                            data-target="#addrole">新增角色
                    </button>
                </div>
            </c:if>
        </div>
        <div class="col-xs-9 borderleft">
            <div id="divrightrow" class="row borderbottom"
                 style="overflow: auto;">

                <div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
                    <ul id="myTabs" class="nav nav-tabs" role="tablist">
                        <c:if test="${ui.hasPerm('MAN_ROLEPERM') || ui.hasPerm('VIEW_ROLEPERM')}">
                            <li role="presentation" class="active"><a href="#perm" id="perm-tab" role="tab"
                                                                      data-toggle="tab" aria-controls="perm"
                                                                      aria-expanded="true"
                                                                      style="font-weight: bold;">权限维护</a></li>
                        </c:if>
                        <c:if test="${ui.hasPerm('MAN_ROLEUSER') || ui.hasPerm('VIEW_ROLEUSER')}">
                            <li role="presentation" class="dropdown">
                                <a href="#" id="myTabDrop1" class="dropdown-toggle" data-toggle="dropdown"
                                   aria-controls="myTabDrop1-contents" aria-expanded="false" style="font-weight: bold;">成员维护<span
                                        class="caret"></span></a>
                                <ul class="dropdown-menu" aria-labelledby="myTabDrop1" id="myTabDrop1-contents">
                                    <c:if test="${ui.hasPerm('MAN_ROLEUSER')}">
                                        <li class=""><a href="#user" role="tab" id="user-tab" data-toggle="tab"
                                                        aria-controls="user" aria-expanded="false">成员管理</a></li>
                                    </c:if>
                                    <c:if test="${ui.hasPerm('VIEW_ROLEUSER') || ui.hasPerm('MAN_ROLEUSER')}">
                                        <li class=""><a href="#userlist" role="tab" id="userlist-tab" data-toggle="tab"
                                                        aria-controls="userlist" aria-expanded="false">成员列表</a></li>
                                    </c:if>
                                </ul>
                            </li>
                        </c:if>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                        <c:if test="${ui.hasPerm('MAN_ROLEPERM') || ui.hasPerm('VIEW_ROLEPERM')}">
                            <div role="tabpanel" class="tab-pane fade active in" id="perm" aria-labelledby="perm-tab">
                                <div class="container-fluid">
                                    <c:if test="${ui.hasPerm('MAN_ROLEPERM')}">
                                        <div class="row" style="margin-top:5px;">
                                            <button class="btn btn-primary" style="margin-left:20px;"
                                                    id="btnSaveRolePerms"
                                                    onclick="saveRolePerms();">保存更改
                                            </button>
                                        </div>
                                    </c:if>
                                    <div class="row bordertop" style="margin-top:5px;">
                                        <div class="col-xs-3">
                                            <div class="row borderbottom">
                                                <div class="col-xs-12">
                                                    <p class="nomargin nopadding navcontenttitle">权限分组</p>
                                                </div>
                                            </div>
                                            <div id="divleftgroup" class="row" style="overflow: auto;">
                                            </div>
                                        </div>
                                        <div class="col-xs-9 borderleft borderright">
                                            <div class="row borderbottom">
                                                <div class="col-xs-12">
                                                    <p class="nomargin nopadding navcontenttitle">权限信息</p>
                                                </div>
                                            </div>
                                            <div class="row borderbottom">

                                            </div>
                                            <div id="divrightperm" class="row borderbottom"
                                                 style="overflow: auto;">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${ui.hasPerm('MAN_ROLEUSER')}">
                            <div role="tabpanel" class="tab-pane fade" id="user" aria-labelledby="user-tab">
                                <div class="container-fluid">
                                    <div class="row" style="margin-top:5px;">
                                        <button class="btn btn-primary" style="margin-left:20px;" id="btnSaveRoleUsers"
                                                onclick="saveRoleUsers();">保存更改
                                        </button>
                                    </div>
                                    <div class="row bordertop" style="margin-top:5px;">
                                        <div class="col-xs-4">
                                            <div class="row borderbottom">
                                                <div class="col-xs-12">
                                                    <p class="nomargin nopadding navcontenttitle">组织机构</p>
                                                </div>
                                            </div>
                                            <div id="divleftdpt" class="row" style="overflow: auto;">

                                            </div>
                                        </div>
                                        <div class="col-xs-8 borderleft borderright">
                                            <div class="row borderbottom">
                                                <div class="col-xs-12">
                                                    <p class="nomargin nopadding navcontenttitle">用户列表</p>
                                                </div>
                                            </div>
                                            <div id="divrightuser" class="row borderbottom"
                                                 style="overflow: auto;">
                                                <table id="tableUser" class="" cellspacing="0" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th width="10%"><input id="cbselall" type="checkbox"
                                                                               onclick="selalluser();">&nbsp;序号
                                                        </th>
                                                        <th width="10%">用户名</th>
                                                        <th width="10%">职务</th>
                                                        <th width="10%">用户状态</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="tableUserbody">

                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${ui.hasPerm('VIEW_ROLEUSER')  || ui.hasPerm('MAN_ROLEUSER')}">
                            <div role="tabpanel" class="tab-pane fade" id="userlist" aria-labelledby="userlist-tab">
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-xs-12 borderleft borderright">
                                            <div class="row borderbottom">
                                                <div class="col-xs-12">
                                                    <p class="nomargin nopadding navcontenttitle">用户列表</p>
                                                </div>
                                            </div>
                                            <div id="divrightuserlist" class="row borderbottom"
                                                 style="overflow: auto;">
                                                <table id="tableUserlist" class="" cellspacing="0" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th width="10%">序号</th>
                                                        <th width="10%">用户名</th>
                                                        <th width="10%">职务</th>
                                                        <th width="10%">用户状态</th>
                                                        <th width="10%">操作</th>
                                                    </tr>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<div class="modal fade" id="addrole" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增角色</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal" id="addroleform">
                        <div class="form-group">
                            <label for="addrolename" class="col-sm-2 control-label">角色名</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="addrolename"
                                       autoajax="true" autoajax-name="rolename"
                                       placeholder="请输入权限角色名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label">行政区划</label>

                            <div class="col-xs-4" id="divseladdxzqh">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="addrole();">增加</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="updaterole" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">编辑角色</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal" id="updateroleform">
                        <input type="hidden" id="updateroleid" autoajax="true" autoajax-name="roleid">

                        <div class="form-group">
                            <label for="updaterolename" class="col-sm-2 control-label">角色名</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="updaterolename"
                                       autoajax="true" autoajax-name="rolename"
                                       placeholder="请输入权限角色名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label">行政区划</label>

                            <div class="col-xs-4" id="divselupdatexzqh">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="updaterole();">修改</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
