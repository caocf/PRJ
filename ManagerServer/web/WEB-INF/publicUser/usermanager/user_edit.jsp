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
  <title>用户编辑</title>

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
  <script src="../js/publicuser/user_edit.js" type="text/javascript"></script>
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
    .table th{
      border-top:solid 1px #ccc!important;
    }
    input.form-control{
      border: 0!important;
    }
    .modaltable td{
      border-top: 0!important;
    }
    .modaltable tr>td:first-child{
      text-align: right;
      padding-right:20px;
    }
  </style>
</head>
<%
  String id = (String)request.getAttribute("id");
%>
<body>
<input type="hidden" value="<%=id%>" id="qjid"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    内河船户编辑
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
              <div class="worddiv">地区<span>*</span>：</div>
              <span id="dq">内河</span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">用户类型<span>*</span>：</div>
              <span id="yhlx">船户</span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">用户名<span>*</span>：</div>
              <span id="username">11121</span>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">所在城市<span>*</span>：</div>
              <div class="dropdown" style="width:120px;float:left;">
                <a data-toggle="dropdown" style="color: #333;cursor: pointer;">
                  <span id="sprword"><span style="color:#ccc">请选择所在城市</span></span>
                  <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel" id="sprul" style="width:110px;">
                  <li><a onclick="setspr(1,this)">事假</a></li>
                </ul>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">手机号<span>*</span>：</div>
              <input  class="form-control"  placeholder="请输入手机号" style="float: left;width:300px;" type="text"/>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">密码<span>*</span>：</div>
              <input  class="form-control"  placeholder="请输入密码" style="float: left;width:300px;" type="password"/>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">确认密码<span>*</span>：</div>
              <input  class="form-control"  placeholder="确认密码" style="float: left;width:300px;" type="password"/>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <div class="box" style="border:solid 1px #cccccc;background: white;float: left">
    <div style="float: left;width: 100%;padding: 10px;">
      <div class="worddiv">绑定的船舶：</div>
    </div>
    <div style="float: left;width: 100%;padding: 10px;">
      <button type="button" class="btn btn-primary" onclick="" data-toggle="modal"
              data-target="#addshipModal">新增船舶</button>
    </div>
    <div  class="tab-pane fade in active" style="float: left;padding: 20px;width: 100%;">
      <table class="table table-hover col-xs-12" id="kqtable" style="border:solid 1px #ccc;">
        <tr style='background-color: rgb(240,245,248);'>
          <th>序号</th>
          <th>船名号</th>
          <th>船舶登记号</th>
          <th>附件</th>
          <th>操作</th>
        </tr>
        <tr>
          <td>01</td>
          <td>蕾姆</td>
          <td>15000000000</td>
          <td>船户</td>
          <td>
            <span class="clickword">启用</span>
          </td>
        </tr>
      </table>
    </div>
  </div>
  <button type="button" class="btn btn-primary" onclick="">保存</button>
  <button type="button" class="btn btn-default" style="margin-left: 10px;" onclick="javascript :history.back(-1);">取消</button>
</section>
<input type="hidden" id="infoId"/>
<!-- 船舶新增模态框（Modal） -->
<div class="modal fade" id="addshipModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal" aria-hidden="true">
          &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
          绑定船舶
        </h4>
      </div>
      <div class="modal-body" style="height: 320px;">
        <table class="table col-xs-12 modaltable" style="border:0;">
          <tr>
            <td class="col-xs-3">船名号<span style="color: red">*</span>：</td>
            <td class="col-xs-9"><input  class="form-control"  placeholder="请输入船名号" style="width:300px;" type="text"/></td>
          </tr>
          <tr>
            <td class="col-xs-3">船舶登记号<span style="color: red">*</span>：</td>
            <td class="col-xs-9"><input  class="form-control"  placeholder="请输入船舶登记号" style="width:300px;" type="text"/></td>
          </tr>
          <tr>
            <td class="col-xs-3">附件<span style="color: red">*</span>：</td>
            <td rowspan="3" class="col-xs-9">
              <p style="text-align: left;">等级证书</p>
              <div style="width:100px;height:100px;border: solid 1px red"></div>
              <div class="form-group">
                <div class="btn btn-default btn-file" style="margin-top: 15px;">
                  <i class="fa fa-paperclip"></i> 点击上传
                  <input type="file" name="attachment" id="attachment" multiple="multiple"/>
                </div>&nbsp;&nbsp;<span id="filenamespan"></span>
              </div>
            </td>
          </tr>
          <tr>
            <td class="col-xs-3"></td>
          </tr>
          <tr>
            <td class="col-xs-3"></td>
          </tr>
        </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary">
          确定
        </button>
        <button type="button" class="btn btn-default"
                data-dismiss="modal">关闭
        </button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal -->
</body>
</html>
