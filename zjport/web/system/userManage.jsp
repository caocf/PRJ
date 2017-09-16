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
    <title>用户管理</title>

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
    <script src="../js/system/userManage.js"></script>
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
    </style>
</head>
<body>
<input type="hidden" name="type" id="type"/>
<input type="hidden" name="structureId" id="structureId"/>
<input type="hidden" name="groupId" id="groupId"/>

<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;">用户管理</b>
    <span style="float: right;font-size: 14px;">系统设置&gt用户管理</span>
</div>
<div style="float: left;width:100%;height:700px;padding:0 10px;">
    <div style="float: left;width:30%;height:100%;padding-right:20px;">
        <div style="float: left;width:100%;height:100%;background-color: white;border:solid 1px #ccc;">
            <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);">
                <div class="phonefz" onclick="phonetab(this,1)"
                     style="border:solid 1px #ccc;border-top:solid 2px rgb(0,103,172);border-bottom:solid 2px white;">
                    用户
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
        <!-- 内部人员搜索栏-->
        <div id='nbryssl'
             style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
            <%--<select  class="form-control" style="background-color: rgb(250,250,250);float:left;width:100px;height:30px;padding:0;line-height: 30px;">
              <option>职位</option>
            </select>--%>
            <div style="heigth:100%;width:300px;float: left;border-radius:4px 0 0 4px;border:solid 1px #cccccc;margin-left: 20px;">
                <input type="text" class="form-control"
                       style="height:100%;padding:5px;width:260px;border: 0;float: left;" placeholder="请输入用户名或手机号"
                       id="nbuserselectinput"/>
                <i class="fa fa-search"
                   style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                   onclick="showUserList('../officeAssistant/showUserList',1)"></i>
            </div>
            <!-- 暂时去掉高级查询 -->
            <%--<i class="fa fa-angle-down" style="float: left;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border:solid 1px #ccc;border-left:0;background-color: rgb(250,250,250)"></i>--%>
        </div>
        <!-- 公共人员搜索栏-->
        <div id='ggryssl'
             style="display:none;float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
            <button class="btn btn-default" style="float: left;">删除</button>
            <div style="heigth:100%;width:300px;float: left;border-radius:4px 0 0 4px;border:solid 1px #cccccc;margin-left: 20px;">
                <input type="text" class="form-control"
                       style="height:100%;padding:5px;width:260px;border: 0;float: left;" placeholder="请输入用户名或手机号"
                       id="gguserselectinput"/>
                <i class="fa fa-search"
                   style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                   onclick="findlxr(1)"></i>
            </div>
            <i class="fa fa-angle-down"
               style="float: left;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border:solid 1px #ccc;border-left:0;background-color: rgb(250,250,250)"></i>
            <button class="btn btn-primary" style="float: right;" data-toggle="modal" data-target="#addgguserModal">
                添加人员
            </button>
            <button class="btn btn-default" style="float: right;margin-right: 10px;">批量导入</button>
        </div>
        <!-- 对应信息列表-->
        <div style="float: left;width:100%;">
            <table class="table table-striped" id="txltable">
                <tr>
                    <%--<th class="center" width="5%"><input type="checkbox" name = "allSelect" id="allSelect" onclick="choose();"/></th>--%>
                    <th class="center" width="10%">序号</th>
                    <th class="center" width="15%">员工姓名</th>
                    <th class="center" width="15%">所属部门</th>
                    <th class="center" width="15%">所在职位</th>
                    <%--<th>在职状态</th>--%><!-- 暂时不存在 -->
                    <th class="center" width="20%">手机号码</th>
                    <th class="center" width="15%">操作</th>
                </tr>
                <%--<tr>
                  <td>01</td>
                  <td>蕾姆</td>
                  <td>办公室</td>
                  <td>秘书</td>
                  <td>在职</td>
                  <td>11111111111</td>
                  <td><span class="clickword"data-toggle="modal" data-target="#checkbguserModal">详情</span>
                    &nbsp;&nbsp;<span class="clickword"data-toggle="modal" data-target="#editbguserModal">编辑</span>
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
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>该单位/部门没有人员</span>
            </div>
        </div>
    </div>

