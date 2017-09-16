<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- 第一个装饰页面 -->
<head>
    <!-- 从被装饰页面获取title标签内容,并设置默认值-->
    <title><decorator:title default="默认title"/></title>
    <!-- 从被装饰页面获取head标签内容 -->
    <decorator:head/>
    <link rel="shortcut icon" href="/image/favicon.ico" type="image/x-icon" />
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
    <!-- Morris chart -->
    <%--<link href="../js/common/plugins/morris/morris.css" rel="stylesheet" type="text/css" />--%>
    <!-- jvectormap -->
    <link href="../js/common/plugins/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
    <!-- Date Picker -->
    <link href="../js/common/plugins/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
    <!-- Daterange picker -->
    <link href="../js/common/plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
    <!-- bootstrap wysihtml5 - text editor -->
    <link href="../js/common/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="Home/top.css" />
    <style type="text/css">
        .sidebar-menu>li.active>a{
            background-color: #eeeff4!important;
            color:#0186ed!important;
        }
        .sidebar-menu>li>a:hover{
            background-color: #eeeff4!important;
            color:#0186ed!important;
        }
        .treeview-menu>li.active>a{
            color:white!important;
            background-color: #0186ed!important;
        }
        .treeview-menu>li>a:hover{
            color:white!important;
            background-color: #0186ed!important;
        }
        .skin-blue-light .sidebar-menu>li>.treeview-menu{
            background-color: #eeeff4;
        }
    </style>
</head>
<%
 String userid=(String)session.getAttribute("id");
%>

