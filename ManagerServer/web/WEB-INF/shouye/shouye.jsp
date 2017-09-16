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
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>我的工作台</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../js/common/mui/mui.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/bootpaging.js"></script>
    <script src="../js/common/mui/mui.js"></script>
    <!-- 页面js -->
    <script src="../js/shouye/shouye.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword {
            color: rgb(0, 104, 177);
            cursor: pointer;
        }
        .sylabel{
            margin-bottom: 0;
            width: 100%;
            height:49px;
            border-bottom: solid 1px #ccc;
            display: inline-block;
            padding: 0 10px;
            text-align: left;
            line-height: 49px;
        }
        .sylabel span{
            color: rgb(0, 104, 177);
        }
        .fadiv{
            float: left;
            width: 33%;
            height:116px;
            text-align: center;
            padding:10px;
        }
        .fadiv div{
            width:70px;
            height:70px;
            border-radius: 4px;
            margin: 0 auto;
            cursor: pointer;
        }
        .fadiv i{
            color:white;
            font-size: 35px;
            line-height: 70px;
        }
        .fadiv p{
            line-height: 26px;
            margin-bottom: 0;
            cursor: pointer;
        }
        .smallsetdiv{
            width:30px;
            height:30px;
            border-radius: 4px;
            float: left;
            margin-top: 10px;
            text-align: center;
        }
        .smallsetdiv i{
            color:white;
            font-size: 15px;
            line-height: 30px;
        }
        .mui-switch{
            float: right;
            line-height: 25px;
            margin-top:10px;
        }

    </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header">
    <h1>
        我的工作台
        <!-- <small>advanced tables</small> -->
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-calendar"></i> 公共用户管理</a></li>
        <li><a href="#">用户管理</a></li>
    </ol>
</section>

<section class="content">
    <div class="box" style="border-top: 0">
        <div  style="float:left;padding: 0 20px;background: #ffffff;width: 100%;box-shadow: 0 0 4px #999;">
            <h3 style="color:dodgerblue;"><%=(String)session.getAttribute("name")%>,您好</h3>
            <p>欢迎您使用浙江港航综合业务管理平台，<span class="clickword" data-toggle="modal" data-target="#changepasswordmodal">修改密码</span></p>
        </div>
    </div>
    <div style="border-top: 0;margin-top:20px;float: left;width: 100%;height:420px;">
        <div style="width:400px;height:400px;background: #ffffff;float: right;box-shadow: 0 0 4px #999;">
            <label class="sylabel">
                <b>常用功能</b>
                <i class="fa fa-cog" style="cursor:pointer;float: right;margin-top: 15px;" data-toggle="modal" data-target="#setModal"></i>
            </label>
            <div style="float: left;width: 100%;">
                <div class="fadiv">
                    <div  style="background: #009966;" onclick="window.location.href='<%=basePath%>xinxi_add'">
                    <i class="fa fa-commenting" aria-hidden="true"></i>
                    </div>
                    <p onclick="window.location.href='<%=basePath%>xinxi_add'">发布通知</p>
                </div>
                <div class="fadiv">
                    <div  style="background: #0099cc;" onclick="window.location.href='<%=basePath%>jcjj_list'">
                    <i class="fa fa-ship" aria-hidden="true"></i>
                    </div>
                    <p onclick="window.location.href='<%=basePath%>jcjj_list'">九位码</p>
                </div>
                <div class="fadiv">
                    <div  style="background: #6666ff;" onclick="window.location.href='<%=basePath%>contact_list'">
                    <i class="fa fa-book" aria-hidden="true"></i>
                    </div>
                    <p onclick="window.location.href='<%=basePath%>contact_list'">通讯录</p>
                </div>
                <div class="fadiv">
                    <div  style="background: #ff6633;" onclick="window.location.href='<%=basePath%>kqsp_list'">
                    <i class="fa fa-calendar" aria-hidden="true"></i>
                    </div>
                    <p onclick="window.location.href='<%=basePath%>kqsp_list'">考勤管理</p>
                </div>
                <div class="fadiv">
                    <div  style="background: #00cc66;" onclick="window.location.href='<%=basePath%>filecheck_list'">
                    <i class="fa fa-file" aria-hidden="true"></i>
                    </div>
                    <p onclick="window.location.href='<%=basePath%>filecheck_list'">文书起草</p>
                </div>
                <div class="fadiv">
                    <div  style="background: #00c686;" onclick="window.location.href='<%=basePath%>xhrz'">
                    <i class="fa fa-file-text-o" aria-hidden="true"></i>
                    </div>
                    <p onclick="window.location.href='<%=basePath%>xhrz'">巡航日志</p>
                </div>
                <div class="fadiv">
                    <div  style="background: #4ea5fc;" onclick="window.location.href='<%=basePath%>cbcx_list'">
                    <i class="fa fa-search" aria-hidden="true"></i>
                    </div>
                    <p onclick="window.location.href='<%=basePath%>cbcx_list'">船舶查询</p>
                </div>
            </div>
        </div>
        <div style="margin-right:420px;background: white;height:400px;box-shadow: 0 0 4px #999;overflow-y: auto">
            <label class="sylabel">
                <b>代办信息</b>
            </label>
            <label class="sylabel">
                <span>[注册审批]</span>&nbsp;&nbsp;您有&nbsp;<b style="color: red;" id="no_zcsp">3</b>&nbsp;条注册信息待审批
            </label>
            <label class="sylabel">
                <span>[违章取证]</span>&nbsp;&nbsp;您有&nbsp;<b style="color: red;" id="no_wzqz">3</b>&nbsp;条违章信息待审批
            </label>
            <label class="sylabel">
                <span>[危货进港]</span>&nbsp;&nbsp;您有&nbsp;<b style="color: red;" id="no_whjg">3</b>&nbsp;条危货进港信息待审批
            </label>
            <label class="sylabel">
                <span>[危货作业]</span>&nbsp;&nbsp;您有&nbsp;<b style="color: red;" id="no_whzy">3</b>&nbsp;条危货作业信息待审批
            </label>
            <%--<label class="sylabel">--%>
                <%--<span>[注册审批]</span>&nbsp;&nbsp;您有&nbsp;<b style="color: red;">3</b>&nbsp;条信息待审批--%>
            <%--</label>--%>
            <%--<label class="sylabel">--%>
                <%--<span>[注册审批]</span>&nbsp;&nbsp;您有&nbsp;<b style="color: red;">3</b>&nbsp;条信息待审批--%>
            <%--</label>--%>
            <%--<label class="sylabel">--%>
                <%--<span>[注册审批]</span>&nbsp;&nbsp;您有&nbsp;<b style="color: red;">3</b>&nbsp;条信息待审批--%>
            <%--</label>--%>
        </div>
    </div>
