<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/2
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.util.CommonConst" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
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

    <!-- iziModal -->
    <script src="../js/common/iziModal.min.js"></script>
    <!-- ajaxfileupload -->
    <script src="../js/common/ajaxfileupload.js"></script>
    <!-- 页面js -->
    <script src="../js/information/Inform_add_message.js" type="text/javascript"></script>
    <script src="../js/information/Inform_bmkj.js" type="text/javascript"></script>
    <%--<script src="../js/information/Inform_add_internet.js" type="text/javascript"></script>--%>
    <style type="text/css">

        .fileinputdiv {
            width: 100%;
            height: 30px;
            line-height: 30px;
            margin-top: 10px;
        }

        .file {
            filter: alpha(opacity=0); /*IE滤镜，透明度50%*/
            -moz-opacity: 0; /*Firefox私有，透明度50%*/
            opacity: 0; /*其他，透明度50%*/
            width: 60px;
            position: relative;
            top: -30px;
        }

        .filenameinput {
            padding: 0;
            height: 28px;
            width: 300px;
            float: left;
            border: solid 1px #ccc;
            margin-left: 20px;
        }

        .filediv {
            width: 60px;
            height: 30px;
            float: left;
            background-color: rgb(238, 238, 238);
            color: #333;
            margin-left: 5px;
            cursor: pointer;
            text-align: center;
        }
    </style>
</head>
<body>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_released.png">
        发布短信
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>

        <div onclick=""
             style='margin-top:5px;cursor:pointer;height:30px;float: right;margin-right:20px;width:150px;color: #333;line-height: 27px;padding:0 10px;background-color: rgb(250,250,250);border: solid 1px #ccc;text-align: center;'>
            <span style='font-size: 15px;line-height: 27px;float: left;cursor: pointer;' onclick="fileDownload()"><i
                    class="fa fa-file-excel-o"></i>&nbsp;&nbsp;下载Excel模板</span>
        </div>
        <!-- <small>advanced tables</small> -->

    </h1>

</section>

<section class="content" style="padding-top:0; margin-top: 5px;">
    <div class="box box-primary" style="border-top: 0;background-color: rgb(241,242,246);margin-bottom: 15px;">
        <form class="form-horizontal" action="informationSubmit" method="post" id="messageForm">
            <div class="box-body">
                <div style="background-color: white;width:100%;padding: 10px 0px;;box-shadow: 0 0 3px #ccc;margin-bottom: 15px;">
                    <div class="form-group" style="margin-bottom: 0;">
                        <label for="showlxrstr" class="control-label"
                               style="float: left;width: 100px;text-align: left;padding-left:10px;">收件人<span
                                style="color: red">*</span>：</label>

                        <div data-toggle="modal" onclick="showDept()"
                             data-target="#myModal"
                             style='cursor:pointer;height:30px;float: right;margin-right:20px;width:80px;color: #333;line-height: 27px;padding:0 10px;background-color: rgb(250,250,250);border: solid 1px #ccc;text-align: center;'>
                            <span style='font-size: 15px;line-height: 27px;float: left;cursor: pointer;'><i
                                    class="fa fa-user"></i>&nbsp;&nbsp;选择</span>
                        </div>
                        <div style="margin-left: 110px;margin-right:110px;">
                            <input type="input" class="form-control" id="showlxrstr" name="informObject"
                                   style="border: 0;" placeholder="请选择收件人或者直接输入手机号码，不同号码之间用英文逗号隔开">
                        </div>
                        <div data-toggle="modal" onclick="daoru()"
                             data-target="#uploadModal"
                             style='cursor:pointer;margin-top:8px;height:30px;float: right;margin-right:20px;width:80px;color: #333;line-height: 27px;padding:0 10px;background-color: rgb(250,250,250);border: solid 1px #ccc;text-align: center;'>
                            <span style='font-size: 15px;line-height: 27px;float: left;cursor: pointer;'><i
                                    class="fa fa-file-excel-o"></i>&nbsp;&nbsp;导入</span>
                        </div>
                    </div>
                </div>

                <%--<div class="input-group"><span class="input-group-addon">收件人<span style="color: red">*</span>:</span>
                    <input type="text" class="form-control" placeholder="" id="to">
                </div>--%>
                <div class="input-group col-sm-12">
                    <textarea id="editor1" name="editor1" rows="10" cols="80" placeholder="请输入短信内容"
                              style="resize:none;height:400px;width: 100%;box-shadow: 0px 0px 3px #ccc;border:0;padding: 10px;"></textarea>
                </div>
                <div class="form-group "
                     style="margin-bottom: 0;border-top: solid 1px #ccc;padding:10px 0;background-color: white;">
                    <label class="control-label" style="text-align: left;float: left;width:120px;padding-left: 10px;">选择短信模板：</label>
                    <%--<button type="button" id="aaa" class="btn btn-primary" title="模板内容" data-container="body" data-toggle="popover" data-placement="top" data-content="【港航信息】您好 xxxxxxxxx —— 来自港航内部信息">
                        顶部的 Popover
                    </button>--%>
                    <div style="margin-left:110px;height:27px;color:#666">
                        <label style='display: inline-block' class="checkbox-inline" id="templet1" title="模板内容" data-placement="top" data-content="【港航信息】您好 xxxxxxxxx —— 来自港航内部信息">
                            <input type="radio" name="stTemplet" value="1" checked>
                            <span id="type1" >模板一</span>
                        </label>
                        <label style='display: inline-block' class="checkbox-inline" id="templet2" title="模板内容" data-placement="top" data-content="【港航信息】您好 xxxxxxxxx —— 来自港航内部信息">
                            <input type="radio" name="stTemplet" value="2">
                            <span id="type2" >模板二</span>
                        </label>
                    </div>

                </div>
                <div class="form-group "
                     style="margin-bottom: 0;border-top: solid 1px #ccc;padding:10px 0;background-color: white;">
                    <label class="control-label" style="text-align: left;float: left;width:100px;padding-left: 10px;">信息可见性<%--<span style="color: red">*</span>--%>：</label>

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
            </div>
            <!-- /.box-body -->
            <input type="hidden" name="type" id="type" value="<%=CommonConst.Information_Message%>"/>
            <input type="hidden" name="bmids" id="bmids"/>

            <div class="box-footer" style="background-color: rgba(0,0,0,0);">
                <div class="pull-left">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-envelope-o"></i> 发布</button>
                    <%--<button class="btn btn-default"><i class="fa fa-pencil"></i> 取消</button>--%>
                </div>
                <%--<button class="btn btn-default"><i class="fa fa-times"></i> 发布</button>--%>
            </div>
            <!-- /.box-footer -->
        </form>
    </div>
    <!-- /. box -->
