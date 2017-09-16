<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>意见箱</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="../js/envelope/message_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword{
            color:rgb(0,104,177);
            cursor: pointer;
        }
    </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
    <section class="content-header">
        <h1>
            意见箱
            <!-- <small>advanced tables</small> -->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-envelope"></i>意见箱</a></li>
        </ol>
    </section>

    <section class="content">
        <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                    <div style="heigth:100%;width:400px;float: left;border-radius:4px;border:solid 1px #cccccc;margin-left: 10px;">
                        <input type="text" class="form-control" style="height:100%;padding:5px;width:330px;border: 0;float: left;"placeholder="请输入反馈人姓名或手机号" id="listkey"/>
                        <div style="float: left;height:30px;width:28px;">
                            <i class="fa fa-close" style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;" id="clearbtn"></i>
                        </div>
                        <i class="fa fa-search" style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;" onclick="showTabledata('AdviceByTip',1)"></i>
                    </div>
                    <script>
                        $(document).ready(function(){
                            $("#clearbtn").bind("click",function(){
                                $("#clearbtn").hide();
                                $("#listkey").val('');
                            })
                            $("#listkey").bind('input propertychange', function() {
                                if($('#listkey').val()!=''&&$('#listkey').val()!=null){
                                    $("#clearbtn").show();
                                }
                            });
                        })
                    </script>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content" style="margin-top:20px;min-height:600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-hover col-xs-12" id="rolelist-info" style="border-top:none">
                </table>
            </div>

            <div class='bootpagediv' style='width:100%;margin-right:20px;'>
                <span style="float: left;margin-left:10px;line-height: 20px;color:#666;" id="pagedetal">
                </span>
                <nav style='float:right;display:none' id='pageshow'>
                    <ul class="pagination" id='pagination'>
                    </ul>
                </nav>
            </div>
            <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
            </div>
        </div>
        </div>
    </section>
    <input type="hidden" id="infoId"/>
</body>
</html>