</section>
<input type="hidden" id="infoId"/>
<!-- 删除提示模态框（Modal） -->
<div class="modal fade" id="setModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" >
        <div class="modal-content" style="width: 600px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    常用功能设置
                </h4>
            </div>
            <div class="modal-body" style="padding:0;">
                <label class="sylabel">
                    <div  class="smallsetdiv" style="background: #009966;">
                        <i class="fa fa-commenting" aria-hidden="true"></i>
                    </div>
                    <span style="float: left;color: #333;margin-left:10px;">发布通知</span>
                    <div class="mui-switch mui-active" >
                        <div class="mui-switch-handle"></div>
                    </div>
                </label>
                <label class="sylabel">
                    <div class="smallsetdiv" style="background: #0099cc;">
                        <i class="fa fa-ship" aria-hidden="true"></i>
                    </div>
                    <span style="float: left;color: #333;margin-left:10px;">九位码</span>
                    <div class="mui-switch mui-active" >
                        <div class="mui-switch-handle"></div>
                    </div>
                </label>
                <label class="sylabel">
                    <div class="smallsetdiv" style="background: #6666ff;">
                        <i class="fa fa-book" aria-hidden="true"></i>
                    </div>
                    <span  style="float: left;color: #333;margin-left:10px;">通讯录</span>
                    <div class="mui-switch mui-active">
                        <div class="mui-switch-handle"></div>
                    </div>
                </label>
                <label class="sylabel">
                    <div class="smallsetdiv" style="background: #ff6633;">
                        <i class="fa fa-calendar" aria-hidden="true"></i>
                    </div>
                    <span style="float: left;color: #333;margin-left:10px;">考勤管理</span>
                    <div class="mui-switch mui-active" >
                        <div class="mui-switch-handle"></div>
                    </div>
                </label>
                <label class="sylabel">
                    <div class="smallsetdiv" style="background: #00cc66;">
                        <i class="fa fa-file" aria-hidden="true"></i>
                    </div>
                    <span style="float: left;color: #333;margin-left:10px;">文书起草</span>
                    <div class="mui-switch mui-active" >
                        <div class="mui-switch-handle"></div>
                    </div>
                </label>
                <label class="sylabel">
                    <div class="smallsetdiv" style="background: #00c686;">
                        <i class="fa fa-file-text-o" aria-hidden="true"></i>
                    </div>
                    <span style="float: left;color: #333;margin-left:10px;">巡航日志</span>
                    <div class="mui-switch mui-active">
                        <div class="mui-switch-handle"></div>
                    </div>
                </label>
                <label class="sylabel">
                    <div class="smallsetdiv" style="background: #4ea5fc;">
                        <i class="fa fa-search" aria-hidden="true"></i>
                    </div>
                    <span style="float: left;color: #333;margin-left:10px;">船舶查询</span>
                    <div class="mui-switch mui-active" >
                        <div class="mui-switch-handle"></div>
                    </div>
                </label>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
                <button type="button" class="btn btn-primary" onclick="savembbtnstatus()">
                    保存
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script>
    mui.init({
        swipeBack:true //启用右滑关闭功能
    });
</script>
<!-- /.modal -->
</body>
</html>
