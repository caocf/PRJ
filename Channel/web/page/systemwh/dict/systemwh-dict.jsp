<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.channel.model.user.CXtUser" %>
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
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>数据字典</title>

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
            src="page/systemwh/dict/systemwh-dict.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/tree.js"></script>

</head>

<body>

<jsp:include page="/page/common/js/permisson.jsp"></jsp:include>

<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<jsp:include page="/page/common/js/leftpopmenu.jsp"></jsp:include>

<div class="container-fluid">
    <jsp:include page="../systemwh-title.jsp"></jsp:include>
    <div class="row navline"></div>

    <div class="row navcontent shadow">
        <div class="col-xs-2">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">字典列表</p>
                </div>
            </div>
            <div id="divleftrow" class="row" style="overflow: auto;">
                <div class="col-xs-12 nopadding" id="divdictlist">
                    <!-- 由js生成字典列表 -->
                </div>
            </div>
        </div>
        <div class="col-xs-10 borderleft" id="divright">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">字典信息</p>
                </div>
            </div>
            <c:if test="${ui.hasPerm('MAN_DICT')}">
                <div class="row borderbottom">
                    <div class="col-xs-12" style="padding: 10px 0 10px 10px;">
                        <button class="btn btn-primary" onclick="newdictload();">新建</button>
                    </div>
                </div>
            </c:if>
            <div id="divrightrow" class="row borderbottom"
                 style="overflow: auto;">
                <table class="table borderbottom" width="100%" cellpadding="0px"
                       cellspacing="0px">
                    <thead>
                    <tr>
                        <th class="text-center" style="font-size: 15px;">序号</th>
                        <th class="text-center" style="font-size: 15px;">字典数据</th>
                        <th class="text-center" style="font-size: 15px;">创建时间</th>
                        <th class="text-center" style="font-size: 15px;">更新时间</th>
                        <c:if test="${ui.hasPerm('MAN_DICT')}">
                            <th class="text-center" style="font-size: 15px;">操作</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody id="tablerighttbody">

                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-xs-10 borderleft" id="divrightzzzt">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">在职状态信息</p>
                </div>
            </div>
            <c:if test="${ui.hasPerm('MAN_DICT')}">
                <div class="row borderbottom">
                    <div class="col-xs-12" style="padding: 10px 0 10px 10px;">
                        <button class="btn btn-primary" onclick="newzzztdictload();">新建</button>
                    </div>
                </div>
            </c:if>
            <div id="divrightzzztrow" class="row" style="overflow: auto;">
                <table class="table borderbottom" width="100%" cellpadding="0px"
                       cellspacing="0px">
                    <thead>
                    <tr>
                        <th class="text-center" style="font-size: 15px;">序号</th>
                        <th class="text-center" style="font-size: 15px;">在职状态</th>
                        <th class="text-center" style="font-size: 15px;">在职详情</th>
                        <th class="text-center" style="font-size: 15px;">创建时间</th>
                        <th class="text-center" style="font-size: 15px;">更新时间</th>
                        <c:if test="${ui.hasPerm('MAN_DICT')}">
                            <th class="text-center" style="font-size: 15px;">操作</th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody id="tablerighttzzztbody">

                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-xs-10 borderleft" id="divrightxzqh">
            <div class="row borderbottom">
                <div class="col-xs-12">
                    <p class="nomargin nopadding navcontenttitle">行政区划信息</p>
                </div>
            </div>

            <div class="row" style="overflow: auto;">
                <div class="col-xs-4 borderright nomargin nopadding" id="divxzqhlist">
                    <!-- 由js生成行政区划列表 -->
                </div>
                <div class="col-xs-8">
                    <c:if test="${ui.hasPerm('MAN_DICT')}">
                        <div class="row borderbottom">
                            <div class="col-xs-12" style="padding: 10px 0 10px 10px;">
                                <button class="btn btn-primary" id="newxzqhbtn"
                                        onclick="newxzqhload();" style="display:none;">新建
                                </button>
                                <button class="btn btn-default" id="updatexzqhbtn"
                                        onclick="updatexzqhload();" style="display:none;">修改
                                </button>
                                <button class="btn btn-default" id="delxzqhbtn"
                                        onclick="delxzqhload();" style="display:none;">删除
                                </button>
                            </div>
                        </div>
                    </c:if>
                    <div class="row" style="overflow: auto;">
                        <table class="table">
                            <tbody>
                            <tr>
                                <td class="text-right borderright">行政区划名称</td>
                                <td id="xzqhname" style="padding-left:20px;"></td>
                            </tr>
                            <tr>
                                <td width="20%" class="text-right borderright">行政区划代码</td>
                                <td id="xzqhcode" style="padding-left:20px;"></td>
                            </tr>
                            <tr>
                                <td class="text-right borderright">创建时间</td>
                                <td id="createtime" style="padding-left:20px;"></td>
                            </tr>
                            <tr class="borderbottom">
                                <td class="text-right borderright">更新时间</td>
                                <td id="updatetime" style="padding-left:20px;"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${ui.hasPerm('MAN_DICT')}">
    <div class="modal fade" id="dialogdel" tabindex="-1" role="dialog"
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
                            你确定要删除该字典数据&nbsp;<label id="lbdel" delname="" delid=""></label>&nbsp;吗？
                        </p>
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
    <div class="modal fade" id="dialogaddnew" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelAdd">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelAdd">新增字典数据</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="form-horizontal" id="divaddform">
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">字典类型</label>
                                    <label class="col-xs-6 control-label" style="text-align: left;"
                                           id="newdicttype">字典类型</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">字典名称</label>

                                    <div class="col-xs-8">
                                        <input type="text" class="form-control" id="newdictname"
                                               placeholder="请输入字典名称">
                                    </div>
                                </div>
                            </div>
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
    <div class="modal fade" id="dialogupdate" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabelUpdate">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabelUpdate">修改字典数据</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="form-horizontal" id="divupdateform">
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">字典类型</label>
                                    <label class="col-xs-6 control-label" style="text-align: left;"
                                           id="updatedicttype">字典类型</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">字典名称</label>

                                    <div class="col-xs-8">
                                        <input type="text" class="form-control" id="updatedictname"
                                               placeholder="请输入字典名称">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnUpdate" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 新增弹窗 -->
    <div class="modal fade" id="dialogaddnewxzqh" tabindex="-1"
         role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增行政区划</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="form-horizontal" id="divaddxzqhform">
                                <div class="form-group" id="ssxzqh">
                                    <label class="col-xs-4 control-label">所属行政区划</label> <label
                                        class="col-xs-6 control-label" style="text-align: left;"
                                        id="newssxzqh"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">行政区划名称</label>

                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="newxzqhname"
                                               placeholder="请输入行政区划名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">行政区划代码</label>

                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="newxzqhcode"
                                               placeholder="请输入行政区划代码">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnSavexzqh" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="dialogxzqhdel" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            你确定要删除该行政区划&nbsp;<label id="lbxzqhdel"></label>&nbsp;吗？
                        </p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnxzqhDel" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 编辑视窗-->
    <div class="modal fade" id="dialogupdatexzqh" tabindex="-1"
         role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">修改行政区划</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="form-horizontal" id="divupdatexzqhform">
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">行政区划名称</label>

                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="updatexzqhname"
                                               placeholder="请输入行政区划名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">行政区划代码</label>

                                    <div class="col-xs-6">
                                        <input type="text" class="form-control" id="updatexzqhcode"
                                               placeholder="请输入行政区划代码">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnUpdatexzqh" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 新增弹窗 -->
    <div class="modal fade" id="dialogaddnewzzzt" tabindex="-1"
         role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增在职状态</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="form-horizontal" id="divaddzzztform">
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">字典类型</label>
                                    <label class="col-xs-6 control-label" style="text-align: left;"
                                           id="newzzztdicttype">字典类型</label>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">在职状态</label>

                                    <div class="col-xs-4">
                                        <select class="form-control" id="zzzttype">
                                            <option value="在职" selected="selected">在职</option>
                                            <option value="离职">离职</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">在职情况</label>

                                    <div class="col-xs-8">
                                        <input type="text" class="form-control" id="newzzztdictname"
                                               placeholder="请输入在职情况">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnzzztSave" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="dialogzzztdel" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除</h4>
                </div>
                <div class="modal-body">
                    <div class="container">
                        <p>
                            你确定要删除该在职情况&nbsp;<label id="lbzzztdel"></label>&nbsp;吗？
                        </p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnzzztDel" type="button" class="btn btn-primary">删除</button>
                </div>
            </div>
        </div>
    </div>


    <!-- 编辑视窗-->
    <div class="modal fade" id="dialogzzztupdate" tabindex="-1"
         role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">修改在职状态信息</h4>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="form-horizontal" id="divupdatezzztform">
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">字典类型</label>
                                    <label class="col-xs-6 control-label" style="text-align: left;"
                                           id="updatezzztdicttype"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">在职状态</label>

                                    <div class="col-xs-4">
                                        <select class="form-control" id="updatezzzttype">
                                            <option value="在职" selected="selected">在职</option>
                                            <option value="离职">离职</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 col-xs-offset-1 control-label">在职情况</label>

                                    <div class="col-xs-8">
                                        <input type="text" class="form-control"
                                               id="updatezzztdictname" placeholder="请输入在职情况">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button id="btnzzztUpdate" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>

</c:if>
</body>
</html>
