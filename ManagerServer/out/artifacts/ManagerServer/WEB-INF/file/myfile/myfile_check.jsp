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
  <title>考勤审批</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <link href="../css/common/bootstrap.min.css" rel="stylesheet" type="text/css" />
  <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
  <link href="../summernote-master/dist/summernote.css"  rel="stylesheet" type="text/css"/>
  <!-- jQuery 2.1.4 -->
  <script src="../js/common/jQuery-2.1.4.min.js"></script>
  <!-- 页面js -->
  <script src="../summernote-master/dist/summernote.min.js"></script>
  <script src="../summernote-master/lang/summernote-zh-CN.js"></script>
  <script src="../js/flie/myfile_check.js" type="text/javascript"></script>
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
%>
<body>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    审批
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>

<section class="content" style="padding-top: 20px;">
  <%
    if("1".equals(type)){
  %>
  <button type="button" class="btn btn-primary" onclick="" style="margin-bottom: 20px;">重新提交</button>
  <%
    } else if("2".equals(type)){
  %>
  <button type="button" class="btn btn-primary" onclick="" style="margin-bottom: 20px;">撤回</button>
  <button type="button" class="btn btn-default" onclick="javascript :history.back(-1);" style="margin-left: 10px;margin-bottom:20px;">取消</button>
  <%}%>
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
              <div class="worddiv">文档名称：</div>
              <span id="wdmc"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">创建时间：</div>
              <span id="cjsj"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">创建人：</div>
              <span id="cjr"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">审批人：</div>
              <span id="spr"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">状态：</div>
              <span id="zt"></span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">审批意见：</div>
              <span id="spyj"></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <div style="width:100%;float:left;min-height: 100px;background-color: white;margin-bottom: 20px;border:solid 1px #ccc;border-radius: 4px;">
    <h5 style="text-align: left;padding-left: 10px;font-weight: 700">文档样式</h5>
    <div id="summernote"></div>
  </div>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
