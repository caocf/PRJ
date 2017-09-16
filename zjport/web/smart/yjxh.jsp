<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.model.TOrg" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>一键巡航</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="../js/smart/yjxh.js" type="text/javascript"></script>
    <style type="text/css">
        .toplabel {
            display: inline-block;
            width: 14%;
            height: 100%;
            float: left;
            border-right: solid 1px #ccc;
            padding-left: 10px;
            text-align: left;
            font-size: 20px;
            font-weight: 400;
        }

        .bluespanword {
            line-height: 40px;
            font-size: 40px;
            color: dodgerblue;
            font-weight: 600;
            cursor: pointer;
        }
        .cccspanword {
            line-height: 40px;
            font-size: 25px;
            color: #ccc;
            font-weight: 600;
        }
        .clickword {
            color: rgb(31, 116, 180);
            cursor: pointer;
        }
        .ztcolordiv{
            margin-left: 5px;
            height: 25px;
            line-height: 25px;
            float: left;
            width: auto;
            padding: 0 10px;
            text-align: center;
            color: white;
        }
    </style>
</head>
<%
    String area = "330000";
    TOrg org = (TOrg) request.getSession().getAttribute("session_org");
    if (org == null) {
        System.out.println("chedan!");
    } else {
        area = org.getStOrgArea();
        if (StringUtils.isNotEmpty(area)) {
            area = area.substring(0, 4) + "00";
        } else {
            area = "330000";
        }
    }
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    Calendar ca = Calendar.getInstance();
//    String edate = format.format(ca.getTime());
//    ca.add(Calendar.HOUR_OF_DAY, -2);
//    String bdate = format.format(ca.getTime());
%>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<input type="hidden" value="<%=area%>" id="userareaNo"/>
<section class="content-header">
    <h1>
        一键巡航
        <!-- <small>advanced tables</small> -->
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-cog"></i> 智慧监管</a></li>
        <li><a href="#">电子巡航</a></li>
        <li><a href="#">一键巡航</a></li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="row" style="margin-right: 0;margin-left: 0;">
            <div class="col-sm-12" style="height:50px;line-height: 50px;border-bottom: solid 1px #ccc;">
                <span style="float: left;font-size: 16px;">辖区：</span>
                <select id='areaselect' class="form-control"
                        style="margin-top:10px;float: left;width:100px;margin-left: 10px;height:30px;line-height: 30px;padding-top:0;padding-bottom:0;">
                </select>
                <span style="margin-top:10px;float: left;margin-left: 10px;height:30px;line-height: 30px;" id="qyname"></span>
				  <span style="float: right;font-size: 16px;color:dodgerblue;cursor: pointer;" id="refreshbtn">
					  <i class="fa fa-refresh"></i>&nbsp;刷新
				  </span>
            </div>
        </div>
        <div class="row" style="margin-right: 0;margin-left: 0;">
            <div class="col-sm-12" style="height:150px;line-height: 50px;padding:20px 10px;" id="yjxhtopworddiv">
            </div>
        </div>
        <div style="float:left;width: 100%;">
            <ul class="nav nav-tabs ul-edit responsive">
                <li class="active"><a class='tabdiv shiplist' href="#tab-internet" data-toggle="tab">危险品船舶</a></li>
                <li><a class='tabdiv shiplist' href="#tab-internet" data-toggle="tab">黑名单船舶</a></li>
                <li><a class='tabdiv shiplist' href="#tab-internet" data-toggle="tab">违章船舶</a></li>
                <li><a class='tabdiv shiplist' href="#tab-internet" data-toggle="tab">证书异常船舶</a></li>
                <li><a class='tabdiv shiplist' href="#tab-internet" data-toggle="tab">规费未缴清船舶</a></li>
                <li><a class='tabdiv shiplist' href="#tab-internet" data-toggle="tab">超载船舶</a></li>
                <li><a class='tabdiv shiplist' href="#tab-internet" data-toggle="tab">违停船舶</a></li>
            </ul>
        </div>
        <div id="generalTabContent" class="t  ab-content no-margin"
             style="margin-top:10px;margin-left:8px;height:600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-bordered table-hover col-xs-12" id="loglist-info" style="border-top:none">
                </table>
            </div>
            <div class='bootpagediv' style='width:100%;margin-right:20px;'>
                <span style="float: left;margin-left:10px;line-height: 20px;color:#666;" id="pagedetal">
                </span>
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
</section>
<!-- 船舶详情模态框（Modal） -->
<div class="modal fade" id="cbxqModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:900px;">
        <div class="modal-content" style="width:100%;height: 480px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    <img src="../image/information/ic_details.png" width="30px" height="30px">
                    船舶详情 —— <span id="titleshipname"></span>
                </h4>
            </div>
            <div class="modal-body" style="height:auto;padding:0;">
                <div style="float: left;height:auto;width:100%;overflow: auto;" class="treediv">
                    <div style="float:left;width: 100%;">
                        <ul class="nav nav-tabs ul-edit responsive">
                            <li class="active"><a class='tabdiv shipxq' href="#tab-internet" data-toggle="tab">基本信息</a></li>
                            <li><a class='tabdiv shipxq' href="#tab-internet" data-toggle="tab">证书信息</a></li>
                            <li><a class='tabdiv shipxq' href="#tab-internet" data-toggle="tab">违章信息</a></li>
                            <li><a class='tabdiv shipxq' href="#tab-internet" data-toggle="tab">缴费信息</a></li>
                            <li><a class='tabdiv shipxq' href="#tab-internet" data-toggle="tab">船检信息</a></li>
                            <%--<li><a class='tabdiv' href="#tab-internet" data-toggle="tab">电子报告信息</a></li>--%>
                            <%--<li><a class='tabdiv' href="#tab-internet" data-toggle="tab">AIS信息</a></li>--%>
                        </ul>
                    </div>
                    <div style="padding: 10px;float: left;width: 100%;height: 350px;overflow-y: auto">
                        <table class="table" id="shipdatatable"></table>
                        <div id="nulltablediv1"
                             style="float: left;width:100%;text-align: center;margin-top:100px;display: none;color: rgb(215,215,215);">
                            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
</body>
</html>