</section>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 700px;height: 550px;">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    选择联系人
                </h4>
            </div>
            <div class="modal-body" style="height:430px;padding:0;">
                <div style="float: left;height:100%;width: 350px;border-right: solid 1px rgb(244,244,244);">
                    <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
                        <div style="heigth:100%;width:300px;float: left;border-radius:4px 0 0 4px;border:solid 1px #cccccc;">
                            <input type="text" class="form-control"
                                   style="height:100%;padding:5px;width:260px;border: 0;float: left;"
                                   placeholder="请输入姓名或手机号" id="leftlxrinput"/>
                            <i class="fa fa-search"
                               style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                               onclick="findlxr(1)"></i>
                        </div>
                        <%--<i class="fa fa-angle-down" style="float: left;height:32px;width:28px;line-height: 30px;cursor:pointer;text-align: center;border:solid 1px #ccc;border-left:0;background-color: rgb(250,250,250)"></i>--%>
                    </div>
                    <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);">
                        <div class="phonefz" onclick="phonetab(this)"
                             style="border:solid 1px #ccc;border-top:solid 2px rgb(0,103,172);border-bottom:solid 2px white;">
                            内部人员
                        </div>
                        <%--<div class="phonefz" onclick="phonetab(this)">公众人员</div>--%>
                        <%--<div class="phonefz" onclick="phonetab(this)">群组</div>--%>
                    </div>
                    <div style="float: left;height:320px;width:100%;overflow: auto;padding:10px;" class="treediv"
                         id="lxrdiv">
                        <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
                        <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
                        <ul id="messageTree" class="ztree"></ul>

                    </div>
                </div>
                <div style="float: left;height:100%;width: 100px;border-right: solid 1px rgb(244,244,244);">
                    <div onclick="addlxr()" class="btn btn-default"
                         style="width:74px;float: left;margin-top:120px;margin-left:13px;">添加&nbsp&gt
                    </div>
                    <div onclick='dellxr()' class="btn btn-default"
                         style="width:74px;float: left;margin-top:12px;margin-left:13px;">删除&nbsp&lt
                    </div>
                    <div onclick="clearlxr()" class="btn btn-default"
                         style="width:74px;float: left;margin-top:120px;margin-left:13px;">清空&nbsp&lt&lt
                    </div>
                </div>
                <div style="float: left;height:100%;width: 248px;">
                    <div style="float: left;height:50px;width:100%;border-bottom: solid 1px rgb(244,244,244);padding:10px;">
                        <div style="heigth:100%;width:100%;float: left;border-radius:4px;border:solid 1px #cccccc;">
                            <input type="text" class="form-control"
                                   style="height:100%;padding:5px;width:198px;border: 0;float: left;"
                                   placeholder="请输入姓名或手机号" id="rightlxrinput"/>
                            <i class="fa fa-search"
                               style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                               onclick="findlxr(2)"></i>
                        </div>
                    </div>
                    <div style="float: left;height:370px;width:100%;overflow: auto;padding:5px;" class="treediv"
                         id="addlxrdiv">
                        <ul id="rightTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="sclxr()">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
                <%--<button type="button" class="btn btn-default" style="float: left;">通讯录--%>
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
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
                <h4 class="modal-title">
                    选择部门
                </h4>
            </div>
            <div class="modal-body" style="height:430px;padding:0;">
                <div style="float: left;height:100%;width:100%;overflow: auto;padding:10px;" class="treediv">
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
<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:500px;height: 170px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    选择文件
                </h4>
            </div>
            <%--<input type="file" name="uploadFile" id="uploadFile"/>--%>
            <div class='fileinputdiv'>
                <input type="text" class='filenameinput myfile' id='filepath'
                       readonly="readonly">

                <div class='filediv'>
                    <span class='tianjiaword'>浏览</span> <span id="uploadspan"> <input
                        type="file" style='border:solid 1px #ccc;' id="uploadFile"
                        name="uploadFile" class="file" onchange="selectFile()"/>
															</span>
                </div>
                <div class='filediv' id='delfile'>清空</div>
            </div>
            <div class="modal-footer">
                <button type="button" style="float: left;" class="btn btn-primary" onclick="uploadFile()">
                    确定
                </button>
                <button type="button" style="float: left;" class="btn btn-default" data-dismiss="modal"
                        aria-hidden="true">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
</body>
</html>
