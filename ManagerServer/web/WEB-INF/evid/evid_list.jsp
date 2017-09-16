<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>证据保全</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/common/common.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
    <script src="../js/evid/evid_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword {
            color: rgb(0, 104, 177);
            cursor: pointer;
        }
    </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header">
    <h1>
        证据保全
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-picture-o"></i>证据保全</a></li>
    </ol>
</section>

<section class="content">
    <div class="box">
        <div class="row">
            <div class="col-sm-12" style="padding-top: 20px">
                <div class="btn-group" style="float: left;width: 90px;margin-left:10px;">
                    <button type="button" class="btn btn-default dropdown-toggle"
                            data-toggle="dropdown" style="width:100%;height:32px;">
                        <span id="spantype">全部类型</span> <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu " role="menu">
                        <li><a onclick="option('0','spantype',this)">全部</a></li>
                        <li><a onclick="option('1','spantype',this)">海事</a></li>
                        <li><a onclick="option('2','spantype',this)">港政</a></li>
                    </ul>
                </div>
                <div class="btn-group" style="float: left;width: 400px;margin-left:50px;">
                    <input type="text" placeholder="取证起始时间" id="beginTime"
                           onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd'})"
                           readonly="readonly" class="Wdate"
                           style="float:left;height:35px;line-height: 35px;padding-left:10px;border: solid 1px #ccc;"/>
                    <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;">至</span>
                    <input type="text" id="endTime" placeholder="取证结束时间"
                           onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd'})"
                           readonly="readonly" class="Wdate"
                           style="float:left;margin-left:10px;height:35px;line-height: 35px;padding-left:10px;border: solid 1px #ccc;"/>
                </div>
                <div style="heigth:100%;width:280px;float: left;margin-left:20px;border-radius:4px;border:solid 1px #cccccc;">
                    <input type="text" class="form-control"
                           style="height:100%;padding:8px;width:200px;border: 0;float: left;" placeholder="请输入船舶名称"
                           id="listkey"/>
                    <div style="float: left;height:30px;width:30px;">
                        <i class="fa fa-close"
                           style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;"
                           id="clearbtn"></i>
                    </div>
                    <i class="fa fa-search"
                       style="cursor:pointer;float: left;height:30px;width:30px;line-height: 30px;text-align: center;"
                       onclick="search(1)"></i>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content" style="margin-top:20px;min-height:600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-hover col-xs-12" id="dttable" style="border-top:none">
                    <col width="5%"/>
                    <col width="15%"/>
                    <col width="30%"/>
                    <col width="15%"/>
                    <col width="10%"/>
                    <col width="15%"/>
                    <col width="10%"/>
                    <tr style="background-color: rgb(240,245,248);">
                        <th class="td">序号</th>
                        <th class="td">船舶名称</th>
                        <th class="td">案由</th>
                        <th class="td">执法人员</th>
                        <th class="td">所属分类</th>
                        <th class="td">取证时间</th>
                        <th class="td">操作</th>
                    </tr>
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
