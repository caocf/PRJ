<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/9/20
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.model.TAttachment" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.model.TInformation" %>
<%@ page import="com.zjport.util.CommonConst" %>
<%@ page import="java.util.List" %>
<%@ page import="com.zjport.model.TWebName" %>
<%@ page import="com.zjport.model.TWebColumn" %>
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

    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- CK Editor -->
    <script src="../js/ckeditor/ckeditor.js"></script>
    <!-- 页面js -->
    <script src="../js/information/Inform_add_internet.js" type="text/javascript"></script>
    <script src="../js/information/Inform_bmkj.js" type="text/javascript"></script>

</head>
<%
    TInformation info = (TInformation)request.getAttribute("info");
    List<TAttachment> attachmentList = (List<TAttachment>)request.getAttribute("attachmentList");
    String attachName = "";
    for(TAttachment attach : attachmentList) {
        attachName += attach.getStFileName()+";";
    }
    TWebName web = (TWebName)request.getAttribute("web");
    TWebColumn column = (TWebColumn)request.getAttribute("webColumn");
    String approvalUserName = (String)request.getAttribute("approvalUserName");
    String deptName = (String)request.getAttribute("deptName");

    String[] deptArray = deptName.split(";");
%>
<body>

<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_released.png">
        修改网站
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>

</section>

