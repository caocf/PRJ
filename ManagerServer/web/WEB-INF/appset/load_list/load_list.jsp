<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>下载统计</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- highcharts -->
    <script src="../js/common/highchart/highcharts.js"></script>
    <script src="../js/common/highchart/exporting.js"></script>
    <!-- 页面js -->
    <script src="../js/appset/loadapp_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword{
            color:rgb(0,104,177);
            cursor: pointer;
        }
        .worddiv{
            width:110px;
            float: left;
            height:30px;
            line-height: 30px;
            padding-right: 30px;
            text-align: right;
        }
        #timetype{
            height:30px;border: solid 1px #ccc;float: left;
        }
        #timetype>div{
            height:100%;
            border-right: solid 1px #ccc;
            text-align: center;
            line-height: 30px;
            width:50px;
            background-color: rgb(250,250,250);
            float: left;
            cursor: pointer;
        }
        .col-sm-3{
            height:100%;
            border-right: solid 1px #e5e5e5;
            text-align: center;
        }
        h3{
            margin-top: 10px!important;
            margin-bottom: 0!important;
            font-weight: 700!important;
        }
    </style>
</head>
<body>
    <section class="content-header">
        <h1>
            下载统计
            <!-- <small>advanced tables</small> -->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-th-large"></i>APP管理</a></li>
            <li><a href="#">下载统计</a></li>
        </ol>
    </section>

    <section class="content">
        <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                    <div id="timetype">
                        <div onclick="settimetype(1)">本周</div>
                        <div onclick="settimetype(2)">上周</div>
                        <div onclick="settimetype(3)">本月</div>
                        <div style="border:0;" onclick="settimetype(4)">上月</div>
                    </div>
                    <div style="float:left;margin-left:10px;">
                        <input type="text" placeholder="起始时间" id="beginTime" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly" class="Wdate" style="float:left;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;" />
                        <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;" >至</span>
                        <input type="text" id="endTime" placeholder="结束时间" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly" class="Wdate" style="float:left;margin-left:10px;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;" />
                    </div>
                    <button type="button" class="btn btn-primary" style="float: left;margin-left: 10px;height:30px;line-height: 16px;">
                        统计
                    </button>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content" style="margin-top:20px;min-height:600px;">
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative;width:90%;left:5%;">

            </div>
            <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
            </div>
        </div>
        </div>
    </section>
    <input type="hidden" id="infoId"/>
</body>
</html>