<body class="skin-blue-light sidebar-mini" style="min-width:1150px!important;font-family: Helvetica,Tahoma,'Microsoft YaHei','微软雅黑',Arial,STXihei,'华文细黑',SimSun,'宋体',Heiti,'黑体',sans-serif; ">
<input type="hidden" value="<%=(String)session.getAttribute("name")%>" id="username"/>
<input type="hidden" value="<%=(String)session.getAttribute("sjzg")%>" id="sjzg"/>
<input type="hidden" value="<%=(String)session.getAttribute("dep")%>" id="bmname"/>
<input type="hidden" value="<%=(String)session.getAttribute("bm")%>" id="userbmid"/>
<input type="hidden" value="<%=userid%>" id="userid"/>
<input type="hidden" value="<%=(String)session.getAttribute("js")%>" id="jsid"/>
<input type="hidden" value="<%=(String)session.getAttribute("dz")%>" id="dz"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<div class="wrapper">
    <div style="width:100%;
                height:100%;
                background: rgba(255,255,255,0.4);
                position: fixed;
                z-index: 10000;
                background-image:url('../image/deng.gif');
                background-repeat: no-repeat;
                background-position: center;
                display:none;" id="waitdiv"></div>
    <script type="text/javascript">
        function showwaitdiv(){
            $("#waitdiv").show();
        }
        function hidewaitdiv(){
            $("#waitdiv").hide();
        }
    </script>
    <header class="main-header" style="max-height: 200px;">
        <div id="top" >
            <div id="top1">
                <div id="top2">
                    <img src="<%=basePath%>image/top/top_logo.png" id="iconImg"/>
                </div>
                <div id="top3">
                    <i class="fa fa-user" style="color: rgb(146,161,194);"></i><font id="top_user">&nbsp;<%=(String)session.getAttribute("name")%></font>&nbsp;|&nbsp;
                    <!--<i class="fa fa-envelope" style="color: rgb(146,161,194);"></i><a onclick="" class="btExit">消息（1）</a>&nbsp;|&nbsp;-->
                    <i class="fa fa-lock" style="color: rgb(146,161,194);"></i><a class="btExit" data-toggle="modal" data-target="#changepasswordmodal"/>修改密码</a>&nbsp;|&nbsp;
                    <i class="fa fa-power-off" style="color: rgb(146,161,194);"></i><a onclick="exit()" class="btExit"/>退出</a>
                    <a style="display: none" href="<%=basePath%>page/downloadAPP.jsp"  target="mainLeft" id="downloadApp">|APP管理</a>
                </div>
            </div>

            <div >
                <script type="text/javascript">
                    function  exit()
                    {
                        window.location.href=$("#basePath").val()+"LogOut";

                    }
                </script>
        </div>
        </div>
    </header>

    <aside class="main-sidebar" style="padding-top: 100px;">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

            <ul class="sidebar-menu">
                <li class="treeview" id="shouye_li" >
                    <a href="<%=basePath%>shouye" class="gztli">
                    <i class="fa fa-tasks"></i> <span>我的工作台</span>
                    </a>
                 </li>

                <li class="treeview" id="book_li" style="display: none">
                    <a href="<%=basePath%>contact_list" class="txlli">
                        <i class="fa fa-book"></i> <span>通讯录</span>
                    </a>
                </li>

                <li class="treeview" id="commenting_li" style="display: none">
                    <a href="#">
                        <i class="fa fa-commenting"></i> <span>通知公告</span> <i class="fa fa-angle-left pull-right"></i>
                        <!-- <span class="label label-primary pull-right">4</span> -->
                    </a>
                    <ul class="treeview-menu">
                        <li id="myxinxi_li"><a  href="<%=basePath%>xinxi_add"><i class="fa fa-circle-o"></i> 通知发布</a></li>
                        <!--<li id="xinxisp_li"><a href="<%=basePath%>xinxi_list"><i class="fa fa-circle-o"></i> 通告审批</a></li> -->
                        <li id="xinxiquery_li"><a href="<%=basePath%>xinxiquery_list"><i class="fa fa-circle-o"></i> 通知管理</a></li>
                    </ul>
                </li>
                <li class="treeview" id="calendar_li" style="display: none">
                    <a href="#">
                        <i class="fa fa-calendar"></i>
                        <span>考勤管理</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li id="kqsp_li"><a href="<%=basePath%>kqsp_list"><i class="fa fa-circle-o"></i> 考勤审批</a></li>
                        <li id="mykq_li"><a  href="<%=basePath%>mykq_list"><i class="fa fa-circle-o"></i> 我的考勤</a></li>
                        <li id="cq_li"><a href="<%=basePath%>cq_list"><i class="fa fa-circle-o"></i> 出勤统计</a></li>
                    </ul>
                </li>
                <li class="treeview" id="check_li" style="display: none">
                    <a href="#">
                        <i class="fa fa-check"></i> <span>业务审核</span><i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li id="wzsp_li"><a href="<%=basePath%>wzsp_list"><i class="fa fa-circle-o"></i> 违章取证审批</a></li>
                        <li id="jycx_li"><a href="<%=basePath%>jycx"><i class="fa fa-circle-o"></i>简易执法审批</a></li>
                        <li id="whjgsp_li"><a  href="<%=basePath%>whjgsp_list"><i class="fa fa-circle-o"></i> 危货进港申报审批</a></li>
                        <li id="whzysp_li"><a  href="<%=basePath%>whzysp_list"><i class="fa fa-circle-o"></i> 危货作业申报审批</a></li>
                    </ul>
                </li>
                <li class="treeview" id="search_li" style="display: none">
                    <a href="#">
                        <i class="fa fa-search"></i> <span>查询统计</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li id="cbcx_li"><a href="<%=basePath%>cbcx_list"><i class="fa fa-circle-o"></i>船舶查询</a></li>
                        <li id="dzbg_li"><a href="<%=basePath%>dzbg"><i class="fa fa-circle-o"></i>电子报告</a></li>
                        <li id="xhrz_li"><a href="<%=basePath%>xhrz"><i class="fa fa-circle-o"></i>巡航日志</a></li>
                        <li id="zfcx_li"><a href="<%=basePath%>zfcx"><i class="fa fa-circle-o"></i>执法查询</a></li>

                        <li id="ybcx_li"><a href="<%=basePath%>ybcx"><i class="fa fa-circle-o"></i>一般程序</a></li>
                        <li id="qztj_li"><a href="<%=basePath%>qztj"><i class="fa fa-circle-o"></i>取证统计</a></li>
                        <li id="xctj_li"><a href="<%=basePath%>xctj"><i class="fa fa-circle-o"></i>巡查统计</a></li>
                    </ul>
                </li>
                <li class="treeview" id="nine_li" style="display: none">
                    <a href="#">
                        <i class="fa fa-ship"></i> <span>九位码核查</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <%--<li id="ninecheck_li"><a href="<%=basePath%>filecheck_list"><i class="fa fa-circle-o"></i> 九位码校验</a></li>--%>
                        <li id="jcjj_li"><a  href="<%=basePath%>jcjj_list"><i class="fa fa-circle-o"></i> 纠错记录</a></li>
                    </ul>
                </li>
                <li class="treeview" id="picture-o_li" style="display: none">
                    <a href="<%=basePath%>evid_list" class="zjbqli">
                        <i class="fa fa-picture-o"></i> <span>证据保全</span>
                    </a>
                </li>
                <li class="treeview" id="user_li" style="display: none">
                    <a href="#">
                        <i class="fa fa-user"></i> <span>公共用户管理</span><i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li id="register_li"><a href="<%=basePath%>register_list"><i class="fa fa-circle-o"></i> 注册审批</a></li>
                        <li id="userlist_li"><a href="<%=basePath%>user_list"><i class="fa fa-circle-o"></i> 用户管理</a></li>
                    </ul>
                </li>
                <li class="treeview " id="envelope_li" style="display: none">
                    <a href="<%=basePath%>message_list" class="yjxli">
                        <i class="fa fa-envelope"></i> <span>意见箱</span>
                        <!-- <span class="label label-primary pull-right">4</span> -->
                    </a>
                </li>
                <li class="treeview" id="file_li" style="display: none">
                    <a href="#">
                        <i class="fa fa-file"></i> <span>文书起草</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li id="filecheck_li"><a href="<%=basePath%>filecheck_list"><i class="fa fa-circle-o"></i> 文书审批</a></li>
                        <li id="myfile_li"><a  href="<%=basePath%>myfile_list"><i class="fa fa-circle-o"></i> 我的文书</a></li>
                    </ul>
                </li>

                <li class="treeview" id="th-large_li" style="display: none">
                    <a href="#">
                        <i class="fa fa-th-large"></i> <span>APP管理</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">

                        <!--<li id="outerApp_li"><a href="<%=basePath%>outerApp_list"><i class="fa fa-circle-o"></i>版本管理（公众版）</a></li>-->
                        <li id="innerApp_li"><a href="<%=basePath%>innerApp_list"><i class="fa fa-circle-o"></i>版本管理（内部版）</a></li>
                        <li id="loadApp_li"><a href="<%=basePath%>load_list"><i class="fa fa-circle-o"></i>下载统计</a></li>
                    </ul>
                </li>
                <li class="treeview" id="cog" style="display: none">
                    <a href="#">
                        <i class="fa fa-cog"></i> <span>系统设置</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li id="rolemanager_li"><a href="<%=basePath%>rolemanager_list"><i class="fa fa-circle-o"></i> 角色管理</a></li>
                        <li id="datadictionary_li"><a href="<%=basePath%>datadictionary_list"><i class="fa fa-circle-o"></i> 数据字典</a></li>
                        <li id="systemlog_li"><a href="<%=basePath%>systemlog_list"><i class="fa fa-circle-o"></i> 系统日志</a></li>
                        <!--<li><a href="#"><i class="fa fa-circle-o"></i> 用户管理</a></li>
                        <li><a href="#"><i class="fa fa-circle-o"></i> 数据备份</a></li>


                        <li><a href="#"><i class="fa fa-circle-o"></i> 数据录入</a></li>
                        <li><a href="#"><i class="fa fa-circle-o"></i> 移动安全</a></li>-->
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

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="changepasswordmodal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        修改密码
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="form-group" style="height:34px;">
                        <label class="col-sm-2 control-label" style="line-height: 34px;text-align: right;padding-right:10px;">原密码：</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="password" type="password"
                                   placeholder="请输入原密码">
                        </div>
                    </div>
                    <div class="form-group" style="height:34px;">
                        <label class="col-sm-2 control-label" style="line-height: 34px;text-align: right;padding-right:10px;">新密码：</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="newuserpassword" type="password"
                                   placeholder="请输入新密码">
                        </div>
                    </div>
                    <div class="form-group" style="height:34px;margin-bottom:0; ">
                        <label class="col-sm-2 control-label" style="line-height: 34px;text-align: right;padding-right:10px;">确认密码：</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="renewuserpassword" type="password"
                                   placeholder="确认新密码">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="changepassword()">
                        确定
                    </button>
                    <button type="button" class="btn btn-default" style="margin-bottom:0!important;"
                            data-dismiss="modal">关闭
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
        <script type="text/javascript">
            function changepassword(){
                if($.trim($("#password").val())==null||$.trim($("#password").val())==''){
                    alert('原密码不能为空');
                    return
                }
                if($.trim($("#newuserpassword").val())==null||$.trim($("#newuserpassword").val())==''){
                    alert('新密码不能为空');
                    return
                }
                if($.trim($("#newuserpassword").val())!=$.trim($("#renewuserpassword").val())){
                    alert('2次新密码输入不一致，请检查');
                    return
                }
                $.ajax({
                    url: 'changepsw',
                    type: "post",
                    dataType: "json",
                    data : {
                        'Username' : $('#userid').val(),
                        'Pswold' : $('#password').val(),
                        'Pswnew' : $('#newuserpassword').val()
                    },
                    success: function (data) {
                        if(data.resultcode!=1){
                            alert(data.resultdesc);
                            return
                        }else{
                            alert(data.resultdesc);
                            $('#changepasswordmodal').modal('hide');
                        }
                    }
                })
            }
        </script>
