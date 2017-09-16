<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.text.SimpleDateFormat" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>查询</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../css/common/bluetree.css" rel="stylesheet" type="text/css" />

    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- CK Editor -->
    <script src="../js/ckeditor/ckeditor.js"></script>
    <!-- 页面js -->
    <script src="../js/common/bootpaging.js"></script>
    <script src="../js/query/query.js"></script>
    <!--css-->
    <style type="text/css">
        .clickword{
            color:rgb(31,116,180);
            cursor: pointer;
        }
        .zhcx_left_label {
            background-color: rgb(249,249,249);
            text-align: right!important;
            color: #666!important;
        }
        .zhcx_table td {
            text-align: left;
            color: #333;
            font-size: 14px;
            word-break: break-all;
            word-wrap: break-word;
            width: 179px;
        }
    </style>
</head>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<body>
<input type="hidden" name="baseId" id="baseId"/>
<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;" >查询</b>
    <span style="float: right;font-size: 14px;">查询统计&gt查询</span>
</div>
<div style="float: left;width:100%;height:700px;padding:0 10px;">
    <div style="float: left;width:30%;height:100%;padding-right:20px;">
        <div style="float: left;width:100%;height:100%;background-color: white;border:solid 1px #ccc;">
            <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:0 10px;">
                <b style="float: left;line-height: 50px;"><i class="fa fa-list"></i>&nbsp;目录</b>
            </div>
            <div style="float: left;height:600px;width:100%;overflow: auto;" class="treediv">
                <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
                <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
                <ul id="konwledgeTree" class="ztree bluetree"></ul>
            </div>
        </div>
    </div>
    <div style="float: left;width:69%;height:100%;background-color: white;border:solid 1px #ccc;">
        <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding-left:10px;line-height: 50px;text-align: left;" >
            <span id="queryname"></span>
        </div>
        <input type="hidden" id="structureId" name = "structureId"/>
        <!-- 搜索栏-->
        <div id='ggryssl'style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
            <div style="heigth:100%;width:300px;float: right;border-radius:4px;border:solid 1px #cccccc;margin-right: 20px;">
                <input type="text" class="form-control" style="height:100%;padding:5px;width:260px;border: 0;float: left;"placeholder="请输入关键字" id="search" name="search"/>
                <i class="fa fa-search" style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;" onclick="querylistshow('../dataquery/single',1)"></i>
            </div>
        </div>
        <!-- 对应信息列表-->
        <div style="float: left;width:100%;">
            <table class="table table-striped" id="txltable">
            </table>
            <div class='bootpagediv' style='width:100%;margin-right:20px;'>
                <span style="float: left;margin-left:10px;line-height: 20px;color:#666;" id="pagedetal">
                </span>
                <nav style='float:right;display:none' id='pageshow'>
                    <ul class="pagination" id='pagination'>
                    </ul>
                </nav>
            </div>
            <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>该目录下无数据</span>
            </div>
        </div>
    </div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="zhcxxq" tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog zhcxdiv"
         style='margin-top: 130px;width: 1076px;'>
        <div class="modal-content" style='height:540px;'>
            <div class="modal-header"
                 style='background-color: rgb(238,238,238);border-radius:4px 4px 0 0;'>
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">详情</h4>
            </div>
            <div class="modal-body zhcxbody"
                 style='height:480px;padding:0;overflow-x:auto;'>
                <table class='table table-bordered zhcx_table'>
                </table>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
</body>
</html>
