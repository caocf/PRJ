<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>危货作业审批</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/common.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/check/whzysp_check.js" type="text/javascript"></script>
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
    </style>
</head>
<%
    String id = (String) request.getAttribute("id");
%>
<body>
<input type="hidden" value="<%=id%>" id="kqid"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        审批
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top: 20px;">
    <div class="box" style="border:solid 1px #cccccc;">
        <div id="generalTabContent" class="t  ab-content" style="height: 657px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
                    <tr>
                        <td>
                            <div class="worddiv">审批编号：</div>
                            <span id="snumber"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">作业码头：</div>
                            <span id="swharfEN"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">承运船舶：</div>
                            <span id="sship"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">始发港：</div>
                            <span id="sstartport"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">目的港：</div>
                            <span id="stargetport"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">货物名称：</div>
                            <span id="sgoodsname"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">危货类型：</div>
                            <span id="sranktype"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">货物总量：</div>
                            <span id="smount"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">开始作业：</div>
                            <span id="sportime"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">结束作业：</div>
                            <span id="sendtime"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">审核状态：</div>
                            <span id="sstatus"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">审核单位(人)：</div>
                            <span id="schecker"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">审核备注：</div>
                            <span id="sreason"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="box" style="border:solid 1px #cccccc;float: left;display: none" id="approdiv">
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
    <div style="width: 100%;float: left;display: none" id="approbtndiv">
        <button type="button" class="btn btn-primary" onclick="appro()">提交</button>
        <button type="button" class="btn btn-default" style="margin-left: 10px;"
                onclick="javascript :history.back(-1);">取消
        </button>
    </div>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
