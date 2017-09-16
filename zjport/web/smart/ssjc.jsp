<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/23
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.zjport.model.TOrg" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<!DOCTYPE html>
<html>
<head>
    <title>实时监测</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <!-- Bootstrap 3.3.4 -->
    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!-- FontAwesome 4.3.0 -->
    <link href="../css/common/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="../css/common/bluetree.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="../js/smart/ssjc.js" type="text/javascript"></script>
    <!--地图js-->
    <script type="text/javascript" src="http://172.20.24.105/WebtAPI/wapi.js"></script>
    <%--<script type="text/javascript" src="../js/common/openlayers.js"></script>--%>
    <script type="text/javascript" src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
    <!--css-->
    <style type="text/css">
        .layerbtn, .shipimg {
            float: left;
            height: 100%;
            width: 100%;
            border-right: solid 1px #ccc;
            line-height: 20px;
            text-align: center;
            padding: 0 10px;
            cursor: pointer;
            color: #666;
        }

        .layerbtnblue {
            color: rgb(0, 135, 239) !important;
        }

        #xqtopname span {
            color: white;
        }

        .whdiv {
            width: 16px;
            height: 16px;
            background: #c9cccf;
            border-radius: 8px;
            text-align: center;
            line-height: 16px;
            color: white;
            cursor: pointer;
            float: right;
            margin-top: 2px;
        }

        .ssjgshipspan {
            margin-right: 5px;
            float: right;
            color: red
        }

        .ssjgshipspan-green {
            margin-right: 5px;
            float: right;
            color: limegreen;
        }

        .lefttd {
            background: rgb(243, 248, 254);
            border-right: solid 1px #f4f4f4;
        }

        .cbgkdiv {
            height: 40px;
            padding: 0 10px;
            line-height: 40px;
            text-align: center;
            width: auto;
            float: left;
            font-size: 17px;
        }

        .togglebtn {
            float: right;
            margin-right: 20px;
            margin-top: 10px;
            cursor: pointer;
        }

        .togglebtn:first-of-type {
            display: none;
        }

        .cbgklefttd {
            border-right: solid 1px #f4f4f4;
        }

        #cbgktable h4 {
            font-weight: 600;
        }

        div.checkbox {
            padding: 4px 10px;
            width: 150px;
            margin: 0;
        }

        #shiptypeUL label {
            line-height: 30px;
        }

        .clickword {
            color: rgb(31, 116, 180);
            cursor: pointer;
        }

        .selectlabel {
            display: inline-block;
            float: left;
            border-right: solid 1px dodgerblue;
            width: 33%;
            height: 100%;
            margin-bottom: 0;
            cursor: pointer;
            color: dodgerblue;
            line-height: 30px;
            text-align: center;
        }

        .selectlabelactived {
            background: dodgerblue;
            color: white;
        }

        .selectshiplabel {
            width: 100%;
            height: 30px;
            line-height: 30px;
            display: inline-block;
            padding-left: 10px;
            text-align: left;
            cursor: pointer;
        }

        .selectshiplabel:hover {
            background: #ecf7ff;
        }

        .selectshiplabel > div {
            margin-left: 10px;
            margin-top: 3px;
            height: 25px;
            line-height: 25px;
            float: left;
            width: auto;
            padding: 0 10px;
            text-align: center;
            color: white;
        }

        .selectshiplabel > span {
            float: left;
        }

        #shipinfodiv_table td {
            border-top: 0 !important;
        }

        .ztcolordiv {
            margin-left: 5px;
            margin-top: 10px;
            height: 25px;
            line-height: 25px;
            float: left;
            width: auto;
            padding: 0 10px;
            text-align: center;
            color: white;
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
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    Calendar ca = Calendar.getInstance();
//    String edate = format.format(ca.getTime());
//    ca.add(Calendar.HOUR_OF_DAY, -2);
//    String bdate = format.format(ca.getTime());
%>
<body style="font-family: Helvetica,Tahoma,'Microsoft YaHei','微软雅黑',Arial,STXihei,'华文细黑',SimSun,'宋体',Heiti,'黑体',sans-serif;">
<input type="hidden" value="<%=area%>" id="userareaNo"/>

<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
    <b style="font-size: 20px;">实时监测</b>
    <span style="float: right;font-size: 14px;">智慧监管&gt实时监测</span>
</div>
<div style="float: left;width:100%;height:700px;padding:0 20px;position: relative;">
    <%--船舶概况,水位，流量列表--%>
    <div id="cblistdiv"
         style="display: none;position: absolute;width: 300px;height:700px;background: white;top:0px;left:20px;z-index:3;box-shadow: 0px 0px 5px #888888;">
        <div style="float: left;width:100%;height:30px;line-height: 30px;padding: 0 10px;border-bottom: solid 1px #ccc;">
            <span style="float: left;" id="datalisttitlename">船舶列表</span>
            <i class="fa fa-close" style="float: right;font-size: 16px;margin-top: 3px;cursor: pointer;"
               onclick="hidecblistdiv()"></i>
            <i class="fa fa-refresh" onclick="refreshshiplist()" id="listrefreshbtn"
               style="float: right;font-size: 16px;margin-top: 3px;margin-right: 10px;cursor: pointer;"></i>
        </div>
        <%--查询框--%>
        <div style="width:300px;float: left;border-bottom: solid 1px #ccc;" id="cxkdiv">
            <i class="fa fa-search"
               style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
               onclick="getcbmclist();"></i>
            <input type="text" class="form-control" style="height:100%;padding:5px;width:230px;border: 0;float: left;"
                   placeholder="请输入船名号" id="cmhinput"/>

            <div style="float: left;height:30px;width:28px;">
                <i class="fa fa-close"
                   style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;"
                   id="clearbtn"></i>
            </div>
        </div>
        <%--列表框--%>
        <div style="float:left;width:300px;height: 630px;overflow-y: auto;padding: 10px;" id="cmhdiv">
            <%--<div style="display: none;float: left;width: 100%;height:30px;border-radius: 4px;margin-bottom:10px;border:solid 1px dodgerblue;">--%>
            <%--<label class="selectlabel ">全部</label>--%>
            <%--<label class="selectlabel ">危险品</label>--%>
            <%--<label class="selectlabel selectlabelactived" style='border-right: 0;width: 34%'>黑名单</label>--%>
            <%--</div>--%>
        </div>
    </div>
    <div id='ssjgdiv'
         style="display: none;position: absolute;width: 400px;background: white;top:70px;left:40px;z-index:2;box-shadow: 0px 0px 5px #888888;">
        <div style="float:left;width: 100%;background: rgb(1,134,237);line-height: 35px;" id="xqtopname">

        </div>
        <table class="table" id="ssjgtable">

        </table>
    </div>
    <%--城市切换下拉按钮--%>
    <div class="btn-group"
         style="position: absolute;top:20px;left:100%;margin-left:-650px;z-index:2;box-shadow: 0px 0px 5px #888888;"
         id="areaallbtn">
        <button type="button" class="btn dropdown-toggle"
                data-toggle="dropdown" style="background: white;height:30px;line-height: 18px;">
            <i class="fa fa-map-marker"></i>&nbsp;<span id="quyuname"></span> <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" role="menu" style="width: 100%;" id="areaul">
        </ul>
    </div>
    <button onclick="movetoareacenter()" class="btn btn-default"
            style="position: absolute;top:20px;left:100%;margin-left:-650px;z-index:2;box-shadow: 0px 0px 5px #888888;display: none;"
            id="areabtn"></button>
    <%--左上选择栏--%>
    <div
            style="position: absolute;height:30px;width: 500px;padding:5px;background: white;top:20px;left:100%;margin-left:-550px;z-index:3;box-shadow: 0px 0px 5px #888888;">
        <div class="dropdown" style="height: 20px;float: left;width:25%;">
            <div class="layerbtnblue dropdown-toggle shipimg" data-toggle="dropdown">
                <img src="../image/smart/ic_ship_blue.png"/>
                船舶&nbsp;&nbsp;<i class="fa fa-caret-down"></i>
            </div>
            <ul class="dropdown-menu pull-left" id='shiptypeUL'
                style="left: 0;width: 105px;height: auto;margin-top:10px;">
                <li><a href="#" onclick="getcblist()">船舶列表</a></li>
                <li><a href="#" onclick="cbgkdataget()">船舶概况</a></li>
            </ul>
        </div>
        <div class="dropdown" style="height: 20px;float: left;width:25%;">
            <div class="layerbtn dropdown-toggle" data-toggle="dropdown">
                <img src="../image/smart/ic_river_black.png"/>
                轨迹查询
            </div>
            <ul class="dropdown-menu pull-left" id=''
                style="left: 0;width: 105px;height: auto;margin-top:10px;">
                <li><a href="#" onclick="showgjdiv()">船舶轨迹</a></li>
                <li><a href="#" onclick="showqygjdiv()">区域轨迹</a></li>
            </ul>
        </div>
        <div class="dropdown" style="height: 20px;float: left;width:25%;">
            <div class="layerbtn dropdown-toggle" data-toggle="dropdown">
                <img src="../image/smart/ic_waterlevel_black.png"/>
                水位流量
            </div>
            <ul class="dropdown-menu pull-left" id=''
                style="left: 0;width: 105px;height: auto;margin-top:10px;">
                <li><a href="#" onclick="swdatalistget(1)">水位观测点</a></li>
                <li><a href="#" onclick="swdatalistget(2)">流量观测点</a></li>
            </ul>
        </div>
        <div class="dropdown" style="height: 20px;float: left;width:25%;" id="showhdmddivbtn">
            <div class="layerbtn" style="border-right: 0;">
                <img src="../image/smart/ic_observation-point_black.png"/>
                航道密度
            </div>
        </div>
        <%--<div class="layerbtn" style="border-right: 0;">--%>
        <%--<img src="../image/smart/ic_Camera_black.png"/>--%>
        <%--视频--%>
        <%--</div>--%>
    </div>
    <%--船舶详情div--%>
    <div id="shipinfodiv"
         style="position: absolute;display:none;height:auto;width: 500px;padding:5px 0;background: white;top:70px;left:100%;margin-left:-550px;z-index:2;box-shadow: 0px 0px 5px #888888;">
        <div style="float: left;width:100%;height:50px;line-height: 50px;padding: 0 10px;border-bottom: solid 1px #ccc;">
            <span style="float: left;font-size: 16px;"><b id="shipinfodiv_shipname">船舶列表</b></span>
            <i class="fa fa-close" style="float: right;font-size: 16px;margin-top: 13px;cursor: pointer;"
               id="shipinfodiv_closebtn"></i>
        </div>
        <table class="table" id="shipinfodiv_table" style="margin-bottom: 0;">
        </table>
        <div style="float: left;width:100%;padding: 10px;border-top: solid 1px #ccc;margin-top:10px;">
            <button class="btn btn-default" style="float: right;"  id="gzcbbtn">跟踪船舶</button>
            <button class="btn btn-default" style="float: right;margin-right: 10px;" onclick="historygj()">历史轨迹</button>
            <button class="btn btn-default" style="float: right;margin-right: 10px;" onclick="getallshipdata()">船舶详情
            </button>
        </div>
    </div>
    <%--水位流量详情div--%>
    <div id="swllinfodiv"
         style="position: absolute;display:none;height:auto;width: 500px;padding:5px 0;background: white;top:70px;left:100%;margin-left:-550px;z-index:2;box-shadow: 0px 0px 5px #888888;">
        <div style="float: left;width:100%;height:50px;line-height: 50px;padding: 0 10px;border-bottom: solid 1px #ccc;">
            <span style="float: left;font-size: 16px;"><b id="swllinfodiv_name">船舶列表</b></span>
            <i class="fa fa-close" style="float: right;font-size: 16px;margin-top: 13px;cursor: pointer;"
               id="swllinfodiv_closebtn"></i>
        </div>
        <table class="table" id="swllinfodiv_table" style="margin-bottom: 0;">
        </table>
    </div>
    <%--航道密度div--%>
    <div id="hdmddiv"
         style="display: none;position: absolute;height:auto;width: 400px;padding:5px 0;background: white;top:20px;left:40px;z-index:2;box-shadow: 0px 0px 5px #888888;">
        <div style="float: left;width:100%;height:50px;line-height: 50px;padding: 0 10px;border-bottom: solid 1px #ccc;">
            <span style="float: left;font-size: 16px;"><b id="hdmd_name">航道密度</b></span>
            <i class="fa fa-close" style="float: right;font-size: 16px;margin-top: 13px;cursor: pointer;"
               id="hdmd_closebtn"></i>
        </div>
        <div style="float: left;width:100%;padding: 0 10px;border-bottom: solid 1px #ccc;">
            <div style="float: left;width: 30%;text-align: right;line-height: 45px;font-size: 16px;font-width:500;padding-right:20px;">区域：<br>区域边长：
            </div>
            <div style="float: left;width: 70%;text-align: left;height:150px;">
                <button class="btn-default btn" style="margin-top:10px;" onclick="mrqy()">默认区域</button>
                <button class="btn-primary btn" style="margin-top:10px;"id="xdcbox">选定区域</button>
                <label style="margin-top:10px;">
                    <input type="number" style="width:80px;" id="jlinput"/>&nbsp;公里
                </label>
                <div style="float: left;margin-top:10px;width: 100%;">
                    <button class="btn-primary btn" onclick="gethdmd()">查询</button>
                    <button class="btn-default btn" style="margin-left:10px;" onclick="hdmdreset(1)">重置</button>
                </div>
            </div>
        </div>
        <div style="float: left;width:100%;padding: 0 10px;border-bottom: solid 1px #ccc;display: none;" id="mdworddiv">
            <div style="float: left;width: 30%;text-align: left;line-height: 30px;font-size: 16px;font-width:500;">
                查询结果：
            </div>
            <div style="float: left;width: 70%;text-align: left;line-height: 30px;font-size: 16px;font-width:500;">
                当前选定区域内的船舶密度为<br><span style="font-size: 22px;font-width:800;color: red" id="mdword">大</span>
            </div>
        </div>
        <div style="float: left;width:100%;padding:10px;">
            参考值：
            <table class="table table-bordered" style="margin-bottom: 0;">
                <tr><td>密度值（艘/平方公里）</td><td>0-5</td><td>5-10</td><td>10+</td></tr>
                <tr><td>密度等级</td><td>小</td><td>中</td><td>大</td></tr>
            </table>
            <span style="color: #999999;font-size: 14px;">注：默认区域是地图中心点为基点绘制的1公里*1公里的正方形区域</span>
        </div>
    </div>
    <%--船舶概况--%>
    <div id='cbgkdiv'
         style="position: absolute;display:none;left: 20px;bottom:0;width: 98%;z-index: 3;background: white;border:solid 1px #ccc;">
        <div class="modal-header" style="padding:5px 10px;">
            <button type="button" class="close" style="margin-top:5px;" id="cbgkclosebtn">
                &times;
            </button>
            <i class="fa fa-refresh" onclick="cbgkdataget()"
               style="float: right;margin-top:5px;margin-right:15px;cursor:pointer;"></i>
            <h4 class="modal-title">
                船舶概况
            </h4>
        </div>
        <div style="max-height:260px;overflow-y: auto;width: 100%;float: left;" id="cbgknrdiv">
            <table id="cbgktable" class="table">
            </table>
        </div>
    </div>
    <%--区域轨迹列表概况--%>
    <div id='qygjlistdiv'
         style="position: absolute;display: none;left: 20px;bottom:0;width: 98%;z-index: 3;background: white;border:solid 1px #ccc;">
        <div class="modal-header" style="padding:5px 10px;">
            <button type="button" class="close" style="margin-top:5px;" id="qygjlistclosebtn">
                &times;
            </button>
            <h4 class="modal-title" id="qygjlisttitle">
                船舶概况
            </h4>
        </div>
        <div style="max-height:260px;overflow-y: auto;width: 100%;float: left;" >
            <table id="qygjtable" class="table">
            </table>
        </div>
    </div>
    <%--船舶轨迹搜索框--%>
    <div id='cbgjdiv'
         style="display:none;position: absolute;width: 350px;background: white;top:20px;left:40px;z-index:2;box-shadow: 0px 0px 5px #888888;">
        <div class="modal-header" style="padding:5px 10px;">
            <button type="button" class="close" style="margin-top:5px;" id="cbgjclosebtn">
                &times;
            </button>
            <h4 class="modal-title">
                船舶轨迹查询
            </h4>
        </div>
        <table class="table" style="background-color: white;margin-bottom:0;">
            <tr>
                <td class="col-xs-3">船名号<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input class="from-control" type="text" style="border: 0;" placeholder="请输入船名号" id="cbgjname"/>
                </td>
            </tr>
            <tr>
                <td class="col-xs-3">开始时间<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input type="text" value="" placeholder="起始时间" id="beginTime"
                           onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}',minDate:'#F{$dp.$D(\'endTime\',{H:-2})}',skin:'twoer'})"
                           readonly="readonly" class="Wdate"
                           style="float:left;height:22px;line-height: 22px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                </td>
            </tr>
            <tr>
                <td class="col-xs-3">结束时间<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input type="text" id="endTime" value="" placeholder="结束时间"
                           onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'beginTime\')}',maxDate:'#F{$dp.$D(\'beginTime\',{H:2})}',skin:'twoer'})"
                           readonly="readonly" class="Wdate"
                           style="float:left;height:22px;line-height: 22px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button class="btn btn-info" style="width: 90%;margin-left: 5%;" onclick="guijimake()">查询</button>
                </td>
            </tr>
        </table>
        <div style="display:none;position: absolute;width:160px;height: 200px;overflow-y: auto;border:solid 1px #ccc;background:white;top:35px;left:95px;"
             id="cbgjnamelist">
        </div>
    </div>
    <%--区域船舶轨迹搜索框--%>
    <div id='qygjdiv'
         style="display:none;position: absolute;width: 350px;background: white;top:20px;left:40px;z-index:2;box-shadow: 0px 0px 5px #888888;">
        <div class="modal-header" style="padding:5px 10px;">
            <button type="button" class="close" style="margin-top:5px;" id="qugjclosebtn">
                &times;
            </button>
            <h4 class="modal-title">
                区域轨迹查询
            </h4>
        </div>
        <table class="table" style="background-color: white;margin-bottom:0;">
            <tr>
                <td class="col-xs-3" style="line-height:35px;">区域<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <button class="btn btn-default" onclick="qycbhz()">绘制区域</button>
                </td>
            </tr>
            <tr>
                <td class="col-xs-3" >区域边长<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input type="number" style="width:100px;" id="qybcinput" value="1"/>&nbsp;公里
                </td>
            </tr>
            <tr>
                <td class="col-xs-3">开始时间<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input type="text" value="" placeholder="起始时间" id="qybeginTime"
                           onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'qyendTime\')}',skin:'twoer'})"
                           readonly="readonly" class="Wdate"
                           style="float:left;height:22px;line-height: 22px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                </td>
            </tr>
            <tr>
                <td class="col-xs-3">结束时间<span style="color: red;">*</span>:</td>
                <td class="col-xs-9">
                    <input type="text" id="qyendTime" value="" placeholder="结束时间"
                           onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'qybeginTime\')}',skin:'twoer'})"
                           readonly="readonly" class="Wdate"
                           style="float:left;height:22px;line-height: 22px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button class="btn btn-info"  onclick="selectqygj()">查询</button>
                    <button class="btn btn-default"  style="margin-left: 10px;" onclick="hdmdreset(2)">重置</button>
                </td>
            </tr>
        </table>
    </div>
    <%--地图div--%>
    <div id="shipOrbitmap_div"
         style="float: left;width:98%;height:100%;border:solid 1px #ccc;position:absolute;z-index: 0">
    </div>
