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
    <title>考勤审批</title>

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
    <script src="../js/publicuser/register_check.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword {
            color: rgb(0, 104, 177);
            cursor: pointer;
        }

        .worddiv {
            width: 120px;
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
    </style>
</head>
<%
    String type = (String) request.getAttribute("type");
    String id = (String) request.getAttribute("idoftype");
    int height = 461;
    if ("2".equals(type)||"4".equals(type)) {
        height = 411;
    }
    String hiname="审批";
    if ("3".equals(type)||"4".equals(type)) {
        hiname="查看";
    }
%>
<body>
<input type="hidden" value="<%=id%>" id="kqid"/>
<input type="hidden" value="<%=type%>" id="type"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <%=hiname%>
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top: 20px;">
    <div class="box" style="border:solid 1px #cccccc;">
        <div id="generalTabContent" class="t  ab-content" style="height: <%=height%>px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
                    <%--<tr>--%>
                    <%--<td>--%>
                    <%--<div class="worddiv">审批编号：</div>--%>
                    <%--<span id="spbh">01</span>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    <tr>
                        <td>
                            <div class="worddiv">审批状态：</div>
                            <span id="spzt"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">用户名：</div>
                            <span id="yhm">wllm</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">手机号：</div>
                            <span id="sjh">15000000000</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">用户类型：</div>
                            <span id="yhlx">码头</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">所在城市：</div>
                            <span id="szcs">嘉兴</span>
                        </td>
                    </tr>
                    <% if ("1".equals(type)||"3".equals(type)) { %>
                    <tr>
                        <td>
                            <div class="worddiv">船名号：</div>
                            <span id="cmh">嘉兴港</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">船舶登记号：</div>
                            <span id="cbdjh">12332112121</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">船舶国籍证书：</div>
                            <span id="cbgjzs">111.jpg&nbsp;<!--<span class="clickword">预览</span>&nbsp;<span
                                    class="clickword">下载</span>--></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">船舶AIS证书：</div>
                            <span id="cbaiszs">111.jpg&nbsp;<!--<span class="clickword">预览</span>&nbsp;<span
                                    class="clickword">下载</span>--></span>
                        </td>
                    </tr>
                    <%} else if ("2".equals(type)||"4".equals(type)) {%>
                    <tr>
                        <td>
                            <div class="worddiv">企业名称：</div>
                            <span id="qymc">12332112121</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">工商营业执照号：</div>
                            <span id="gsyyzzh">12332112121</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">工商营业执照：</div>
                            <span id="gsyyzz">12332112121</span>
                        </td>
                    </tr>
                    <%}%>
                </table>
            </div>
        </div>
    </div>
    <%if ("1".equals(type)||"2".equals(type)) {%>
    <div class="box" style="border:solid 1px #cccccc;">
        <div style="float: left;width: 100%;padding: 10px;">
            <div class="worddiv">审批意见：</div>
            <label for="passradio" style="cursor: pointer;">
                <input type="radio" name="radiobutton" id="passradio" value="2"/>&nbsp;&nbsp;&nbsp;&nbsp;<span
                    style="color: green">通过</span>
            </label>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <label for="nopassradio" style="cursor: pointer;">
                <input type="radio" name="radiobutton" id="nopassradio" value="3"/>&nbsp;&nbsp;&nbsp;&nbsp;<span
                    style="color: red">驳回</span>
            </label>
        </div>
        <textarea placeholder="请输入意见说明" id="shenheword"
                  style="width:100%;height:150px;resize: none;border: 0;padding:10px;border-top: solid 1px #ccc;"></textarea>
    </div>
    <button type="button" class="btn btn-primary" onclick="checksp()">提交</button>
    <button type="button" class="btn btn-default" style="margin-left: 10px;" onclick="javascript :history.back(-1);">取消</button>
    <%}%>
</section>
<input type="hidden" id="infoId"/>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    证书预览
                </h4>
            </div>
            <div class="modal-body" style="height:400px;padding:10px;">
                <img src="" id="zsylimg" width="100%" height="100%"/>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- /.modal -->
</body>
</html>
