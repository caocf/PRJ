<%--
  Created by IntelliJ IDEA.
  User: Will
  Date: 2016/9/27
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>电子巡航</title>
    <%--<link href="http://vjs.zencdn.net/5.8/video-js.css" rel="stylesheet">--%>
    <!-- If you'd like to support IE8 -->
    <%--<script src="http://vjs.zencdn.net/ie8/1.1.2/videojs-ie8.min.js"></script>--%>
    <script type="text/javascript" src="../common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="../js/smart/cruising.js"></script>
    <!--地图js-->
    <script type="text/javascript" src="http://172.20.24.105/WebtAPI/wapi.js"></script>
    <script language="javascript">
        var PlayOCXObj;
        var para = new Array();
        var latlng = new Array();//摄像头经纬度信息集合
        var index = 0;
        var int = 0;
        var timeOut = Number(${timeOut});
        $(document).ready(function () {
            try {
                PlayOCXObj = document.getElementById("RealTimePlayOcx");
                PlayOCXObj.SetCapturParam('D:\\temp', "0");
                PlayOCXObj.SetWndNum(1);//设置播放窗口数量
            } catch (e) {
                console.log(e);
                $("#download-control").show();
            }
            <c:forEach items="${perviewParam}" var="p">
            para.push('${p[0]}');
            latlng.push({"lat":${p[1]}, "lng":${p[2]}})
            </c:forEach>
            console.log(para);
            console.log(latlng);
            var vList = $("#video_list");
            console.log("time out:"+timeOut);
            vList.html("");
            for (var i = 0; i < para.length; i++) {
                var p = para[i];
                var start = p.indexOf("<CameraName>");
                var end = p.indexOf("</CameraName>");
                var name = p.substr(start + 12, end - start + 11);
                var li = "<label id='videolabel" + i + "' onclick='changeVideo(" + i + ")'><i class='fa fa-video-camera'></i>&nbsp;&nbsp;" + name + "</label>";
                vList.append(li);
            }
            vList.css('width', (para.length * 200 + 20) + 'px');
            vList.find('label').click(function () {
                vList.find('label').removeClass('label_hover');
                $(this).addClass('label_hover');
            })
            try {
                setTimeout(function () {
                    var ret = PlayOCXObj.StartTask_Preview(para[0]);
//                    alert(ret);
                }, 2000);
                int = setInterval(carousel, timeOut * 1000);
            } catch (e) {
                console.log(e);
                $("#download-control").show();
            }
            shipOrbitmapmake();
        });

        function changeVideo(thisIndex) {
            if (para.length == 1) {
                return;
            }
            $("#video_list").find('label').removeClass('label_hover');
            $("#videolabel" + thisIndex).addClass('label_hover');
            window.clearInterval(int);
            var p = para[thisIndex];
            var start = p.indexOf("<CameraName>");
            var end = p.indexOf("</CameraName>");
            var name = p.substr(start + 12, end - start + 11);
            for(var key in videomap){
                videomap[key].setUrl("../image/smart/map_ic_movie_normal.png");
            }
            videomap[name].setUrl("../image/smart/map_ic_movie_pressed.png");
            shipOrbitmap.panToLonLat(videomap[name].getLonLat());
            PlayOCXObj.StartTask_Preview(para[thisIndex]);
            index=thisIndex;
            int = setInterval(carousel, timeOut * 1000);
        }
        function carousel() {
            if (para.length == 1) {
                return;
            }
            index++;
            var x = index % para.length;
            $("#video_list").find('label').removeClass('label_hover');
            $("#videolabel" + x).addClass('label_hover');
            var p = para[x];
            var start = p.indexOf("<CameraName>");
            var end = p.indexOf("</CameraName>");
            var name = p.substr(start + 12, end - start + 11);
            for(var key in videomap){
                videomap[key].setUrl("../image/smart/map_ic_movie_normal.png");
            }
            videomap[name].setUrl("../image/smart/map_ic_movie_pressed.png");
            shipOrbitmap.panToLonLat(videomap[name].getLonLat());
            var backInt = PlayOCXObj.StartTask_Preview(para[x]);
        }
        function finishCuries() {
//            history.back(-1);
            $('#endmodal').modal('show');
            PlayOCXObj.StopAllPreview();
            window.clearInterval(int);
            $("#videoDiv").hide();
        }
        function cancel_b(){
            changeVideo(index % para.length);
            $("#videoDiv").show();
        }
    </script>
    <style>
        .body_div {
            margin: 10px;;
        }

        .float-left {
            float: left;
        }

        .float-right {
            float: right;
        }

        #chart_list ul li {
            margin: 20px;
        }

        #chart_list ul li div {
            background-color: white;
        }

        #video_list label {
            float: left;
            display: inline-block;
            margin-bottom: 0;
            width: 200px;
            height: 50px;
            padding: 0 20px;
            line-height: 50px;
            text-align: center;
            text-align: left;
        }

        #video_list label:hover {
            background: #0067ac;
            color: white;
        }

        .label_hover {
            background: #0067ac;
            color: white;
        }

        li {
            list-style: none;
            margin: 0 !important;
            padding: 5px 0;
        }
    </style>
