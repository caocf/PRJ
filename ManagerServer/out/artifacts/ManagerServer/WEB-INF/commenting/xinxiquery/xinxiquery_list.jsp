<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>信息查询</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/bootpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <!-- 页面js -->
    <script src="../js/commenting/xinxiquery_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword{
            color:rgb(0,104,177);
            cursor: pointer;
        }
         .worddiv{
            width:100px;
            float: left;
             margin-top: 5px;
        }

        .aa
        {
            padding: 0;
            margin: 0;
        }
        .td
        {
            text-align: center;
        }
        .tr{

        }


    </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
    <section class="content-header">
        <h1>
            通知管理
            <!-- <small>advanced tables</small> -->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-commenting"></i> 通知公告</a></li>
            <li><a href="#">通知管理</a></li>
        </ol>
    </section>

    <section class="content">
        <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                    <div class="worddiv">通知类别：</div>
                    <div class="btn-group" style="float: left;width: 90px;">
                        <button type="button" class="btn btn-default dropdown-toggle"
                                data-toggle="dropdown" style="width:100%;height:32px;">
                            <span id="type" title="">全部类别</span> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu " role="menu">
                            <li><a onclick="option('','type',this)">全部</a></li>
                            <li><a onclick="option(4,'type',this)">港航通知</a></li>
                            <li><a onclick="option(5,'type',this)">行政通告</a></li>
                            <li><a onclick="option(6,'type',this)">航行警告</a></li>
                        </ul>
                    </div>

                    <div class="worddiv" style="margin-left: 100px;">发布单位：</div>
                    <div class="btn-group" style="float: left;width: 90px;">
                        <button type="button" class="btn btn-default dropdown-toggle"
                                data-toggle="dropdown" style="width:140px;height:32px;">
                            <span id="lbnamespan" title="">全部</span> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu " role="menu" style="left: 0;width: 140px;text-align: center;padding: 0;margin: 0;">
                            <li ><a onclick="option('','lbnamespan',this)" class="aa">全部</a></li>
                            <li class="aa"><a onclick="option(1,'lbnamespan',this)" class="aa">浙江省港航管理局</a></li>
                            <li class="aa"><a onclick="option(2,'lbnamespan',this)">杭州市港航管理局</a></li>
                            <li class="aa"><a onclick="option(3,'lbnamespan',this)">嘉兴市港管理航局</a></li>
                            <li class="aa"><a onclick="option(4,'lbnamespan',this)">湖州市港航管理局</a></li>
                            <li class="aa"><a onclick="option(5,'lbnamespan',this)">舟山市港航管理局</a></li>
                        </ul>
                    </div>

                    <div class="worddiv" style="margin-left: 10%;">发布时间：</div>
                    <div style="float:left;margin-left:10px;">
                        <input type="text" placeholder="起始时间" id="beginTime" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd'})" readonly="readonly" class="Wdate" style="float:left;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;" />
                        <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;" >至</span>
                        <input type="text" id="endTime" placeholder="结束时间" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd'})" readonly="readonly" class="Wdate" style="float:left;margin-left:10px;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;" />
                    </div>
                </div>
            </div>
        </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                    <div class="worddiv">通知状态：</div>
                    <div class="btn-group" style="float: left;width: 90px;">
                        <button type="button" class="btn btn-default dropdown-toggle"
                                data-toggle="dropdown" style="width:100%;height:32px;">
                            <span id="status" title="">全部状态</span> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu " role="menu">
                            <li><a onclick="option('','status',this)">全部</a></li>
                            <li><a onclick="option(1,'status',this)">待审核</a></li>
                            <li><a onclick="option(2,'status',this)">不通过</a></li>
                            <li><a onclick="option(3,'status',this)">已发布</a></li>
                        </ul>
                    </div>
                    <div class="worddiv" style="margin-left: 100px;">通知标题：</div>
                    <div style="width:378px;float: left;border-radius:4px;border:solid 1px #cccccc;">
                        <input type="text" class="form-control" style="height:100%;padding:5px;width:308px;border: 0;float: left;"placeholder="请输入通知标题" id="listkey"/>
                        <div style="float: left;height:30px;width:28px;">
                            <i class="fa fa-close" style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;" id="clearbtn"></i>
                        </div>
                    </div>
                    <script>
                        $(document).ready(function(){
                            $("#clearbtn").bind("click",function(){
                                $("#clearbtn").hide();
                                $("#listkey").val('');
                            })
                            $("#listkey").bind('input propertychange', function() {
                                if($('#listkey').val()!=''&&$('#listkey').val()!=null){
                                    $("#clearbtn").show();
                                }
                            });
                        })
                    </script>
                </div>
            </div>
            </div>
            <button class="btn btn-primary" style="margin-top:20px;margin-left:10px;" onclick="Search(1)">查询</button>
        <div id="generalTabContent" class="t  ab-content" style="margin-top:20px;min-height:600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-hover col-xs-12" id="kqtable" style="border-top:none">
                    <col width="5%" />
                    <col width="15%" />
                    <col width="10%"/>
                    <col width="15%" />
                    <col width="15%" />
                    <col width="10%"/>
                    <col width="30%" />
                    <tr style="background-color: rgb(240,245,248);">
                        <th class="td">序号</th>
                        <th class="td">标题</th>
                        <th class="td">类型</th>
                        <th class="td">地区</th>
                        <th class="td">发布时间</th>
                        <th class="td">状态</th>
                        <th class="td">操作</th>
                    </tr>
                </table>
            </div>

            <div class='bootpagediv' style='width:100%;margin-right:50px;margin-top: 20px;'>
                <span style="float: left;margin-left:10px;line-height: 20px;color:#666;" id="pagedetal">
                </span>
                <nav style='float:right;display:none' id='pageshow'>
                    <ul class="pagination" id='pagination'>
                    </ul>
                </nav>
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
</body>
</html>
