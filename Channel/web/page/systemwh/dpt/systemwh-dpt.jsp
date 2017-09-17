<%@ page import="com.channel.model.user.CXtUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.channel.model.user.CZdXzqhdm" %>
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
    CXtDpt dpt = (CXtDpt) session.getAttribute("dpt");
    int szdpt = -1;
    if (dpt != null)
        szdpt = dpt.getId();

    CXtDpt shiju = (CXtDpt) session.getAttribute("shiju");
    int szshiju = -1;
    if (shiju != null)
        szshiju = shiju.getId();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>组织机构</title>

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
          href="page/systemwh/dpt/systemwh-dpt.css">
    <link rel="stylesheet" type="text/css"
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">

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
            src="page/systemwh/dpt/systemwh-dpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/loaddpt.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>


</head>
<body>

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">
<input type="hidden" id="szshiju" value="<%=szshiju%>">
<input type="hidden" id="szdpt" value="<%=szdpt%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <jsp:include page="../systemwh-title.jsp"></jsp:include>
    <div class="row navline"></div>

    <div class="row navcontent shadow">
        <div class="col-xs-3">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">组织机构</p>
                </div>
            </div>
            <div id="divleftdpt" class="row" style="overflow: auto;">
            </div>
        </div>
        <div class="col-xs-9 borderleft">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">基本信息</p>
                </div>
            </div>
            <c:if test="${ui.hasPerm('MAN_SHENDPT') || ui.hasPerm('MAN_SHIDPT')}">
                <div class="row borderbottom">
                    <div class="col-xs-12" style="padding: 10px 0 10px 10px;">
                        <button id="btnAdd" class="btn btn-primary hide" data-toggle="modal"
                                data-target="#AddNew">新建
                        </button>
                        <button id="btnEdit" class="btn btn-primary hide" style=""
                                data-toggle="modal" data-target="#UpdateDpt">修改
                        </button>
                        <button id="btnDelete" class="btn btn-default hide" style=""
                                data-toggle="modal" data-target="#Del">删除
                        </button>
                    </div>
                </div>
            </c:if>
            <div id="divrightrow" class="row borderbottom"
                 style="overflow: auto;">
                <table class="table">
                    <tr>
                        <td width="20%" class="text-right borderright">机构名称</td>
                        <td id="inputName" style="padding-left:20px;"></td>
                    </tr>
                    <tr>
                        <td class="text-right borderright">所属行政区划</td>
                        <td id="inputXzqh" style="padding-left:20px;"></td>
                    </tr>
                    <tr>
                        <td class="text-right borderright">业务主管机构</td>
                        <td id="inputPdpt" style="padding-left:20px;"></td>
                    </tr>
                    <tr class="borderbottom">
                        <td class="text-right borderright">机构描述</td>
                        <td id="taDesc" style="padding-left:20px;"></td>
                    </tr>
                    <tr class="borderbottom">
                        <td class="text-right borderright">管理行政区划区域</td>
                        <td id="tadefaultxzqhs" style="padding-left:20px;"></td>
                    </tr>
                    <tr class="borderbottom">
                        <td class="text-right borderright">机构用户默认角色</td>
                        <td id="tadefaultroles" style="padding-left:20px;"></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="Del" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelDel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelDel">删除</h4>
            </div>
            <div class="modal-body">
                <div class="container">
                    <p>
                        你确定要删除该组织机构&nbsp;<label id="lbdel" delname="" delid=""></label>&nbsp;吗？
                    </p>

                    <p id="pErrMsg" class="text-danger"></p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnDel" type="button" class="btn btn-primary">删除</button>
            </div>
        </div>
    </div>
</div>

<!-- 新增弹窗 -->
<div class="modal fade" id="AddNew" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelAdd">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelAdd">新增组织机构</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal" id="formadddpt">
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label">机构名称</label>

                            <div class="col-xs-4">
                                <input id="inputDptName" type="text" class="form-control" style="color:#333333">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label">所属行政区划</label>

                            <div class="col-xs-4" id="divseladdxzqh">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label" style="color:#333333">机构描述</label>

                            <div class="col-xs-8">
                                <textarea class="form-control" rows="8" placeholder="请输入机构描述"
                                          id="txadptdesc"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label" style="color:#333333">管理行政区划区域</label>

                            <div class="col-xs-4" id="divadddeptxzqh">

                            </div>
                        </div>
                        <c:if test="${ui.hasPerm('MAN_DPTDEFAULTROLE')}">
                            <div class="form-group">
                                <label class="col-xs-2 text-right control-label" style="color:#333333">机构用户默认角色</label>

                                <div class="col-xs-4" id="divseldefaultroles">

                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnSave" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>


<!-- 编辑视窗-->
<div class="modal fade" id="UpdateDpt" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelUpdate">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabelUpdate">修改组织机构</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-horizontal" id="formupdatedpt">
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label">机构名称</label>

                            <div class="col-xs-4">
                                <input id="inputDptNameupdate" type="text" class="form-control" style="color:#333333">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label">所属行政区划</label>

                            <div class="col-xs-4" id="divselupdatexzqh">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label" style="color:#333333">机构描述</label>

                            <div class="col-xs-8">
                                <textarea class="form-control" rows="8" placeholder="请输入机构描述"
                                          id="txadptdescupdate"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2 text-right control-label" style="color:#333333">管理行政区划区域</label>

                            <div class="col-xs-4" id="divupdatedeptxzqh">

                            </div>
                        </div>
                        <c:if test="${ui.hasPerm('MAN_DPTDEFAULTROLE')}">
                            <div class="form-group">
                                <label class="col-xs-2 text-right control-label" style="color:#333333">机构用户默认角色</label>

                                <div class="col-xs-4" id="divselupdatedefaultroles">

                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <input type="hidden" name="selidupdate" id="selidupdate" value="">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="btnUpdate" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
