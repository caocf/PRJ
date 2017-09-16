<%@ page import="com.zjport.model.TUser" %>
<%@ page import="com.zjport.model.TDepartment" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zjport.model.Portlet" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<!DOCTYPE html>
<html>
<!-- 第一个装饰页面 -->
<head>
    <!-- 从被装饰页面获取title标签内容,并设置默认值-->
    <title><decorator:title default="默认title"/></title>
    <!-- 从被装饰页面获取head标签内容 -->
    <decorator:head/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="../css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- FontAwesome 4.3.0 -->
    <%--<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />--%>
    <link href="../css/common/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons 2.0.0 -->
    <%--<link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet" type="text/css" />--%>
    <link href="../css/common/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="../css/common/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link href="../css/common/_all-skins.min.css" rel="stylesheet" type="text/css" />
    <!-- iCheck -->
    <link href="../js/common/plugins/iCheck/flat/blue.css" rel="stylesheet" type="text/css" />
    <%--<!-- Morris chart -->
    <link href="../js/common/plugins/morris/morris.css" rel="stylesheet" type="text/css" />--%>
    <!-- jvectormap -->
    <link href="../js/common/plugins/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
    <!-- Date Picker -->
    <link href="../js/common/plugins/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
    <!-- Daterange picker -->
    <link href="../js/common/plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
    <!-- bootstrap wysihtml5 - text editor -->
    <link href="../js/common/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" rel="stylesheet" type="text/css" />
    <!-- HTML5 js -->
    <%--<script src="../js/common/html5.js" type="text/javascript"></script>--%>

    <style type="text/css">
        body {font-family:Helvetica,Tahoma,"Microsoft YaHei","微软雅黑",Arial,STXihei,"华文细黑",SimSun,"宋体",Heiti,"黑体",sans-serif;}
        #totop {
            background: transparent none repeat scroll 0 0;
            border: 3px solid #e74c3c;
            border-radius: 50%;
            bottom: 40px;
            height: 45px;
            position: fixed;
            right: 1.5%;
            text-align: center;
            width: 45px;
            z-index: 9999;
        }
        #totop i {
            color: #e74c3c;
            font-size: 33px;
            line-height: 40px;
        }
        .sidebar-menu>li.active>a{
            background-color: #4687cb!important;
            color:white!important;
        }
        .sidebar-menu>li.active>a>i{
            color:white!important;
        }
        .sidebar-menu>li>ul a{
            padding-left: 40px!important;
        }
        .treeview-menu{
            padding-left:0!important;
        }
        .sidebar-menu>li>a:hover{
            background-color: #b9d4f1!important;
            color:#444444!important;
        }
        .liactive{
            background-color: #b9d4f1!important;
            color:#444444!important;
        }
        .treeview-menu>li.active>a{
            background-color: #4687cb!important;
            color:white!important;
        }
        .treeview-menu>li>a:hover{
            background-color: #b9d4f1!important;
            color:#444444!important;
        }
        .skin-blue-light .sidebar-menu>li>.treeview-menu{
            background-color: #eeeff4;
        }
        .onescheckxl{
            cursor: pointer;
        }
        .onescheckxl:hover {
            background-color: rgb(0,103,172)!important;
            color: white!important;
        }
        .checkedxl{
            color: #ccc!important;
        }
        .removebt:hover{
            color:  rgb(0,103,172)!important;
        }
        .phonefz{
            height:100%;
            width: 80px;
            float: left;
            line-height: 50px;
            text-align: center;
            cursor: pointer;
        }
        .board_color_div{
            width:20px;
            height:20px;
            float: left;
            margin-left: 10px;
            cursor:pointer;
        }
        .board_color_div:hover{
            border:solid 2px blue;
        }
        .center{
            text-align: center;
        }
        .user_p{
            line-height: 40px;
            cursor: pointer;
        }
        .user_p:hover{
            background: #0086ed;
            color:white;
        }
        .user-body>*{
            padding:0 10px;
        }
        .user-body p{
            margin-bottom: 0;
        }
        input.from-control{
            height:34px;
        }
    </style>