<section class="content" style="padding-top:0; margin-top: 5px;" >
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;">
        <form class="form-horizontal" enctype="multipart/form-data" id="internetForm" action="internetSubmit" method="post">
            <input type="hidden" id="infoId" name="infoId" value="<%=info.getStInformId()%>"/>
            <div class="box-body">
                <div style="background-color: white;width:100%;padding: 10px 0px;;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                    <div class="form-group dropdown" id="web_list">
                        <label  class="col-sm-1 control-label">发布对象<span style="color: red">*</span>：</label>
                        <div class="col-sm-11 dropdown-toggle" id="dropdownMenu1" style="height:27px;color:#ccc"
                             data-toggle="dropdown">
                            <span style="height:27px;margin-top:7px;line-height: 20px;float: left;display: none" id="dxbt">请选择发布网站对象</span>
                            <div onclick="closexl('dropdownMenu1')" style="height:27px;float: left;margin-left:10px;width:120px;color: #333;line-height: 27px;padding:0 5px;background-color: rgb(250,250,250);border: solid 1px #ccc;">
                                <%=web.getStName()%>
                                <span class='removebt' style='font-size: 20px;font-weight:700;line-height: 27px;float: right;cursor: pointer;' onclick=removename(this,'web<%=web.getStWebNameId()%>','dxbt',1)>
                                ×
                                </span>
                            </div>
                        </div>

                        <input type="hidden" name="webName" id="webName" value="<%=web.getStWebNameId()%>"/>
                    </div>
                    <div class="form-group dropdown" id="web_column" style="margin-bottom: 0;border-top: solid 1px #ccc;padding-top:10px;">
                        <label  class="col-sm-1 control-label">文章类目<span style="color: red">*</span>：</label>
                        <div class="col-sm-11 dropdown-toggle" id="dropdownMenu2" style="height:27px;color:#ccc"
                             data-toggle="dropdown">
                            <span style="height:27px;margin-top:7px;line-height: 20px;float: left;display: none" id="zjbt">请选择网站章节</span>
                            <div onclick="closexl('dropdownMenu2')" style="height:27px;float: left;margin-left:10px;width:120px;color: #333;line-height: 27px;padding:0 5px;background-color: rgb(250,250,250);border: solid 1px #ccc;">
                                <%=column.getStWebColumnName()%>
                                <span class='removebt' style='font-size: 20px;font-weight:700;line-height: 27px;float: right;cursor: pointer;' onclick=removename(this,'webcolumn<%=column.getStWebColumnId()%>','zjbt',1)>
                                ×
                                </span>
                            </div>
                        </div>
                        <input type="hidden" name="webColumn" id="webColumn" value="<%=column.getStWebColumnId()%>"/>
                    </div>
                </div>
                <div style="background-color: white;width:100%;padding-top: 10px;box-shadow: 0 0 3px #ccc;">
                    <div class="form-group" >
                        <label for="title" class="col-sm-1 control-label">文章标题<span style="color: red">*</span>：</label>
                        <div class="col-sm-11">
                            <input type="input" class="form-control" style="border: 0;" id="title" name="title" placeholder="请输入文章标题，标题文字不能超过60个中文字符" value="<%=info.getStInformTitle()%>">
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
                        <label for="keyword" class="col-sm-1 control-label">关键字&nbsp;&nbsp;&nbsp;&nbsp;：</label>
                        <div class="col-sm-11">
                            <input type="input" style="border: 0;" class="form-control" id="keyword" name="keyword" placeholder="请输入关键字" value="<%=info.getStInformKeyword()%>">
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 10px;border-top: solid 1px #ccc;padding-top:10px;">
                        <label class="col-sm-1 control-label">添加附件&nbsp;：</label>
                        <div class="btn btn-default btn-file" style="margin-left: 15px;">
                            <i class="fa fa-paperclip"></i> 添加附件
                            <input type="file" name="attachment" id="attachment" multiple="multiple"/>
                        </div>&nbsp;&nbsp;<span id="filenamespan"><%=attachName%></span>
                    </div>
                    <div class="form-group"style="margin-bottom: 0;">
                        <textarea id="editor1" name="editor1" rows="10" cols="80" placeholder="请输入文章内容"><%=info.getStInformContent()%></textarea>
                    </div>
                    <div class="form-group " style="margin-bottom: 0;border-top: solid 1px #ccc;padding:10px 0;">
                        <label  class="col-sm-1 control-label">信息可见性<%--<span style="color: red">*</span>--%>：</label>
                        <div class="col-sm-11 "  style="height:27px;color:#666">
                            <label class="checkbox-inline">
                                <input type="radio" name="optionsRadiosinline" id="onlysee_radio" autocomplete="off"
                                       value="option1" <%if(info.getStScanDepartMiddleId() =="") {%>checked="checked"<%}%>> 仅自己和审批人可见
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="optionsRadiosinline" id="check_radio" autocomplete="off"
                                       value="option2" <%if(info.getStScanDepartMiddleId() !="") {%>checked="checked"<%}%>> 自定义可见部门
                            </label>
                        </div>
                    </div>
                    <div class="form-group" style="background-color: white;margin-bottom: 0;<%if(info.getStScanDepartMiddleId() =="") {%>display: none;<%}%>width:100%;float:left;padding-top:10px;" id="zdybm">
                        <label  class="col-sm-1 control-label"></label>
                        <div class="col-sm-10" style="line-height: 30px;text-align: left;color: #ccc;padding-left: 10px; " id="bmseldiv">
                            <%for(int i = 0; i<deptArray.length; i++) {%>
                            <div style="margin-bottom:10px;height:27px;float: left;margin-left:10px;width:120px;color: #333;line-height: 27px;padding:0 5px;background-color: rgb(250,250,250);border: solid 1px #ccc;">
                                <%=deptArray[i]%>
                            <span class='bmclosebutton' style='font-size: 20px;font-weight:700;line-height: 27px;float: right;cursor: pointer;'onclick='removebmdiv(this)'>
                                ×
                            </span>
                            </div>
                            <%}%>
                        </div>
                        <div  data-toggle="modal" onclick="bmtreemake()"
                              data-target="#bmModal"style='cursor:pointer;height:30px;float: left;margin-left:10px;width:80px;color: #333;line-height: 27px;padding:0 10px;background-color: rgb(250,250,250);border: solid 1px #ccc;text-align: center;'>
                            <span  style='font-size: 15px;line-height: 27px;float: left;cursor: pointer;' ><i class="fa fa-home"></i>&nbsp;&nbsp;选择</span>
                        </div>
                    </div>
                    <div class="form-group" style="float:left;width:100%;background-color: white;margin-bottom: 10px;border-top: solid 1px #ccc;padding:10px 0;">
                        <label for="disabledSelect"  class="col-sm-1 control-label">
                            审批人<span style="color: red">*</span>：
                        </label>
                        <div class="col-sm-2" style="width:200px;">
                            <select id="disabledSelect" name="approvalUser" class="form-control"  style="background-color: rgb(250,250,250);">
                                <option name="choose" selected="selected" value="<%=info.getStApprovalUserId()%>"><%=approvalUserName%></option>
                            </select>
                        </div>
                    </div>
                </div>
            </div><!-- /.box-body -->
            <input type="hidden" name="type" id="type" value="<%=CommonConst.Information_Internet%>"/>
            <div class="box-footer" style="background-color: rgba(0,0,0,0);">
                <div class="pull-left" >
                    <button type="button" class="btn btn-primary" onclick="send()"><i class="fa fa-envelope-o"></i>发布</button>
                    <%--<button class="btn btn-default">预览</button>--%>
                    <%--<button class="btn btn-default">取消</button>--%>
                </div>
                <%--<button class="btn btn-default"><i class="fa fa-times"></i> 发布</button>--%>
            </div><!-- /.box-footer -->
        </form>
    </div><!-- /. box -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="bmModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content"style="width:500px;height: 550px;">
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
                <div style="float: left;height:100%;width:100%;overflow: auto;padding:10px;" class="treediv" id="lxrdiv">
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
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
