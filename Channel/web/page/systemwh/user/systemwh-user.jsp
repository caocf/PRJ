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


    CXtDpt shiju = (CXtDpt) session.getAttribute("shiju");
    int szshiju = -1;
    if (shiju != null)
        szshiju = shiju.getId();

    CXtDpt chuju = (CXtDpt) session.getAttribute("chuju");
    int szchuju = -1;
    if (chuju != null)
        szchuju = chuju.getId();


    CXtDpt szjudpt = (CXtDpt) session.getAttribute("dpt");
    int szju = -1;
    if (szjudpt != null)
        szju = szjudpt.getId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>用户管理</title>

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
          href="page/systemwh/user/systemwh-user.css">
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
            src="page/systemwh/systemwh.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/systemwh/user/systemwh-user.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>

</head>

<body  style="min-width: 1200px;overflow-x: auto;">

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szchuju" value="<%=szchuju%>">
<input type="hidden" id="szju" value="<%=szju%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <jsp:include page="../systemwh-title.jsp"></jsp:include>
    <div class="row navline"></div>

    <div class="row navcontent shadow">
        <div class="col-xs-3 borderright">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">组织机构</p>
                </div>
            </div>
            <div class="row" id="divleftdpt" style="overflow:auto;">

            </div>
        </div>
        <div class="col-xs-9 ">
            <div class="row">
                <div class="col-xs-12 borderbottom">
                    <p class="nomargin nopadding navcontenttitle">用户列表</p>
                </div>
            </div>
            <div class="row borderbottom">
                <c:if test="${ui.hasPerm('MAN_SHENUSER') || ui.hasPerm('MAN_SHIUSER') || ui.hasPerm('MAN_CHUUSER') || ui.hasPerm('MAN_SELFUSER')}">
                    <div class="col-xs-4 text-left">
                        <input type="button" class="btn btn-primary" value="新增用户"
                               data-toggle="modal" data-target="#AddUser"
                               style="margin:10px 0 10px 0"> <input id="btnDelusers"
                                                                    type="button" class="btn btn-default"
                                                                    style="margin:10px 0 10px 0" value="删除">
                    </div>
                </c:if>
                <div class="col-xs-4 pull-right text-right">
                    <div id="divSearch" class="input-group"
                         style="margin:10px 0 10px 0">
                        <input id="inputContent" type="text" class="form-control"
                               placeholder="请输入用户名或姓名" aria-describedby="basic-addon2">
							<span id="btnSearch" class="btn input-group-addon"
                                  id="basic-addon2">搜索</span>
                    </div>
                </div>
            </div>
            <div class="row borderright borderbottom" id="divrightrow"
                 style="overflow:auto;">
                <!-- datatable -->
                <table id="tableUser" class="" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th width="10%"><input id="cbselall" type="checkbox">&nbsp;序号</th>
                        <th width="12%">用户名</th>
                        <th width="28%">所在机构</th>
                        <th width="10%">职务</th>
                        <th width="12%">用户状态</th>
                        <th width="28%">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- 新增用户弹窗 -->
