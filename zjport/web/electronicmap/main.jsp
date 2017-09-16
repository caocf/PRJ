<%--
  Created by IntelliJ IDEA.
  User: Will
  Date: 2016/11/4
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>电子地图</title>
    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/bluetree.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../js/common/jquery-1.10.2.min.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript" src="../js/electronicmap/main.js"></script>
    <!--地图js-->
    <script type="text/javascript" src="http://172.20.24.105/WebtAPI/wapi.js"></script>
</head>
<body>
<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;">电子地图</b>
    <span style="float: right;font-size: 14px;">视频查看&gt电子地图</span>
</div>
<div style="float: left;width:100%;height:700px;padding:0 10px;margin-bottom:30px;">
    <div style="float: left;width:300px;height:100%;">
        <div style="float: left;width:100%;height:100%;background-color: white;border:solid 1px #ccc;">
            <div style="float: left;height:100%;width:100%;overflow: auto;" class="treediv">

                <ul id="konwledgeTree" class="ztree bluetree"></ul>
            </div>
        </div>
    </div>
    <div style="margin-left:320px;height:100%;background-color: white;border:solid 1px #ccc;" >
        <div id="mapdiv" style="width: 100%;height:100%;float: left"></div>
    </div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="videomodal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false" >
    <div class="modal-dialog" style="width:900px;">
        <div class="modal-content" style="width:900px;height: 550px;">
            <div class="modal-header">
                <button type="button" class="close" onclick="stopView()"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="videotopname">
                </h4>
            </div>
            <div class="modal-body" style="height:480px;padding:0;background: rgb(241,242,246);padding:10px;">
                <%--<button class="btn btn-primary" style="float:left;"><i class="fa fa-scissors"></i>&nbsp;截图</button>--%>
                <div id="videodiv" style="float: left;width: 100%;height:440px;margin-top:5px;position: relative;">
                    <object classid="clsid:D74575FC-EE89-4b05-8851-1A0C417038B9" id="RealTimePlayOcx" width="100%" height="100%" name="RealTimePlayOcx"></object>
                    <div id="download-control" style="padding-top:10px;position:absolute;top:0;left:0;width: 100%;height: 100%;background-color: black;text-align: center;vertical-align: middle;display: none">
                        <span style="color:whitesmoke;">
                            请<a href="../file/download?filePath=plug%2Fsetup.exe" style="color:red">下载</a>并安装控件<br/>
                            并使用IE11浏览
                        </span>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script language="javascript" for="RealTimePlayOcx" event="FireStopRealPlay(iWindowID,iPreviewTime)">   //停止预览监控点时触发事件
$('#videomodal').modal('hide');
</script>
</body>
</html>
