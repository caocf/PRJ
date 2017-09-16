<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>违章信息查看</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/ajaxfileupload.js"></script>
    <script src="../js/common/common.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/check/wzsp_update.js" type="text/javascript"></script>
    <script type="text/javascript" language="javascript"
            src="http://172.20.24.105/WebtAPI/wapi.js"></script>
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
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        补充
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>
<section class="content" style="padding-top: 20px;">
    <div class="box" style="border:solid 1px #cccccc;float:left;width:50%;">
        <div id="generalTabContent" class="t  ab-content" style="height: 643px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
                    <tr>
                        <td>
                            <div class="worddiv">当事人：</div>
                            <span id="starget"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">违章地点：</div>
                            <span id="slocation"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">违章缘由：</div>
                            <span id="sreason"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">违章描述：</div>
                            <span id="sdetail"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">所属分类：</div>
                            <span id="stypeen"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">审批状态：</div>
                            <span id="sstatus"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">意见说明：</div>
                            <span id="sisllegal"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">第一执法人：</div>
                            <span id="sfirstman"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">第二执法人：</div>
                            <span id="ssecman"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">取证时间：</div>
                            <span id="ssumbdate"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="box" style="border:solid 1px #cccccc;float:left;margin-left:15px;width:48%;margin-bottom:0;">
        <div class="t  ab-content" style="height: 250px;">
            <div style="float: left;width: 100%;padding: 10px;">
                <div class="worddiv">地图：</div>
            </div>
            <div style="float: left;width:100%;height:100%;border:solid 1px #ccc;" id="mapdiv">
            </div>
        </div>
    </div>
    <div class="box" style="border:solid 1px #cccccc;float:left;margin-left:15px;width:48%;margin-top:15px;">
        <div class="t  ab-content" style="height: 335px;overflow-y: auto;" id="evidencediv">
            <div style="float: left;width: 100%;padding: 10px;">
                <div class="worddiv" style="width: 100%"><label>证据</label>
                    <input onclick="loadevidence()" class="btn btn-default" type="button" style="float: right;width: 60px;cursor: pointer" value="上传" id="devibtn">
                    <input onchange="uploadevidence()" class="btn btn-default" type="file" style="display: none" multiple="multiple" id="attachment" name="attachment">
                </div>
            </div>

        </div>
    </div>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
