<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/9/22
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.util.CommonConst" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.model.TInformation" %>
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
    <script src="../js/information/Inform_add_board.js" type="text/javascript"></script>
    <script src="../js/information/Inform_bmkj.js" type="text/javascript"></script>
</head>
<%
    TInformation info = (TInformation)request.getAttribute("info");
    String informObject = info.getStInformObject();
    String[] objectArray = informObject.split(",");

    String approvalUserName = (String)request.getAttribute("approvalUserName");
    String deptName = (String)request.getAttribute("deptName");

    String[] deptArray = deptName.split(";");
%>
<body>
<input type="hidden" id="addOrEdit" name="addOrEdit" value="edit"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_released.png">
        修改情报板
        <!-- <small>advanced tables</small> -->
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>

<section class="content" style="padding-top:0; margin-top: 5px;">
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;">
        <form class="form-horizontal" action="informationSubmit" id="boardForm" method="post">
            <div class="box-body">
                <div style="background-color: white;width:100%;padding: 10px 0px;;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                    <div class="form-group" style="margin-bottom: 0;">
                        <label  class="col-sm-1 control-label">情报板<span style="color: red">*</span>：</label>
                        <div class="col-sm-10" style="padding-left:0;" id="qbbdiv">
                            <% for(int i = 0; i<objectArray.length; i++) {%>
                            <div style="margin-bottom:10px;height:27px;float: left;margin-left:10px;width:120px;color: #333;line-height: 27px;padding:0 5px;background-color: rgb(250,250,250);border: solid 1px #ccc;">
                                <%=objectArray[i]%>
                                <span  style="font-size: 20px;font-weight:700;line-height: 27px;float: right;cursor: pointer;" onclick="removeqbbbqdiv(this)">
                                    ×
                                </span>
                            </div>
                            <%}%>
                        </div>
                        <div  data-toggle="modal" onclick="qbmtreemake()"
                              data-target="#myModal"style='cursor:pointer;height:30px;float: left;margin-left:10px;width:80px;color: #333;line-height: 27px;padding:0 10px;background-color: rgb(250,250,250);border: solid 1px #ccc;text-align: center;'>

                            <span  style='font-size: 15px;line-height: 27px;float: left;cursor: pointer;' ><i class="fa fa-user"></i>&nbsp;&nbsp;选择</span>
                        </div>
                    </div>
                </div>

                <div class="input-group col-sm-12" style="background-color: white;">
                    <div class="form-group " style="margin-bottom: 0;border: solid 1px #ccc;border-bottom:0;padding:10px 0px;">
                        <label class="col-sm-1 control-label">请选择色彩：</label>
                        <div class="dropdown"  style="height:27px;color:#ccc;float: left;width: 100px;">
                            <div type="button" class="btn btn-default dropdown-toggle" id="dropdownMenu1"
                                 data-toggle="dropdown">
                                <%
                                    if(CommonConst.BOARD_COLOR_GREEN.equals(info.getStInformColor())) {
                                %>
                                <div style="width:20px;height:20px;float: left;background-color: rgb(90,192,0);"id="btn_colordiv"></div>
                                <input type="hidden" name="color" id="color" value="<%=CommonConst.BOARD_COLOR_GREEN%>"/>
                                <%
                                } else if(CommonConst.BOARD_COLOR_YELLOW.equals(info.getStInformColor())) {
                                %>
                                <div style="width:20px;height:20px;float: left;background-color: rgb(242,177,0);"id="btn_colordiv"></div>
                                <input type="hidden" name="color" id="color" value="<%=CommonConst.BOARD_COLOR_YELLOW%>"/>

                                <%
                                } else if(CommonConst.BOARD_COLOR_RED.equals(info.getStInformColor())) {
                                %>
                                <div style="width:20px;height:20px;float: left;background-color: rgb(243,89,89);"id="btn_colordiv"></div>
                                <input type="hidden" name="color" id="color" value="<%=CommonConst.BOARD_COLOR_RED%>"/>
                                <%
                                    }
                                %>
                                <span class="caret" style="float: left;margin-left: 10px;margin-top: 10px;"></span>
                            </div>
                            <ul class="dropdown-menu pull-right" role="menu"
                                aria-labelledby="dropdownMenu1" style="width: 100px;margin-top:10px;border: solid 1px #ccc;">
                                <li role="presentation">
                                    <div class='board_color_div' style="background-color: rgb(90,192,0);" onclick="colorchange(this,<%=CommonConst.BOARD_COLOR_GREEN%>)"></div>
                                    <div class='board_color_div' style="background-color: rgb(242,177,0);" onclick="colorchange(this,<%=CommonConst.BOARD_COLOR_YELLOW%>)"></div>
                                    <div class='board_color_div' style="background-color: rgb(243,89,89);" onclick="colorchange(this,<%=CommonConst.BOARD_COLOR_RED%>)"></div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <textarea id="editor1" name="editor1"value="11111" placeholder="请输入文章内容" style="resize:none;height:400px;width: 100%;box-shadow: 0px 0px 3px #ccc;border:0;padding: 10px;"><%=info.getStInformContent()%>
                    </textarea>
                </div>
                <div class="form-group " style="margin-bottom: 0;border-top: solid 1px #ccc;padding:10px 0;background-color: white;">
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
                <div class="form-group" style="background-color: white;margin-bottom: 0;<%if(info.getStScanDepartMiddleId() =="") {%>display: none;<%}%>width:100%;float:left;padding:10px 0;" id="zdybm">
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
            </div><!-- /.box-body -->
            <input type="hidden" name="type" id="type" value="<%=CommonConst.Information_Board%>"/>
            <input type="hidden" name="bmids" id="bmids"/>
            <input type="hidden" name="informObject" id="boards" value="<%=informObject%>"/>
            <div class="box-footer" style="background-color: rgba(0,0,0,0);">
                <div class="pull-left">
                    <button type="button" class="btn btn-primary" onclick="send()"><i class="fa fa-envelope-o"></i> 发布</button>
                    <%--<button class="btn btn-default" onclick="javascript :history.back(-1);"><i class="fa fa-pencil"></i> 取消</button>--%>
                </div>
                <%--<button class="btn btn-default"><i class="fa fa-times"></i> 发布</button>--%>
            </div><!-- /.box-footer -->
        </form>
    </div><!-- /. box -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content"style="width: 700px;height: 550px;">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    选择情报板
                </h4>
            </div>
            <div class="modal-body" style="height:430px;padding:0;">
                <div style="float: left;height:100%;width: 350px;border-right: solid 1px rgb(244,244,244);">
                    <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
                        <div style="heigth:100%;width:300px;float: left;border-radius:4px 0 0 4px;border:solid 1px #cccccc;">
                            <input type="text" id='leftqbbinput'class="form-control" style="height:100%;padding:5px;width:260px;border: 0;float: left;"placeholder="请输入..."/>
                            <i class="fa fa-search" onclick="findqbb(1)"style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"></i>
                        </div>
                        <i class="fa fa-angle-down"  style="float: left;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border:solid 1px #ccc;border-left:0;background-color: rgb(250,250,250)"></i>
                    </div>
                    <div style="float: left;height:370px;width:100%;overflow: auto;padding:10px;" class="treediv" id="lxrdiv">
                        <ul id="leftTree" class="ztree"></ul>
                    </div>
                </div>
                <div style="float: left;height:100%;width: 100px;border-right: solid 1px rgb(244,244,244);">
                    <div onclick="addqbb()" class="btn btn-default" style="width:74px;float: left;margin-top:120px;margin-left:13px;">添加&nbsp&gt</div>
                    <div onclick='delqbb()' class="btn btn-default" style="width:74px;float: left;margin-top:12px;margin-left:13px;">删除&nbsp&lt</div>
                    <div onclick="clearqbb()" class="btn btn-default" style="width:74px;float: left;margin-top:120px;margin-left:13px;">清空&nbsp&lt&lt</div>
                </div>
                <div style="float: left;height:100%;width: 248px;">
                    <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
                        <div style="heigth:100%;width:100%;float: left;border-radius:4px;border:solid 1px #cccccc;">
                            <input type="text" id='rightqbbinput'class="form-control" style="height:100%;padding:5px;width:198px;border: 0;float: left;"placeholder="请输入..."/>
                            <i class="fa fa-search"onclick="findqbb(2)" style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"></i>
                        </div>
                    </div>
                    <div style="float: left;height:370px;width:100%;overflow: auto;padding:5px;" class="treediv"id="addlxrdiv" >
                        <ul id="rightTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="qbbbqmake()">
                    确定
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
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
                <h4 class="modal-title" >
                    选择部门
                </h4>
            </div>
            <div class="modal-body" style="height:430px;padding:0;">
                <div style="float: left;height:100%;width:100%;overflow: auto;padding:10px;" class="treediv" >
                    <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
                    <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
                    <ul id="bmTree" class="ztree"></ul>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="bmmake()">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</div>
</body>
</html>
