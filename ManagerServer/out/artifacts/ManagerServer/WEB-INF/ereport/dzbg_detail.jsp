<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>电子报告查看</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/common.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script src="../js/ereport/dzbg_detail.js" type="text/javascript"></script>
    <style type="text/css">
        .worddiv {
            width: 130px;
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
            border: 1px solid red;
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
<input type="hidden" value="<%=id%>" id="pkid"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        查看
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>
<section class="content" style="padding-top: 20px;">
    <div class="box" style="border:solid 1px #cccccc;float:left;width:100%;">
        <div id="generalTabContent" class="t  ab-content" style="height: 600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table col-xs-12" id="rolelist-info">
                    <tr>
                        <td style="border-top: 1px solid red">
                            <div class="worddiv">船名：</div>
                            <span id="sshipname"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">报告类型：</div>
                            <span id="sclassname"></span>
                        </td>
                    </tr>
                    <!--<tr>
                        <td>
                            <div class="worddiv">作业区域：</div>
                            <span id="sregion"></span>
                        </td>
                    </tr>-->
                    <tr>
                        <td>
                            <div class="worddiv">报告时间：</div>
                            <span id="scommitdate"></span>
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
                            <div class="worddiv">终点港：</div>
                            <span id="sendport"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">进出港：</div>
                            <span id="sporttype"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">预计进出港时间：</div>
                            <span id="sporttime"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">货物：</div>
                            <span id="sgoodstype"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">数量：</div>
                            <span id="snum"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">上次航行加油时间：</div>
                            <span id="slastfueltime"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">上次航行加油量：</div>
                            <span id="slastfuelcount"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</section>
</body>
</html>
