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
    <title>数据字典</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../js/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../js/css/zTreeStyle/bluetree.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
    <script src="../js/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="../js/system/datadictionary/datadictionary_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword{
            color:rgb(0,104,177);
            cursor: pointer;
        }
        .bqlabel{
            display: inline-block;
            width: 100%;
            margin-bottom: 0;
            height: 40px;
            padding: 0 10px;
            line-height: 40px;
        }
        .bqlabel span{
            float: left;
        }
        .bqlabel i{
            float: right;
            line-height: 40px;
            cursor: pointer;
            color:white!important;
        }
        .bqlabel:hover{
            background: dodgerblue;
            color:white!important;
        }
        .bqlabelactive{
            background: dodgerblue;
            color:white!important;
        }
    </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
    <section class="content-header">
        <h1>
            数据字典
            <!-- <small>advanced tables</small> -->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-th-large"></i>系统设置</a></li>
            <li><a href="#">数据字典</a></li>
        </ol>
    </section>

    <section class="content">
        <div style="float: left;width:250px;background: white;box-shadow: 0 0 5px #ccc;height:660px;">
            <div style="line-height: 40px;border-bottom: solid 1px #ccc;width:100%;padding:0 10px;text-align: left;font-size: 15px;font-weight: 600">
                <i class="fa fa-list"></i>&nbsp;数据字典列表
            </div>
            <label class="bqlabel bqlabelactive" onclick="showTabledata('PortListPage',1)">
                <span>港口</span>
                <i class="fa fa-edit"></i>
            </label>
            <label class="bqlabel" onclick="showTabledata('LawTypeListPage',1)">
                <span>执法类别</span>
                <i class="fa fa-edit"></i>
            </label>
            <label class="bqlabel" onclick="showTabledata('DangerListPage',1)">
                <span>危货品类别</span>
                <i class="fa fa-edit"></i>
            </label>
            <label class="bqlabel" onclick="showTabledata('UnitListPage',1)">
                <span>重量单位类别</span>
                <i class="fa fa-edit"></i>
            </label>
        </div>
        <div style="margin-left:270px;background: white;box-shadow: 0 0 5px #ccc;">
        <div style="width:100%;height:40px;">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                    <%--<div style="heigth:100%;width:400px;float: left;border-radius:4px;border:solid 1px #cccccc;margin-left: 10px;">--%>
                        <%--<input type="text" class="form-control" style="height:100%;padding:5px;width:330px;border: 0;float: left;"placeholder="请输入申请人姓名" id="listkey"/>--%>
                        <%--<div style="float: left;height:30px;width:28px;">--%>
                            <%--<i class="fa fa-close" style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;" id="clearbtn"></i>--%>
                        <%--</div>--%>
                        <%--<i class="fa fa-search" style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;" onclick="showTabledata('LeaveAndOvertimefinish',1)"></i>--%>
                    <%--</div>--%>
                    <%--<script>--%>
                        <%--$(document).ready(function(){--%>
                            <%--$("#clearbtn").bind("click",function(){--%>
                                <%--$("#clearbtn").hide();--%>
                                <%--$("#listkey").val('');--%>
                            <%--})--%>
                            <%--$("#listkey").bind('input propertychange', function() {--%>
                                <%--if($('#listkey').val()!=''&&$('#listkey').val()!=null){--%>
                                    <%--$("#clearbtn").show();--%>
                                <%--}--%>
                            <%--});--%>
                        <%--})--%>
                    <%--</script>--%>
                        <button type="button" class="btn btn-primary" style="height:32px;float: right;margin-right:10px;" data-toggle="modal"
                                data-target="#addroleModal" onclick="openmodel(1)">
                            添加
                        </button>
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
                <div style="background-color: rgb(255,168,0);height:20px;width:20px;border-radius:10px;text-align: center; color:white;float:left;">!</div>
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
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div><!-- /.modal -->
<!-- 新增、编辑角色模态框（Modal） -->
<div class="modal fade" id="addroleModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content"style="width:500px;height: auto;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="addroletitle">
                    新建
                </h4>
            </div>
            <div class="modal-body" >
                <input type="text" class="form-control" placeholder="请输入数据字典内容" id="role_name"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="addrolebtn">
                    确定
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal" >取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
