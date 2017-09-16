<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/2
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息发布</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- CK Editor -->
    <script src="../js/ckeditor/ckeditor.js"></script>
    <!-- 页面js -->
    <script src="../js/information/Inform_add_internet.js" type="text/javascript"></script>
    <script src="../js/information/Inform_bmkj.js" type="text/javascript"></script>

</head>
<body>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_released.png">
        发布网站
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>

</section>

<section class="content" style="padding-top:0; margin-top: 5px;">
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;">
        <form class="form-horizontal" enctype="multipart/form-data" id="internetForm" action="internetSubmit"
              method="post">
            <div class="box-body">
                <div style="background-color: white;width:100%;padding: 10px 0px;;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                    <div class="form-group dropdown" id="web_list">
                        <label class="control-label"
                               style="float: left;width: 100px;text-align: left;padding-left:10px;">发布对象<span style="color: red">*</span>：</label>

                        <div class="dropdown-toggle" id="dropdownMenu1" style="margin-left:110px;height:27px;color:#ccc"
                             data-toggle="dropdown">
                            <span style="height:27px;margin-top:7px;line-height: 20px;float: left"
                                  id="dxbt">请选择发布网站对象</span>
                        </div>
                        <%-- <ul class="dropdown-menu col-sm-11 " role="menu" aria-labelledby="dropdownMenu1">
                            <li role="presentation">
                                <a role="menuitem" tabindex="-1" class="onescheckxl" onclick="addname(this,1)" id="wz1">网站1</a>
                            </li>
                            <li role="presentation">
                                <a role="menuitem" tabindex="-1" class="onescheckxl" onclick="addname(this,1)" id="wz2">网站2</a>
                            </li>
                        </ul>--%>
                        <input type="hidden" name="webName" id="webName"/>
                    </div>
                    <div class="form-group dropdown" id="web_column"
                         style="margin-bottom: 0;border-top: solid 1px #ccc;padding-top:10px;">
                        <label class="control-label"
                               style="float: left;width: 100px;text-align: left;padding-left:10px;">文章类目<span style="color: red">*</span>：</label>

                        <div class="dropdown-toggle" id="dropdownMenu2" style="margin-left:110px;height:27px;color:#ccc"
                             data-toggle="dropdown">
                            <span style="height:27px;margin-top:7px;line-height: 20px;float: left"
                                  id="zjbt">请选择网站章节</span>
                        </div>
                        <%--<ul class="dropdown-menu col-sm-11 " role="menu" aria-labelledby="dropdownMenu2">
                            <li role="presentation">
                                <a role="menuitem" tabindex="-1" class="onescheckxl" onclick="addname(this,2)" id="zj1">章节1</a>
                            </li>
                            <li role="presentation">
                                <a role="menuitem" tabindex="-1" class="onescheckxl" onclick="addname(this,2)" id="zj2">章节2</a>
                            </li>
                        </ul>--%>
                        <input type="hidden" name="webColumn" id="webColumn"/>
                    </div>
                </div>
                <div style="background-color: white;width:100%;padding-top: 10px;box-shadow: 0 0 3px #ccc;">
                    <div class="form-group">
                        <label for="title" class="control-label"
                               style="float: left;width: 100px;text-align: left;padding-left:10px;">文章标题<span style="color: red">*</span>：</label>

                        <div style="margin-left: 110px;">
                            <input type="input" class="form-control" style="border: 0;" id="title" maxlength="60" name="title"
                                   placeholder="请输入文章标题，标题文字不能超过60个中文字符">
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
                        <label for="keyword" class="control-label"
                               style="float: left;width: 100px;text-align: left;padding-left:10px;">关键字&nbsp;&nbsp;&nbsp;&nbsp;：</label>

                        <div style="margin-left: 110px;">
                            <input type="input" style="border: 0;" class="form-control" id="keyword" maxlength="30" name="keyword"
                                   placeholder="请输入关键字">
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
                        <label class="control-label"
                               style="float: left;width: 100px;text-align: left;padding-left:10px;">添加附件&nbsp;：</label>
                        <div class="btn btn-default btn-file" style="margin-left: 15px;">
                            <i class="fa fa-paperclip"></i> 添加附件
                            <input type="file" name="attachment" id="attachment" multiple="multiple"/>
                        </div>
                        &nbsp;&nbsp;<span id="filenamespan"></span>
                    </div>
                    <div class="form-group" style="margin-bottom: 0;">
                        <textarea id="editor1" name="editor1" rows="10" cols="80" placeholder="请输入文章内容"></textarea>
                    </div>
                    <div class="form-group "
                         style="margin-bottom: 0;border-top: solid 1px #ccc;padding:10px 0;background-color: white;">
                        <label class="control-label"
                               style="text-align: left;float: left;width:100px;padding-left: 10px;">信息可见性<%--<span style="color: red">*</span>--%>：</label>

                        <div style="margin-left:110px;height:27px;color:#666">
                            <label class="checkbox-inline">
                                <input type="radio" name="optionsRadiosinline" id="onlysee_radio" autocomplete="off"
                                       value="option1" checked> 仅自己和审批人可见
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="optionsRadiosinline" id="check_radio" autocomplete="off"
                                       value="option2"> 自定义可见部门
                            </label>
                        </div>
                    </div>
                    <div class="form-group"
                         style="background-color: white;margin-bottom: 0;display: none;width:100%;float:left;padding:10px 0;"
                         id="zdybm">
                        <label class="control-label" style="width: 100px;float: left;"></label>

                        <div data-toggle="modal" onclick="bmtreemake()"
                             data-target="#bmModal"
                             style='cursor:pointer;height:30px;float: right;margin-right:20px;width:80px;color: #333;line-height: 27px;padding:0 10px;background-color: rgb(250,250,250);border: solid 1px #ccc;text-align: center;'>
                        <span style='font-size: 15px;line-height: 27px;float: left;cursor: pointer;'><i
                                class="fa fa-home"></i>&nbsp;&nbsp;选择</span>
                        </div>
                        <div style="margin-left:110px;margin-right:110px;line-height: 30px;text-align: left;color: #ccc;padding-left: 10px; "
                             id="bmseldiv">
                            请选择部门，可多选
                        </div>
                    </div>
                    <div class="form-group"
                         style="float:left;width:100%;background-color: white;margin-bottom: 10px;border-top: solid 1px #ccc;padding:10px 0;">
                        <label for="disabledSelect" class="control-label"
                               style="text-align: left;float: left;width:100px;padding-left: 10px;">
                            审批人<span style="color: red">*</span>：
                        </label>
                        <div class="col-sm-2" style="width:200px;">
                            <select id="disabledSelect" name="approvalUser" class="form-control"
                                    style="background-color: rgb(250,250,250);">
                                <option name="choose" selected="selected" value="0">请选择</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.box-body -->
            <input type="hidden" name="type" id="type" value="<%=CommonConst.Information_Internet%>"/>

            <div class="box-footer" style="background-color: rgba(0,0,0,0);">
                <div class="pull-left">
                    <button type="button" class="btn btn-primary" onclick="send()"><i class="fa fa-envelope-o"></i>发布
                    </button>
                    <%--<button class="btn btn-default">预览</button>--%>
                    <%--<button class="btn btn-default">取消</button>--%>
                </div>
                <%--<button class="btn btn-default"><i class="fa fa-times"></i> 发布</button>--%>
            </div>
            <!-- /.box-footer -->
        </form>
    </div>
    <!-- /. box -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="bmModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:500px;height: 550px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    选择部门
                </h4>
            </div>
            <div class="modal-body" style="height:430px;padding:0;">
                <div style="float: left;height:100%;width:100%;overflow: auto;padding:10px;" class="treediv"
                     id="lxrdiv">
                    <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
                    <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
                    <ul id="bmTree" class="ztree"></ul>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="bmmake()">
                    确定
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
</body>
</html>
