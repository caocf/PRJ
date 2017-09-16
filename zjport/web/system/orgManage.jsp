<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.util.CommonConst" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>单位管理</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/bluetree.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- CK Editor -->
    <script src="../js/ckeditor/ckeditor.js"></script>
    <!-- 页面js -->
    <script src="../js/common/bootpaging.js"></script>
    <script src="../js/system/orgManage.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
    <style type="text/css">
        .clickword {
            color: rgb(31, 116, 180);
            cursor: pointer;
        }

        .qzdiv {
            float: left;
            width: 100%;
            height: 40px;
            line-height: 40px;
            padding-left: 20px;
            cursor: pointer;
        }

        #qzbq {
            float: left;
            width: 100%;
            display: none;
        }

        #leftTree li a {
            display: -moz-inline-box;
        }

        #leftTree .node_name {
            float: left;
        }
        .table tr{
            height:50px;
        }
        .table td{
            line-height: 34px!important;
        }
    </style>
</head>
<body>
<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;">单位管理</b>
    <span style="float: right;font-size: 14px;">系统设置&gt单位管理</span>
</div>
<div style="float: left;width:100%;height:700px;padding:0 10px;">
    <div style="float: left;width:30%;height:100%;padding-right:20px;">
        <div style="float: left;width:100%;height:100%;background-color: white;border:solid 1px #ccc;">
            <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);">
                <div class="phonefz" onclick="phonetab(this,1)"
                     style="border:solid 1px #ccc;border-top:solid 2px rgb(0,103,172);border-bottom:solid 2px white;">
                    单位
                </div>
                <%--<div class="phonefz" onclick="phonetab(this,2)">公众人员</div>--%>
            </div>
            <div style="float: left;height:600px;width:100%;overflow: auto;" class="treediv">
                <ul id="messageTree" class="ztree bluetree"></ul>
            </div>
        </div>
    </div>
    <div style="float: left;width:69%;height:100%;background-color: white;border:solid 1px #ccc;">
        <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding-left:10px;line-height: 50px;text-align: left;">
            <sapn id="orgName"></sapn>
        </div>

        <!-- 对应信息列表-->

        <table class="table" style="background-color: white;margin-top:20px;">
            <tr>
                <td colspan="2"><b>单位基本信息</b> &nbsp;&nbsp;<span style="color: #c9cccf;">这是同步过来的信息，在本系统无法修改</span></td>
            </tr>
            <tr>
                <td class="col-xs-2">单位名称：</td>
                <td class="col-xs-10" id="org_name">
                    15000000000
                </td>
            </tr>
            <tr>
                <td class="col-xs-2">单位代码：</td>
                <td class="col-xs-10" id="org_code">
                    8888888
                </td>
            </tr>
            <tr>
                <td class="col-xs-2">单位简称：</td>
                <td class="col-xs-10" id="org_short_name">
                    223
                </td>
            </tr>
            <tr>
                <td class="col-xs-2">单位域名：</td>
                <td class="col-xs-10" id="org_domains">
                    123123@qq.com
                </td>
            </tr>
            <tr>
                <td class="col-xs-2">上级单位：</td>
                <td class="col-xs-10" id="parent_org">
                    123123@qq.com
                </td>
            </tr>
        </table>
        <table class="table" style="background-color: white;">
            <tr>
                <td colspan="2"><b>单位所属信息</b> &nbsp;&nbsp;&nbsp;&nbsp;<a data-toggle="modal" data-target="#editModal" href="javascript:void(0)" onclick="selectArea()">修改单位所属信息</a></td>
            </tr>
            <tr>
                <td class="col-xs-2">单位地址：</td>
                <td class="col-xs-10" id="org_address">
                    蕾姆
                </td>
            </tr>
            <tr>
                <td class="col-xs-2">单位电话：</td>
                <td class="col-xs-10" id="org_phone">
                    ******
                </td>
            </tr>
            <tr>
                <td class="col-xs-2">单位所属区域：</td>
                <td class="col-xs-10" id="org_area">
                    秘书
                </td>
            </tr>
            <tr>
                <td class="col-xs-2">单位传真：</td>
                <td class="col-xs-10" id="org_fox">
                    秘书
                </td>
            </tr>
            <tr>
                <td class="col-xs-2">单位邮编：</td>
                <td class="col-xs-10" id="org_post">
                    秘书
                </td>
            </tr>

        </table>
    </div>
</div>

<!-- 辅助信息修改模态框（Modal） -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px;height:410px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" >
                    修改单位所属信息
                </h4>
            </div>
            <form id="orgForm" action="orgDetailSubmit" method="post">
            <div class="modal-body" style="height:290px;background-color: rgb(241,242,246);">
                <div style="float: left;width:96%;height:100%;margin-left:15px;">

                    <table class="table" style="background-color: white;border:solid 1px #ccc;">
                        <tr>
                            <td class="col-xs-3">单位地址：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;"placeholder="请输入单位地址" name="orgAddress" id="orgAddress"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">单位电话：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;"placeholder="请输入单位电话" name="orgPhone" id="orgPhone"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">单位所属区域：</td>
                            <td class="col-xs-9">
                                <div class="dropdown" style="float: left;width: 300px;">
                                    <button type="button" class="btn btn-default dropdown-toggle" id="dropdownMenu1"
                                            style="background-color: rgb(250,250,250);"
                                            data-toggle="dropdown">
                                        <span id="sjmlname"></span>
                                        <span class="caret"></span>
                                    </button>
                                    <input type="hidden" name="orgArea" id="orgArea"/>
                                    <ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1"
                                        style="width: 300px;height:200px;overflow-y: auto;overflow-x: hidden"
                                        id="addmlul">
                                        <ul id="addmlTree" class="ztree bluetree"></ul>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">单位传真：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;"placeholder="请输入单位传真号" name="orgFox" id="orgFox"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">单位邮编：</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;"placeholder="请输入单位邮编" name="orgPost" id="orgPost"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="send()">
                    保存
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
            </div>
            <input type="hidden" name="orgId" id="orgId"/>
            </form>
        </div><!-- /.modal-content -->
    </div></div><!-- /.modal -->
</body>
</html>