</head>
<body>
<div class="body_div" style="margin: 0">
    <input type="hidden" id="cruiseId" value="${TCurise.stCuriseMiddleId}">
    <input type="hidden" id="cruiseMiddleId" value="${TCurise.stCuriseMiddleId}">

    <div style="float: left;width:100%;padding:0 10px;height:800px;">
        <div style="width:100%;float:left;line-height: 50px;text-align: left;">
            <b style="font-size: 20px;">电子巡航</b>
            <span style="float: right;font-size: 14px;">我的工作台&gt;智慧监管&gt;电子巡航</span>
        </div>
        <div style="width:800px;height:auto;float: left;">
            <div
                    style="float:left;position:relative;height: 600px;width:100%;text-align: center"
                    id="videoDiv">
                <OBJECT classid="clsid:D74575FC-EE89-4b05-8851-1A0C417038B9" id="RealTimePlayOcx" width="100%" style="z-index: 3"
                        height="100%"
                        name="RealTimePlayOcx"></OBJECT>
                <div id="download-control"
                     style="position:absolute;top:0;left:0;padding-top:10px;width: 800px;height: 600px;background-color: black;text-align: center;vertical-align: middle;display: none">
                        <span style="color:whitesmoke;">
                            请<a href="../file/download?filePath=plug%2Fsetup.exe" style="color:red">下载</a>并安装控件<br/>
                            并使用IE11浏览
                        </span>
                </div>
                <%--<video style="width: 100%;height: 800px;" id="video" crossorigin="anonymous" >--%>
                <%--&lt;%&ndash;<source src="http://www.w3school.com.cn/i/movie.ogg" >&ndash;%&gt;--%>
                <%--<source src="../smart/android.webm" >--%>
                <%--</video>--%>
                <%--<video id="video" class="video-js" controls preload="auto" width="800" height="600"--%>
                <%--poster="" data-setup="{}">--%>
                <%--&lt;%&ndash;<source src="http://www.w3school.com.cn/i/movie.ogg" type='video/ogg'>&ndash;%&gt;--%>
                <%--<source src="../smart/android.webm" type='video/webm'>--%>
                <%--<track kind="captions" src="" srclang="en" label="English"/>--%>
                <%--<p class="vjs-no-js">--%>
                <%--To view this video please enable JavaScript, and consider upgrading to a web browser that--%>
                <%--<a href="http://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>--%>
                <%--</p>--%>
                <%--</video>--%>

                <%--<script src="http://vjs.zencdn.net/5.8/video.js"></script>--%>
            </div>
            <div class="float-left"
                 style="overflow-x:auto;background-color: white;margin-top: 10px;width:100%;height: 70px ">
                <div style="height:100%;" id="video_list">
                </div>
            </div>
        </div>
        <div style="margin-top:52px;margin-left:800px;background: white;height:680px;" >
            <div style="float: left;width:100%;height:100%;" id="shipOrbitmap_div"></div>
        </div>
            <button class="float-left btn btn-default"  style="margin-top:10px;" id="finish" onclick="finishCuries()">
                <i class="fa fa-stop"></i>&nbsp;结束巡航
            </button>
            <button class="float-left btn btn-primary"  style="margin-top:10px;margin-left:10px;" data-toggle="modal"
                    data-target="#videomodal">
                新建事件
            </button>
    </div>
    <div id="chart_list" class="float-left" style="display:none;width:100%;overflow-x: auto;">
        <div style="line-height: 40px;height:40px;width:100%;float: left;padding:0 10px;">
            记录的事件
        </div>
        <div style="padding:10px;width:100%;float: left;height:330px;" >
            <div style="height:100%;float: left;width: auto;" id="sjdiv">
            </div>
        </div>
    </div>
</div>
<!-- 新建事件模态框（Modal） -->
<div class="modal fade" id="videomodal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:400px;">
        <div class="modal-content" style="width:400px;height: 380px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    新建事件
                </h4>
            </div>
            <div class="modal-body" style="height:250px;padding:10px;">
                <div class="float-left"
                     style="width: 100%;height: 100%;text-align: center;padding:10px;background: white;">
                    <div style="width: 100%;height: 200px;text-align: center" id="output">
                        <img style="width: 100%;height: 90%;" id="upload_img" src="" alt="此处应该是张图片">
                        <br/>
                        <input type="file" id="upload_file" style="display: none">
                    </div>
                    <div class="float-left" style="width: 100%;margin-bottom: 10px;">
                        <input type="text" style="float: left;border:0" id="photo_context" placeholder="请在此处输入说明"/>
                    </div>
                    <%--<button class="float-left btn btn-primary" id="">保存</button>--%>
                    <%--<button class="float-left btn btn-default" style="margin-left: 10px;" id="cancel_img">取消</button>--%>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="saveCruisePhoto">
                    保存
                </button>
                <button type="button" class="btn btn-default" id="cancel_b" data-dismiss="modal">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- 结束模态框（Modal） -->
<div class="modal fade" id="endmodal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:400px;">
        <div class="modal-content" style="width:400px;height: 280px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    结束巡航
                </h4>
            </div>
            <div class="modal-body" style="height:150px;padding:10px;">
                <div id="content_div" style="">
                    <p>总体情况</p>
                    <textarea placeholder="请输入本次巡航总体情况" id="cruiseContent" style="resize: none;width: 100%;height: 100px;"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="submit_b">
                    确定
                </button>
                <button type="button" class="btn btn-default" onclick="cancel_b()" data-dismiss="modal">取消
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script language="javascript" for="RealTimePlayOcx" event="FireStopRealPlay(iWindowID,iPreviewTime)">   //停止预览监控点时触发事件
//console.log(int);
//window.clearInterval(int);
//$("#content_div").show();
</script>
</body>
</html>
