<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/2
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ page import="com.zjport.model.TInformation" %>
<%@ page import="com.zjport.util.CommonConst" %>
<%@ page import="com.zjport.model.TUser" %>
<%@ page import="com.zjport.model.TAttachment" %>
<%@ page import="com.zjport.model.TCalendar" %>
<!DOCTYPE html>
<html>
<head>
    <title>日程详情</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>


    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- CK Editor -->
    <script src="../js/ckeditor/ckeditor.js"></script>
    <!-- 页面js -->
    <script src="../js/officeAssistant/calendar_view.js" type="text/javascript"></script>
    <style>
        .table td{
            border:0!important;
        }
    </style>
</head>
<%
    TCalendar calendar = (TCalendar)request.getAttribute("calendar");
    List<TAttachment> attachmentList = (List<TAttachment>)request.getAttribute("attachmentList");
    String userNames = (String)request.getAttribute("userNames");
    String createUser = (String)request.getAttribute("createUser");

    String type = "";
    if(CommonConst.CALENDAR_COMMON.equals(calendar.getStType())) {
        type = "日常事务";
    } else if(CommonConst.CALENDAR_MEETING.equals(calendar.getStType())) {
        type = "会议日程";
    } else if(CommonConst.CALENDAR_WORK.equals(calendar.getStType())) {
        type = "工作日程";
    } else {
        type = "其他日程";
    }

    String alert = "";
    if(calendar.getStIsAlert()) {
        alert = "提醒";
    } else {
        alert = "不提醒";
    }

    String urgent = "";
    if("1".equals(calendar.getStUrgentState())) {
        urgent = "一般";
    } else if("2".equals(calendar.getStUrgentState())) {
        urgent = "重要";
    } else {
        urgent = "紧急";
    }

%>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath" />
<section class="content-header" style="background-color: white;padding: 15px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_details.png">
        日程详情
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top:0; margin-top: 5px;" >
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;float:left;box-shadow: 0 0 0 white;">
        <div class="box-body" style="float: left;width: 100%;">
            <div style="float: left;width: 100%;">
                <div class="pull-left" >
                    <button class="btn btn-primary" onclick=window.location.href='calendarEdit?id=<%=calendar.getStCalendarId()%>'>编辑</button>
                    <button class="btn btn-default" style="margin-left: 10px;"data-toggle="modal"
                            data-target="#delModal">删除</button>
                </div>
            </div>
            <div style="float: left;margin-top:10px;background-color: white;width:100%;padding: 10px 0px;height:570px;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                <div style="float: left;width:100%;height:50px;border-bottom: solid 1px rgb(239,239,239);">
                    <b style="margin-left:10px;float: left;font-size: 18px;color: #333;line-height: 50px;"><%=calendar.getStCalendarTitle()%></b>
                </div>
                <div style="float: left;width:100%;padding:5px 10px;border-bottom: solid 1px rgb(239,239,239);">
                    <div style="float: left;width:50%;border-right:solid 1px rgb(239,239,239)">
                        <table class="table" style="margin-bottom: 0;border:0;">
                            <tr>
                                <td class="col-xs-3" style="line-height: 26px;">创建人：</td>
                                <td class="col-xs-9"><div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=createUser%></div></td>
                            </tr>
                            <tr>
                                <td style="line-height: 26px;">日程类型：</td>
                                <td ><div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding :0 10px;"><%=type%></div></td>
                            </tr>
                            <tr>
                                <td style="line-height: 26px;">时间：</td>
                                <td ><div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=calendar.getDtStart()%>~<%=calendar.getDtEnd()%></div></td>
                            </tr>
                            <tr>
                                <td  style="line-height: 26px;">参与人员：</td>
                                <td ><div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=userNames%></div></td>
                            </tr>
                        </table>
                    </div>
                    <div style="float: left;width:49%;height:100%;padding:0 10px;">
                        <table class="table" style="margin-bottom: 0;border:0;">
                            <%--<tr>
                                <td class="col-xs-3" style="line-height: 26px;">修改时间：</td>
                                <td class="col-xs-9"><div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;">111</div></td>
                            </tr>--%>
                            <tr>
                                <td class="col-xs-3"  style="line-height: 26px;">紧急程度：</td>
                                <td class="col-xs-9"><div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=urgent%></div></td>
                            </tr>
                            <tr>
                                <td  style="line-height: 26px;">提醒：</td>
                                <td ><div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=alert%></div></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div style="float: left;width:100%;height:200px;padding:5px 10px;border-bottom: solid 1px rgb(239,239,239);overflow-y: auto;word-break: break-all;">
                <%=calendar.getStContent()%>
                </div>
                <%--<%--%>
                <%--if(attachmentList != null) {--%>
                    <%--for(int i = 0; i<attachmentList.size(); i++) {--%>
                        <%--TAttachment attachment = attachmentList.get(i);--%>
                <%--%>--%>
                    <%--<div style="float: left;margin-left:10px;margin-top:10px;height:40px;padding:10px 10px;background-color: rgb(250,250,250);border:solid 1px rgb(239,239,239);border-radius:4px;">--%>
                        <%--<span style="float: left;line-height: 20px;color:#666;"><%=attachment.getStFileName()%></span>--%>
                        <%--<div style="float: left;width:40px;height:20px;margin-left:10px;line-height: 20px;text-align: center;background-color: rgb(1,134,237);color: #ffffff;cursor:pointer;">--%>
                            <%--<a href="downloadFile?path=<%=attachment.getStFileSource()%>&name=<%=attachment.getStFileName()%>" style="color: #fff" >下载</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--<%--%>
                    <%--}--%>
                <%--}--%>
                <%--%>--%>
                <%
                    if(attachmentList != null) {
                        for(int i = 0; i<attachmentList.size(); i++) {
                            TAttachment attachment = attachmentList.get(i);
                %>
                <div style="float: left;margin-left:10px;margin-top:10px;height:40px;padding:10px 10px;background-color: rgb(250,250,250);border:solid 1px rgb(239,239,239);border-radius:4px;">
                    <span style="float: left;line-height: 20px;color:#666;"><%=attachment.getStFileName()%></span>
                    <div style="float: left;width:40px;height:20px;margin-left:10px;line-height: 20px;text-align: center;background-color: rgb(1,134,237);color: #ffffff;cursor:pointer;">
                        <a href="../common/downloadFile?id=<%=attachment.getStAttachmentId()%>" style="color: #fff" >下载</a>
                    </div>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </div><!-- /.box-body -->
    </div><!-- /. box -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top:300px;">
        <div class="modal-content" style="width: 400px;">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body" style="line-height: 20px;">
               <div style="background-color: rgb(255,168,0);height:20px;width:20px;border-radius:10px;text-align: center; color:white;float:left;">!</div>
                &nbsp;您确定要删除么？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="deleteIt(<%=calendar.getStCalendarId()%>)">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
    </div><!-- /.modal -->
</body>
</html>
