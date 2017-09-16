<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String shipname = (String) request.getAttribute("shipname");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>船舶详情</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="../css/ship/shipinfo.css" rel="stylesheet" type="text/css"/>
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/common.js"></script>
    <script src="../js/dtpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/ship/shipinfo.js"></script>
</head>
<body>
<input type="hidden" value="<%=shipname%>" id="hideshipname">

<div class="row" style="background-color: white;width:100%;height: 50px;margin: 0">
    <div class="col-xs-4" style="height: 100%"><img src="../image/ship/ic_details.png" alt=""
                                                    style="padding: 5px 10px 5px 10px"><label class="label"
                                                                                              style="color: black;height: 100%;font-size: large;padding-left: 0px">船舶详情</label>
    </div>
    <div style="float:right;height: 100%;width: 50px;font-size:30px; line-height: 1.5em;"><i class="fa fa-times"
                                                                                             aria-hidden="true"
                                                                                             style="height: 100%"
                                                                                             onclick="history.go(-1)"></i>
    </div>
</div>
<div class="box" style="width:97%;height:780px;margin: 20px 40px 0 20px;border: solid 1px #ccc;">
    <div class="row" style="width:100%;margin-left:0px;">
        <div style="width: 40%;float: left">
            <label class="tablabel" style="width: 20%;float: left" onclick="showcbxx(1)" id="jbxxlab">基本信息</label>
            <label class="tablabel" style="width: 20%;float: left" onclick="showcbxx(2)">证书信息</label>
            <label class="tablabel" style="width: 20%;float: left" onclick="showcbxx(3)">违章信息</label>
            <label class="tablabel" style="width: 20%;float: left" onclick="showcbxx(4)">缴费信息</label>
            <label class="tablabel" style="width: 20%;float: left" onclick="showcbxx(5)">检验信息</label>
        </div>
        <div style="width: 60%;float: left;height: 50px;border-bottom: solid 1px #ccc;">

        </div>
    </div>
    <div style="height:728px;">
        <div id="shipcontent" style="height:648px;overflow-y:auto;overflow-x: hidden;display: none">
            <table style="width: 65%;margin: 20px 0 0 20px" id="dt"></table>
        </div>
        <div class='bootpagediv' style='width:100%;margin-right:50px;margin-top: 20px;'>
            <%--                <span style="float: left;margin-left:20px;line-height: 20px;color:#666;" id="pagedetal">
                            </span>--%>
            <nav style='float:right;display:none' id='pageshow'>
                <ul class="pagination" id='pagination'>
                </ul>
            </nav>
        </div>
        <div id="nulltablediv"
             style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
        </div>
    </div>
</div>

<div class="modal fade" id="wzmodal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 900px;border-radius: 5px">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    违章详情
                </h4>
            </div>
            <div class="modal-body" style="height: 320px;">
                <table class="table col-xs-12" style="border: solid 1px #ccc;">
                    <tr>
                        <td class="col-xs-3 texttd">受理号</td>
                        <td class="col-xs-3 datatd" id="wxdetail0"></td>
                        <td class="col-xs-3 texttd">受理时间</td>
                        <td class="col-xs-3 datatd" id="wxdetail1"></td>
                    </tr>
                    <tr>
                        <td class="col-xs-3 texttd">中文船名</td>
                        <td class="col-xs-3 datatd" id="wxdetail2"></td>
                        <td class="col-xs-3 texttd">案由</td>
                        <td class="col-xs-3 datatd" id="wxdetail3"></td>
                    </tr>
                    <tr>
                        <td class="col-xs-3 texttd">发案时间</td>
                        <td class="col-xs-3 datatd" id="wxdetail4"></td>
                        <td class="col-xs-3 texttd">发案地点</td>
                        <td class="col-xs-3 datatd" id="wxdetail5"></td>
                    </tr>
                    <tr>
                        <td class="col-xs-3 texttd">案件类别</td>
                        <td class="col-xs-3 datatd" id="wxdetail6"></td>
                        <td class="col-xs-3 texttd">结案日期</td>
                        <td class="col-xs-3 datatd" id="wxdetail16"></td>
                    </tr>
                    <tr>
                        <td class="col-xs-3 texttd">主要事实</td>
                        <td class="col-xs-9 datatd" id="wxdetail7" colspan="3"></td>
                    </tr>
                    <tr>
                        <td class="col-xs-3 texttd">执法手册编号</td>
                        <td class="col-xs-3 datatd" id="wxdetail8"></td>
                        <td class="col-xs-3 texttd">违法内容</td>
                        <td class="col-xs-3 datatd" id="wxdetail9"></td>
                    </tr>
                    <tr>
                        <td class="col-xs-3 texttd">违法条款</td>
                        <td class="col-xs-3 datatd" id="wxdetail10"></td>
                        <td class="col-xs-3 texttd">处罚条款</td>
                        <td class="col-xs-3 datatd" id="wxdetail11"></td>
                    </tr>
                    <tr>
                        <td class="col-xs-3 texttd">处罚意见</td>
                        <td class="col-xs-3 datatd" id="wxdetail12"></td>
                        <td class="col-xs-3 texttd">处罚类别</td>
                        <td class="col-xs-3 datatd" id="wxdetail13"></td>
                    </tr>
                    <tr>
                        <td class="col-xs-3 texttd">是否处罚</td>
                        <td class="col-xs-3 datatd" id="wxdetail14"></td>
                        <td class="col-xs-3 texttd">是否结案</td>
                        <td class="col-xs-3 datatd" id="wxdetail15"></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
