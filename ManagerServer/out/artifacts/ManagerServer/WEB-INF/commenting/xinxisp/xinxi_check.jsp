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
  <title>信息审批</title>

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
  <script src="../js/commenting/xinxi_check.js" type="text/javascript"></script>
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
  String type = (String)request.getAttribute("type");
  String id = (String)request.getAttribute("id");
  String typename="";
  if("1".equals(type)){
    typename="请假";
  }else  if("2".equals(type)){
    typename="加班";
  }else  if("3".equals(type)){
    typename="出差";
  }
%>
<body>
<input type="hidden" value="<%=id%>" id="kqid" />
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    审批
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>

<section class="content" style="padding-top: 20px;">
  <div class="box" style="border:solid 1px #cccccc;">
    <div id="generalTabContent" class="t  ab-content" style="height: 357px;">
      <!-- 角色列表 -->
      <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
        <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
          <tr>
            <td>
              <div class="worddiv">审批编号：</div>
              <span id="spbh"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">发布人：</div>
              <span id="fbr"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">所在单位：</div>
              <span id="szdw"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">发布时间：</div>
              <span id="fbsj"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">信息类型：</div>
              <span id="xxlx"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">发布对象：</div>
              <span id="fbdx"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">审批状态：</div>
              <span id="spzt"></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <div class="box" style="border:solid 1px #cccccc;float: left;background-color: white">
    <div style="float: left;width: 100%;padding: 10px;">
      <div class="worddiv">正文内容：</div>
    </div>
    <div style="float: left;width: 100%;padding: 10px;">
      <h3 style="text-align: center">信息标题</h3>
      <div style="margin-top:30px;width: 100%;height:50px;line-height: 50px;text-align: left;padding-left:10px;background-color: rgb(234,246,251)">
        摘要：信息摘要哦哦哦
      </div>
      <div style=" margin-top:30px;width: 100%;text-align: left;min-height: 300px;">
        正文
      </div>
      <span>图片</span>&nbsp;<span class="clickword">下载</span>
    </div>
  </div>
  <div class="box" style="border:solid 1px #cccccc;float: left">
    <div style="float: left;width: 100%;padding: 10px;">
      <div class="worddiv">审批意见：</div>
      <label for="passradio" style="cursor: pointer;">
        <input type="radio" name="radiobutton" id="passradio" value="3" />&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: green">审批</span>
      </label>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <label for="nopassradio"  style="cursor: pointer;">
        <input type="radio" name="radiobutton" id="nopassradio" value="4"/>&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">驳回</span>
      </label>
    </div>
    <textarea placeholder="请输入意见说明" id="shenheword" style="width:100%;height:150px;resize: none;border: 0;padding:10px;border-top: solid 1px #ccc;"></textarea>
  </div>
  <button type="button" class="btn btn-primary" onclick="checksp()">提交</button>
  <button type="button" class="btn btn-default" style="margin-left: 10px;">取消</button>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
