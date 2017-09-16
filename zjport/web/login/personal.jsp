<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/6/30
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.model.Portlet" pageEncoding="UTF-8" %>
<%@page import="com.zjport.model.TBacklog"%>
<%@ page import="com.zjport.util.CommonConst" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zjport.model.SsoSystem" %>
<%@ page import="com.zjport.model.TApplication" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>个人首页</title>

    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <%--<script src="../js/common/dashboard.js" type="text/javascript"></script>--%>
    <%--    <script type="text/javascript" src="../js/common/echarts-all.js"></script>--%>
    <script type="text/javascript" src="../js/common/echarts.js"></script>
    <script type="text/javascript" src="../js/login/personal.js"></script>
    <script type="text/javascript" src="../js/common/bootpaging.js"></script>
    <script src="../js/common/html5.js" type="text/javascript"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <script src="../js/common/dashboard.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../css/common/animate.css" type="text/css" />
    <style type="text/css">
        .table thead>tr>th {
            text-align:center !important;
        }
        .table tbody>tr>td {
            text-align:center !important;
        }
        .chart-legend li,.panel-body h4,.panel-body span {
            color:#333 !important;
        }
        .btn.btn-outline.blue {
            background: rgba(0, 0, 0, 0) none repeat scroll 0 0;
            border-color: #3598dc;
            color: #3598dc;
        }
        .btn.btn-outline.blue.active, .btn.btn-outline.blue:active, .btn.btn-outline.blue:active:focus, .btn.btn-outline.blue:active:hover, .btn.btn-outline.blue:focus, .btn.btn-outline.blue:hover {
            background-color: #3598dc;
            border-color: #3598dc;
            color: #fff;
        }
        .emphasis *{
            color:#777;
        }
        .emphasis h3{
            margin:0;
            padding:0;
        }
        .emphasis p{
            margin-bottom:10px;
        }
        .emphasis{
            border-top: 4px solid transparent;
            padding-top: 15px;
        }
        .emphasis:hover{
            border-top-color:green;
            color:#000;
        }
        .emphasis:hover *{
            color:#000;
        }
        .user-action{
            padding: 0px 15px 15px;
        }
        .border-right{
            border-right:1px solid #F4F4F4;
        }
    </style>
</head>
<%
    //List<TBacklog> backList = (List<TBacklog>)request.getAttribute("backlogList");
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //int count = ((Integer)request.getAttribute("count")).intValue();

    //List<SsoSystem> ssoSystemList = (List<SsoSystem>)request.getAttribute("ssoSystemList");

    List<Portlet> portletLeftList = (List<Portlet>)session.getAttribute("portletLeftList");
    List<Portlet> portletRightList = (List<Portlet>)session.getAttribute("portletRightList");

    List<TApplication> appList = (List<TApplication>)session.getAttribute("appList");
