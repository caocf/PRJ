<%@ page import="com.zjport.model.TOrg" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.Random" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>电子巡航</title>
    <!-- Bootstrap 3.3.4 -->
    <link href="../css/common/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!-- FontAwesome 4.3.0 -->
    <link href="../css/common/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="../js/smart/electronicCruise.js?v=<%= new Random().nextInt()%>"></script>
    <style>
        #selecttable {
            border: solid 1px #cccccc;
            margin-top: 10px;
            margin-bottom: 10px !important;
        }

        #selecttable td {
            border-top: 0 !important;
        }

        .body_div {
            padding: 10px;
            float: left;
            width: 100%;
        }

        .count {
            background-color: white;
            border: solid 1px #cccccc;
            padding: 0 10px;
        }

        select {
            border: 1px solid grey;
        }

        select.form-control {
            background: rgb(250, 250, 250);
            width: auto;
            float: left;
            margin-left: 20px;
        }

        #tablediv {
            height: 650px;
            width: 100%;
            float: left;
            background: white;
            border: solid 1px #ccc;
            border-top: 0;
            padding: 10px;
        }

        #tablediv .btn.btn-default {
            float: left;
            margin-left: 10px;
        }

        .clickword {
            color: rgb(31, 116, 180);
            cursor: pointer;
        }
    </style>
</head>
<body>
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
%>
<input type="hidden" id="xzqh" value="<%=area%>"/>
<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;">电子巡航</b>
    <span style="float: right;font-size: 14px;">智慧监管&lt;电子巡航</span>
</div>
<div class="body_div">
    <div class="count">
        <table class="table" id="selecttable">
            <tr>
                <td class="col-xs-4"><span style="float: left;line-height: 35px;margin-left: 10px;">选择航道 <span
                        style="color:red">*</span>:</span>
                    <select class="form-control" id="checkchanneloption" onchange="addnextoption()">
                    </select>
                </td>
                <td class="col-xs-4" style="border-left: 1px solid #ccc">
                    <span style="float: left;line-height: 35px;margin-left: 10px;">选择航段 <span style="color:red">*</span>:</span>
                    <select class="form-control" id="startoption">
                        <option>请选择航段</option>
                    </select>
                </td>
                <%--<td class="col-xs-4" style="border-left: 1px solid #ccc">--%>
                <%--<span style="float: left;line-height: 35px;margin-left: 10px;">巡航终点 <span style="color:red">*</span>:</span>--%>
                <%--<select class="form-control" id="endoption">--%>
                <%--<option>请选择巡航终点</option>--%>
                <%--</select>--%>
                <%--</td>--%>
            </tr>
        </table>
        <button class="start btn btn-primary" style="margin-bottom: 10px;" id="start_cruies" onclick="addmodaldata()">
            开始巡航
        </button>
    </div>
    <div id="tablediv">
        <b>电子巡航记录</b><br>

        <div style="float: left;width: 100%;margin-top:10px;margin-bottom: 10px;">
            <button id="deleted_history" onclick="deletetr(-1)" class="btn btn-default" style="margin-left: 0;">删除
            </button>
            <button class="btn btn-default" id="export_b">导出</button>
            <%--<select id="channel" name="channel" class="form-control">--%>
            <%--<option>选择航道</option>--%>
            <%--</select>--%>
            <div style="float:left;">
                <input type="text" placeholder="起始时间" id="beginTime"
                       onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer'})" readonly="readonly"
                       class="Wdate"
                       style="float:left;margin-left:10px;height:35px;line-height: 35px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;">至</span>
                <input type="text" id="endTime" placeholder="结束时间"
                       onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer'})" readonly="readonly"
                       class="Wdate"
                       style="float:left;margin-left:10px;height:35px;line-height: 35px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
            </div>
            <button class="btn btn-default" onclick="showInfoInTable('../smart/history',1)">搜索</button>
        </div>
        <table class="table table-striped" id="loglist-info" style="border-top:none;">
        </table>
        <div id="nulltablediv"
             style="float: left;width:100%;text-align: center;margin-top:200px;display: none;color: rgb(215,215,215);">
            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
        </div>
        <div class='bootpagediv' style='width:100%;margin-right:20px;'>
                <span style="float: left;margin-left:10px;line-height: 20px;color:#666;" id="pagedetal">
                </span>
            <nav style='float:right;display:none' id='pageshow'>
                <ul class="pagination" id='pagination'>
                </ul>
            </nav>
        </div>
    </div>
