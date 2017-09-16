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
    <title>知识库</title>
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
    <script src="../js/officeAssistant/knowledgeBase.js"></script>
    <!--css-->
    <style type="text/css">

        .clickword {
            color: rgb(31, 116, 180);
            cursor: pointer;
        }
    </style>
</head>
<body>
<input type="hidden" name="baseId" id="baseId"/>

<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;">知识库</b>
    <span style="float: right;font-size: 14px;">办公助理&gt知识库</span>
</div>
<div style="float: left;width:100%;height:700px;padding:0 10px;">
    <div style="float: left;width:30%;height:100%;padding-right:20px;">
        <div style="float: left;width:100%;height:100%;background-color: white;border:solid 1px #ccc;">
            <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:0 10px;">
                <b style="float: left;line-height: 50px;"><i class="fa fa-list"></i>&nbsp;知识库目录</b>
                <i onclick="addmltreemake(-100)" data-toggle="modal" data-target="#addmlModal"
                   style="float: right;line-height: 50px;color: #0066cc;font-size: 20px;cursor: pointer;"
                   class="fa fa-plus-square"></i>
                <%--<ul class="dropdown-menu" role="menu" style="margin-top:-15px;">--%>
                <%--<li><a href="#">添加同级目录</a></li>--%>
                <%--<li><a href="#">添加子目录</a></li>--%>
                <%--</ul>--%>
            </div>
            <div style="float: left;height:600px;width:100%;overflow: auto;" class="treediv">
                <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
                <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
                <ul id="konwledgeTree" class="ztree bluetree"></ul>
            </div>
        </div>
    </div>
    <div style="float: left;width:69%;height:100%;background-color: white;border:solid 1px #ccc;">
        <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding-left:10px;line-height: 50px;text-align: left;">
            <span id="structureTitle"></span>
        </div>
        <input type="hidden" id="structureId" name="structureId"/>
        <!-- 搜索栏-->
        <div id='ggryssl'
             style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
            <button class="btn btn-default" style="float: left;" onclick="batchDeleteOrOne()">删除</button>
            <div class="dropdown" style="float: left;width: 150px;margin-left:20px;">
                <button type="button" class="btn btn-default dropdown-toggle" id="moveMenu1"
                        style="background-color: rgb(250,250,250);"
                        data-toggle="dropdown">
                    <span id="movename">移动至</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1"
                    style="width: 300px;position:relative;height:auto;overflow-y: auto;overflow-x: hidden" id="moveul">
                    <ul id="moveTree" class="ztree bluetree"></ul>
                </ul>
            </div>
            <div style="heigth:100%;width:300px;float: left;border-radius:4px;border:solid 1px #cccccc;margin-left: 20px;">
                <input type="text" class="form-control"
                       style="height:100%;padding:5px;width:260px;border: 0;float: left;" placeholder="请输入标题关键字"
                       id="gguserselectinput" name="search"/>
                <i class="fa fa-search"
                   style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                   onclick="searchIt()"></i>
            </div>
            <button class="btn btn-primary qxz qx14" style="float: right;" onclick="add()">新建文档</button>
        </div>
        <!-- 对应信息列表-->
        <div style="float: left;width:100%;">
            <table class="table table-striped" id="txltable">
                <tr>
                    <th class="center" width="5%"><input type="checkbox" name="allSelect" id="allSelect"
                                                         onclick="choose();"/></th>
                    <th class="center" width="10%">序号</th>
                    <th width="36%">标题</th>
                    <th width="26%">创建时间</th>
                    <th class="center" width="23%">操作</th>
                </tr>
                <%--<tr>
                  <td>01</td>
                  <td>蕾姆</td>
                  <td>11111111111</td>
                  <td><span class="clickword" onclick="window.location.href='../officeAssistant/Office_view_file.jsp'">详情</span>
                    &nbsp;&nbsp;<span class="clickword"onclick="window.location.href='../officeAssistant/Office_edit_file.jsp'">编辑</span>
                    &nbsp;&nbsp;<span class="clickword"data-toggle="modal" data-target="#editbguserModal">删除</span>
                  </td>
                </tr>--%>
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
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>该目录下无文件</span>
            </div>
        </div>
    </div>
</div>
<!-- 添加目录模态框（Modal） -->
<div class="modal fade" id="addmlModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px;height:430px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    <span id="modalName">添加目录</span>
                </h4>
            </div>
            <form id="structureForm" action="structureSubmit" method="post">
                <div class="modal-body" style="height:310px;background-color: rgb(241,242,246);padding:15px;">
                    <div style="float: left;width:100%;">

                        <table class="table" style="background-color: white;border:solid 1px #ccc;">
                            <tr>
                                <td class="col-xs-2" style="line-height: 34px;">上级目录<span style="color: red;">*</span>:
                                </td>
                                <td class="col-xs-10" class="dropdown">
                                    <div class="dropdown" style="float: left;width: 300px;">
                                        <button type="button" class="btn btn-default dropdown-toggle" id="dropdownMenu1"
                                                style="background-color: rgb(250,250,250);"
                                                data-toggle="dropdown">
                                            <span id="sjmlname"></span>
                                            <span class="caret"></span>
                                        </button>
                                        <input type="hidden" name="parentStructure" value="330000" id="parentStructure"/>
                                        <ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1"
                                            style="width: 300px;height:200px;overflow-y: auto;overflow-x: hidden"
                                            id="addmlul">
                                            <ul id="addmlTree" class="ztree bluetree"></ul>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-xs-2">目录名称<span style="color: red;">*</span>:</td>
                                <td class="col-xs-10">
                                    <input class="from-control" type="text" style="border: 0;" maxlength="10" placeholder="请输入目录名称"
                                           id="structureName" name="structureName"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="float: left;width:100%;border: solid 1px #ccc;padding:10px;background-color: white;">
                        <span style="float: left;line-height: 30px;">目录描述：</span>
                        <textarea style="resize: none;float: left;border: 0;padding:5px;height:150px;width:670px;"
                                  placeholder="请输入目录描述" maxlength="50" id="structureDescribe" name="structureDescribe"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="send()">
                        确定
                    </button>
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                </div>
                <input type="hidden" name="structure" id="structure"/>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- /.modal -->
</body>
</html>
