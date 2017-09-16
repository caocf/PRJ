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
    <title>角色管理</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/bootpaging.js"></script>
    <!-- 页面js -->
    <script src="../js/system/roleManage.js" type="text/javascript"></script>
</head>
<body>
    <section class="content-header">
        <h1>
            角色管理
            <!-- <small>advanced tables</small> -->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-cog"></i> 系统设置</a></li>
            <li><a href="#">角色管理</a></li>
        </ol>
    </section>

    <section class="content">
        <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 5px;margin-left: 5px">
                        <button class="btn btn-primary"  onclick="addrolemodelmake()">新建角色</button>
                        <div style="heigth:100%;width:400px;float: right;margin-right:20px;border-radius:4px;border:solid 1px #cccccc;margin-left: 20px;">
                            <input type="text" class="form-control" style="height:100%;padding:5px;width:330px;border: 0;float: left;"placeholder="请输入角色名称" id="rolenameselectinput"/>
                            <div style="float: left;height:30px;width:28px;">
                                <i class="fa fa-close" style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;" id="clearbtn"></i>
                            </div>
                            <i class="fa fa-search" style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;" onclick="showInfoInTable('../manage/rolelist',1)"></i>
                        </div>
                        <script>
                            $(document).ready(function(){
                                $("#clearbtn").bind("click",function(){
                                    $("#clearbtn").hide();
                                    $("#rolenameselectinput").val('');
                                })
                                $("#rolenameselectinput").bind('input propertychange', function() {
                                    if($('#rolenameselectinput').val()!=''&&$('#rolenameselectinput').val()!=null){
                                        $("#clearbtn").show();
                                    }
                                });
                            })
                        </script>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content no-margin" style="margin-top:10px;margin-left:8px;height:600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-bordered table-hover col-xs-12" id="rolelist-info" style="border-top:none">
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
            <div class="modal-content"style="width:500px;height: 550px;">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="addroletitle">
                        查看角色
                    </h4>
                </div>
                <div class="modal-body" style="height:430px;padding:0;">
                    <div style="float: left;height:100%;width:100%;overflow: auto;padding:10px;" class="treediv" >
                        <div style="height:35px;width:100%;float:left;margin-top:20px;">
                            <div style="width:100px;float:left;height:100%;line-height: 35px;vertical-align: middle;padding-left:20px;color:#666666;">角色名称：</div>
                            <input  style="height:100%;width:300px;float:left;border-radius: 4px;" id="role_name" type="text"/>
                        </div>
                        <div style="height:338px;width:100%;background: #ffffff;margin-top:15px;float:left;">
                            <div style="width:100px;float:left;height:100%;line-height: 35px;vertical-align: middle;padding-left:20px;color:#666666;">角色权限：</div>
                            <div style="float:left;height:338px;width:300px; border:1px solid #ececec;overflow: auto;">
                        <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
                        <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
                                    <ul id="addroleTree" class="ztree"></ul>
                            </div>
                        </div>

                    </div>
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
    <!-- 查看角色模态框（Modal） -->
    <div class="modal fade" id="checkroleModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content"style="width:500px;height: 550px;">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        查看角色
                    </h4>
                </div>
                <div class="modal-body" style="height:430px;padding:0;">
                    <div style="float: left;height:100%;width:100%;overflow: auto;padding:10px;" class="treediv" >
                        <div style="height:35px;width:100%;float:left;margin-top:20px;">
                            <div style="width:100px;float:left;height:100%;line-height: 35px;vertical-align: middle;padding-left:20px;color:#666666;">角色名称：</div>
                            <input  style="height:100%;width:300px;float:left;border: 0;" id="checkinput" readonly="readonly" type="text"/>
                        </div>
                        <div style="height:288px;width:100%;background: #ffffff;margin-top:15px;float:left;">
                            <div style="width:100px;float:left;height:100%;line-height: 35px;vertical-align: middle;padding-left:20px;color:#666666;">角色权限：</div>
                            <div style="float:left;height:288px;width:300px; border:1px solid #ececec;overflow: auto;">
                                    <ul id="checkroleTree" class="ztree"></ul>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" >关闭
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</body>
</html>
