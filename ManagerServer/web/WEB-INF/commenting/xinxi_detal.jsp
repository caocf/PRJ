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
  <title>信息查看</title>

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
  <script src="../js/commenting/xinxi_detal.js" type="text/javascript"></script>
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
  </style>
</head>
<%
  String mode = (String)request.getAttribute("mode");
  String id = (String)request.getAttribute("id");

%>
<body>
<input type="hidden" value="<%=id%>" id="itemid" />
<input type="hidden" value="<%=mode%>" id="mode" />
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    详情
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>

<section class="content" style="padding-top: 20px;">
  <div class="box" style="border:solid 1px #cccccc;height: auto;">
    <div id="generalTabContent" class="t  ab-content" style="height: 110px;">
      <!-- 角色列表 -->
      <div id="tab-internet"  style="position: relative;">
        <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
          <tr>
            <td>
              <div class="worddiv" style="font-size: 15px;">审核状态：</div>
              <span id="status"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv" style="font-size: 15px;">审核意见：</div>
              <span id="opinon"></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <div class="box" style="border:solid 1px #cccccc;float: left;background-color: white">
    <div style="float: left;width: 100%;padding: 10px;">
      <h3 style="text-align: center" id="title">信息标题</h3>
      <div style="margin-top:30px;width: 100%;height:50px;line-height: 50px;text-align: left;padding-left:10px;background-color: rgb(234,246,251);text-align: center">
        <span>时间：<span id="ptime"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布者：<span id="region"></span></span>
      </div>
      <div style=" margin-top:30px;width: 100%;text-align: left;min-height: 300px;" id="content">
        正文
      </div>
    </div>
  </div>

  <div class="box" style="display:none;border:solid 1px #cccccc;float: left" id="check">
    <div style="float: left;width: 100%;padding: 10px;">
      <div class="worddiv" style="font-size: 16px;font-weight: 400;">审批意见：</div>
      <label for="passradio" style="cursor: pointer;">
        <input type="radio" name="radiobutton" id="passradio" value="3" />&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: green">批准发布</span>
      </label>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <label for="nopassradio"  style="cursor: pointer;">
        <input type="radio" name="radiobutton" id="nopassradio" value="2"/>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">驳回</span>
      </label>
    </div>
    <textarea placeholder="请输入意见说明" id="shenheword" style="width:100%;height:150px;resize: none;border: 0;padding:10px;border-top: solid 1px #ccc;"></textarea>
  </div>
  <button type="button" class="btn btn-primary" style="display: none;" onclick="Check()" id="c1">提交</button>
  <button type="button" class="btn btn-default" style="display:none;margin-left: 10px;" id="c2" onclick="javascript :history.back(-1);">取消</button>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
