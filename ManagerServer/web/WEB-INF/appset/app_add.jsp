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
  <title>发布新版本</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
  <!-- jQuery 2.1.4 -->
  <script src="../js/common/jQuery-2.1.4.min.js"></script>
  <script src="../js/common/ajaxfileupload.js"></script>
  <script src="../js/bootpaging.js"></script>
  <script type="text/javascript" src="../js/common/My97DatePicker/WdatePicker.js"></script>
  <!-- 页面js -->
  <script src="../js/appset/app_add.js" type="text/javascript"></script>
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
    .nrinput{
      float: left;
      width:300px!important;
      border: 0!important;
    }
  </style>
</head>
<%
  String type=(String)request.getAttribute("type");
  String appname="";
  if("1".equals(type)){
    appname="省港航公共版";
  }else if("2".equals(type)){
    appname="省港航内部版";
  }
%>
<body>
<input type="hidden" value="<%=type%>" id="apptype"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    发布新版本
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>

<section class="content" style="padding-top: 20px;">
  <input type="hidden" id="addtype" value="1"/>
  <div class="box" style="border:solid 1px #cccccc;">
    <div id="generalTabContent" class="t  ab-content" style="height: 357px;">
      <!-- 角色列表 -->
      <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
        <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
          <tr>
            <td>
              <div class="worddiv">应用名称：</div>
              <span style="margin-left: 10px;"><%=appname%></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">版本号<span>*</span>：</div>
              <input class="form-control nrinput" type="number" placeholder="请输入版本号" id="bbh"/>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">版本名<span>*</span>：</div>
              <input class="form-control nrinput" type="text" placeholder="请输入版本名" id="bbm"/>
            </td>
          </tr>
          <tr style="height:150px!important;">
            <td>
              <div class="worddiv">更新日志<span>*</span>：</div>
              <textarea id='syword' class="form-control" rows="10" cols="10" style="resize: none;width:60%;height:110px;border: 0;" placeholder="请输入日志"></textarea>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">选择app<span>*</span>：</div>
              <div class="form-group" style="float:left;">
                <div class="btn btn-default btn-file" style="margin-left: 15px;">
                  浏览
                  <input type="file" name="appfile" id="appfile" multiple="multiple"/>
                </div>&nbsp;&nbsp;<span id="filenamespan"></span>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <button type="button" class="btn btn-primary" onclick="sendapp()">发布新版本</button>
  <button type="button" class="btn btn-default" style="margin-left: 10px;" onclick="javascript :history.back(-1);">取消</button>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