</head>
<%
    TUser user = (TUser)session.getAttribute("session_user");
    TDepartment dept = (TDepartment)session.getAttribute("session_dept");
    List<Portlet> portletLeftList = (List<Portlet>)session.getAttribute("portletLeftList");
    List<Portlet> portletRightList = (List<Portlet>)session.getAttribute("portletRightList");
%>

<body class="skin-blue-light sidebar-mini" style="/*min-width:1150px!important;*/font-family: Helvetica,Tahoma,'Microsoft YaHei','微软雅黑',Arial,STXihei,'华文细黑',SimSun,'宋体',Heiti,'黑体',sans-serif; ">
<input type="hidden" value="<%=(String) request.getSession().getAttribute("qx")%>" id="qxstr"/>
<div class="wrapper">
    <header class="main-header">
        <!-- Logo -->
        <a href="../login/person" class="logo" style="background: #0067ac;">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><img src="../image/logo/logo.png" alt="User Image" /></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><img src="../image/logo/logo.png" alt="User Image" /> <b>浙江港航综合管理与服务平台1.0</b></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation" style="background: #0067ac;">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <div class="navbar-custom-menu" style="margin-top:0;">
                <ul class="nav navbar-nav">
                    <%--<li class="dropdown notifications-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning">10</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">您有10条消息提醒</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-warning text-yellow"></i> Very long description here that may not fit into the page and may cause design problems
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-users text-red"></i> 5 new members joined
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-user text-red"></i> You changed your username
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="footer"><a href="#">View all</a></li>
                        </ul>
                    </li>--%>
                    <!-- User Account: style can be found in dropdown.less -->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="../image/logo/ic_user.png" class="user-image" alt="User Image" />
                            <span class="hidden-xs"><%=user.getStUserName()%></span>
                        </a>
                        <ul class="dropdown-menu" style="width: 180px;box-shadow: 0 6px 12px rgba(0,0,0,.18)!important;border-radius: 0;">
                            <!-- User image -->
                            <li class="user-body" style="height:auto;padding:10px 0;border:0;">
                                <p style="font-weight:600;font-size: 16px;line-height: 30px; ">
                                    <%=user.getStUserName()%>
                                    <%--<small>Member since Nov. 2016</small>--%>
                                </p>
                                <p style="margin-bottom: 15px;">
                                    <%=dept.getStDepartmentName()%>
                                    <%--<small>Member since Nov. 2016</small>--%>
                                </p>
                                <p class="user_p" style="line-height: 40px;border-top: solid 1px #d7d7d7;" onclick="turnPersonCenter()" >
                                    <i class="fa fa-user"></i>&nbsp;个人中心
                                </p>
                                <p class="user_p" style="line-height: 40px;" data-target="#delModal" data-toggle="modal">
                                    <i class="fa fa-sign-out"></i>&nbsp;注销
                                </p>
                            </li>
                            <!-- Menu Body --><!-- TWQ注释 留做备用 -->
                            <%--<li class="user-body">
                                <div class="col-xs-4 text-center">
                                    <a href="#">Followers</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="#">Sales</a>
                                </div>
                                <div class="col-xs-4 text-center">
                                    <a href="#">Friends</a>
                                </div>
                            </li>--%>
                            <!-- Menu Footer-->
                            <%--<li class="user-footer" style="height:40px;padding:5px;">--%>
                                <%--<div class="pull-left" >--%>
                                    <%--<a href="#" style="height:30px;"class="btn btn-default btn-flat">个人中心</a>--%>
                                <%--</div>--%>

                                <%--<div class="pull-right"  >--%>
                                    <%--<button style="height:30px;"class="btn btn-default btn-flat" data-toggle="modal"--%>
                                            <%--data-target="#delModal">注销</button>--%>
                                <%--</div>--%>
                            <%--</li>--%>
                        </ul>
                    </li>
                    <%--<li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">

                            <img src="../image/logo/ic_user.png" class="user-image" alt="User Image" />

                            <span class="hidden-xs"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="../image/logo/ic_user100.png" class="img-circle" alt="User Image" />
                                <p>

                                    <small>Member since Nov. 2012</small>
                                </p>
                            </li>

                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">个人中心</a>
                                </div>
                                <div class="pull-right">
                                    <a href="#" class="btn btn-default btn-flat">注销</a>
                                </div>
                            </li>
                        </ul>
                    </li>--%>
                    <!-- Control Sidebar Toggle Button -->
                    <li>
                        <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!--BEGIN BACK TO TOP--><%--<a id="totop" href="#" title="回到顶部"><i class="fa fa-angle-up"></i></a>--%>
    <!--END BACK TO TOP-->
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar" style="background: url('../image/left_nav_bg.png') 100% 100% no-repeat;background-color: #e8f3f5;">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

            <ul class="sidebar-menu">
                <li id="workspace"><a href="../login/person"><i class="fa fa-user" style="color:#19a297" ></i> <span>我的工作台</span></a></li>

                <li class="treeview qxz qx1" id="information">
                    <a href="#">
                        <i class="fa fa-file-text" style="color:#5e8bfa"></i> <span>信息发布</span> <i class="fa fa-angle-left pull-right"></i>
                        <!-- <span class="label label-primary pull-right">4</span> -->
                    </a>
                    <ul class="treeview-menu">
                        <li id="inform-personal" class="qxz qx8"><a href="../information/personalInfommain"> 我相关的信息</a></li>
                        <li id="inform-send" class="qxz qx9"><a href="../information/sendmain"> 我发布的信息</a></li>
                        <li id="inform-approval" class="qxz qx10"><a href="../information/approvalmain"> 我审批的信息</a></li>
                        <%--<li id="inform-edit"><a href="../information/editmain"> 我修改的信息</a></li>--%>
                    </ul>
                </li>
                <li class="treeview qxz qx2" id="officeAssistant">
                    <a href="#">
                        <i class="fa fa-briefcase" style="color:#168dfe"></i> <span>办公助理</span> <i class="fa fa-angle-left pull-right"></i>
                        <!-- <span class="label label-primary pull-right">4</span> -->
                    </a>
                    <ul class="treeview-menu">
                        <li id="calendar_li" class="qxz qx11"><a href="../officeAssistant/calendar"> 日程安排</a></li>
                        <li id="knowledgeBase_li" class="qxz qx12"><a href="../officeAssistant/knowledgemain"> 知识库</a></li>
                        <li id="addressList_li" class="qxz qx13"><a href="../officeAssistant/addressList"> 通讯录</a></li>
                    </ul>
                </li>
                <li class="treeview qxz qx3" id="report"><a href="#"><i class="fa fa-file-text-o" style="color:#fd706f"></i> <span>电子报告</span>
                </a>
                </li>
                <li class="treeview qxz qx4" id="viewcheck"><a href="../smart/videocheck.jsp"><i class="fa fa-file-video-o" style="color:#35b746"></i> <span>视频查看</span>
                    </a>
                </li>
                <li class="treeview qxz qx5" id="smart">
                    <a href="#">
                        <i class="fa fa-desktop" style="color:#168dfe"></i>
                        <span>智慧监管</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li id="ssjc_li" class="qxz qx16"><a href="../smart/ssjc.jsp"> 实时监测</a></li>
                        <%--<li id="shipOrbit_li" class="qxz qx20"><a href="../smart/shipOrbit.jsp"> 船舶轨迹</a></li>--%>
                        <li id="electronicCruise_id" class="qxz qx17">
                            <a href="../smart/electronicCruise.jsp"> 电子巡航<i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="treeview-menu">
                                <li id="rjjh_li" class="qxz qx19">
                                    <a href="../smart/electronicCruise.jsp"> 人机交互</a>
                                </li>
                                <li id="yjxh_li" class="qxz qx20">
                                    <a href="../smart/yjxh.jsp"> 一键巡航</a>
                                </li>
                            </ul>
                        </li>
                        <li id="warn" class="qxz qx18">
                            <a href="#"> 监测预警<i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="treeview-menu">
                                <li id="warndata_li" class="qxz qx21"><a href="../smart/warndata.jsp?area=330000"> 报警信息</a></li>
                                <li id="warnset_li" class="qxz qx22">
                                    <a href="../smart/warnset.jsp"> 报警设置</a>
                                </li>
                                <li id="llyc_li" class="qxz qx23"><a href="../smart/llyc.jsp"> 流量预测</a></li>
                            </ul>
                        </li>
                    </ul>
                </li>

                <li class="treeview qxz qx6" id="query">
                    <a href="#">
                        <i class="fa fa-search" style="color:#ea7d34"></i> <span>查询统计</span>
                        <!--<i class="fa fa-angle-left pull-right"></i>-->
                    </a>
                    <!--<ul class="treeview-menu">
                        <li id="query_li" class="qxz qx13"><a href="../query/query.jsp"> 综合查询</a></li>
                        <li class="qxz qx14"><a href="#"> 统计图表</a></li>
                    </ul>-->
                </li>
                <li class="treeview qxz qx7" id="system">
                    <a href="#">
                        <i class="fa fa-cog" style="color:#6669fe"></i> <span>系统设置</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <!--<li><a href="#"> 用户信息审核</a></li>-->
                        <li id="userManage_li" class="qxz qx24"><a href="../system/userManage"> 用户管理</a></li>
                        <li id="orgManage_li" class="qxz qx25"><a href="../system/orgManage"> 单位管理</a></li>
                        <li id="rolemanage_li" class="qxz qx26"><a href="../system/roleManage.jsp"> 角色管理</a></li>
                        <!--<li><a href="#"> portlet权限</a></li>-->
                        <li id="portletManage_li" class="qxz qx27"><a href="../system/portletManage"> portlet管理</a></li>
                        <li id="portletLib_li" class="qxz qx28"><a href="../system/portletLib"> portlet字典</a></li>
                        <li id="LogManage_li" class="qxz qx29"><a href="../system/LogManage.jsp"> 系统日志</a></li>
                        <!--<li><a href="#"> 数据字典</a></li>
                        <li><a href="#"> 模板管理</a></li>
                        <li><a href="#"> 我的应用</a></li>
                        <li><a href="#"> 系统应用</a></li>-->
                    </ul>
                </li>
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>
    <!-- 从被装饰页面获取body标签内容 -->
    <div class="content-wrapper"    style="overflow:auto;background-color: rgb(241,242,246);" >
        <decorator:body />
    </div>
    <footer class="main-footer" >
        <div class="pull-right hidden-xs">
            Version
            1.0
        </div>
        Copyright &copy; 2016 <a href="#">浙江省港航管理局</a>. 版权所有.
    </footer>

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Create the tabs -->
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
            <li id="control_setting " class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
            <%--<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
          --%></ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <!-- Home tab content -->
            <div class="tab-pane active" id="control-sidebar-home-tab">
                <h4 class="control-sidebar-heading">控制台设置</h4>
                <ul class="control-sidebar-menu" id="control_setting_ul">
                    <% for(Portlet portlet:portletLeftList) {%>
                    <li id="<%=portlet.getStModuleId()%>_index">
                        <a href="javascript:;">
                            <i class="menu-icon fa <%=portlet.getStIcon()%> bg-yellow"></i>
                            <div class="menu-info" style="margin-top:9px">
                                <h4 class="control-sidebar-subheading"><%=portlet.getStModuleName()%></h4>
                                <%--<p>New phone +1(800)555-1234</p>--%>
                                <input type="checkbox" class="pull-right function_menu" style="margin-top:-14px" id="<%=portlet.getStModuleId()%>_check" checked="checked" onchange="main_to_index(this)"/>
                            </div>
                        </a>
                    </li>
                    <%}%>
                    <% for(Portlet portlet:portletRightList) {%>
                    <li id="<%=portlet.getStModuleId()%>_index">
                        <a href="javascript:;">
                            <i class="menu-icon fa <%=portlet.getStIcon()%> bg-yellow"></i>
                            <div class="menu-info" style="margin-top:9px">
                                <h4 class="control-sidebar-subheading"><%=portlet.getStModuleName()%></h4>
                                <%--<p>New phone +1(800)555-1234</p>--%>
                                <input type="checkbox" class="pull-right function_menu" style="margin-top:-14px" id="<%=portlet.getStModuleId()%>_check" checked="checked" onchange="main_to_index(this)"/>
                            </div>
                        </a>
                    </li>
                    <%}%>
                    <%--<li id="company_index">
                        <a href="javascript:;">
                            <i class="menu-icon fa fa-bar-chart bg-yellow-twq"></i>
                            <div class="menu-info" style="margin-top:9px">
                                <h4 class="control-sidebar-subheading">企业泊位分港区统计</h4>
                                &lt;%&ndash;<p>Execution time 5 seconds</p>&ndash;%&gt;
                                <input type="checkbox" class="pull-right function_menu" style="margin-top:-14px" id="company_check" checked="checked" onchange="main_to_index(this)"/>
                            </div>
                        </a>
                    </li>
                    <li id="accessTop_index">
                        <a href="javascript:;">
                            <i class="menu-icon fa fa-search bg-red"></i>
                            <div class="menu-info" style="margin-top:9px">
                                <h4 class="control-sidebar-subheading">本周应用访问top</h4>
                                &lt;%&ndash;<p>Will be 23 on April 24th</p>&ndash;%&gt;
                                <input type="checkbox" class="pull-right function_menu" style="margin-top:-14px" id="accessTop_check" checked="checked" onchange="main_to_index(this)"/>
                            </div>
                        </a>
                    </li>

                    <li id="channel_index">
                        <a href="javascript:;">
                            <i class="menu-icon fa fa-signal bg-yellow-twq"></i>
                            <div class="menu-info" style="margin-top:9px">
                                <h4 class="control-sidebar-subheading">航道按等级统计</h4>
                                &lt;%&ndash;<p>Execution time 5 seconds</p>&ndash;%&gt;
                                <input type="checkbox" class="pull-right function_menu" style="margin-top:-14px" id="channel_check" checked="checked" onchange="main_to_index(this)"/>
                            </div>
                        </a>
                    </li>
                    <li id="calendar">
                        <a href="javascript:;">
                            <i class="menu-icon fa fa-search bg-fuchsia"></i>
                            <div class="menu-info" style="margin-top:9px">
                                <h4 class="control-sidebar-subheading">日程安排</h4>
                                &lt;%&ndash;<p>Execution time 5 seconds</p>&ndash;%&gt;
                                <input type="checkbox" class="pull-right function_menu" style="margin-top:-14px" id="calendar_check" checked="checked" onchange="main_to_index(this)"/>
                            </div>
                        </a>
                    </li>--%>

                </ul><!-- /.control-sidebar-menu -->

                <%--<h3 class="control-sidebar-heading">Tasks Progress</h3>
                <ul class="control-sidebar-menu">
                  <li>
                    <a href="javascript:;">
                      <h4 class="control-sidebar-subheading">
                        Custom Template Design
                        <span class="label label-danger pull-right">70%</span>
                      </h4>
                      <div class="progress progress-xxs">
                        <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
                      </div>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <h4 class="control-sidebar-subheading">
                        Update Resume
                        <span class="label label-success pull-right">95%</span>
                      </h4>
                      <div class="progress progress-xxs">
                        <div class="progress-bar progress-bar-success" style="width: 95%"></div>
                      </div>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <h4 class="control-sidebar-subheading">
                        Laravel Integration
                        <span class="label label-warning pull-right">50%</span>
                      </h4>
                      <div class="progress progress-xxs">
                        <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
                      </div>
                    </a>
                  </li>
                  <li>
                    <a href="javascript:;">
                      <h4 class="control-sidebar-subheading">
                        Back End Framework
                        <span class="label label-primary pull-right">68%</span>
                      </h4>
                      <div class="progress progress-xxs">
                        <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
                      </div>
                    </a>
                  </li>
                </ul><!-- /.control-sidebar-menu -->--%>

            </div><!-- /.tab-pane -->
            <!-- Stats tab content -->
            <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div><!-- /.tab-pane -->
            <!-- Settings tab content -->
            <div class="tab-pane" id="control-sidebar-settings-tab">
                <form method="post">
                    <h3 class="control-sidebar-heading">General Settings</h3>
                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Report panel usage
                            <input type="checkbox" class="pull-right" checked />
                        </label>
                        <p>
                            Some information about this general settings option
                        </p>
                    </div><!-- /.form-group -->

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Allow mail redirect
                            <input type="checkbox" class="pull-right" checked />
                        </label>
                        <p>
                            Other sets of options are available
                        </p>
                    </div><!-- /.form-group -->

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Expose author name in posts
                            <input type="checkbox" class="pull-right" checked />
                        </label>
                        <p>
                            Allow the user to show his name in blog posts
                        </p>
                    </div><!-- /.form-group -->

                    <h3 class="control-sidebar-heading">Chat Settings</h3>

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Show me as online
                            <input type="checkbox" class="pull-right" checked />
                        </label>
                    </div><!-- /.form-group -->

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Turn off notifications
                            <input type="checkbox" class="pull-right" />
                        </label>
                    </div><!-- /.form-group -->

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Delete chat history
                            <a href="javascript:;" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
                        </label>
                    </div><!-- /.form-group -->
                </form>
            </div><!-- /.tab-pane -->
        </div>
    </aside><!-- /.control-sidebar -->
    <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>

