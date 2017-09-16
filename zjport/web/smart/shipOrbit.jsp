<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.util.CommonConst" pageEncoding="UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>
<html>
<head>
    <title>实时预览</title>
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
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!--地图js-->
    <script type="text/javascript" src="http://172.20.24.105/WebtAPI/wapi.js"></script>
    <%--<script type="text/javascript" src="http://115.231.73.253/WebtAPI/wapi.js"></script>--%>
    <!-- 页面js -->
    <script src="../js/common/bootpaging.js"></script>
    <script src="../js/smart/shipOrbit.js"></script>
    <!--css-->
    <style type="text/css">
        #cmhdiv > label {
            display: inline-block;
            word-break: break-all;
            word-wrap: break-word;
            width: 100%;
            padding: 10px;
            float: left;
            cursor: pointer;
            text-align: left;
        }

        #cmhdiv > label:hover {
            background: dodgerblue;
            color: white;
        }

        #beginTime, #endTime {
            width: 200px;
        }
    </style>
</head>
<%
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar ca = Calendar.getInstance();
    String edate = format.format(ca.getTime());
    ca.add(Calendar.HOUR_OF_DAY, -2);
    String bdate = format.format(ca.getTime());
%>
<body>
<input type="hidden" value="<%=(String) request.getSession().getAttribute("cbmc")%>" id="shipname"/>

<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;">船舶轨迹</b>
    <span style="float: right;font-size: 14px;">智慧监管&gt船舶轨迹</span>
</div>
<div style="float: left;width:100%;height:700px;padding:0 20px;position: relative;">
    <div style="position: absolute;width: 350px;background: white;top:20px;left:40px;z-index:2;box-shadow: 0px 0px 5px #888888;">
        <table class="table" style="background-color: white;margin-bottom:0;">
            <tr>
                <td class="col-xs-3">船名号<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input class="from-control" type="text" style="border: 0;" placeholder="请输入船名号" id="cmhinput"/>
                </td>
            </tr>
            <tr>
                <td class="col-xs-3">开始时间<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input type="text" value="<%=bdate%>" placeholder="起始时间" id="beginTime"
                           onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}',minDate:'#F{$dp.$D(\'endTime\',{H:-2})}',skin:'twoer'})"
                           readonly="readonly" class="Wdate"
                           style="float:left;height:22px;line-height: 22px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                </td>
            </tr>
            <tr>
                <td class="col-xs-3">结束时间<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input type="text" id="endTime" value="<%=edate%>" placeholder="结束时间"
                           onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}',maxDate:'#F{$dp.$D(\'beginTime\',{H:2})}',skin:'twoer'})"
                           readonly="readonly" class="Wdate"
                           style="float:left;height:22px;line-height: 22px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button class="btn btn-info" style="width: 90%;margin-left: 5%;" onclick="guijimake()">查询</button>
                </td>
            </tr>
        </table>
        <div style="display:none;position: absolute;width:160px;height: 200px;overflow-y: auto;border:solid 1px #ccc;background:white;top:35px;left:95px;"
             id="cmhdiv">
        </div>
    </div>
    <div style="position:absolute;float: left;width:98%;height:100%;background-color: white;border:solid 1px #ccc;z-index: 0"
         id="shipOrbitmap_div">
    </div>
</div>
</body>
</html>
