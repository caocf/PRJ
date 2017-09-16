<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.model.TOrg" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>报警信息</title>

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
    <script src="../js/common/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="../js/smart/warndata.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword {
            color: rgb(31, 116, 180);
            cursor: pointer;
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
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<input type="hidden" value="<%=area%>" id="userareaNo"/>
<section class="content-header">
    <h1>
        报警信息
        <!-- <small>advanced tables</small> -->
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-cog"></i> 智慧监管</a></li>
        <li><a href="#">监测预警</a></li>
        <li><a href="#">报警信息</a></li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter"
                     style="text-align:left;margin-top: 5px;margin-left: 5px">
                    <select id='areaselect' class="form-control"
                            style="float: left;width:100px;height:30px;line-height: 30px;padding-top:0;padding-bottom:0;">
                    </select>
                    <span style="float: left;height:30px;line-height: 30px;"
                          id="qyname"></span>
                    <select id='bjlxselect' class="form-control"
                            style="float: left;width:150px;height:30px;margin-left: 10px;line-height: 30px;padding-top:0;padding-bottom:0;">
                        <option value="1">危险品船舶</option>
                        <option value="2">状态异常船舶</option>
                    </select>
                    <div style="float:left;">
                        <input type="text" placeholder="起始时间" id="beginTime"
                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer'})"
                               readonly="readonly" class="Wdate"
                               style="float:left;margin-left:10px;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                        <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;">至</span>
                        <input type="text" id="endTime" placeholder="结束时间"
                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer'})"
                               readonly="readonly" class="Wdate"
                               style="float:left;margin-left:10px;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;"/>
                    </div>
                    <div style="heigth:100%;width:250px;float: left;border-radius:4px;border:solid 1px #cccccc;margin-left: 10px;">
                        <input type="text" class="form-control"
                               style="height:100%;padding:5px;width:180px;border: 0;float: left;" placeholder="请输入报警对象名称"
                               id="rolenameselectinput"/>

                        <div style="float: left;height:30px;width:28px;">
                            <i class="fa fa-close"
                               style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;"
                               id="clearbtn"></i>
                        </div>
                        <i class="fa fa-search"
                           style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                           onclick="showInfoInTable('../supervise/warnlist',1)"></i>
                    </div>
                    <script>
                        $(document).ready(function () {
                            $("#clearbtn").bind("click", function () {
                                $("#clearbtn").hide();
                                $("#rolenameselectinput").val('');
                            })
                            $("#rolenameselectinput").bind('input propertychange', function () {
                                if ($('#rolenameselectinput').val() != '' && $('#rolenameselectinput').val() != null) {
                                    $("#clearbtn").show();
                                }
                            });
                        })
                    </script>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content no-margin"
             style="margin-top:10px;margin-left:8px;height:600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-bordered table-hover col-xs-12" id="loglist-info" style="border-top:none">
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
            <div id="nulltablediv"
                 style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
            </div>
        </div>
    </div>
</section>
</body>
</html>