<!-- 装饰页面获取footer内容 -->
<%--<hr />
<h2>SiteMesh装饰footer</h2>--%>
<!-- AdminLTE for demo purposes -->

<!-- jQuery UI 1.11.4 -->
<script src="../js/common/jquery-ui.min.js" type="text/javascript"></script>
<script type="text/javascript">
    function turnPersonCenter() {
        window.location.href="../system/personCenter";
    }

    $.widget.bridge('uibutton', $.ui.button);
    function logout(){
        $.ajax({
            url: '../logout/exit',
            type: 'post',
            dataType: 'json',
            complete : function() {
                window.location.href="http://172.26.24.160/casZjgh/logout?service=http://10.100.70.129"
                //window.location.href = "http://220.189.207.21:8290/casEG/logout?service=http://localhost:8080/ZjPort/";
            }
        })
        //
    }
</script>

<!-- Bootstrap 3.3.2 JS -->
<script src="../js/common/bootstrap.min.js" type="text/javascript"></script>
<!-- Morris.js charts -->
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
--%>
<script src="../js/common/raphael-min.js" type="text/javascript"></script>
<%--<script src="../js/common/plugins/morris/morris.min.js" type="text/javascript"></script>--%>
<!-- Sparkline -->
<script src="../js/common/plugins/sparkline/jquery.sparkline.min.js" type="text/javascript"></script>
<!-- jvectormap -->
<script src="../js/common/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js" type="text/javascript"></script>
<script src="../js/common/plugins/jvectormap/jquery-jvectormap-world-mill-en.js" type="text/javascript"></script>
<!-- jQuery Knob Chart -->
<script src="../js/common/plugins/knob/jquery.knob.js" type="text/javascript"></script>
<!-- daterangepicker -->
<script src="../js/common/moment.min.js" type="text/javascript"></script>

<%--<script src="../js/moment-min.js" type="text/javascript"></script>--%>
<script src="../js/common/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
<!-- datepicker -->
<script src="../js/common/plugins/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="../js/common/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js" type="text/javascript"></script>
<!-- Slimscroll -->
<script src="../js/common/plugins/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<!-- FastClick -->
<script src="../js/common/plugins/fastclick/fastclick.min.js" type="text/javascript"></script>

<!-- ChartJS 1.0.1 -->
<script src="../js/common/plugins/chartjs/Chart.min.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="../js/common/app.min.js" type="text/javascript"></script>

<!-- AdminLTE for demo purposes -->
<script src="../js/common/demo.js" type="text/javascript"></script>
<script src="../js/common/qx.js" type="text/javascript"></script>
<script type="text/javascript">

setqx();

    var timerID = null;
    var timerRunning = false;

    function showtime() {
        var now = new Date();

        document.clock.thetime.value = now.toString();
        timerID = setTimeout("showtime()", 1000);
        timerRunning = true;
    }
</script>
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
                &nbsp;您确定要注销么？
            </div>
            <div class="modal-footer">
                <button  class="btn btn-primary"  onclick="logout()">
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