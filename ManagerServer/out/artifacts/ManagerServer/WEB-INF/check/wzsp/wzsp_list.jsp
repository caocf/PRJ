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
    <title>违章取证审批</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <!-- 页面js -->
    <script src="../js/check/wzsp_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword{
            color:rgb(0,104,177);
            cursor: pointer;
        }
        .worddiv{
            width:100px;
            float: left;
            margin-top: 5px;
        }

        .aa
        {
            padding: 0;
            margin: 0;
        }
        .td
        {
            text-align: center;
        }
        .tr{

        }
    </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header">
    <h1>
        违章取证审批
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-check"></i> 业务审核</a></li>
        <li><a href="#">违章取证审批</a></li>
    </ol>
</section>

<section class="content">
    <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter"
                     style="text-align:left;margin-top: 15px;margin-left:10px">
                    <div class="btn-group" style="float: left;width: 90px;">
                        <button type="button" class="btn btn-default dropdown-toggle"
                                data-toggle="dropdown" style="width:100%;height:32px;">
                            <span id="spanstatus">全部状态</span> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu " role="menu">
                            <li><a onclick="option('','spanstatus',this)">全部</a></li>
                            <li><a onclick="option('待审核','spanstatus',this)">待审核</a></li>
                            <li><a onclick="option('已审核','spanstatus',this)">已审核</a></li>
                        </ul>
                    </div>
                    <div class="btn-group" style="float: left;width: 90px;margin-left:10px;">
                        <button type="button" class="btn btn-default dropdown-toggle"
                                data-toggle="dropdown" style="width:100%;height:32px;">
                            <span id="spantype">全部类型</span> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu " role="menu">
                            <li><a onclick="option('','spantype',this)">全部</a></li>
                            <li><a onclick="option('海事','spantype',this)">海事</a></li>
                            <li><a onclick="option('港政','spantype',this)">港政</a></li>
                            <li><a onclick="option('航政','spantype',this)">航政</a></li>
                        </ul>
                    </div>
                    <div style="heigth:100%;width:400px;float: left;margin-left:20px;border-radius:4px;border:solid 1px #cccccc;">
                        <input type="text" class="form-control"
                               style="height:100%;padding:5px;width:330px;border: 0;float: left;" placeholder="请输入船名或案由"
                               id="listkey"/>
                        <div style="float: left;height:30px;width:28px;">
                            <i class="fa fa-close"
                               style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;"
                               id="clearbtn"></i>
                        </div>
                        <i class="fa fa-search"
                           style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                           onclick="Search(1)"></i>
                    </div>
                    <script>
                        $(document).ready(function () {
                            $("#clearbtn").bind("click", function () {
                                $("#clearbtn").hide();
                                $("#listkey").val('');
                            })
                            $("#listkey").bind('input propertychange', function () {
                                if ($('#listkey').val() != '' && $('#listkey').val() != null) {
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
                <table class="table table-hover col-xs-12" id="kqtable" style="border-top:none">
                    <col width="5%"/>
                    <col width="10%"/>
                    <col width="20%"/>
                    <col width="10%"/>
                    <col width="5%"/>
                    <col width="15%"/>
                    <col width="15%"/>
                    <col width="20%"/>
                    <tr style="background-color: rgb(240,245,248);">
                        <th class="td">序号</th>
                        <th class="td">船舶名称</th>
                        <th class="td">案由</th>
                        <th class="td">执法人员</th>
                        <th class="td">所属分类</th>
                        <th class="td">取证时间</th>
                        <th class="td">状态</th>
                        <th class="td">操作</th>
                    </tr>
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
            <div id="nulltablediv"
                 style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
            </div>
        </div>
    </div>
</section>
<input type="hidden" id="infoId"/>
<!-- 删除提示模态框（Modal） -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top:300px;">
        <div class="modal-content" style="width: 400px;">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body" style="line-height: 20px;">
                <div style="background-color: rgb(255,168,0);height:20px;width:20px;border-radius:10px;text-align: center; color:white;float:left;">
                    !
                </div>
                &nbsp;您确定要删除么？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="delbtn">
                    确定
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
<!-- /.modal -->
</body>
</html>