</div>
<!-- 编辑内部用户信息模态框（Modal） -->
<div class="modal fade" id="editbguserModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px;height:700px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    编辑
                </h4>
            </div>
            <div class="modal-body" style="height:570px;background-color: rgb(241,242,246);">
                <div style="float: left;width: 150px;">
                    <div style="float: left;width:150px;height:200px;background-image: url('../image/img_photo.png');background-repeat: no-repeat;background-size: cover;"></div>
                    <button class="btn btn-primary" style="float: left;margin-top:10px;">编辑头像</button>
                </div>
                <div style="float: left;width:603px;height:100%;margin-left:15px;">
                    <form method="post" action="saveUser" name="userForm" id="userForm">
                        <input type="hidden" name="userId" id="userId"/>
                        <table class="table" style="background-color: white;border:solid 1px #ccc;">
                            <tr>
                                <td colspan="2"><b>基本信息</b></td>
                            </tr>
                            <tr>
                                <td class="col-xs-2">职员姓名:</td>
                                <td class="col-xs-10" id="name_edit">
                                    <%--<input class="from-control" type="text" style="border: 0;"placeholder="请输入姓名"/>--%>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-xs-2">所属部门:</td>
                                <td class="col-xs-10" id="dept_edit">
                                    <%--<select   style="background-color: rgb(250,250,250);float:left;height:30px;padding:0;line-height: 30px;">
                                      <option>禁止选择11111111</option>
                                    </select>--%>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-xs-2">职位<span style="color: red;">*</span>:</td>
                                <td class="col-xs-10" id="position_edit">
                                    <%--<select  style="background-color: rgb(250,250,250);float:left;height:30px;padding:0;line-height: 30px;">
                                      <option>禁止选择</option>
                                    </select>--%>
                                </td>
                            </tr>
                            <%--<tr>
                              <td class="col-xs-2">在职状态<span style="color: red;">*</span>:</td>
                              <td class="col-xs-10">
                                <select   style="background-color: rgb(250,250,250);float:left;height:30px;padding:0;line-height: 30px;">
                                  <option>禁止选择</option>
                                </select>
                              </td>
                            </tr>--%>
                            <tr>
                                <td class="col-xs-2">执法证编号:</td>
                                <td class="col-xs-10">
                                    <input class="from-control" type="text" style="border: 0;" placeholder="请输入执法证编号"
                                           id="lawcode_edit" name="lawcode"/>
                                </td>
                            </tr>
                            <%--<tr>
                              <td class="col-xs-2">办公地点:</td>
                              <td class="col-xs-10">
                                <input class="from-control" type="text" style="border: 0;"placeholder="请输入办公地点"/>
                              </td>
                            </tr>--%>
                            <tr>
                                <td class="col-xs-2">用户序号<span style="color: red;">*</span>:</td>
                                <td class="col-xs-10">
                                    <input class="from-control" type="text" style="border: 0;" placeholder="请输入序号"
                                           id="order_edit" name="order"/>
                                </td>
                            </tr>
                        </table>
                        <table class="table" style="background-color: white;margin-top:20px;border:solid 1px #ccc;">
                            <tr>
                                <td colspan="2"><b>通讯信息</b></td>
                            </tr>
                            <tr>
                                <td class="col-xs-2">移动电话<span style="color: red;">*</span>:</td>
                                <td class="col-xs-10" id="phone_edit">
                                    <%--<input class="from-control" type="text" style="border: 0;"placeholder="请输入移动电话" id="phone_edit"/>--%>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-xs-2">办公室电话:</td>
                                <td class="col-xs-10" id="officephone_edit">
                                    <%--<input class="from-control" type="text" style="border: 0;"placeholder="请输入办公室电话" id="officephone_edit"/>--%>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-xs-2">虚拟短号:</td>
                                <td class="col-xs-10" id="fu_edit">
                                    <%--<input class="from-control" type="text" style="border: 0;"placeholder="请输入虚拟短号" id="fu_edit"/>--%>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-xs-2">电子邮件:</td>
                                <td class="col-xs-10" id="email_edit">
                                    <%--<input class="from-control" type="text" style="border: 0;"placeholder="请输入电子邮件" id="email_edit"/>--%>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveUser()">
                    保存
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- /.modal -->
<!--查看内部用户信息 模态框（Modal） -->
<div class="modal fade" id="checkbguserModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px;height:620px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <button type="button" onclick="editDetail()" class="close" data-toggle="modal"
                        data-target="#editbguserModal"
                        data-dismiss="modal" aria-hidden="true"
                        style="color: rgb(66,131,185);font-size: 14px;margin-right: 20px;">
                    <i class="fa fa-pencil-square-o"></i>&nbsp;编辑
                </button>
                <h4 class="modal-title">
                    详情
                </h4>
            </div>
            <div class="modal-body" style="height:560px;background-color: rgb(241,242,246);">
                <div style="float: left;width:150px;height:200px;background-image: url('../image/img_photo.png');background-repeat: no-repeat;background-size: cover;"></div>
                <div style="float: left;width:603px;height:100%;margin-left:15px;">
                    <table class="table" style="background-color: white;border:solid 1px #ccc;">
                        <tr>
                            <td colspan="2"><b>基本信息</b></td>
                        </tr>
                        <tr>
                            <td class="col-xs-2">职员姓名<span style="color: red;">*</span>:</td>
                            <td class="col-xs-10" id="name_info">
                                蕾姆
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-2">所属部门<span style="color: red;">*</span>:</td>
                            <td class="col-xs-10" id="dept_info">
                                办公室
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-2">职位<span style="color: red;">*</span>:</td>
                            <td class="col-xs-10" id="position_info">
                                秘书
                            </td>
                        </tr>
                        <%--<tr>
                          <td class="col-xs-2">在职状态<span style="color: red;">*</span>:</td>
                          <td class="col-xs-10">
                            在职
                          </td>
                        </tr>--%>
                        <tr>
                            <td class="col-xs-2">执法证编号:</td>
                            <td class="col-xs-10" id="lawcode_info">
                                5555555
                            </td>
                        </tr>
                        <%--<tr>
                          <td class="col-xs-2">办公地点:</td>
                          <td class="col-xs-10">
                            嘉兴
                          </td>
                        </tr>--%>
                        <tr>
                            <td class="col-xs-2">用户序号<span style="color: red;">*</span>:</td>
                            <td class="col-xs-10" id="order_info">
                                2222222
                            </td>
                        </tr>
                    </table>
                    <table class="table" style="background-color: white;margin-top:20px;border:solid 1px #ccc;">
                        <tr>
                            <td colspan="2"><b>通讯信息</b></td>
                        </tr>
                        <tr>
                            <td class="col-xs-2">移动电话<span style="color: red;">*</span>:</td>
                            <td class="col-xs-10" id="phone_info">
                                15000000000
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-2">办公室电话:</td>
                            <td class="col-xs-10" id="officephone_info">
                                8888888
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-2">虚拟短号:</td>
                            <td class="col-xs-10" id="fu_info">
                                223
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-2">电子邮件:</td>
                            <td class="col-xs-10" id="email_info">
                                123123@qq.com
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- /.modal -->
<!-- 新增公共用户信息模态框（Modal） -->
<div class="modal fade" id="addgguserModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px;height:430px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    新增人员
                </h4>
            </div>
            <div class="modal-body" style="height:310px;background-color: rgb(241,242,246);">
                <div style="float: left;width:150px;height:200px;border:solid 1px red;"></div>
                <div style="float: left;width:603px;height:100%;margin-left:15px;">
                    <table class="table" style="background-color: white;border:solid 1px #ccc;">
                        <tr>
                            <td colspan="2"><b>基本信息</b></td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户姓名<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户手机号<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入用户手机号"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户单位:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入用户单位"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">船员适任证编号:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户船铭牌号:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">所属管理单位<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <select style="background-color: rgb(250,250,250);float:left;height:30px;padding:0;line-height: 30px;">
                                    <option>禁止选择</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary">
                    保存
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- /.modal -->
<!-- 编辑公共用户信息模态框（Modal） -->
<div class="modal fade" id="editgguserModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px;height:430px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    编辑人员
                </h4>
            </div>
            <div class="modal-body" style="height:310px;background-color: rgb(241,242,246);">
                <div style="float: left;width:150px;height:200px;border:solid 1px red;"></div>
                <div style="float: left;width:603px;height:100%;margin-left:15px;">
                    <table class="table" style="background-color: white;border:solid 1px #ccc;">
                        <tr>
                            <td colspan="2"><b>基本信息</b></td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户姓名<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户手机号<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入用户手机号"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户单位:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入用户单位"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">船员适任证编号:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户船铭牌号:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">所属管理单位<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <select style="background-color: rgb(250,250,250);float:left;height:30px;padding:0;line-height: 30px;">
                                    <option>禁止选择</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary">
                    保存
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- /.modal -->
<!-- 查看公共用户信息模态框（Modal） -->
<div class="modal fade" id="checkgguserModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:800px;height:350px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <button type="button" class="close" data-toggle="modal" data-target="#editgguserModal"
                        data-dismiss="modal" aria-hidden="true"
                        style="color: rgb(66,131,185);font-size: 14px;margin-right: 20px;">
                    <i class="fa fa-pencil-square-o"></i>&nbsp;编辑
                </button>
                <h4 class="modal-title">
                    详情
                </h4>
            </div>
            <div class="modal-body" style="height:310px;background-color: rgb(241,242,246);">
                <div style="float: left;width:150px;height:200px;border:solid 1px red;"></div>
                <div style="float: left;width:603px;height:100%;margin-left:15px;">
                    <table class="table" style="background-color: white;border:solid 1px #ccc;">
                        <tr>
                            <td colspan="2"><b>基本信息</b></td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户姓名<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户手机号<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入用户手机号"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户单位:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入用户单位"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">船员适任证编号:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">用户船铭牌号:</td>
                            <td class="col-xs-9">
                                <input class="from-control" type="text" style="border: 0;" placeholder="请输入姓名"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-xs-3">所属管理单位<span style="color: red;">*</span>:</td>
                            <td class="col-xs-9">
                                <select style="background-color: rgb(250,250,250);float:left;height:30px;padding:0;line-height: 30px;">
                                    <option>禁止选择</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- /.modal -->
