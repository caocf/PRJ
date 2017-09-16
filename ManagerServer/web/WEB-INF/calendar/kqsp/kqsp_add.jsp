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
  <title>请假申请</title>

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
  <script src="../js/calendar/kqsp_add.js" type="text/javascript"></script>
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
  String id = (String)request.getAttribute("id");
%>
<body>
<input type="hidden" value="<%=id%>" id="qjid"/>
<section class="content-header" style="background-color: white;padding: 10px;box-shadow: 0px 3px 3px #ccc;">
  <h1>
    请假申请
    <!-- <small>advanced tables</small> -->
    <i class="fa fa-close" style="float: right;cursor: pointer" onclick="javascript :history.back(-1);"></i>
  </h1>
</section>

<section class="content" style="padding-top: 20px;">
  <input type="hidden" id="addtype" value="1"/>
  <div class="box" style="border:solid 1px #cccccc;">
    <div id="generalTabContent" class="t  ab-content" style="height: 350px;">
      <!-- 角色列表 -->
      <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
        <table class="table col-xs-12" id="rolelist-info" style="border-top:none">
          <tr>
            <td>
              <div class="worddiv">请假类型<span>*</span>：</div>
              <div class="dropdown" style="width:90px;float:left;">
                <a data-toggle="dropdown" style="color: #333;cursor: pointer;">
                  <span id="qjtypeword">事假</span>
                  <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                  <li><a onclick="setaddtype(1)">事假</a></li>
                  <li><a onclick="setaddtype(4)">婚假</a></li>
                  <li><a onclick="setaddtype(3)">病假</a></li>
                  <li><a onclick="setaddtype(6)">产假</a></li>
                  <li><a onclick="setaddtype(7)">丧假</a></li>
                </ul>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">请假时间<span>*</span>：</div>
              <div style="float:left;">
                <input type="text" placeholder="起始时间" id="beginTime" onFocus="WdatePicker({onpicked:getduringtime,maxDate:'#F{$dp.$D(\'endTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd HH:00',isShowClear:false})" readonly="readonly" class="Wdate" style="float:left;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;" />
                <span style="float:left;height:35px;line-height: 35px;vertical-align: middle;margin-left:10px;" >至</span>
                <input type="text" id="endTime" placeholder="结束时间" onFocus="WdatePicker({onpicked:getduringtime,minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd HH:00',isShowClear:false})" readonly="readonly" class="Wdate" style="float:left;margin-left:10px;height:30px;line-height: 30px;padding-left:10px;border-radius:4px;border: solid 1px #ccc;" />
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <div class="worddiv">请假时长<span>*</span>：</div>
              <span id="qjNo">0</span>
            </td>
          </tr>
          <tr style="height:150px!important;">
            <td>
              <div class="worddiv">请假事由<span>*</span>：</div>
              <textarea id='syword' rows="10" cols="10" style="resize: none;width:60%;height:110px;border: 0;" placeholder="请输入事由"></textarea>
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
    <span id="qjfootspan"></span><br><br>
  <button type="button" class="btn btn-primary" onclick="addkq()">提交</button>
  <button type="button" class="btn btn-default" style="margin-left: 10px;" onclick="javascript :history.back(-1);">取消</button>
</section>
<input type="hidden" id="infoId"/>
</body>
</html>