</div>
<!-- Bootstrap 3.3.2 JS -->
<script src="js/common/bootstrap.min.js" type="text/javascript"></script>
<!-- Morris.js charts -->
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
--%>
<script src="js/common/raphael-min.js" type="text/javascript"></script>
<script src="js/common/plugins/morris/morris.min.js" type="text/javascript"></script>
<!-- Sparkline -->
<script src="js/common/plugins/sparkline/jquery.sparkline.min.js" type="text/javascript"></script>
<!-- jvectormap -->
<script src="js/common/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js" type="text/javascript"></script>
<script src="js/common/plugins/jvectormap/jquery-jvectormap-world-mill-en.js" type="text/javascript"></script>
<!-- jQuery Knob Chart -->
<script src="js/common/plugins/knob/jquery.knob.js" type="text/javascript"></script>
<!-- daterangepicker -->
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js" type="text/javascript"></script>--%>

<%--<script src="../js/moment-min.js" type="text/javascript"></script>--%>
<script src="js/common/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
<!-- datepicker -->
<script src="js/common/plugins/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="js/common/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js" type="text/javascript"></script>
<!-- Slimscroll -->
<script src="js/common/plugins/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<!-- FastClick -->
<script src="js/common/plugins/fastclick/fastclick.min.js" type="text/javascript"></script>

<!-- ChartJS 1.0.1 -->
<script src="js/common/plugins/chartjs/Chart.min.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="js/common/app.min.js" type="text/javascript"></script>
<!-- AdminLTE for demo purposes -->
<script src="js/common/demo.js" type="text/javascript"></script>
<!-- include summernote css/js-->
    <script src="js/main/main.js" type="text/javascript"></script>
</body>
</html>