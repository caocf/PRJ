<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>查看</title>

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
    <script src="../js/common/pdfobject/pdfobject.min.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/ship/zfcx_detail.js" type="text/javascript"></script>
    <script type="text/javascript" language="javascript"
                src="http://172.20.24.105/WebtAPI/wapi.js"></script>
    <style type="text/css">
        .pdfobject-container {
            height: 900px;
        }

        .pdfobject {
            border: 1px solid #666;
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

        .signedpdfdiv {
            float: left;
            position: absolute;
            left: 60px;
            bottom: 60px;
            z-index: 200;
            cursor: pointer;
            width: 133px;
            height: 50px;
            background-image: url('/image/btn_gaizhang_normal.png')
        }

        .signedpdfdiv:hover {
            background: url("../../image/btn_gaizhang_hover.png") no-repeat;
        }
    </style>
</head>
<%
    String id = (String) request.getAttribute("id");
    String flag = (String) request.getAttribute("flag");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<body>
<input type="hidden" value="<%=id%>" id="kqid"/>
<input type="hidden" value="0" id="penaltyid"/>
<input type="hidden" value="<%=flag%>" id="flag"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        查看
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
                    <tr id="trdepend" style="display: none">
                        <td>
                            <div class="worddiv">法律依据：</div>
                            <span id="sdepend"></span>
                        </td>
                    </tr>
                    <tr id="trsuggest" style="display: none">
                        <td>
                            <div class="worddiv">处罚意见：</div>
                            <span id="ssuggest"></span>
                        </td>
                    </tr>
                    <tr id="trnumber" style="display: none">
                        <td>
                            <div class="worddiv">处罚金额：</div>
                            <span id="snumber"></span>
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
                <div class="worddiv">证据：</div>
            </div>
        </div>
    </div>
</section>

<div class="modal fade" id="pdfmodal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelpicview">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div id="pdfdiv" class="modal-body" style="padding:0 0 0 0;">
            </div>
            <div id="signpdfdiv" class="signedpdfdiv" style="display: none" onclick="pdfsigned()"></div>
        </div>
    </div>
</div>
</body>
</html>