<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>危货进港信息查看</title>

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
    <script src="../js/check/whjgsp_detal.js" type="text/javascript"></script>
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
        查看
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top: 20px;">
    <div class="box" style="border:solid 1px #cccccc;">
        <div id="generalTabContent" class="t  ab-content" style="height: 520px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
                    <!--<tr>
                        <td>
                            <div class="worddiv">审批编号：</div>
                            <span id="snumber"></span>
                        </td>
                    </tr>-->
                    <tr>
                        <td>
                            <div class="worddiv">船舶名称：</div>
                            <span id="sshipname"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">起终港：</div>
                            <span id="sport"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">预进港时间：</div>
                            <span id="sberthtime"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">货物名称：</div>
                            <span id="sgoods"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">货物类型：</div>
                            <span id="srank"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">货物总量：</div>
                            <span id="stons"></span>
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
                            <div class="worddiv">申报备注：</div>
                            <span id="sreason"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
