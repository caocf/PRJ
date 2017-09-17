<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.channel.model.user.CXtUser" %>
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
    CZdXzqhdm dptshixzqh = (CZdXzqhdm) session.getAttribute("dptshixzqh");
    int szshixzqh = 1;
    String szxzqhname="浙江省";
    if (dptshixzqh != null){
        szshixzqh = dptshixzqh.getId();
        szxzqhname=dptshixzqh.getName();
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Page</title>

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
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/systemwh/perm/PM.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/icheck/icheck.min.js"></script>


</head>

<body style="min-width: 1200px;">


<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<script>
    <c:if test="${!ui.hasPerm('MAN_PERM')}">
    window.location.href = $('#basePath').val() + "/page/nopermPage.jsp";
    </c:if>
</script>
<div class="container-fluid">
    <div class="row navrow">
        <div class="col-xs-2">
            <i id="back" class="icon-circle-arrow-left icon-2x pull-left" onclick="window.close();"></i> <label
                class="pull-left">权限管理(<label style="color: #f00000;;font-weight: bold;">慎用</label>)</label>
        </div>
    </div>
    <div class="row navline"></div>

    <div class="row navcontent shadow">
        <div class="col-xs-3">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">权限分组</p>
                </div>
            </div>
            <div id="divleftgroup" class="row" style="overflow: auto;">
            </div>
            <div class="bordertop" style="margin-top:20px;">
                <button class="btn btn-link" style="margin-top:5px;font-weight: bold;" data-toggle="modal"
                        data-target="#addgroup">新增分组
                </button>
            </div>
        </div>
        <div class="col-xs-9 borderleft">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">权限信息</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-12" style="padding: 10px 0 10px 10px;">
                    <button class="btn btn-primary" data-toggle="modal" id="btnnewperm"
                            data-target="#addperm">新建
                    </button>
                    <button class="btn btn-default" style="" id="btnupdateperm"
                            data-toggle="modal" data-target="#updateperm">修改
                    </button>
                    <button class="btn btn-default" style="" id="btndelperm"
                            data-toggle="modal" data-target="#delperm">删除
                    </button>
                </div>
            </div>
            <div id="divrightrow" class="row borderbottom"
                 style="overflow: auto;">

            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="addgroup" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增分组</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal" id="addgroupform">
                        <div class="form-group">
                            <label for="newgroupname" class="col-sm-2 control-label">所属分组</label>

                            <div class="col-sm-10">
                                <select class="form-control" id="addgroupssfz">
                                    <option value="0">无所属分组</option>
                                    <option value="1">当前所选分组</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newgroupname" class="col-sm-2 control-label">组名</label>

                            <div class="col-sm-10">
                                <input type="test" class="form-control" id="newgroupname"
                                       autoajax="true" autoajax-name="groupname"
                                       placeholder="请输入权限分组名">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="addgroup();">增加</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="updategroup" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">更新分组</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal" id="updategroupform">
                        <div class="form-group">
                            <label for="updategroupname" class="col-sm-2 control-label">组名</label>

                            <div class="col-sm-10">
                                <input type="hidden" id="updategroupid" autoajax="true" autoajax-name="groupid">
                                <input type="test" class="form-control" id="updategroupname"
                                       autoajax="true" autoajax-name="groupname"
                                       placeholder="请输入权限分组名">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="updategroup();">修改</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="addperm" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新增权限</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal" id="addpermform">
                        <input id="addpermgroupid" type="hidden" autoajax="true" autoajax-name="groupid">

                        <div class="form-group">
                            <label for="addpermcode" class="col-sm-2 control-label">权限代码</label>

                            <div class="col-sm-10">
                                <input type="test" class="form-control" id="addpermcode"
                                       autoajax="true" autoajax-name="permcode"
                                       placeholder="请输入权限代码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="addpermdesc" class="col-sm-2 control-label">权限说明</label>

                            <div class="col-sm-10">
                                <input type="test" class="form-control" id="addpermdesc"
                                       autoajax="true" autoajax-name="permdesc"
                                       placeholder="请输入权限说明">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="addperm();">增加</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="updateperm" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改权限</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal" id="updatepermform">
                        <input id="updatepermid" type="hidden" autoajax="true" autoajax-name="permid">

                        <div class="form-group">
                            <label for="updatepermcode" class="col-sm-2 control-label">权限代码</label>

                            <div class="col-sm-10">
                                <input type="test" class="form-control" id="updatepermcode"
                                       autoajax="true" autoajax-name="permcode"
                                       placeholder="请输入权限代码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="updatepermdesc" class="col-sm-2 control-label">权限说明</label>

                            <div class="col-sm-10">
                                <input type="test" class="form-control" id="updatepermdesc"
                                       autoajax="true" autoajax-name="permdesc"
                                       placeholder="请输入权限说明">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="updateperm();">修改</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="delperm" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改权限</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <label>您确定要删除所选的权限吗？</label>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" onclick="delperm();">删除</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
