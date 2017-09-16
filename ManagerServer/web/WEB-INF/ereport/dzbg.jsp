<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>电子报告</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/noactionpaging.js"></script>
    <script src="../js/ereport/dzbg.js" type="text/javascript"></script>
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
        电子报告查询
        <!-- <small>advanced tables</small> -->
    </h1>
  <%--  <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-check"></i> 业务审核</a></li>
        <li><a href="#">危货作业审批</a></li>
    </ol>--%>
</section>

<section class="content">
    <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter"
                     style="text-align:left;margin-top: 15px;margin-left:10px">
                    <div style="heigth:100%;width:400px;float: left;margin-left:20px;border-radius:4px;border:solid 1px #cccccc;margin-left: 20px;">
                        <input type="text" class="form-control"
                               style="height:100%;padding:5px;width:330px;border: 0;float: left;" placeholder="请输入船名"
                               id="listkey"/>
                        <div style="float: left;height:30px;width:28px;">
                            <i class="fa fa-close"
                               style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;"
                               id="clearbtn"></i>
                        </div>
                        <i class="fa fa-search"
                           style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;"
                           onclick="Search(1)"></i>
                    </div>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content" style="margin-top:20px;min-height:600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-hover col-xs-12" id="dttable" style="border-top:none">
                    <col width="10%"/>
                    <col width="15%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="10%"/>
                    <col width="15%"/>
                    <col width="15%"/>
                    <col width="15%"/>
                    <tr style="background-color: rgb(240,245,248);">
                        <th>编号</th>
                        <th>船名</th>
                        <th>类型</th>
                        <th>进/出港</th>
                        <th>进/出港时间</th>
                        <th>报告时间</th>
                        <th>操作</th>
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