<div class="modal fade" id="AddUser" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelAdd">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelAdd">新增用户</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            用户名
                        </div>
                        <div id="divUsername" class="col-xs-2 text-left">
                            <input id="inputUsername" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pUsernameErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            密码
                        </div>
                        <div id="divPassword" class="col-xs-2 text-left">
                            <input id="inputPassword" type="password" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pPasswordErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            姓名
                        </div>
                        <div id="divName" class="col-xs-2 text-left">
                            <input id="inputName" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pNameErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            职务
                        </div>
                        <div class="col-xs-2 text-left">
                            <div class="dropdown " id="divTitle">
                                <button class="btn btn-default dropdown-toggle btn-block"
                                        type="button" id="btnTitle" seltitleid="-1"
                                        data-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">
                                    <p id="pTitle" class="pull-left" style="margin: 0; padding:0;">请选择职务</p>
                                    <span class="caret pull-right" style="margin-top:8px;"></span>
                                </button>
                                <ul id="ulTitle" class="dropdown-menu ultitle"
                                    aria-labelledby="btnTitle">
                                </ul>
                            </div>
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pTitleErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            在职状态
                        </div>
                        <div class="col-xs-2 text-left">
                            <div class="dropdown" id="divJstatus1">
                                <button class="btn btn-default dropdown-toggle btn-block"
                                        type="button" id="btnJstatus1" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    <p id="pJstatus1" class="pull-left"
                                       style="margin: 0; padding:0;"></p>
                                    <span class="caret pull-right" style="margin-top:8px;"></span>
                                </button>
                                <ul id="jstatusul1" class="dropdown-menu ultitle"
                                    aria-labelledby="divJstatus1">
                                </ul>
                            </div>
                        </div>
                        <div class="col-xs-2 text-left">
                            <div class="dropdown" id="divJstatus2">
                                <button class="btn btn-default dropdown-toggle btn-block"
                                        type="button" id="btnJstatus2" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    <p id="pJstatus2" class="pull-left"
                                       style="margin: 0; padding:0;"></p>
                                    <span class="caret pull-right" style="margin-top:8px;"></span>
                                </button>
                                <ul id="jstatusul2" class="dropdown-menu ultitle"
                                    aria-labelledby="divJstatus2">
                                </ul>
                            </div>
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pSeljstatusErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            联系电话
                        </div>
                        <div id="divPhone" class="col-xs-2 text-left">
                            <input id="inputPhone" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pPhoneErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            电子邮件
                        </div>
                        <div id="divEmail" class="col-xs-2 text-left">
                            <input id="inputEmail" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pEmailErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            <p style="margin-left:-30px;">执法证编号</p>
                        </div>
                        <div id="divLawid" class="col-xs-2 text-left">
                            <input id="inputLawid" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pLawidErr" class="text-danger hide"></h5>
                        </div>
                    </div>

                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            <p style="margin-left:-30px;">用户角色</p>
                        </div>
                        <div id="divUserRole" class="col-xs-4 text-left">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnAddUser" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>


<!-- 查看用户弹窗 -->
<div class="modal fade" id="ViewUser" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelView">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelView">查看用户</h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered table-striped" style="margin-bottom: 0px;">
                    <tr style="height:46px;">
                        <td class="text-right" width="25%" style="height:34px;line-height: 34px;">用户名</td>
                        <td class="text-left" width="100%" id="pUsername"></td>
                    </tr>
                    <tr style="height:46px;">
                        <td class="text-right" style="height:34px;line-height: 34px;">密码</td>
                        <td class="text-left"><label id="pPassword">******&nbsp;&nbsp;</label>
                    </tr>
                    <tr style="height:46px;">
                        <td class="text-right" style="height:34px;line-height: 34px;">姓名</td>
                        <td class="text-left" id="pName"></td>
                    </tr>
                    <tr style="height:46px;">
                        <td class="text-right" style="height:34px;line-height: 34px;">职务</td>
                        <td class="text-left" id="pTitleView"></td>
                    </tr>
                    <tr style="height:46px;">
                        <td class="text-right" style="height:34px;line-height: 34px;">在职状态</td>
                        <td class="text-left" id="pJstatus"></td>
                    </tr>
                    <tr style="height:46px;">
                        <td class="text-right" style="height:34px;line-height: 34px;">联系电话</td>
                        <td class="text-left" id="pPhone"></td>
                    </tr>
                    <tr style="height:46px;">
                        <td class="text-right" style="height:34px;line-height: 34px;">电子邮件</td>
                        <td class="text-left" id="pEmail"></td>
                    </tr>
                    <tr style="height:46px;">
                        <td class="text-right" style="height:34px;line-height: 34px;">执法证编号</td>
                        <td class="text-left" id="pLawid"></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>


