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

    int successNum = 0;
    int sum = 0;
    if(!"".equals(info.getStResult()) && info.getStResult() != null) {
        String[] num = info.getStResult().split("/");
        successNum = Integer.valueOf(num[0]);
        sum = Integer.valueOf(num[1]);
    }

%>
<body>
<section class="content-header" style="background-color: white;padding: 15px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_details.png">
        详情-短信
        <!-- <small>advanced tables</small> -->
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top:0; margin-top: 5px;" >
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;float:left;box-shadow: 0 0 0 white;">
        <div class="box-body" style="float: left;width: 100%;">
            <%--<div style="float: left;width: 100%;margin-top:20px;">
                <div class="pull-left" >
                    <button class="btn btn-default" data-toggle="modal"
                            data-target="#myModal">删除</button>
                </div>
            </div>--%>
            <div style="float: left;margin-top:20px;background-color: white;width:100%;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                <div style="float: left;width:100%;padding-left: 10px;">
                    <span style="float: left;color:#666;font-size: 15px;line-height:45px;">发布对象：</span>
                    <div style="margin-left:80px;height:30px;">
                        <div style="float: left;
                                    height:auto;
                                    max-height: 150px;
                                    overflow-x: auto;
                                    background-color: rgb(241,242,246);
                                    color:#333;
                                    line-height: 25px;
                                    margin-top:10px;
                                    text-align: center;
                                    border:solid 1px #ccc;
                                    border-radius:4px;
                                    padding:0 10px;
                                    width:auto;
                                    max-width: 100%;
                                    word-break: break-all;">
                            <%=info.getStInformObject()%>
                        </div>
                    </div>
                </div>
                <div style="float: left;width:100%;padding:10px;padding-bottom:40px;word-break: break-all;word-wrap: break-word;line-height: 20px;text-align: left;color: #666;">
                    <%=info.getStInformContent()%>
                </div>
            </div>

            <div style="float: left;margin-top:20px;background-color: white;width:100%;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                <div style="float: left;width:100%;padding-left: 10px;">
                    <span style="float: left;color:#666;font-size: 15px;line-height:45px;">发布结果：</span>
                    <div style="float: left;">
                        <div style="float: left;height:25px;line-height: 25px;margin-top:10px;margin-left:15px;text-align: center;padding:0 10px;">
                            本次共发布 <%=sum%> 条短信，其中 <%=successNum%> 条发送成功，<span style="color: #f00">失败 <%=sum-successNum%> 条<%if(sum != successNum) {%>，失败号码为：<%=info.getStFailnum()%><%}%></span>
                        </div>
                    </div>
                </div>

            </div>
            <%--<div style="float: left;margin-top:20px;width:100%;">
                <div style="float: left;width:100%;background-color: white;box-shadow: 0 0 3px #ccc;">
                    <div style="float: left;width:100%;height:30px;padding:5px 10px;color:#666;font-size:14px;line-height: 20px;text-align: left;border-bottom: solid 1px rgb(239,239,239);">
                        12message,10success,<span style="color: red">2 false</span></div>
                    <table class="table table-striped" style="float: left;">
                        <tr>
                            <th><input type="checkbox" class="checkbox" onclick="checkedOrNo(this)"/></th>
                            <th>序号</th>
                            <th>姓名/手机</th>
                            <th>发送时间</th>
                            <th>失败原因</th>
                        </tr>
                        <tr>
                        <td><input type="checkbox" class="checkbox systemcheck"/></td>
                        <td>1</td>
                        <td>123213/1321321</td>
                        <td>1321321312</td>
                        <td>31221321</td>
                        </tr>
                        <tr>
                        <td><input type="checkbox" class="checkbox systemcheck"/></td>
                        <td>1</td>
                        <td>123213/1321321</td>
                        <td>1321321312</td>
                        <td>31221321</td>
                        </tr>
                    </table>
                    <span style="float: left;line-height: 30px;margin-left:10px;">当前页2条记录，共2条记录 每页10条</span>
                    <div class='bootpagediv' style='width:100%;margin-right:20px;'>
                        <nav style='float:right;display:none' id='pageshow'>
                            <ul class="pagination" id='pagination'>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>--%>
        </div><!-- /.box-body -->
    </div><!-- /. box -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
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
                <button type="button" class="btn btn-primary">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