</div>
<%--<div id="p_div" style="display: none;top: 220px;left: 300px;position: absolute;background-color: white;box-shadow: 0 0 5px #333;padding: 10px;">--%>
<%--<table class="table table-bordered" id="querytable">--%>
<%--<tr>--%>
<%--<td class="col-xs-4"><span style="float: left;line-height: 35px;margin-left: 10px;">选择航道 <span style="color:red">*</span>:</span>--%>
<%--<select class="form-control">--%>
<%--<option>请选择航道</option>--%>
<%--</select>--%>
<%--</td>--%>
<%--<td class="col-xs-4" >--%>
<%--<span style="float: left;line-height: 35px;margin-left: 10px;">巡航起点 <span style="color:red">*</span>:</span>--%>
<%--<select  class="form-control">--%>
<%--<option>请巡航起点</option>--%>
<%--</select>--%>
<%--</td>--%>
<%--<td class="col-xs-4" >--%>
<%--<span style="float: left;line-height: 35px;margin-left: 10px;">巡航终点 <span style="color:red">*</span>:</span>--%>
<%--<select class="form-control">--%>
<%--<option>请选择巡航终点</option>--%>
<%--</select>--%>
<%--</td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--<p>摄像头列表</p>--%>
<%--<table class="table table-striped">--%>
<%--<tr>--%>
<%--<th>摄像头名称</th>--%>
<%--<th>所在位置</th>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>监控01</td>--%>
<%--<td>监控位置</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>监控01</td>--%>
<%--<td>监控位置</td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td>监控01</td>--%>
<%--<td>监控位置</td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--<button id="start" class="btn btn-primary" >开始巡航</button>--%>
<%--</div>--%>
<!-- 模态框（Modal）撤回提示 -->
<div class="modal fade" id="addmlModal" tabindex="-1" role="dialog"
     aria-labelledby="recallModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top:200px;width:850px;box-shadow: 0 0 5px #333;">
        <div class="modal-content" style="width: 100%;padding:10px;">
            <table class="table table-bordered" id="querytable">
                <tr>
                    <td class="col-xs-4"><span style="float: left;line-height: 35px;margin-left: 10px;">所选航道 <span
                            style="color:red">*</span>:</span>
                        <span style="float:left;line-height: 35px;margin-left: 10px;" id="modalsxhd"></span>
                    </td>
                    <td class="col-xs-8">
                        <span style="float: left;line-height: 35px;margin-left: 10px;">巡航起点 <span
                                style="color:red">*</span>:</span>
                        <span style="float:left;line-height: 35px;margin-left: 10px;" id="modalhdqd"></span>
                        <i class="fa fa-refresh" style="float: left;font-size: 16px;margin-top: 5px;margin-left: 10px;cursor: pointer;" id="changebtn"></i>
                        <span style="float: left;line-height: 35px;margin-left: 10px;">巡航终点 <span
                                style="color:red">*</span>:</span>
                        <span style="float:left;line-height: 35px;margin-left: 10px;" id="modalhdzd"></span>
                    </td>
                </tr>
            </table>
            <p>摄像头列表</p>
            <table class="table table-striped" id="modaltable">
                <tr>
                    <th>摄像头名称</th>
                    <th>所在位置</th>
                </tr>
                <tr>
                    <td>监控01</td>
                    <td>监控位置</td>
                </tr>
                <tr>
                    <td>监控01</td>
                    <td>监控位置</td>
                </tr>
                <tr>
                    <td>监控01</td>
                    <td>监控位置</td>
                </tr>
            </table>
            <form action="../smart/start" method='post' style="float: left;">
                切换间隔&nbsp;
                <input type="text" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
                       onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')"
                       value="30" placeholder="请输入数字，最少为10秒" name="timeOut"/>
                &nbsp;秒
                <%--<input type="hidden" value="" name="cameraIds" id="videoids"/>--%>
                <input type="hidden" value="" name="hduanId" id="hduanId"/>
                <input type="hidden" value="" name="order" id="order"/><!--排序-->
                <input type="hidden" value="" name="stChannel" id="channelName">
                <input type="hidden" value="" name="stCruiseFrom" id="xhdqform"/>
                <input type="hidden" value="" name="stCruiseTo" id="xhzdform"/>
                <button id="start" type="submit" class="btn btn-primary">开始巡航</button>
            </form>
            <button type="button" class="btn btn-default" style="margin-left: 10px;"
                    data-dismiss="modal">关闭
            </button>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- /.modal -->
</body>

</html>
