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

    <title>通航论证</title>

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
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/datatable/media/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/hangzheng/thlz.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/wdatepicker/WdatePicker.js"></script>
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
                <a class="btn btn-primary active" id="tabthlz">通航论证</a>
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
                    <p class="nomargin nopadding navcontenttitle">通航论证</p>
                </div>
            </div>
            <div class="row borderbottom">
                <div class="col-xs-4">
                    <c:if test="${pui.hasPerm('MAN_THLZ')}">
                        <button class="btn btn-primary" style="margin: 15px 0 0 0" onclick="newthlzload();">新建通航论证
                        </button>
                        <button class="btn btn-default" style="margin: 15px 0 0 0" onclick="delthlzsload();">删除</button>
                    </c:if>
                </div>
                <div class="col-xs-5">
                    <div class="row">
                        <div class="col-xs-3 text-right">
                            <h4 style="margin-top:22px;font-size:16px;">论证日期:</h4>
                        </div>
                        <div class="col-xs-9 text-left" id="divtimeregion"
                             style="margin: 15px 0 0 0">
                            <script>
                                $("#divtimeregion").adddayregion({
                                    idstart: 'seldaystart',
                                    hintstart: '请选择开始时间',
                                    hintend: '请选择结束时间',
                                    idend: 'seldayend',
                                    onpickedstart: 'loadthlz',
                                    onpickedend: 'loadthlz'
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
    <button class="btn btn-default" type="button" id="btnsearch" onclick="loadthlz();">搜索
    </button>
    </span>
                    </div>
                    <!-- /input-group -->
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <table id="thlztable" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th style="text-align: center;"><input
                                    id="cbselall" type="checkbox">&nbsp;序号
                            </th>
                            <th style="text-align: center;">项目名称</th>
                            <th style="text-align: center;">论证时间</th>
                            <th style="text-align: center;">参加人员</th>
                            <th style="text-align: center;">操作</th>
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
                    <h4 class="modal-title">通航论证详情</h4>
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
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增通航论证</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid nopadding">
                        <div class="row form-horizontal" style="margin-top:-15px;" id="addthlzform">
                            <div class="col-xs-12" style="background:#f0f0f0;height:40px;line-height: 40px;">
                                通航论证概况
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" style="margin-top:15px;">项目名称</label>

                                <div class="col-sm-6" style="margin-top:15px;">
                                    <input type="text" class="form-control" autoajax-name="argument.name"
                                           autoajax="true" id="inputname">
                                    <script>
                                        $("#inputname").validateTargetBind({notempty: {msg: "请输入项目名称"}});
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">论证时间</label>

                                <div class="col-sm-4" id="divatime">
                                    <script>
                                        var nowday = getTimeFmt(new Date(),
                                                'yyyy-MM-dd HH:mm:ss');
                                        $("#divatime").addtime({
                                            id: "newsellzsj",
                                            defaultval: nowday,
                                            autoajax: {
                                                name: "argument.atime"
                                            }
                                        });
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">航道</label>

                                <div class="col-sm-2" style="width: auto" id="addhdaodiv">

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">参与人员</label>

                                <div class="col-sm-6">
                                    <input type="text" class="form-control" autoajax="true"
                                           autoajax-name="argument.pname" id="inputpname">
                                    <script>
                                        $("#inputpname").validateTargetBind({notempty: {msg: "请输入参与人员"}});
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">审查意见</label>

                                <div class="col-sm-9" id="divtextareanew">
                                    <script>
                                        $("#divtextareanew").addtextarea({
                                            id: 'newtextarea',
                                            autoajax: {
                                                name: "argument.title"
                                            },
                                            validators: {
                                                notempty: {
                                                    msg: '请输入审查意见'
                                                }
                                            }
                                        });
                                    </script>
                                </div>
                            </div>
                            <div class="col-xs-12" style="background:#f0f0f0;height:40px;line-height: 40px;">
                                附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                <i class="icon-paper-clip" style="color:rgb(35, 82, 124);"></i>
                                <input type="button" class="btn btn-link" value="添加附件" style="padding-left: 0px;"
                                       onclick="addpicture('addthlzform');">
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="btnaddargument" onclick="addargument()">保存
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
                    <h4 class="modal-title">更新通航论证</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid nopadding">
                        <div class="row form-horizontal" style="margin-top:-15px;" id="updatethlzform">
                            <div class="col-xs-12" style="background:#f0f0f0;height:40px;line-height: 40px;">
                                通航论证概况
                            </div>
                            <div class="row hide">
                                <div class="col-xs-12" id="divupdateid"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" style="margin-top:15px;">项目名称</label>

                                <div class="col-sm-6" style="margin-top:15px;" id="divupdatename">
                                    <script>
                                        $("#divupdatename").addinputtext({
                                            id: 'updatename',
                                            autoajax: {
                                                name: "argument.name"
                                            }
                                        });
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">论证时间</label>

                                <div class="col-sm-4" id="divupdateatime">
                                    <script>
                                        $("#divupdateatime").addtime({
                                            id: 'updateatime',
                                            autoajax: {
                                                name: "argument.atime"
                                            }
                                        });
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">航道</label>

                                <div class="col-sm-2" style="width: auto" id="updatehdaodiv">

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">参与人员</label>

                                <div class="col-sm-6" id="divupdatepname">
                                    <script>
                                        $("#divupdatepname").addinputtext({
                                            id: 'updatepname',
                                            autoajax: {
                                                name: "argument.pname"
                                            }
                                        });
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">审查意见</label>

                                <div class="col-sm-9" id="divtextareaupdate">
                                    <script>
                                        $("#divtextareaupdate").addtextarea({
                                            id: 'updatetitle',
                                            autoajax: {
                                                name: "argument.title"
                                            }
                                        });
                                    </script>
                                </div>
                            </div>
                            <div class="col-xs-12" style="background:#f0f0f0;height:40px;line-height: 40px;">
                                附件信息 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                <i class="icon-paper-clip" style="color:rgb(35, 82, 124);"></i>
                                <input type="button" class="btn btn-link" value="添加附件" style="padding-left: 0px;"
                                       onclick="addpicture('updatethlzform');">
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="updatethlz()">保存</button>
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
                    <h4 class="modal-title">删除通航论证</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            你确定要删除项目名称为&nbsp;<label id="lbmodaldelnames"></label>&nbsp;的通航论证吗？
                        </p>

                        <p id="pErrMsg" class="text-danger"></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="btndelthlz" type="button" class="btn btn-primary">删除</button>
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
</div>
</body>
</html>
