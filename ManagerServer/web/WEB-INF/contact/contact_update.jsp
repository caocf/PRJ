<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>通讯录编辑</title>

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
    <script src="../js/contact/contact_update.js" type="text/javascript"></script>
    <style type="text/css">
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

        .imagediv {
            width: 33%;
            float: left;
            height: 140px;
            padding: 5px;
        }

        .imagediv > div {
            border: solid 1px #ccc;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<%
    String id = (String) request.getAttribute("id");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<body>
<input type="hidden" value="<%=id%>" id="kqid"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<input type="hidden" value="" id="userid"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        编辑
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>
<section class="content" style="padding-top: 20px;">
    <div class="box" style="float:left;width: 180px;height: 240px;border-top:none;"><img src="image/headpic.png" alt=""
                                                                                         style="width: 100%;height: 100%">
    </div>
    <div class="box" style="float:left;border:solid 1px #cccccc;width:50%;margin-left: 20px">
        <div id="generalTabContent" class="t  ab-content" style="height: 643px;">
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
                    <tr>
                        <td>
                            <div class="worddiv">账号：</div>
                            <span id="szh"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">姓名：</div>
                            <span id="sxm"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">职务：</div>
                            <span id="szw"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">单位：</div>
                            <span id="sdw"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">部门：</div>
                            <span id="sbm"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">角色：</div>
                            <span id="sjs"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv" style="float: left">邮箱：</div>
                            <div style="float: left"><input class="form-control"  type="text" id="syx"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv" style="float: left">办公电话：</div>
                            <div style="float: left"><input class="form-control"  type="text" id="sbgdh"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv" style="float: left">手机号码：</div>
                            <div style="float: left"><input class="form-control"  type="text" id="ssjhm"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv" style="float: left">虚拟网号：</div>
                            <div style="float: left"><input class="form-control"  type="text" id="sxnwh"></div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">状态：</div>
                            <span id="szt"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div><input id="btnedit" class="btn btn-primary" value="保存"
                                        style="float: right;width: 60px;margin-right: 40px" onclick="updateuser()"></div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</section>
</body>
</html>
