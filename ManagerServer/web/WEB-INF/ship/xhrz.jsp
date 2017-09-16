<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>巡查日志</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../css/ship/cbcxlist.css" rel="stylesheet" type="text/css"/>

    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/ship/xhrz.js"></script>
</head>
<body>
<section class="content-header">
    <h1>
        巡查日志
    </h1>
</section>
<section class="content">
    <div class="box">
        <div class="row">
            <div class="col-xs-3" style="padding: 10px 0 10px 30px">
                <input type="text" placeholder="巡航起始时间" id="beginTime"
                       onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd'})"
                       readonly="readonly" class="Wdate"
                       style="float:left;height:35px;line-height: 35px;padding-left:10px;border: solid 1px #ccc;"/>
                <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;">至</span>
                <input type="text" id="endTime" placeholder="巡航结束时间"
                       onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd'})"
                       readonly="readonly" class="Wdate"
                       style="float:left;margin-left:10px;height:35px;line-height: 35px;padding-left:10px;border: solid 1px #ccc;"/>
            </div>
            <div class="col-xs-2" style="margin-top:12px;border-radius:4px;border:solid 1px #cccccc;">
                <input type="text" class="form-control"
                       style="height:100%;padding:5px;width:190px;border: 0;float: left;" placeholder="请输入巡查人员"
                       id="listkey"/>
                <div style="float: left;height:30px;width:28px;">
                    <i class="fa fa-close"
                       style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;"
                       id="clearbtn"></i>
                </div>
                <i class="fa fa-search"
                   style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                   onclick="Search(1)"></i>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content" style="min-height:600px;">
            <div id="tab-internet" class="tab-pane fade in active"
                 style="position: relative">
                <table class="table table-hover col-xs-12" id="dttable" style="border-top:none">
                    <col width="5%"/>
                    <col width="10%"/>
                    <col width="20%"/>
                    <col width="20%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="15%"/>
                    <col width="10%"/>
                    <tr style="background-color: rgb(240,245,248);">
                        <th class="td">序号</th>
                        <th class="td">人员</th>
                        <th class="td">工具</th>
                        <th class="td">区域</th>
                        <th class="td">里程</th>
                        <th class="td">事件数量</th>
                        <th class="td">巡航时间</th>
                        <th class="td">操作</th>
                    </tr>
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
</body>
</html>
