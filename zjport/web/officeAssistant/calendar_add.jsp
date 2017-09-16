<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/2
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE html>
<html>
<head>
    <title>新建日程</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../js/common/My97DatePicker/skin/default/datepicker.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- CK Editor -->
    <script src="../js/ckeditor/ckeditor.js"></script>
    <!-- 页面js -->
    <script src="../js/officeAssistant/calendar_add.js" type="text/javascript"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_released.png">
        新建日程
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>

</section>

<section class="content" style="padding-top:0; margin-top: 5px;">
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;">
        <form class="form-horizontal" enctype="multipart/form-data" id="calendarForm" action="calendarSubmit"
              method="post">
            <div class="box-body">
                <div style="background-color: white;width:100%;padding-top: 10px;box-shadow: 0 0 3px #ccc;">
                    <div class="form-group">
                        <label class="control-label" style="float: left;width:100px;">日程类型<span style="color: red">*</span>：</label>

                        <div style="float: left;margin-left:10px;">
                            <div style="width:200px;">
                                <select id="disabledSelect" name="calendarType" class="form-control"
                                        style="background-color: rgb(250,250,250);">
                                    <option name="choose" selected="selected" value="1">日常事物</option>
                                    <option name="choose" value="2">会议安排</option>
                                    <option name="choose" value="3">工作安排</option>
                                    <option name="choose" value="4">其他任务</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
                        <label for="title" class="control-label" style="float: left;width:100px;">日程标题<span style="color: red">*</span>：</label>

                        <div style="margin-left:100px;">
                            <input type="input" class="form-control" style="border: 0;" maxlength="60" id="title" name="title"
                                   placeholder="请输入文章标题，标题文字不能超过60个中文字符">
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
                        <label class="control-label" style="float: left;width:100px;">添加附件&nbsp;：</label>

                        <div class="btn btn-default btn-file" style="margin-left: 15px;">
                            <i class="fa fa-paperclip"></i> 添加附件
                            <input type="file" name="attachment" id="attachment" multiple="multiple"/>
                        </div>
                        &nbsp;&nbsp;<span id="filenamespan"></span>
                    </div>
                    <div class="form-group" style="margin-bottom: 0;">
                        <textarea id="editor1" name="editor1" rows="10" cols="80" placeholder="请输入文章内容"></textarea>
                    </div>
                    <div class="form-group " style="margin-bottom: 0;border-top: solid 1px #ccc;padding:10px 0;">
                        <label class="control-label"style="text-align: left;float: left;width:100px;padding-left:10px;">紧急程度<span
                                style="color: red">*</span>：</label>

                        <div style="float:left;height:27px;color:#666">
                            <label class="checkbox-inline">
                                <input type="radio" name="calendarIsUrgent" autocomplete="off"
                                       value="1" checked="checked"> 一般
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="calendarIsUrgent" autocomplete="off"
                                       value="2"> 重要
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="calendarIsUrgent" autocomplete="off"
                                       value="3">紧急
                            </label>
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
                        <label class="control-label" style="text-align: left;float: left;width:100px;padding-left:10px;">收件人<span
                                style="color: red">*</span>：</label>
                        <div data-toggle="modal" onclick="showDept()"
                             data-target="#myModal"
                             style='cursor:pointer;height:30px;float: right;margin-right:10px;width:80px;color: #333;line-height: 27px;padding:0 10px;background-color: rgb(250,250,250);border: solid 1px #ccc;text-align: center;'>
                            <span style='font-size: 15px;line-height: 27px;float: left;cursor: pointer;'><i
                                    class="fa fa-user"></i>&nbsp;&nbsp;选择</span>
                        </div>
                        <div style="padding-top:7px;margin-left:110px;margin-right:100px;">
                            <input type="hidden" id="useridstr" name="useridstr" value=""/>
                            <span id='usernamespan'
                                  style="display: block;color: #cccccc;width: 100%;overflow: hidden;text-overflow:ellipsis; word-break:keep-all;white-space:nowrap;">请选择收件人</span>
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
                        <label class="control-label" style="text-align: left;float: left;width:100px;padding-left:10px;">时间<span
                                style="color: red">*</span>：</label>

                        <div style="float: left;">
                            <div style="float:left;">
                                <input type="text" placeholder="起始时间" id="beginTime" name="beginTime"
                                       onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'})"
                                       readonly="readonly" class="Wdate"
                                       style="float:left;margin-left:10px;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                                <%--<div  style="width:100px;float: left;margin-left:10px;">--%>
                                <%--<select class="form-control"  style="background-color: rgb(250,250,250);height:30px;">--%>
                                <%--<option name="choose" selected="selected" value="0">00:00</option>--%>
                                <%--<option name="choose"  value="0">01:00</option>--%>
                                <%--<option name="choose"  value="0">02:00</option>--%>
                                <%--<option name="choose"  value="0">03:00</option>--%>
                                <%--<option name="choose"  value="0">04:00</option>--%>
                                <%--<option name="choose"  value="0">05:00</option>--%>
                                <%--<option name="choose"  value="0">06:00</option>--%>
                                <%--<option name="choose"  value="0">07:00</option>--%>
                                <%--<option name="choose"  value="0">08:00</option>--%>
                                <%--<option name="choose"  value="0">09:00</option>--%>
                                <%--<option name="choose"  value="0">10:00</option>--%>
                                <%--<option name="choose"  value="0">11:00</option>--%>
                                <%--<option name="choose"  value="0">12:00</option>--%>
                                <%--<option name="choose"  value="0">13:00</option>--%>
                                <%--<option name="choose"  value="0">14:00</option>--%>
                                <%--<option name="choose"  value="0">15:00</option>--%>
                                <%--<option name="choose"  value="0">16:00</option>--%>
                                <%--<option name="choose"  value="0">17:00</option>--%>
                                <%--<option name="choose"  value="0">18:00</option>--%>
                                <%--<option name="choose"  value="0">19:00</option>--%>
                                <%--<option name="choose"  value="0">20:00</option>--%>
                                <%--<option name="choose"  value="0">21:00</option>--%>
                                <%--<option name="choose"  value="0">22:00</option>--%>
                                <%--<option name="choose"  value="0">23:00</option>--%>
                                <%--</select>--%>
                                <%--</div>--%>
                                <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;">至</span>
                                <input type="text" id="endTime" name="endTime" placeholder="结束时间"
                                       onFocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}'})"
                                       readonly="readonly" class="Wdate"
                                       style="float:left;margin-left:10px;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                                <%--<div  style="width:100px;float: left;margin-left:10px;">--%>
                                <%--<select class="form-control"  style="background-color: rgb(250,250,250);height:30px;">--%>
                                <%--<option name="choose" selected="selected" value="0">00:00</option>--%>
                                <%--<option name="choose"  value="0">01:00</option>--%>
                                <%--<option name="choose"  value="0">02:00</option>--%>
                                <%--<option name="choose"  value="0">03:00</option>--%>
                                <%--<option name="choose"  value="0">04:00</option>--%>
                                <%--<option name="choose"  value="0">05:00</option>--%>
                                <%--<option name="choose"  value="0">06:00</option>--%>
                                <%--<option name="choose"  value="0">07:00</option>--%>
                                <%--<option name="choose"  value="0">08:00</option>--%>
                                <%--<option name="choose"  value="0">09:00</option>--%>
                                <%--<option name="choose"  value="0">10:00</option>--%>
                                <%--<option name="choose"  value="0">11:00</option>--%>
                                <%--<option name="choose"  value="0">12:00</option>--%>
                                <%--<option name="choose"  value="0">13:00</option>--%>
                                <%--<option name="choose"  value="0">14:00</option>--%>
                                <%--<option name="choose"  value="0">15:00</option>--%>
                                <%--<option name="choose"  value="0">16:00</option>--%>
                                <%--<option name="choose"  value="0">17:00</option>--%>
                                <%--<option name="choose"  value="0">18:00</option>--%>
                                <%--<option name="choose"  value="0">19:00</option>--%>
                                <%--<option name="choose"  value="0">20:00</option>--%>
                                <%--<option name="choose"  value="0">21:00</option>--%>
                                <%--<option name="choose"  value="0">22:00</option>--%>
                                <%--<option name="choose"  value="0">23:00</option>--%>
                                <%--</select>--%>
                                <%--</div>--%>
                                <!-- 暂时去掉全天事件 -->
                                <%--<label class="checkbox-inline" style="margin-left: 10px;">
                                    <input type="checkbox"  autocomplete="off" >全天事件
                                </label>--%>
                            </div>
                        </div>
                    </div>
                    <div class="form-group " style="margin-bottom: 10px;border-top: solid 1px #ccc;padding:10px 0;">
                        <label class="control-label" style="text-align: left;float: left;width:100px;padding-left:10px;">提醒<span
                                style="color: red">*</span>：</label>

                        <div style="height:27px;color:#666;float: left;">
                            <label class="checkbox-inline" style="float: left;">
                                <input type="radio" name="isAlert" autocomplete="off"
                                       value="0" checked="checked"> 不提醒
                            </label>
                            <label class="checkbox-inline" style="float: left;margin-left:10px;">
                                <input type="radio" name="isAlert" autocomplete="off"
                                       value="1"> 提醒
                            </label>

                            <div style="float: left;margin-left:10px;">
                                <select class="form-control"
                                        style="background-color: rgb(250,250,250);height:30px;padding:0;line-height: 30px;">
                                    <option name="choose" selected="selected" value="1">提前15分钟</option>
                                    <option name="choose" selected="selected" value="2">提前30分钟</option>
                                    <option name="choose" selected="selected" value="3">提前45分钟</option>
                                    <option name="choose" selected="selected" value="4">提前60分钟</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.box-body -->
            <div class="box-footer" style="background-color: rgba(0,0,0,0);">
                <div class="pull-left">
                    <button type="button" class="btn btn-primary" onclick="send()"><i class="fa fa-envelope-o"></i>发布
                    </button>
                    <%--<button class="btn btn-default">预览</button>--%>
                    <%--<button class="btn btn-default">取消</button>--%>
                </div>
                <%--<button class="btn btn-default"><i class="fa fa-times"></i> 发布</button>--%>
            </div>
            <!-- /.box-footer -->
        </form>
    </div>
    <!-- /. box -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 700px;height: 550px;">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    选择收件人
                </h4>
            </div>
            <div class="modal-body" style="height:430px;padding:0;">
                <div style="float: left;height:100%;width: 350px;border-right: solid 1px rgb(244,244,244);">
                    <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
                        <div style="heigth:100%;width:300px;float: left;border-radius:4px 0 0 4px;border:solid 1px #cccccc;">
                            <input type="text" class="form-control"
                                   style="height:100%;padding:5px;width:260px;border: 0;float: left;"
                                   placeholder="请输入姓名或手机号" id="leftlxrinput"/>
                            <i class="fa fa-search"
                               style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                               onclick="findlxr(1)"></i>
                        </div>
                        <%--<i class="fa fa-angle-down" style="float: left;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border:solid 1px #ccc;border-left:0;background-color: rgb(250,250,250)"></i>--%>
                    </div>
                    <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);">
                        <div class="phonefz" onclick="phonetab(this)"
                             style="border:solid 1px #ccc;border-top:solid 2px rgb(0,103,172);border-bottom:solid 2px white;">
                            内部人员
                        </div>
                        <%--<div class="phonefz" onclick="phonetab(this)">公众人员</div>--%>
                        <%--<div class="phonefz" onclick="phonetab(this)">群组</div>--%>
                    </div>
                    <div style="float: left;height:320px;width:100%;overflow: auto;padding:10px;" class="treediv"
                         id="lxrdiv">
                        <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
                        <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
                        <ul id="messageTree" class="ztree"></ul>

                    </div>
                </div>
                <div style="float: left;height:100%;width: 100px;border-right: solid 1px rgb(244,244,244);">
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
                <div style="float: left;height:100%;width: 248px;">
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
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="sclxr()">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
                <%--<button type="button" class="btn btn-default" style="float: left;">通讯录--%>
                <%--</button>--%>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
</body>
</html>