</div>
<!-- 船舶详情模态框（Modal） -->
<div class="modal fade" id="cbxqModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:900px;">
        <div class="modal-content" style="width:100%;height: 480px;">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    <img src="../image/information/ic_details.png" width="30px" height="30px">
                    船舶详情 —— <span id="titleshipname"></span>
                </h4>
            </div>
            <div class="modal-body" style="height:auto;padding:0;">
                <div style="float: left;height:auto;width:100%;overflow: auto;" class="treediv">
                    <div style="float:left;width: 100%;">
                        <ul class="nav nav-tabs ul-edit responsive">
                            <li class="active"><a class='tabdiv' href="#tab-internet" data-toggle="tab">基本信息</a></li>
                            <li><a class='tabdiv' href="#tab-internet" data-toggle="tab">证书信息</a></li>
                            <li><a class='tabdiv' href="#tab-internet" data-toggle="tab">违章信息</a></li>
                            <li><a class='tabdiv' href="#tab-internet" data-toggle="tab">缴费信息</a></li>
                            <li><a class='tabdiv' href="#tab-internet" data-toggle="tab">船检信息</a></li>
                            <%--<li><a class='tabdiv' href="#tab-internet" data-toggle="tab">电子报告信息</a></li>--%>
                            <%--<li><a class='tabdiv' href="#tab-internet" data-toggle="tab">AIS信息</a></li>--%>
                        </ul>
                    </div>
                    <div style="padding: 10px;float: left;width: 100%;height: 350px;overflow-y: auto">
                        <table class="table" id="shipdatatable"></table>
                        <div id="nulltablediv"
                             style="float: left;width:100%;text-align: center;margin-top:100px;display: none;color: rgb(215,215,215);">
                            <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

</body>
</html>
