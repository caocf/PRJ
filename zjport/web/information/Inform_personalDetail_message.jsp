<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/2
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.model.TInformation" %>
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
    <script src="../js/information/Inform_sendDetail_message.js" type="text/javascript"></script>

</head>

<%
    TInformation info = (TInformation)request.getAttribute("info");

%>
<body>
<section class="content-header" style="background-color: white;padding: 15px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_details.png">
        详情-短信
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top:0; margin-top: 5px;" >
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;float:left;box-shadow: 0 0 0 white;">
        <div class="box-body" style="float: left;width: 100%;">
            <div style="float: left;margin-top:20px;background-color: white;width:100%;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                <div style="float: left;width:100%;padding-left: 10px;">
                    <span style="float: left;color:#666;font-size: 15px;line-height:45px;">发布对象：</span>
                    <div style="float: left;">
                        <div style="float: left;height:25px;background-color: rgb(241,242,246);color:#333;line-height: 25px;margin-top:10px;margin-left:15px;text-align: center;border:solid 1px #ccc;border-radius:4px;padding:0 10px;"><%=info.getStInformObject()%></div>
                    </div>
                </div>
                <div style="float: left;width:100%;padding:10px;padding-bottom:40px;word-break: break-all;word-wrap: break-word;line-height: 20px;text-align: left;color: #666;">
                    <%=info.getStInformContent()%>
                </div>
            </div>

        </div><!-- /.box-body -->
    </div><!-- /. box -->
</section>
</body>
</html>