<!--群组新增 模态框（Modal） -->
<div class="modal fade" id="addqzModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 720px;height: 670px;">
            <div class="modal-header">
                <h4 class="modal-title" id="qzmodaltotal">
                    新增群组
                </h4>
            </div>
            <form method="post" id="groupForm" name="groupForm" action="saveGroup">
                <div class="modal-body" style="height:540px;padding:10px;background-color: rgb(241,242,246);">
                    <div style="height:40px;float: left;width: 100%;line-height: 40px;padding-left:10px;border: solid 1px #ccc;background-color: white;">
                        <span style="float: left;">群组名称：</span>
                        <input placeholder="请输入群组名称" id="groupName" name="groupName" type="text"
                               style="height:30px;float:left;border: 0;margin-top:5px;width: 300px;line-height: 30px;padding-left:10px;"/>
                    </div>
                    <div style="float: left;width: 100%;border: solid 1px #ccc;background-color: white;margin-top:10px;">
                        <div style="float: left;width: 100%;border-bottom: solid 1px rgb(244,244,244);height:40px;line-height: 40px;text-align: left;padding-left:10px;">
                            <b>选择群组成员</b>
                        </div>
                        <div style="float: left;width: 350px;border-right: solid 1px rgb(244,244,244);">
                            <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
                                <div style="heigth:100%;width:330px;float: left;border-radius:4px;border:solid 1px #cccccc;">
                                    <input type="text" class="form-control"
                                           style="height:100%;padding:5px;width:290px;border: 0;float: left;"
                                           placeholder="请输入姓名或手机号" id="leftlxrinput"/>
                                    <i class="fa fa-search"
                                       style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                                       onclick="findlxr(1)"></i>
                                </div>
                            </div>

                            <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);">
                                <div style="text-align:center;height: 100%;width: 80px;float: left;line-height: 50px;border:solid 1px #ccc;border-top:solid 2px rgb(0,103,172);border-bottom:solid 2px white;">
                                    内部人员
                                </div>
                            </div>
                            <div style="float: left;height:320px;width:100%;overflow: auto;padding:10px;"
                                 class="treediv" id="lxrdiv">
                                <ul id="leftTree" class="ztree"></ul>

                            </div>
                        </div>
                        <div style="float: left;width: 100px;border-right: solid 1px rgb(244,244,244);">
                            <div onclick="addlxr()" class="btn btn-default"
                                 style="width:74px;float: left;margin-top:120px;margin-left:13px;">添加&nbsp&gt
                            </div>
                            <div onclick='dellxr()' class="btn btn-default"
                                 style="width:74px;float: left;margin-top:12px;margin-left:13px;">删除&nbsp&lt
                            </div>
                            <div onclick="clearlxr()" class="btn btn-default"
                                 style="width:74px;float: left;margin-top:120px;margin-left:13px;">清空&nbsp&lt&lt
                            </div>
                        </div>
                        <div style="float: left;width: 248px;">
                            <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
                                <div style="heigth:100%;width:100%;float: left;border-radius:4px;border:solid 1px #cccccc;">
                                    <input type="text" class="form-control"
                                           style="height:100%;padding:5px;width:198px;border: 0;float: left;"
                                           placeholder="请输入姓名或手机号" id="rightlxrinput"/>
                                    <i class="fa fa-search"
                                       style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                                       onclick="findlxr(2)"></i>
                                </div>
                            </div>
                            <div style="float: left;height:370px;width:100%;overflow: auto;padding:5px;" class="treediv"
                                 id="addlxrdiv">
                                <ul id="rightTree" class="ztree"></ul>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="hidden" id="userIds" name="userIds"/>
                <input type="hidden" id="id" name="id"/>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="sclxr()">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal" onclick="clean()">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- 模态框（Modal）删除提示 -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top:300px;">
        <div class="modal-content" style="width: 400px;">
            <div class="modal-header">
                <h4 class="modal-title" id="deleteModalLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body" style="line-height: 20px;">
                <div style="background-color: rgb(255,168,0);height:20px;width:20px;border-radius:10px;text-align: center; color:white;float:left;">
                    !
                </div>
                &nbsp;您确定要删除么？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="deleteIt()">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- /.modal -->
</body>
</html>
