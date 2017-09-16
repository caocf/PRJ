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
  <title>起草文书</title>

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
  <script src="../js/flie/file_add.js" type="text/javascript"></script>
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
%>
<body>
<input type="hidden" value="<%=type%>" id="filetype"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    起草文书
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>

<section class="content" style="padding-top: 20px;">
  <div class="box" style="border:solid 1px #cccccc;">
    <div id="generalTabContent" class="t  ab-content" style="height: 101px;">
      <!-- 角色列表 -->
      <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
        <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
          <tr>
            <td>
              <div class="worddiv">文档名称<span>*</span>：</div>
              <input class="form-control nrinput" type="text" placeholder="请输入文档名称"/>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">审批人<span>*</span>：</div>
              <div class="dropdown" style="width:110px;float:left;">
                <a data-toggle="dropdown" style="color: #333;cursor: pointer;">
                  <span id="sprword"><span style="color:#ccc">请选择审批人</span></span>
                  <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" id="sprul" style="width:110px;">
                  <li><a onclick="setspr(1,this)">事假</a></li>
                </ul>
              </div>
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
  <button type="button" class="btn btn-primary" onclick="addapp()">发布</button>
  <button type="button" class="btn btn-default" style="margin-left: 10px;" onclick="javascript :history.back(-1);">取消</button>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
