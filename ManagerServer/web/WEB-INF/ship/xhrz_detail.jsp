<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>查看</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <link href="../js/common/SlidesJS/css/slidesjs.css" rel="stylesheet" type="text/css"/>
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/common.js"></script>
    <script src="../js/common/SlidesJS/js/jquery.slides.min.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/ship/xhrz_detail.js" type="text/javascript"></script>
    <script type="text/javascript" language="javascript"
            src="http://172.20.24.105/WebtAPI/wapi.js"></script>
    <style type="text/css">
        .worddiv {
            width: 100px;
            float: left;
        }

        .worddivb {
            width: 100px;
            float: left;
            font-weight: bolder;
        }

        .worddiv span {
            color: red;
        }

        .table tr {
            height: 50px !important;
        }

        .table td {
            line-height: 34px !important;
        }

        .imagediv {
            width: 33%;
            float: left;
            height: 140px;
            padding: 5px;
        }

        .imagediv > div {
            border: solid 1px #ccc;
            width: 100%;
            height: 100%;
        }
    </style>
    <style>
        body {
            -webkit-font-smoothing: antialiased;
            font: normal 15px/1.5 "Helvetica Neue", Helvetica, Arial, sans-serif;
            color: #232525;
            padding-top: 70px;
        }

        #slides {
            display: none
        }

        #slides .slidesjs-navigation {
            margin-top: 5px;
        }

        a.slidesjs-next,
        a.slidesjs-previous,
        a.slidesjs-play,
        a.slidesjs-stop {
            background-image: url(image/btns-next-prev.png);
            background-repeat: no-repeat;
            display: block;
            width: 12px;
            height: 18px;
            overflow: hidden;
            text-indent: -9999px;
            float: left;
            margin-right: 5px;
        }

        a.slidesjs-next {
            margin-right: 10px;
            background-position: -12px 0;
        }

        a:hover.slidesjs-next {
            background-position: -12px -18px;
        }

        a.slidesjs-previous {
            background-position: 0 0;
        }

        a:hover.slidesjs-previous {
            background-position: 0 -18px;
        }

        a.slidesjs-play {
            width: 15px;
            background-position: -25px 0;
        }

        a:hover.slidesjs-play {
            background-position: -25px -18px;
        }

        a.slidesjs-stop {
            width: 18px;
            background-position: -41px 0;
        }

        a:hover.slidesjs-stop {
            background-position: -41px -18px;
        }

        .slidesjs-pagination {
            margin: 7px 0 0;
            float: right;
            list-style: none;
        }

        .slidesjs-pagination li {
            float: left;
            margin: 0 1px;
        }

        .slidesjs-pagination li a {
            display: block;
            width: 13px;
            height: 0;
            padding-top: 13px;
            background-image: url(image/pagination.png);
            background-position: 0 0;
            float: left;
            overflow: hidden;
        }

        .slidesjs-pagination li a.active,
        .slidesjs-pagination li a:hover.active {
            background-position: 0 -13px
        }

        .slidesjs-pagination li a:hover {
            background-position: 0 -26px
        }

        #slides a:link,
        #slides a:visited {
            color: #333
        }

        #slides a:hover,
        #slides a:active {
            color: #9e2020
        }

        .navbar {
            overflow: hidden
        }
    </style>
    <style>
        #slides {
            display: none
        }

        .container {
            margin: 0 auto
        }

        /* For tablets & smart phones */
        @media (max-width: 767px) {
            body {
                padding-left: 20px;
                padding-right: 20px;
            }

            .container {
                width: auto
            }
        }

        /* For smartphones */
        @media (max-width: 480px) {
            .container {
                width: auto
            }
        }

        /* For smaller displays like laptops */
        @media (min-width: 768px) and (max-width: 979px) {
            .container {
                width: 724px
            }
        }

        /* For larger displays */
        @media (min-width: 1200px) {
            .container {
                width: 1170px
            }
        }
    </style>
</head>
<%
    String id = (String) request.getAttribute("id");
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<body>
<input type="hidden" value="<%=id%>" id="kqid"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        详情
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>
</section>
<section class="content" style="padding-top: 20px;">
    <div class="box" style="border:solid 1px #cccccc;float:left;width:50%;">
        <div id="generalTabContent" class="t  ab-content">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <div style="float: left;width: 100%;padding: 10px;">
                    <div class="worddivb">概況</div>
                </div>
                <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
                    <tr>
                        <td>
                            <div class="worddiv">人员：</div>
                            <span id="smember"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">工具：</div>
                            <span id="stools"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">区域：</div>
                            <span id="sarea"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">里程：</div>
                            <span id="smiles"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">事件个数：</div>
                            <span id="seventnum"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="worddiv">时间：</div>
                            <span id="sstarttime"></span>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="box" style="border:solid 1px #cccccc;float:left;margin-left:15px;width:48%;margin-bottom:0;">
        <div class="t  ab-content" style="height: 326px;">
            <div style="float: left;width: 100%;padding: 10px;">
                <div class="worddivb">轨迹</div>
            </div>
            <div style="float: left;width:100%;height:100%;border:solid 1px #ccc;" id="mapdiv">
            </div>
        </div>
    </div>
    <div class="box" style="border:solid 1px #cccccc;float:left;width:99%;">
        <div class="t  ab-content" style="height: 390px;overflow-y: auto;" id="eventdiv">
            <div style="float: left;width: 100%;padding: 10px;">
                <div class="worddivb">事件</div>
            </div>
            <table class="table col-xs-12" id="eventtable" style="border-top:none">
                <tr>
                    <th width="20%">
                        记录人
                    </th>
                    <th width="20%">
                        描述
                    </th>
                    <th width="20%">
                        地点
                    </th>
                    <th width="20%">
                        记录时间
                    </th>
                    <th width="20%">
                        操作
                    </th>
                </tr>
            </table>
        </div>
    </div>
</section>

<div class="modal" id="picmodal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabelpicview">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div id="picdiv" class="modal-body" style="padding:0 0 0 0;">
            </div>
        </div>
    </div>
</div>
</body>
</html>
