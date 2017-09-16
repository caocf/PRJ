<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>出勤统计</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="../js/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="../js/css/zTreeStyle/bluetree.css" rel="stylesheet" type="text/css"/>
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.core-3.5.js"></script>
    <script src="../js/common/zTreejs/jquery.ztree.excheck-3.5.js"></script>
    <script src="../js/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="../js/calendar/cq_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword{
            color:rgb(0,104,177);
            cursor: pointer;
        }
        .worddiv{
            width:110px;
            float: left;
            height:30px;
            line-height: 30px;
            padding-right: 30px;
            text-align: right;
        }
        #timetype{
            height:30px;border: solid 1px #ccc;float: left;
        }
        #timetype>div{
            height:100%;
            border-right: solid 1px #ccc;
            text-align: center;
            line-height: 30px;
            width:50px;
            background-color: rgb(250,250,250);
            float: left;
            cursor: pointer;
        }
        .col-sm-3{
            height:100%;
            border-right: solid 1px #e5e5e5;
            text-align: center;
        }
        h3{
            margin-top: 10px!important;
            margin-bottom: 0!important;
            font-weight: 700!important;
        }
    </style>
</head>
<body>
    <section class="content-header">
        <h1>
            出勤统计
            <!-- <small>advanced tables</small> -->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-calendar"></i> 考勤管理</a></li>
            <li><a href="#">出勤统计</a></li>
        </ol>
    </section>

    <section class="content">
        <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                    <div class="worddiv">时间：</div>
                    <div id="timetype">
                        <div onclick="settimetype(1)">本周</div>
                        <div onclick="settimetype(2)">上周</div>
                        <div onclick="settimetype(3)">本月</div>
                        <div style="border:0;" onclick="settimetype(4)">上月</div>
                    </div>
                    <div style="float:left;margin-left:10px;">
                        <input type="text" placeholder="起始时间" id="beginTime" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly" class="Wdate" style="float:left;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;" />
                        <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;" >至</span>
                        <input type="text" id="endTime" placeholder="结束时间" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly" class="Wdate" style="float:left;margin-left:10px;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;" />
                    </div>
                </div>
            </div>
        </div>
            <div class="row">
                <div class="col-sm-12">
                    <div  class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                        <div class="worddiv">单位\部门：</div>
                        <div class="btn-group" style="float: left;width: 200px;">
                            <button type="button" class="btn btn-default dropdown-toggle"
                                    data-toggle="dropdown" style="width:100%;height:32px;">
                                <span id="sjmlname">类别</span> <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu " role="menu" style="width: 200px;height:auto;max-height:300px;overflow-y: auto;" id="addmlul">
                                <ul id="bmtree" class="ztree bluetree"></ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div  class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                        <div class="worddiv"></div>
                            <button type="button" class="btn btn-primary" style="width: 100px;" onclick="showcqdata()">
                               统计
                            </button>
                    </div>
                </div>
            </div>
        <div id="generalTabContent" class="t  ab-content" style="margin-top:20px;min-height:600px;overflow-y: auto;">
            <div style="height:100px;width: 100%;float: left;padding:15px 20px;border-top: solid 1px #e5e5e5;">
                <div class="col-sm-3">
                    <h3 style="color:rgb(241,168,57);" id="zqjts">--</h3>
                    总共请假天数
                </div>
                <div class="col-sm-3" >
                    <h3 style="color: rgb(113,227,106)" id="zccts">--</h3>
                    总共出差天数
                </div>
                <div class="col-sm-3" >
                    <h3 style="color: rgb(90,221,207)" id="zjbts">--</h3>
                    总共加班天数
                </div>
                <div class="col-sm-3" style="border-right: 0;">
                    <h3 style="color: rgb(96,168,225)" id="zcqts">--</h3>
                    总共出勤天数
                </div>
            </div>
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-hover col-xs-12" id="rolelist-info" style="border-top:none">
                    <%--<tr>--%>
                        <%--<td>编号</td>--%>
                        <%--<td><span class="clickword" data-toggle="modal" data-target="#cqxqmodal">1</span></td>--%>
                        <%--<td><span class="clickword" data-toggle="modal" data-target="#cqxqmodal">1</span></td>--%>
                        <%--<td><span class="clickword" data-toggle="modal" data-target="#cqxqmodal">1</span></td>--%>
                        <%--<td>编号</td>--%>
                        <%--<td>编号</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>编号</td>--%>
                        <%--<td>编号</td>--%>
                        <%--<td>编号</td>--%>
                        <%--<td>编号</td>--%>
                        <%--<td>编号</td>--%>
                        <%--<td><span class="clickword" data-toggle="modal" data-target="#deleteModal">删除</span></td>--%>
                    <%--</tr>--%>
                </table>
            </div>
            <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
            </div>
        </div>
        </div>
    </section>
    <input type="hidden" id="infoId"/>
<!-- 删除提示模态框（Modal） -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top:300px;">
        <div class="modal-content" style="width: 400px;">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body" style="line-height: 20px;">
                <div style="background-color: rgb(255,168,0);height:20px;width:20px;border-radius:10px;text-align: center; color:white;float:left;">!</div>
                &nbsp;您确定要删除么？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="delbtn">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div><!-- /.modal -->
    <!-- 明细提示模态框（Modal） -->
    <div class="modal fade" id="cqxqmodal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="margin-top:200px;">
            <div class="modal-content" style="width: 800px;height:400px;">
                <div class="modal-header">
                    <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        明细
                    </h4>
                </div>
                <div class="modal-body" style="line-height: 20px;height: 340px;overflow: auto;">
                    <table class="table table-hover col-xs-12"style="border:0;">
                        <tr style="background-color: rgb(240,245,248);">
                            <th>编号</th>
                            <th>类别</th>
                            <th>时长</th>
                            <th>申请人</th>
                            <th>时间</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                        <tr>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td>编号</td>
                            <td><span class="clickword">查看</span></td>
                        </tr>
                    </table>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div><!-- /.modal -->
</body>
</html>
