<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/2
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.model.TInformation" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.model.TUser" %>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息发布</title>

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
    <script src="../js/information/Inform_personalDetail_board.js" type="text/javascript"></script>

</head>

<%
    HttpSession sessionUser = request.getSession();
    TUser user = (TUser)sessionUser.getAttribute("session_user");

    String approvalUser = (String)request.getAttribute("approvalUser");
    String sendUser = (String)request.getAttribute("sendUser");
    TInformation info = (TInformation)request.getAttribute("info");
    String state = "";
    String color = "";
    if(info != null) {
        color = info.getStInformColor();
        state = info.getStState();
    }
%>
<body>
<section class="content-header" style="background-color: white;padding: 15px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_details.png">
        详情-情报板
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top:0; margin-top: 5px;" >
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;float:left;box-shadow: 0 0 0 white;">
        <div class="box-body" style="float: left;width: 100%;">
            <div style="float: left;margin-top:20px;background-color: white;width:100%;height:370px;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                <div style="float: left;width:100%;padding-left: 10px;">
                    <span style="float: left;color:#666;font-size: 15px;line-height:45px;">发布对象：</span>
                    <div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;margin-top:10px;margin-left:15px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=info.getStInformObject()%></div>
                    <%
                        if(CommonConst.InfoState_Wait_Approval.equals(state)) {
                    %>
                    <img style="margin-top:10px;margin-right:15px;float: right;" src="../image/information/information_state_1.png">
                    <%
                    } else if(CommonConst.InfoState_Approvaling.equals(state)) {
                    %>
                    <img style="margin-top:10px;margin-right:15px;float: right;" src="../image/information/information_state_2.png">
                    <%
                    } else if(CommonConst.InfoState_Rejected.equals(state)) {
                    %>
                    <img style="margin-top:10px;margin-right:15px;float: right;" src="../image/information/information_state_3.png">
                    <%
                    } else if(CommonConst.InfoState_Send.equals(state)) {
                    %>
                    <img style="margin-top:10px;margin-right:15px;float: right;" src="../image/information/information_state_4.png">
                    <%
                        }
                    %>
                    <%--<div style="float: right;height:25px;background-color: rgb(243,178,0);color:white;line-height: 25px;margin-top:10px;margin-right:15px;text-align: center;padding:0 10px;">待审批</div>--%>
                </div>
                <div style="float: left;width:100%;padding-left: 10px;border-bottom: solid 1px rgb(239,239,239);">
                    <span style="float: left;color:#666;font-size: 15px;line-height:45px;">文字颜色：</span>
                    <%
                        if(CommonConst.BOARD_COLOR_GREEN.equals(color)) {
                    %>
                    <div style="float: left;height:25px;width:25px;background-color: #0f0;margin-top:10px;margin-left:15px;"></div>
                    <%
                    } else if(CommonConst.BOARD_COLOR_YELLOW.equals(color)) {
                    %>
                    <div style="float: left;height:25px;width:25px;background-color: #ff0;margin-top:10px;margin-left:15px;"></div>
                    <%
                    } else if(CommonConst.BOARD_COLOR_RED.equals(color)) {
                    %>
                    <div style="float: left;height:25px;width:25px;background-color: #f00;margin-top:10px;margin-left:15px;"></div>
                    <%
                        }
                    %>
                </div>
                <div style="float: left;width:100%;padding:10px;word-break: break-all;word-wrap: break-word;line-height: 20px;text-align: left;color: #666;">
                    <%=info.getStInformContent()%>
                </div>
            </div>
            <div style="float: left;margin-top:20px;width:100%;">
                <div style="float: left;width:100%;background-color: white;box-shadow: 0 0 3px #ccc;">
                    <div style="float: left;width:100%;height:30px;padding:5px 10px;color:#666;font-size:14px;line-height: 20px;text-align: left;border-bottom: solid 1px rgb(239,239,239);">信息动态</div>
                    <%
                        if(CommonConst.InfoState_Send.equals(state)) {
                    %>
                    <div style="float: left;width:100%;height:60px;padding:5px 10px;border-bottom: solid 1px rgb(239,239,239);text-align: left;line-height: 25px;">
                        <span style="color: #0073b7;font-size: 16px;"><i class="fa fa-user" aria-hidden="true"></i>&nbsp;<%=approvalUser%>&nbsp;<span style="color: #c6c6c6;font-size: 14px;"><%=info.getDtCreate()%></span></span><br>
                        <span style="color: #c6c6c6;font-size: 14px;"><%--<span style="color:  rgb(242,90,89);font-size: 16px;">【已驳回】</span>--%><span style="color:#333;font-size: 16px;">【已审核】</span><%=info.getStBackContent()%></span><br>
                        <%--<span style="color: #0073b7;font-size: 16px;"><i class="fa fa-user" aria-hidden="true"></i>&nbsp;陈琳&nbsp;<span style="color: #c6c6c6;font-size: 14px;">2016-9-1</span></span><br>
                        <span style="color: #c6c6c6;font-size: 14px;"><span style="color:  rgb(242,90,89);font-size: 16px;">【已驳回】</span>记录卡苏打撒旦飞机飞洒大量进口大幅萨利赫客观地说</span><br>--%>
                    </div>
                    <%
                    } else if(CommonConst.InfoState_Rejected.equals(state)) {
                    %>
                    <div style="float: left;width:100%;height:60px;padding:5px 10px;border-bottom: solid 1px rgb(239,239,239);text-align: left;line-height: 25px;">
                        <span style="color: #0073b7;font-size: 16px;"><i class="fa fa-user" aria-hidden="true"></i>&nbsp;<%=sendUser%>&nbsp;<span style="color: #c6c6c6;font-size: 14px;"><%=info.getDtCreate()%></span></span><br>
                        <span style="color: #c6c6c6;font-size: 14px;"><span style="color:  rgb(242,90,89);font-size: 16px;">【已驳回】</span><%=info.getStBackContent()%></span><br>
                        <%--<span style="color: #0073b7;font-size: 16px;"><i class="fa fa-user" aria-hidden="true"></i>&nbsp;陈琳&nbsp;<span style="color: #c6c6c6;font-size: 14px;">2016-9-1</span></span><br>
                        <span style="color: #c6c6c6;font-size: 14px;"><span style="color:  rgb(242,90,89);font-size: 16px;">【已驳回】</span>记录卡苏打撒旦飞机飞洒大量进口大幅萨利赫客观地说</span><br>--%>
                    </div>
                    <%
                        }
                    %>
                    <div style="float: left;width:100%;height:60px;padding:5px 10px;border-bottom: solid 1px rgb(239,239,239);text-align: left;line-height: 25px;">
                        <span style="color: #0073b7;font-size: 16px;"><i class="fa fa-user" aria-hidden="true"></i>&nbsp;<%=user.getStUserName()%>&nbsp;<span style="color: #c6c6c6;font-size: 14px;"><%=info.getDtCreate()%></span></span><br>
                        <span style="color: #c6c6c6;font-size: 14px;"><%--<span style="color:  rgb(242,90,89);font-size: 16px;">【已驳回】</span>--%><span style="color:#333;font-size: 16px;">【提交审核】</span></span><br>
                    </div>
                </div>
            </div>
        </div><!-- /.box-body -->
    </div><!-- /. box -->
</section>

</body>
</html>
