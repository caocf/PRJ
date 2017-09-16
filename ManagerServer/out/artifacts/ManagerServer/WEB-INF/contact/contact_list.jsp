<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>通讯录</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="../js/common/plugins/ztree/css/zTreeStyle/zTreeStyle.css">
    <link href="../js/css/zTreeStyle/bluetree.css" rel="stylesheet" type="text/css"/>
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/common.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
    <script src="../js/contact/contact_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword {
            color: rgb(0, 104, 177);
            cursor: pointer;
        }
    </style>
</head>
<body>
<input type="hidden" id="deptid" value="">
<input type="hidden" id="isleaf" value="0">
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header">
    <h1>
        通讯录
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-book"></i>通讯录</a></li>
    </ol>
</section>

<section class="content">
    <div class="box">
        <div id="deptsdiv" class="col-xs-3" style="float:left;width:20%;background-color: white">
            <div class="row" style="background-color: white;height: 50px">
                <label style="padding: 10px 0 0 20px;font-size: large;color: #0d6aad">浙江省港航管理局</label>
            </div>
            <!--<div   style="background-color: white;height: 50px;">
                <label style="padding: 10px 0 0 20px;font-size: large;color: #0d6aad">浙江省港航管理局</label>
            </div>-->
            <div style="float: left;height:700px;width:100%;overflow: auto;" class="treediv">
                <ul id="depttree" class="ztree bluetree"></ul>
            </div>
        </div>
        <div id="usersdiv" style="float:right;width:79%;background-color: white">
            <div class="row" style="width:100%;background-color: white;height: 50px;margin-left: 0px">
                <label style="padding: 10px 0 0 20px;font-size: large">联系人</label>
            </div>
            <div style="heigth:100%;width:400px;float: left;margin: 20px;border-radius:4px;border:solid 1px #cccccc;">
                <input type="text" class="form-control"
                       style="height:100%;padding:5px;width:330px;border: 0;float: left;" placeholder="请输入姓名"
                       id="iptusername"/>
                <div style="float: left;height:30px;width:28px;">
                    <i class="fa fa-close"
                       style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;"
                       id="clearbtn"></i>
                </div>
                <i class="fa fa-search"
                   style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                   onclick="Search(1)"></i>
            </div>
            <!-- 对应信息列表-->
            <div style="float: left;width:100%;height: 628px">
                <table class="table table-striped" id="txltable">
                    <tr>
                        <th class="center" width="10%">序号</th>
                        <th class="center" width="15%">姓名</th>
                        <th class="center" width="15%">所属部门</th>
                        <th class="center" width="15%">所在职位</th>
                        <th class="center" width="20%">手机号码</th>
                        <th class="center" width="15%">操作</th>
                    </tr>
                </table>
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
                    <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>该单位/部门没有人员</span>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
