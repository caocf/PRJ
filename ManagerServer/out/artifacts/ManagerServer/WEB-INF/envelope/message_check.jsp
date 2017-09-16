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
  <title>查看意见箱</title>

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
  <script src="../js/envelope/message_check.js" type="text/javascript"></script>
  <style type="text/css">
    .clickword{
      color:rgb(0,104,177);
      cursor: pointer;
    }
    .worddiv{
      width:100px;
      float: left;
    }
    .worddiv span{
      color:red;
    }
    .table tr{
      height:50px!important;
    }
    .table td{
      line-height: 34px!important;
    }
    .btn-default{
      margin-bottom: 20px!important;
    }
  </style>
</head>
<%
  String id = (String)request.getAttribute("mid");
%>
<body>
<input type="hidden" value="<%=id%>" id="kqid" />
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    查看
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>

<section class="content" style="padding-top: 20px;">
  <div class="box" style="border:solid 1px #cccccc;">
    <div id="generalTabContent" class="t  ab-content" style="height: 300px;">
      <!-- 角色列表 -->
      <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
        <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
          <tr>
            <td>
              <div class="worddiv">城市：</div>
              <span id="cs"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">联系电话：</div>
              <span id="lxdh"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">反馈用户：</div>
              <span id="fkyh"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">反馈时间：</div>
              <span id="fksj"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">内容：</div>
              <span id="nr"></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
