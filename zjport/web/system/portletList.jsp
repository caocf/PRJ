<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/12/10
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>portlet字典信息</title>

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
    <script src="../js/system/portletList.js" type="text/javascript"></script>
    <style type="text/css">
        .topcolordiv {
            padding: 5px;
            width: 34px;
            height: 34px;
            float: left;
            margin-left: 10px;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
        }

        .topcolordiv > div {
            width: 100%;
            height: 100%;
        }

        .topcolordiv > i {
            font-size: 20px;
            line-height: 24px;
        }
        .topcolordiv:hover{
            background: #bae3be;
        }
    </style>
</head>
<%
    String type = (String)request.getAttribute("type");
%>
<body >
<input type="hidden" id="type" value="<%=type%>"/>
<section class="content-header">
    <h1>
        Portlet字典信息
        <!-- <small>advanced tables</small> -->
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-file-text"></i> 系统设置</a></li>
        <li><a href="#">portlet字典列表</a></li>
    </ol>
</section>
<%--<button type="button" onclick="test()">测试短信机</button>--%>
<section class="content">
    <div class="box">

        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 5px;margin-left: 5px">
                    <div style="heigth:100%;width:400px;float: left;border-radius:4px 0 0 4px;border:solid 1px #cccccc;margin-left: 20px;">
                        <%--<input type="text" class="form-control" style="height:100%;padding:5px;width:330px;border: 0;float: left;"placeholder="请输入标题或内容" id="nbuserselectinput"/>--%>
                        <div style="float: left;height:30px;width:28px;">
                            <i class="fa fa-close" style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;" id="clearbtn"></i>
                        </div>
                        <i class="fa fa-search" style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;" onclick="searchInform()"></i>
                    </div>
                    <button style="float: right;margin-right: 10px;"data-toggle="modal"
                            data-target="#mkModal" class="btn btn-primary">新增</button>
                    <!-- 暂时注释高级查询 -->
                    <%--<i class="fa fa-angle-down" style="float: left;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border:solid 1px #ccc;border-left:0;background-color: rgb(250,250,250)"></i>--%>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="tab-content no-margin" style="margin-top:10px;margin-left:8px;height:600px;">
            <!-- portlet字典列表信息 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-bordered table-hover col-xs-12" id="portletInfo" style="border-top:none">
                    <thead>
                    <tr>
                        <%--<th width="8%">选择</th>--%>
                        <th width="8%" class="center">序号</th>
                        <th width="13%">模块名称</th>
                        <th width="10%">添加人</th>
                        <th width="10%">添加时间</th>
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
<input type="hidden" id="libId"/>
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
<!-- 新增模态框（Modal） -->
<div class="modal fade" id="mkModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:500px;height: 430px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    新增模块
                </h4>
            </div>
            <div class="modal-body" style="height:310px;padding:0;">
                <div style="float: left;height:100%;width:100%;overflow: auto;" class="treediv">
                    <form action="savePortletLib" id="libForm" method="post">
                    <table class="table" style="margin-bottom:0;">
                        <tr>
                            <td style="width:100px;line-height: 34px;">模块名</td>
                            <td>
                                <input class="form-control" id="moduleName" name="moduleName"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">模块ID</td>
                            <td>
                                <input class="form-control" id="moduleId" name="moduleId"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">模块图标</td>
                            <td id="tbtd">
                                <div class="btn-group" style="width: 100%;">
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            data-toggle="dropdown">
                                        <i class="fa fa-bar-chart" ></i> <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu" style="width: 100%;max-height:150px;height:auto;overflow-y: auto;">
                                        <div class="topcolordiv"><i class="fa fa-bar-chart" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-database" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-cube" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-anchor" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-bars" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-book" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-bell-o" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-cloud" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-cogs" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-envelope" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-external-link" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-fire" ></i></div>
                                        <div class="topcolordiv"><i class="fa fa-heart" ></i></div>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">数据来源</td>
                            <td>
                                <input class="form-control" id="url" name="url"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;" >模块描述</td>
                            <td style="height:100px;" colspan="2">
                                <textarea rows="" cols="" style="width:100%;height:100%;resize: none;" id="describe" name="describe"></textarea>
                            </td>
                        </tr>
                    </table>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="addLib()">
                    保存
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<!-- 查看模态框（Modal） -->
<div class="modal fade" id="viewModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:500px;height: 430px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    查看模块
                </h4>
            </div>
            <div class="modal-body" style="height:310px;padding:0;">
                <div style="float: left;height:100%;width:100%;overflow: auto;" class="treediv">
                    <table class="table" style="margin-bottom:0;">
                        <tr>
                            <td style="width:100px;line-height: 34px;">模块名</td>
                            <td id="viewName">
                                <%--<input class="form-control" id="viewName"/>--%>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">模块ID</td>
                            <td id="viewId">
                                <%--<input class="form-control" id="viewId"/>--%>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">模块图标</td>
                            <td id="viewIcon">
                                <button type="button" class="btn btn-default dropdown-toggle"
                                        data-toggle="dropdown">
                                    <i class="fa fa-bar-chart" ></i>
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">数据来源</td>
                            <td id="viewUrl">
                                <%--<input class="form-control" id="url"/>--%>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;" >模块描述</td>
                            <td style="height:100px;" id="viewDescribe">
                                <%--<textarea rows="" cols="" style="width:100%;height:100%;resize: none;" ></textarea>--%>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
</body>
</html>