%>
<body onload="showtime()" class="skin-blue-light sidebar-mini" style="font-family: Helvetica,Tahoma,'Microsoft YaHei','微软雅黑',Arial,STXihei,'华文细黑',SimSun,'宋体',Heiti,'黑体',sans-serif; ">
<a href="../common/turn">测试</a>
<section class="content">
    <div class="row">
        <section class="col-lg-7 connectedSortable">

            <%if(portletLeftList.size()==0 && portletRightList.size()==0) {%>
            <div class="box box-solid bg-light-blue-gradient sortTable" id="function">
                <div class="box box-primary" style="margin-bottom:0px">
                    <div class="box-header with-border">
                        <i class="fa fa-cubes"></i>
                        <h3 class="box-title">常用功能</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('function')"><i class="fa fa-times"></i></button>
                        </div>
                    </div><!-- /.box-header -->
                    <div class="box-body no-padding">
                        <ul class="users-list clearfix" style="width: 100%;float: left">
                            <li>
                                <a class="users-list-name" href="http://10.100.70.60" target="_blank" title="内网门户">
                                    <img src="../image/logo/ic_web.png" alt="User Image" /><br>
                                    内网门户</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="javascript:void(0)" onclick="turnOa();" title="办公OA">
                                    <img src="../image/logo/ic_oa.png" alt="User Image" /><br>
                                    办公OA</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="javascript:void(0)" onclick="waitOpen('考勤管理');" title="考勤管理">
                                    <img src="../image/logo/ic_kaoqin.png" alt="User Image" /><br>
                                    考勤管理</a>
                            </li>
                        </ul><!-- /.users-list -->
                    </div><!-- /.box-body -->
                </div>
            </div>
            <div class="box box-solid sortTable" id="backlog">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <i class="fa fa-search"></i>
                        <h3 class="box-title">待办事项</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('backlog')"><i class="fa fa-times"></i></button>
                        </div>
                    </div><!-- /.box-header -->
                    <input type="hidden" id="currentPage" name="currentPage"/>
                    <div class="box-body" id="backlogTable">
                        <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:70px;margin-bottom:70px;display: none;color: rgb(215,215,215);">
                            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
                        </div>
                    </div><!-- /.box-body -->
                </div>
            </div>
            <%
                } else if(portletLeftList.size()!=0) {
                    for(Portlet port:portletLeftList) {
                        if("function".equals(port.getStModuleId())) {
            %>

            <div class="box box-solid bg-light-blue-gradient sortTable" id="function">
                <div class="box box-primary" style="margin-bottom:0px">
                    <div class="box-header with-border">
                        <i class="fa fa-cubes"></i>
                        <h3 class="box-title">常用功能</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('function')"><i class="fa fa-times"></i></button>
                        </div>
                    </div><!-- /.box-header -->
                    <div class="box-body no-padding">
                        <ul class="users-list clearfix" style="width: 100%;float: left">
                            <li>
                                <a class="users-list-name" href="http://10.100.70.60" target="_blank" title="内网门户">
                                    <img src="../image/logo/ic_web.png" alt="User Image" /><br>
                                    内网门户</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="javascript:void(0)" onclick="turnOa();" title="办公OA">
                                    <img src="../image/logo/ic_oa.png" alt="User Image" /><br>
                                    办公OA</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="javascript:void(0)" onclick="waitOpen('考勤管理');" title="考勤管理">
                                    <img src="../image/logo/ic_kaoqin.png" alt="User Image" /><br>
                                    考勤管理</a>
                            </li>
                        </ul><!-- /.users-list -->
                    </div><!-- /.box-body -->
                </div>
            </div>
            <%} else if("backlog".equals(port.getStModuleId())) {%>
            <div class="box box-solid sortTable" id="backlog">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <i class="fa fa-search"></i>
                        <h3 class="box-title">待办事项</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('backlog')"><i class="fa fa-times"></i></button>
                        </div>
                    </div><!-- /.box-header -->
                    <input type="hidden" id="currentPage" name="currentPage"/>
                    <div class="box-body" id="backlogTable">
                        <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:70px;margin-bottom:70px;display: none;color: rgb(215,215,215);">
                            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
                        </div>
                    </div><!-- /.box-body -->
                </div>
            </div>

            <%} else if("serviceSystem".equals(port.getStModuleId())) {%>

            <div class="box box-solid bg-light-blue-gradient sortTable" id="serviceSystem">
                <div class="box box-primary" style="margin-bottom:0px">
                    <div class="box-header with-border">
                        <i class="fa fa-cubes"></i>
                        <h3 class="box-title">业务系统</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('serviceSystem')"><i class="fa fa-times"></i></button>
                            <%--<button class="btn btn-box-tool" onclick="closeIt('supplier')"><i class="fa fa-times"></i></button>--%>
                        </div>
                    </div><!-- /.box-header -->
                    <div class="box-body no-padding">
                        <ul class="users-list clearfix">
                            <%--<li>
                                <img src="../image/logo/ic_zhihuijianguan.png" alt="User Image" />
                                <a class="users-list-name" href="javascript:void(0)" onclick="change();" title="智慧监管">智慧监管</a>
                            </li>--%>
                            <li>
                                <a class="users-list-name" href="http://172.26.24.33/manage/login.aspx" target="_blank" title="行政许可">
                                    <img src="../image/logo/ic_xinzhengxuke.png" alt="User Image" /><br>
                                    行政许可</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="http://172.26.24.209:9002/login.aspx" target="_blank" title="港政管理">
                                    <img src="../image/logo/ic_gangzhengguanli.png" alt="User Image" /><br>
                                    港政管理</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="http://172.26.24.209:8014" target="_blank" title="运政管理">
                                    <img src="../image/logo/ic_yunzhenguanli.png" alt="User Image" /><br>
                                    运政管理</a>
                            </li>
                            <% for(TApplication app : appList) {%>
                                <li>
                                    <a class="users-list-name" href="../common/turnTo?appId=<%=app.getStApplicationId()%>" target="_blank" title="<%=app.getStAppName()%>">
                                        <img src="<%=app.getStAppImgUrl()%>" alt="User Image" /><br>
                                        <%=app.getStAppName()%></a>
                                </li>
                            <%}%>
                        </ul><!-- /.users-list -->

                        <div style="float: left;width:100%;text-align: center;margin-top:70px;margin-bottom:70px;display: none;color: rgb(215,215,215);">
                            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何系统</span>
                        </div>
                    </div><!-- /.box-body -->
                </div>
            </div>
            <%} else {%>
            <div class="box box-solid bg-green-gradient sortTable" id="<%=port.getStModuleId()%>">
                <input type="hidden" id="<%=port.getStModuleId()%>C" value="<%=port.getStDisplayForm()%>"/>
                <div class="box box-<%=port.getStColor()%>">
                    <div class="box-header with-border">
                        <i class="fa <%=port.getStIcon()%>"></i>
                        <h3 class="box-title"><%=port.getStModuleName()%></h3>

                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('<%=port.getStModuleId()%>')"><i class="fa fa-times"></i></button>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active main" id="<%=port.getStModuleId()%>Chart" style="position: relative; height: 300px;"></div>
                            <div class="chart tab-pane" id="<%=port.getStModuleId()%>-chart" style="position: relative; height: 300px;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <%
                        }
                    }
                }
            %>

        </section><!-- /.Left col -->
        <!-- right col (We are only adding the ID to make the widgets sortable)-->
        <section class="col-lg-5 connectedSortable">

            <%if(portletRightList.size()==0 && portletRightList.size()==0) {%>
            <div class="box box-solid bg-light-blue-gradient sortTable" id="serviceSystem">
                <div class="box box-primary" style="margin-bottom:0px">
                    <div class="box-header with-border">
                        <i class="fa fa-cubes"></i>
                        <h3 class="box-title">业务系统</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('serviceSystem')"><i class="fa fa-times"></i></button>
                            <%--<button class="btn btn-box-tool" onclick="closeIt('supplier')"><i class="fa fa-times"></i></button>--%>
                        </div>
                    </div><!-- /.box-header -->
                    <div class="box-body no-padding">
                        <ul class="users-list clearfix">
                            <%--<li>
                                <img src="../image/logo/ic_zhihuijianguan.png" alt="User Image" />
                                <a class="users-list-name" href="javascript:void(0)" onclick="change();" title="智慧监管">智慧监管</a>
                            </li>--%>
                            <li>
                                <a class="users-list-name" href="http://172.26.24.33/manage/login.aspx" target="_blank" title="行政许可">
                                    <img src="../image/logo/ic_xinzhengxuke.png" alt="User Image" /><br>
                                    行政许可</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="http://172.26.24.209:9002/login.aspx" target="_blank" title="港政管理">
                                    <img src="../image/logo/ic_gangzhengguanli.png" alt="User Image" /><br>
                                    港政管理</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="http://172.26.24.209:8014" target="_blank" title="运政管理">
                                    <img src="../image/logo/ic_yunzhenguanli.png" alt="User Image" /><br>
                                    运政管理</a>
                            </li>
                            <% for(TApplication app : appList) {%>
                            <li>
                                <a class="users-list-name" href="../common/turnTo?appId=<%=app.getStApplicationId()%>" target="_blank" title="<%=app.getStAppName()%>">
                                    <img src="<%=app.getStAppImgUrl()%>" alt="User Image" /><br>
                                    <%=app.getStAppName()%></a>
                            </li>
                            <%}%>
                        </ul><!-- /.users-list -->
                    </div><!-- /.box-body -->
                </div>
            </div>
            <%
                } else if(portletRightList.size()!=0) {
                    for(Portlet port:portletRightList) {
                        if("function".equals(port.getStModuleId())) {
            %>
            <div class="box box-solid bg-light-blue-gradient sortTable" id="function">
                <div class="box box-primary" id="gys" style="margin-bottom:0px">
                    <div class="box-header with-border">
                        <i class="fa fa-cubes"></i>
                        <h3 class="box-title">常用功能</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('function')"><i class="fa fa-times"></i></button>
                        </div>
                    </div><!-- /.box-header -->
                    <div class="box-body no-padding">
                        <ul class="users-list clearfix" style="width: 100%;float: left">
                            <li>
                                <a class="users-list-name" href="http://10.100.70.60" target="_blank" title="内网门户">
                                    <img src="../image/logo/ic_web.png" alt="User Image" /><br>
                                    内网门户</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="javascript:void(0)" onclick="turnOa();" title="办公OA">
                                    <img src="../image/logo/ic_oa.png" alt="User Image" /><br>
                                    办公OA</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="javascript:void(0)" onclick="waitOpen('考勤管理');" title="考勤管理">
                                    <img src="../image/logo/ic_kaoqin.png" alt="User Image" /><br>
                                    考勤管理</a>
                            </li>
                        </ul><!-- /.users-list -->
                    </div><!-- /.box-body -->
                </div>
            </div>
            <%} else if("backlog".equals(port.getStModuleId())) {%>
            <div class="box box-solid sortTable" id="backlog">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <i class="fa fa-search"></i>
                        <h3 class="box-title">待办事项</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('backlog')"><i class="fa fa-times"></i></button>
                        </div>
                    </div><!-- /.box-header -->
                    <input type="hidden" id="currentPage" name="currentPage"/>
                    <div class="box-body" id="backlogTable">
                        <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:70px;margin-bottom:70px;display: none;color: rgb(215,215,215);">
                            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
                        </div>
                    </div><!-- /.box-body -->
                </div>
            </div>

            <%} else if("serviceSystem".equals(port.getStModuleId())) {%>

            <div class="box box-solid bg-light-blue-gradient sortTable" id="serviceSystem">
                <div class="box box-primary" style="margin-bottom:0px">
                    <div class="box-header with-border">
                        <i class="fa fa-cubes"></i>
                        <h3 class="box-title">业务系统</h3>
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('serviceSystem')"><i class="fa fa-times"></i></button>
                            <%--<button class="btn btn-box-tool" onclick="closeIt('supplier')"><i class="fa fa-times"></i></button>--%>
                        </div>
                    </div><!-- /.box-header -->
                    <div class="box-body no-padding">
                        <ul class="users-list clearfix">

                            <li>
                                <a class="users-list-name" href="http://172.26.24.33/manage/login.aspx" target="_blank" title="行政许可">
                                    <img src="../image/logo/ic_xinzhengxuke.png" alt="User Image" /><br>
                                    行政许可</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="http://172.26.24.209:9002/login.aspx" target="_blank" title="港政管理">
                                    <img src="../image/logo/ic_gangzhengguanli.png" alt="User Image" /><br>
                                    港政管理</a>
                            </li>
                            <li>
                                <a class="users-list-name" href="http://172.26.24.209:8014" target="_blank" title="运政管理">
                                    <img src="../image/logo/ic_yunzhenguanli.png" alt="User Image" /><br>
                                    运政管理</a>
                            </li>
                            <% for(TApplication app : appList) {%>
                            <li>
                                <a class="users-list-name" href="../common/turnTo?appId=<%=app.getStApplicationId()%>" target="_blank" title="<%=app.getStAppName()%>">
                                    <img src="<%=app.getStAppImgUrl()%>" alt="User Image" /><br>
                                    <%=app.getStAppName()%></a>
                            </li>
                            <%}%>
                        </ul><!-- /.users-list -->

                        <%--<div style="float: left;width:100%;text-align: center;margin-top:70px;margin-bottom:70px;color: rgb(215,215,215);">
                            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何系统</span>
                        </div>--%>

                    </div><!-- /.box-body -->
                </div>
            </div>
            <%} else {%>
            <div class="box box-solid bg-green-gradient sortTable" id="<%=port.getStModuleId()%>">
                <input type="hidden" id="<%=port.getStModuleId()%>C" value="<%=port.getStDisplayForm()%>"/>
                <div class="box box-<%=port.getStColor()%>">
                    <div class="box-header with-border">
                        <i class="fa <%=port.getStIcon()%>"></i>
                        <h3 class="box-title"><%=port.getStModuleName()%></h3>

                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            <button class="btn btn-box-tool" onclick="closeIt('<%=port.getStModuleId()%>')"><i class="fa fa-times"></i></button>
                        </div>
                    </div>
                    <div class="box-body">
                        <div class="tab-content no-padding">
                            <!-- Morris chart - Sales -->
                            <div class="chart tab-pane active main" id="<%=port.getStModuleId()%>Chart" style="position: relative; height: 300px;"></div>
                            <div class="chart tab-pane" id="<%=port.getStModuleId()%>-chart" style="position: relative; height: 300px;"></div>
                        </div>
                    </div>
                </div>
            </div>
            <%
                        }
                    }
                }
            %>
        </section><!-- right col -->
    </div><!-- /.row (main row) -->
