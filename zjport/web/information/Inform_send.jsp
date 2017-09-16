<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>我发布的信息</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>


    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/bootpaging.js"></script>
    <!-- 页面js -->
    <script src="../js/information/Inform_send.js" type="text/javascript"></script>
</head>
<%
    String type = (String)request.getAttribute("type");
%>
<body >
    <input type="hidden" id="type" value="<%=type%>"/>
    <section class="content-header">
        <h1>
            我发布的信息
            <!-- <small>advanced tables</small> -->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-file-text"></i> 信息发布</a></li>
            <li><a href="#">我发布的信息</a></li>
        </ol>
    </section>
    <%--<button type="button" onclick="test()">测试短信机</button>--%>
    <section class="content">
        <div class="box">
        <ul class="nav nav-tabs ul-edit responsive">
            <%--<li <%if(null==type || CommonConst.Information_Internet.equals(type)) {%>class="active" <%}%>><a href="#tab-internet" data-toggle="tab" onclick="showInfoInTable('sendlist',1,1)"><i
                    class="fa fa-th-large"></i>&nbsp;网站</a>
            </li>
            <li <%if(CommonConst.Information_Board.equals(type)) {%>class="active" <%}%>><a href="#tab-board" data-toggle="tab" onclick="showInfoInTable('sendlist',1,2)"><i
                    class="fa fa-clipboard"></i>&nbsp;情报板</a>
            </li>
            <li <%if(CommonConst.Information_Message.equals(type)) {%>class="active" <%}%>><a href="#tab-message" data-toggle="tab" onclick="showInfoInTable('sendlist',1,3)"><i
                    class="fa fa-comment"></i>&nbsp;短信</a>
            </li>--%>
            <li id="internet" class="active"><a href="#tab-internet" data-toggle="tab" onclick="showInform(1)"><i
                    class="fa fa-th-large"></i>&nbsp;网站</a>
            </li>
            <li id="board"><a href="#tab-board" data-toggle="tab" onclick="showInform(2)"><i
                    class="fa fa-clipboard"></i>&nbsp;情报板</a>
            </li>
            <li id="message"><a href="#tab-message" data-toggle="tab" onclick="showInform(3)"><i
                    class="fa fa-comment"></i>&nbsp;短信</a>
            </li>
        </ul>
        <%--<div class="dataTables_length" id="example1_length"><label>Show
            <select name="example1_length" aria-controls="example1" class="form-control input-sm">
                <option value="10">10</option>
                <option value="25">25</option>
                <option value="50">50</option>
                <option value="100">100</option>
            </select> entries</label>
        </div>--%>
        <div class="row">
            <div class="col-sm-6">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 5px;margin-left: 5px">
                    <%--<button type="button" class="btn btn-default">删除</button>--%>
                    <%--<input type="search" class="form-control" placeholder="查询">--%>
                        <div class="dropdown" style="float: left;" id="stateSelect">
                            <button type="button" class="btn btn-default dropdown-toggle" id="ztbutton" data-toggle="dropdown">状态
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu " role="menu" aria-labelledby="ztbutton" style="left: 0;width: 140px;">
                                <li role="presentation" onclick="showStateInform('')">
                                    <a role="menuitem" tabindex="-1" href="#">全  部</a>
                                </li>
                                <li role="presentation" onclick="showStateInform(<%=CommonConst.InfoState_Send%>)">
                                    <a role="menuitem" tabindex="-1" href="#">已发布</a>
                                </li>
                                <li role="presentation" onclick="showStateInform(<%=CommonConst.InfoState_Wait_Approval%>)">
                                    <a role="menuitem" tabindex="-1" href="#">待审批</a>
                                </li>
                                <li role="presentation" onclick="showStateInform(<%=CommonConst.InfoState_Approvaling%>)">
                                    <a role="menuitem" tabindex="-1" href="#">审批中</a>
                                </li>
                                <li role="presentation" onclick="showStateInform(<%=CommonConst.InfoState_Rejected%>)">
                                    <a role="menuitem" tabindex="-1" href="#">已驳回</a>
                                </li>
                                <li role="presentation" onclick="showStateInform(<%=CommonConst.InfoState_Recall%>)">
                                    <a role="menuitem" tabindex="-1" href="#">已撤回</a>
                                </li>
                            </ul>
                            <input type="hidden" id="state" name="state"/>
                        </div>
                        <div style="heigth:100%;width:400px;float: left;border-radius:4px 0 0 4px;border:solid 1px #cccccc;margin-left: 20px;">
                            <input type="text" class="form-control" style="height:100%;padding:5px;width:330px;border: 0;float: left;"placeholder="请输入标题或内容" id="nbuserselectinput"/>
                            <div style="float: left;height:30px;width:28px;">
                                <i class="fa fa-close" style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;" id="clearbtn"></i>
                            </div>
                            <i class="fa fa-search" style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;" onclick="searchInform()"></i>
                        </div>
                        <!-- 暂时注释高级查询 -->
                        <%--<i class="fa fa-angle-down" style="float: left;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border:solid 1px #ccc;border-left:0;background-color: rgb(250,250,250)"></i>--%>
                </div>
            </div>

            <div class="col-sm-6">
                <div class="input-group-btn" style="text-align:right">
                    <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle" type="button" aria-expanded="false">信息发布 <span class="fa fa-caret-down"></span></button>
                    <ul class="dropdown-menu">
                        <li><a href="sendAdd?type=1" >网站</a></li>
                        <li><a href="sendAdd?type=3" >短信</a></li>
                        <li><a href="sendAdd?type=2" >情报板</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="tab-content no-margin" style="margin-top:10px;margin-left:8px;height:600px;">
            <!-- 网站信息 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-bordered table-hover col-xs-12" id="internet-info" style="border-top:none">
                    <thead>
                    <tr>
                        <%--<th width="8%">选择</th>--%>
                        <th width="8%" class="center">序号</th>
                        <th width="35%">标题</th>
                        <th width="15%">创建时间</th>
                        <th width="10%" class="center">状态</th>
                        <th width="10%" class="center">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <!-- 情板报 -->
            <div id="tab-board" class="tab-pane fade" style="position: relative">
                <table class="table table-bordered table-hover col-xs-12" id="board-info" style="border-top:none">
                    <thead>
                        <tr>
                            <%--<th width="8%">选择</th>--%>
                            <th width="8%" class="center">序号</th>
                            <th width="40%">情报板内容</th>
                            <th width="15%">创建时间</th>
                            <th width="10%" class="center">状态</th>
                            <th width="10%" class="center">操作</th>
                        </tr>
                    </thead>
                    <%--<tfoot>
                    <tr>
                        <th>Rendering engine</th>
                        <th>Browser</th>
                        <th>Platform(s)</th>
                        <th>Engine version</th>
                        <th>CSS grade</th>
                    </tr>
                    </tfoot>--%>
                </table>
            </div>
            <!-- 短信 -->
            <div id="tab-message" class="tab-pane fade" style="position: relative">
                <table class="table table-bordered table-hover col-xs-12" id="message-info" style="border-top:none">
                    <thead>
                    <tr>
                        <%--<th width="8%">选择</th>--%>
                        <th width="8%" class="center">序号</th>
                        <th width="40%">短信内容</th>
                        <th width="12%">创建时间</th>
                        <th width="12%" class="center">成功/总数</th>
                        <th width="10%" class="center">操作</th>
                    </tr>
                    </thead>
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
            <%--<div class="row">--%>
                <%--<button class="btn btn-info btn-sm pull-right"--%>
                        <%--style="margin-right: 15px;width:80px;"--%>
                        <%--onClick="javascript :history.back(-1);">--%>
                    <%--<i class="fa fa-reply "></i>&nbsp;&nbsp;&nbsp;返回--%>
                <%--</button>--%>
            <%--</div>--%>
        </div>
        </div>
    </section>
    <input type="hidden" id="infoId"/>
<!-- 模态框（Modal）删除提示 -->
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
                <button type="button" class="btn btn-primary" onclick="deleteIt()">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div><!-- /.modal -->

<!-- 模态框（Modal）撤回提示 -->
<div class="modal fade" id="recallModal" tabindex="-1" role="dialog"
     aria-labelledby="recallModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top:300px;">
        <div class="modal-content" style="width: 400px;">
            <div class="modal-header">
                <h4 class="modal-title" id="recallModalLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body" style="line-height: 20px;">
                <div style="background-color: rgb(255,168,0);height:20px;width:20px;border-radius:10px;text-align: center; color:white;float:left;">!</div>
                &nbsp;您确定要撤回吗？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="recall()">
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
