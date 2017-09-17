<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-欢迎进入行业基本信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <h4 style="color: rgb(92,92,92);">基本情况</h4>
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
   
     平湖公共自行车二期工程39个网点（新客运站网点今年9月建成）经过前期调试后，车辆开始逐步上架，2014年7月31日上午6时起全面投入运行。  
   </p>
   <br>
   <h4 style="color: rgb(92,92,92);">办卡地点</h4>
   <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
        公共自行车公司办卡中心或移动公司代理办卡点办理
   </p>
   <br>
    <h4 style="color: rgb(92,92,92);">办卡时间</h4>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
       8:00 - 18:00，节假日照常；<br/>
   
     </p>
     <br>
    <h4 style="color: rgb(92,92,92);">收费情况</h4>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(51,51,51);">
      租车服务费用实行分段计费：1小时内免费，1小时以上2小时以内1元，2小时以上3小时以内2元，3小时以上每小时3元，24小时内以10元计费，超过24小时重新按标准计费（不足1小时按1小时计）。 

    
    </p>
    <p  style="font-size: 14px;font-weight: normal;letter-spacing: 2px;color:rgb(244,108,8);">温馨提示：请大家记住租还车时间，以避免自行车在家过夜产生大量费用</p>
    </body>
</html>
