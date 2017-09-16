<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.zjport.model.TOrg" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <title>船舶跟踪</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <!-- Bootstrap 3.3.4 -->
    <link href="js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="css/common/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!-- FontAwesome 4.3.0 -->
    <link href="css/common/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="js/common/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="js/smart/cbgz.js" type="text/javascript"></script>
    <!--地图js-->
    <script type="text/javascript" src="http://172.20.24.105/WebtAPI/wapi.js"></script>
    <!--css-->
    <style type="text/css">

    </style>
</head>
<input type="hidden" value="<%=(String) request.getSession().getAttribute("cbmc")%>" id="shipname"/>
<body style="font-family: Helvetica,Tahoma,'Microsoft YaHei','微软雅黑',Arial,STXihei,'华文细黑',SimSun,'宋体',Heiti,'黑体',sans-serif;">
            <div class="modal-body" style="height:auto;padding:0;width:100%;">
                <p style="margin-bottom:0;line-height: 30px;padding-left:10px;"id="cbgztitle">111</p>
                <div style="float: left;height:350px;width:100%;position: relative;" >
                    <div id="shipgzmap_div"
                         style="float: left;width:100%;height:100%;position: absolute;">
                    </div>
                    <p style="color:red;display:none;position: absolute;z-index: 3;left:10px;top:10px;" id="cbgzredword"></p>
                </div>
            </div>
            <button class="btn btn-default" style="position: absolute; top:80%;left:80%;" id="sendbtn"><i class="fa fa-envelope"></i>&nbsp;发送短信</button>
</body>
</html>
