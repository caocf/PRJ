<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="com.zjport.util.CommonConst" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.model.TOrg" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <title>视频查看</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/bluetree.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- CK Editor -->
    <script src="../js/ckeditor/ckeditor.js"></script>
    <!-- 页面js -->
    <script src="../js/common/bootpaging.js"></script>
    <script src="../js/smart/videocheck.js"></script>
    <!--地图js-->
    <script type="text/javascript" src="http://172.20.24.105/WebtAPI/wapi.js"></script>
    <script type="text/javascript" src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
    <style type="text/css">
        .clickword {
            color: rgb(31, 116, 180);
            cursor: pointer;
        }

        .qzdiv {
            float: left;
            width: 100%;
            height: 40px;
            line-height: 40px;
            padding-left: 20px;
            cursor: pointer;
        }

        #qzbq {
            float: left;
            width: 100%;
            display: none;
        }

        #leftTree li a {
            display: -moz-inline-box;
        }

        #leftTree .node_name {
            float: left;
        }
        #namelistdiv{
            background: white;
            position: absolute;
            width:100%;
            max-height:250px;
            height: auto;
            overflow-y: auto;
            top:40px;
            box-shadow: 0 0 4px #666;
            padding:10px 0;
            display: none;
            z-index: 1000;
        }
        #namelistdiv label{
            display: inline-block;
            width:100%;
            float: left;
            height:30px;
            line-height: 20px;
            padding: 5px;
            cursor: pointer;
        }
        #namelistdiv label:hover{
            color:white;
            background: royalblue;
        }
        abbr{
            border-bottom:0!important;
            cursor:pointer!important;
        }
        .poplabel{
            width: 100%;
            height:30px;
            display: inline-block;
            line-height: 30px;
            padding: 0 10px;
            text-align: left;
        }
        .poplabel span{
            width: 100px;
            display: inline-block;
        }
    </style>
</head>
<%
    String area = "330000";
    TOrg org = (TOrg) request.getSession().getAttribute("session_org");
    if (org == null) {
        System.out.println("chedan!");
    } else {
        area = org.getStOrgArea();
        if (StringUtils.isNotEmpty(area)) {
            area = area.substring(0, 4) + "00";
        } else {
            area = "330000";
        }
    }
%>
<body>
<input type="hidden" name="type" id="type"/>
<input type="hidden" name="structureId" id="structureId"/>
<input type="hidden" name="groupId" id="groupId"/>
<input type="hidden" value="<%=area%>" id="userareaNo"/>
<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;">视频查看</b>
</div>
<div style="float: left;width:100%;height:700px;padding:0 10px;">
    <div style="float: left;width:350px;height:100%;padding-right:20px;">
        <div style="float: left;width:100%;height:100%;background-color: white;border:solid 1px #ccc;">
            <select class="form-control" style="float: left;
                                                width:100px;
                                                margin-top:10px;
                                                margin-left:10px;
                                                height:30px;
                                                padding:0;
                                                line-height: 30px;" id="treetype">
                <option value="2" selected="selected">航道</option>
                <option value="3">航段</option>
                <option value="7">摄像头</option>
            </select>
            <div style="position:relative;width:150px;float: left;border-radius:4px 0 0 4px;border:solid 1px #cccccc;margin-left: 20px;margin-top:10px;">
                <input type="text" class="form-control"
                       style="height:100%;padding:5px;width:120px;border: 0;float: left;" placeholder="请输入名称"
                       id="keyinput" />
                <i class="fa fa-search"
                   style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                   onclick=""></i>
                <div id='namelistdiv'>
                </div>
            </div>
            <div style="float: left;height:600px;width:100%;overflow: auto;" class="treediv">
                <ul id="messageTree" class="ztree bluetree"></ul>
            </div>
        </div>
    </div>
    <div style='height:100%;background-color: white;border:solid 1px #ccc;margin-left:350px;' >
        <div style="width: 100%;height:100%;float: left;" id="shipOrbitmap_div"></div>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="addzbModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" style="width:500px;height: 330px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    定位修改视频点信息
                </h4>
            </div>
            <div class="modal-body" style="height:210px;padding:0;">
                <div style="float: left;height:100%;width:100%;overflow: auto;" class="treediv">
                    <table class="table" style="margin-bottom:0;">
                        <tr>
                            <td style="width:100px;line-height: 34px;">采集坐标：</td>
                            <td>
                                <input class="from-control" id="lonlatinput" readonly='readonly' style="width: 60%;height:34px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">地址：</td>
                            <td >
                                <input class="from-control" id="addressinput" style="width: 60%;height:34px;" placeholder="请输入地址"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">所属航道：</td>
                            <td id="tbtd">
                                <select class="form-control" style="float: left;
                                                width:40%;
                                                height:34px;
                                                padding:0;
                                                line-height: 34px;" id="checkhd">
                                </select>
                                <select class="form-control" style="float: left;
                                                width:40%;
                                                height:34px;
                                                padding:0;
                                                margin-left:10px;
                                                line-height: 34px;" id="checkhduan">
                                    <option value="-1" selected="selected">请选择航段</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px;line-height: 34px;">视频类型：</td>
                            <td >
                                <select class="form-control" style="float: left;
                                                width:40%;
                                                height:34px;
                                                padding:0;
                                                line-height: 34px;" id="videotype">
                                    <option selected="selected" value="4">航段</option>
                                    <option value="5">码头</option>
                                    <option value="6">观测点</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="updatechannelcamera()">
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
<!-- 模态框（Modal） -->
<div class="modal fade" id="videomodal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog" style="width:600px;">
        <div class="modal-content" style="width:600px;height: 550px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    视频
                </h4>
            </div>
            <div class="modal-body" style="height:480px;padding:0;background: rgb(241,242,246);padding:10px;">
                <%--<button class="btn btn-primary" style="float:left;"><i class="fa fa-scissors"></i>&nbsp;截图</button>--%>
                <div id="videodiv" style="float: left;width: 100%;height:450px;margin-top:5px;position: relative;">
                    <object classid="clsid:D74575FC-EE89-4b05-8851-1A0C417038B9" id="RealTimePlayOcx" width="100%"
                            height="100%" name="RealTimePlayOcx"></object>
                    <div id="download-control"
                         style="padding-top:10px;position:absolute;top:0;left:0;width: 100%;height: 100%;background-color: black;text-align: center;vertical-align: middle;display: none">
                        <span style="color:whitesmoke;">
                            请<a href="../file/download?filePath=plug%2Fsetup.exe" style="color:red">下载</a>并安装控件<br/>
                            并使用IE11浏览
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script language="javascript" for="RealTimePlayOcx" event="FireStopRealPlay(iWindowID,iPreviewTime)">   //停止预览监控点时触发事件
</script>
</body>
</html>
