<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/8/2
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.zjport.util.CommonConst" %>
<!DOCTYPE html>
<html>
<head>
    <title>船舶详情</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link href="../js/common/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <!-- CK Editor -->
    <script src="../js/ckeditor/ckeditor.js"></script>
    <!-- 页面js -->
    <script src="../js/smart/ssjc_cbxq.js" type="text/javascript"></script>
<style type="text/css">
    .colortd,#shipdatatable th{
        background: #f4f9ff;
    }
    #shipdatatable tr{
        height:50px;
    }
    #shipdatatable td,#shipdatatable th{
        line-height: 32px;
    }
    .clickword{
        color:rgb(31,116,180);
        cursor: pointer;
    }
    .redtr td{
        color: red!important;
    }
</style>
</head>
<body>
<input type="hidden" value="<%=(String) request.getSession().getAttribute("cbmc")%>" id="shipname"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
    <h1>
        <img src="../image/information/ic_details.png">
        船舶详情 —— <%=(String) request.getSession().getAttribute("cbmc")%>
        <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
    </h1>

</section>

<section class="content" style="padding-top:0; margin-top: 15px;" >
    <div class="box box-primary" style="border-top:0;box-shadow:0px 0px 5px #ccc;background-color: white;height:700px;">
        <div style="float:left;width: 100%;">
            <ul class="nav nav-tabs ul-edit responsive">
                <li class="active"><a class='tabdiv' href="#tab-internet" data-toggle="tab" >基本信息</a></li>
                <li><a class='tabdiv' href="#tab-internet" data-toggle="tab">证书信息</a></li>
                <li><a class='tabdiv' href="#tab-internet" data-toggle="tab">违章信息</a></li>
                <li><a class='tabdiv' href="#tab-internet" data-toggle="tab">缴费信息</a></li>
                <li><a class='tabdiv' href="#tab-internet" data-toggle="tab">船检信息</a></li>
                <%--<li><a class='tabdiv' href="#tab-internet" data-toggle="tab">电子报告信息</a></li>--%>
                <%--<li><a class='tabdiv' href="#tab-internet" data-toggle="tab">AIS信息</a></li>--%>
            </ul>
        </div>
        <div style="padding: 10px;float: left;width: 100%;height: 630px;overflow-y: auto">
            <table class="table" id="shipdatatable"></table>
            <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
            </div>
        </div>
    </div><!-- /. box -->
</section>

</body>
</html>