<!-- 编辑用户弹窗 -->
<div class="modal fade" id="EditUser" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelEdit">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelEdit">修改用户</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <input type="hidden" id="edituserid">

                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            用户名
                        </div>
                        <div id="divUsernameEdit" class="col-xs-2 text-left">
                            <input id="inputUsernameEdit" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pUsernameEditErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            密码
                        </div>
                        <div id="divPasswordEdit" class="col-xs-2 text-left">
                            <input id="inputPasswordEdit" type="password" ifchanged="0"
                                   class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pPasswordEditErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            姓名
                        </div>
                        <div id="divNameEdit" class="col-xs-2 text-left">
                            <input id="inputNameEdit" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pNameEditErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            职务
                        </div>
                        <div class="col-xs-2 text-left">
                            <div class="dropdown " id="divTitleEdit">
                                <button class="btn btn-default dropdown-toggle btn-block"
                                        type="button" id="btnTitleEdit" seltitleid="-1"
                                        data-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">
                                    <p id="pTitleEdit" class="pull-left"
                                       style="margin: 0; padding:0;">请选择职务</p>
                                    <span class="caret pull-right" style="margin-top:8px;"></span>
                                </button>
                                <ul id="ulTitleEdit" class="dropdown-menu ultitle"
                                    aria-labelledby="btnTitleEdit">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            在职状态
                        </div>
                        <div class="col-xs-2 text-left">
                            <div class="dropdown" id="divJstatusEdit1">
                                <button class="btn btn-default dropdown-toggle btn-block"
                                        type="button" id="btnJstatusEdit1" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    <p id="pJstatusEdit1" class="pull-left"
                                       style="margin: 0; padding:0;"></p>
                                    <span class="caret pull-right" style="margin-top:8px;"></span>
                                </button>
                                <ul id="jstatusulEdit1" class="dropdown-menu ultitle"
                                    aria-labelledby="divJstatusEdit1">
                                </ul>
                            </div>
                        </div>
                        <div class="col-xs-2 text-left">
                            <div class="dropdown" id="divJstatusEdit2">
                                <button class="btn btn-default dropdown-toggle btn-block"
                                        type="button" id="btnJstatusEdit2" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    <p id="pJstatusEdit2" class="pull-left"
                                       style="margin: 0; padding:0;"></p>
                                    <span class="caret pull-right" style="margin-top:8px;"></span>
                                </button>
                                <ul id="jstatusulEdit2" class="dropdown-menu ultitle"
                                    aria-labelledby="divJstatusEdit2">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            联系电话
                        </div>
                        <div id="divPhoneEdit" class="col-xs-2 text-left">
                            <input id="inputPhoneEdit" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pPhoneEditErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            电子邮件
                        </div>
                        <div id="divEmailEdit" class="col-xs-2 text-left">
                            <input id="inputEmailEdit" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pEmailEditErr" class="text-danger hide"></h5>
                        </div>
                    </div>
                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            <p style="margin-left:-30px;">执法证编号</p>
                        </div>
                        <div id="divLawidEdit" class="col-xs-2 text-left">
                            <input id="inputLawidEdit" type="text" class="form-control">
                        </div>
                        <div class="col-xs-2 text-left">
                            <h5 id="pLawidEditErr" class="text-danger hide"></h5>
                        </div>
                    </div>

                    <div class="row adddialogpadding">
                        <div class="col-xs-1 text-right" style="height:34px;line-height: 34px;">
                            <p style="margin-left:-30px;">用户角色</p>
                        </div>
                        <div id="divUpdateUserRole" class="col-xs-4 text-left">

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnEditUser" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modaldelusers" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelDelUsers">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelDelUsers">删除用户</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <p>
                        你确定要删除以下用户 &nbsp;<label id="lbdelusers" delname="" delid=""></label>&nbsp;吗？
                    </p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnDelUser" type="button" class="btn btn-primary">删除</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