</section>

<script type="text/javascript">
    $(document).ready(function() {
        $("#workspace").addClass("active");
        showBacklogTable('../backlog/showBacklog', 1);

        $('.sortTable').each(function(){
            console.log($(this).children('input').val());
            //console.log($(this).attr('id'));
            if("function"!=$(this).attr('id') && "serviceSystem"!=$(this).attr('id') && "backlog"!=$(this).attr('id')) {
                createChart($(this).attr('id'),$(this).children('input').val());
            }
        })
    })
    function waitOpen(name) {
        alert(name+" 正在接入中！");
    }

    function turnLeft() {
        if(!$("#currentPage").val() == "" && $("#currentPage").val() != null) {
            var currentPage = $("#currentPage").val();
            $.ajax({
                url:"backlogShow",
                type:'post',
                dataType:'json',
                data:{
                    'page':currentPage,
                    'rows':1
                },
                /*beforeSend : function() {
                 ShowLoadingDiv();// 获取数据之前显示loading图标。
                 },*/
                success:function(data){
                    $(".addTr").empty();
                    $(".addTr").remove();
                    if(data.resultcode==-1){
                        BackToLoginPage();
                    }else if(data.resultcode==0){
                        var list=data.records.data;
                        $("#pagedetal").empty();
                        $("#pagedetal").text(
                                "当前页"+list.length+"条记录 共"+data.records.total+"条记录，每页"+data.records.rows+"条"
                        );
                        pagingmake(actionName,'showInfoInTable',selectedPage,data.records.pages);
                        if(list==""){
                            TableIsNull();
                        }else{
                            appendToTable(list);
                        }
                    }else{
                        alert(data.result.resultdesc);
                    }

                }/*,
                 complete : function() {
                 CloseLoadingDiv();
                 }*/
            });
        }
    }

    function tuenRight() {

    }

    function turnOa(){
        try{
            var objShell = new ActiveXObject("wscript.shell");
            var path = objShell.RegRead("HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Lotus\\Notes\\Path");
            var name = objShell.RegRead("HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Lotus\\Notes\\Name");
            objShell.exec(path + name);
        }catch(e){
            console.error(e);
            alert(e)
        }
    }

    function deal(type,id) {
        if("<%=CommonConst.BACKLOG_INFORM_APPROVAL%>" == type) {
            window.location.href = "../information/approvalDetail?id="+id+"&state=<%=CommonConst.InfoState_Wait_Approval%>";
        } else {
            window.location.href = "../officeAssistant/calendarView?id="+id+"";
        }

    }

    var timerID = null;
    var timerRunning = false;

    function showtime() {
        var now = new Date();

        document.clock.thetime.value = now.toString();
        timerID = setTimeout("showtime()", 1000);
        timerRunning = true;
    }

    function createChart(id,form){
        var Echarts = echarts.init(document.getElementById(id+'Chart'),'macarons');
        Echarts.clear();
        Echarts.showLoading({text: '正在努力的读取数据中...'});
        $.ajax({
            url:"../system/getStaticOption",
            type:'post',
            dataType:'json',
            data : {
                'moduleId':id,
                'form':form
            },
            success:function(data){
                //console.log(data.option);
                eval("var option = "+data.option);
                Echarts.setOption(option);
                Echarts.hideLoading();
            }/*,
             complete : function() {
             CloseLoadingDiv();
             }*/
        });
    }

    $("#outIn-chart").resize(function() {
        echartsShow('outInChart');
    });

    function closeIt(id) {
        $("#"+id).hide();
        $("#"+id+"_check").attr("checked",false);
    }

    function main_to_index(obj) {
        var checkId = obj.id;
        var check = "_check";
        var function_menu = checkId.replace(check,"");
        if(obj.checked) {
            $("#"+function_menu).show();
        } else {
            $("#"+function_menu).hide();
        }
    }

</script>
</body>
</html>
