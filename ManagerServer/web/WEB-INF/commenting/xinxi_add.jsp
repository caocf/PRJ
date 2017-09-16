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
  <title>信息发布</title>

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
  <script src="../js/commenting/xinxi_add.js" type="text/javascript"></script>
  <style type="text/css">
    .clickword{
      color:rgb(0,104,177);
      cursor: pointer;
    }
    .worddiv{
      width:70px;
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

    .aa
    {
      margin: 0;
      padding: 0;
    }
  </style>
</head>
<%
  String type=(String)request.getAttribute("type");
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<body>
<input type="hidden" value="<%=type%>" id="filetype"/>
<input type="hidden" value="<%=basePath%>" id="basePath" />
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    通知发布
    <!-- <small>advanced tables</small>
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>-->
  </h1>
</section>

<section class="content" style="padding-top: 20px;">
  <div class="box" style="border:solid 1px #cccccc;">
    <div id="generalTabContent" class="t  ab-content" style="height: 151px;">
      <!-- 角色列表 -->
      <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
        <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
          <tr>
            <td>
              <div class="worddiv">标题<span>*</span>：</div>
              <input id="title" class="nrinput" style="margin-left: 0px;float: left;" type="text" placeholder="请输入标题名称"/>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">地区<span>*</span>：</div>
              <div class="dropdown" style="width:130px;float:left;margin-left: 0px;">
                <a data-toggle="dropdown" style="color: #333;cursor: pointer;">
                  <span id="sprword" title=""><span style="color:#ccc">请选择地区</span></span>
                  <span class="caret"></span>
                </a>
                <ul class="dropdown-menu"  role="menu" aria-labelledby="dLabel" id="sprul" style="width:150px;left:0px; ">
                  <li><a onclick="setspr('sprword',this)" title="1">浙江省港航管理局</a></li>
                  <li><a onclick="setspr('sprword',this)" title="2">杭州市港航管理局</a></li>
                  <li><a onclick="setspr('sprword',this)" title="3">嘉兴市港航管理局</a></li>
                  <li><a onclick="setspr('sprword',this)" title="4">湖州市港管理航局</a></li>
                  <li><a onclick="setspr('sprword',this)" title="5">舟山市港管理航局</a></li>
                </ul>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">类型<span>*</span>：</div>
              <div class="dropdown" style="width:120px;float:left;margin-left: 0px;">
                <a data-toggle="dropdown" style="color: #333;cursor: pointer;">
                  <span id="sprword1" title=""><span style="color:#ccc">请选择通知类型</span></span>
                  <span class="caret"></span>
                </a>
                <ul class="dropdown-menu"  role="menu" aria-labelledby="dLabel" id="sprul1" style="width:120px;left:0px; ">
                  <li><a onclick="setspr('sprword1',this)" title="4">通知公告</a></li>
                  <li><a onclick="setspr('sprword1',this)" title="5">行政通知</a></li>
                  <li><a onclick="setspr('sprword1',this)" title="6">航行预警</a></li>
                </ul>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
    <div style="width:100%;float:left;min-height: 100px;background-color: white;margin-bottom: 20px;border:solid 1px #ccc;border-radius: 4px;">
      <h5 style="text-align: left;padding-left: 10px;font-weight: 700">通告内容</h5>
      <div id="summernote"></div>
    </div>
  <button type="button" class="btn btn-primary" onclick="Sumit()">提交审核</button>
  <button type="button" class="btn btn-default" style="margin-left: 10px;" onclick="javascript:window.location.href=$('#basePath').val()+'xinxiquery_list';">取消</button>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
