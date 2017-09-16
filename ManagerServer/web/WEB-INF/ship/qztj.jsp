<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>船舶查询</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/highcharts.css" rel="stylesheet" type="text/css"/>
    <link href="../css/ship/cbcxlist.css" rel="stylesheet" type="text/css"/>
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/common/highchart/highcharts.js"></script>
    <script src="../js/common/highchart/exporting.js"></script>
    <script src="../js/ship/qztj.js"></script>
</head>
<body>
<section class="content-header">
    <h1>
        取证统计
    </h1>
</section>

<section class="content">
    <div class="box">
        <div class="row">
            <div class="col-xs-1" style="padding: 10px 0 10px 30px">
                <select class="form-control" style="width: 120px" id="seltypeen">
                    <option value="0">执法类型</option>
                    <option value="1">海事</option>
                    <option value="2">港政</option>
                    <option value="3">航道</option>
                </select>
            </div>
            <div class="col-xs-3" style="padding: 10px 0 10px 30px">
                <input type="text" placeholder="起始时间" id="beginTime"
                       onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd'})"
                       readonly="readonly" class="Wdate"
                       style="float:left;height:35px;line-height: 35px;padding-left:10px;border: solid 1px #ccc;"/>
                <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;">至</span>
                <input type="text" id="endTime" placeholder="结束时间"
                       onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd'})"
                       readonly="readonly" class="Wdate"
                       style="float:left;margin-left:10px;height:35px;line-height: 35px;padding-left:10px;border: solid 1px #ccc;"/>
            </div>
            <div class="col-xs-1" style="padding: 10px 0 10px 30px">
                <input type="button" value="统计" id="searchbtn" class="btn btn-primary" style="width: 100%"
                       onclick="init()">
            </div>
        </div>
        <div style="overflow-y: auto">
            <div style="width: 40%;float:left;height: 400px;" id="pieqz" class="tab-pane fade in active">

            </div>
            <div style="width: 60%;float:left;height: 400px;" id="colqz" class="tab-pane fade in active">

            </div>
            <div style="width: 40%;float:left;height: 400px;text-align: center" id="tpieqz"
                 class="tab-pane fade in active">
                <table id="t1" class="table" style="margin:0 auto;width: 240px;border: solid 2px #0072b1!important;">
                    <tr>
                        <th style="text-align: center"></th>
                        <th style="text-align: center">海事</th>
                        <th style="text-align: center">港政</th>
                        <th style="text-align: center">航道</th>
                        <th style="text-align: center">合计</th>
                    </tr>
                </table>
            </div>
            <div style="width: 60%;float:left;height: 400px;text-align: center" id="tcolqz"
                 class="tab-pane fade in active">
                <table id="t2" class="table"
                       style="margin:0 auto;width:580px;border: solid 2px #0072b1!important;">
                    <tr>
                        <th style="text-align: center">时间</th>
                        <th style="text-align: center">合计</th>
                        <th style="text-align: center">通过</th>
                        <th style="text-align: center">驳回</th>
                        <th style="text-align: center">海事</th>
                        <th style="text-align: center">港政</th>
                        <th style="text-align: center">航道</th>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    </div>
</section>
</body>
</html>
