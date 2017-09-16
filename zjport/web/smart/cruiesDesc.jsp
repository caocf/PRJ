<%--
  Created by IntelliJ IDEA.
  User: Will
  Date: 2016/10/20
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>巡航详情</title>
    <!-- Bootstrap 3.3.4 -->
    <link href="../css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- FontAwesome 4.3.0 -->
    <link href="../css/common/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/bootpaging.js"></script>
    <script src="../js/smart/cruiesDesc.js"></script>
    <script src="../js/common/moment.min.js"></script>
    <!--地图js-->
    <script type="text/javascript" src="http://172.20.24.105/WebtAPI/wapi.js"></script>
    <style type="text/css">
        #datatable td{
            border-top: 0!important;
            line-height: 25px;
        }
        .datadiv{
            height:25px;
            line-height: 25px;
            text-align: center;
            padding: 0 7px;
            float: left;
            width: auto;
            background: #f1f2f6;
            border: solid 1px #ccc;
            border-radius: 3px;
        }
    </style>
</head>
<body>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_details.png">
        巡航日志详情
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>
    <div id="photo_list" style="width: 50%;float: left;padding:10px;height:800px;overflow-y: auto;">

    </div>
<div style="width: 50%;float: left;padding:10px;">
    <div style="width: 100%;float: left;box-shadow: 0 0 3px #ccc;background: white;padding:10px 0 0 0;">
    <p style="padding-left:10px;line-height: 30px;">基本信息</p>
    <table class="table" id="datatable" style="border-top: solid 1px #ccc;">
        <tr>
            <td align="left" style="width: 100px;">巡查船舶：</td>
            <td id="shipName"><div class="datadiv">111</div></td>
        </tr>
        <tr>
            <td align="left">起终点：</td>
            <td id="point"><div class="datadiv">起点 至 终点</div></td>
        </tr>
        <tr>
            <td align="left">巡查时间</td>
            <td id="curiesTime"><div class="datadiv">2016-10-20 10:19:02 至 2016-10-20 10:19:08</div></td>
        </tr>
        <tr>
            <td align="left">巡查时长</td>
            <td id="useTime"><div class="datadiv">3小时</div></td>
        </tr>
        <tr>
            <td align="left">操作员</td>
            <td id="userNname"><div class="datadiv">陈</div></td>
        </tr>
    </table>
    </div>
    <div style="width: 100%;margin-top:20px;float: left;box-shadow: 0 0 3px #ccc;background: white;padding:10px 0 0 0;">
        <p style="padding-left:10px;line-height: 30px;">巡查概况</p>
        <div id='datagkdiv' style="float: left;border-top: solid 1px #ccc;width: 100%;padding:10px;text-align: left;word-break: break-all; ">

        </div>
    </div>
    <div style="width: 100%;margin-top:20px;float: left;box-shadow: 0 0 3px #ccc;background: white;padding:10px 0 0 0;">
        <p style="padding-left:10px;line-height: 30px;">巡查轨迹</p>
        <div style="float: left;border-top: solid 1px #ccc;width: 100%;padding:5px;">
            <div id="mapdiv" style="height:400px;width:100%;"></div>
        </div>
    </div>
</div>
</body>
</html>
