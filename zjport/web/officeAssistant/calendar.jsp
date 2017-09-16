<%@ page language="java" import="com.zjport.util.CommonConst" pageEncoding="UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://"
          + request.getServerName() + ":" + request.getServerPort()
          + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>日程安排</title>
  <meta charset='utf-8' />
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <link rel='stylesheet' href='../fullcalendar/lib/cupertino/jquery-ui.min.css' />
  <link href='../fullcalendar/fullcalendar.css' rel='stylesheet' />
  <link href='../fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
  <script src='../fullcalendar/lib/moment.min.js'></script>
  <script src='../fullcalendar/lib/jquery.min.js'></script>
  <script src='../fullcalendar/fullcalendar.min.js'></script>
  <script src='../fullcalendar/lang-all.js'></script>
  <script src='../js/officeAssistant/calendar.js'></script>
  <style type="text/css">
    td.fc-event-container{
      cursor:pointer!important;
    }
  </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath" />
<div style="width:100%;float:left;line-height: 50px;padding:0 10px;text-align: left;">
  <b style="font-size: 20px;">日程安排</b>
  <span style="float: right;font-size: 14px;">办公助理&gt日程安排</span>
</div>
<div style="float: left;width:100%;padding:10px;">
  <div style="float: left;width:100%;padding:20px;background-color: white;">
    <div id="data" ></div>
  </div>
</div>
</body>
</html>
