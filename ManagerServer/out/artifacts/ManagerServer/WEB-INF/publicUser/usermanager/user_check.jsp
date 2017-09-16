<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>用户详情</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="../js/publicuser/user_check.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword {
            color: rgb(0, 104, 177);
            cursor: pointer;
        }

        .worddiv {
            width: 100px;
            float: left;
        }

        .worddiv span {
            color: red;
        }

        .table tr {
            height: 50px !important;
        }

        .table td {
            line-height: 34px !important;
        }

        .btn-default {
            margin-bottom: 20px !important;
        }
    </style>
</head>
<%
    String type = (String) request.getAttribute("type");
    String id = (String) request.getAttribute("idoftype");
    String pagetotal;
    if ("1".equals(type)) {
        pagetotal = "详情-船户";
    } else {
        pagetotal = "详情-企业用户";
    }
%>
<body>
<input type="hidden" value="<%=id%>" id="kqid"/>
<input type="hidden" value="<%=type%>" id="type"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <%=pagetotal%>
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top: 20px;">
    <button type="button" class="btn btn-default">禁用</button>
    <div class="box" style="border:solid 1px #cccccc;">
        <div id="generalTabContent" class="t  ab-content" style="height: 350px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
                    <tr>
                        <th>
                            <div class="worddiv">用户基本信息</div>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">用户名：</div>
                            <span id="yhm"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">用户类型：</div>
                            <span id="yhlx"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">所在城市：</div>
                            <span id="szcs"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">手机号：</div>
                            <span id="sjh"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">密码：</div>
                            <span id="mm">******&nbsp;&nbsp;&nbsp;&nbsp;
                                <span class='clickword' data-toggle="modal" data-target="#myModal">重置密码</span></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">用户状态：</div>
                            <span id="yhzt"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <%if ("1".equals(type)) {%>
    <div class="box" style="border:solid 1px #cccccc;background: white;float: left;height:300px;overflow-y: auto">
        <div style="float: left;width: 100%;padding: 10px;">
            <div class="worddiv"><b>绑定的船舶：</b></div>
        </div>
        <div class="tab-pane fade in active" style="position: relative;">
            <table class="table table-hover col-xs-12" id="kqtable" style="border-top:none">
            </table>
        </div>
    </div>
    <%} else {%>
    <div class="box" style="border:solid 1px #cccccc;background: white;float: left;max-height: 400px;overflow-y: auto;">
        <div style="float: left;width: 100%;padding: 10px;">
            <div class="worddiv" style="width: 100%;"><b>绑定的企业</b>&nbsp;&nbsp;&nbsp;&nbsp;<!--<font class="clickword">更换绑定企业</font>--></div>
        </div>
        <div class="tab-pane fade in active" style="position: relative;">
            <table class="table table-hover col-xs-12"  style="border-top:none">
                <tr>
                    <td style="width: 150px;">企业名称</td>
                    <td id="qymc">11</td>
                </tr>
                <tr>
                    <td >工商营业执照号</td>
                    <td id="gsyyzzh">11</td>
                </tr>
                <tr>
                    <td >工商营业执照</td>
                    <td id="gsyyzz">11</td>
                </tr>
            </table>
        </div>
    </div>
    <%}%>
</section>
<input type="hidden" id="infoId"/>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    重置密码
                </h4>
            </div>
            <div class="modal-body">
                <div class="form-group" style="height:34px;">
                    <label class="col-sm-2 control-label" style="line-height: 34px;text-align: right;padding-right:10px;">新密码：</label>
                    <div class="col-sm-10">
                        <input class="form-control" id="newpassword" type="password"
                               placeholder="请输入新密码">
                    </div>
                </div>
                <div class="form-group" style="height:34px;margin-bottom:0; ">
                    <label class="col-sm-2 control-label" style="line-height: 34px;text-align: right;padding-right:10px;">确认密码：</label>
                    <div class="col-sm-10">
                        <input class="form-control" id="renewpassword" type="password"
                               placeholder="确认新密码">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="repassword()">
                    确定
                </button>
                <button type="button" class="btn btn-default" style="margin-bottom:0!important;"
                        data-dismiss="modal">关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</body>
</html>
